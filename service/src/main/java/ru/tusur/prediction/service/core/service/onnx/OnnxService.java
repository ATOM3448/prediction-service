package ru.tusur.prediction.service.core.service.onnx;

import static ru.tusur.prediction.service.core.service.onnx.OnnxConstants.*;

import ai.onnxruntime.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.prediction.dto.student.DisciplineResultsDto;
import ru.tusur.prediction.service.api.prediction.dto.student.SemesterResultsDto;
import ru.tusur.prediction.service.api.prediction.dto.student.StudentResultsDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ErrorMessages;
import ru.tusur.prediction.service.core.error.ServiceException;

/**
 * Сервис для работы с мл-моделью.
 */
@Slf4j
@Service
@AllArgsConstructor
public class OnnxService {

    OrtEnvironment ortEnvironment;

    OrtSession ortSession;

    /**
     * Возвращает средний балл на ближайшей сессии студента.
     *
     * @param studentResults Данные по успеваемости студента.
     * @return Средний балл, округленный до целого.
     */
    public boolean predict(StudentResultsDto studentResults) {
        float[][][] inputData = convertToInputData(studentResults);

        try {
            OnnxTensor inputTensor = OnnxTensor.createTensor(ortEnvironment, inputData);

            log.info("Запрос предсказания у модели");

            boolean result = (
                    (boolean[]) ortSession.run(Collections.singletonMap("input", inputTensor)).get(0).getValue()
            )[0];

            log.info("Модель вернула результат: [{}]", result);

            return result;
        } catch (OrtException exception) {
            throw new ServiceException(
                    ErrorCode.INTERNAL_ERROR, ErrorMessages.INTERNAL_ERROR_MESSAGE);
        }
    }

    private float[][][] convertToInputData(StudentResultsDto studentResults) {
        float[][][] inputData = new float[1][SEQUENCE_LENGTH][FEATURE_SIZE];

        Arrays.stream(inputData[0]).forEach(row -> Arrays.fill(row, 0.0f));

        IntStream.range(0, Math.min(studentResults.semesters().size(), MAX_SEMESTERS_COUNT_SUPPORTED))
                .forEach(semesterIdx -> {
                    SemesterResultsDto semester = studentResults.semesters().get(semesterIdx);
                    List<DisciplineResultsDto> sortedDisciplines = semester.disciplines().stream()
                            .sorted(Comparator.comparing(DisciplineResultsDto::resultSession).reversed())
                            .limit(MAX_DISCIPLINES_COUNT_SUPPORTED)
                            .toList();

                    IntStream.range(0, sortedDisciplines.size())
                            .forEach(disciplineIdx -> {
                                DisciplineResultsDto discipline = sortedDisciplines.get(disciplineIdx);
                                int position = semesterIdx * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIdx;

                                float[] features = {
                                        discipline.resultSession(),
                                        discipline.resultControlPointFirst(),
                                        discipline.resultControlPointSecond(),
                                        discipline.attendanceLectureFirstPeriod(),
                                        discipline.attendanceLectureSecondPeriod(),
                                        discipline.attendanceLectureThirdPeriod(),
                                        discipline.attendancePracticeFirstPeriod(),
                                        discipline.attendancePracticeSecondPeriod(),
                                        discipline.attendancePracticeThirdPeriod()
                                };

                                System.arraycopy(features, 0, inputData[0][position], 0, FEATURE_SIZE);
                            });
                });

        return inputData;
    }
}

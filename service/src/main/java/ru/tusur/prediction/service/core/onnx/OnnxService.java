package ru.tusur.prediction.service.core.onnx;

import static ru.tusur.prediction.service.core.onnx.OnnxConstants.*;

import ai.onnxruntime.*;
import java.util.Collections;
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

            float result =
                    ((float[])
                                    ortSession
                                            .run(Collections.singletonMap("input", inputTensor))
                                            .get(0)
                                            .getValue())
                            [0];

            log.info("Модель вернула результат: [{}]", result);

            return result > 3.0;
        } catch (OrtException exception) {
            throw new ServiceException(
                    ErrorCode.INTERNAL_ERROR, ErrorMessages.INTERNAL_ERROR_MESSAGE);
        }
    }

    private float[][][] convertToInputData(StudentResultsDto studentResults) {
        float[][][] inputData = new float[1][SEQUENCE_LENGTH][FEATURE_SIZE];

        int semesterIndex = 0;

        for (SemesterResultsDto semester : studentResults.semesters()) {
            int disciplineIndex = 0;

            for (DisciplineResultsDto discipline : semester.disciplines()) {
                int featureIndex = 0;

                inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendanceLectureFirstPeriod();
                inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendanceLectureSecondPeriod();
                inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendanceLectureThirdPeriod();
                inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendancePracticeFirstPeriod();
                inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendancePracticeSecondPeriod();
                inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendancePracticeThirdPeriod();
                inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.resultControlPointFirst();
                inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.resultControlPointSecond();
                inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex] =
                        discipline.resultSession();

                disciplineIndex++;
            }

            for (; disciplineIndex < MAX_DISCIPLINES_COUNT_SUPPORTED; disciplineIndex++) {
                for (int featureIndex = 0; featureIndex < FEATURE_SIZE; featureIndex++) {
                    inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                    featureIndex] =
                            0.0f;
                }
            }

            semesterIndex++;
        }

        for (; semesterIndex < MAX_SEMESTERS_COUNT_SUPPORTED; semesterIndex++) {
            for (int disciplineIndex = 0;
                    disciplineIndex < MAX_DISCIPLINES_COUNT_SUPPORTED;
                    disciplineIndex++) {
                for (int featureIndex = 0; featureIndex < FEATURE_SIZE; featureIndex++) {
                    inputData[0][semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                    featureIndex] =
                            0.0f;
                }
            }
        }

        return inputData;
    }
}

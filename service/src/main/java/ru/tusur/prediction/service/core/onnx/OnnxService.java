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
    public int predict(StudentResultsDto studentResults) {

        float[][] inputData = getInputData(studentResults);

        try {

            OnnxTensor inputTensor = OnnxTensor.createTensor(ortEnvironment, inputData);

            long[] shape = inputTensor.getInfo().getShape();

            log.info("Запрос предсказания у модели");

            OnnxValue result =
                    ortSession.run(Collections.singletonMap("input", inputTensor)).get(0);

            int averageResult = getIndexOfMax(result);

            if (averageResult != 0) {
                averageResult++;
            }

            return averageResult;
        } catch (OrtException exception) {
            throw new ServiceException(
                    ErrorCode.INTERNAL_ERROR, ErrorMessages.INTERNAL_ERROR_MESSAGE);
        }
    }

    private static float[][] getInputData(StudentResultsDto studentResults) {
        float[][] inputData = new float[SEQUENCE_LENGTH][FEATURE_SIZE];

        int semesterIndex = 0;

        for (SemesterResultsDto semester : studentResults.semesters()) {
            int disciplineIndex = 0;

            for (DisciplineResultsDto discipline : semester.disciplines()) {
                int featureIndex = 0;

                inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendanceLectureFirstPeriod();
                inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendanceLectureSecondPeriod();
                inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendanceLectureThirdPeriod();
                inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendancePracticeFirstPeriod();
                inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendancePracticeSecondPeriod();
                inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.attendancePracticeThirdPeriod();
                inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.resultControlPointFirst();
                inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex++] =
                        discipline.resultControlPointSecond();
                inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                featureIndex] =
                        discipline.resultSession();

                disciplineIndex++;
            }

            for (; disciplineIndex < MAX_DISCIPLINES_COUNT_SUPPORTED; disciplineIndex++) {
                for (int featureIndex = 0; featureIndex < FEATURE_SIZE; featureIndex++) {
                    inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
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
                    inputData[semesterIndex * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIndex][
                                    featureIndex] =
                            0.0f;
                }
            }
        }

        return inputData;
    }

    private static int getIndexOfMax(OnnxValue value) throws OrtException {
        float[] predictionValue = (float[]) value.getValue();

        int maxIndex = 0;
        float maxValue = predictionValue[0];

        for (int i = 1; i < predictionValue.length; i++) {
            if (predictionValue[i] > maxValue) {
                maxValue = predictionValue[i];
                maxIndex = i;
            }
        }

        log.info("Модель вернула предсказание [{}]", predictionValue);

        return maxIndex;
    }
}

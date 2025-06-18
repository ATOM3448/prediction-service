package ru.tusur.prediction.service.core.service.onnx;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.prediction.dto.student.DisciplineResultsDto;
import ru.tusur.prediction.service.api.prediction.dto.student.SemesterResultsDto;
import ru.tusur.prediction.service.api.prediction.dto.student.StudentResultsDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ErrorMessages;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.discipline.course.DisciplineCourse;
import ru.tusur.prediction.service.core.model.indicator.Indicator;
import ru.tusur.prediction.service.core.model.indicator.IndicatorType;
import ru.tusur.prediction.service.core.model.result.Result;
import ru.tusur.prediction.service.core.model.result.view.onnx.Discipline;
import ru.tusur.prediction.service.core.service.discipline.course.DisciplineCourseService;
import ru.tusur.prediction.service.core.service.indicator.IndicatorService;
import ru.tusur.prediction.service.core.service.result.ResultService;

import static ru.tusur.prediction.service.core.error.ErrorCode.INTERNAL_ERROR;
import static ru.tusur.prediction.service.core.error.ErrorCode.OBJECT_NOT_FOUND;
import static ru.tusur.prediction.service.core.error.ErrorMessages.INTERNAL_ERROR_MESSAGE;
import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;
import static ru.tusur.prediction.service.core.service.onnx.OnnxConstants.FEATURE_SIZE;
import static ru.tusur.prediction.service.core.service.onnx.OnnxConstants.MAX_DISCIPLINES_COUNT_SUPPORTED;
import static ru.tusur.prediction.service.core.service.onnx.OnnxConstants.MAX_SEMESTERS_COUNT_SUPPORTED;
import static ru.tusur.prediction.service.core.service.onnx.OnnxConstants.SEQUENCE_LENGTH;

@Slf4j
@Service
@RequiredArgsConstructor
public class OnnxService {

    private final ResultService resultService;

    private final DisciplineCourseService disciplineCourseService;

    private final IndicatorService indicatorService;

    private final OrtEnvironment ortEnvironment;

    private final OrtSession ortSession;

    public Boolean predict(long studentId) {
        List<Result> resultsByStudentId = resultService.getResultsByStudentId(studentId);

        if (resultsByStudentId.isEmpty()) {
            throw new ServiceException(OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }

        List<DisciplineCourse> courses = resultsByStudentId.stream()
                .map(Result::disciplineCourseId)
                .collect(Collectors.toSet()).stream()
                .map(disciplineCourseService::getDisciplineCourseByIdNotProtected)
                .toList();

        List<Indicator> indicators = resultsByStudentId.stream()
                .map(Result::indicatorId)
                .collect(Collectors.toSet()).stream()
                .map(indicatorService::getIndicatorByIdNotProtected)
                .toList();

        List<Discipline> disciplines = courses.stream().map(course -> {
            Map<IndicatorType, Double> indicatorResults = resultsByStudentId.stream()
                    .filter(result -> result.disciplineCourseId() == course.id())
                    .collect(Collectors.toMap(
                            result -> {
                                // Находим тип индикатора по ID
                                Indicator indicator = indicators.stream()
                                        .filter(ind -> ind.id() == result.indicatorId())
                                        .findFirst()
                                        .orElseThrow(() -> new ServiceException(INTERNAL_ERROR, INTERNAL_ERROR_MESSAGE));
                                return indicator.type();
                            },
                            Result::value
                    ));

            return new Discipline(
                    course.disciplineId(),
                    indicatorResults,
                    course.semester()
            );
        }).toList();

        float[][][] inputData = new float[1][SEQUENCE_LENGTH][FEATURE_SIZE];

        Arrays.stream(inputData[0]).forEach(row -> Arrays.fill(row, 0.0f));

        Map<Integer, List<Discipline>> disciplinesBySemester = disciplines.stream()
                .collect(Collectors.groupingBy(Discipline::semester));

        List<Integer> sortedSemesters = disciplinesBySemester.keySet().stream()
                .sorted()
                .toList();

        IntStream.range(0, Math.min(sortedSemesters.size(), MAX_SEMESTERS_COUNT_SUPPORTED))
                .forEach(semesterIdx -> {
                    int semester = sortedSemesters.get(semesterIdx);
                    List<Discipline> semesterDisciplines = disciplinesBySemester.get(semester);

                    List<Discipline> sortedDisciplines = semesterDisciplines.stream()
                            .sorted(Comparator.comparing(d ->
                                            d.results().getOrDefault(IndicatorType.RESULT_SESSION, 0.0),
                                    Comparator.reverseOrder()))
                            .limit(MAX_DISCIPLINES_COUNT_SUPPORTED)
                            .toList();

                    IntStream.range(0, sortedDisciplines.size())
                            .forEach(disciplineIdx -> {
                                Discipline discipline = sortedDisciplines.get(disciplineIdx);
                                Map<IndicatorType, Double> results = discipline.results();

                                int position = semesterIdx * MAX_DISCIPLINES_COUNT_SUPPORTED + disciplineIdx;

                                float[] features = {
                                        results.getOrDefault(IndicatorType.RESULT_SESSION, 0.0).floatValue(),
                                        results.getOrDefault(IndicatorType.RESULT_CONTROL_POINT_FIRST, 0.0).floatValue(),
                                        results.getOrDefault(IndicatorType.RESULT_CONTROL_POINT_SECOND, 0.0).floatValue(),
                                        results.getOrDefault(IndicatorType.ATTENDANCE_LECTURE_FIRST_PERIOD, 0.0).floatValue(),
                                        results.getOrDefault(IndicatorType.ATTENDANCE_LECTURE_SECOND_PERIOD, 0.0).floatValue(),
                                        results.getOrDefault(IndicatorType.ATTENDANCE_LECTURE_THIRD_PERIOD, 0.0).floatValue(),
                                        results.getOrDefault(IndicatorType.ATTENDANCE_PRACTICE_FIRST_PERIOD, 0.0).floatValue(),
                                        results.getOrDefault(IndicatorType.ATTENDANCE_PRACTICE_SECOND_PERIOD, 0.0).floatValue(),
                                        results.getOrDefault(IndicatorType.ATTENDANCE_PRACTICE_THIRD_PERIOD, 0.0).floatValue()
                                };

                                System.arraycopy(features, 0, inputData[0][position], 0, FEATURE_SIZE);
                            });
                });

        return predict(inputData);
    }

    public boolean predict(StudentResultsDto studentResults) {
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

        return predict(inputData);
    }

    private boolean predict(float[][][] inputData) {
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

}

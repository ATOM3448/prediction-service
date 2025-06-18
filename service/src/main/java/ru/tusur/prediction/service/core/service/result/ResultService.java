package ru.tusur.prediction.service.core.service.result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.data.dto.result.CreateResultDto;
import ru.tusur.prediction.service.api.data.dto.result.UpdateResultDto;
import ru.tusur.prediction.service.api.data.dto.result.filter.ResultFilterDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.result.Result;
import ru.tusur.prediction.service.core.repository.ResultRepository;
import ru.tusur.prediction.service.core.service.discipline.course.DisciplineCourseService;
import ru.tusur.prediction.service.core.service.indicator.IndicatorService;
import ru.tusur.prediction.service.core.service.student.StudentService;

import java.time.LocalDate;
import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultService {

    private final IndicatorService indicatorService;

    private final StudentService studentService;

    private final DisciplineCourseService disciplineCourseService;

    private final ResultRepository resultRepository;

    public List<Result> getResults(
            long programId,
            long profileId,
            long disciplineCourseId,
            ResultFilterDto filter
    ) {
        disciplineCourseService.getDisciplineCourse(programId, profileId, disciplineCourseId);

        if (filter != null) {
            Long indicatorId = filter.indicatorId();
            if (indicatorId != null) {
                indicatorService.getIndicator(indicatorId);
            }
            Long studentId = filter.studentId();
            if (studentId != null) {
                studentService.getStudent(studentId);
            }
        }

        return resultRepository.getResultsByDisciplineCourseIdAndFilter(
                disciplineCourseId,
                createConditional(filter)
        );
    }

    public List<Result> getResultsByStudentId(long studentId) {
        studentService.getStudent(studentId);

        return resultRepository.getResultsByFilter(createConditional(new ResultFilterDto(
                null,
                studentId,
                null,
                null,
                null,
                null
        )));
    }

    public Result getResult(long programId, long profileId, long disciplineCourseId, long id) {
        disciplineCourseService.getDisciplineCourse(programId, profileId, disciplineCourseId);

        Result result = resultRepository.getResultById(id);

        checkExistenceOfResult(result, disciplineCourseId);

        return result;
    }

    public Result saveResult(long programId, long profileId, long disciplineCourseId, CreateResultDto result) {
        disciplineCourseService.getDisciplineCourse(programId, profileId, disciplineCourseId);

        long indicatorId = result.indicatorId();
        indicatorService.getIndicator(indicatorId);

        long studentId = result.studentId();
        studentService.getStudent(studentId);

        double value = result.value();
        LocalDate date = result.date();
        boolean isRetake = result.isRetake();

        Result savedResult = resultRepository.saveResult(
                disciplineCourseId,
                indicatorId,
                studentId,
                value,
                date,
                isRetake
        );

        log.info(
                "Данные по результатам #{} сохранены",
                savedResult.id()
        );

        return savedResult;
    }

    public Result updateResult(
            long programId,
            long profileId,
            long disciplineCourseId,
            long id,
            UpdateResultDto result
    ) {
        disciplineCourseService.getDisciplineCourse(programId, profileId, disciplineCourseId);

        Result oldResult = resultRepository.getResultById(id);

        checkExistenceOfResult(oldResult, disciplineCourseId);

        double newValue = result.value();
        LocalDate newDate =  result.date();
        boolean isRetake =  result.isRetake();

        Result updatedResult = resultRepository.updateResult(
                id,
                newValue,
                newDate,
                isRetake
        );

        log.info(
                "Данные по результату #{} обновлены на [\"{}\", {}, {}]",
                id,
                newValue,
                newDate,
                isRetake
        );

        return updatedResult;
    }

    private String createConditional(ResultFilterDto filter) {
        if (filter == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        if (filter.indicatorId() != null) {
            builder.append("\nand indicator_id = ").append(filter.indicatorId());
        }
        if (filter.studentId() != null) {
            builder.append("\nand student_id = ").append(filter.studentId());
        }
        if (filter.value() != null) {
            if (filter.value().value() != null) {
                builder.append("\nand value ");

                if (filter.value().type() == null) {
                    builder.append("= ");
                } else {
                    switch (filter.value().type()) {
                        case INCLUDE_EQUALS_TO -> builder.append("= ");
                        case NOT_INCLUDE_EQUALS_TO -> builder.append("<> ");
                        case INCLUDE_GREATER_THAN -> builder.append("> ");
                        case NOT_INCLUDE_GREATER_THAN -> builder.append("<= ");
                        case INCLUDE_LESS_THAN -> builder.append("< ");
                        case NOT_INCLUDE_LESS_THAN -> builder.append(">= ");
                    }
                }

                builder.append(filter.value().value());
            }
        }
        if (filter.date() != null) {
            if (filter.date().date() != null) {
                builder.append("\nand date ");

                if (filter.date().type() == null) {
                    builder.append("= ");
                } else {
                    switch (filter.date().type()) {
                        case INCLUDE_EQUALS_TO -> builder.append("= ");
                        case NOT_INCLUDE_EQUALS_TO -> builder.append("<> ");
                        case INCLUDE_GREATER_THAN -> builder.append("> ");
                        case NOT_INCLUDE_GREATER_THAN -> builder.append("<= ");
                        case INCLUDE_LESS_THAN -> builder.append("< ");
                        case NOT_INCLUDE_LESS_THAN -> builder.append(">= ");
                    }
                }

                builder.append(filter.date().date());
            }
        }
        if (filter.isRetake() != null) {
            builder.append("\nand is_retake = ").append(filter.isRetake());
        }
        if (filter.isPrediction() != null) {
            builder.append("\nand is_prediction = ").append(filter.isPrediction());
        }

        return builder.toString();
    }

    private void checkExistenceOfResult(Result result, long disciplineCourseId) {
        if ((result == null) || (disciplineCourseId != result.disciplineCourseId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }
    }
}

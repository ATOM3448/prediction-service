package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.tusur.prediction.service.api.data.dto.PageDto;
import ru.tusur.prediction.service.api.data.dto.discipline.course.DisciplineCourseDto;
import ru.tusur.prediction.service.api.data.dto.discipline.course.CreateDisciplineCourseDto;
import ru.tusur.prediction.service.api.data.mapper.DisciplineCourseToDisciplineCourseDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.ResourceCreatedApiResponse;
import ru.tusur.prediction.service.core.service.discipline.course.DisciplineCourseService;

import java.net.URI;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_DISCIPLINE_COURSE;
import static ru.tusur.prediction.service.api.data.ApiPaths.ID;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными курсов дисциплин")
@RestController
@RequestMapping(DATA_API_DISCIPLINE_COURSE)
@Validated
@AllArgsConstructor
public class DisciplineCourseController {

    private final DisciplineCourseService disciplineCourseService;

    private final DisciplineCourseToDisciplineCourseDtoMapper disciplineCourseToDisciplineCourseDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по курсам дисциплин")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<DisciplineCourseDto> getAllDisciplineCourses(
            @RequestParam(defaultValue = "0")
                    @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
                    int page,
            @RequestParam(defaultValue = "25")
                    @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                    @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
                    int size,
            @PathVariable
                    long programId,
            @PathVariable
                    long profileId
    ) {
        return mapToPage(
                disciplineCourseService.getDisciplineCourses(programId, profileId),
                page,
                size,
                disciplineCourseToDisciplineCourseDtoMapper::map
        );
    }

    @GetMapping(ID)
    @Operation(description = "Возвращает данные по курсу дисциплины")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public DisciplineCourseDto getDisciplineCourse(
            @PathVariable long programId,
            @PathVariable long profileId,
            @PathVariable long id
    ) {
        return disciplineCourseToDisciplineCourseDtoMapper.map(disciplineCourseService.getDisciplineCourse(programId, profileId, id));
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по курсу дисциплины")
    @ResourceCreatedApiResponse
    @NotFoundApiResponse
    @BadRequestApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public ResponseEntity<DisciplineCourseDto> createDisciplineCourse(
            @PathVariable long programId,
            @PathVariable long profileId,
            @Valid @RequestBody CreateDisciplineCourseDto disciplineCourse
    ) {
        DisciplineCourseDto created = disciplineCourseToDisciplineCourseDtoMapper.map(disciplineCourseService.saveDisciplineCourse(
                programId,
                profileId,
                disciplineCourse
        ));

        URI location = UriComponentsBuilder.fromPath(DATA_API_DISCIPLINE_COURSE)
                .buildAndExpand(programId, profileId)
                .toUri()
                .resolve("/" + created.id());

        return ResponseEntity.created(location).body(created);
    }
    
}

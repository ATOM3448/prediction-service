package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.dto.PageDto;
import ru.tusur.prediction.service.api.data.dto.teacher.TeacherDto;
import ru.tusur.prediction.service.api.data.mapper.TeacherToTeacherDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.core.service.teacher.TeacherService;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_TEACHER;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными преподавателей")
@RestController
@RequestMapping(DATA_API_TEACHER)
@Validated
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    private final TeacherToTeacherDtoMapper teacherToTeacherDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по преподавателям")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<TeacherDto> getTeachers(
            @RequestParam(defaultValue = "0")
                    @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
                    int page,
            @RequestParam(defaultValue = "25")
                    @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                    @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
                    int size,
            @PathVariable
                    long facultyId,
            @PathVariable
                    long departmentId
    ) {
        return mapToPage(
                teacherService.getTeachers(facultyId, departmentId),
                page,
                size,
                teacherToTeacherDtoMapper::map
        );
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по преподавателю")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public TeacherDto createTeacher(
            @PathVariable long facultyId,
            @PathVariable long departmentId
    ) {
        return teacherToTeacherDtoMapper.map(teacherService.saveTeacher(facultyId, departmentId));
    }

}

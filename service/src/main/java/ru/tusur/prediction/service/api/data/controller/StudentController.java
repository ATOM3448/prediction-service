package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.dto.PageDto;
import ru.tusur.prediction.service.api.data.dto.student.StudentDto;
import ru.tusur.prediction.service.api.data.mapper.StudentToStudentDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.ReadAccess;
import ru.tusur.prediction.service.api.security.annotation.WriteAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.core.service.student.StudentService;


import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_STUDENT;
import static ru.tusur.prediction.service.util.PageMapperUtils.mapToPage;

@Tag(name = "Работа с данными студентов")
@RestController
@RequestMapping(DATA_API_STUDENT)
@Validated
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    private final StudentToStudentDtoMapper studentToStudentDtoMapper;

    @GetMapping
    @Operation(description = "Возвращает данные по студентам")
    @OkApiResponse
    @InternalErrorApiResponse
    @ReadAccess
    public PageDto<StudentDto> getStudents(
            @RequestParam(defaultValue = "0")
                    @Min(value = 0, message = "Страница не может иметь значение ниже нуля")
                    int page,
            @RequestParam(defaultValue = "25")
                    @Min(value = 1, message = "Размер страницы не может иметь значение ниже единицы")
                    @Max(value = 50, message = "Размер страницы не может иметь значение выше пятидесяти")
                    int size
    ) {
        return mapToPage(
                studentService.getStudents(),
                page,
                size,
                studentToStudentDtoMapper::map
        );
    }

    @PostMapping
    @Operation(description = "Сохраняет информацию по студенту")
    @OkApiResponse
    @InternalErrorApiResponse
    @WriteAccess
    public StudentDto createStudent() {
        return studentToStudentDtoMapper.map(studentService.saveStudent());
    }

}

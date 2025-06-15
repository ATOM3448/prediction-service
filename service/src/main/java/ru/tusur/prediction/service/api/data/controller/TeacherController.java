package ru.tusur.prediction.service.api.data.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.mapper.TeacherToTeacherDtoMapper;
import ru.tusur.prediction.service.core.service.teacher.TeacherService;

import static ru.tusur.prediction.service.api.data.ApiPaths.DATA_API_TEACHER;

@Tag(name = "Работа с данными преподавателей")
@RestController
@RequestMapping(DATA_API_TEACHER)
@Validated
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    private final TeacherToTeacherDtoMapper teacherToTeacherDtoMapper;

}

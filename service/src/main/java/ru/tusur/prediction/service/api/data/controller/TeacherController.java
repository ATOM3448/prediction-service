package ru.tusur.prediction.service.api.data.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.teacher.TeacherDto;
import ru.tusur.prediction.service.core.teacher.TeacherService;

import java.util.List;

/**
 * Контроллер для работы с данными преподавателей.
 */
@RestController(ApiPaths.DATA_API_TEACHER)
@AllArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherDto> getTeacher() {
        return null;
    }

    @PostMapping
    public void saveTeacher() {

    }

    @PutMapping
    public void updateTeacher() {

    }

}

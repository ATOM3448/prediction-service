package ru.tusur.prediction.service.api.data.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.student.StudentDto;
import ru.tusur.prediction.service.core.student.StudentService;

import java.util.List;

/**
 * Контроллер для работы с данными студентов.
 */
@RestController(ApiPaths.DATA_API_STUDENT)
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> getStudent() {
        return null;
    }

    @PostMapping
    public void saveStudent() {

    }

    @PutMapping
    public void updateStudent() {

    }
    
}

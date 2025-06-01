package ru.tusur.prediction.service.api.data.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.faculty.FacultyDto;

import java.util.List;

// TODO Аутентификация на всех эндпоинтах
// TODO Подключить свагер
// TODO Логирование
// TODO Заменить использования временного контекста
/**
 * Контроллер для работы с данными факультетов
 */
@RestController(ApiPaths.DATA_API_FACULTY)
@AllArgsConstructor
public class FacultyController {

    @GetMapping
    public List<FacultyDto> getFaculty() {
        return null;
    }

    @PostMapping
    public void saveFaculty() {

    }

    @PutMapping
    public void updateFaculty() {

    }

}

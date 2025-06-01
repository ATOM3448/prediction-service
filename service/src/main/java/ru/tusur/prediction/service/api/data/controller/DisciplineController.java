package ru.tusur.prediction.service.api.data.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.discipline.DisciplineDto;

import java.util.List;

/**
 * Контроллер для работы с данными дисциплин.
 */
@RestController(ApiPaths.DATA_API_DISCIPLINE)
@AllArgsConstructor
public class DisciplineController {

    @GetMapping
    public List<DisciplineDto> getDiscipline() {
        return null;
    }

    @PostMapping
    public void saveDiscipline() {

    }

    @PutMapping
    public void updateDiscipline() {

    }
    
}

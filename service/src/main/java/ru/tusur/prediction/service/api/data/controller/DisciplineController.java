package ru.tusur.prediction.service.api.data.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tusur.prediction.service.api.data.ApiPaths;
import ru.tusur.prediction.service.api.data.dto.discipline.DisciplineDto;
import ru.tusur.prediction.service.core.discipline.DisciplineService;

/**
 * Контроллер для работы с данными дисциплин.
 */
@RestController
@RequestMapping(ApiPaths.DATA_API_DISCIPLINE)
@AllArgsConstructor
public class DisciplineController {

    private final DisciplineService disciplineService;

    @GetMapping
    public List<DisciplineDto> getDiscipline() {
        return null;
    }

    @PostMapping
    public void saveDiscipline() {}

    @PutMapping
    public void updateDiscipline() {}
}

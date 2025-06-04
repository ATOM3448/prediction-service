package ru.tusur.prediction.service.api.prediction.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.prediction.ApiPaths;
import ru.tusur.prediction.service.api.prediction.dto.result.StudentPredictionResult;
import ru.tusur.prediction.service.core.ml.MlService;

/**
 * Контроллер для работы с запросами предсказаний по студентам.
 */
@RestController(ApiPaths.PREDICTION_API_STUDENT)
@AllArgsConstructor
public class StudentController {

    private final MlService mlService;

    @GetMapping
    public StudentPredictionResult getPredictionForStudent() {
        return null;
    }
}

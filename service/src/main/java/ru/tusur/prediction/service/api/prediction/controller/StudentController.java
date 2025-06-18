package ru.tusur.prediction.service.api.prediction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tusur.prediction.service.api.prediction.dto.result.StudentPredictionResultDto;
import ru.tusur.prediction.service.api.prediction.dto.student.StudentResultsDto;
import ru.tusur.prediction.service.api.prediction.mapper.StudentPredictionResultDtoMapper;
import ru.tusur.prediction.service.api.security.annotation.PredictionAccess;
import ru.tusur.prediction.service.configuration.openapi.annotation.BadRequestApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.InternalErrorApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.NotFoundApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.OkApiResponse;
import ru.tusur.prediction.service.core.service.onnx.OnnxService;

import static ru.tusur.prediction.service.api.prediction.ApiPaths.ID;
import static ru.tusur.prediction.service.api.prediction.ApiPaths.PREDICTION_API_STUDENT;

@Tag(name = "Работа с моделью предсказания по данным студентов")
@RestController("predictionApiStudentController")
@RequestMapping(PREDICTION_API_STUDENT)
@Validated
@AllArgsConstructor
@PredictionAccess
public class StudentController {

    private final OnnxService mlService;

    private final StudentPredictionResultDtoMapper studentPredictionResultDtoMapper;

    @GetMapping(ID)
    @Operation(description = "Возвращает предсказание закроет ли студент ближайшую сессию")
    @OkApiResponse
    @NotFoundApiResponse
    @InternalErrorApiResponse
    @PredictionAccess
    public StudentPredictionResultDto getPredictionStudent(@PathVariable long id) {
        return studentPredictionResultDtoMapper.map(mlService.predict(id));
    }

    @PostMapping
    @Operation(description = "Возвращает предсказание закроет ли студент ближайшую сессию")
    @OkApiResponse
    @BadRequestApiResponse
    @InternalErrorApiResponse
    @PredictionAccess
    public StudentPredictionResultDto getPredictionForStudentData(
            @Valid @RequestBody StudentResultsDto studentResults
    ) {
        return studentPredictionResultDtoMapper.map(mlService.predict(studentResults));
    }
}

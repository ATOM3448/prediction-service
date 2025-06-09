package ru.tusur.prediction.service.api.prediction.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Сущность, описывающая результаты предсказания среднего балла студента на ближайшей сессии.
 *
 * @param predictionResult Средний балл студента, округленный до целого.
 */
public record StudentPredictionResultDto(
        @Schema(description = "Средний балл студента, округленный до целого") @NotNull
                Integer predictionResult) {}

package ru.tusur.prediction.service.api.prediction.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Сущность, описывающая результаты предсказания закроет студент сессию или нет.
 *
 * @param predictionResult Флаг обозначающий закрыл ли студент сессию.
 */
public record StudentPredictionResultDto(
        @Schema(description = "Флаг обозначающий закрыл ли студент сессию") @NotNull
                boolean predictionResult) {}

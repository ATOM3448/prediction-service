package ru.tusur.prediction.service.api.prediction.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;

public record StudentPredictionResultDto(
        @Schema(description = "Флаг обозначающий закрыл ли студент сессию")
                boolean predictionResult) {}

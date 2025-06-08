package ru.tusur.prediction.service.api.error;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Данные об ошибке на уровне API.
 */
@Schema(description = "Объект содержащий информацию об ошибке", type = "object")
public record Error(
        @Schema(description = "Тип ошибки", requiredMode = REQUIRED) @NotNull ErrorType type,
        @Schema(description = "Код ошибки", requiredMode = REQUIRED) int code,
        @Schema(description = "Сообщение об ошибке", requiredMode = REQUIRED) @NotNull
                String message) {}

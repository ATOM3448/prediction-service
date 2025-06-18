package ru.tusur.prediction.service.api.data.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Сущность, описывающая данные по результату", name = "UpdateResult")
public record UpdateResultDto(
        @Schema(description = "Значение", requiredMode = Schema.RequiredMode.REQUIRED)
                @DecimalMin(value = "0.0", message = "Значение не может быть меньше 0.0")
                double value,
        @Schema(description = "Дата проставления результата выпуска", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Дата проставления результата не может быть null")
                LocalDate date,
        @Schema(description = "Флаг, является ли результат пересдачей", requiredMode = Schema.RequiredMode.REQUIRED)
                boolean isRetake
) {
}

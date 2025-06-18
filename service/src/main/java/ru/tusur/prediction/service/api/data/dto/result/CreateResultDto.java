package ru.tusur.prediction.service.api.data.dto.result;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Сущность, описывающая данные по результату", name = "CreateResult")
public record CreateResultDto(
        @Schema(description = "Идентификатор индикатора", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор индикатора не может быть меньше 1")
                long indicatorId,
        @Schema(description = "Идентификатор студента", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор студента не может быть меньше 1")
                long studentId,
        @Schema(description = "Значение", requiredMode = Schema.RequiredMode.REQUIRED)
                @DecimalMin(value = "0.0", message = "Значение не может быть меньше 0.0")
                double value,
        @Schema(description = "Дата проставления результата", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Дата проставления результата не может быть null")
                LocalDate date,
        @Schema(description = "Флаг, является ли результат пересдачей", requiredMode = Schema.RequiredMode.REQUIRED)
                boolean isRetake
) {
}

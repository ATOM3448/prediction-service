package ru.tusur.prediction.service.api.data.dto.indicator;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность, описывающая данные по индикатору", name = "Indicator")
public record IndicatorDto(
        @Schema(description = "Идентификатор", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор индикатора не может быть меньше 1")
                long id,
        @Schema(description = "Тип", requiredMode = Schema.RequiredMode.REQUIRED)
                IndicatorTypeDto type,
        @Schema(description = "Наименование индикатора", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Название индикатора не может быть null")
                @Size(min = 1, max = 128, message = "Название индикатора должно быть от 1 до 128 символов")
                String name,
        @Schema(description = "Максимальный возможный результат", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Максимальный результат индикатора не может быть меньше 1")
                int maxValue
) {}

package ru.tusur.prediction.service.api.data.dto.result.filter.date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import ru.tusur.prediction.service.api.data.dto.result.filter.FilterTypeDto;

@Schema(description = "Сущность описывающая фильтр значения результатов", name = "ResultValueFilter")
public record ResultValueFilterDto(
        @Schema(description = "Значение", requiredMode = Schema.RequiredMode.REQUIRED)
                @DecimalMin(value = "0.0", message = "Значение не может быть меньше 0.0")
                Double value,
        @Schema(description = "Тип фильтра", requiredMode = Schema.RequiredMode.REQUIRED)
                FilterTypeDto type
) {}

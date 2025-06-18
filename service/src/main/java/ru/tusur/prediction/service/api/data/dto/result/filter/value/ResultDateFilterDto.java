package ru.tusur.prediction.service.api.data.dto.result.filter.value;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.tusur.prediction.service.api.data.dto.result.filter.FilterTypeDto;

import java.time.LocalDate;

@Schema(description = "Сущность описывающая фильтр даты проставления результатов", name = "ResultDateFilter")
public record ResultDateFilterDto(
        @Schema(description = "Дата проставления результата", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Дата проставления результата не может быть null")
                LocalDate date,
        @Schema(description = "Тип фильтра", requiredMode = Schema.RequiredMode.REQUIRED)
                FilterTypeDto type
) {
}

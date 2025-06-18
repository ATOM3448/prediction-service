package ru.tusur.prediction.service.api.data.dto.result.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import ru.tusur.prediction.service.api.data.dto.result.filter.date.ResultValueFilterDto;
import ru.tusur.prediction.service.api.data.dto.result.filter.value.ResultDateFilterDto;

@Schema(description = "Сущность, описывающая фильтр результатов", name = "ResultFilter")
public record ResultFilterDto(
        @Schema(
                description = "Идентификатор индикатора",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                nullable = true
        )
                @Min(value = 1, message = "Идентификатор индикатора не может быть меньше 0")
                Long indicatorId,
        @Schema(
                description = "Идентификатор студента",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                nullable = true
        )
                @Min(value = 1, message = "Идентификатор студента не может быть меньше 1")
                Long studentId,
        @Schema(description = "Фильтр значения",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                nullable = true
        )
                ResultValueFilterDto value,
        @Schema(description = "Фильтр даты проставления результата",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                nullable = true
        )
                ResultDateFilterDto date,
        @Schema(description = "Флаг, является ли результат пересдачей",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                nullable = true
        )
                Boolean isRetake,
        @Schema(description = "Флаг, является ли результат предсказанием",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED,
                nullable = true
        )
                Boolean isPrediction
) {
}

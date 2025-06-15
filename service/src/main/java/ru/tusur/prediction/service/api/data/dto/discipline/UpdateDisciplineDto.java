package ru.tusur.prediction.service.api.data.dto.discipline;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность, описывающая данные по дисциплине", name = "UpdateDiscipline")
public record UpdateDisciplineDto(
        @Schema(description = "Наименование дисциплины", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Название дисциплины не может быть null")
                @Size(min = 1, max = 128, message = "Название дисциплины должно быть от 1 до 128 символов")
                String name
) {
}

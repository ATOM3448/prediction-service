package ru.tusur.prediction.service.api.data.dto.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность, описывающая данные по образовательной программе", name = "Program")
public record ProgramDto(
        @Schema(description = "Идентификатор", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор образовательной программы не может быть меньше 1")
                long id,
        @Schema(description = "Код образовательной программы", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Код образовательной программы не может быть null")
                @Size(min = 1, max = 32, message = "Код образовательной программы должно быть от 1 до 32 символов")
                String code,
        @Schema(description = "Наименование образовательной программы", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Название образовательной программы не может быть null")
                @Size(min = 1, max = 128, message = "Название образовательной программы должно быть от 1 до 128 символов")
                String name
) {
}

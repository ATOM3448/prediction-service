package ru.tusur.prediction.service.api.data.dto.program;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность, описывающая данные по образовательной программе", name = "UpdateProgram")
public record UpdateProgramDto(
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

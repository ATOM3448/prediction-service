package ru.tusur.prediction.service.api.data.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность, описывающая данные по кафедре", name = "Department")
public record DepartmentDto(
        @Schema(description = "Идентификатор", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор кафедры не может быть меньше 1")
                long id,
        @Schema(description = "Идентификатор факультета", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор факультета не может быть меньше 1")
                long facultyId,
        @Schema(description = "Наименование кафедры", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Название кафедры не может быть null")
                @Size(min = 1, max = 128, message = "Название кафедры должно быть от 1 до 128 символов")
                String name
) {
}

package ru.tusur.prediction.service.api.data.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность, описывающая данные по кафедре", name = "UpdateDepartment")
public record UpdateDepartmentDto(
        @Schema(description = "Наименование кафедры", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull(message = "Название кафедры не может быть null")
            @Size(min = 1, max = 128, message = "Название кафедры должно быть от 1 до 128 символов")
            String name
) {
}

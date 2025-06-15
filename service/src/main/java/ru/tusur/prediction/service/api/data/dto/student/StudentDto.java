package ru.tusur.prediction.service.api.data.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Сущность, описывающая данные по студенту", name = "Student")
public record StudentDto(
        @Schema(description = "Идентификатор", requiredMode = Schema.RequiredMode.REQUIRED)
        @Min(value = 1, message = "Идентификатор студента не может быть меньше 1")
        long id
) {
}

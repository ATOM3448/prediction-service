package ru.tusur.prediction.service.api.data.dto.teacher;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Сущность, описывающая данные по преподавателю", name = "Teacher")
public record TeacherDto(
        @Schema(description = "Идентификатор", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор преподавателя не может быть меньше 1")
                long id,
        @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор кафедры не может быть меньше 1")
                long departmentId
) {
}

package ru.tusur.prediction.service.api.data.dto.discipline.course;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

@Schema(description = "Сущность, описывающая данные по курсу дисциплины", name = "CreateDisciplineCourseDto")
public record CreateDisciplineCourseDto(
        @Schema(description = "Идентификатор дисциплины", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор дисциплины не может быть меньше 1")
                long disciplineId,
        @Schema(description = "Идентификатор преподавателя", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор преподавателя не может быть меньше 1")
                long teacherId,
        @Schema(description = "Номер семестра", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Номер семестра не может быть меньше 1")
                int semester
) {
}

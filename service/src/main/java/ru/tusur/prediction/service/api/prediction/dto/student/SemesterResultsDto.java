package ru.tusur.prediction.service.api.prediction.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import ru.tusur.prediction.service.core.service.onnx.OnnxConstants;

/**
 * Сущность, описывающая данные успеваемости за семестр.
 *
 * @param disciplines Данные успеваемости по дисциплинам.
 */
@Schema(description = "Сущность, описывающая данные успеваемости за семестр", name = "Semester")
public record SemesterResultsDto(
        @Schema(description = "Данные успеваемости по дисциплинам")
                @NotNull
                @Size(min = 1, max = OnnxConstants.MAX_DISCIPLINES_COUNT_SUPPORTED)
                List<DisciplineResultsDto> disciplines) {}

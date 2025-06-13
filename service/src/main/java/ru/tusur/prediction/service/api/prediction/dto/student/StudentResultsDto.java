package ru.tusur.prediction.service.api.prediction.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import ru.tusur.prediction.service.core.service.onnx.OnnxConstants;

/**
 * Сущность, описывающая данные успеваемости студента.
 *
 * @param semesters Данные успеваемости по семестрам.
 */
public record StudentResultsDto(
        @Schema(description = "Данные успеваемости по семестрам")
                @NotNull
                @Size(min = 1, max = OnnxConstants.MAX_SEMESTERS_COUNT_SUPPORTED)
                List<SemesterResultsDto> semesters) {}

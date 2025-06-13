package ru.tusur.prediction.service.api.data.dto.faculty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Сущность описывающая изменения в данных по факультету.
 *
 * @param oldFaculty Старые данные факультета.
 * @param newFaculty Новые данные факультета.
 */
@Schema(description = "Сущность описывающая изменения в данных по факультету", name = "FacultyUpdate")
public record FacultyUpdateDto(
        @Schema(description = "Старые данные факультета") @NotNull FacultyDto oldFaculty,
        @Schema(description = "Новые данные факультета") @NotNull FacultyDto newFaculty
) {}

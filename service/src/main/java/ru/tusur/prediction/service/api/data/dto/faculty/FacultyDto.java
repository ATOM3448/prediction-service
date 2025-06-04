package ru.tusur.prediction.service.api.data.dto.faculty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Сущность, описывающая данные по факультету.
 *
 * @param name Наименование факультета.
 */
@Schema(description = "Сущность, описывающая данные по факультету", name = "Faculty")
public record FacultyDto(@Schema(description = "Наименование факультета") @NotNull String name) {}

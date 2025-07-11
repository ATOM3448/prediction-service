package ru.tusur.prediction.service.api.data.dto.faculty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность, описывающая данные по факультету", name = "UpdateFaculty")
public record UpdateFacultyDto(
        @Schema(description = "Наименование факультета", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Название факультета не может быть null")
                @Size(min = 1, max = 128, message = "Название факультета должно быть от 1 до 128 символов")
                String name
) {}

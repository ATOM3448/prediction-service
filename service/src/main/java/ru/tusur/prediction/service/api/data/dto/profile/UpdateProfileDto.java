package ru.tusur.prediction.service.api.data.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность, описывающая данные по образовательному профилю", name = "UpdateProfile")
public record UpdateProfileDto(
        @Schema(description = "", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Название образовательного профиля не может быть null")
                @Size(min = 1, max = 128, message = "Название образовательного профиля должно быть от 1 до 128 символов")
                String name
) {
}

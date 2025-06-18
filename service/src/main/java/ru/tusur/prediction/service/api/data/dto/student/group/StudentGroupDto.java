package ru.tusur.prediction.service.api.data.dto.student.group;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Сущность, описывающая данные по студенческой группы", name = "StudentGroup")
public record StudentGroupDto(
        @Schema(description = "Идентификатор", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор студенческой группы не может быть меньше 1")
                long id,
        @Schema(description = "Идентификатор образовательного профиля", requiredMode = Schema.RequiredMode.REQUIRED)
                @Min(value = 1, message = "Идентификатор образовательного профиля не может быть меньше 1")
                long profileId,
        @Schema(description = "Наименование студенческой группы", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Название студенческой группы не может быть null")
                @Size(min = 1, max = 128, message = "Название студенческой группы должно быть от 1 до 128 символов")
                String name,
        @Schema(description = "Обычная для группы дата поступления", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Обычная для группы дата поступления не может быть null")
                LocalDate generalEnrollment,
        @Schema(description = "Обычная для группы дата выпуска", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Обычная для группы дата выпуска не может быть null")
                LocalDate planedExpulsion
) {
}

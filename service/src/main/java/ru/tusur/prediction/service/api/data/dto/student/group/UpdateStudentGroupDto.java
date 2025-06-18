package ru.tusur.prediction.service.api.data.dto.student.group;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Сущность, описывающая данные по студенческой группы", name = "UpdateStudentGroup")
public record UpdateStudentGroupDto(
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

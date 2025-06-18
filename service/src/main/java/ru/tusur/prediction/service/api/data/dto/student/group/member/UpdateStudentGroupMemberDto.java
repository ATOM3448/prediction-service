package ru.tusur.prediction.service.api.data.dto.student.group.member;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Сущность, описывающая данные по члену студенческой группы", name = "UpdateStudentGroupMember")
public record UpdateStudentGroupMemberDto(
        @Schema(description = "Дата вступления", requiredMode = Schema.RequiredMode.REQUIRED)
                @NotNull(message = "Дата вступления не может быть null")
                LocalDate enrollment,
        @Schema(description = "Дата выпуска/исключения", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
                LocalDate expulsion
) {
}

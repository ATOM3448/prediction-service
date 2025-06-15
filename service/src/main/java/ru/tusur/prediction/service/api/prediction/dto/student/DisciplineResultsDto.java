package ru.tusur.prediction.service.api.prediction.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Schema(
        description = "Сущность, описывающая результаты студента по дисциплине",
        name = "Discipline")
public record DisciplineResultsDto(
        @Schema(description = "Результат сессии")
                @DecimalMin("0.0")
                @DecimalMax("1.0")
                float resultSession,
        @Schema(description = "Результат КТ 1")
                @DecimalMin("0.0")
                @DecimalMax("1.0")
                float resultControlPointFirst,
        @Schema(description = "Результат КТ 2")
                @DecimalMin("0.0")
                @DecimalMax("1.0")
                float resultControlPointSecond,
        @Schema(description = "Посещаемость лекций за промежуток 1")
                @DecimalMin("0.0")
                @DecimalMax("1.0")
                float attendanceLectureFirstPeriod,
        @Schema(description = "Посещаемость лекций за промежуток 2")
                @DecimalMin("0.0")
                @DecimalMax("1.0")
                float attendanceLectureSecondPeriod,
        @Schema(description = "Посещаемость лекций за промежуток 3")
                @DecimalMin("0.0")
                @DecimalMax("1.0")
                float attendanceLectureThirdPeriod,
        @Schema(description = "Посещаемость практик за промежуток 1")
                @DecimalMin("0.0")
                @DecimalMax("1.0")
                float attendancePracticeFirstPeriod,
        @Schema(description = "Посещаемость практик за промежуток 2")
                @DecimalMin("0.0")
                @DecimalMax("1.0")
                float attendancePracticeSecondPeriod,
        @Schema(description = "Посещаемость практик за промежуток 3")
                @DecimalMin("0.0")
                @DecimalMax("1.0")
                float attendancePracticeThirdPeriod) {}

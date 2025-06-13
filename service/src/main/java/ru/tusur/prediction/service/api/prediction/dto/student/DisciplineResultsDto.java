package ru.tusur.prediction.service.api.prediction.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Сущность, описывающая результаты студента по дисциплине.
 *
 * @param resultSession Результат сессии.
 * @param resultControlPointFirst Результат КТ 1.
 * @param resultControlPointSecond Результат КТ 2.
 * @param attendanceLectureFirstPeriod Посещаемость лекций за промежуток 1.
 * @param attendanceLectureSecondPeriod Посещаемость лекций за промежуток 2.
 * @param attendanceLectureThirdPeriod Посещаемость лекций за промежуток 3.
 * @param attendancePracticeFirstPeriod Посещаемость практик за промежуток 1.
 * @param attendancePracticeSecondPeriod Посещаемость практик за промежуток 2.
 * @param attendancePracticeThirdPeriod Посещаемость практик за промежуток 3.
 */
@Schema(
        description = "Сущность, описывающая результаты студента по дисциплине",
        name = "Discipline")
public record DisciplineResultsDto(
        @Schema(description = "Результат сессии")
                @NotNull
                @DecimalMin("0.0")
                @DecimalMax("0.0")
                float resultSession,
        @Schema(description = "Результат КТ 1")
                @NotNull
                @DecimalMin("0.0")
                @DecimalMax("0.0")
                float resultControlPointFirst,
        @Schema(description = "Результат КТ 2")
                @NotNull
                @DecimalMin("0.0")
                @DecimalMax("0.0")
                float resultControlPointSecond,
        @Schema(description = "Посещаемость лекций за промежуток 1")
                @NotNull
                @DecimalMin("0.0")
                @DecimalMax("0.0")
                float attendanceLectureFirstPeriod,
        @Schema(description = "Посещаемость лекций за промежуток 2")
                @NotNull
                @DecimalMin("0.0")
                @DecimalMax("0.0")
                float attendanceLectureSecondPeriod,
        @Schema(description = "Посещаемость лекций за промежуток 3")
                @NotNull
                @DecimalMin("0.0")
                @DecimalMax("0.0")
                float attendanceLectureThirdPeriod,
        @Schema(description = "Посещаемость практик за промежуток 1")
                @NotNull
                @DecimalMin("0.0")
                @DecimalMax("0.0")
                float attendancePracticeFirstPeriod,
        @Schema(description = "Посещаемость практик за промежуток 2")
                @NotNull
                @DecimalMin("0.0")
                @DecimalMax("0.0")
                float attendancePracticeSecondPeriod,
        @Schema(description = "Посещаемость практик за промежуток 3")
                @NotNull
                @DecimalMin("0.0")
                @DecimalMax("0.0")
                float attendancePracticeThirdPeriod) {}

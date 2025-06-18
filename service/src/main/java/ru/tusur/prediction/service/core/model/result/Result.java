package ru.tusur.prediction.service.core.model.result;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;

public record Result(
        long id,
        @ColumnName("discipline_course_id") long disciplineCourseId,
        @ColumnName("indicator_id") long indicatorId,
        @ColumnName("student_id") long studentId,
        double value,
        LocalDate date,
        @ColumnName("is_retake") boolean isRetake,
        @ColumnName("is_prediction") boolean isPrediction
) {
}

package ru.tusur.prediction.service.core.model.discipline.course;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record DisciplineCourse(
        long id,
        @ColumnName("profile_id") long profileId,
        @ColumnName("discipline_id") long disciplineId,
        @ColumnName("teacher_id") long teacherId,
        int semester
) {
}

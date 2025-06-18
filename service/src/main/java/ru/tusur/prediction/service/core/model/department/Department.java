package ru.tusur.prediction.service.core.model.department;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record Department(
        long id,
        @ColumnName("faculty_id") long facultyId,
        String name
) {
}

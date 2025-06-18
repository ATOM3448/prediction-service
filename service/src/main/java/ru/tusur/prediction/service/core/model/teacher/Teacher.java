package ru.tusur.prediction.service.core.model.teacher;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record Teacher(
        long id,
        @ColumnName("department_id") long departmentId
) {
}

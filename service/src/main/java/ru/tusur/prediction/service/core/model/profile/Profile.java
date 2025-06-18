package ru.tusur.prediction.service.core.model.profile;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record Profile(
        long id,
        @ColumnName("department_id") long departmentId,
        @ColumnName("program_id") long programId,
        String name
) {
}

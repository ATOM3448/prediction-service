package ru.tusur.prediction.service.core.model.student;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record Student(
        long id,
        @ColumnName("organization_id") long organizationId
) {
}

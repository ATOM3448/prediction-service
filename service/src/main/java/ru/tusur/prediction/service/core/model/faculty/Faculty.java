package ru.tusur.prediction.service.core.model.faculty;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record Faculty(
        long id,
        @ColumnName("organization_id") long organizationId,
        String name
) {}

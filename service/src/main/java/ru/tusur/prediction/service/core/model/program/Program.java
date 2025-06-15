package ru.tusur.prediction.service.core.model.program;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record Program(
        long id,
        @ColumnName("organization_id") long organizationId,
        String code,
        String name
) {}

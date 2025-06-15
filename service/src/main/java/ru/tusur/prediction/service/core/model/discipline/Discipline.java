package ru.tusur.prediction.service.core.model.discipline;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record Discipline(
        long id,
        @ColumnName("organization_id") long organizationId,
        String name
) {
}

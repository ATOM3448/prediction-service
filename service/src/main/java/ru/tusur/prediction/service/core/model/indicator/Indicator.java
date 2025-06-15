package ru.tusur.prediction.service.core.model.indicator;

import org.jdbi.v3.core.enums.EnumByName;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record Indicator(
        long id,
        @ColumnName("organization_id") long organizationId,
        @EnumByName IndicatorType type,
        String name,
        int maxValue
) {}

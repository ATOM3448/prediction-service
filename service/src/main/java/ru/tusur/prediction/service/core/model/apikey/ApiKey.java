package ru.tusur.prediction.service.core.model.apikey;

import java.time.LocalDate;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record ApiKey(
        long id,
        String hash,
        @ColumnName("organization_id") long organizationId,
        LocalDate expired
) {
}

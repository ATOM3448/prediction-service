package ru.tusur.prediction.service.core.model.apikey;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

/**
 * Класс описывающий данные по API ключу.
 */
@Data
@Builder
@Jacksonized
@AllArgsConstructor
public class ApiKey {

    /**
     * Идентификатор ключа.
     */
    private long id;

    /**
     * Хэш ключа.
     */
    private String hash;

    /**
     * Организация - владелец ключа.
     */
    @ColumnName("organization_id")
    private long organizationId;

    /**
     * Дата протухания ключа.
     */
    private LocalDate expired;
}

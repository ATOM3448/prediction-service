package ru.tusur.prediction.service.core.model.faculty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

/**
 * Класс описывающий данные по факультету.
 */
@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class Faculty {

    /**
     * Идентификатор записи.
     */
    private long id;

    /**
     * Организация факультета.
     */
    @ColumnName("organization_id")
    private long organizationId;

    /**
     * Наименование факультета.
     */
    private String name;
}

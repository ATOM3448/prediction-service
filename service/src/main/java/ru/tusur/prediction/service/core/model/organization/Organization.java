package ru.tusur.prediction.service.core.model.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * Класс описывающий данные по организации.
 */
@Data
@Builder
@AllArgsConstructor
@Jacksonized
public class Organization {

    /**
     * Идентификатор записи.
     */
    private long id;

    /**
     * Наименование организации.
     */
    private String name;

}

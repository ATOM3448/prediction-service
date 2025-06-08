package ru.tusur.prediction.service.core.repository;

import java.util.Optional;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;

/**
 * Интерфейс для работы с Api Key.
 */
@Transactional(readOnly = true)
public interface ApiKeyRepository {

    /**
     * Возвращает идентификатор организации, под которой обращается клиент.
     *
     * @param value Значение api key.
     * @return Идентификатор организации клиента.
     */
    @SqlQuery(
            """
            select ak.organization_id
            from api_key ak
            where ak.value = :organizationId;
            """)
    Optional<Long> getOrganizationByApiKey(@Bind("organizationId") String value);
}

package ru.tusur.prediction.service.core.repository;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.apikey.ApiKey;

/**
 * Интерфейс для работы с Api Key.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(ApiKey.class)
public interface ApiKeyRepository {

    /**
     * Возвращает возможные API ключи по префиксу.
     *
     * @param prefix Префикс API ключа.
     * @return API ключи.
     */
    @SqlQuery(
            """
            select *
            from api_key
            where prefix = :prefix;
            """)
    List<ApiKey> getApiKeysByPrefix(@Bind("prefix") String prefix);

    /**
     * Возвращает разрешения доступные под переданным ключом.
     *
     * @param apiKey Идентификатор API ключа.
     * @return Список разрешений.
     */
    @SqlQuery(
            """
            select type
            from scope
            where api_key_id = :apiKeyId;
            """)
    List<String> getScopesByApiKey(@Bind("apiKeyId") long apiKey);
}

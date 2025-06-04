package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.result.NextSessionAverangeResult;

/**
 * Интерфейс для управления объектами {@link NextSessionAverangeResult}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(NextSessionAverangeResult.class)
public interface NextSessionAverangeResultRepository {

}

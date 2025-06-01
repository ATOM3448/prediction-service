package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.indicator.Indicator;

/**
 * Интерфейс для управления объектами {@link Indicator}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Indicator.class)
public interface IndicatorRepository {

}

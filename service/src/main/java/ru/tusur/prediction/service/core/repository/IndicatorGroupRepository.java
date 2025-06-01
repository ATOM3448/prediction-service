package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.indicator.IndicatorGroup;

/**
 * Интерфейс для управления объектами {@link IndicatorGroup}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(IndicatorGroup.class)
public interface IndicatorGroupRepository {

}

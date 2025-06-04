package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.indicator.IndicatorType;

/**
 * Интерфейс для управления объектами {@link IndicatorType}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(IndicatorType.class)
public interface IndicatorTypeRepository {}

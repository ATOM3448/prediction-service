package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.result.Result;

/**
 * Интерфейс для управления объектами {@link Result}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Result.class)
public interface ResultRepository {}

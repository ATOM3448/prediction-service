package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.discipline.Discipline;

/**
 * Интерфейс для управления объектами {@link Discipline}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Discipline.class)
public interface DisciplineRepository {}

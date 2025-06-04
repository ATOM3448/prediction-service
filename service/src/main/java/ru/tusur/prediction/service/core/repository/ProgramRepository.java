package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.program.Program;

/**
 * Интерфейс для управления объектами {@link Program}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Program.class)
public interface ProgramRepository {}

package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.faculty.Faculty;

/**
 * Интерфейс для управления объектами {@link Faculty}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Faculty.class)
public interface FacultyRepository {
}

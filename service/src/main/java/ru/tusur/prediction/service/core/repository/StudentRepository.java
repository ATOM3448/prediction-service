package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.student.Student;

/**
 * Интерфейс для управления объектами {@link Student}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Student.class)
public interface StudentRepository {

}

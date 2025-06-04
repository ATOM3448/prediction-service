package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.student.StudentGroup;

/**
 * Интерфейс для управления объектами {@link StudentGroup}.
 */

@Transactional(readOnly = true)
@RegisterConstructorMapper(StudentGroup.class)
public interface StudentGroupRepository {

}

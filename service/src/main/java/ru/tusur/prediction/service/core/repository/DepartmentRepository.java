package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.department.Department;

/**
 * Интерфейс для управления объектами {@link Department}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Department.class)
public interface DepartmentRepository {

}

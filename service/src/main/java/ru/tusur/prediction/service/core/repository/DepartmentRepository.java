package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.department.Department;

import java.util.List;

@Transactional
@RegisterConstructorMapper(Department.class)
public interface DepartmentRepository {

    @SqlQuery(
            """
            select *
            from department;
            """
    )
    List<Department> temp();
}

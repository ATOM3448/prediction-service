package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
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
            from department
            where faculty_id = :facultyId
            order by id;
            """
    )
    List<Department> getDepartmentsByFacultyId(@Bind("facultyId") long facultyId);

    @SqlQuery(
            """
            select *
            from department
            where id = :id;
            """
    )
    Department getDepartmentById(@Bind("id") long id);

    @SqlQuery(
            """
            insert into department (faculty_id, name)
            values (:facultyId, :name)
            returning *;
            """
    )
    Department saveDepartment(
            @Bind("facultyId") long facultyId,
            @Bind("name") String name
    );

    @SqlQuery(
            """
            update department
            set name = :name
            where id = :id
            returning *;
            """
    )
    Department updateDepartment(
            @Bind("id") long id,
            @Bind("name") String name
    );

}

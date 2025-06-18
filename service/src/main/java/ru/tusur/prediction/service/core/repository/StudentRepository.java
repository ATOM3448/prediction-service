package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.student.Student;

import java.util.List;

@Transactional
@RegisterConstructorMapper(Student.class)
public interface StudentRepository {

    @SqlQuery(
            """
            select *
            from student
            where organization_id = :organizationId
            order by id;
            """
    )
    List<Student> getStudentsByOrganizationId(@Bind("organizationId") long organizationId);

    @SqlQuery(
            """
            select *
            from student
            where id = :id;
            """
    )
    Student getStudentById(@Bind("id") long id);

    @SqlQuery(
            """
            insert into student (organization_id)
            values (:organizationId)
            returning *;
            """
    )
    Student saveStudent(@Bind("organizationId") long organizationId);

}

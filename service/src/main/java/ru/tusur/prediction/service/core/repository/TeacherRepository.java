package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.teacher.Teacher;

import java.util.List;

@Transactional
@RegisterConstructorMapper(Teacher.class)
public interface TeacherRepository {

    @SqlQuery(
            """
            select *
            from teacher
            where department_id = :departmentId
            order by id;
            """
    )
    List<Teacher> getTeachersByDepartmentId(@Bind("departmentId") long departmentId);

    @SqlQuery(
            """
            select *
            from teacher
            where id = :id;
            """
    )
    Teacher getTeacherById(@Bind("id") long id);

    @SqlQuery(
            """
            insert into teacher (department_id)
            values (:departmentId)
            returning *;
            """
    )
    Teacher saveTeacher(@Bind("departmentId") long departmentId);
}

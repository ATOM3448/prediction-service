package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
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
            from teacher;
            """
    )
    List<Teacher> temp();

}

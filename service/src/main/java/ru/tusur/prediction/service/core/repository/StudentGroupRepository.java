package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.student.group.StudentGroup;

import java.util.List;

@Transactional
@RegisterConstructorMapper(StudentGroup.class)
public interface StudentGroupRepository {

    @SqlQuery(
            """
            select *
            from student_group;
            """
    )
    List<StudentGroup> temp();

}

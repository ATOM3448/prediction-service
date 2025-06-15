package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.discipline.course.DisciplineCourse;

import java.util.List;

@Transactional
@RegisterConstructorMapper(DisciplineCourse.class)
public interface DisciplineCourseRepository {

    @SqlQuery(
            """
            select *
            from discipline_course;
            """
    )
    List<DisciplineCourse> temp();
}

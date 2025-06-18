package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
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
            from discipline_course
            where profile_id = :profileId
            order by id;
            """
    )
    List<DisciplineCourse> getDisciplineCoursesByProfileId(@Bind("profileId") long profileId);

    @SqlQuery(
            """
            select *
            from discipline_course
            where id = :id;
            """
    )
    DisciplineCourse getDisciplineCourseById(@Bind("id") long id);

    @SqlQuery(
            """
            insert into discipline_course (profile_id, discipline_id, teacher_id, semester)
            values (:profileId, :disciplineId, :teacherId, :semester)
            returning *;
            """
    )
    DisciplineCourse saveDisciplineCourse(
            @Bind("profileId") long profileId,
            @Bind("disciplineId") long disciplineId,
            @Bind("teacherId") long teacherId,
            @Bind("semester") long semester
    );

}

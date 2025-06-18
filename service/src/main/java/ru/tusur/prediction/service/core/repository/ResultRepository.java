package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.result.Result;

import java.time.LocalDate;
import java.util.List;

@Transactional
@RegisterConstructorMapper(Result.class)
public interface ResultRepository {

    @SqlQuery(
            """
            select *
            from result
            where discipline_course_id = :disciplineCourseId
            <filter>
            order by id;
            """
    )
    List<Result> getResultsByDisciplineCourseIdAndFilter(
            @Bind("disciplineCourseId") long disciplineCourseId,
            @Define("filter") String filter
    );

    @SqlQuery(
            """
            select *
            from result
            where 1 = 1
            <filter>
            order by id;
            """
    )
    List<Result> getResultsByFilter(
            @Define("filter") String filter
    );

    @SqlQuery(
            """
            select *
            from result
            where id = :id;
            """
    )
    Result getResultById(@Bind("id") long id);

    @SqlQuery(
            """
            insert into result (discipline_course_id, indicator_id, student_id, value, date, is_retake, is_prediction)
            values (:disciplineCourseId, :indicatorId, :studentId, :value, :date, :isRetake, false)
            returning *;
            """
    )
    Result saveResult(
            @Bind("disciplineCourseId") long disciplineCourseId,
            @Bind("indicatorId") long indicatorId,
            @Bind("studentId") long studentId,
            @Bind("value") double value,
            @Bind("date") LocalDate date,
            @Bind("isRetake") boolean isRetake
    );

    @SqlQuery(
            """
            update result
            set value = :value,
                date = :date,
                is_retake = :isRetake
            where id = :id
            returning *;
            """
    )
    Result updateResult(
            @Bind("id") long id,
            @Bind("value") double newValue,
            @Bind("date") LocalDate newDate,
            @Bind("isRetake") boolean isRetake
    );
}

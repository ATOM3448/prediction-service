package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.discipline.Discipline;
import ru.tusur.prediction.service.core.model.discipline.Discipline;

import java.util.List;

@Transactional
@RegisterConstructorMapper(Discipline.class)
public interface DisciplineRepository {

    @SqlQuery(
            """
            select *
            from discipline
            where organization_id = :organizationId
            order by id;
            """)
    List<Discipline> getDisciplinesByOrganizationId(@Bind("organizationId") long organizationId);

    @SqlQuery(
            """
            select *
            from discipline
            where id = :id;
            """
    )
    Discipline getDisciplineById(
            @Bind("id") long id
    );

    @SqlQuery(
            """
            insert into discipline (organization_id, name)
            values (:organizationId, :name)
            returning *;
            """
    )
    Discipline saveDiscipline(
            @Bind("organizationId") long organizationId,
            @Bind("name") String name
    );

    @SqlQuery(
            """
            update discipline
            set name = :name
            where id = :id
            returning *;
            """
    )
    Discipline updateDiscipline(
            @Bind("id") long id,
            @Bind("name") String name
    );
}

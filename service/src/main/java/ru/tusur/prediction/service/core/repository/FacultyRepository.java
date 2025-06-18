package ru.tusur.prediction.service.core.repository;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.faculty.Faculty;

@Transactional
@RegisterConstructorMapper(Faculty.class)
public interface FacultyRepository {

    @SqlQuery(
            """
            select *
            from faculty
            where organization_id = :organizationId
            order by id;
            """)
    List<Faculty> getFacultiesByOrganizationId(@Bind("organizationId") long organizationId);

    @SqlQuery(
            """
            select *
            from faculty
            where id = :id;
            """
    )
    Faculty getFacultyById(
            @Bind("id") long id
    );

    @SqlQuery(
            """
            insert into faculty (organization_id, name)
            values (:organizationId, :name)
            returning *;
            """
    )
    Faculty saveFaculty(
            @Bind("organizationId") long organizationId,
            @Bind("name") String name
    );

    @SqlQuery(
            """
            update faculty
            set name = :name
            where id = :id
            returning *;
            """
    )
    Faculty updateFaculty(
            @Bind("id") long id,
            @Bind("name") String name
    );
}

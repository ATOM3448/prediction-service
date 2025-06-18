package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.program.Program;

import java.util.List;

@Transactional
@RegisterConstructorMapper(Program.class)
public interface ProgramRepository {

    @SqlQuery(
            """
            select *
            from program
            where organization_id = :organizationId
            order by id;
            """
    )
    List<Program> getProgramsByOrganizationId(@Bind("organizationId") long organizationId);

    @SqlQuery(
            """
            select *
            from program
            where id = :id;
            """
    )
    Program getProgramById(@Bind("id") long id);

    @SqlQuery(
            """
            insert into program (organization_id, code, name)
            values (:organizationId, :code, :name)
            returning *;
            """
    )
    Program saveProgram(
            @Bind("organizationId") long organizationId,
            @Bind("code") String code,
            @Bind("name") String name
    );

    @SqlQuery(
            """
            update program
            set code = :code,
            name = :name
            where id = :id
            returning *;
            """
    )
    Program updateProgram(
            @Bind("id") long programId,
            @Bind("code") String code,
            @Bind("name") String name
    );
}

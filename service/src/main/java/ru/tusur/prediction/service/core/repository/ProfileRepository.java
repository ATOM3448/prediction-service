package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.profile.Profile;

import java.util.List;

@Transactional
@RegisterConstructorMapper(Profile.class)
public interface ProfileRepository {

    @SqlQuery(
            """
            select *
            from profile
            where program_id = :programId
            order by id;
            """
    )
    List<Profile> getProfilesByProgramId(@Bind("programId") long programId);

    @SqlQuery(
            """
            select *
            from profile
            where id = :id;
            """
    )
    Profile getProfileById(@Bind("id") long id);

    @SqlQuery(
            """
            insert into profile (program_id, department_id, name)
            values (:programId, :departmentId, :name)
            returning *;
            """
    )
    Profile saveProfile(
            @Bind("programId") long programId,
            @Bind("departmentId") long departmentId,
            @Bind("name") String name
    );

    @SqlQuery(
            """
            update profile
            set name = :name
            where id = :id
            returning *;
            """
    )
    Profile updateProfile(
            @Bind("id") long id,
            @Bind("name") String name
    );
}

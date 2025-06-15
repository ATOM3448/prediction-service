package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
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
            from profile;
            """
    )
    List<Profile> temp();

}

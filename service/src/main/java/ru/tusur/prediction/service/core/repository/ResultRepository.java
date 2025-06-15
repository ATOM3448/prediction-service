package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.result.Result;

import java.util.List;

@Transactional
@RegisterConstructorMapper(Result.class)
public interface ResultRepository {

    @SqlQuery(
            """
            select *
            from result;
            """
    )
    List<Result> temp();

}

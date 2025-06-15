package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.api.data.dto.indicator.IndicatorTypeDto;
import ru.tusur.prediction.service.core.model.indicator.Indicator;

import java.util.List;

@Transactional
@RegisterConstructorMapper(Indicator.class)
public interface IndicatorRepository {

    @SqlQuery(
            """
            select *
            from indicator
            where organization_id = :organizationId
            order by id;
            """
    )
    List<Indicator> getIndicatorByOrganizationId(@Bind("organizationId") long organizationId);

    @SqlQuery(
            """
            select *
            from indicator
            where id = :id;
            """
    )
    Indicator getIndicatorById(@Bind("id") long id);

    @SqlQuery(
            """
            insert into indicator (organization_id, type, name, max_value)
            values (:organizationId, :type, :name, :maxValue)
            returning *;
            """
    )
    Indicator saveIndicator(
            @Bind("organizationId") long organizationId,
            @Bind("type") IndicatorTypeDto type,
            @Bind("name") String name,
            @Bind("maxValue") int maxValue
    );

    @SqlUpdate(
            """
            update indicator
            set id = :id,
                type = :type,
                name = :newName,
                max_value = :maxValue
            where id = :id;
            """
    )
    void updateIndicator(
            @Bind("id") long id,
            @Bind("type") IndicatorTypeDto type,
            @Bind("newName") String newName,
            @Bind("maxValue") int maxValue
    );

}

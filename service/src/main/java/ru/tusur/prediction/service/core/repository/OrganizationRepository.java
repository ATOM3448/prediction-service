package ru.tusur.prediction.service.core.repository;

import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.organization.Organization;

import java.util.List;

/**
 * Интерфейс для управления объектами {@link Organization}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Organization.class)
public interface OrganizationRepository {

    @SqlQuery("""
            select
                *
            from organization o;
            """)
    List<Organization> getOrganizations();

}

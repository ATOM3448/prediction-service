package ru.tusur.prediction.service.core.repository;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.faculty.Faculty;

/**
 * Интерфейс для управления объектами {@link Faculty}.
 */
@Transactional(readOnly = true)
@RegisterConstructorMapper(Faculty.class)
public interface FacultyRepository {

  @SqlQuery(
      """
            select
                *
            from faculty f
            where organization_id = :organizationId;
            """)
  List<Faculty> getFacultiesByOrganizationId(@Bind("organizationId") int organizationId);
}

package ru.tusur.prediction.service.core.repository;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.prediction.service.core.model.faculty.Faculty;

/**
 * Интерфейс для управления объектами {@link Faculty}.
 */
@Transactional
@RegisterConstructorMapper(Faculty.class)
public interface FacultyRepository {

    /**
     * Возвращает список факультетов организации.
     *
     * @param organizationId Идентификатор организации.
     * @return Список факультетов.
     */
    @SqlQuery(
            """
            select
                id,
                organization_id,
                name
            from faculty
            where organization_id = :organizationId;
            """)
    List<Faculty> getFacultiesByOrganizationId(@Bind("organizationId") long organizationId);

    /**
     * Сохраняет данные по факультету.
     *
     * @param organizationId Идентификатор организации.
     * @param name Наименование факультета.
     */
    @SqlUpdate(
            """
            insert into faculty (organization_id, name)
            values (:organizationId, :name);
            """)
    void saveFaculty(
            @Bind("organizationId") long organizationId,
            @Bind("name") String name
    );

    /**
     * Обновляет данные по факультету.
     *
     * @param organizationId Идентификатор организации.
     * @param oldName Старое наименование факультета.
     * @param newName Новое наименование факультета.
     */
    @SqlUpdate(
            """
            update faculty
            set name = :newName
            where name = :oldName;
            """
    )
    int updateFaculty(
            @Bind("organizationId") long organizationId,
            @Bind("oldName") String oldName,
            @Bind("newName") String newName
    );
}

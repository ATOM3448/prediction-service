package ru.tusur.prediction.service.core.service.faculty;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ErrorMessages;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.faculty.Faculty;
import ru.tusur.prediction.service.core.repository.FacultyRepository;

import static ru.tusur.prediction.service.util.SecurityUtils.getOrganizationIdFromSecurityContext;

/**
 * Сервис для работы с факультетами.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;

    /**
     * Возвращает данные по факультетам организации клиента.
     *
     * @return {@link List<Faculty>} список факультетов организации клиента хранимых в базе.
     */
    public List<Faculty> getFaculties() {
        long organizationId = getOrganizationIdFromSecurityContext();

        return facultyRepository.getFacultiesByOrganizationId(organizationId);
    }

    /**
     * Сохранение в базу данных по факультету организации клиента.
     *
     * @param name Наименование факультета.
     */
    public void saveFaculty(String name) {
        long organizationId = getOrganizationIdFromSecurityContext();

        try {
            facultyRepository.saveFaculty(organizationId, name);
            log.info(
                    "Данные по факультету \"{}\" для организации #{} сохранены",
                    name,
                    organizationId
            );
        } catch (UnableToExecuteStatementException ex) {
            throw new ServiceException(ErrorCode.DUPLICATE, ErrorMessages.DUPLICATE_MESSAGE);
        }
    }

    /**
     * Обновление в базе данных по факультету организации клиента.
     *
     * @param oldName Старое наименование факультета.
     * @param newName Новое наименование факультета.
     */
    public void updateFaculty(String oldName, String newName) {
        long organizationId = getOrganizationIdFromSecurityContext();

        if (facultyRepository.updateFaculty(organizationId, oldName, newName) == 0) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, ErrorMessages.OBJECT_NOT_FOUND_MESSAGE);
        }

        log.info(
                "Данные по факультету \"{}\" для организации #{} обновлены на \"{}\"",
                oldName,
                organizationId,
                newName
        );
    }

}

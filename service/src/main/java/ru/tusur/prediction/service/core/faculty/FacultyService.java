package ru.tusur.prediction.service.core.faculty;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ErrorMessages;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.faculty.Faculty;
import ru.tusur.prediction.service.core.repository.FacultyRepository;

/**
 * Сервис для работы с факультетами.
 */
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
        } catch (UnableToExecuteStatementException ex) {
            throw new ServiceException(ErrorCode.DUPLICATE, ErrorMessages.DUPLICATE_MESSAGE);
        }
    }

    private static long getOrganizationIdFromSecurityContext() {
        return (long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

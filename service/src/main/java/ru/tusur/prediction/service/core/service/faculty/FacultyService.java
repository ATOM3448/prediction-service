package ru.tusur.prediction.service.core.service.faculty;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.data.dto.faculty.UpdateFacultyDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.faculty.Faculty;
import ru.tusur.prediction.service.core.repository.FacultyRepository;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;
import static ru.tusur.prediction.service.util.SecurityUtils.getOrganizationIdFromSecurityContext;
import static ru.tusur.prediction.service.util.SecurityUtils.validateAccessByOrganizationId;

/**
 * Сервис для работы с факультетами.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public List<Faculty> getFaculties() {
        long organizationId = getOrganizationIdFromSecurityContext();

        return facultyRepository.getFacultiesByOrganizationId(organizationId);
    }

    public Faculty getFaculty(long id) {
        Faculty faculty = facultyRepository.getFacultyById(id);

        if ((faculty == null) || !validateAccessByOrganizationId(faculty.organizationId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }

        return faculty;
    }

    public Faculty saveFaculty(UpdateFacultyDto faculty) {
        long organizationId = getOrganizationIdFromSecurityContext();

        String facultyName = faculty.name();

        Faculty savedFaculty = facultyRepository.saveFaculty(organizationId, facultyName);

        log.info(
                "Данные по факультету #{} сохранены",
                savedFaculty.id()
        );

        return savedFaculty;
    }

    public void updateFaculty(long facultyId, UpdateFacultyDto newFaculty) {
        Faculty oldFaculty = facultyRepository.getFacultyById(facultyId);

        if ((oldFaculty == null) || !validateAccessByOrganizationId(oldFaculty.organizationId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }

        String newName = newFaculty.name();

        if (newName.equals(oldFaculty.name())) {
            return;
        }

        facultyRepository.updateFaculty(facultyId, newName);

        log.info(
                "Данные по факультету #{} обновлены на \"{}\"",
                facultyId,
                newName
        );
    }

}

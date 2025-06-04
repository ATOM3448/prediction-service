package ru.tusur.prediction.service.core.faculty;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.core.model.faculty.Faculty;
import ru.tusur.prediction.service.core.repository.FacultyRepository;

/**
 * Сервис для работы с факультетами.
 */
@Service
@AllArgsConstructor
public class FacultyService {

  private final FacultyRepository facultyRepository;

  public List<Faculty> getFacultiesByOrganizationId(int organizationId) {
    return facultyRepository.getFacultiesByOrganizationId(organizationId);
  }
}

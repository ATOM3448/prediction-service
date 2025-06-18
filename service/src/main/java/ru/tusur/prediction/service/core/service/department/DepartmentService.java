package ru.tusur.prediction.service.core.service.department;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.api.data.dto.department.UpdateDepartmentDto;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.department.Department;
import ru.tusur.prediction.service.core.repository.DepartmentRepository;
import ru.tusur.prediction.service.core.service.faculty.FacultyService;

import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final FacultyService facultyService;

    private final DepartmentRepository departmentRepository;

    public List<Department> getDepartments(long facultyId) {
        facultyService.getFaculty(facultyId);

        return departmentRepository.getDepartmentsByFacultyId(facultyId);
    }

    public Department getDepartment(long facultyId, long id) {
        facultyService.getFaculty(facultyId);

        Department department = departmentRepository.getDepartmentById(id);

        checkExistenceOfDepartment(department, facultyId);

        return department;
    }

    public Department saveDepartment(long facultyId, UpdateDepartmentDto department) {
        facultyService.getFaculty(facultyId);

        String departmentName = department.name();

        Department savedDepartment = departmentRepository.saveDepartment(facultyId, departmentName);

        log.info(
                "Данные по кафедре #{} сохранены",
                savedDepartment.id()
        );

        return savedDepartment;
    }

    public Department updateDepartment(long facultyId, long departmentId, UpdateDepartmentDto newDepartment) {
        facultyService.getFaculty(facultyId);

        Department oldDepartment = departmentRepository.getDepartmentById(departmentId);

        checkExistenceOfDepartment(oldDepartment, facultyId);

        String newName = newDepartment.name();

        Department updatedDepartment = departmentRepository.updateDepartment(departmentId, newName);

        log.info(
                "Данные по кафедре #{} обновлены на \"{}\"",
                departmentId,
                newName
        );

        return updatedDepartment;
    }

    public void checkExistenceOfDepartment(long departmentId) {
        Department department = departmentRepository.getDepartmentById(departmentId);

        if (department == null) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }

        facultyService.getFaculty(department.facultyId());
    }

    private void checkExistenceOfDepartment(Department department, long facultyId) {
        if ((department == null) || (facultyId != department.facultyId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }
    }

}

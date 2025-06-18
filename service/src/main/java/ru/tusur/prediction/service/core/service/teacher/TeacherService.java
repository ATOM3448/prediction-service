package ru.tusur.prediction.service.core.service.teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.teacher.Teacher;
import ru.tusur.prediction.service.core.repository.TeacherRepository;
import ru.tusur.prediction.service.core.service.department.DepartmentService;

import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherService {
    
    private final DepartmentService departmentService;

    private final TeacherRepository teacherRepository;

    public List<Teacher> getTeachers(long facultyId, long departmentId) {
        departmentService.getDepartment(facultyId, departmentId);

        return teacherRepository.getTeachersByDepartmentId(departmentId);
    }

    public void getTeacher(long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);

        checkExistenceOfTeacher(teacher);
    }

    public Teacher saveTeacher(long facultyId, long departmentId) {
        departmentService.getDepartment(facultyId, departmentId);
        
        return teacherRepository.saveTeacher(departmentId);
    }

    private void checkExistenceOfTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }

        departmentService.checkExistenceOfDepartment(teacher.departmentId());
    }
}

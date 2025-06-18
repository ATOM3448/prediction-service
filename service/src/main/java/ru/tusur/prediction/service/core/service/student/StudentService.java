package ru.tusur.prediction.service.core.service.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.core.error.ErrorCode;
import ru.tusur.prediction.service.core.error.ServiceException;
import ru.tusur.prediction.service.core.model.student.Student;
import ru.tusur.prediction.service.core.repository.StudentRepository;

import java.util.List;

import static ru.tusur.prediction.service.core.error.ErrorMessages.OBJECT_NOT_FOUND_MESSAGE;
import static ru.tusur.prediction.service.util.SecurityUtils.getOrganizationIdFromSecurityContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getStudents() {
        long organizationId = getOrganizationIdFromSecurityContext();

        return studentRepository.getStudentsByOrganizationId(organizationId);
    }

    public void getStudent(long id) {
        Student student = studentRepository.getStudentById(id);

        checkExistenceOfStudent(student);
    }

    public Student saveStudent() {
        long organizationId = getOrganizationIdFromSecurityContext();

        return studentRepository.saveStudent(organizationId);
    }

    private void checkExistenceOfStudent(Student student) {
        long organizationId = getOrganizationIdFromSecurityContext();

        if ((student == null) || (organizationId != student.organizationId())) {
            throw new ServiceException(ErrorCode.OBJECT_NOT_FOUND, OBJECT_NOT_FOUND_MESSAGE);
        }
    }
}

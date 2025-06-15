package ru.tusur.prediction.service.core.service.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tusur.prediction.service.core.model.student.Student;
import ru.tusur.prediction.service.core.repository.StudentRepository;

import java.util.List;

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

    public Student saveStudent() {
        long organizationId = getOrganizationIdFromSecurityContext();

        return studentRepository.saveStudent(organizationId);
    }
}

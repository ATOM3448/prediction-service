package ru.tusur.prediction.service.configuration.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tusur.prediction.service.core.repository.ApiKeyRepository;
import ru.tusur.prediction.service.core.repository.DepartmentRepository;
import ru.tusur.prediction.service.core.repository.DisciplineCourseRepository;
import ru.tusur.prediction.service.core.repository.DisciplineRepository;
import ru.tusur.prediction.service.core.repository.FacultyRepository;
import ru.tusur.prediction.service.core.repository.IndicatorRepository;
import ru.tusur.prediction.service.core.repository.ProfileRepository;
import ru.tusur.prediction.service.core.repository.ProgramRepository;
import ru.tusur.prediction.service.core.repository.ResultRepository;
import ru.tusur.prediction.service.core.repository.StudentGroupRepository;
import ru.tusur.prediction.service.core.repository.StudentRepository;
import ru.tusur.prediction.service.core.repository.TeacherRepository;

@Configuration
public class RepositoryJdbiConfiguration {

    @Bean
    ApiKeyRepository apiKeyRepository(Jdbi jdbi) {
        return jdbi.onDemand(ApiKeyRepository.class);
    }

    @Bean
    StudentRepository studentRepository(Jdbi jdbi) {
        return jdbi.onDemand(StudentRepository.class);
    }

    @Bean
    IndicatorRepository indicatorRepository(Jdbi jdbi) {
        return jdbi.onDemand(IndicatorRepository.class);
    }

    @Bean
    FacultyRepository facultyRepository(Jdbi jdbi) {
        return jdbi.onDemand(FacultyRepository.class);
    }

    @Bean
    DepartmentRepository departmentRepository(Jdbi jdbi) {
        return jdbi.onDemand(DepartmentRepository.class);
    }

    @Bean
    TeacherRepository teacherRepository(Jdbi jdbi) {
        return jdbi.onDemand(TeacherRepository.class);
    }

    @Bean
    ProgramRepository programRepository(Jdbi jdbi) {
        return jdbi.onDemand(ProgramRepository.class);
    }

    @Bean
    ProfileRepository profileRepository(Jdbi jdbi) {
        return jdbi.onDemand(ProfileRepository.class);
    }

    @Bean
    StudentGroupRepository studentGroupRepository(Jdbi jdbi) {
        return jdbi.onDemand(StudentGroupRepository.class);
    }

    @Bean
    DisciplineRepository disciplineRepository(Jdbi jdbi) {
        return jdbi.onDemand(DisciplineRepository.class);
    }

    @Bean
    DisciplineCourseRepository disciplineCourseRepository(Jdbi jdbi) {
        return jdbi.onDemand(DisciplineCourseRepository.class);
    }

    @Bean
    ResultRepository resultRepository(Jdbi jdbi) {
        return jdbi.onDemand(ResultRepository.class);
    }

}

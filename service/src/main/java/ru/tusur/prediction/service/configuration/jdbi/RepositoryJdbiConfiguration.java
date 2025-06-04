package ru.tusur.prediction.service.configuration.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tusur.prediction.service.core.repository.*;

/**
 * Конфигурация репозиториев JDBI.
 */
@Configuration
public class RepositoryJdbiConfiguration {

    @Bean
    OrganizationRepository organizationRepository(Jdbi jdbi) {
        return jdbi.onDemand(OrganizationRepository.class);
    }

    @Bean
    FacultyRepository facultyRepository(Jdbi jdbi) {
        return jdbi.onDemand(FacultyRepository.class);
    }

    /*@Bean
    DepartmentRepository departmentRepository(Jdbi jdbi) {
        return jdbi.onDemand(DepartmentRepository.class);
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
    TeacherRepository teacherRepository(Jdbi jdbi) {
        return jdbi.onDemand(TeacherRepository.class);
    }

    @Bean
    DisciplineRepository disciplineRepository(Jdbi jdbi) {
        return jdbi.onDemand(DisciplineRepository.class);
    }

    @Bean
    IndicatorTypeRepository indicatorTypeRepository(Jdbi jdbi) {
        return jdbi.onDemand(IndicatorTypeRepository.class);
    }

    @Bean
    IndicatorRepository indicatorRepository(Jdbi jdbi) {
        return jdbi.onDemand(IndicatorRepository.class);
    }

    @Bean
    StudentGroupRepository studentGroupRepository(Jdbi jdbi) {
        return jdbi.onDemand(StudentGroupRepository.class);
    }

    @Bean
    StudentRepository studentRepository(Jdbi jdbi) {
        return jdbi.onDemand(StudentRepository.class);
    }

    @Bean
    ResultRepository resultRepository(Jdbi jdbi) {
        return jdbi.onDemand(ResultRepository.class);
    }

    @Bean
    NextSessionAverangeResultRepository nextSessionAverangeResultRepository(Jdbi jdbi) {
        return jdbi.onDemand(NextSessionAverangeResultRepository.class);
    }*/

}

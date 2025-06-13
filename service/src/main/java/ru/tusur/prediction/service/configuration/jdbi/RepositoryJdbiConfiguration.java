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
    ApiKeyRepository apiKeyRepository(Jdbi jdbi) {
        return jdbi.onDemand(ApiKeyRepository.class);
    }

    @Bean
    FacultyRepository facultyRepository(Jdbi jdbi) {
        return jdbi.onDemand(FacultyRepository.class);
    }
}

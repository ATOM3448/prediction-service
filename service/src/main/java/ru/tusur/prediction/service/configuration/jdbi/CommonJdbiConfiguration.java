package ru.tusur.prediction.service.configuration.jdbi;

import static ru.tusur.prediction.service.util.JsonUtils.OBJECT_MAPPER_WITH_DEFAULT_JAVA_TIME;

import javax.sql.DataSource;
import org.jdbi.v3.core.ConnectionFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.jackson2.Jackson2Config;
import org.jdbi.v3.jackson2.Jackson2Plugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.spring.SpringConnectionFactory;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

// TODO логгирование
/**
 * Конфигурация JDBI.
 */
@Configuration
public class CommonJdbiConfiguration {

  @Bean
  public Jdbi jdbi(DataSource dataSource) {
    ConnectionFactory connectionFactory = new SpringConnectionFactory(dataSource);
    Jdbi jdbi = Jdbi.create(connectionFactory);
    jdbi.installPlugin(new SqlObjectPlugin())
        .installPlugin(new PostgresPlugin())
        .installPlugin(new Jackson2Plugin());
    jdbi.getConfig(Jackson2Config.class).setMapper(OBJECT_MAPPER_WITH_DEFAULT_JAVA_TIME);
    return jdbi;
  }

  @Bean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}

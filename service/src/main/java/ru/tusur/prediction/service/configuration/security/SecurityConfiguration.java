package ru.tusur.prediction.service.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Конфигурация security бинов.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(
            HandlerExceptionResolver handlerExceptionResolver) {
        return new DefaultAuthenticationFailureHandler(handlerExceptionResolver);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(
            HandlerExceptionResolver handlerExceptionResolver) {
        return new DefaultAuthenticationEntryPoint(handlerExceptionResolver);
    }
}

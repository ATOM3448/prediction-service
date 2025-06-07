package ru.tusur.prediction.service.configuration.security.apikey;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.tusur.prediction.service.api.security.apikey.ApiKeyAuthenticationConverter;
import ru.tusur.prediction.service.api.security.apikey.ApiKeyAuthenticationProvider;
import ru.tusur.prediction.service.api.security.apikey.model.apikey.ApiKey;
import ru.tusur.prediction.service.core.repository.ApiKeyRepository;

/**
 * Конфигурация безопасности на основе {@link ApiKey}.
 */
@Slf4j
@Configuration
public class ApiKeySecurityConfiguration {

    /**
     * Конфигурация фильтра с использованием {@link ApiKey}.
     */
    @Configuration
    @RequiredArgsConstructor
    public static class ApiKeySecurityFilterChainConfiguration {

        private final ApiKeyAuthenticationConverter apiKeyAuthenticationConverter;

        private final ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

        @Bean
        SecurityFilterChain apiKeySecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

            httpSecurity
                    .formLogin(login -> login.disable())
                    .httpBasic(basic -> basic.disable())
                    .logout(logout -> logout.disable())
                    .sessionManagement(
                            session ->
                                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            AuthenticationManager authenticationManager =
                    new ProviderManager(apiKeyAuthenticationProvider);

            AuthenticationFilter authenticationFilter =
                    new AuthenticationFilter(authenticationManager, apiKeyAuthenticationConverter);
            authenticationFilter.setSuccessHandler(
                    (request, response, authentication) ->
                            response.setStatus(HttpServletResponse.SC_OK));
            authenticationFilter.setFailureHandler(
                    (request, response, exception) -> {
                        response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED, exception.getMessage());
                    });

            httpSecurity
                    .addFilterAt(authenticationFilter, BasicAuthenticationFilter.class)
                    .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                    .csrf(csrf -> csrf.disable());
            return httpSecurity.build();
        }
    }

    @Bean
    public ApiKeyAuthenticationConverter apiKeyAuthenticationConverter() {
        return new ApiKeyAuthenticationConverter();
    }

    @Bean
    public ApiKeyAuthenticationProvider apiKeyAuthenticationProvider(
            ApiKeyRepository apiKeyRepository) {
        return new ApiKeyAuthenticationProvider(apiKeyRepository);
    }
}

package ru.tusur.prediction.service.configuration.security.apikey;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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

        private final AuthenticationSuccessHandler authenticationSuccessHandler =
                ((request, response, authentication) -> {});

        private final AuthenticationFailureHandler authenticationFailureHandler;

        private final AuthenticationEntryPoint authenticationEntryPoint;

        private final ApiKeyAuthenticationConverter apiKeyAuthenticationConverter;

        private final ApiKeyAuthenticationProvider apiKeyAuthenticationProvider;

        @Bean
        SecurityFilterChain apiKeySecurityFilterChain(HttpSecurity httpSecurity) throws Exception {

            httpSecurity
                    .formLogin(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .logout(AbstractHttpConfigurer::disable)
                    .csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(
                            session ->
                                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            AuthenticationManager authenticationManager =
                    new ProviderManager(apiKeyAuthenticationProvider);

            AuthenticationFilter authenticationFilter =
                    new AuthenticationFilter(authenticationManager, apiKeyAuthenticationConverter);

            authenticationFilter.setSuccessHandler(authenticationSuccessHandler);
            authenticationFilter.setFailureHandler(authenticationFailureHandler);

            httpSecurity
                    .addFilterAt(authenticationFilter, BasicAuthenticationFilter.class)
                    .authorizeHttpRequests(
                            authorize ->
                                    authorize
                                            .requestMatchers("/v3/**", "/swagger-ui/**")
                                            .permitAll()
                                            .anyRequest()
                                            .authenticated())
                    .exceptionHandling(
                            exceptionHandling ->
                                    exceptionHandling.authenticationEntryPoint(
                                            authenticationEntryPoint));
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

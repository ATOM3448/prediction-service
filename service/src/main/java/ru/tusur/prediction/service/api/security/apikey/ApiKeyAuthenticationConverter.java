package ru.tusur.prediction.service.api.security.apikey;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import ru.tusur.prediction.service.api.security.apikey.model.apikey.ApiKey;

/**
 * Класс реализующий извлечение {@link Authentication} из заголовка авторизации.
 */
public class ApiKeyAuthenticationConverter implements AuthenticationConverter {

    private static final String API_KEY_PREFIX = "Api-Key ";

    @Override
    public Authentication convert(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(API_KEY_PREFIX)) {
            return new ApiKey(authHeader.substring(API_KEY_PREFIX.length()));
        }

        return null;
    }
}

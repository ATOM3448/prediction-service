package ru.tusur.prediction.service.api.security.apikey;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import ru.tusur.prediction.service.api.security.apikey.model.apikey.ApiKey;

public class ApiKeyAuthenticationConverter implements AuthenticationConverter {

    private static final String PREFIX_AND_API_KEY_SPLITTER = "\\.";

    @Override
    public Authentication convert(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null) {
            return null;
        }

        String[] credentials = authHeader.split(PREFIX_AND_API_KEY_SPLITTER);

        if (credentials.length != 2) {
            return null;
        }

        return new ApiKey(credentials[0], credentials[1]);
    }
}

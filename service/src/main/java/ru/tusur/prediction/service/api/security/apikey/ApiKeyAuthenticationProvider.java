package ru.tusur.prediction.service.api.security.apikey;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.tusur.prediction.service.api.security.apikey.model.apikey.ApiKey;
import ru.tusur.prediction.service.core.repository.ApiKeyRepository;

/**
 * Класс производит аутентификацию, основанную на Api Key, затем заполняет контекст.
 */
@RequiredArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final ApiKeyRepository apiKeyRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication instanceof ApiKey apiKey) {
            Optional<Long> organization =
                    apiKeyRepository.getOrganizationByApiKey((String) apiKey.getCredentials());

            if (organization.isEmpty()) {
                throw new BadCredentialsException("Не корректный API-ключ");
            }

            ApiKey trustedApiKey = new ApiKey((String) apiKey.getCredentials(), organization.get());

            SecurityContextHolder.getContext().setAuthentication(trustedApiKey);

            return trustedApiKey;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKey.class.isAssignableFrom(authentication);
    }
}

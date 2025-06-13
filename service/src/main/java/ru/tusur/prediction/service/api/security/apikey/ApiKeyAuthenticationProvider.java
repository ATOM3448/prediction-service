package ru.tusur.prediction.service.api.security.apikey;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
        if (authentication instanceof ApiKey notVerifiedApiKey) {
            ru.tusur.prediction.service.core.model.apikey.ApiKey verifiedApiKey = verifyApiKey(
                    (String) notVerifiedApiKey.getCredentials(),
                    apiKeyRepository.getApiKeysByPrefix(notVerifiedApiKey.getPrefix())
            );

            List<GrantedAuthority> authorities = apiKeyRepository.getScopesByApiKey(verifiedApiKey.getId()).stream()
                            .map(SimpleGrantedAuthority::new)
                            .map(GrantedAuthority.class::cast)
                            .toList();

            ApiKey trustedApiKey = new ApiKey(verifiedApiKey.getOrganizationId(), authorities);

            SecurityContextHolder.getContext().setAuthentication(trustedApiKey);

            return trustedApiKey;
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKey.class.isAssignableFrom(authentication);
    }

    private ru.tusur.prediction.service.core.model.apikey.ApiKey verifyApiKey(
            String notVerifiedApiKey,
            List<ru.tusur.prediction.service.core.model.apikey.ApiKey> apiKeys
    ) {
        for (ru.tusur.prediction.service.core.model.apikey.ApiKey apiKey : apiKeys) {
            if (BCrypt.checkpw(notVerifiedApiKey, apiKey.getHash())) {
                if (apiKey.getExpired().isBefore(LocalDate.now())) {
                    throw new CredentialsExpiredException("API ключ истек");
                }

                return apiKey;
            }
        }

        throw new BadCredentialsException("Некорректный API-ключ");
    }
}

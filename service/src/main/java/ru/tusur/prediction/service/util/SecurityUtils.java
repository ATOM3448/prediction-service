package ru.tusur.prediction.service.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.tusur.prediction.service.api.security.apikey.model.apikey.ApiKey;
import ru.tusur.prediction.service.core.error.ServiceException;

import static ru.tusur.prediction.service.core.error.ErrorCode.UNAUTHORIZED;
import static ru.tusur.prediction.service.core.error.ErrorMessages.UNAUTHORIZED_MESSAGE;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class SecurityUtils {

    public static long getOrganizationIdFromSecurityContext() {
        return getPrincipal();
    }

    public static boolean validateAccessByOrganizationId(long organizationId) {
        if (getPrincipal() == organizationId) {
            return true;
        }

        log.warn("Не пройдена проверка валидации по идентификатору организации");
        return false;
    }

    private static long getPrincipal() {
        return (long) getAuthentication().getPrincipal();
    }

    private static ApiKey getAuthentication() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof ApiKey apiKey) {
            return apiKey;
        }

        throw new ServiceException(UNAUTHORIZED, UNAUTHORIZED_MESSAGE);
    }

}

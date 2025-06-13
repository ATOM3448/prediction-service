package ru.tusur.prediction.service.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Методы для работы с контекстом безопасности.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    public static long getOrganizationIdFromSecurityContext() {
        return (long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}

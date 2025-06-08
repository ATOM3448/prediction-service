package ru.tusur.prediction.service.configuration.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * Класс, переопределяющий поведение {@link AuthenticationEntryPoint} для дополнительного отображения
 * сообщения об ошибке.
 */
@AllArgsConstructor
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) {
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}

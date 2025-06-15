package ru.tusur.prediction.service.api.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.tusur.prediction.service.configuration.openapi.annotation.AccessDeniedApiResponse;
import ru.tusur.prediction.service.configuration.openapi.annotation.UnauthorizedApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@UnauthorizedApiResponse
@AccessDeniedApiResponse
@PreAuthorize("hasAuthority('READ')")
public @interface ReadAccess {
}

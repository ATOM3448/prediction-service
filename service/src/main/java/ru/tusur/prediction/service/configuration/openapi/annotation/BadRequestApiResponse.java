package ru.tusur.prediction.service.configuration.openapi.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@ApiResponse(
        responseCode = "400",
        description = "Некорректные данные",
        content = @Content(schema = @Schema(hidden = true))
)
public @interface BadRequestApiResponse {
}

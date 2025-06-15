package ru.tusur.prediction.service.configuration.openapi.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.servlet.function.EntityResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@ApiResponse(
        responseCode = "403",
        description = "Доступ запрещен",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EntityResponse.class),
                examples = @ExampleObject(
                        value =
                                """
                                {
                                  "type": "error",
                                  "code": 5,
                                  "message": "Доступ запрещен"
                                }
                                """
                )
        )
)
public @interface AccessDeniedApiResponse {
}

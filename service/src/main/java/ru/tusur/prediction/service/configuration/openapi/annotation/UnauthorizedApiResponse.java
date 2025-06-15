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
        responseCode = "401",
        description = "Ошибка авторизации",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = EntityResponse.class),
                examples = @ExampleObject(
                        value =
                                """
                                {
                                  "type": "error",
                                  "code": 4,
                                  "message": "Ошибка авторизации"
                                }
                                """
                )
        )
)
public @interface UnauthorizedApiResponse {
}

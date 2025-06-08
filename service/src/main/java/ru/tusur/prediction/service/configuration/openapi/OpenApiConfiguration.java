package ru.tusur.prediction.service.configuration.openapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(externalDocs = @ExternalDocumentation(description = "Документация"))
@SecuritySchemes(
        value = {
            @SecurityScheme(
                    name = OpenApiConfiguration.API_AUTH_SCHEMA_NAME,
                    type = SecuritySchemeType.APIKEY,
                    in = SecuritySchemeIn.HEADER,
                    paramName = "Authorization",
                    description = "Api key")
        })
public class OpenApiConfiguration {

    public static final String API_AUTH_SCHEMA_NAME = "apiAuth";
}

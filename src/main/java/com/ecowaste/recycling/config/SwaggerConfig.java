package com.ecowaste.recycling.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-apis")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Waste Recycling System").version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("barerAuth"))
                .components(
                        new Components()
                                .addSecuritySchemes("barerAuth", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}

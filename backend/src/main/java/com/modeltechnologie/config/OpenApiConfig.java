package com.modeltechnologie.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Model Technologie API")
                        .description("API Backend pour la plateforme de formation Model Technologie")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Support API")
                                .email("support@modeltechnologie.com")
                                .url("https://modeltechnologie.com"))
                        .license(new License()
                                .name("Proprietary")
                                .url("https://modeltechnologie.com")));
    }
}
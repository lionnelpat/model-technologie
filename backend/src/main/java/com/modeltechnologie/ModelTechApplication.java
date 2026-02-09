package com.modeltechnologie;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main application class for Model Technologie Backend
 *
 * Launches the Spring Boot application with embedded Tomcat
 */
@SpringBootApplication
public class ModelTechApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModelTechApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Model Technologie API")
                        .version("0.1.0")
                        .description("Backend API pour Model Technologie - Plateforme de formation en data science")
                        .termsOfService("https://model-technologie.com/terms")
                        .contact(new Contact()
                                .name("Model Technologie")
                                .url("https://model-technologie.com")
                                .email("business.modeltech@gmail.com"))
                        .license(new License()
                                .name("Proprietary")
                                .url("https://model-technologie.com")));
    }
}
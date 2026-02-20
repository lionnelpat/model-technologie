// src/main/java/com/modeltechnologie/config/CorsProperties.java
/**
 * Propriétés de configuration CORS
 * Permet de paramétrer les origines acceptées via application.yml
 */

package com.modeltechnologie.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {

    /**
     * Origines CORS acceptées
     * Exemple: "http://localhost:3000,https://modeltechnologie.com"
     */
    private List<String> allowedOrigins = new ArrayList<>();

    /**
     * Méthodes HTTP acceptées
     * Exemple: "GET,POST,PUT,DELETE,OPTIONS,PATCH"
     */
    private List<String> allowedMethods = new ArrayList<>();

    /**
     * Headers acceptés
     * Exemple: "*" ou "Content-Type,Authorization"
     */
    private List<String> allowedHeaders = new ArrayList<>();

    /**
     * Exposer les credentials
     */
    private boolean allowCredentials = true;

    /**
     * Durée de vie du cache preflight en secondes
     */
    private long maxAge = 3600L;
}

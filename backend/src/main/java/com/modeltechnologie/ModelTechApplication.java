package com.modeltechnologie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Main application class for Model Technologie Backend
 *
 * Launches the Spring Boot application with embedded Tomcat
 */

@Slf4j
@SpringBootApplication
public class ModelTechApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(ModelTechApplication.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    /**
     * Log les informations de démarrage de l'application
     */
    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        String serverPort = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "/");
        String hostAddress = "localhost";

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("Impossible de déterminer l'adresse IP de l'hôte", e);
        }

        log.info("""
            
            ----------------------------------------------------------
            \t️ Backend de Model Technologie démarré avec succès! 🏛️
            \t
            \t🌍 Accès local: \t\t{}://localhost:{}{}
            \t🌐 Accès externe: \t\t{}://{}:{}{}
            \t📊 Monitoring: \t\t{}://{}:{}{}actuactor/health
            \t
            \t📋 Profil(s): \t\t{}
            \t🗃️ Base de données: \t{}
            ----------------------------------------------------------
            """,
                protocol, serverPort, contextPath,
                protocol, hostAddress, serverPort, contextPath,
                protocol, hostAddress, serverPort, contextPath,
                env.getActiveProfiles().length == 0 ? "default" : Arrays.toString(env.getActiveProfiles()),
                env.getProperty("spring.datasource.url", "H2 (en mémoire)")
        );
    }
}
package com.modeltechnologie.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@Slf4j
@Tag(name = "Health Check", description = "APIs for monitoring")
public class HealthController {

    @Value("${spring.application.name:model-technologie-backend}")
    private String applicationName;

    @Value("${app.version:0.1.0}")
    private String appVersion;

    @GetMapping("/health")
    @Operation(summary = "Health check endpoint")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", applicationName);
        response.put("version", appVersion);
        response.put("timestamp", System.currentTimeMillis());

        log.info("Health check called - Application is UP");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/actuator/health")
    @Operation(summary = "Spring Boot Actuator health endpoint")
    public ResponseEntity<Map<String, String>> actuatorHealth() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");

        log.info("Actuator health check called");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    @Operation(summary = "Application info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", applicationName);
        response.put("version", appVersion);
        response.put("description", "Backend API pour Model Technologie");
        response.put("environment", System.getenv("SPRING_PROFILES_ACTIVE"));

        return ResponseEntity.ok(response);
    }
}
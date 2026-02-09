package com.modeltechnologie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configure HTTP security
     *
     * Public endpoints (no authentication required):
     * - Health checks
     * - Auth endpoints
     * - Swagger/API docs
     * - GET endpoints (read-only for frontend)
     *
     * Protected endpoints (authentication required):
     * - /api/admin/** (CRUD operations)
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for stateless API (Spring Security 6.1+ syntax)
                .csrf(csrf -> csrf.disable())

                // CORS configuration
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Session management (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Authorization rules
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints
                        .requestMatchers(
                                "/v1/health",
                                "/v1/actuator/**",
                                "/v1/info",
                                "/v1/auth/**",
                                "/api/swagger-ui/**",
                                "/api/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // READ-ONLY endpoints (GET) - Public API for frontend
                        .requestMatchers("GET", "/v1/bootcamps").permitAll()
                        .requestMatchers("GET", "/v1/bootcamps/**").permitAll()
                        .requestMatchers("GET", "/v1/alumni").permitAll()
                        .requestMatchers("GET", "/v1/alumni/**").permitAll()
                        .requestMatchers("GET", "/v1/bootcamp-sessions").permitAll()
                        .requestMatchers("GET", "/v1/bootcamp-sessions/**").permitAll()
                        .requestMatchers("GET", "/v1/project-gallery").permitAll()
                        .requestMatchers("GET", "/v1/project-gallery/**").permitAll()

                        // ADMIN endpoints (POST/PUT/DELETE) - Require authentication
                        .requestMatchers("/v1/admin/**").authenticated()

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )

                // Basic auth for testing (optional)
                .httpBasic(basic -> {});

        return http.build();
    }

    /**
     * Password encoder for user passwords
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * CORS configuration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:80",
                "http://localhost",
                "http://127.0.0.1:3000"
        ));
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
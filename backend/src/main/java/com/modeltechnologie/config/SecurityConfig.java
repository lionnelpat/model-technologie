package com.modeltechnologie.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsProperties corsProperties;

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
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        // Public endpoints AVANT context-path (Spring Security voit sans /api)
                        .requestMatchers(
                                "/v1/health",
                                "/v1/actuator/**",
                                "/v1/info",
                                "/v1/auth/**"
                        ).permitAll()

                        // Swagger/OpenAPI docs
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // READ-ONLY endpoints
                        .requestMatchers("GET", "/v1/bootcamps").permitAll()
                        .requestMatchers("GET", "/v1/bootcamps/**").permitAll()
                        .requestMatchers("GET", "/v1/alumni").permitAll()
                        .requestMatchers("GET", "/v1/alumni/**").permitAll()
                        .requestMatchers("GET", "/v1/bootcamp-sessions").permitAll()
                        .requestMatchers("GET", "/v1/bootcamp-sessions/**").permitAll()
                        .requestMatchers("GET", "/v1/project-gallery").permitAll()
                        .requestMatchers("GET", "/v1/project-gallery/**").permitAll()

                        // Admin endpoints
                        .requestMatchers("/v1/admin/**").authenticated()

                        // Tous les autres endpoints
                        .anyRequest().authenticated()
                )
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

    /**
     * Crée la source de configuration CORS à partir des propriétés
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        log.info("Initialisation CORS avec les origines: {}", corsProperties.getAllowedOrigins());

        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ Utiliser les propriétés au lieu de hardcoder
        configuration.setAllowedOrigins(corsProperties.getAllowedOrigins());
        configuration.setAllowedMethods(corsProperties.getAllowedMethods());
        configuration.setAllowedHeaders(corsProperties.getAllowedHeaders());
        configuration.setAllowCredentials(corsProperties.isAllowCredentials());
        configuration.setMaxAge(corsProperties.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        log.debug("Configuration CORS initialisée");
        return source;
    }
}
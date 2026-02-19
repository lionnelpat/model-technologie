package com.modeltechnologie.controller;

import com.modeltechnologie.dto.LoginRequestDTO;
import com.modeltechnologie.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "Authentication and JWT token management")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Login endpoint - authenticate user and return JWT token.
     * Credentials are sent in the request body (never in URL params).
     */
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate and get JWT token")
    public Map<String, Object> login(@Valid @RequestBody LoginRequestDTO loginRequest) {

        Map<String, Object> response = new HashMap<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            String token = jwtTokenProvider.generateTokenFromAuthentication(authentication);

            response.put("success", true);
            response.put("token", token);
            response.put("username", loginRequest.getUsername());
            response.put("message", "Login successful");

            log.info("User {} logged in successfully", loginRequest.getUsername());

        } catch (AuthenticationException e) {
            response.put("success", false);
            response.put("message", "Invalid username or password");

            log.warn("Failed login attempt for user: {}", loginRequest.getUsername());
        }

        return response;
    }

    /**
     * Validate token endpoint
     */
    @GetMapping("/validate")
    @Operation(summary = "Validate Token", description = "Check if JWT token is valid")
    public Map<String, Object> validateToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        Map<String, Object> response = new HashMap<>();

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.put("valid", false);
            response.put("message", "Missing or invalid Authorization header");
            return response;
        }

        String token = authHeader.substring(7);

        if (jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUsernameFromToken(token);
            response.put("valid", true);
            response.put("username", username);
            response.put("message", "Token is valid");
        } else {
            response.put("valid", false);
            response.put("message", "Token is invalid or expired");
        }

        return response;
    }

    /**
     * Get current user info
     */
    @GetMapping("/me")
    @Operation(summary = "Get Current User", description = "Get information about the currently authenticated user")
    public Map<String, Object> getCurrentUser(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();

        if (authentication != null && authentication.isAuthenticated()) {
            response.put("username", authentication.getName());
            response.put("authenticated", true);
        } else {
            response.put("authenticated", false);
            response.put("message", "User not authenticated");
        }

        return response;
    }
}

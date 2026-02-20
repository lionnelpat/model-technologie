package com.modeltechnologie.controller;

import com.modeltechnologie.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Dev-only authentication controller.
 * Only loaded when Spring profile is "dev" — never available in staging or production.
 */
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication (Dev only)", description = "Development-only auth endpoints")
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
public class DevAuthController {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Dev login — generates a JWT token without credentials.
     * Restricted to the "dev" Spring profile only.
     */
    @PostMapping("/dev-login")
    @Operation(summary = "Dev Login", description = "Login for development only (no credentials required). NOT available in staging/prod.")
    public Map<String, Object> devLogin() {
        Map<String, Object> response = new HashMap<>();

        String token = jwtTokenProvider.generateToken("dev-user");

        response.put("success", true);
        response.put("token", token);
        response.put("username", "dev-user");
        response.put("message", "Dev login successful - use this token for API calls");
        response.put("usage", "Add 'Authorization: Bearer " + token + "' to your API requests");

        log.info("Dev login token generated");

        return response;
    }
}

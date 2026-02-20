package com.modeltechnologie.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO for login requests.
 * Credentials are transmitted in the request body, never in URL parameters.
 */
@Data
public class LoginRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}

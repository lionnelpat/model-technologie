package com.modeltechnologie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de réponse après soumission d'un message de contact
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
    private LocalDateTime createdAt;
}

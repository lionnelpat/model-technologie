package com.modeltechnologie.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la création d'un message de contact
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageCreateDTO {

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne peut pas dépasser 100 caractères")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String lastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 255, message = "L'email ne peut pas dépasser 255 caractères")
    private String email;

    @Size(max = 30, message = "Le téléphone ne peut pas dépasser 30 caractères")
    private String phone;

    @Size(max = 255, message = "L'entreprise ne peut pas dépasser 255 caractères")
    private String company;

    @NotBlank(message = "L'objet est obligatoire")
    @Size(max = 255, message = "L'objet ne peut pas dépasser 255 caractères")
    private String subject;

    @NotBlank(message = "Le message est obligatoire")
    @Size(max = 5000, message = "Le message ne peut pas dépasser 5000 caractères")
    private String message;
}

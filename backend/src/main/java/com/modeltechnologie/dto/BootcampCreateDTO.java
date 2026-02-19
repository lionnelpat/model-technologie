package com.modeltechnologie.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BootcampCreateDTO {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 255, message = "Le titre doit contenir entre 3 et 255 caractères")
    private String title;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 2000, message = "La description ne peut pas dépasser 2000 caractères")
    private String description;

    @Min(value = 1, message = "La durée en jours doit être d'au moins 1 jour")
    @Max(value = 30, message = "La durée en jours ne peut pas dépasser 30 jours")
    private Integer durationDays;

    @Min(value = 1, message = "La durée en heures doit être d'au moins 1 heure")
    @Max(value = 200, message = "La durée en heures ne peut pas dépasser 200 heures")
    private Integer durationHours;

    @NotBlank(message = "Le public cible est obligatoire")
    private String audience;

    @NotBlank(message = "Les prérequis sont obligatoires")
    private String prerequisites;

    @NotBlank(message = "Le prix est obligatoire")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})? [A-Z]+$",
            message = "Le prix doit être au format '450000 FCFA'")
    private String price;

    private String nextSession;

    @NotNull(message = "Le statut featured est obligatoire")
    private Boolean featured;

    @NotEmpty(message = "Au moins un bénéfice est requis")
    private List<@NotBlank String> benefits;

    private String level;
}
package com.modeltechnologie.mapper;

import com.modeltechnologie.dto.BootcampCreateDTO;
import com.modeltechnologie.dto.BootcampResponseDTO;
import com.modeltechnologie.dto.BootcampUpdateDTO;
import com.modeltechnologie.entity.Bootcamp;
import com.modeltechnologie.entity.BootcampBenefit;
import com.modeltechnologie.entity.BootcampLevel;
import com.modeltechnologie.entity.BootcampStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BootcampMapper {

    private static final String DEFAULT_CURRENCY = "FCFA";
    private static final BootcampLevel DEFAULT_LEVEL = BootcampLevel.FOUNDATION;

    public BootcampResponseDTO toResponseDTO(Bootcamp bootcamp) {
        if (bootcamp == null) {
            return null;
        }

        return BootcampResponseDTO.builder()
                .title(bootcamp.getName())
                .description(bootcamp.getDescription())
                .duration(formatDuration(bootcamp))
                .audience(bootcamp.getAudience())
                .prerequisites(bootcamp.getPrerequisites())
                .price(bootcamp.getPriceFcfa())
                .nextSession("Fevrier 2026") // À remplacer par une vraie logique
                .benefits(extractBenefits(bootcamp))
                .featured(bootcamp.getFeatured() != null ? bootcamp.getFeatured() : false)
                .build();
    }

    public Bootcamp toEntity(BootcampCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Bootcamp bootcamp = Bootcamp.builder()
                .name(dto.getTitle())
                .description(dto.getDescription())
                .audience(dto.getAudience())
                .prerequisites(dto.getPrerequisites())
                .durationDays(dto.getDurationDays())
                .durationHours(dto.getDurationHours())
                .durationWeeks(calculateWeeks(dto.getDurationDays()))
                .priceFcfa(dto.getPrice())
                .currency(DEFAULT_CURRENCY)
                .level(dto.getLevel() != null ? dto.getLevel() : DEFAULT_LEVEL)
                .featured(dto.getFeatured())
                .status(BootcampStatus.ACTIVE)
                .maxStudents(15) // Valeur par défaut
                .build();

        if (dto.getBenefits() != null) {
            List<BootcampBenefit> benefits = createBenefits(dto.getBenefits(), bootcamp);
            bootcamp.setBenefits(benefits);
        }

        return bootcamp;
    }

    public void updateEntity(Bootcamp bootcamp, BootcampUpdateDTO dto) {
        if (dto.getDescription() != null) {
            bootcamp.setDescription(dto.getDescription());
        }
        if (dto.getAudience() != null) {
            bootcamp.setAudience(dto.getAudience());
        }
        if (dto.getPrerequisites() != null) {
            bootcamp.setPrerequisites(dto.getPrerequisites());
        }
        if (dto.getDurationDays() != null) {
            bootcamp.setDurationDays(dto.getDurationDays());
            bootcamp.setDurationWeeks(calculateWeeks(dto.getDurationDays()));
        }
        if (dto.getDurationHours() != null) {
            bootcamp.setDurationHours(dto.getDurationHours());
        }
        if (dto.getPrice() != null) {
            bootcamp.setPriceFcfa(dto.getPrice());
        }
        if (dto.getFeatured() != null) {
            bootcamp.setFeatured(dto.getFeatured());
        }
        if (dto.getStatus() != null) {
            bootcamp.setStatus(dto.getStatus());
        }
        if (dto.getBenefits() != null) {
            updateBenefits(bootcamp, dto.getBenefits());
        }
    }

    private String formatDuration(Bootcamp bootcamp) {
        if (bootcamp.getDurationDays() != null && bootcamp.getDurationHours() != null) {
            return String.format("%d jours (%dh)",
                    bootcamp.getDurationDays(),
                    bootcamp.getDurationHours());
        }
        return "Non spécifié";
    }

    private Integer calculateWeeks(Integer days) {
        return days != null ? (int) Math.ceil(days / 7.0) : 1;
    }

    private List<String> extractBenefits(Bootcamp bootcamp) {
        if (bootcamp.getBenefits() == null || bootcamp.getBenefits().isEmpty()) {
            return Collections.emptyList();
        }

        return bootcamp.getBenefits().stream()
                .map(BootcampBenefit::getBenefitText)
                .collect(Collectors.toList());
    }

    private List<BootcampBenefit> createBenefits(List<String> benefitTexts, Bootcamp bootcamp) {
        if (benefitTexts == null) {
            return Collections.emptyList();
        }

        return benefitTexts.stream()
                .map(text -> BootcampBenefit.builder()
                        .bootcamp(bootcamp)
                        .benefitText(text)
                        .displayOrder(benefitTexts.indexOf(text))
                        .build())
                .collect(Collectors.toList());
    }

    private void updateBenefits(Bootcamp bootcamp, List<String> newBenefits) {
        // Supprimer les anciens bénéfices
        bootcamp.getBenefits().clear();

        // Ajouter les nouveaux
        if (newBenefits != null && !newBenefits.isEmpty()) {
            List<BootcampBenefit> benefits = createBenefits(newBenefits, bootcamp);
            bootcamp.getBenefits().addAll(benefits);
        }
    }
}
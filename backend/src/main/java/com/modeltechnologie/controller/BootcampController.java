package com.modeltechnologie.controller;

import com.modeltechnologie.dto.BootcampCreateDTO;
import com.modeltechnologie.dto.BootcampResponseDTO;
import com.modeltechnologie.dto.BootcampUpdateDTO;
import com.modeltechnologie.exception.DuplicateBootcampException;
import com.modeltechnologie.service.BootcampService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/bootcamps")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Bootcamp Management", description = "APIs pour la gestion des bootcamps")
public class BootcampController {

    private final BootcampService bootcampService;

    @PostMapping
    @Operation(summary = "Créer un nouveau bootcamp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bootcamp créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides"),
            @ApiResponse(responseCode = "409", description = "Un bootcamp avec ce nom existe déjà")
    })
    public ResponseEntity<BootcampResponseDTO> createBootcamp(@Valid @RequestBody BootcampCreateDTO createDTO) {
        log.info("Requête de création de bootcamp: {}", createDTO.getTitle());

        try {
            BootcampResponseDTO created = bootcampService.createBootcamp(createDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (DuplicateBootcampException e) {
            log.warn("Tentative de création d'un bootcamp en doublon: {}", createDTO.getTitle());
            throw e; // Sera géré par l'ExceptionHandler
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un bootcamp par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bootcamp trouvé"),
            @ApiResponse(responseCode = "404", description = "Bootcamp non trouvé")
    })
    public ResponseEntity<BootcampResponseDTO> getBootcampById(@PathVariable Long id) {
        log.debug("Requête de récupération du bootcamp avec l'id: {}", id);

        BootcampResponseDTO bootcamp = bootcampService.getBootcampById(id);
        return ResponseEntity.ok(bootcamp);
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les bootcamps actifs")
    public ResponseEntity<List<BootcampResponseDTO>> getAllBootcamps() {
        log.debug("Requête de récupération de tous les bootcamps actifs");

        List<BootcampResponseDTO> bootcamps = bootcampService.getAllActiveBootcamps();
        return ResponseEntity.ok(bootcamps);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Récupérer un bootcamp par son nom")
    public ResponseEntity<BootcampResponseDTO> getBootcampByName(@PathVariable String name) {
        log.debug("Requête de récupération du bootcamp avec le nom: {}", name);

        BootcampResponseDTO bootcamp = bootcampService.getBootcampByName(name);
        return ResponseEntity.ok(bootcamp);
    }

    @GetMapping("/featured")
    @Operation(summary = "Récupérer les bootcamps en vedette")
    public ResponseEntity<List<BootcampResponseDTO>> getFeaturedBootcamps() {
        log.debug("Requête de récupération des bootcamps en vedette");

        List<BootcampResponseDTO> bootcamps = bootcampService.getFeaturedBootcamps();
        return ResponseEntity.ok(bootcamps);
    }

    @GetMapping("/level/{level}")
    @Operation(summary = "Récupérer les bootcamps par niveau")
    public ResponseEntity<List<BootcampResponseDTO>> getBootcampsByLevel(@PathVariable String level) {
        log.debug("Requête de récupération des bootcamps de niveau: {}", level);

        List<BootcampResponseDTO> bootcamps = bootcampService.getBootcampsByLevel(level);
        return ResponseEntity.ok(bootcamps);
    }

    @GetMapping("/sector/{sector}")
    @Operation(summary = "Récupérer les bootcamps par secteur")
    public ResponseEntity<List<BootcampResponseDTO>> getBootcampsByTargetSector(@PathVariable String sector) {
        log.debug("Requête de récupération des bootcamps du secteur: {}", sector);

        List<BootcampResponseDTO> bootcamps = bootcampService.getBootcampsByTargetSector(sector);
        return ResponseEntity.ok(bootcamps);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Récupérer les bootcamps par statut")
    public ResponseEntity<List<BootcampResponseDTO>> getBootcampsByStatus(@PathVariable String status) {
        log.debug("Requête de récupération des bootcamps avec statut: {}", status);

        List<BootcampResponseDTO> bootcamps = bootcampService.getBootcampsByStatus(status);
        return ResponseEntity.ok(bootcamps);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un bootcamp")
    public ResponseEntity<BootcampResponseDTO> updateBootcamp(
            @PathVariable Long id,
            @Valid @RequestBody BootcampUpdateDTO updateDTO) {
        log.info("Requête de mise à jour du bootcamp avec l'id: {}", id);

        BootcampResponseDTO updated = bootcampService.updateBootcamp(id, updateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un bootcamp (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bootcamp supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Bootcamp non trouvé")
    })
    public ResponseEntity<Void> deleteBootcamp(@PathVariable Long id) {
        log.info("Requête de suppression du bootcamp avec l'id: {}", id);

        bootcampService.deleteBootcamp(id);
        return ResponseEntity.noContent().build();
    }
}
package com.modeltechnologie.controller;

import com.modeltechnologie.dto.ContactMessageCreateDTO;
import com.modeltechnologie.dto.ContactMessageResponseDTO;
import com.modeltechnologie.service.ContactMessageService;
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

@RestController
@RequestMapping("/v1/contact")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Contact", description = "API pour la soumission des messages de contact")
public class ContactController {

    private final ContactMessageService contactMessageService;

    @PostMapping
    @Operation(summary = "Soumettre un message de contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message enregistré avec succès"),
            @ApiResponse(responseCode = "400", description = "Données d'entrée invalides")
    })
    public ResponseEntity<ContactMessageResponseDTO> submitContact(
            @Valid @RequestBody ContactMessageCreateDTO createDTO) {
        log.info("Nouveau message de contact reçu de: {}", createDTO.getEmail());

        ContactMessageResponseDTO response = contactMessageService.saveMessage(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

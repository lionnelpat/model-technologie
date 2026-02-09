package com.modeltechnologie.controller;

import com.modeltechnologie.entity.Alumni;
import com.modeltechnologie.service.AlumniService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumni")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Alumni Management", description = "APIs for managing alumni")
public class AlumniController {

    private final AlumniService alumniService;

    @PostMapping
    @Operation(summary = "Create a new alumni")
    public ResponseEntity<Alumni> createAlumni(@RequestBody Alumni alumni) {
        log.info("Creating alumni: {}", alumni.getEmail());
        Alumni created = alumniService.createAlumni(alumni);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get alumni by ID")
    public ResponseEntity<Alumni> getAlumniById(@PathVariable Long id) {
        Optional<Alumni> alumni = alumniService.getAlumniById(id);
        return alumni.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all active alumni")
    public ResponseEntity<List<Alumni>> getAllAlumni() {
        List<Alumni> alumni = alumniService.getAllActiveAlumni();
        return ResponseEntity.ok(alumni);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get alumni by email")
    public ResponseEntity<Alumni> getAlumniByEmail(@PathVariable String email) {
        Optional<Alumni> alumni = alumniService.getAlumniByEmail(email);
        return alumni.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/country/{country}")
    @Operation(summary = "Get alumni by country")
    public ResponseEntity<List<Alumni>> getAlumniByCountry(@PathVariable String country) {
        List<Alumni> alumni = alumniService.getAlumniByCountry(country);
        return ResponseEntity.ok(alumni);
    }

    @GetMapping("/company/{company}")
    @Operation(summary = "Get alumni by current company")
    public ResponseEntity<List<Alumni>> getAlumniByCompany(@PathVariable String company) {
        List<Alumni> alumni = alumniService.getAlumniByCompany(company);
        return ResponseEntity.ok(alumni);
    }

    @GetMapping("/skill/{skill}")
    @Operation(summary = "Get alumni by skill")
    public ResponseEntity<List<Alumni>> getAlumniBySkill(@PathVariable String skill) {
        List<Alumni> alumni = alumniService.getAlumniBySkill(skill);
        return ResponseEntity.ok(alumni);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update alumni information")
    public ResponseEntity<Alumni> updateAlumni(@PathVariable Long id, @RequestBody Alumni alumni) {
        try {
            Alumni updated = alumniService.updateAlumni(id, alumni);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete alumni (soft delete)")
    public ResponseEntity<Void> deleteAlumni(@PathVariable Long id) {
        alumniService.deleteAlumni(id);
        return ResponseEntity.noContent().build();
    }
}
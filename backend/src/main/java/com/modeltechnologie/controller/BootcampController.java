package com.modeltechnologie.controller;

import com.modeltechnologie.entity.Bootcamp;
import com.modeltechnologie.service.BootcampService;
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
@RequestMapping("/v1/bootcamps")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Bootcamp Management", description = "APIs for managing bootcamps")
public class BootcampController {

    private final BootcampService bootcampService;

    @PostMapping
    @Operation(summary = "Create a new bootcamp")
    public ResponseEntity<Bootcamp> createBootcamp(@RequestBody Bootcamp bootcamp) {
        log.info("Creating bootcamp: {}", bootcamp.getName());
        Bootcamp created = bootcampService.createBootcamp(bootcamp);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bootcamp by ID")
    public ResponseEntity<Bootcamp> getBootcampById(@PathVariable Long id) {
        Optional<Bootcamp> bootcamp = bootcampService.getBootcampById(id);
        return bootcamp.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all active bootcamps")
    public ResponseEntity<List<Bootcamp>> getAllBootcamps() {
        List<Bootcamp> bootcamps = bootcampService.getAllActiveBootcamps();
        return ResponseEntity.ok(bootcamps);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get bootcamp by name")
    public ResponseEntity<Bootcamp> getBootcampByName(@PathVariable String name) {
        Optional<Bootcamp> bootcamp = bootcampService.getBootcampByName(name);
        return bootcamp.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/level/{level}")
    @Operation(summary = "Get bootcamps by level")
    public ResponseEntity<List<Bootcamp>> getBootcampsByLevel(@PathVariable String level) {
        List<Bootcamp> bootcamps = bootcampService.getBootcampsByLevel(level);
        return ResponseEntity.ok(bootcamps);
    }

    @GetMapping("/sector/{sector}")
    @Operation(summary = "Get bootcamps by target sector")
    public ResponseEntity<List<Bootcamp>> getBootcampsByTargetSector(@PathVariable String sector) {
        List<Bootcamp> bootcamps = bootcampService.getBootcampsByTargetSector(sector);
        return ResponseEntity.ok(bootcamps);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get bootcamps by status")
    public ResponseEntity<List<Bootcamp>> getBootcampsByStatus(@PathVariable String status) {
        List<Bootcamp> bootcamps = bootcampService.getBootcampsByStatus(status);
        return ResponseEntity.ok(bootcamps);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update bootcamp information")
    public ResponseEntity<Bootcamp> updateBootcamp(@PathVariable Long id, @RequestBody Bootcamp bootcamp) {
        try {
            Bootcamp updated = bootcampService.updateBootcamp(id, bootcamp);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete bootcamp (soft delete)")
    public ResponseEntity<Void> deleteBootcamp(@PathVariable Long id) {
        bootcampService.deleteBootcamp(id);
        return ResponseEntity.noContent().build();
    }
}
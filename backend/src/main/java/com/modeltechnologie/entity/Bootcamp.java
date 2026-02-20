package com.modeltechnologie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

/**
 * Bootcamp entity - Represents a data science bootcamp program
 */
@Entity
@Table(name = "bootcamps")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bootcamp extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "audience", length = 500)
    private String audience;

    @Column(name = "prerequisites", length = 500)
    private String prerequisites;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private BootcampLevel level;

    @Column(name = "duration_weeks", nullable = false)
    private Integer durationWeeks;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Column(name = "duration_hours", nullable = false)
    private Integer durationHours;

    @Column(name = "max_students")
    private Integer maxStudents;

    @Column(name = "price_euros")
    private Double priceEuros;

    @Column(name = "price_fcfa")
    private String priceFcfa;

    @Column(name = "currency")
    private String currency = "EUR";

    @Enumerated(EnumType.STRING)
    @Column(name = "target_sector")
    private TargetSector targetSector;

    @Column(name = "featured")
    private Boolean featured = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BootcampStatus status;

    // Relation avec les bénéfices — LAZY pour éviter le chargement systématique.
    // Les requêtes qui nécessitent les benefits utilisent LEFT JOIN FETCH explicitement.
    @OneToMany(mappedBy = "bootcamp", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BootcampBenefit> benefits = new ArrayList<>();

    // ✅ Relation avec les sessions
    @OneToMany(mappedBy = "bootcamp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BootcampSession> sessions = new ArrayList<>();
}
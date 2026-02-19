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
    private String audience; // ← NOUVEAU

    @Column(name = "prerequisites", length = 500)
    private String prerequisites; // ← NOUVEAU

    @Column(name = "level", nullable = false)
    private String level; // Foundation, Practitioner, Professional

    @Column(name = "duration_weeks", nullable = false)
    private Integer durationWeeks;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays; // ← NOUVEAU (pour "5 jours")

    @Column(name = "duration_hours", nullable = false)
    private Integer durationHours; // ← NOUVEAU (pour "35h")

    @Column(name = "max_students")
    private Integer maxStudents;

    @Column(name = "price_euros")
    private Double priceEuros;

    @Column(name = "price_fcfa")
    private String priceFcfa; // ← NOUVEAU (stocké comme string)

    @Column(name = "currency")
    private String currency = "EUR";

    @Column(name = "target_sector")
    private String targetSector;

    @Column(name = "featured")
    private Boolean featured = false; // ← NOUVEAU

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE";

    // ✅ Relation avec les bénéfices
    @OneToMany(mappedBy = "bootcamp", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<BootcampBenefit> benefits = new ArrayList<>();

    // ✅ Relation avec les sessions
    @OneToMany(mappedBy = "bootcamp", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BootcampSession> sessions = new ArrayList<>();
}
package com.modeltechnologie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "level", nullable = false)
    private String level; // Foundation, Practitioner, Professional

    @Column(name = "duration_weeks", nullable = false)
    private Integer durationWeeks;

    @Column(name = "max_students")
    private Integer maxStudents;

    @Column(name = "price_euros")
    private Double priceEuros;

    @Column(name = "currency")
    private String currency = "EUR";

    @Column(name = "target_sector")
    private String targetSector; // Banking, Telecom, Retail, Health, Agritech, Government

    @Column(name = "status", nullable = false)
    private String status = "PLANNED"; // PLANNED, ACTIVE, COMPLETED, ARCHIVED
}
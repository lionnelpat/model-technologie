package com.modeltechnologie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * BootcampSession entity - Represents a session/cohort of a bootcamp
 */
@Entity
@Table(name = "bootcamp_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BootcampSession extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_id", nullable = false)
    private Bootcamp bootcamp;

    @Column(name = "session_name", nullable = false)
    private String sessionName; // e.g., "Cohort 2024-Q1"

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "country", nullable = false)
    private String country; // Côte d'Ivoire, Senegal, etc.

    @Column(name = "location", nullable = false)
    private String location; // City name

    @Column(name = "language", nullable = false)
    private String language = "FR"; // FR, EN

    @Column(name = "status", nullable = false)
    private String status = "SCHEDULED"; // SCHEDULED, ONGOING, COMPLETED, CANCELLED
}
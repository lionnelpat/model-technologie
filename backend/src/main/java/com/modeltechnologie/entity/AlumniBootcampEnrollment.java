package com.modeltechnologie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * AlumniBootcampEnrollment entity - Represents enrollment of an alumni in a bootcamp session
 */
@Entity
@Table(name = "alumni_bootcamp_enrollments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumniBootcampEnrollment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumni_id", nullable = false)
    private Alumni alumni;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_session_id", nullable = false)
    private BootcampSession bootcampSession;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "final_score")
    private Double finalScore; // 0-100

    @Column(name = "status", nullable = false)
    private String status = "ENROLLED"; // ENROLLED, IN_PROGRESS, COMPLETED, DROPPED

    @Column(name = "certificate_issued")
    private Boolean certificateIssued = false;

    @Column(name = "certificate_url")
    private String certificateUrl;

    @Column(name = "immersion_company")
    private String immersionCompany; // Company for final project immersion
}
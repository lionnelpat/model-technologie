package com.modeltechnologie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProjectGallery entity - Represents final projects completed by alumni
 */
@Entity
@Table(name = "project_galleries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectGallery extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumni_id", nullable = false)
    private Alumni alumni;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bootcamp_session_id", nullable = false)
    private BootcampSession bootcampSession;

    @Column(name = "project_title", nullable = false)
    private String projectTitle;

    @Column(name = "project_description", length = 2000)
    private String projectDescription;

    @Column(name = "technologies_used", length = 500)
    private String technologiesUsed; // Comma-separated: Python, SQL, Machine Learning, etc.

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "project_url")
    private String projectUrl;

    @Column(name = "demo_video_url")
    private String demoVideoUrl;

    @Column(name = "project_image_url")
    private String projectImageUrl;

    @Column(name = "company_partner")
    private String companyPartner; // The company that sponsored the immersion

    @Column(name = "status", nullable = false)
    private String status = "COMPLETED"; // ONGOING, COMPLETED, APPROVED
}
package com.modeltechnologie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Alumni entity - Represents a student who completed a bootcamp
 */
@Entity
@Table(name = "alumni")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumni extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "linkedin_profile")
    private String linkedinProfile;

    @Column(name = "github_profile")
    private String githubProfile;

    @Column(name = "bio", length = 1000)
    private String bio;

    @Column(name = "current_job_title")
    private String currentJobTitle;

    @Column(name = "current_company")
    private String currentCompany;

    @Column(name = "skills", length = 2000)
    private String skills; // Comma-separated skills

    @Column(name = "portfolio_url")
    private String portfolioUrl;

    @Column(name = "status", nullable = false)
    private String status = "ACTIVE"; // ACTIVE, INACTIVE, ARCHIVED
}
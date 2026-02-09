package com.modeltechnologie.repository;

import com.modeltechnologie.entity.ProjectGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectGalleryRepository extends JpaRepository<ProjectGallery, Long> {

    List<ProjectGallery> findByAlumniIdAndIsActiveTrue(Long alumniId);

    List<ProjectGallery> findByBootcampSessionIdAndIsActiveTrue(Long bootcampSessionId);

    List<ProjectGallery> findByCompanyPartnerAndIsActiveTrue(String companyPartner);

    @Query("SELECT pg FROM ProjectGallery pg WHERE pg.status = 'APPROVED' AND pg.isActive = true ORDER BY pg.createdAt DESC")
    List<ProjectGallery> findApprovedProjects();

    @Query("SELECT pg FROM ProjectGallery pg WHERE pg.technologiesUsed LIKE %:tech% AND pg.isActive = true")
    List<ProjectGallery> findByTechnology(@Param("tech") String tech);
}
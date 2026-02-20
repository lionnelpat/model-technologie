package com.modeltechnologie.repository;

import com.modeltechnologie.entity.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumniRepository extends JpaRepository<Alumni, Long> {

    Optional<Alumni> findByEmail(String email);

    List<Alumni> findByCountryAndIsActiveTrue(String country);

    List<Alumni> findByCurrentCompanyAndIsActiveTrue(String currentCompany);

    @Query("SELECT a FROM Alumni a WHERE a.isActive = true ORDER BY a.createdAt DESC")
    List<Alumni> findAllActive();

    @Query("SELECT a FROM Alumni a WHERE a.skills LIKE %:skill% AND a.isActive = true")
    List<Alumni> findBySkill(@Param("skill") String skill);
}
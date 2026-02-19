package com.modeltechnologie.repository;

import com.modeltechnologie.entity.Bootcamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BootcampRepository extends JpaRepository<Bootcamp, Long> {

    // ✅ CORRECT: Sans @Query, Spring utilise query by method name
    Optional<Bootcamp> findByName(String name);

    // ✅ Récupérer les bootcamps actifs
    @Query("SELECT b FROM Bootcamp b WHERE b.status = 'ACTIVE' ORDER BY b.name ASC")
    List<Bootcamp> findAllActive();

    // ✅ Récupérer les bootcamps en vedette
    @Query("SELECT b FROM Bootcamp b WHERE b.featured = true AND b.status = 'ACTIVE' ORDER BY b.name ASC")
    List<Bootcamp> findFeaturedBootcamps();

    // ✅ Récupérer par niveau
    @Query("SELECT b FROM Bootcamp b WHERE b.level = :level AND b.status = 'ACTIVE' ORDER BY b.name ASC")
    List<Bootcamp> findByLevel(@Param("level") String level);

    // ✅ Récupérer par secteur
    @Query("SELECT b FROM Bootcamp b WHERE b.targetSector = :sector AND b.status = 'ACTIVE' ORDER BY b.name ASC")
    List<Bootcamp> findByTargetSector(@Param("sector") String sector);

    // ✅ Récupérer par statut
    @Query("SELECT b FROM Bootcamp b WHERE b.status = :status ORDER BY b.name ASC")
    List<Bootcamp> findByStatus(@Param("status") String status);

    // ✅ CORRECT: Récupérer avec les bénéfices (LEFT JOIN FETCH)
    @Query("SELECT DISTINCT b FROM Bootcamp b " +
            "LEFT JOIN FETCH b.benefits " +
            "WHERE b.id = :id")
    Optional<Bootcamp> findByIdWithBenefits(@Param("id") Long id);

    // ✅ Vérifier si un nom existe
    boolean existsByNameIgnoreCase(String name);
}

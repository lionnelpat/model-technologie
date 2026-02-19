package com.modeltechnologie.repository;

import com.modeltechnologie.entity.Bootcamp;
import com.modeltechnologie.entity.BootcampLevel;
import com.modeltechnologie.entity.BootcampStatus;
import com.modeltechnologie.entity.TargetSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BootcampRepository extends JpaRepository<Bootcamp, Long> {

    // Sans @Query, Spring utilise query by method name (sans benefits)
    Optional<Bootcamp> findByName(String name);

    // Récupérer un bootcamp par nom avec ses benefits (LEFT JOIN FETCH)
    @Query("SELECT DISTINCT b FROM Bootcamp b " +
            "LEFT JOIN FETCH b.benefits " +
            "WHERE b.name = :name")
    Optional<Bootcamp> findByNameWithBenefits(@Param("name") String name);

    // Bootcamps actifs avec benefits chargés (LEFT JOIN FETCH pour éviter N+1)
    // Utilisation du FQN de l'enum dans JPQL pour les valeurs fixes
    @Query("SELECT DISTINCT b FROM Bootcamp b " +
            "LEFT JOIN FETCH b.benefits " +
            "WHERE b.status = com.modeltechnologie.entity.BootcampStatus.ACTIVE " +
            "ORDER BY b.name ASC")
    List<Bootcamp> findAllActive();

    // Bootcamps en vedette avec benefits chargés
    @Query("SELECT DISTINCT b FROM Bootcamp b " +
            "LEFT JOIN FETCH b.benefits " +
            "WHERE b.featured = true AND b.status = com.modeltechnologie.entity.BootcampStatus.ACTIVE " +
            "ORDER BY b.name ASC")
    List<Bootcamp> findFeaturedBootcamps();

    // Bootcamps par niveau avec benefits chargés
    @Query("SELECT DISTINCT b FROM Bootcamp b " +
            "LEFT JOIN FETCH b.benefits " +
            "WHERE b.level = :level AND b.status = com.modeltechnologie.entity.BootcampStatus.ACTIVE " +
            "ORDER BY b.name ASC")
    List<Bootcamp> findByLevel(@Param("level") BootcampLevel level);

    // Bootcamps par secteur avec benefits chargés
    @Query("SELECT DISTINCT b FROM Bootcamp b " +
            "LEFT JOIN FETCH b.benefits " +
            "WHERE b.targetSector = :sector AND b.status = com.modeltechnologie.entity.BootcampStatus.ACTIVE " +
            "ORDER BY b.name ASC")
    List<Bootcamp> findByTargetSector(@Param("sector") TargetSector sector);

    // Bootcamps par statut avec benefits chargés
    @Query("SELECT DISTINCT b FROM Bootcamp b " +
            "LEFT JOIN FETCH b.benefits " +
            "WHERE b.status = :status " +
            "ORDER BY b.name ASC")
    List<Bootcamp> findByStatus(@Param("status") BootcampStatus status);

    // Récupérer un bootcamp par ID avec ses benefits (LEFT JOIN FETCH)
    @Query("SELECT DISTINCT b FROM Bootcamp b " +
            "LEFT JOIN FETCH b.benefits " +
            "WHERE b.id = :id")
    Optional<Bootcamp> findByIdWithBenefits(@Param("id") Long id);

    // Vérifier si un nom existe
    boolean existsByNameIgnoreCase(String name);
}

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

    Optional<Bootcamp> findByName(String name);

    List<Bootcamp> findByLevelAndIsActiveTrue(String level);

    List<Bootcamp> findByTargetSectorAndIsActiveTrue(String targetSector);

    List<Bootcamp> findByStatusAndIsActiveTrue(String status);

    @Query("SELECT b FROM Bootcamp b WHERE b.isActive = true ORDER BY b.createdAt DESC")
    List<Bootcamp> findAllActive();
}
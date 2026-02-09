package com.modeltechnologie.repository;

import com.modeltechnologie.entity.BootcampSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BootcampSessionRepository extends JpaRepository<BootcampSession, Long> {

    List<BootcampSession> findByBootcampIdAndIsActiveTrue(Long bootcampId);

    List<BootcampSession> findByCountryAndIsActiveTrue(String country);

    List<BootcampSession> findByStatusAndIsActiveTrue(String status);

    @Query("SELECT bs FROM BootcampSession bs WHERE bs.startDate >= :startDate AND bs.isActive = true")
    List<BootcampSession> findUpcomingSessions(@Param("startDate") LocalDate startDate);

    @Query("SELECT bs FROM BootcampSession bs WHERE bs.isActive = true ORDER BY bs.startDate DESC")
    List<BootcampSession> findAllActive();
}

package com.modeltechnologie.repository;

import com.modeltechnologie.entity.AlumniBootcampEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumniBootcampEnrollmentRepository extends JpaRepository<AlumniBootcampEnrollment, Long> {

    List<AlumniBootcampEnrollment> findByAlumniIdAndIsActiveTrue(Long alumniId);

    List<AlumniBootcampEnrollment> findByBootcampSessionIdAndIsActiveTrue(Long bootcampSessionId);

    Optional<AlumniBootcampEnrollment> findByAlumniIdAndBootcampSessionId(Long alumniId, Long bootcampSessionId);

    List<AlumniBootcampEnrollment> findByStatusAndIsActiveTrue(String status);

    @Query("SELECT ae FROM AlumniBootcampEnrollment ae WHERE ae.certificateIssued = true AND ae.isActive = true")
    List<AlumniBootcampEnrollment> findCertifiedEnrollments();

    @Query("SELECT COUNT(ae) FROM AlumniBootcampEnrollment ae WHERE ae.bootcampSessionId = :sessionId AND ae.status = 'COMPLETED'")
    Long countCompletedBySession(@Param("sessionId") Long sessionId);
}
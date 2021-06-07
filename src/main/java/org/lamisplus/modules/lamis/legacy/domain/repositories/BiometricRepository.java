package org.lamisplus.modules.lamis.legacy.domain.repositories;

import org.lamisplus.modules.lamis.legacy.domain.entities.Biometric;
import org.lamisplus.modules.lamis.legacy.domain.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BiometricRepository extends JpaRepository<Biometric, String> {
    List<Biometric> findByPatient(Patient patient);

    @Query(value = "select * from biometric where facility_id = ?1 and extra->'source'->>'type' = 'mobile' and " +
        "cast(extra->'source'->>'date' as date) > ?2 and archived = false", nativeQuery = true)
    List<Biometric> getMobileBiometric(Long facilityId, LocalDate date);
}

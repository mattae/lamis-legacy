package org.lamisplus.modules.lamis.legacy.domain.repositories;

import org.lamisplus.modules.lamis.legacy.domain.entities.Facility;
import org.lamisplus.modules.lamis.legacy.domain.entities.Hts;
import org.lamisplus.modules.lamis.legacy.domain.entities.Patient;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HtsRepository extends JpaRepository<Hts, Long> {
    Hts findByFacilityAndClientCode(Facility facility, String code);

    List<Hts> findByFacility(Facility facility, Pageable pageable);

    List<Hts> findByFacility(Facility facility);

    Hts findByFacilityAndSurnameAndOtherNamesAndGenderAndDateBirth(Facility facility, String surname, String otherNames, String gender, LocalDate dateBirth);

    Optional<Hts> findByUuid(String uuid);

    @Query(value = "select * from hts where facility_id = ?1 and extra->'source'->>'type' = 'mobile' and " +
        "cast(extra->'source'->>'date' as date ) > ?2 and archived = false", nativeQuery = true)
    List<Hts> getMobileHts(Long facilityId, LocalDate date);
}

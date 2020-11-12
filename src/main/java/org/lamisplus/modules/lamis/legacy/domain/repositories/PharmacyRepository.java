package org.lamisplus.modules.lamis.legacy.domain.repositories;


import org.lamisplus.modules.lamis.legacy.domain.entities.Facility;
import org.lamisplus.modules.lamis.legacy.domain.entities.Patient;
import org.lamisplus.modules.lamis.legacy.domain.entities.Pharmacy;
import org.lamisplus.modules.lamis.legacy.domain.repositories.projections.VisitDates;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    List<Pharmacy> findByPatientAndDateVisit(Patient patient, LocalDate date);

    List<Pharmacy> findByPatient(Patient patient);

    @Query("select v from Pharmacy v where v.patient = ?1")
    List<VisitDates> findVisitsByPatient(Patient patient);

    List<Pharmacy> findByPatientAndFacilityOrderByDateVisit(Patient patient, Facility facility, Pageable pageable);

    default Pharmacy getLastRefillByPatient(Patient patient) {
        List<Pharmacy> pharmacies = getLastRefillVisit(patient.getId(), PageRequest.of(0, 1));
        return pharmacies.isEmpty() ? null : pharmacies.get(0);
    }

    @Query(value = "select * from pharmacy p,jsonb_array_elements(lines) with ordinality a(l)  where p.patient_id = ?1 and " +
            "cast(jsonb_extract_path_text(l,'regimen_type_id') as integer) = 15 and archived = false and p.date_visit <= current_date",
            nativeQuery = true)
    List<Pharmacy> getIptRefills(Patient patient);

    @Query(value = "select * from pharmacy p, jsonb_array_elements(lines) with ordinality a(l)  where p.patient_id = ?1 " +
            "and cast(jsonb_extract_path_text(l,'regimen_type_id') as integer) in (1, 2, 3, 4, 14) " +
            "and archived = false and p.date_visit <= current_date order by p.date_visit desc",
            countQuery = "select count(*) from pharmacy p, jsonb_array_elements(lines) with ordinality a(l)  where p.patient_id = ?1 " +
                    "and cast(jsonb_extract_path_text(l,'regimen_type_id') as integer) in (1, 2, 3, 4, 14) " +
                    "and archived = false and p.date_visit <= current_date order by p.date_visit desc",
            nativeQuery = true)
    List<Pharmacy> getLastRefillVisit(Long patientId, Pageable pageable);

    Optional<Pharmacy> findByUuid(String uuid);

    List<Pharmacy> findByPatientAndDateVisitBefore(Patient patient, LocalDate date, Pageable pageable);

    List<Pharmacy> findByPatientAndDateVisitAfter(Patient patient, LocalDate date, Pageable pageable);

    @Query(value = "select date(date_visit + cast(jsonb_extract_path_text(l,'duration') as integer) + INTERVAL '29 DAYS') ltfu_date from pharmacy ," +
            "jsonb_array_elements(lines) with ordinality a(l) " +
            "where cast(jsonb_extract_path_text(l,'regimen_type_id') as integer) in (1,2,3,4,14) and patient_id = ? and date_visit <= CURRENT_DATE " +
            "and archived = false ORDER BY date_visit DESC, l->>'duration' DESC LIMIT 1", nativeQuery = true)
    Optional<Date> getLTFUDate(Long patientId);
}

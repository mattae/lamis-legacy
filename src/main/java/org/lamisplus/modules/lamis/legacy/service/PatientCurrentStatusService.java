package org.lamisplus.modules.lamis.legacy.service;

import com.foreach.across.core.annotations.Exposed;
import lombok.RequiredArgsConstructor;
import org.lamisplus.modules.lamis.legacy.domain.entities.Patient;
import org.lamisplus.modules.lamis.legacy.domain.entities.StatusHistory;
import org.lamisplus.modules.lamis.legacy.domain.entities.enumerations.ClientStatus;
import org.lamisplus.modules.lamis.legacy.domain.repositories.PharmacyRepository;
import org.lamisplus.modules.lamis.legacy.domain.repositories.StatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Exposed
public class PatientCurrentStatusService {
    private final PharmacyRepository pharmacyRepository;
    private final StatusHistoryRepository statusHistoryRepository;

    public ClientStatus getStatus(Patient patient) {
        ClientStatus status = patient.getStatusAtRegistration();
        Optional<Date> date = pharmacyRepository.getLTFUDate(patient.getId());
        Optional<StatusHistory> statusHistory = statusHistoryRepository.getCurrentStatusForPatientAt(patient, LocalDate.now());
        if (!date.isPresent()) {
            if (patient.getStatusAtRegistration() != null) {
                status = patient.getStatusAtRegistration();
            } else if (statusHistory.isPresent()) {
                status = statusHistory.get().getStatus();
            }
        } else {
            LocalDate ltfuDate = Instant.ofEpochMilli(date.get().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            if (!ltfuDate.isBefore(LocalDate.now())) {
                if (statusHistory.isPresent() && statusHistory.get().getStatus().equals(ClientStatus.KNOWN_DEATH)) {
                    status = ClientStatus.KNOWN_DEATH;
                } else if (statusHistory.isPresent() && statusHistory.get().getStatus().equals(ClientStatus.ART_TRANSFER_OUT)) {
                    status = ClientStatus.ART_TRANSFER_OUT;
                } else if (statusHistory.isPresent() && statusHistory.get().getStatus().equals(ClientStatus.STOPPED_TREATMENT)) {
                    status = ClientStatus.STOPPED_TREATMENT;
                } else if (statusHistory.isPresent()) {
                    StatusHistory history = statusHistory.get();
                    if (history.getStatus().equals(ClientStatus.ART_RESTART)) {
                        status = ClientStatus.ART_RESTART;
                    } else if (history.getStatus().equals(ClientStatus.ART_START)) {
                        status = ClientStatus.ART_START;
                    } else if (history.getStatus().equals(ClientStatus.ART_TRANSFER_IN)) {
                        status = ClientStatus.ART_TRANSFER_IN;
                    } else {
                        status = ClientStatus.ART_START;
                    }
                } else {
                    status = ClientStatus.ART_START;
                }
            } else {
                if (statusHistory.isPresent() && statusHistory.get().getStatus().equals(ClientStatus.KNOWN_DEATH)) {
                    status = ClientStatus.KNOWN_DEATH;
                } else if (statusHistory.isPresent() && statusHistory.get().getStatus().equals(ClientStatus.ART_TRANSFER_OUT)) {
                    status = ClientStatus.ART_TRANSFER_OUT;
                } else if (statusHistory.isPresent() && statusHistory.get().getStatus().equals(ClientStatus.STOPPED_TREATMENT)) {
                    status = ClientStatus.STOPPED_TREATMENT;
                } else {
                    status = ClientStatus.LOST_TO_FOLLOWUP;
                }
            }
        }
        return status;
    }
}

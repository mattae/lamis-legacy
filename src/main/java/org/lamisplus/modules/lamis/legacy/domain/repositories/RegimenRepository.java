package org.lamisplus.modules.lamis.legacy.domain.repositories;


import org.lamisplus.modules.lamis.legacy.domain.entities.Regimen;
import org.lamisplus.modules.lamis.legacy.domain.entities.RegimenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface RegimenRepository extends JpaRepository<Regimen, Long> {
    Optional<Regimen> findByDescription(String description);

    List<Regimen> findByRegimenType(RegimenType regimenType);

    List<Regimen> findByRegimenTypeIn(List<RegimenType> regimenTypes);

    @Query("SELECT distinct r FROM Regimen r join r.regimenType t WHERE t.description = ?1 ORDER BY r.description")
    List<Regimen> retrieveRegimenByName(RegimenType regimenType);

    List<Regimen> findByRegimenTypeAndActiveTrueOrderByPriorityDesc(RegimenType regimenType);
}

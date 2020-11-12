package org.lamisplus.modules.lamis.legacy.domain.repositories;


import org.lamisplus.modules.base.domain.entities.Province;
import org.lamisplus.modules.lamis.legacy.domain.entities.CommunityPharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommunityPharmacyRepository extends JpaRepository<CommunityPharmacy, Long> {
    Optional<CommunityPharmacy> findByUuid(String uuid);

    List<CommunityPharmacy> findByPin(String pin);

    List<CommunityPharmacy> findByActiveIsTrue();

    @Query("select c from CommunityPharmacy c join c.lga l join l.state s where s.id = ?1")
    Page<CommunityPharmacy> findByState(Long stateId, Pageable pageable);

    List<CommunityPharmacy> findByLga(Province province);
}

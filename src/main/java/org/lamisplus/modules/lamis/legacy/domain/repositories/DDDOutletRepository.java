package org.lamisplus.modules.lamis.legacy.domain.repositories;


import org.lamisplus.modules.base.domain.entities.Province;
import org.lamisplus.modules.lamis.legacy.domain.entities.DDDOutlet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DDDOutletRepository extends JpaRepository<DDDOutlet, Long> {
    Optional<DDDOutlet> findByUuid(String uuid);

    List<DDDOutlet> findByPin(String pin);

    List<DDDOutlet> findByActiveIsTrue();

    List<DDDOutlet> findByActiveIsTrueAndType(String type);

    @Query("select c from DDDOutlet c join c.lga l join l.state s where s.id = ?1")
    Page<DDDOutlet> findByState(Long stateId, Pageable pageable);

    List<DDDOutlet> findByLgaAndType(Province province, String type);
}

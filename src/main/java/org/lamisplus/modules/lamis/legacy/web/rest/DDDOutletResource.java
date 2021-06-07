package org.lamisplus.modules.lamis.legacy.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.web.errors.BadRequestAlertException;
import org.lamisplus.modules.base.web.util.HeaderUtil;
import org.lamisplus.modules.base.web.util.PaginationUtil;
import org.lamisplus.modules.lamis.legacy.domain.entities.DDDOutlet;
import org.lamisplus.modules.lamis.legacy.domain.repositories.DDDOutletRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DDDOutletResource {
    private final static String ENTITY_NAME = "ddd-outlet";
    private final DDDOutletRepository DDDOutletRepository;

    /**
     * POST  /ddd-outlets : Create a new DDDOutlet.
     *
     * @param DDDOutlet the DDDOutlet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new DDDOutlet, or with status 400 (Bad Request) if the DDDOutlet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ddd-outlets")
    public ResponseEntity<DDDOutlet> createDDDOutlet(@RequestBody DDDOutlet DDDOutlet) throws URISyntaxException {
        LOG.debug("REST request to save DDD Outlet : {}", DDDOutlet);
        if (DDDOutlet.getId() != null) {
            throw new BadRequestAlertException("A new DDD Outlet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DDDOutlet result = DDDOutletRepository.save(DDDOutlet);
        return ResponseEntity.created(new URI("/api/ddd-outlets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ddd-outlets : Updates an existing DDDOutlet.
     *
     * @param DDDOutlet the DDDOutlet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated DDDOutlet,
     * or with status 400 (Bad Request) if the DDDOutlet is not valid,
     * or with status 500 (Internal Server Error) if the DDDOutlet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ddd-outlets")
    public ResponseEntity<DDDOutlet> updateDDDOutlet(@RequestBody DDDOutlet DDDOutlet) throws URISyntaxException {
        LOG.debug("REST request to update DDD Outlet : {}", DDDOutlet);
        if (DDDOutlet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DDDOutlet result = DDDOutletRepository.save(DDDOutlet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, DDDOutlet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ddd-outlets/:id : get the "id" DDDOutlet.
     *
     * @param id the id of the DDDOutlet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the DDDOutlet, or with status 404 (Not Found)
     */
    @GetMapping("/ddd-outlets/{id}")
    public ResponseEntity<DDDOutlet> getDDDOutlet(@PathVariable Long id) {
        LOG.debug("REST request to get DDDOutlet : {}", id);
        Optional<DDDOutlet> DDDOutlet = DDDOutletRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(DDDOutlet);
    }

    /**
     * GET  /ddd-outlets : get the all DDDOutlet.
     *
     * @param stateId the id of the state whose DDDOutlet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the DDDOutlet, or with status 404 (Not Found)
     */
    @GetMapping("/ddd-outlets")
    public ResponseEntity<List<DDDOutlet>> getDDDOutlets(@RequestParam Long stateId, Pageable pageable) {
        LOG.debug("REST request to get all DDD Outlet by state: {}", stateId);
        Page<DDDOutlet> page = DDDOutletRepository.findByState(stateId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/patients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}

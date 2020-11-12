package org.lamisplus.modules.lamis.legacy.web.rest;

import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lamisplus.modules.base.web.errors.BadRequestAlertException;
import org.lamisplus.modules.base.web.util.HeaderUtil;
import org.lamisplus.modules.base.web.util.PaginationUtil;
import org.lamisplus.modules.lamis.legacy.domain.entities.CommunityPharmacy;
import org.lamisplus.modules.lamis.legacy.domain.repositories.CommunityPharmacyRepository;
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
public class CommunityPharmacyResource {
    private final static String ENTITY_NAME = "community-pharmacy";
    private final CommunityPharmacyRepository communityPharmacyRepository;

    /**
     * POST  /community-pharmacies : Create a new communityPharmacy.
     *
     * @param communityPharmacy the communityPharmacy to create
     * @return the ResponseEntity with status 201 (Created) and with body the new communityPharmacy, or with status 400 (Bad Request) if the communityPharmacy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/community-pharmacies")
    public ResponseEntity<CommunityPharmacy> createCommunityPharmacy(@RequestBody CommunityPharmacy communityPharmacy) throws URISyntaxException {
        LOG.debug("REST request to save Community Pharmacy : {}", communityPharmacy);
        if (communityPharmacy.getId() != null) {
            throw new BadRequestAlertException("A new community pharmacy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommunityPharmacy result = communityPharmacyRepository.save(communityPharmacy);
        return ResponseEntity.created(new URI("/api/community-pharmacies/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /community-pharmacies : Updates an existing communityPharmacy.
     *
     * @param communityPharmacy the communityPharmacy to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated communityPharmacy,
     * or with status 400 (Bad Request) if the communityPharmacy is not valid,
     * or with status 500 (Internal Server Error) if the communityPharmacy couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/community-pharmacies")
    public ResponseEntity<CommunityPharmacy> updateCommunityPharmacy(@RequestBody CommunityPharmacy communityPharmacy) throws URISyntaxException {
        LOG.debug("REST request to update Community Pharmacy : {}", communityPharmacy);
        if (communityPharmacy.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommunityPharmacy result = communityPharmacyRepository.save(communityPharmacy);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, communityPharmacy.getId().toString()))
                .body(result);
    }

    /**
     * GET  /community-pharmacies/:id : get the "id" communityPharmacy.
     *
     * @param id the id of the communityPharmacy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the communityPharmacy, or with status 404 (Not Found)
     */
    @GetMapping("/community-pharmacies/{id}")
    public ResponseEntity<CommunityPharmacy> getCommunityPharmacy(@PathVariable Long id) {
        LOG.debug("REST request to get CommunityPharmacy : {}", id);
        Optional<CommunityPharmacy> communityPharmacy = communityPharmacyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(communityPharmacy);
    }

    /**
     * GET  /community-pharmacies : get the all communityPharmacy.
     *
     * @param stateId the id of the state whose communityPharmacy to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the communityPharmacy, or with status 404 (Not Found)
     */
    @GetMapping("/community-pharmacies")
    public ResponseEntity<List<CommunityPharmacy>> getCommunityPharmacies(@RequestParam Long stateId, Pageable pageable) {
        LOG.debug("REST request to get all Community Pharmacy by state: {}", stateId);
        Page<CommunityPharmacy> page = communityPharmacyRepository.findByState(stateId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/patients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}

package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.BucoVersion;
import io.github.jhipster.application.service.BucoVersionService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.BucoVersionCriteria;
import io.github.jhipster.application.service.BucoVersionQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * REST controller for managing BucoVersion.
 */
@RestController
@RequestMapping("/api")
public class BucoVersionResource {

    private final Logger log = LoggerFactory.getLogger(BucoVersionResource.class);

    private static final String ENTITY_NAME = "bucoVersion";

    private final BucoVersionService bucoVersionService;

    private final BucoVersionQueryService bucoVersionQueryService;

    public BucoVersionResource(BucoVersionService bucoVersionService, BucoVersionQueryService bucoVersionQueryService) {
        this.bucoVersionService = bucoVersionService;
        this.bucoVersionQueryService = bucoVersionQueryService;
    }

    /**
     * POST  /buco-versions : Create a new bucoVersion.
     *
     * @param bucoVersion the bucoVersion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bucoVersion, or with status 400 (Bad Request) if the bucoVersion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/buco-versions")
    @Timed
    public ResponseEntity<BucoVersion> createBucoVersion(@RequestBody BucoVersion bucoVersion) throws URISyntaxException {
        log.debug("REST request to save BucoVersion : {}", bucoVersion);
        if (bucoVersion.getId() != null) {
            throw new BadRequestAlertException("A new bucoVersion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BucoVersion result = bucoVersionService.save(bucoVersion);
        return ResponseEntity.created(new URI("/api/buco-versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buco-versions : Updates an existing bucoVersion.
     *
     * @param bucoVersion the bucoVersion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bucoVersion,
     * or with status 400 (Bad Request) if the bucoVersion is not valid,
     * or with status 500 (Internal Server Error) if the bucoVersion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/buco-versions")
    @Timed
    public ResponseEntity<BucoVersion> updateBucoVersion(@RequestBody BucoVersion bucoVersion) throws URISyntaxException {
        log.debug("REST request to update BucoVersion : {}", bucoVersion);
        if (bucoVersion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BucoVersion result = bucoVersionService.save(bucoVersion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bucoVersion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buco-versions : get all the bucoVersions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of bucoVersions in body
     */
    @GetMapping("/buco-versions")
    @Timed
    public ResponseEntity<List<BucoVersion>> getAllBucoVersions(BucoVersionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BucoVersions by criteria: {}", criteria);
        Page<BucoVersion> page = bucoVersionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/buco-versions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /buco-versions/count : count all the bucoVersions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/buco-versions/count")
    @Timed
    public ResponseEntity<Long> countBucoVersions(BucoVersionCriteria criteria) {
        log.debug("REST request to count BucoVersions by criteria: {}", criteria);
        return ResponseEntity.ok().body(bucoVersionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /buco-versions/:id : get the "id" bucoVersion.
     *
     * @param id the id of the bucoVersion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bucoVersion, or with status 404 (Not Found)
     */
    @GetMapping("/buco-versions/{id}")
    @Timed
    public ResponseEntity<BucoVersion> getBucoVersion(@PathVariable Long id) {
        log.debug("REST request to get BucoVersion : {}", id);
        Optional<BucoVersion> bucoVersion = bucoVersionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bucoVersion);
    }

    /**
     * DELETE  /buco-versions/:id : delete the "id" bucoVersion.
     *
     * @param id the id of the bucoVersion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/buco-versions/{id}")
    @Timed
    public ResponseEntity<Void> deleteBucoVersion(@PathVariable Long id) {
        log.debug("REST request to delete BucoVersion : {}", id);
        bucoVersionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

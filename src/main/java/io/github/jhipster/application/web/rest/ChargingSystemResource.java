package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.ChargingSystem;
import io.github.jhipster.application.service.ChargingSystemService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ChargingSystemCriteria;
import io.github.jhipster.application.service.ChargingSystemQueryService;
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
 * REST controller for managing ChargingSystem.
 */
@RestController
@RequestMapping("/api")
public class ChargingSystemResource {

    private final Logger log = LoggerFactory.getLogger(ChargingSystemResource.class);

    private static final String ENTITY_NAME = "chargingSystem";

    private final ChargingSystemService chargingSystemService;

    private final ChargingSystemQueryService chargingSystemQueryService;

    public ChargingSystemResource(ChargingSystemService chargingSystemService, ChargingSystemQueryService chargingSystemQueryService) {
        this.chargingSystemService = chargingSystemService;
        this.chargingSystemQueryService = chargingSystemQueryService;
    }

    /**
     * POST  /charging-systems : Create a new chargingSystem.
     *
     * @param chargingSystem the chargingSystem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chargingSystem, or with status 400 (Bad Request) if the chargingSystem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/charging-systems")
    @Timed
    public ResponseEntity<ChargingSystem> createChargingSystem(@RequestBody ChargingSystem chargingSystem) throws URISyntaxException {
        log.debug("REST request to save ChargingSystem : {}", chargingSystem);
        if (chargingSystem.getId() != null) {
            throw new BadRequestAlertException("A new chargingSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargingSystem result = chargingSystemService.save(chargingSystem);
        return ResponseEntity.created(new URI("/api/charging-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /charging-systems : Updates an existing chargingSystem.
     *
     * @param chargingSystem the chargingSystem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chargingSystem,
     * or with status 400 (Bad Request) if the chargingSystem is not valid,
     * or with status 500 (Internal Server Error) if the chargingSystem couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/charging-systems")
    @Timed
    public ResponseEntity<ChargingSystem> updateChargingSystem(@RequestBody ChargingSystem chargingSystem) throws URISyntaxException {
        log.debug("REST request to update ChargingSystem : {}", chargingSystem);
        if (chargingSystem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ChargingSystem result = chargingSystemService.save(chargingSystem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chargingSystem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /charging-systems : get all the chargingSystems.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of chargingSystems in body
     */
    @GetMapping("/charging-systems")
    @Timed
    public ResponseEntity<List<ChargingSystem>> getAllChargingSystems(ChargingSystemCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ChargingSystems by criteria: {}", criteria);
        Page<ChargingSystem> page = chargingSystemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/charging-systems");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /charging-systems/count : count all the chargingSystems.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/charging-systems/count")
    @Timed
    public ResponseEntity<Long> countChargingSystems(ChargingSystemCriteria criteria) {
        log.debug("REST request to count ChargingSystems by criteria: {}", criteria);
        return ResponseEntity.ok().body(chargingSystemQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /charging-systems/:id : get the "id" chargingSystem.
     *
     * @param id the id of the chargingSystem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chargingSystem, or with status 404 (Not Found)
     */
    @GetMapping("/charging-systems/{id}")
    @Timed
    public ResponseEntity<ChargingSystem> getChargingSystem(@PathVariable Long id) {
        log.debug("REST request to get ChargingSystem : {}", id);
        Optional<ChargingSystem> chargingSystem = chargingSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chargingSystem);
    }

    /**
     * DELETE  /charging-systems/:id : delete the "id" chargingSystem.
     *
     * @param id the id of the chargingSystem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/charging-systems/{id}")
    @Timed
    public ResponseEntity<Void> deleteChargingSystem(@PathVariable Long id) {
        log.debug("REST request to delete ChargingSystem : {}", id);
        chargingSystemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

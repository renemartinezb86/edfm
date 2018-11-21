package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.FreeUnit;
import io.github.jhipster.application.service.FreeUnitService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.FreeUnitCriteria;
import io.github.jhipster.application.service.FreeUnitQueryService;
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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing FreeUnit.
 */
@RestController
@RequestMapping("/api")
public class FreeUnitResource {

    private final Logger log = LoggerFactory.getLogger(FreeUnitResource.class);

    private static final String ENTITY_NAME = "freeUnit";

    private final FreeUnitService freeUnitService;

    private final FreeUnitQueryService freeUnitQueryService;

    public FreeUnitResource(FreeUnitService freeUnitService, FreeUnitQueryService freeUnitQueryService) {
        this.freeUnitService = freeUnitService;
        this.freeUnitQueryService = freeUnitQueryService;
    }

    /**
     * POST  /free-units : Create a new freeUnit.
     *
     * @param freeUnit the freeUnit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freeUnit, or with status 400 (Bad Request) if the freeUnit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/free-units")
    @Timed
    public ResponseEntity<FreeUnit> createFreeUnit(@RequestBody FreeUnit freeUnit) throws URISyntaxException {
        log.debug("REST request to save FreeUnit : {}", freeUnit);
        if (freeUnit.getId() != null) {
            throw new BadRequestAlertException("A new freeUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FreeUnit result = freeUnitService.save(freeUnit);
        return ResponseEntity.created(new URI("/api/free-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-units : Updates an existing freeUnit.
     *
     * @param freeUnit the freeUnit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freeUnit,
     * or with status 400 (Bad Request) if the freeUnit is not valid,
     * or with status 500 (Internal Server Error) if the freeUnit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/free-units")
    @Timed
    public ResponseEntity<FreeUnit> updateFreeUnit(@RequestBody FreeUnit freeUnit) throws URISyntaxException {
        log.debug("REST request to update FreeUnit : {}", freeUnit);
        if (freeUnit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FreeUnit result = freeUnitService.save(freeUnit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, freeUnit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-units : get all the freeUnits.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of freeUnits in body
     */
    @GetMapping("/free-units")
    @Timed
    public ResponseEntity<List<FreeUnit>> getAllFreeUnits(FreeUnitCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FreeUnits by criteria: {}", criteria);
        Page<FreeUnit> page = freeUnitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-units");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /free-units/count : count all the freeUnits.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/free-units/count")
    @Timed
    public ResponseEntity<Long> countFreeUnits(FreeUnitCriteria criteria) {
        log.debug("REST request to count FreeUnits by criteria: {}", criteria);
        return ResponseEntity.ok().body(freeUnitQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /free-units/:id : get the "id" freeUnit.
     *
     * @param id the id of the freeUnit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freeUnit, or with status 404 (Not Found)
     */
    @GetMapping("/free-units/{id}")
    @Timed
    public ResponseEntity<FreeUnit> getFreeUnit(@PathVariable Long id) {
        log.debug("REST request to get FreeUnit : {}", id);
        Optional<FreeUnit> freeUnit = freeUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(freeUnit);
    }

    /**
     * DELETE  /free-units/:id : delete the "id" freeUnit.
     *
     * @param id the id of the freeUnit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/free-units/{id}")
    @Timed
    public ResponseEntity<Void> deleteFreeUnit(@PathVariable Long id) {
        log.debug("REST request to delete FreeUnit : {}", id);
        freeUnitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

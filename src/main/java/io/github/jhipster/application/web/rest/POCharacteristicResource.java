package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.POCharacteristic;
import io.github.jhipster.application.service.POCharacteristicService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.POCharacteristicCriteria;
import io.github.jhipster.application.service.POCharacteristicQueryService;
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
 * REST controller for managing POCharacteristic.
 */
@RestController
@RequestMapping("/api")
public class POCharacteristicResource {

    private final Logger log = LoggerFactory.getLogger(POCharacteristicResource.class);

    private static final String ENTITY_NAME = "pOCharacteristic";

    private final POCharacteristicService pOCharacteristicService;

    private final POCharacteristicQueryService pOCharacteristicQueryService;

    public POCharacteristicResource(POCharacteristicService pOCharacteristicService, POCharacteristicQueryService pOCharacteristicQueryService) {
        this.pOCharacteristicService = pOCharacteristicService;
        this.pOCharacteristicQueryService = pOCharacteristicQueryService;
    }

    /**
     * POST  /po-characteristics : Create a new pOCharacteristic.
     *
     * @param pOCharacteristic the pOCharacteristic to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pOCharacteristic, or with status 400 (Bad Request) if the pOCharacteristic has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/po-characteristics")
    @Timed
    public ResponseEntity<POCharacteristic> createPOCharacteristic(@RequestBody POCharacteristic pOCharacteristic) throws URISyntaxException {
        log.debug("REST request to save POCharacteristic : {}", pOCharacteristic);
        if (pOCharacteristic.getId() != null) {
            throw new BadRequestAlertException("A new pOCharacteristic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        POCharacteristic result = pOCharacteristicService.save(pOCharacteristic);
        return ResponseEntity.created(new URI("/api/po-characteristics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /po-characteristics : Updates an existing pOCharacteristic.
     *
     * @param pOCharacteristic the pOCharacteristic to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pOCharacteristic,
     * or with status 400 (Bad Request) if the pOCharacteristic is not valid,
     * or with status 500 (Internal Server Error) if the pOCharacteristic couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/po-characteristics")
    @Timed
    public ResponseEntity<POCharacteristic> updatePOCharacteristic(@RequestBody POCharacteristic pOCharacteristic) throws URISyntaxException {
        log.debug("REST request to update POCharacteristic : {}", pOCharacteristic);
        if (pOCharacteristic.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        POCharacteristic result = pOCharacteristicService.save(pOCharacteristic);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pOCharacteristic.getId().toString()))
            .body(result);
    }

    /**
     * GET  /po-characteristics : get all the pOCharacteristics.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of pOCharacteristics in body
     */
    @GetMapping("/po-characteristics")
    @Timed
    public ResponseEntity<List<POCharacteristic>> getAllPOCharacteristics(POCharacteristicCriteria criteria, Pageable pageable) {
        log.debug("REST request to get POCharacteristics by criteria: {}", criteria);
        Page<POCharacteristic> page = pOCharacteristicQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/po-characteristics");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /po-characteristics/count : count all the pOCharacteristics.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/po-characteristics/count")
    @Timed
    public ResponseEntity<Long> countPOCharacteristics(POCharacteristicCriteria criteria) {
        log.debug("REST request to count POCharacteristics by criteria: {}", criteria);
        return ResponseEntity.ok().body(pOCharacteristicQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /po-characteristics/:id : get the "id" pOCharacteristic.
     *
     * @param id the id of the pOCharacteristic to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pOCharacteristic, or with status 404 (Not Found)
     */
    @GetMapping("/po-characteristics/{id}")
    @Timed
    public ResponseEntity<POCharacteristic> getPOCharacteristic(@PathVariable Long id) {
        log.debug("REST request to get POCharacteristic : {}", id);
        Optional<POCharacteristic> pOCharacteristic = pOCharacteristicService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pOCharacteristic);
    }

    /**
     * DELETE  /po-characteristics/:id : delete the "id" pOCharacteristic.
     *
     * @param id the id of the pOCharacteristic to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/po-characteristics/{id}")
    @Timed
    public ResponseEntity<Void> deletePOCharacteristic(@PathVariable Long id) {
        log.debug("REST request to delete POCharacteristic : {}", id);
        pOCharacteristicService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

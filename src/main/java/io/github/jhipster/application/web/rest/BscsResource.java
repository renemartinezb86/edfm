package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Bscs;
import io.github.jhipster.application.service.BscsService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.BscsCriteria;
import io.github.jhipster.application.service.BscsQueryService;
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
 * REST controller for managing Bscs.
 */
@RestController
@RequestMapping("/api")
public class BscsResource {

    private final Logger log = LoggerFactory.getLogger(BscsResource.class);

    private static final String ENTITY_NAME = "bscs";

    private final BscsService bscsService;

    private final BscsQueryService bscsQueryService;

    public BscsResource(BscsService bscsService, BscsQueryService bscsQueryService) {
        this.bscsService = bscsService;
        this.bscsQueryService = bscsQueryService;
    }

    /**
     * POST  /bscs : Create a new bscs.
     *
     * @param bscs the bscs to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bscs, or with status 400 (Bad Request) if the bscs has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bscs")
    @Timed
    public ResponseEntity<Bscs> createBscs(@RequestBody Bscs bscs) throws URISyntaxException {
        log.debug("REST request to save Bscs : {}", bscs);
        if (bscs.getId() != null) {
            throw new BadRequestAlertException("A new bscs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bscs result = bscsService.save(bscs);
        return ResponseEntity.created(new URI("/api/bscs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bscs : Updates an existing bscs.
     *
     * @param bscs the bscs to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bscs,
     * or with status 400 (Bad Request) if the bscs is not valid,
     * or with status 500 (Internal Server Error) if the bscs couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bscs")
    @Timed
    public ResponseEntity<Bscs> updateBscs(@RequestBody Bscs bscs) throws URISyntaxException {
        log.debug("REST request to update Bscs : {}", bscs);
        if (bscs.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Bscs result = bscsService.save(bscs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bscs.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bscs : get all the bscs.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of bscs in body
     */
    @GetMapping("/bscs")
    @Timed
    public ResponseEntity<List<Bscs>> getAllBscs(BscsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Bscs by criteria: {}", criteria);
        Page<Bscs> page = bscsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bscs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /bscs/count : count all the bscs.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/bscs/count")
    @Timed
    public ResponseEntity<Long> countBscs(BscsCriteria criteria) {
        log.debug("REST request to count Bscs by criteria: {}", criteria);
        return ResponseEntity.ok().body(bscsQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /bscs/:id : get the "id" bscs.
     *
     * @param id the id of the bscs to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bscs, or with status 404 (Not Found)
     */
    @GetMapping("/bscs/{id}")
    @Timed
    public ResponseEntity<Bscs> getBscs(@PathVariable Long id) {
        log.debug("REST request to get Bscs : {}", id);
        Optional<Bscs> bscs = bscsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bscs);
    }

    /**
     * DELETE  /bscs/:id : delete the "id" bscs.
     *
     * @param id the id of the bscs to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bscs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBscs(@PathVariable Long id) {
        log.debug("REST request to delete Bscs : {}", id);
        bscsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

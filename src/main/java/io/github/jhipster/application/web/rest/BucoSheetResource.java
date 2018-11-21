package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.BucoSheet;
import io.github.jhipster.application.service.BucoSheetService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.BucoSheetCriteria;
import io.github.jhipster.application.service.BucoSheetQueryService;
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
 * REST controller for managing BucoSheet.
 */
@RestController
@RequestMapping("/api")
public class BucoSheetResource {

    private final Logger log = LoggerFactory.getLogger(BucoSheetResource.class);

    private static final String ENTITY_NAME = "bucoSheet";

    private final BucoSheetService bucoSheetService;

    private final BucoSheetQueryService bucoSheetQueryService;

    public BucoSheetResource(BucoSheetService bucoSheetService, BucoSheetQueryService bucoSheetQueryService) {
        this.bucoSheetService = bucoSheetService;
        this.bucoSheetQueryService = bucoSheetQueryService;
    }

    /**
     * POST  /buco-sheets : Create a new bucoSheet.
     *
     * @param bucoSheet the bucoSheet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bucoSheet, or with status 400 (Bad Request) if the bucoSheet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/buco-sheets")
    @Timed
    public ResponseEntity<BucoSheet> createBucoSheet(@RequestBody BucoSheet bucoSheet) throws URISyntaxException {
        log.debug("REST request to save BucoSheet : {}", bucoSheet);
        if (bucoSheet.getId() != null) {
            throw new BadRequestAlertException("A new bucoSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BucoSheet result = bucoSheetService.save(bucoSheet);
        return ResponseEntity.created(new URI("/api/buco-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buco-sheets : Updates an existing bucoSheet.
     *
     * @param bucoSheet the bucoSheet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bucoSheet,
     * or with status 400 (Bad Request) if the bucoSheet is not valid,
     * or with status 500 (Internal Server Error) if the bucoSheet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/buco-sheets")
    @Timed
    public ResponseEntity<BucoSheet> updateBucoSheet(@RequestBody BucoSheet bucoSheet) throws URISyntaxException {
        log.debug("REST request to update BucoSheet : {}", bucoSheet);
        if (bucoSheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BucoSheet result = bucoSheetService.save(bucoSheet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bucoSheet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buco-sheets : get all the bucoSheets.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of bucoSheets in body
     */
    @GetMapping("/buco-sheets")
    @Timed
    public ResponseEntity<List<BucoSheet>> getAllBucoSheets(BucoSheetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BucoSheets by criteria: {}", criteria);
        Page<BucoSheet> page = bucoSheetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/buco-sheets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /buco-sheets/count : count all the bucoSheets.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/buco-sheets/count")
    @Timed
    public ResponseEntity<Long> countBucoSheets(BucoSheetCriteria criteria) {
        log.debug("REST request to count BucoSheets by criteria: {}", criteria);
        return ResponseEntity.ok().body(bucoSheetQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /buco-sheets/:id : get the "id" bucoSheet.
     *
     * @param id the id of the bucoSheet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bucoSheet, or with status 404 (Not Found)
     */
    @GetMapping("/buco-sheets/{id}")
    @Timed
    public ResponseEntity<BucoSheet> getBucoSheet(@PathVariable Long id) {
        log.debug("REST request to get BucoSheet : {}", id);
        Optional<BucoSheet> bucoSheet = bucoSheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bucoSheet);
    }

    /**
     * DELETE  /buco-sheets/:id : delete the "id" bucoSheet.
     *
     * @param id the id of the bucoSheet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/buco-sheets/{id}")
    @Timed
    public ResponseEntity<Void> deleteBucoSheet(@PathVariable Long id) {
        log.debug("REST request to delete BucoSheet : {}", id);
        bucoSheetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.CfssPop;
import io.github.jhipster.application.service.CfssPopService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.CfssPopCriteria;
import io.github.jhipster.application.service.CfssPopQueryService;
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
 * REST controller for managing CfssPop.
 */
@RestController
@RequestMapping("/api")
public class CfssPopResource {

    private final Logger log = LoggerFactory.getLogger(CfssPopResource.class);

    private static final String ENTITY_NAME = "cfssPop";

    private final CfssPopService cfssPopService;

    private final CfssPopQueryService cfssPopQueryService;

    public CfssPopResource(CfssPopService cfssPopService, CfssPopQueryService cfssPopQueryService) {
        this.cfssPopService = cfssPopService;
        this.cfssPopQueryService = cfssPopQueryService;
    }

    /**
     * POST  /cfss-pops : Create a new cfssPop.
     *
     * @param cfssPop the cfssPop to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfssPop, or with status 400 (Bad Request) if the cfssPop has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cfss-pops")
    @Timed
    public ResponseEntity<CfssPop> createCfssPop(@RequestBody CfssPop cfssPop) throws URISyntaxException {
        log.debug("REST request to save CfssPop : {}", cfssPop);
        if (cfssPop.getId() != null) {
            throw new BadRequestAlertException("A new cfssPop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CfssPop result = cfssPopService.save(cfssPop);
        return ResponseEntity.created(new URI("/api/cfss-pops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfss-pops : Updates an existing cfssPop.
     *
     * @param cfssPop the cfssPop to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfssPop,
     * or with status 400 (Bad Request) if the cfssPop is not valid,
     * or with status 500 (Internal Server Error) if the cfssPop couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cfss-pops")
    @Timed
    public ResponseEntity<CfssPop> updateCfssPop(@RequestBody CfssPop cfssPop) throws URISyntaxException {
        log.debug("REST request to update CfssPop : {}", cfssPop);
        if (cfssPop.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CfssPop result = cfssPopService.save(cfssPop);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cfssPop.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cfss-pops : get all the cfssPops.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of cfssPops in body
     */
    @GetMapping("/cfss-pops")
    @Timed
    public ResponseEntity<List<CfssPop>> getAllCfssPops(CfssPopCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CfssPops by criteria: {}", criteria);
        Page<CfssPop> page = cfssPopQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cfss-pops");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /cfss-pops/count : count all the cfssPops.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/cfss-pops/count")
    @Timed
    public ResponseEntity<Long> countCfssPops(CfssPopCriteria criteria) {
        log.debug("REST request to count CfssPops by criteria: {}", criteria);
        return ResponseEntity.ok().body(cfssPopQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /cfss-pops/:id : get the "id" cfssPop.
     *
     * @param id the id of the cfssPop to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cfssPop, or with status 404 (Not Found)
     */
    @GetMapping("/cfss-pops/{id}")
    @Timed
    public ResponseEntity<CfssPop> getCfssPop(@PathVariable Long id) {
        log.debug("REST request to get CfssPop : {}", id);
        Optional<CfssPop> cfssPop = cfssPopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cfssPop);
    }

    /**
     * DELETE  /cfss-pops/:id : delete the "id" cfssPop.
     *
     * @param id the id of the cfssPop to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cfss-pops/{id}")
    @Timed
    public ResponseEntity<Void> deleteCfssPop(@PathVariable Long id) {
        log.debug("REST request to delete CfssPop : {}", id);
        cfssPopService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

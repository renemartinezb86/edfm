package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.PoPrice;
import io.github.jhipster.application.service.PoPriceService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.PoPriceCriteria;
import io.github.jhipster.application.service.PoPriceQueryService;
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
 * REST controller for managing PoPrice.
 */
@RestController
@RequestMapping("/api")
public class PoPriceResource {

    private final Logger log = LoggerFactory.getLogger(PoPriceResource.class);

    private static final String ENTITY_NAME = "poPrice";

    private final PoPriceService poPriceService;

    private final PoPriceQueryService poPriceQueryService;

    public PoPriceResource(PoPriceService poPriceService, PoPriceQueryService poPriceQueryService) {
        this.poPriceService = poPriceService;
        this.poPriceQueryService = poPriceQueryService;
    }

    /**
     * POST  /po-prices : Create a new poPrice.
     *
     * @param poPrice the poPrice to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poPrice, or with status 400 (Bad Request) if the poPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/po-prices")
    @Timed
    public ResponseEntity<PoPrice> createPoPrice(@RequestBody PoPrice poPrice) throws URISyntaxException {
        log.debug("REST request to save PoPrice : {}", poPrice);
        if (poPrice.getId() != null) {
            throw new BadRequestAlertException("A new poPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoPrice result = poPriceService.save(poPrice);
        return ResponseEntity.created(new URI("/api/po-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /po-prices : Updates an existing poPrice.
     *
     * @param poPrice the poPrice to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poPrice,
     * or with status 400 (Bad Request) if the poPrice is not valid,
     * or with status 500 (Internal Server Error) if the poPrice couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/po-prices")
    @Timed
    public ResponseEntity<PoPrice> updatePoPrice(@RequestBody PoPrice poPrice) throws URISyntaxException {
        log.debug("REST request to update PoPrice : {}", poPrice);
        if (poPrice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoPrice result = poPriceService.save(poPrice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, poPrice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /po-prices : get all the poPrices.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of poPrices in body
     */
    @GetMapping("/po-prices")
    @Timed
    public ResponseEntity<List<PoPrice>> getAllPoPrices(PoPriceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PoPrices by criteria: {}", criteria);
        Page<PoPrice> page = poPriceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/po-prices");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /po-prices/count : count all the poPrices.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/po-prices/count")
    @Timed
    public ResponseEntity<Long> countPoPrices(PoPriceCriteria criteria) {
        log.debug("REST request to count PoPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(poPriceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /po-prices/:id : get the "id" poPrice.
     *
     * @param id the id of the poPrice to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poPrice, or with status 404 (Not Found)
     */
    @GetMapping("/po-prices/{id}")
    @Timed
    public ResponseEntity<PoPrice> getPoPrice(@PathVariable Long id) {
        log.debug("REST request to get PoPrice : {}", id);
        Optional<PoPrice> poPrice = poPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(poPrice);
    }

    /**
     * DELETE  /po-prices/:id : delete the "id" poPrice.
     *
     * @param id the id of the poPrice to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/po-prices/{id}")
    @Timed
    public ResponseEntity<Void> deletePoPrice(@PathVariable Long id) {
        log.debug("REST request to delete PoPrice : {}", id);
        poPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

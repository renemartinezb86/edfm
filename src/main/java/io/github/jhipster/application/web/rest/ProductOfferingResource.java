package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.service.ProductOfferingService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ProductOfferingCriteria;
import io.github.jhipster.application.service.ProductOfferingQueryService;
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
 * REST controller for managing ProductOffering.
 */
@RestController
@RequestMapping("/api")
public class ProductOfferingResource {

    private final Logger log = LoggerFactory.getLogger(ProductOfferingResource.class);

    private static final String ENTITY_NAME = "productOffering";

    private final ProductOfferingService productOfferingService;

    private final ProductOfferingQueryService productOfferingQueryService;

    public ProductOfferingResource(ProductOfferingService productOfferingService, ProductOfferingQueryService productOfferingQueryService) {
        this.productOfferingService = productOfferingService;
        this.productOfferingQueryService = productOfferingQueryService;
    }

    /**
     * POST  /product-offerings : Create a new productOffering.
     *
     * @param productOffering the productOffering to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productOffering, or with status 400 (Bad Request) if the productOffering has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/product-offerings")
    @Timed
    public ResponseEntity<ProductOffering> createProductOffering(@RequestBody ProductOffering productOffering) throws URISyntaxException {
        log.debug("REST request to save ProductOffering : {}", productOffering);
        if (productOffering.getId() != null) {
            throw new BadRequestAlertException("A new productOffering cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductOffering result = productOfferingService.save(productOffering);
        return ResponseEntity.created(new URI("/api/product-offerings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /product-offerings : Updates an existing productOffering.
     *
     * @param productOffering the productOffering to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productOffering,
     * or with status 400 (Bad Request) if the productOffering is not valid,
     * or with status 500 (Internal Server Error) if the productOffering couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/product-offerings")
    @Timed
    public ResponseEntity<ProductOffering> updateProductOffering(@RequestBody ProductOffering productOffering) throws URISyntaxException {
        log.debug("REST request to update ProductOffering : {}", productOffering);
        if (productOffering.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductOffering result = productOfferingService.save(productOffering);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productOffering.getId().toString()))
            .body(result);
    }

    /**
     * GET  /product-offerings : get all the productOfferings.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of productOfferings in body
     */
    @GetMapping("/product-offerings")
    @Timed
    public ResponseEntity<List<ProductOffering>> getAllProductOfferings(ProductOfferingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProductOfferings by criteria: {}", criteria);
        Page<ProductOffering> page = productOfferingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/product-offerings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /product-offerings/count : count all the productOfferings.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/product-offerings/count")
    @Timed
    public ResponseEntity<Long> countProductOfferings(ProductOfferingCriteria criteria) {
        log.debug("REST request to count ProductOfferings by criteria: {}", criteria);
        return ResponseEntity.ok().body(productOfferingQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /product-offerings/:id : get the "id" productOffering.
     *
     * @param id the id of the productOffering to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productOffering, or with status 404 (Not Found)
     */
    @GetMapping("/product-offerings/{id}")
    @Timed
    public ResponseEntity<ProductOffering> getProductOffering(@PathVariable Long id) {
        log.debug("REST request to get ProductOffering : {}", id);
        Optional<ProductOffering> productOffering = productOfferingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productOffering);
    }

    /**
     * DELETE  /product-offerings/:id : delete the "id" productOffering.
     *
     * @param id the id of the productOffering to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/product-offerings/{id}")
    @Timed
    public ResponseEntity<Void> deleteProductOffering(@PathVariable Long id) {
        log.debug("REST request to delete ProductOffering : {}", id);
        productOfferingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

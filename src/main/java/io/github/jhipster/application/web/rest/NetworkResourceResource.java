package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.NetworkResource;
import io.github.jhipster.application.service.NetworkResourceService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.NetworkResourceCriteria;
import io.github.jhipster.application.service.NetworkResourceQueryService;
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
 * REST controller for managing NetworkResource.
 */
@RestController
@RequestMapping("/api")
public class NetworkResourceResource {

    private final Logger log = LoggerFactory.getLogger(NetworkResourceResource.class);

    private static final String ENTITY_NAME = "networkResource";

    private final NetworkResourceService networkResourceService;

    private final NetworkResourceQueryService networkResourceQueryService;

    public NetworkResourceResource(NetworkResourceService networkResourceService, NetworkResourceQueryService networkResourceQueryService) {
        this.networkResourceService = networkResourceService;
        this.networkResourceQueryService = networkResourceQueryService;
    }

    /**
     * POST  /network-resources : Create a new networkResource.
     *
     * @param networkResource the networkResource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new networkResource, or with status 400 (Bad Request) if the networkResource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/network-resources")
    @Timed
    public ResponseEntity<NetworkResource> createNetworkResource(@RequestBody NetworkResource networkResource) throws URISyntaxException {
        log.debug("REST request to save NetworkResource : {}", networkResource);
        if (networkResource.getId() != null) {
            throw new BadRequestAlertException("A new networkResource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetworkResource result = networkResourceService.save(networkResource);
        return ResponseEntity.created(new URI("/api/network-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /network-resources : Updates an existing networkResource.
     *
     * @param networkResource the networkResource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated networkResource,
     * or with status 400 (Bad Request) if the networkResource is not valid,
     * or with status 500 (Internal Server Error) if the networkResource couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/network-resources")
    @Timed
    public ResponseEntity<NetworkResource> updateNetworkResource(@RequestBody NetworkResource networkResource) throws URISyntaxException {
        log.debug("REST request to update NetworkResource : {}", networkResource);
        if (networkResource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NetworkResource result = networkResourceService.save(networkResource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, networkResource.getId().toString()))
            .body(result);
    }

    /**
     * GET  /network-resources : get all the networkResources.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of networkResources in body
     */
    @GetMapping("/network-resources")
    @Timed
    public ResponseEntity<List<NetworkResource>> getAllNetworkResources(NetworkResourceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NetworkResources by criteria: {}", criteria);
        Page<NetworkResource> page = networkResourceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/network-resources");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /network-resources/count : count all the networkResources.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/network-resources/count")
    @Timed
    public ResponseEntity<Long> countNetworkResources(NetworkResourceCriteria criteria) {
        log.debug("REST request to count NetworkResources by criteria: {}", criteria);
        return ResponseEntity.ok().body(networkResourceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /network-resources/:id : get the "id" networkResource.
     *
     * @param id the id of the networkResource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the networkResource, or with status 404 (Not Found)
     */
    @GetMapping("/network-resources/{id}")
    @Timed
    public ResponseEntity<NetworkResource> getNetworkResource(@PathVariable Long id) {
        log.debug("REST request to get NetworkResource : {}", id);
        Optional<NetworkResource> networkResource = networkResourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(networkResource);
    }

    /**
     * DELETE  /network-resources/:id : delete the "id" networkResource.
     *
     * @param id the id of the networkResource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/network-resources/{id}")
    @Timed
    public ResponseEntity<Void> deleteNetworkResource(@PathVariable Long id) {
        log.debug("REST request to delete NetworkResource : {}", id);
        networkResourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

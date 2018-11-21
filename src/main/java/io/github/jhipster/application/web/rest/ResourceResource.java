package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Resource;
import io.github.jhipster.application.service.ResourceService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ResourceCriteria;
import io.github.jhipster.application.service.ResourceQueryService;
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
 * REST controller for managing Resource.
 */
@RestController
@RequestMapping("/api")
public class ResourceResource {

    private final Logger log = LoggerFactory.getLogger(ResourceResource.class);

    private static final String ENTITY_NAME = "resource";

    private final ResourceService resourceService;

    private final ResourceQueryService resourceQueryService;

    public ResourceResource(ResourceService resourceService, ResourceQueryService resourceQueryService) {
        this.resourceService = resourceService;
        this.resourceQueryService = resourceQueryService;
    }

    /**
     * POST  /resources : Create a new resource.
     *
     * @param resource the resource to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resource, or with status 400 (Bad Request) if the resource has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/resources")
    @Timed
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) throws URISyntaxException {
        log.debug("REST request to save Resource : {}", resource);
        if (resource.getId() != null) {
            throw new BadRequestAlertException("A new resource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resource result = resourceService.save(resource);
        return ResponseEntity.created(new URI("/api/resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resources : Updates an existing resource.
     *
     * @param resource the resource to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resource,
     * or with status 400 (Bad Request) if the resource is not valid,
     * or with status 500 (Internal Server Error) if the resource couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/resources")
    @Timed
    public ResponseEntity<Resource> updateResource(@RequestBody Resource resource) throws URISyntaxException {
        log.debug("REST request to update Resource : {}", resource);
        if (resource.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Resource result = resourceService.save(resource);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, resource.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resources : get all the resources.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of resources in body
     */
    @GetMapping("/resources")
    @Timed
    public ResponseEntity<List<Resource>> getAllResources(ResourceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Resources by criteria: {}", criteria);
        Page<Resource> page = resourceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resources");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /resources/count : count all the resources.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/resources/count")
    @Timed
    public ResponseEntity<Long> countResources(ResourceCriteria criteria) {
        log.debug("REST request to count Resources by criteria: {}", criteria);
        return ResponseEntity.ok().body(resourceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /resources/:id : get the "id" resource.
     *
     * @param id the id of the resource to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resource, or with status 404 (Not Found)
     */
    @GetMapping("/resources/{id}")
    @Timed
    public ResponseEntity<Resource> getResource(@PathVariable Long id) {
        log.debug("REST request to get Resource : {}", id);
        Optional<Resource> resource = resourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resource);
    }

    /**
     * DELETE  /resources/:id : delete the "id" resource.
     *
     * @param id the id of the resource to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/resources/{id}")
    @Timed
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        log.debug("REST request to delete Resource : {}", id);
        resourceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

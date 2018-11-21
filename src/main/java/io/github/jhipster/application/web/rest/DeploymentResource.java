package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Deployment;
import io.github.jhipster.application.service.DeploymentService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.DeploymentCriteria;
import io.github.jhipster.application.service.DeploymentQueryService;
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
 * REST controller for managing Deployment.
 */
@RestController
@RequestMapping("/api")
public class DeploymentResource {

    private final Logger log = LoggerFactory.getLogger(DeploymentResource.class);

    private static final String ENTITY_NAME = "deployment";

    private final DeploymentService deploymentService;

    private final DeploymentQueryService deploymentQueryService;

    public DeploymentResource(DeploymentService deploymentService, DeploymentQueryService deploymentQueryService) {
        this.deploymentService = deploymentService;
        this.deploymentQueryService = deploymentQueryService;
    }

    /**
     * POST  /deployments : Create a new deployment.
     *
     * @param deployment the deployment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deployment, or with status 400 (Bad Request) if the deployment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deployments")
    @Timed
    public ResponseEntity<Deployment> createDeployment(@RequestBody Deployment deployment) throws URISyntaxException {
        log.debug("REST request to save Deployment : {}", deployment);
        if (deployment.getId() != null) {
            throw new BadRequestAlertException("A new deployment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Deployment result = deploymentService.save(deployment);
        return ResponseEntity.created(new URI("/api/deployments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deployments : Updates an existing deployment.
     *
     * @param deployment the deployment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deployment,
     * or with status 400 (Bad Request) if the deployment is not valid,
     * or with status 500 (Internal Server Error) if the deployment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deployments")
    @Timed
    public ResponseEntity<Deployment> updateDeployment(@RequestBody Deployment deployment) throws URISyntaxException {
        log.debug("REST request to update Deployment : {}", deployment);
        if (deployment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Deployment result = deploymentService.save(deployment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deployment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deployments : get all the deployments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of deployments in body
     */
    @GetMapping("/deployments")
    @Timed
    public ResponseEntity<List<Deployment>> getAllDeployments(DeploymentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Deployments by criteria: {}", criteria);
        Page<Deployment> page = deploymentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deployments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /deployments/count : count all the deployments.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/deployments/count")
    @Timed
    public ResponseEntity<Long> countDeployments(DeploymentCriteria criteria) {
        log.debug("REST request to count Deployments by criteria: {}", criteria);
        return ResponseEntity.ok().body(deploymentQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /deployments/:id : get the "id" deployment.
     *
     * @param id the id of the deployment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deployment, or with status 404 (Not Found)
     */
    @GetMapping("/deployments/{id}")
    @Timed
    public ResponseEntity<Deployment> getDeployment(@PathVariable Long id) {
        log.debug("REST request to get Deployment : {}", id);
        Optional<Deployment> deployment = deploymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deployment);
    }

    /**
     * DELETE  /deployments/:id : delete the "id" deployment.
     *
     * @param id the id of the deployment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deployments/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeployment(@PathVariable Long id) {
        log.debug("REST request to delete Deployment : {}", id);
        deploymentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

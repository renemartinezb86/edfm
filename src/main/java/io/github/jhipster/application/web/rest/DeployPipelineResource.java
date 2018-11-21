package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DeployPipeline;
import io.github.jhipster.application.repository.DeployPipelineRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DeployPipeline.
 */
@RestController
@RequestMapping("/api")
public class DeployPipelineResource {

    private final Logger log = LoggerFactory.getLogger(DeployPipelineResource.class);

    private static final String ENTITY_NAME = "deployPipeline";

    private final DeployPipelineRepository deployPipelineRepository;

    public DeployPipelineResource(DeployPipelineRepository deployPipelineRepository) {
        this.deployPipelineRepository = deployPipelineRepository;
    }

    /**
     * POST  /deploy-pipelines : Create a new deployPipeline.
     *
     * @param deployPipeline the deployPipeline to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deployPipeline, or with status 400 (Bad Request) if the deployPipeline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deploy-pipelines")
    @Timed
    public ResponseEntity<DeployPipeline> createDeployPipeline(@RequestBody DeployPipeline deployPipeline) throws URISyntaxException {
        log.debug("REST request to save DeployPipeline : {}", deployPipeline);
        if (deployPipeline.getId() != null) {
            throw new BadRequestAlertException("A new deployPipeline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeployPipeline result = deployPipelineRepository.save(deployPipeline);
        return ResponseEntity.created(new URI("/api/deploy-pipelines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deploy-pipelines : Updates an existing deployPipeline.
     *
     * @param deployPipeline the deployPipeline to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deployPipeline,
     * or with status 400 (Bad Request) if the deployPipeline is not valid,
     * or with status 500 (Internal Server Error) if the deployPipeline couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deploy-pipelines")
    @Timed
    public ResponseEntity<DeployPipeline> updateDeployPipeline(@RequestBody DeployPipeline deployPipeline) throws URISyntaxException {
        log.debug("REST request to update DeployPipeline : {}", deployPipeline);
        if (deployPipeline.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeployPipeline result = deployPipelineRepository.save(deployPipeline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deployPipeline.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deploy-pipelines : get all the deployPipelines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deployPipelines in body
     */
    @GetMapping("/deploy-pipelines")
    @Timed
    public List<DeployPipeline> getAllDeployPipelines() {
        log.debug("REST request to get all DeployPipelines");
        return deployPipelineRepository.findAll();
    }

    /**
     * GET  /deploy-pipelines/:id : get the "id" deployPipeline.
     *
     * @param id the id of the deployPipeline to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deployPipeline, or with status 404 (Not Found)
     */
    @GetMapping("/deploy-pipelines/{id}")
    @Timed
    public ResponseEntity<DeployPipeline> getDeployPipeline(@PathVariable Long id) {
        log.debug("REST request to get DeployPipeline : {}", id);
        Optional<DeployPipeline> deployPipeline = deployPipelineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deployPipeline);
    }

    /**
     * DELETE  /deploy-pipelines/:id : delete the "id" deployPipeline.
     *
     * @param id the id of the deployPipeline to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deploy-pipelines/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeployPipeline(@PathVariable Long id) {
        log.debug("REST request to delete DeployPipeline : {}", id);

        deployPipelineRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

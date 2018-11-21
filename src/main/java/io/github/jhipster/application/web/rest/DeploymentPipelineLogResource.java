package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DeploymentPipelineLog;
import io.github.jhipster.application.repository.DeploymentPipelineLogRepository;
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
 * REST controller for managing DeploymentPipelineLog.
 */
@RestController
@RequestMapping("/api")
public class DeploymentPipelineLogResource {

    private final Logger log = LoggerFactory.getLogger(DeploymentPipelineLogResource.class);

    private static final String ENTITY_NAME = "deploymentPipelineLog";

    private final DeploymentPipelineLogRepository deploymentPipelineLogRepository;

    public DeploymentPipelineLogResource(DeploymentPipelineLogRepository deploymentPipelineLogRepository) {
        this.deploymentPipelineLogRepository = deploymentPipelineLogRepository;
    }

    /**
     * POST  /deployment-pipeline-logs : Create a new deploymentPipelineLog.
     *
     * @param deploymentPipelineLog the deploymentPipelineLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deploymentPipelineLog, or with status 400 (Bad Request) if the deploymentPipelineLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deployment-pipeline-logs")
    @Timed
    public ResponseEntity<DeploymentPipelineLog> createDeploymentPipelineLog(@RequestBody DeploymentPipelineLog deploymentPipelineLog) throws URISyntaxException {
        log.debug("REST request to save DeploymentPipelineLog : {}", deploymentPipelineLog);
        if (deploymentPipelineLog.getId() != null) {
            throw new BadRequestAlertException("A new deploymentPipelineLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeploymentPipelineLog result = deploymentPipelineLogRepository.save(deploymentPipelineLog);
        return ResponseEntity.created(new URI("/api/deployment-pipeline-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deployment-pipeline-logs : Updates an existing deploymentPipelineLog.
     *
     * @param deploymentPipelineLog the deploymentPipelineLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deploymentPipelineLog,
     * or with status 400 (Bad Request) if the deploymentPipelineLog is not valid,
     * or with status 500 (Internal Server Error) if the deploymentPipelineLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deployment-pipeline-logs")
    @Timed
    public ResponseEntity<DeploymentPipelineLog> updateDeploymentPipelineLog(@RequestBody DeploymentPipelineLog deploymentPipelineLog) throws URISyntaxException {
        log.debug("REST request to update DeploymentPipelineLog : {}", deploymentPipelineLog);
        if (deploymentPipelineLog.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeploymentPipelineLog result = deploymentPipelineLogRepository.save(deploymentPipelineLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deploymentPipelineLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deployment-pipeline-logs : get all the deploymentPipelineLogs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deploymentPipelineLogs in body
     */
    @GetMapping("/deployment-pipeline-logs")
    @Timed
    public List<DeploymentPipelineLog> getAllDeploymentPipelineLogs() {
        log.debug("REST request to get all DeploymentPipelineLogs");
        return deploymentPipelineLogRepository.findAll();
    }

    /**
     * GET  /deployment-pipeline-logs/:id : get the "id" deploymentPipelineLog.
     *
     * @param id the id of the deploymentPipelineLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deploymentPipelineLog, or with status 404 (Not Found)
     */
    @GetMapping("/deployment-pipeline-logs/{id}")
    @Timed
    public ResponseEntity<DeploymentPipelineLog> getDeploymentPipelineLog(@PathVariable Long id) {
        log.debug("REST request to get DeploymentPipelineLog : {}", id);
        Optional<DeploymentPipelineLog> deploymentPipelineLog = deploymentPipelineLogRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deploymentPipelineLog);
    }

    /**
     * DELETE  /deployment-pipeline-logs/:id : delete the "id" deploymentPipelineLog.
     *
     * @param id the id of the deploymentPipelineLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deployment-pipeline-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeploymentPipelineLog(@PathVariable Long id) {
        log.debug("REST request to delete DeploymentPipelineLog : {}", id);

        deploymentPipelineLogRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

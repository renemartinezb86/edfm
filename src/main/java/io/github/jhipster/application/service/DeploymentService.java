package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Deployment;
import io.github.jhipster.application.repository.DeploymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Deployment.
 */
@Service
@Transactional
public class DeploymentService {

    private final Logger log = LoggerFactory.getLogger(DeploymentService.class);

    private final DeploymentRepository deploymentRepository;

    public DeploymentService(DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
    }

    /**
     * Save a deployment.
     *
     * @param deployment the entity to save
     * @return the persisted entity
     */
    public Deployment save(Deployment deployment) {
        log.debug("Request to save Deployment : {}", deployment);
        return deploymentRepository.save(deployment);
    }

    /**
     * Get all the deployments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Deployment> findAll(Pageable pageable) {
        log.debug("Request to get all Deployments");
        return deploymentRepository.findAll(pageable);
    }


    /**
     * Get one deployment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Deployment> findOne(Long id) {
        log.debug("Request to get Deployment : {}", id);
        return deploymentRepository.findById(id);
    }

    /**
     * Delete the deployment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Deployment : {}", id);
        deploymentRepository.deleteById(id);
    }
}

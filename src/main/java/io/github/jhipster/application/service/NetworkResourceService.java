package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.NetworkResource;
import io.github.jhipster.application.repository.NetworkResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NetworkResource.
 */
@Service
@Transactional
public class NetworkResourceService {

    private final Logger log = LoggerFactory.getLogger(NetworkResourceService.class);

    private final NetworkResourceRepository networkResourceRepository;

    public NetworkResourceService(NetworkResourceRepository networkResourceRepository) {
        this.networkResourceRepository = networkResourceRepository;
    }

    /**
     * Save a networkResource.
     *
     * @param networkResource the entity to save
     * @return the persisted entity
     */
    public NetworkResource save(NetworkResource networkResource) {
        log.debug("Request to save NetworkResource : {}", networkResource);
        return networkResourceRepository.save(networkResource);
    }

    /**
     * Get all the networkResources.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NetworkResource> findAll(Pageable pageable) {
        log.debug("Request to get all NetworkResources");
        return networkResourceRepository.findAll(pageable);
    }

    /**
     * Get all the NetworkResource with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<NetworkResource> findAllWithEagerRelationships(Pageable pageable) {
        return networkResourceRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one networkResource by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NetworkResource> findOne(Long id) {
        log.debug("Request to get NetworkResource : {}", id);
        return networkResourceRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the networkResource by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NetworkResource : {}", id);
        networkResourceRepository.deleteById(id);
    }
}

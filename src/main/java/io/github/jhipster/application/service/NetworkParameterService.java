package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.NetworkParameter;
import io.github.jhipster.application.repository.NetworkParameterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NetworkParameter.
 */
@Service
@Transactional
public class NetworkParameterService {

    private final Logger log = LoggerFactory.getLogger(NetworkParameterService.class);

    private final NetworkParameterRepository networkParameterRepository;

    public NetworkParameterService(NetworkParameterRepository networkParameterRepository) {
        this.networkParameterRepository = networkParameterRepository;
    }

    /**
     * Save a networkParameter.
     *
     * @param networkParameter the entity to save
     * @return the persisted entity
     */
    public NetworkParameter save(NetworkParameter networkParameter) {
        log.debug("Request to save NetworkParameter : {}", networkParameter);
        return networkParameterRepository.save(networkParameter);
    }

    /**
     * Get all the networkParameters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NetworkParameter> findAll(Pageable pageable) {
        log.debug("Request to get all NetworkParameters");
        return networkParameterRepository.findAll(pageable);
    }


    /**
     * Get one networkParameter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NetworkParameter> findOne(Long id) {
        log.debug("Request to get NetworkParameter : {}", id);
        return networkParameterRepository.findById(id);
    }

    /**
     * Delete the networkParameter by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NetworkParameter : {}", id);
        networkParameterRepository.deleteById(id);
    }
}

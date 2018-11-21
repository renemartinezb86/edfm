package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ChargingSystem;
import io.github.jhipster.application.repository.ChargingSystemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ChargingSystem.
 */
@Service
@Transactional
public class ChargingSystemService {

    private final Logger log = LoggerFactory.getLogger(ChargingSystemService.class);

    private final ChargingSystemRepository chargingSystemRepository;

    public ChargingSystemService(ChargingSystemRepository chargingSystemRepository) {
        this.chargingSystemRepository = chargingSystemRepository;
    }

    /**
     * Save a chargingSystem.
     *
     * @param chargingSystem the entity to save
     * @return the persisted entity
     */
    public ChargingSystem save(ChargingSystem chargingSystem) {
        log.debug("Request to save ChargingSystem : {}", chargingSystem);
        return chargingSystemRepository.save(chargingSystem);
    }

    /**
     * Get all the chargingSystems.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChargingSystem> findAll(Pageable pageable) {
        log.debug("Request to get all ChargingSystems");
        return chargingSystemRepository.findAll(pageable);
    }


    /**
     * Get one chargingSystem by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ChargingSystem> findOne(Long id) {
        log.debug("Request to get ChargingSystem : {}", id);
        return chargingSystemRepository.findById(id);
    }

    /**
     * Delete the chargingSystem by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChargingSystem : {}", id);
        chargingSystemRepository.deleteById(id);
    }
}

package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.POCharacteristic;
import io.github.jhipster.application.repository.POCharacteristicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing POCharacteristic.
 */
@Service
@Transactional
public class POCharacteristicService {

    private final Logger log = LoggerFactory.getLogger(POCharacteristicService.class);

    private final POCharacteristicRepository pOCharacteristicRepository;

    public POCharacteristicService(POCharacteristicRepository pOCharacteristicRepository) {
        this.pOCharacteristicRepository = pOCharacteristicRepository;
    }

    /**
     * Save a pOCharacteristic.
     *
     * @param pOCharacteristic the entity to save
     * @return the persisted entity
     */
    public POCharacteristic save(POCharacteristic pOCharacteristic) {
        log.debug("Request to save POCharacteristic : {}", pOCharacteristic);
        return pOCharacteristicRepository.save(pOCharacteristic);
    }

    /**
     * Get all the pOCharacteristics.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<POCharacteristic> findAll(Pageable pageable) {
        log.debug("Request to get all POCharacteristics");
        return pOCharacteristicRepository.findAll(pageable);
    }


    /**
     * Get one pOCharacteristic by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<POCharacteristic> findOne(Long id) {
        log.debug("Request to get POCharacteristic : {}", id);
        return pOCharacteristicRepository.findById(id);
    }

    /**
     * Delete the pOCharacteristic by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete POCharacteristic : {}", id);
        pOCharacteristicRepository.deleteById(id);
    }
}

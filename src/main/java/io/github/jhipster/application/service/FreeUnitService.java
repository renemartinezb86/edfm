package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.FreeUnit;
import io.github.jhipster.application.repository.FreeUnitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing FreeUnit.
 */
@Service
@Transactional
public class FreeUnitService {

    private final Logger log = LoggerFactory.getLogger(FreeUnitService.class);

    private final FreeUnitRepository freeUnitRepository;

    public FreeUnitService(FreeUnitRepository freeUnitRepository) {
        this.freeUnitRepository = freeUnitRepository;
    }

    /**
     * Save a freeUnit.
     *
     * @param freeUnit the entity to save
     * @return the persisted entity
     */
    public FreeUnit save(FreeUnit freeUnit) {
        log.debug("Request to save FreeUnit : {}", freeUnit);
        return freeUnitRepository.save(freeUnit);
    }

    /**
     * Get all the freeUnits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FreeUnit> findAll(Pageable pageable) {
        log.debug("Request to get all FreeUnits");
        return freeUnitRepository.findAll(pageable);
    }



    /**
     *  get all the freeUnits where ChargingSystem is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<FreeUnit> findAllWhereChargingSystemIsNull() {
        log.debug("Request to get all freeUnits where ChargingSystem is null");
        return StreamSupport
            .stream(freeUnitRepository.findAll().spliterator(), false)
            .filter(freeUnit -> freeUnit.getChargingSystem() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one freeUnit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FreeUnit> findOne(Long id) {
        log.debug("Request to get FreeUnit : {}", id);
        return freeUnitRepository.findById(id);
    }

    /**
     * Delete the freeUnit by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FreeUnit : {}", id);
        freeUnitRepository.deleteById(id);
    }
}

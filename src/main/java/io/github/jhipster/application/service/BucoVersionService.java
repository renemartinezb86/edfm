package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.BucoVersion;
import io.github.jhipster.application.repository.BucoVersionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing BucoVersion.
 */
@Service
@Transactional
public class BucoVersionService {

    private final Logger log = LoggerFactory.getLogger(BucoVersionService.class);

    private final BucoVersionRepository bucoVersionRepository;

    public BucoVersionService(BucoVersionRepository bucoVersionRepository) {
        this.bucoVersionRepository = bucoVersionRepository;
    }

    /**
     * Save a bucoVersion.
     *
     * @param bucoVersion the entity to save
     * @return the persisted entity
     */
    public BucoVersion save(BucoVersion bucoVersion) {
        log.debug("Request to save BucoVersion : {}", bucoVersion);
        return bucoVersionRepository.save(bucoVersion);
    }

    /**
     * Get all the bucoVersions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BucoVersion> findAll(Pageable pageable) {
        log.debug("Request to get all BucoVersions");
        return bucoVersionRepository.findAll(pageable);
    }


    /**
     * Get one bucoVersion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BucoVersion> findOne(Long id) {
        log.debug("Request to get BucoVersion : {}", id);
        return bucoVersionRepository.findById(id);
    }

    /**
     * Delete the bucoVersion by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BucoVersion : {}", id);
        bucoVersionRepository.deleteById(id);
    }
}

package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Bscs;
import io.github.jhipster.application.repository.BscsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Bscs.
 */
@Service
@Transactional
public class BscsService {

    private final Logger log = LoggerFactory.getLogger(BscsService.class);

    private final BscsRepository bscsRepository;

    public BscsService(BscsRepository bscsRepository) {
        this.bscsRepository = bscsRepository;
    }

    /**
     * Save a bscs.
     *
     * @param bscs the entity to save
     * @return the persisted entity
     */
    public Bscs save(Bscs bscs) {
        log.debug("Request to save Bscs : {}", bscs);
        return bscsRepository.save(bscs);
    }

    /**
     * Get all the bscs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Bscs> findAll(Pageable pageable) {
        log.debug("Request to get all Bscs");
        return bscsRepository.findAll(pageable);
    }


    /**
     * Get one bscs by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Bscs> findOne(Long id) {
        log.debug("Request to get Bscs : {}", id);
        return bscsRepository.findById(id);
    }

    /**
     * Delete the bscs by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bscs : {}", id);
        bscsRepository.deleteById(id);
    }
}

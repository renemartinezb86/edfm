package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.BucoSheet;
import io.github.jhipster.application.repository.BucoSheetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing BucoSheet.
 */
@Service
@Transactional
public class BucoSheetService {

    private final Logger log = LoggerFactory.getLogger(BucoSheetService.class);

    private final BucoSheetRepository bucoSheetRepository;

    public BucoSheetService(BucoSheetRepository bucoSheetRepository) {
        this.bucoSheetRepository = bucoSheetRepository;
    }

    /**
     * Save a bucoSheet.
     *
     * @param bucoSheet the entity to save
     * @return the persisted entity
     */
    public BucoSheet save(BucoSheet bucoSheet) {
        log.debug("Request to save BucoSheet : {}", bucoSheet);
        return bucoSheetRepository.save(bucoSheet);
    }

    /**
     * Get all the bucoSheets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BucoSheet> findAll(Pageable pageable) {
        log.debug("Request to get all BucoSheets");
        return bucoSheetRepository.findAll(pageable);
    }


    /**
     * Get one bucoSheet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BucoSheet> findOne(Long id) {
        log.debug("Request to get BucoSheet : {}", id);
        return bucoSheetRepository.findById(id);
    }

    /**
     * Delete the bucoSheet by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BucoSheet : {}", id);
        bucoSheetRepository.deleteById(id);
    }
}

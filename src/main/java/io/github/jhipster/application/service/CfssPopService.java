package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.CfssPop;
import io.github.jhipster.application.repository.CfssPopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CfssPop.
 */
@Service
@Transactional
public class CfssPopService {

    private final Logger log = LoggerFactory.getLogger(CfssPopService.class);

    private final CfssPopRepository cfssPopRepository;

    public CfssPopService(CfssPopRepository cfssPopRepository) {
        this.cfssPopRepository = cfssPopRepository;
    }

    /**
     * Save a cfssPop.
     *
     * @param cfssPop the entity to save
     * @return the persisted entity
     */
    public CfssPop save(CfssPop cfssPop) {
        log.debug("Request to save CfssPop : {}", cfssPop);
        return cfssPopRepository.save(cfssPop);
    }

    /**
     * Get all the cfssPops.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CfssPop> findAll(Pageable pageable) {
        log.debug("Request to get all CfssPops");
        return cfssPopRepository.findAll(pageable);
    }


    /**
     * Get one cfssPop by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CfssPop> findOne(Long id) {
        log.debug("Request to get CfssPop : {}", id);
        return cfssPopRepository.findById(id);
    }

    /**
     * Delete the cfssPop by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CfssPop : {}", id);
        cfssPopRepository.deleteById(id);
    }
}

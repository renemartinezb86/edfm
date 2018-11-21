package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Rule;
import io.github.jhipster.application.repository.RuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Rule.
 */
@Service
@Transactional
public class RuleService {

    private final Logger log = LoggerFactory.getLogger(RuleService.class);

    private final RuleRepository ruleRepository;

    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * Save a rule.
     *
     * @param rule the entity to save
     * @return the persisted entity
     */
    public Rule save(Rule rule) {
        log.debug("Request to save Rule : {}", rule);
        return ruleRepository.save(rule);
    }

    /**
     * Get all the rules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Rule> findAll(Pageable pageable) {
        log.debug("Request to get all Rules");
        return ruleRepository.findAll(pageable);
    }


    /**
     * Get one rule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Rule> findOne(Long id) {
        log.debug("Request to get Rule : {}", id);
        return ruleRepository.findById(id);
    }

    /**
     * Delete the rule by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Rule : {}", id);
        ruleRepository.deleteById(id);
    }
}

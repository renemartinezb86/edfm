package io.github.jhipster.application.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.jhipster.application.domain.Rule;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.RuleRepository;
import io.github.jhipster.application.service.dto.RuleCriteria;

/**
 * Service for executing complex queries for Rule entities in the database.
 * The main input is a {@link RuleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Rule} or a {@link Page} of {@link Rule} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RuleQueryService extends QueryService<Rule> {

    private final Logger log = LoggerFactory.getLogger(RuleQueryService.class);

    private final RuleRepository ruleRepository;

    public RuleQueryService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * Return a {@link List} of {@link Rule} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Rule> findByCriteria(RuleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rule> specification = createSpecification(criteria);
        return ruleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Rule} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Rule> findByCriteria(RuleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rule> specification = createSpecification(criteria);
        return ruleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RuleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rule> specification = createSpecification(criteria);
        return ruleRepository.count(specification);
    }

    /**
     * Function to convert RuleCriteria to a {@link Specification}
     */
    private Specification<Rule> createSpecification(RuleCriteria criteria) {
        Specification<Rule> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Rule_.id));
            }
            if (criteria.getRuleId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRuleId(), Rule_.ruleId));
            }
            if (criteria.getRuleType() != null) {
                specification = specification.and(buildSpecification(criteria.getRuleType(), Rule_.ruleType));
            }
            if (criteria.getDefinition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefinition(), Rule_.definition));
            }
            if (criteria.getScenario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getScenario(), Rule_.scenario));
            }
            if (criteria.getDetail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetail(), Rule_.detail));
            }
            if (criteria.getProductOfferingId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOfferingId(),
                    root -> root.join(Rule_.productOfferings, JoinType.LEFT).get(ProductOffering_.id)));
            }
        }
        return specification;
    }
}

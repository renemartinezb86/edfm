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

import io.github.jhipster.application.domain.POCharacteristic;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.POCharacteristicRepository;
import io.github.jhipster.application.service.dto.POCharacteristicCriteria;

/**
 * Service for executing complex queries for POCharacteristic entities in the database.
 * The main input is a {@link POCharacteristicCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link POCharacteristic} or a {@link Page} of {@link POCharacteristic} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class POCharacteristicQueryService extends QueryService<POCharacteristic> {

    private final Logger log = LoggerFactory.getLogger(POCharacteristicQueryService.class);

    private final POCharacteristicRepository pOCharacteristicRepository;

    public POCharacteristicQueryService(POCharacteristicRepository pOCharacteristicRepository) {
        this.pOCharacteristicRepository = pOCharacteristicRepository;
    }

    /**
     * Return a {@link List} of {@link POCharacteristic} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<POCharacteristic> findByCriteria(POCharacteristicCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<POCharacteristic> specification = createSpecification(criteria);
        return pOCharacteristicRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link POCharacteristic} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<POCharacteristic> findByCriteria(POCharacteristicCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<POCharacteristic> specification = createSpecification(criteria);
        return pOCharacteristicRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(POCharacteristicCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<POCharacteristic> specification = createSpecification(criteria);
        return pOCharacteristicRepository.count(specification);
    }

    /**
     * Function to convert POCharacteristicCriteria to a {@link Specification}
     */
    private Specification<POCharacteristic> createSpecification(POCharacteristicCriteria criteria) {
        Specification<POCharacteristic> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), POCharacteristic_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), POCharacteristic_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), POCharacteristic_.value));
            }
            if (criteria.getProductOfferingId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOfferingId(),
                    root -> root.join(POCharacteristic_.productOffering, JoinType.LEFT).get(ProductOffering_.id)));
            }
        }
        return specification;
    }
}

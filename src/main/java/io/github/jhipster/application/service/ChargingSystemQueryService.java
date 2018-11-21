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

import io.github.jhipster.application.domain.ChargingSystem;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.ChargingSystemRepository;
import io.github.jhipster.application.service.dto.ChargingSystemCriteria;

/**
 * Service for executing complex queries for ChargingSystem entities in the database.
 * The main input is a {@link ChargingSystemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChargingSystem} or a {@link Page} of {@link ChargingSystem} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChargingSystemQueryService extends QueryService<ChargingSystem> {

    private final Logger log = LoggerFactory.getLogger(ChargingSystemQueryService.class);

    private final ChargingSystemRepository chargingSystemRepository;

    public ChargingSystemQueryService(ChargingSystemRepository chargingSystemRepository) {
        this.chargingSystemRepository = chargingSystemRepository;
    }

    /**
     * Return a {@link List} of {@link ChargingSystem} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChargingSystem> findByCriteria(ChargingSystemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ChargingSystem> specification = createSpecification(criteria);
        return chargingSystemRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ChargingSystem} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChargingSystem> findByCriteria(ChargingSystemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ChargingSystem> specification = createSpecification(criteria);
        return chargingSystemRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChargingSystemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ChargingSystem> specification = createSpecification(criteria);
        return chargingSystemRepository.count(specification);
    }

    /**
     * Function to convert ChargingSystemCriteria to a {@link Specification}
     */
    private Specification<ChargingSystem> createSpecification(ChargingSystemCriteria criteria) {
        Specification<ChargingSystem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ChargingSystem_.id));
            }
            if (criteria.getServiceClassId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceClassId(), ChargingSystem_.serviceClassId));
            }
            if (criteria.getOfferTemplate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOfferTemplate(), ChargingSystem_.offerTemplate));
            }
            if (criteria.getCharacteristicName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCharacteristicName(), ChargingSystem_.characteristicName));
            }
            if (criteria.getServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceId(),
                    root -> root.join(ChargingSystem_.service, JoinType.LEFT).get(Service_.id)));
            }
            if (criteria.getFreeUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getFreeUnitId(),
                    root -> root.join(ChargingSystem_.freeUnit, JoinType.LEFT).get(FreeUnit_.id)));
            }
        }
        return specification;
    }
}

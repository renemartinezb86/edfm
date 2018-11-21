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

import io.github.jhipster.application.domain.FreeUnit;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.FreeUnitRepository;
import io.github.jhipster.application.service.dto.FreeUnitCriteria;

/**
 * Service for executing complex queries for FreeUnit entities in the database.
 * The main input is a {@link FreeUnitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FreeUnit} or a {@link Page} of {@link FreeUnit} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FreeUnitQueryService extends QueryService<FreeUnit> {

    private final Logger log = LoggerFactory.getLogger(FreeUnitQueryService.class);

    private final FreeUnitRepository freeUnitRepository;

    public FreeUnitQueryService(FreeUnitRepository freeUnitRepository) {
        this.freeUnitRepository = freeUnitRepository;
    }

    /**
     * Return a {@link List} of {@link FreeUnit} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FreeUnit> findByCriteria(FreeUnitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FreeUnit> specification = createSpecification(criteria);
        return freeUnitRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FreeUnit} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FreeUnit> findByCriteria(FreeUnitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FreeUnit> specification = createSpecification(criteria);
        return freeUnitRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FreeUnitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FreeUnit> specification = createSpecification(criteria);
        return freeUnitRepository.count(specification);
    }

    /**
     * Function to convert FreeUnitCriteria to a {@link Specification}
     */
    private Specification<FreeUnit> createSpecification(FreeUnitCriteria criteria) {
        Specification<FreeUnit> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FreeUnit_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), FreeUnit_.name));
            }
            if (criteria.getFreeUnitType() != null) {
                specification = specification.and(buildSpecification(criteria.getFreeUnitType(), FreeUnit_.freeUnitType));
            }
            if (criteria.getUnits() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnits(), FreeUnit_.units));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), FreeUnit_.amount));
            }
            if (criteria.getChargingSystemId() != null) {
                specification = specification.and(buildSpecification(criteria.getChargingSystemId(),
                    root -> root.join(FreeUnit_.chargingSystem, JoinType.LEFT).get(ChargingSystem_.id)));
            }
            if (criteria.getProductOfferingId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOfferingId(),
                    root -> root.join(FreeUnit_.productOfferings, JoinType.LEFT).get(ProductOffering_.id)));
            }
        }
        return specification;
    }
}

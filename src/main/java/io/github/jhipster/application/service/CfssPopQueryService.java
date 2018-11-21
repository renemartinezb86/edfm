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

import io.github.jhipster.application.domain.CfssPop;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.CfssPopRepository;
import io.github.jhipster.application.service.dto.CfssPopCriteria;

/**
 * Service for executing complex queries for CfssPop entities in the database.
 * The main input is a {@link CfssPopCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CfssPop} or a {@link Page} of {@link CfssPop} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CfssPopQueryService extends QueryService<CfssPop> {

    private final Logger log = LoggerFactory.getLogger(CfssPopQueryService.class);

    private final CfssPopRepository cfssPopRepository;

    public CfssPopQueryService(CfssPopRepository cfssPopRepository) {
        this.cfssPopRepository = cfssPopRepository;
    }

    /**
     * Return a {@link List} of {@link CfssPop} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CfssPop> findByCriteria(CfssPopCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CfssPop> specification = createSpecification(criteria);
        return cfssPopRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CfssPop} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CfssPop> findByCriteria(CfssPopCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CfssPop> specification = createSpecification(criteria);
        return cfssPopRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CfssPopCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CfssPop> specification = createSpecification(criteria);
        return cfssPopRepository.count(specification);
    }

    /**
     * Function to convert CfssPopCriteria to a {@link Specification}
     */
    private Specification<CfssPop> createSpecification(CfssPopCriteria criteria) {
        Specification<CfssPop> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CfssPop_.id));
            }
            if (criteria.getCfssPopType() != null) {
                specification = specification.and(buildSpecification(criteria.getCfssPopType(), CfssPop_.cfssPopType));
            }
            if (criteria.getCharacteristic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCharacteristic(), CfssPop_.characteristic));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), CfssPop_.value));
            }
            if (criteria.getProductOfferingId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOfferingId(),
                    root -> root.join(CfssPop_.productOfferings, JoinType.LEFT).get(ProductOffering_.id)));
            }
        }
        return specification;
    }
}

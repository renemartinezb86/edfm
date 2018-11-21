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

import io.github.jhipster.application.domain.BucoVersion;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.BucoVersionRepository;
import io.github.jhipster.application.service.dto.BucoVersionCriteria;

/**
 * Service for executing complex queries for BucoVersion entities in the database.
 * The main input is a {@link BucoVersionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BucoVersion} or a {@link Page} of {@link BucoVersion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BucoVersionQueryService extends QueryService<BucoVersion> {

    private final Logger log = LoggerFactory.getLogger(BucoVersionQueryService.class);

    private final BucoVersionRepository bucoVersionRepository;

    public BucoVersionQueryService(BucoVersionRepository bucoVersionRepository) {
        this.bucoVersionRepository = bucoVersionRepository;
    }

    /**
     * Return a {@link List} of {@link BucoVersion} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BucoVersion> findByCriteria(BucoVersionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BucoVersion> specification = createSpecification(criteria);
        return bucoVersionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BucoVersion} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BucoVersion> findByCriteria(BucoVersionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BucoVersion> specification = createSpecification(criteria);
        return bucoVersionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BucoVersionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BucoVersion> specification = createSpecification(criteria);
        return bucoVersionRepository.count(specification);
    }

    /**
     * Function to convert BucoVersionCriteria to a {@link Specification}
     */
    private Specification<BucoVersion> createSpecification(BucoVersionCriteria criteria) {
        Specification<BucoVersion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BucoVersion_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BucoVersion_.name));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), BucoVersion_.fileName));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), BucoVersion_.createdDate));
            }
        }
        return specification;
    }
}

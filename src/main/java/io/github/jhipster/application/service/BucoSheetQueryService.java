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

import io.github.jhipster.application.domain.BucoSheet;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.BucoSheetRepository;
import io.github.jhipster.application.service.dto.BucoSheetCriteria;

/**
 * Service for executing complex queries for BucoSheet entities in the database.
 * The main input is a {@link BucoSheetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BucoSheet} or a {@link Page} of {@link BucoSheet} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BucoSheetQueryService extends QueryService<BucoSheet> {

    private final Logger log = LoggerFactory.getLogger(BucoSheetQueryService.class);

    private final BucoSheetRepository bucoSheetRepository;

    public BucoSheetQueryService(BucoSheetRepository bucoSheetRepository) {
        this.bucoSheetRepository = bucoSheetRepository;
    }

    /**
     * Return a {@link List} of {@link BucoSheet} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BucoSheet> findByCriteria(BucoSheetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BucoSheet> specification = createSpecification(criteria);
        return bucoSheetRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BucoSheet} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BucoSheet> findByCriteria(BucoSheetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BucoSheet> specification = createSpecification(criteria);
        return bucoSheetRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BucoSheetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BucoSheet> specification = createSpecification(criteria);
        return bucoSheetRepository.count(specification);
    }

    /**
     * Function to convert BucoSheetCriteria to a {@link Specification}
     */
    private Specification<BucoSheet> createSpecification(BucoSheetCriteria criteria) {
        Specification<BucoSheet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BucoSheet_.id));
            }
            if (criteria.getSheetName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSheetName(), BucoSheet_.sheetName));
            }
            if (criteria.getSheetType() != null) {
                specification = specification.and(buildSpecification(criteria.getSheetType(), BucoSheet_.sheetType));
            }
            if (criteria.getBucoVersionId() != null) {
                specification = specification.and(buildSpecification(criteria.getBucoVersionId(),
                    root -> root.join(BucoSheet_.bucoVersion, JoinType.LEFT).get(BucoVersion_.id)));
            }
        }
        return specification;
    }
}

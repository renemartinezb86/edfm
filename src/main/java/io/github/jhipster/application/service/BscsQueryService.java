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

import io.github.jhipster.application.domain.Bscs;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.BscsRepository;
import io.github.jhipster.application.service.dto.BscsCriteria;

/**
 * Service for executing complex queries for Bscs entities in the database.
 * The main input is a {@link BscsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Bscs} or a {@link Page} of {@link Bscs} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BscsQueryService extends QueryService<Bscs> {

    private final Logger log = LoggerFactory.getLogger(BscsQueryService.class);

    private final BscsRepository bscsRepository;

    public BscsQueryService(BscsRepository bscsRepository) {
        this.bscsRepository = bscsRepository;
    }

    /**
     * Return a {@link List} of {@link Bscs} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Bscs> findByCriteria(BscsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bscs> specification = createSpecification(criteria);
        return bscsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Bscs} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Bscs> findByCriteria(BscsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bscs> specification = createSpecification(criteria);
        return bscsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BscsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bscs> specification = createSpecification(criteria);
        return bscsRepository.count(specification);
    }

    /**
     * Function to convert BscsCriteria to a {@link Specification}
     */
    private Specification<Bscs> createSpecification(BscsCriteria criteria) {
        Specification<Bscs> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Bscs_.id));
            }
            if (criteria.getServices() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServices(), Bscs_.services));
            }
            if (criteria.getServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceId(),
                    root -> root.join(Bscs_.service, JoinType.LEFT).get(Service_.id)));
            }
        }
        return specification;
    }
}

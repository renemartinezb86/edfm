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

import io.github.jhipster.application.domain.Resource;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.ResourceRepository;
import io.github.jhipster.application.service.dto.ResourceCriteria;

/**
 * Service for executing complex queries for Resource entities in the database.
 * The main input is a {@link ResourceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Resource} or a {@link Page} of {@link Resource} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResourceQueryService extends QueryService<Resource> {

    private final Logger log = LoggerFactory.getLogger(ResourceQueryService.class);

    private final ResourceRepository resourceRepository;

    public ResourceQueryService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    /**
     * Return a {@link List} of {@link Resource} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Resource> findByCriteria(ResourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Resource> specification = createSpecification(criteria);
        return resourceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Resource} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Resource> findByCriteria(ResourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Resource> specification = createSpecification(criteria);
        return resourceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResourceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Resource> specification = createSpecification(criteria);
        return resourceRepository.count(specification);
    }

    /**
     * Function to convert ResourceCriteria to a {@link Specification}
     */
    private Specification<Resource> createSpecification(ResourceCriteria criteria) {
        Specification<Resource> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Resource_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Resource_.name));
            }
            if (criteria.getResourceType() != null) {
                specification = specification.and(buildSpecification(criteria.getResourceType(), Resource_.resourceType));
            }
            if (criteria.getRelatedItem() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelatedItem(), Resource_.relatedItem));
            }
            if (criteria.getRelatedItemCharacteristic() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelatedItemCharacteristic(), Resource_.relatedItemCharacteristic));
            }
            if (criteria.getProductOfferingId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOfferingId(),
                    root -> root.join(Resource_.productOfferings, JoinType.LEFT).get(ProductOffering_.id)));
            }
        }
        return specification;
    }
}

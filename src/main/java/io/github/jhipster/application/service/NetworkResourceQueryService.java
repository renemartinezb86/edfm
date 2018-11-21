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

import io.github.jhipster.application.domain.NetworkResource;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.NetworkResourceRepository;
import io.github.jhipster.application.service.dto.NetworkResourceCriteria;

/**
 * Service for executing complex queries for NetworkResource entities in the database.
 * The main input is a {@link NetworkResourceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NetworkResource} or a {@link Page} of {@link NetworkResource} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NetworkResourceQueryService extends QueryService<NetworkResource> {

    private final Logger log = LoggerFactory.getLogger(NetworkResourceQueryService.class);

    private final NetworkResourceRepository networkResourceRepository;

    public NetworkResourceQueryService(NetworkResourceRepository networkResourceRepository) {
        this.networkResourceRepository = networkResourceRepository;
    }

    /**
     * Return a {@link List} of {@link NetworkResource} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NetworkResource> findByCriteria(NetworkResourceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NetworkResource> specification = createSpecification(criteria);
        return networkResourceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NetworkResource} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NetworkResource> findByCriteria(NetworkResourceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NetworkResource> specification = createSpecification(criteria);
        return networkResourceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NetworkResourceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NetworkResource> specification = createSpecification(criteria);
        return networkResourceRepository.count(specification);
    }

    /**
     * Function to convert NetworkResourceCriteria to a {@link Specification}
     */
    private Specification<NetworkResource> createSpecification(NetworkResourceCriteria criteria) {
        Specification<NetworkResource> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NetworkResource_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), NetworkResource_.name));
            }
            if (criteria.getNetworkParameterId() != null) {
                specification = specification.and(buildSpecification(criteria.getNetworkParameterId(),
                    root -> root.join(NetworkResource_.networkParameters, JoinType.LEFT).get(NetworkParameter_.id)));
            }
            if (criteria.getProductOfferingId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOfferingId(),
                    root -> root.join(NetworkResource_.productOfferings, JoinType.LEFT).get(ProductOffering_.id)));
            }
        }
        return specification;
    }
}

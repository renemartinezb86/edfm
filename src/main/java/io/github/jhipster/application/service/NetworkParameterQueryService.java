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

import io.github.jhipster.application.domain.NetworkParameter;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.NetworkParameterRepository;
import io.github.jhipster.application.service.dto.NetworkParameterCriteria;

/**
 * Service for executing complex queries for NetworkParameter entities in the database.
 * The main input is a {@link NetworkParameterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NetworkParameter} or a {@link Page} of {@link NetworkParameter} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NetworkParameterQueryService extends QueryService<NetworkParameter> {

    private final Logger log = LoggerFactory.getLogger(NetworkParameterQueryService.class);

    private final NetworkParameterRepository networkParameterRepository;

    public NetworkParameterQueryService(NetworkParameterRepository networkParameterRepository) {
        this.networkParameterRepository = networkParameterRepository;
    }

    /**
     * Return a {@link List} of {@link NetworkParameter} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NetworkParameter> findByCriteria(NetworkParameterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NetworkParameter> specification = createSpecification(criteria);
        return networkParameterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NetworkParameter} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NetworkParameter> findByCriteria(NetworkParameterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NetworkParameter> specification = createSpecification(criteria);
        return networkParameterRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NetworkParameterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NetworkParameter> specification = createSpecification(criteria);
        return networkParameterRepository.count(specification);
    }

    /**
     * Function to convert NetworkParameterCriteria to a {@link Specification}
     */
    private Specification<NetworkParameter> createSpecification(NetworkParameterCriteria criteria) {
        Specification<NetworkParameter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NetworkParameter_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), NetworkParameter_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), NetworkParameter_.type));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), NetworkParameter_.value));
            }
            if (criteria.getNetworkResourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getNetworkResourceId(),
                    root -> root.join(NetworkParameter_.networkResources, JoinType.LEFT).get(NetworkResource_.id)));
            }
        }
        return specification;
    }
}

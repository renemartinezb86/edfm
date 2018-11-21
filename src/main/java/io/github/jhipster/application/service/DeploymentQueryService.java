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

import io.github.jhipster.application.domain.Deployment;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.DeploymentRepository;
import io.github.jhipster.application.service.dto.DeploymentCriteria;

/**
 * Service for executing complex queries for Deployment entities in the database.
 * The main input is a {@link DeploymentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Deployment} or a {@link Page} of {@link Deployment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeploymentQueryService extends QueryService<Deployment> {

    private final Logger log = LoggerFactory.getLogger(DeploymentQueryService.class);

    private final DeploymentRepository deploymentRepository;

    public DeploymentQueryService(DeploymentRepository deploymentRepository) {
        this.deploymentRepository = deploymentRepository;
    }

    /**
     * Return a {@link List} of {@link Deployment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Deployment> findByCriteria(DeploymentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Deployment> specification = createSpecification(criteria);
        return deploymentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Deployment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Deployment> findByCriteria(DeploymentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Deployment> specification = createSpecification(criteria);
        return deploymentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeploymentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Deployment> specification = createSpecification(criteria);
        return deploymentRepository.count(specification);
    }

    /**
     * Function to convert DeploymentCriteria to a {@link Specification}
     */
    private Specification<Deployment> createSpecification(DeploymentCriteria criteria) {
        Specification<Deployment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Deployment_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Deployment_.name));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Deployment_.date));
            }
            if (criteria.getApplicationVersionId() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicationVersionId(),
                    root -> root.join(Deployment_.applicationVersion, JoinType.LEFT).get(ApplicationVersion_.id)));
            }
            if (criteria.getEnvironmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnvironmentId(),
                    root -> root.join(Deployment_.environment, JoinType.LEFT).get(Environment_.id)));
            }
            if (criteria.getDeploymentLogsId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeploymentLogsId(),
                    root -> root.join(Deployment_.deploymentLogs, JoinType.LEFT).get(DeploymentPipelineLog_.id)));
            }
        }
        return specification;
    }
}

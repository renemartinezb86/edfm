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

import io.github.jhipster.application.domain.Service;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.ServiceRepository;
import io.github.jhipster.application.service.dto.ServiceCriteria;

/**
 * Service for executing complex queries for Service entities in the database.
 * The main input is a {@link ServiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Service} or a {@link Page} of {@link Service} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceQueryService extends QueryService<Service> {

    private final Logger log = LoggerFactory.getLogger(ServiceQueryService.class);

    private final ServiceRepository serviceRepository;

    public ServiceQueryService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Return a {@link List} of {@link Service} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Service> findByCriteria(ServiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Service> specification = createSpecification(criteria);
        return serviceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Service} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Service> findByCriteria(ServiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Service> specification = createSpecification(criteria);
        return serviceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Service> specification = createSpecification(criteria);
        return serviceRepository.count(specification);
    }

    /**
     * Function to convert ServiceCriteria to a {@link Specification}
     */
    private Specification<Service> createSpecification(ServiceCriteria criteria) {
        Specification<Service> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Service_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Service_.name));
            }
            if (criteria.getServiceType() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceType(), Service_.serviceType));
            }
            if (criteria.getChargingSystemId() != null) {
                specification = specification.and(buildSpecification(criteria.getChargingSystemId(),
                    root -> root.join(Service_.chargingSystem, JoinType.LEFT).get(ChargingSystem_.id)));
            }
            if (criteria.getBscsId() != null) {
                specification = specification.and(buildSpecification(criteria.getBscsId(),
                    root -> root.join(Service_.bscs, JoinType.LEFT).get(Bscs_.id)));
            }
            if (criteria.getProductOfferingId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOfferingId(),
                    root -> root.join(Service_.productOfferings, JoinType.LEFT).get(ProductOffering_.id)));
            }
        }
        return specification;
    }
}

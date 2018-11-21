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

import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.ProductOfferingRepository;
import io.github.jhipster.application.service.dto.ProductOfferingCriteria;

/**
 * Service for executing complex queries for ProductOffering entities in the database.
 * The main input is a {@link ProductOfferingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductOffering} or a {@link Page} of {@link ProductOffering} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductOfferingQueryService extends QueryService<ProductOffering> {

    private final Logger log = LoggerFactory.getLogger(ProductOfferingQueryService.class);

    private final ProductOfferingRepository productOfferingRepository;

    public ProductOfferingQueryService(ProductOfferingRepository productOfferingRepository) {
        this.productOfferingRepository = productOfferingRepository;
    }

    /**
     * Return a {@link List} of {@link ProductOffering} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductOffering> findByCriteria(ProductOfferingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductOffering> specification = createSpecification(criteria);
        return productOfferingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProductOffering} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductOffering> findByCriteria(ProductOfferingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductOffering> specification = createSpecification(criteria);
        return productOfferingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductOfferingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductOffering> specification = createSpecification(criteria);
        return productOfferingRepository.count(specification);
    }

    /**
     * Function to convert ProductOfferingCriteria to a {@link Specification}
     */
    private Specification<ProductOffering> createSpecification(ProductOfferingCriteria criteria) {
        Specification<ProductOffering> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProductOffering_.id));
            }
            if (criteria.getPoId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPoId(), ProductOffering_.poId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), ProductOffering_.name));
            }
            if (criteria.getComercialName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComercialName(), ProductOffering_.comercialName));
            }
            if (criteria.getPoPriceId() != null) {
                specification = specification.and(buildSpecification(criteria.getPoPriceId(),
                    root -> root.join(ProductOffering_.poPrice, JoinType.LEFT).get(PoPrice_.id)));
            }
            if (criteria.getBucoSheetId() != null) {
                specification = specification.and(buildSpecification(criteria.getBucoSheetId(),
                    root -> root.join(ProductOffering_.bucoSheet, JoinType.LEFT).get(BucoSheet_.id)));
            }
            if (criteria.getRulesId() != null) {
                specification = specification.and(buildSpecification(criteria.getRulesId(),
                    root -> root.join(ProductOffering_.rules, JoinType.LEFT).get(Rule_.id)));
            }
            if (criteria.getResourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getResourceId(),
                    root -> root.join(ProductOffering_.resources, JoinType.LEFT).get(Resource_.id)));
            }
            if (criteria.getNetworkResourcesId() != null) {
                specification = specification.and(buildSpecification(criteria.getNetworkResourcesId(),
                    root -> root.join(ProductOffering_.networkResources, JoinType.LEFT).get(NetworkResource_.id)));
            }
            if (criteria.getServiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceId(),
                    root -> root.join(ProductOffering_.services, JoinType.LEFT).get(Service_.id)));
            }
            if (criteria.getCfssPopId() != null) {
                specification = specification.and(buildSpecification(criteria.getCfssPopId(),
                    root -> root.join(ProductOffering_.cfssPops, JoinType.LEFT).get(CfssPop_.id)));
            }
            if (criteria.getFreeUnitId() != null) {
                specification = specification.and(buildSpecification(criteria.getFreeUnitId(),
                    root -> root.join(ProductOffering_.freeUnits, JoinType.LEFT).get(FreeUnit_.id)));
            }
        }
        return specification;
    }
}

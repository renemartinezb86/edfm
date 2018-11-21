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

import io.github.jhipster.application.domain.PoPrice;
import io.github.jhipster.application.domain.*; // for static metamodels
import io.github.jhipster.application.repository.PoPriceRepository;
import io.github.jhipster.application.service.dto.PoPriceCriteria;

/**
 * Service for executing complex queries for PoPrice entities in the database.
 * The main input is a {@link PoPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PoPrice} or a {@link Page} of {@link PoPrice} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PoPriceQueryService extends QueryService<PoPrice> {

    private final Logger log = LoggerFactory.getLogger(PoPriceQueryService.class);

    private final PoPriceRepository poPriceRepository;

    public PoPriceQueryService(PoPriceRepository poPriceRepository) {
        this.poPriceRepository = poPriceRepository;
    }

    /**
     * Return a {@link List} of {@link PoPrice} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PoPrice> findByCriteria(PoPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PoPrice> specification = createSpecification(criteria);
        return poPriceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PoPrice} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PoPrice> findByCriteria(PoPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PoPrice> specification = createSpecification(criteria);
        return poPriceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PoPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PoPrice> specification = createSpecification(criteria);
        return poPriceRepository.count(specification);
    }

    /**
     * Function to convert PoPriceCriteria to a {@link Specification}
     */
    private Specification<PoPrice> createSpecification(PoPriceCriteria criteria) {
        Specification<PoPrice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PoPrice_.id));
            }
            if (criteria.getPoPriceType() != null) {
                specification = specification.and(buildSpecification(criteria.getPoPriceType(), PoPrice_.poPriceType));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), PoPrice_.amount));
            }
            if (criteria.getPaymentType() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentType(), PoPrice_.paymentType));
            }
            if (criteria.getShowInBill() != null) {
                specification = specification.and(buildSpecification(criteria.getShowInBill(), PoPrice_.showInBill));
            }
            if (criteria.getPayInAdvance() != null) {
                specification = specification.and(buildSpecification(criteria.getPayInAdvance(), PoPrice_.payInAdvance));
            }
            if (criteria.getBillOnSuspension() != null) {
                specification = specification.and(buildSpecification(criteria.getBillOnSuspension(), PoPrice_.billOnSuspension));
            }
            if (criteria.getMultiDiscount() != null) {
                specification = specification.and(buildSpecification(criteria.getMultiDiscount(), PoPrice_.multiDiscount));
            }
            if (criteria.getProductOfferingId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductOfferingId(),
                    root -> root.join(PoPrice_.productOffering, JoinType.LEFT).get(ProductOffering_.id)));
            }
        }
        return specification;
    }
}

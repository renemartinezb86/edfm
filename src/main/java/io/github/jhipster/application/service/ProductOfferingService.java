package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.repository.ProductOfferingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ProductOffering.
 */
@Service
@Transactional
public class ProductOfferingService {

    private final Logger log = LoggerFactory.getLogger(ProductOfferingService.class);

    private final ProductOfferingRepository productOfferingRepository;

    public ProductOfferingService(ProductOfferingRepository productOfferingRepository) {
        this.productOfferingRepository = productOfferingRepository;
    }

    /**
     * Save a productOffering.
     *
     * @param productOffering the entity to save
     * @return the persisted entity
     */
    public ProductOffering save(ProductOffering productOffering) {
        log.debug("Request to save ProductOffering : {}", productOffering);
        return productOfferingRepository.save(productOffering);
    }

    /**
     * Get all the productOfferings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProductOffering> findAll(Pageable pageable) {
        log.debug("Request to get all ProductOfferings");
        return productOfferingRepository.findAll(pageable);
    }

    /**
     * Get all the ProductOffering with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<ProductOffering> findAllWithEagerRelationships(Pageable pageable) {
        return productOfferingRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one productOffering by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProductOffering> findOne(Long id) {
        log.debug("Request to get ProductOffering : {}", id);
        return productOfferingRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the productOffering by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProductOffering : {}", id);
        productOfferingRepository.deleteById(id);
    }
}

package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.PoPrice;
import io.github.jhipster.application.repository.PoPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing PoPrice.
 */
@Service
@Transactional
public class PoPriceService {

    private final Logger log = LoggerFactory.getLogger(PoPriceService.class);

    private final PoPriceRepository poPriceRepository;

    public PoPriceService(PoPriceRepository poPriceRepository) {
        this.poPriceRepository = poPriceRepository;
    }

    /**
     * Save a poPrice.
     *
     * @param poPrice the entity to save
     * @return the persisted entity
     */
    public PoPrice save(PoPrice poPrice) {
        log.debug("Request to save PoPrice : {}", poPrice);
        return poPriceRepository.save(poPrice);
    }

    /**
     * Get all the poPrices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PoPrice> findAll(Pageable pageable) {
        log.debug("Request to get all PoPrices");
        return poPriceRepository.findAll(pageable);
    }



    /**
     *  get all the poPrices where ProductOffering is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<PoPrice> findAllWhereProductOfferingIsNull() {
        log.debug("Request to get all poPrices where ProductOffering is null");
        return StreamSupport
            .stream(poPriceRepository.findAll().spliterator(), false)
            .filter(poPrice -> poPrice.getProductOffering() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one poPrice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PoPrice> findOne(Long id) {
        log.debug("Request to get PoPrice : {}", id);
        return poPriceRepository.findById(id);
    }

    /**
     * Delete the poPrice by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PoPrice : {}", id);
        poPriceRepository.deleteById(id);
    }
}

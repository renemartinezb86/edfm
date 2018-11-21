package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Service;
import io.github.jhipster.application.repository.ServiceRepository;
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
 * Service Implementation for managing Service.
 */
@Service
@Transactional
public class ServiceService {

    private final Logger log = LoggerFactory.getLogger(ServiceService.class);

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    /**
     * Save a service.
     *
     * @param service the entity to save
     * @return the persisted entity
     */
    public Service save(Service service) {
        log.debug("Request to save Service : {}", service);
        return serviceRepository.save(service);
    }

    /**
     * Get all the services.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Service> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        return serviceRepository.findAll(pageable);
    }



    /**
     *  get all the services where ChargingSystem is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Service> findAllWhereChargingSystemIsNull() {
        log.debug("Request to get all services where ChargingSystem is null");
        return StreamSupport
            .stream(serviceRepository.findAll().spliterator(), false)
            .filter(service -> service.getChargingSystem() == null)
            .collect(Collectors.toList());
    }


    /**
     *  get all the services where Bscs is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Service> findAllWhereBscsIsNull() {
        log.debug("Request to get all services where Bscs is null");
        return StreamSupport
            .stream(serviceRepository.findAll().spliterator(), false)
            .filter(service -> service.getBscs() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one service by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Service> findOne(Long id) {
        log.debug("Request to get Service : {}", id);
        return serviceRepository.findById(id);
    }

    /**
     * Delete the service by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Service : {}", id);
        serviceRepository.deleteById(id);
    }
}

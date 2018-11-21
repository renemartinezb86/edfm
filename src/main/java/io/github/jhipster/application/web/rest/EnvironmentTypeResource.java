package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.EnvironmentType;
import io.github.jhipster.application.repository.EnvironmentTypeRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EnvironmentType.
 */
@RestController
@RequestMapping("/api")
public class EnvironmentTypeResource {

    private final Logger log = LoggerFactory.getLogger(EnvironmentTypeResource.class);

    private static final String ENTITY_NAME = "environmentType";

    private final EnvironmentTypeRepository environmentTypeRepository;

    public EnvironmentTypeResource(EnvironmentTypeRepository environmentTypeRepository) {
        this.environmentTypeRepository = environmentTypeRepository;
    }

    /**
     * POST  /environment-types : Create a new environmentType.
     *
     * @param environmentType the environmentType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new environmentType, or with status 400 (Bad Request) if the environmentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/environment-types")
    @Timed
    public ResponseEntity<EnvironmentType> createEnvironmentType(@RequestBody EnvironmentType environmentType) throws URISyntaxException {
        log.debug("REST request to save EnvironmentType : {}", environmentType);
        if (environmentType.getId() != null) {
            throw new BadRequestAlertException("A new environmentType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnvironmentType result = environmentTypeRepository.save(environmentType);
        return ResponseEntity.created(new URI("/api/environment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /environment-types : Updates an existing environmentType.
     *
     * @param environmentType the environmentType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated environmentType,
     * or with status 400 (Bad Request) if the environmentType is not valid,
     * or with status 500 (Internal Server Error) if the environmentType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/environment-types")
    @Timed
    public ResponseEntity<EnvironmentType> updateEnvironmentType(@RequestBody EnvironmentType environmentType) throws URISyntaxException {
        log.debug("REST request to update EnvironmentType : {}", environmentType);
        if (environmentType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnvironmentType result = environmentTypeRepository.save(environmentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, environmentType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /environment-types : get all the environmentTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of environmentTypes in body
     */
    @GetMapping("/environment-types")
    @Timed
    public List<EnvironmentType> getAllEnvironmentTypes() {
        log.debug("REST request to get all EnvironmentTypes");
        return environmentTypeRepository.findAll();
    }

    /**
     * GET  /environment-types/:id : get the "id" environmentType.
     *
     * @param id the id of the environmentType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the environmentType, or with status 404 (Not Found)
     */
    @GetMapping("/environment-types/{id}")
    @Timed
    public ResponseEntity<EnvironmentType> getEnvironmentType(@PathVariable Long id) {
        log.debug("REST request to get EnvironmentType : {}", id);
        Optional<EnvironmentType> environmentType = environmentTypeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(environmentType);
    }

    /**
     * DELETE  /environment-types/:id : delete the "id" environmentType.
     *
     * @param id the id of the environmentType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/environment-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnvironmentType(@PathVariable Long id) {
        log.debug("REST request to delete EnvironmentType : {}", id);

        environmentTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.ApplicationVersionRelation;
import io.github.jhipster.application.repository.ApplicationVersionRelationRepository;
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
 * REST controller for managing ApplicationVersionRelation.
 */
@RestController
@RequestMapping("/api")
public class ApplicationVersionRelationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationVersionRelationResource.class);

    private static final String ENTITY_NAME = "applicationVersionRelation";

    private final ApplicationVersionRelationRepository applicationVersionRelationRepository;

    public ApplicationVersionRelationResource(ApplicationVersionRelationRepository applicationVersionRelationRepository) {
        this.applicationVersionRelationRepository = applicationVersionRelationRepository;
    }

    /**
     * POST  /application-version-relations : Create a new applicationVersionRelation.
     *
     * @param applicationVersionRelation the applicationVersionRelation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationVersionRelation, or with status 400 (Bad Request) if the applicationVersionRelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/application-version-relations")
    @Timed
    public ResponseEntity<ApplicationVersionRelation> createApplicationVersionRelation(@RequestBody ApplicationVersionRelation applicationVersionRelation) throws URISyntaxException {
        log.debug("REST request to save ApplicationVersionRelation : {}", applicationVersionRelation);
        if (applicationVersionRelation.getId() != null) {
            throw new BadRequestAlertException("A new applicationVersionRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationVersionRelation result = applicationVersionRelationRepository.save(applicationVersionRelation);
        return ResponseEntity.created(new URI("/api/application-version-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-version-relations : Updates an existing applicationVersionRelation.
     *
     * @param applicationVersionRelation the applicationVersionRelation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationVersionRelation,
     * or with status 400 (Bad Request) if the applicationVersionRelation is not valid,
     * or with status 500 (Internal Server Error) if the applicationVersionRelation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/application-version-relations")
    @Timed
    public ResponseEntity<ApplicationVersionRelation> updateApplicationVersionRelation(@RequestBody ApplicationVersionRelation applicationVersionRelation) throws URISyntaxException {
        log.debug("REST request to update ApplicationVersionRelation : {}", applicationVersionRelation);
        if (applicationVersionRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationVersionRelation result = applicationVersionRelationRepository.save(applicationVersionRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationVersionRelation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-version-relations : get all the applicationVersionRelations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applicationVersionRelations in body
     */
    @GetMapping("/application-version-relations")
    @Timed
    public List<ApplicationVersionRelation> getAllApplicationVersionRelations() {
        log.debug("REST request to get all ApplicationVersionRelations");
        return applicationVersionRelationRepository.findAll();
    }

    /**
     * GET  /application-version-relations/:id : get the "id" applicationVersionRelation.
     *
     * @param id the id of the applicationVersionRelation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationVersionRelation, or with status 404 (Not Found)
     */
    @GetMapping("/application-version-relations/{id}")
    @Timed
    public ResponseEntity<ApplicationVersionRelation> getApplicationVersionRelation(@PathVariable Long id) {
        log.debug("REST request to get ApplicationVersionRelation : {}", id);
        Optional<ApplicationVersionRelation> applicationVersionRelation = applicationVersionRelationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(applicationVersionRelation);
    }

    /**
     * DELETE  /application-version-relations/:id : delete the "id" applicationVersionRelation.
     *
     * @param id the id of the applicationVersionRelation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/application-version-relations/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicationVersionRelation(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationVersionRelation : {}", id);

        applicationVersionRelationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

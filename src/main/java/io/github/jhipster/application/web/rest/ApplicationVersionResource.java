package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.ApplicationVersion;
import io.github.jhipster.application.repository.ApplicationVersionRepository;
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
 * REST controller for managing ApplicationVersion.
 */
@RestController
@RequestMapping("/api")
public class ApplicationVersionResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationVersionResource.class);

    private static final String ENTITY_NAME = "applicationVersion";

    private final ApplicationVersionRepository applicationVersionRepository;

    public ApplicationVersionResource(ApplicationVersionRepository applicationVersionRepository) {
        this.applicationVersionRepository = applicationVersionRepository;
    }

    /**
     * POST  /application-versions : Create a new applicationVersion.
     *
     * @param applicationVersion the applicationVersion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applicationVersion, or with status 400 (Bad Request) if the applicationVersion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/application-versions")
    @Timed
    public ResponseEntity<ApplicationVersion> createApplicationVersion(@RequestBody ApplicationVersion applicationVersion) throws URISyntaxException {
        log.debug("REST request to save ApplicationVersion : {}", applicationVersion);
        if (applicationVersion.getId() != null) {
            throw new BadRequestAlertException("A new applicationVersion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationVersion result = applicationVersionRepository.save(applicationVersion);
        return ResponseEntity.created(new URI("/api/application-versions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /application-versions : Updates an existing applicationVersion.
     *
     * @param applicationVersion the applicationVersion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationVersion,
     * or with status 400 (Bad Request) if the applicationVersion is not valid,
     * or with status 500 (Internal Server Error) if the applicationVersion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/application-versions")
    @Timed
    public ResponseEntity<ApplicationVersion> updateApplicationVersion(@RequestBody ApplicationVersion applicationVersion) throws URISyntaxException {
        log.debug("REST request to update ApplicationVersion : {}", applicationVersion);
        if (applicationVersion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationVersion result = applicationVersionRepository.save(applicationVersion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applicationVersion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /application-versions : get all the applicationVersions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of applicationVersions in body
     */
    @GetMapping("/application-versions")
    @Timed
    public List<ApplicationVersion> getAllApplicationVersions() {
        log.debug("REST request to get all ApplicationVersions");
        return applicationVersionRepository.findAll();
    }

    /**
     * GET  /application-versions/:id : get the "id" applicationVersion.
     *
     * @param id the id of the applicationVersion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applicationVersion, or with status 404 (Not Found)
     */
    @GetMapping("/application-versions/{id}")
    @Timed
    public ResponseEntity<ApplicationVersion> getApplicationVersion(@PathVariable Long id) {
        log.debug("REST request to get ApplicationVersion : {}", id);
        Optional<ApplicationVersion> applicationVersion = applicationVersionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(applicationVersion);
    }

    /**
     * DELETE  /application-versions/:id : delete the "id" applicationVersion.
     *
     * @param id the id of the applicationVersion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/application-versions/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplicationVersion(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationVersion : {}", id);

        applicationVersionRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

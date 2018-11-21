package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.NetworkParameter;
import io.github.jhipster.application.service.NetworkParameterService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.NetworkParameterCriteria;
import io.github.jhipster.application.service.NetworkParameterQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NetworkParameter.
 */
@RestController
@RequestMapping("/api")
public class NetworkParameterResource {

    private final Logger log = LoggerFactory.getLogger(NetworkParameterResource.class);

    private static final String ENTITY_NAME = "networkParameter";

    private final NetworkParameterService networkParameterService;

    private final NetworkParameterQueryService networkParameterQueryService;

    public NetworkParameterResource(NetworkParameterService networkParameterService, NetworkParameterQueryService networkParameterQueryService) {
        this.networkParameterService = networkParameterService;
        this.networkParameterQueryService = networkParameterQueryService;
    }

    /**
     * POST  /network-parameters : Create a new networkParameter.
     *
     * @param networkParameter the networkParameter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new networkParameter, or with status 400 (Bad Request) if the networkParameter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/network-parameters")
    @Timed
    public ResponseEntity<NetworkParameter> createNetworkParameter(@RequestBody NetworkParameter networkParameter) throws URISyntaxException {
        log.debug("REST request to save NetworkParameter : {}", networkParameter);
        if (networkParameter.getId() != null) {
            throw new BadRequestAlertException("A new networkParameter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NetworkParameter result = networkParameterService.save(networkParameter);
        return ResponseEntity.created(new URI("/api/network-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /network-parameters : Updates an existing networkParameter.
     *
     * @param networkParameter the networkParameter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated networkParameter,
     * or with status 400 (Bad Request) if the networkParameter is not valid,
     * or with status 500 (Internal Server Error) if the networkParameter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/network-parameters")
    @Timed
    public ResponseEntity<NetworkParameter> updateNetworkParameter(@RequestBody NetworkParameter networkParameter) throws URISyntaxException {
        log.debug("REST request to update NetworkParameter : {}", networkParameter);
        if (networkParameter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NetworkParameter result = networkParameterService.save(networkParameter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, networkParameter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /network-parameters : get all the networkParameters.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of networkParameters in body
     */
    @GetMapping("/network-parameters")
    @Timed
    public ResponseEntity<List<NetworkParameter>> getAllNetworkParameters(NetworkParameterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NetworkParameters by criteria: {}", criteria);
        Page<NetworkParameter> page = networkParameterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/network-parameters");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /network-parameters/count : count all the networkParameters.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/network-parameters/count")
    @Timed
    public ResponseEntity<Long> countNetworkParameters(NetworkParameterCriteria criteria) {
        log.debug("REST request to count NetworkParameters by criteria: {}", criteria);
        return ResponseEntity.ok().body(networkParameterQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /network-parameters/:id : get the "id" networkParameter.
     *
     * @param id the id of the networkParameter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the networkParameter, or with status 404 (Not Found)
     */
    @GetMapping("/network-parameters/{id}")
    @Timed
    public ResponseEntity<NetworkParameter> getNetworkParameter(@PathVariable Long id) {
        log.debug("REST request to get NetworkParameter : {}", id);
        Optional<NetworkParameter> networkParameter = networkParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(networkParameter);
    }

    /**
     * DELETE  /network-parameters/:id : delete the "id" networkParameter.
     *
     * @param id the id of the networkParameter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/network-parameters/{id}")
    @Timed
    public ResponseEntity<Void> deleteNetworkParameter(@PathVariable Long id) {
        log.debug("REST request to delete NetworkParameter : {}", id);
        networkParameterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

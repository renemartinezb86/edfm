package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Rule;
import io.github.jhipster.application.service.RuleService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.RuleCriteria;
import io.github.jhipster.application.service.RuleQueryService;
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
 * REST controller for managing Rule.
 */
@RestController
@RequestMapping("/api")
public class RuleResource {

    private final Logger log = LoggerFactory.getLogger(RuleResource.class);

    private static final String ENTITY_NAME = "rule";

    private final RuleService ruleService;

    private final RuleQueryService ruleQueryService;

    public RuleResource(RuleService ruleService, RuleQueryService ruleQueryService) {
        this.ruleService = ruleService;
        this.ruleQueryService = ruleQueryService;
    }

    /**
     * POST  /rules : Create a new rule.
     *
     * @param rule the rule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rule, or with status 400 (Bad Request) if the rule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rules")
    @Timed
    public ResponseEntity<Rule> createRule(@RequestBody Rule rule) throws URISyntaxException {
        log.debug("REST request to save Rule : {}", rule);
        if (rule.getId() != null) {
            throw new BadRequestAlertException("A new rule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rule result = ruleService.save(rule);
        return ResponseEntity.created(new URI("/api/rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rules : Updates an existing rule.
     *
     * @param rule the rule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rule,
     * or with status 400 (Bad Request) if the rule is not valid,
     * or with status 500 (Internal Server Error) if the rule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rules")
    @Timed
    public ResponseEntity<Rule> updateRule(@RequestBody Rule rule) throws URISyntaxException {
        log.debug("REST request to update Rule : {}", rule);
        if (rule.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rule result = ruleService.save(rule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rules : get all the rules.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of rules in body
     */
    @GetMapping("/rules")
    @Timed
    public ResponseEntity<List<Rule>> getAllRules(RuleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Rules by criteria: {}", criteria);
        Page<Rule> page = ruleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rules");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /rules/count : count all the rules.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/rules/count")
    @Timed
    public ResponseEntity<Long> countRules(RuleCriteria criteria) {
        log.debug("REST request to count Rules by criteria: {}", criteria);
        return ResponseEntity.ok().body(ruleQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /rules/:id : get the "id" rule.
     *
     * @param id the id of the rule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rule, or with status 404 (Not Found)
     */
    @GetMapping("/rules/{id}")
    @Timed
    public ResponseEntity<Rule> getRule(@PathVariable Long id) {
        log.debug("REST request to get Rule : {}", id);
        Optional<Rule> rule = ruleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rule);
    }

    /**
     * DELETE  /rules/:id : delete the "id" rule.
     *
     * @param id the id of the rule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rules/{id}")
    @Timed
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        log.debug("REST request to delete Rule : {}", id);
        ruleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

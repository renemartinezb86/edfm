package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.Rule;
import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.repository.RuleRepository;
import io.github.jhipster.application.service.RuleService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.RuleCriteria;
import io.github.jhipster.application.service.RuleQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.RuleType;
/**
 * Test class for the RuleResource REST controller.
 *
 * @see RuleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class RuleResourceIntTest {

    private static final String DEFAULT_RULE_ID = "AAAAAAAAAA";
    private static final String UPDATED_RULE_ID = "BBBBBBBBBB";

    private static final RuleType DEFAULT_RULE_TYPE = RuleType.Availability;
    private static final RuleType UPDATED_RULE_TYPE = RuleType.Validation;

    private static final String DEFAULT_DEFINITION = "AAAAAAAAAA";
    private static final String UPDATED_DEFINITION = "BBBBBBBBBB";

    private static final String DEFAULT_SCENARIO = "AAAAAAAAAA";
    private static final String UPDATED_SCENARIO = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private RuleQueryService ruleQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRuleMockMvc;

    private Rule rule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RuleResource ruleResource = new RuleResource(ruleService, ruleQueryService);
        this.restRuleMockMvc = MockMvcBuilders.standaloneSetup(ruleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rule createEntity(EntityManager em) {
        Rule rule = new Rule()
            .ruleId(DEFAULT_RULE_ID)
            .ruleType(DEFAULT_RULE_TYPE)
            .definition(DEFAULT_DEFINITION)
            .scenario(DEFAULT_SCENARIO)
            .detail(DEFAULT_DETAIL);
        return rule;
    }

    @Before
    public void initTest() {
        rule = createEntity(em);
    }

    @Test
    @Transactional
    public void createRule() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();

        // Create the Rule
        restRuleMockMvc.perform(post("/api/rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isCreated());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate + 1);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getRuleId()).isEqualTo(DEFAULT_RULE_ID);
        assertThat(testRule.getRuleType()).isEqualTo(DEFAULT_RULE_TYPE);
        assertThat(testRule.getDefinition()).isEqualTo(DEFAULT_DEFINITION);
        assertThat(testRule.getScenario()).isEqualTo(DEFAULT_SCENARIO);
        assertThat(testRule.getDetail()).isEqualTo(DEFAULT_DETAIL);
    }

    @Test
    @Transactional
    public void createRuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleRepository.findAll().size();

        // Create the Rule with an existing ID
        rule.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleMockMvc.perform(post("/api/rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRules() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList
        restRuleMockMvc.perform(get("/api/rules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleId").value(hasItem(DEFAULT_RULE_ID.toString())))
            .andExpect(jsonPath("$.[*].ruleType").value(hasItem(DEFAULT_RULE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].definition").value(hasItem(DEFAULT_DEFINITION.toString())))
            .andExpect(jsonPath("$.[*].scenario").value(hasItem(DEFAULT_SCENARIO.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get the rule
        restRuleMockMvc.perform(get("/api/rules/{id}", rule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rule.getId().intValue()))
            .andExpect(jsonPath("$.ruleId").value(DEFAULT_RULE_ID.toString()))
            .andExpect(jsonPath("$.ruleType").value(DEFAULT_RULE_TYPE.toString()))
            .andExpect(jsonPath("$.definition").value(DEFAULT_DEFINITION.toString()))
            .andExpect(jsonPath("$.scenario").value(DEFAULT_SCENARIO.toString()))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllRulesByRuleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where ruleId equals to DEFAULT_RULE_ID
        defaultRuleShouldBeFound("ruleId.equals=" + DEFAULT_RULE_ID);

        // Get all the ruleList where ruleId equals to UPDATED_RULE_ID
        defaultRuleShouldNotBeFound("ruleId.equals=" + UPDATED_RULE_ID);
    }

    @Test
    @Transactional
    public void getAllRulesByRuleIdIsInShouldWork() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where ruleId in DEFAULT_RULE_ID or UPDATED_RULE_ID
        defaultRuleShouldBeFound("ruleId.in=" + DEFAULT_RULE_ID + "," + UPDATED_RULE_ID);

        // Get all the ruleList where ruleId equals to UPDATED_RULE_ID
        defaultRuleShouldNotBeFound("ruleId.in=" + UPDATED_RULE_ID);
    }

    @Test
    @Transactional
    public void getAllRulesByRuleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where ruleId is not null
        defaultRuleShouldBeFound("ruleId.specified=true");

        // Get all the ruleList where ruleId is null
        defaultRuleShouldNotBeFound("ruleId.specified=false");
    }

    @Test
    @Transactional
    public void getAllRulesByRuleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where ruleType equals to DEFAULT_RULE_TYPE
        defaultRuleShouldBeFound("ruleType.equals=" + DEFAULT_RULE_TYPE);

        // Get all the ruleList where ruleType equals to UPDATED_RULE_TYPE
        defaultRuleShouldNotBeFound("ruleType.equals=" + UPDATED_RULE_TYPE);
    }

    @Test
    @Transactional
    public void getAllRulesByRuleTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where ruleType in DEFAULT_RULE_TYPE or UPDATED_RULE_TYPE
        defaultRuleShouldBeFound("ruleType.in=" + DEFAULT_RULE_TYPE + "," + UPDATED_RULE_TYPE);

        // Get all the ruleList where ruleType equals to UPDATED_RULE_TYPE
        defaultRuleShouldNotBeFound("ruleType.in=" + UPDATED_RULE_TYPE);
    }

    @Test
    @Transactional
    public void getAllRulesByRuleTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where ruleType is not null
        defaultRuleShouldBeFound("ruleType.specified=true");

        // Get all the ruleList where ruleType is null
        defaultRuleShouldNotBeFound("ruleType.specified=false");
    }

    @Test
    @Transactional
    public void getAllRulesByDefinitionIsEqualToSomething() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where definition equals to DEFAULT_DEFINITION
        defaultRuleShouldBeFound("definition.equals=" + DEFAULT_DEFINITION);

        // Get all the ruleList where definition equals to UPDATED_DEFINITION
        defaultRuleShouldNotBeFound("definition.equals=" + UPDATED_DEFINITION);
    }

    @Test
    @Transactional
    public void getAllRulesByDefinitionIsInShouldWork() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where definition in DEFAULT_DEFINITION or UPDATED_DEFINITION
        defaultRuleShouldBeFound("definition.in=" + DEFAULT_DEFINITION + "," + UPDATED_DEFINITION);

        // Get all the ruleList where definition equals to UPDATED_DEFINITION
        defaultRuleShouldNotBeFound("definition.in=" + UPDATED_DEFINITION);
    }

    @Test
    @Transactional
    public void getAllRulesByDefinitionIsNullOrNotNull() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where definition is not null
        defaultRuleShouldBeFound("definition.specified=true");

        // Get all the ruleList where definition is null
        defaultRuleShouldNotBeFound("definition.specified=false");
    }

    @Test
    @Transactional
    public void getAllRulesByScenarioIsEqualToSomething() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where scenario equals to DEFAULT_SCENARIO
        defaultRuleShouldBeFound("scenario.equals=" + DEFAULT_SCENARIO);

        // Get all the ruleList where scenario equals to UPDATED_SCENARIO
        defaultRuleShouldNotBeFound("scenario.equals=" + UPDATED_SCENARIO);
    }

    @Test
    @Transactional
    public void getAllRulesByScenarioIsInShouldWork() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where scenario in DEFAULT_SCENARIO or UPDATED_SCENARIO
        defaultRuleShouldBeFound("scenario.in=" + DEFAULT_SCENARIO + "," + UPDATED_SCENARIO);

        // Get all the ruleList where scenario equals to UPDATED_SCENARIO
        defaultRuleShouldNotBeFound("scenario.in=" + UPDATED_SCENARIO);
    }

    @Test
    @Transactional
    public void getAllRulesByScenarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where scenario is not null
        defaultRuleShouldBeFound("scenario.specified=true");

        // Get all the ruleList where scenario is null
        defaultRuleShouldNotBeFound("scenario.specified=false");
    }

    @Test
    @Transactional
    public void getAllRulesByDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where detail equals to DEFAULT_DETAIL
        defaultRuleShouldBeFound("detail.equals=" + DEFAULT_DETAIL);

        // Get all the ruleList where detail equals to UPDATED_DETAIL
        defaultRuleShouldNotBeFound("detail.equals=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllRulesByDetailIsInShouldWork() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where detail in DEFAULT_DETAIL or UPDATED_DETAIL
        defaultRuleShouldBeFound("detail.in=" + DEFAULT_DETAIL + "," + UPDATED_DETAIL);

        // Get all the ruleList where detail equals to UPDATED_DETAIL
        defaultRuleShouldNotBeFound("detail.in=" + UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void getAllRulesByDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the ruleList where detail is not null
        defaultRuleShouldBeFound("detail.specified=true");

        // Get all the ruleList where detail is null
        defaultRuleShouldNotBeFound("detail.specified=false");
    }

    @Test
    @Transactional
    public void getAllRulesByProductOfferingIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductOffering productOffering = ProductOfferingResourceIntTest.createEntity(em);
        em.persist(productOffering);
        em.flush();
        rule.addProductOffering(productOffering);
        ruleRepository.saveAndFlush(rule);
        Long productOfferingId = productOffering.getId();

        // Get all the ruleList where productOffering equals to productOfferingId
        defaultRuleShouldBeFound("productOfferingId.equals=" + productOfferingId);

        // Get all the ruleList where productOffering equals to productOfferingId + 1
        defaultRuleShouldNotBeFound("productOfferingId.equals=" + (productOfferingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRuleShouldBeFound(String filter) throws Exception {
        restRuleMockMvc.perform(get("/api/rules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rule.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleId").value(hasItem(DEFAULT_RULE_ID.toString())))
            .andExpect(jsonPath("$.[*].ruleType").value(hasItem(DEFAULT_RULE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].definition").value(hasItem(DEFAULT_DEFINITION.toString())))
            .andExpect(jsonPath("$.[*].scenario").value(hasItem(DEFAULT_SCENARIO.toString())))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL.toString())));

        // Check, that the count call also returns 1
        restRuleMockMvc.perform(get("/api/rules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRuleShouldNotBeFound(String filter) throws Exception {
        restRuleMockMvc.perform(get("/api/rules?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRuleMockMvc.perform(get("/api/rules/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRule() throws Exception {
        // Get the rule
        restRuleMockMvc.perform(get("/api/rules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRule() throws Exception {
        // Initialize the database
        ruleService.save(rule);

        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // Update the rule
        Rule updatedRule = ruleRepository.findById(rule.getId()).get();
        // Disconnect from session so that the updates on updatedRule are not directly saved in db
        em.detach(updatedRule);
        updatedRule
            .ruleId(UPDATED_RULE_ID)
            .ruleType(UPDATED_RULE_TYPE)
            .definition(UPDATED_DEFINITION)
            .scenario(UPDATED_SCENARIO)
            .detail(UPDATED_DETAIL);

        restRuleMockMvc.perform(put("/api/rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRule)))
            .andExpect(status().isOk());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
        Rule testRule = ruleList.get(ruleList.size() - 1);
        assertThat(testRule.getRuleId()).isEqualTo(UPDATED_RULE_ID);
        assertThat(testRule.getRuleType()).isEqualTo(UPDATED_RULE_TYPE);
        assertThat(testRule.getDefinition()).isEqualTo(UPDATED_DEFINITION);
        assertThat(testRule.getScenario()).isEqualTo(UPDATED_SCENARIO);
        assertThat(testRule.getDetail()).isEqualTo(UPDATED_DETAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingRule() throws Exception {
        int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

        // Create the Rule

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleMockMvc.perform(put("/api/rules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rule)))
            .andExpect(status().isBadRequest());

        // Validate the Rule in the database
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRule() throws Exception {
        // Initialize the database
        ruleService.save(rule);

        int databaseSizeBeforeDelete = ruleRepository.findAll().size();

        // Get the rule
        restRuleMockMvc.perform(delete("/api/rules/{id}", rule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rule> ruleList = ruleRepository.findAll();
        assertThat(ruleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rule.class);
        Rule rule1 = new Rule();
        rule1.setId(1L);
        Rule rule2 = new Rule();
        rule2.setId(rule1.getId());
        assertThat(rule1).isEqualTo(rule2);
        rule2.setId(2L);
        assertThat(rule1).isNotEqualTo(rule2);
        rule1.setId(null);
        assertThat(rule1).isNotEqualTo(rule2);
    }
}

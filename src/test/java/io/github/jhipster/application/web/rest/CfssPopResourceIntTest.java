package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.CfssPop;
import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.repository.CfssPopRepository;
import io.github.jhipster.application.service.CfssPopService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.CfssPopCriteria;
import io.github.jhipster.application.service.CfssPopQueryService;

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

import io.github.jhipster.application.domain.enumeration.CfssPopType;
/**
 * Test class for the CfssPopResource REST controller.
 *
 * @see CfssPopResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class CfssPopResourceIntTest {

    private static final CfssPopType DEFAULT_CFSS_POP_TYPE = CfssPopType.Usage;
    private static final CfssPopType UPDATED_CFSS_POP_TYPE = CfssPopType.Usage;

    private static final String DEFAULT_CHARACTERISTIC = "AAAAAAAAAA";
    private static final String UPDATED_CHARACTERISTIC = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private CfssPopRepository cfssPopRepository;

    @Autowired
    private CfssPopService cfssPopService;

    @Autowired
    private CfssPopQueryService cfssPopQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCfssPopMockMvc;

    private CfssPop cfssPop;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CfssPopResource cfssPopResource = new CfssPopResource(cfssPopService, cfssPopQueryService);
        this.restCfssPopMockMvc = MockMvcBuilders.standaloneSetup(cfssPopResource)
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
    public static CfssPop createEntity(EntityManager em) {
        CfssPop cfssPop = new CfssPop()
            .cfssPopType(DEFAULT_CFSS_POP_TYPE)
            .characteristic(DEFAULT_CHARACTERISTIC)
            .value(DEFAULT_VALUE);
        return cfssPop;
    }

    @Before
    public void initTest() {
        cfssPop = createEntity(em);
    }

    @Test
    @Transactional
    public void createCfssPop() throws Exception {
        int databaseSizeBeforeCreate = cfssPopRepository.findAll().size();

        // Create the CfssPop
        restCfssPopMockMvc.perform(post("/api/cfss-pops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfssPop)))
            .andExpect(status().isCreated());

        // Validate the CfssPop in the database
        List<CfssPop> cfssPopList = cfssPopRepository.findAll();
        assertThat(cfssPopList).hasSize(databaseSizeBeforeCreate + 1);
        CfssPop testCfssPop = cfssPopList.get(cfssPopList.size() - 1);
        assertThat(testCfssPop.getCfssPopType()).isEqualTo(DEFAULT_CFSS_POP_TYPE);
        assertThat(testCfssPop.getCharacteristic()).isEqualTo(DEFAULT_CHARACTERISTIC);
        assertThat(testCfssPop.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCfssPopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cfssPopRepository.findAll().size();

        // Create the CfssPop with an existing ID
        cfssPop.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCfssPopMockMvc.perform(post("/api/cfss-pops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfssPop)))
            .andExpect(status().isBadRequest());

        // Validate the CfssPop in the database
        List<CfssPop> cfssPopList = cfssPopRepository.findAll();
        assertThat(cfssPopList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCfssPops() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList
        restCfssPopMockMvc.perform(get("/api/cfss-pops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cfssPop.getId().intValue())))
            .andExpect(jsonPath("$.[*].cfssPopType").value(hasItem(DEFAULT_CFSS_POP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].characteristic").value(hasItem(DEFAULT_CHARACTERISTIC.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getCfssPop() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get the cfssPop
        restCfssPopMockMvc.perform(get("/api/cfss-pops/{id}", cfssPop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cfssPop.getId().intValue()))
            .andExpect(jsonPath("$.cfssPopType").value(DEFAULT_CFSS_POP_TYPE.toString()))
            .andExpect(jsonPath("$.characteristic").value(DEFAULT_CHARACTERISTIC.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllCfssPopsByCfssPopTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList where cfssPopType equals to DEFAULT_CFSS_POP_TYPE
        defaultCfssPopShouldBeFound("cfssPopType.equals=" + DEFAULT_CFSS_POP_TYPE);

        // Get all the cfssPopList where cfssPopType equals to UPDATED_CFSS_POP_TYPE
        defaultCfssPopShouldNotBeFound("cfssPopType.equals=" + UPDATED_CFSS_POP_TYPE);
    }

    @Test
    @Transactional
    public void getAllCfssPopsByCfssPopTypeIsInShouldWork() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList where cfssPopType in DEFAULT_CFSS_POP_TYPE or UPDATED_CFSS_POP_TYPE
        defaultCfssPopShouldBeFound("cfssPopType.in=" + DEFAULT_CFSS_POP_TYPE + "," + UPDATED_CFSS_POP_TYPE);

        // Get all the cfssPopList where cfssPopType equals to UPDATED_CFSS_POP_TYPE
        defaultCfssPopShouldNotBeFound("cfssPopType.in=" + UPDATED_CFSS_POP_TYPE);
    }

    @Test
    @Transactional
    public void getAllCfssPopsByCfssPopTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList where cfssPopType is not null
        defaultCfssPopShouldBeFound("cfssPopType.specified=true");

        // Get all the cfssPopList where cfssPopType is null
        defaultCfssPopShouldNotBeFound("cfssPopType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCfssPopsByCharacteristicIsEqualToSomething() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList where characteristic equals to DEFAULT_CHARACTERISTIC
        defaultCfssPopShouldBeFound("characteristic.equals=" + DEFAULT_CHARACTERISTIC);

        // Get all the cfssPopList where characteristic equals to UPDATED_CHARACTERISTIC
        defaultCfssPopShouldNotBeFound("characteristic.equals=" + UPDATED_CHARACTERISTIC);
    }

    @Test
    @Transactional
    public void getAllCfssPopsByCharacteristicIsInShouldWork() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList where characteristic in DEFAULT_CHARACTERISTIC or UPDATED_CHARACTERISTIC
        defaultCfssPopShouldBeFound("characteristic.in=" + DEFAULT_CHARACTERISTIC + "," + UPDATED_CHARACTERISTIC);

        // Get all the cfssPopList where characteristic equals to UPDATED_CHARACTERISTIC
        defaultCfssPopShouldNotBeFound("characteristic.in=" + UPDATED_CHARACTERISTIC);
    }

    @Test
    @Transactional
    public void getAllCfssPopsByCharacteristicIsNullOrNotNull() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList where characteristic is not null
        defaultCfssPopShouldBeFound("characteristic.specified=true");

        // Get all the cfssPopList where characteristic is null
        defaultCfssPopShouldNotBeFound("characteristic.specified=false");
    }

    @Test
    @Transactional
    public void getAllCfssPopsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList where value equals to DEFAULT_VALUE
        defaultCfssPopShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the cfssPopList where value equals to UPDATED_VALUE
        defaultCfssPopShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCfssPopsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCfssPopShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the cfssPopList where value equals to UPDATED_VALUE
        defaultCfssPopShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllCfssPopsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        cfssPopRepository.saveAndFlush(cfssPop);

        // Get all the cfssPopList where value is not null
        defaultCfssPopShouldBeFound("value.specified=true");

        // Get all the cfssPopList where value is null
        defaultCfssPopShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllCfssPopsByProductOfferingIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductOffering productOffering = ProductOfferingResourceIntTest.createEntity(em);
        em.persist(productOffering);
        em.flush();
        cfssPop.addProductOffering(productOffering);
        cfssPopRepository.saveAndFlush(cfssPop);
        Long productOfferingId = productOffering.getId();

        // Get all the cfssPopList where productOffering equals to productOfferingId
        defaultCfssPopShouldBeFound("productOfferingId.equals=" + productOfferingId);

        // Get all the cfssPopList where productOffering equals to productOfferingId + 1
        defaultCfssPopShouldNotBeFound("productOfferingId.equals=" + (productOfferingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCfssPopShouldBeFound(String filter) throws Exception {
        restCfssPopMockMvc.perform(get("/api/cfss-pops?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cfssPop.getId().intValue())))
            .andExpect(jsonPath("$.[*].cfssPopType").value(hasItem(DEFAULT_CFSS_POP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].characteristic").value(hasItem(DEFAULT_CHARACTERISTIC.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));

        // Check, that the count call also returns 1
        restCfssPopMockMvc.perform(get("/api/cfss-pops/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCfssPopShouldNotBeFound(String filter) throws Exception {
        restCfssPopMockMvc.perform(get("/api/cfss-pops?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCfssPopMockMvc.perform(get("/api/cfss-pops/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCfssPop() throws Exception {
        // Get the cfssPop
        restCfssPopMockMvc.perform(get("/api/cfss-pops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCfssPop() throws Exception {
        // Initialize the database
        cfssPopService.save(cfssPop);

        int databaseSizeBeforeUpdate = cfssPopRepository.findAll().size();

        // Update the cfssPop
        CfssPop updatedCfssPop = cfssPopRepository.findById(cfssPop.getId()).get();
        // Disconnect from session so that the updates on updatedCfssPop are not directly saved in db
        em.detach(updatedCfssPop);
        updatedCfssPop
            .cfssPopType(UPDATED_CFSS_POP_TYPE)
            .characteristic(UPDATED_CHARACTERISTIC)
            .value(UPDATED_VALUE);

        restCfssPopMockMvc.perform(put("/api/cfss-pops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCfssPop)))
            .andExpect(status().isOk());

        // Validate the CfssPop in the database
        List<CfssPop> cfssPopList = cfssPopRepository.findAll();
        assertThat(cfssPopList).hasSize(databaseSizeBeforeUpdate);
        CfssPop testCfssPop = cfssPopList.get(cfssPopList.size() - 1);
        assertThat(testCfssPop.getCfssPopType()).isEqualTo(UPDATED_CFSS_POP_TYPE);
        assertThat(testCfssPop.getCharacteristic()).isEqualTo(UPDATED_CHARACTERISTIC);
        assertThat(testCfssPop.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingCfssPop() throws Exception {
        int databaseSizeBeforeUpdate = cfssPopRepository.findAll().size();

        // Create the CfssPop

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCfssPopMockMvc.perform(put("/api/cfss-pops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfssPop)))
            .andExpect(status().isBadRequest());

        // Validate the CfssPop in the database
        List<CfssPop> cfssPopList = cfssPopRepository.findAll();
        assertThat(cfssPopList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCfssPop() throws Exception {
        // Initialize the database
        cfssPopService.save(cfssPop);

        int databaseSizeBeforeDelete = cfssPopRepository.findAll().size();

        // Get the cfssPop
        restCfssPopMockMvc.perform(delete("/api/cfss-pops/{id}", cfssPop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CfssPop> cfssPopList = cfssPopRepository.findAll();
        assertThat(cfssPopList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CfssPop.class);
        CfssPop cfssPop1 = new CfssPop();
        cfssPop1.setId(1L);
        CfssPop cfssPop2 = new CfssPop();
        cfssPop2.setId(cfssPop1.getId());
        assertThat(cfssPop1).isEqualTo(cfssPop2);
        cfssPop2.setId(2L);
        assertThat(cfssPop1).isNotEqualTo(cfssPop2);
        cfssPop1.setId(null);
        assertThat(cfssPop1).isNotEqualTo(cfssPop2);
    }
}

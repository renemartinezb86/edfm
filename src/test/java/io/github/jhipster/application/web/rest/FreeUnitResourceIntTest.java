package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.FreeUnit;
import io.github.jhipster.application.domain.ChargingSystem;
import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.repository.FreeUnitRepository;
import io.github.jhipster.application.service.FreeUnitService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.FreeUnitCriteria;
import io.github.jhipster.application.service.FreeUnitQueryService;

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

import io.github.jhipster.application.domain.enumeration.FreeUnitType;
/**
 * Test class for the FreeUnitResource REST controller.
 *
 * @see FreeUnitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class FreeUnitResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final FreeUnitType DEFAULT_FREE_UNIT_TYPE = FreeUnitType.RecurringFee;
    private static final FreeUnitType UPDATED_FREE_UNIT_TYPE = FreeUnitType.SMS;

    private static final String DEFAULT_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_UNITS = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    @Autowired
    private FreeUnitRepository freeUnitRepository;

    @Autowired
    private FreeUnitService freeUnitService;

    @Autowired
    private FreeUnitQueryService freeUnitQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFreeUnitMockMvc;

    private FreeUnit freeUnit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FreeUnitResource freeUnitResource = new FreeUnitResource(freeUnitService, freeUnitQueryService);
        this.restFreeUnitMockMvc = MockMvcBuilders.standaloneSetup(freeUnitResource)
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
    public static FreeUnit createEntity(EntityManager em) {
        FreeUnit freeUnit = new FreeUnit()
            .name(DEFAULT_NAME)
            .freeUnitType(DEFAULT_FREE_UNIT_TYPE)
            .units(DEFAULT_UNITS)
            .amount(DEFAULT_AMOUNT);
        return freeUnit;
    }

    @Before
    public void initTest() {
        freeUnit = createEntity(em);
    }

    @Test
    @Transactional
    public void createFreeUnit() throws Exception {
        int databaseSizeBeforeCreate = freeUnitRepository.findAll().size();

        // Create the FreeUnit
        restFreeUnitMockMvc.perform(post("/api/free-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freeUnit)))
            .andExpect(status().isCreated());

        // Validate the FreeUnit in the database
        List<FreeUnit> freeUnitList = freeUnitRepository.findAll();
        assertThat(freeUnitList).hasSize(databaseSizeBeforeCreate + 1);
        FreeUnit testFreeUnit = freeUnitList.get(freeUnitList.size() - 1);
        assertThat(testFreeUnit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFreeUnit.getFreeUnitType()).isEqualTo(DEFAULT_FREE_UNIT_TYPE);
        assertThat(testFreeUnit.getUnits()).isEqualTo(DEFAULT_UNITS);
        assertThat(testFreeUnit.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFreeUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = freeUnitRepository.findAll().size();

        // Create the FreeUnit with an existing ID
        freeUnit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFreeUnitMockMvc.perform(post("/api/free-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freeUnit)))
            .andExpect(status().isBadRequest());

        // Validate the FreeUnit in the database
        List<FreeUnit> freeUnitList = freeUnitRepository.findAll();
        assertThat(freeUnitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFreeUnits() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList
        restFreeUnitMockMvc.perform(get("/api/free-units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freeUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].freeUnitType").value(hasItem(DEFAULT_FREE_UNIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getFreeUnit() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get the freeUnit
        restFreeUnitMockMvc.perform(get("/api/free-units/{id}", freeUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(freeUnit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.freeUnitType").value(DEFAULT_FREE_UNIT_TYPE.toString()))
            .andExpect(jsonPath("$.units").value(DEFAULT_UNITS.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where name equals to DEFAULT_NAME
        defaultFreeUnitShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the freeUnitList where name equals to UPDATED_NAME
        defaultFreeUnitShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFreeUnitShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the freeUnitList where name equals to UPDATED_NAME
        defaultFreeUnitShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where name is not null
        defaultFreeUnitShouldBeFound("name.specified=true");

        // Get all the freeUnitList where name is null
        defaultFreeUnitShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByFreeUnitTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where freeUnitType equals to DEFAULT_FREE_UNIT_TYPE
        defaultFreeUnitShouldBeFound("freeUnitType.equals=" + DEFAULT_FREE_UNIT_TYPE);

        // Get all the freeUnitList where freeUnitType equals to UPDATED_FREE_UNIT_TYPE
        defaultFreeUnitShouldNotBeFound("freeUnitType.equals=" + UPDATED_FREE_UNIT_TYPE);
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByFreeUnitTypeIsInShouldWork() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where freeUnitType in DEFAULT_FREE_UNIT_TYPE or UPDATED_FREE_UNIT_TYPE
        defaultFreeUnitShouldBeFound("freeUnitType.in=" + DEFAULT_FREE_UNIT_TYPE + "," + UPDATED_FREE_UNIT_TYPE);

        // Get all the freeUnitList where freeUnitType equals to UPDATED_FREE_UNIT_TYPE
        defaultFreeUnitShouldNotBeFound("freeUnitType.in=" + UPDATED_FREE_UNIT_TYPE);
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByFreeUnitTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where freeUnitType is not null
        defaultFreeUnitShouldBeFound("freeUnitType.specified=true");

        // Get all the freeUnitList where freeUnitType is null
        defaultFreeUnitShouldNotBeFound("freeUnitType.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByUnitsIsEqualToSomething() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where units equals to DEFAULT_UNITS
        defaultFreeUnitShouldBeFound("units.equals=" + DEFAULT_UNITS);

        // Get all the freeUnitList where units equals to UPDATED_UNITS
        defaultFreeUnitShouldNotBeFound("units.equals=" + UPDATED_UNITS);
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByUnitsIsInShouldWork() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where units in DEFAULT_UNITS or UPDATED_UNITS
        defaultFreeUnitShouldBeFound("units.in=" + DEFAULT_UNITS + "," + UPDATED_UNITS);

        // Get all the freeUnitList where units equals to UPDATED_UNITS
        defaultFreeUnitShouldNotBeFound("units.in=" + UPDATED_UNITS);
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByUnitsIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where units is not null
        defaultFreeUnitShouldBeFound("units.specified=true");

        // Get all the freeUnitList where units is null
        defaultFreeUnitShouldNotBeFound("units.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where amount equals to DEFAULT_AMOUNT
        defaultFreeUnitShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the freeUnitList where amount equals to UPDATED_AMOUNT
        defaultFreeUnitShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultFreeUnitShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the freeUnitList where amount equals to UPDATED_AMOUNT
        defaultFreeUnitShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        freeUnitRepository.saveAndFlush(freeUnit);

        // Get all the freeUnitList where amount is not null
        defaultFreeUnitShouldBeFound("amount.specified=true");

        // Get all the freeUnitList where amount is null
        defaultFreeUnitShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllFreeUnitsByChargingSystemIsEqualToSomething() throws Exception {
        // Initialize the database
        ChargingSystem chargingSystem = ChargingSystemResourceIntTest.createEntity(em);
        em.persist(chargingSystem);
        em.flush();
        freeUnit.setChargingSystem(chargingSystem);
        chargingSystem.setFreeUnit(freeUnit);
        freeUnitRepository.saveAndFlush(freeUnit);
        Long chargingSystemId = chargingSystem.getId();

        // Get all the freeUnitList where chargingSystem equals to chargingSystemId
        defaultFreeUnitShouldBeFound("chargingSystemId.equals=" + chargingSystemId);

        // Get all the freeUnitList where chargingSystem equals to chargingSystemId + 1
        defaultFreeUnitShouldNotBeFound("chargingSystemId.equals=" + (chargingSystemId + 1));
    }


    @Test
    @Transactional
    public void getAllFreeUnitsByProductOfferingIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductOffering productOffering = ProductOfferingResourceIntTest.createEntity(em);
        em.persist(productOffering);
        em.flush();
        freeUnit.addProductOffering(productOffering);
        freeUnitRepository.saveAndFlush(freeUnit);
        Long productOfferingId = productOffering.getId();

        // Get all the freeUnitList where productOffering equals to productOfferingId
        defaultFreeUnitShouldBeFound("productOfferingId.equals=" + productOfferingId);

        // Get all the freeUnitList where productOffering equals to productOfferingId + 1
        defaultFreeUnitShouldNotBeFound("productOfferingId.equals=" + (productOfferingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFreeUnitShouldBeFound(String filter) throws Exception {
        restFreeUnitMockMvc.perform(get("/api/free-units?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freeUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].freeUnitType").value(hasItem(DEFAULT_FREE_UNIT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].units").value(hasItem(DEFAULT_UNITS.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())));

        // Check, that the count call also returns 1
        restFreeUnitMockMvc.perform(get("/api/free-units/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFreeUnitShouldNotBeFound(String filter) throws Exception {
        restFreeUnitMockMvc.perform(get("/api/free-units?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFreeUnitMockMvc.perform(get("/api/free-units/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFreeUnit() throws Exception {
        // Get the freeUnit
        restFreeUnitMockMvc.perform(get("/api/free-units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreeUnit() throws Exception {
        // Initialize the database
        freeUnitService.save(freeUnit);

        int databaseSizeBeforeUpdate = freeUnitRepository.findAll().size();

        // Update the freeUnit
        FreeUnit updatedFreeUnit = freeUnitRepository.findById(freeUnit.getId()).get();
        // Disconnect from session so that the updates on updatedFreeUnit are not directly saved in db
        em.detach(updatedFreeUnit);
        updatedFreeUnit
            .name(UPDATED_NAME)
            .freeUnitType(UPDATED_FREE_UNIT_TYPE)
            .units(UPDATED_UNITS)
            .amount(UPDATED_AMOUNT);

        restFreeUnitMockMvc.perform(put("/api/free-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFreeUnit)))
            .andExpect(status().isOk());

        // Validate the FreeUnit in the database
        List<FreeUnit> freeUnitList = freeUnitRepository.findAll();
        assertThat(freeUnitList).hasSize(databaseSizeBeforeUpdate);
        FreeUnit testFreeUnit = freeUnitList.get(freeUnitList.size() - 1);
        assertThat(testFreeUnit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFreeUnit.getFreeUnitType()).isEqualTo(UPDATED_FREE_UNIT_TYPE);
        assertThat(testFreeUnit.getUnits()).isEqualTo(UPDATED_UNITS);
        assertThat(testFreeUnit.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingFreeUnit() throws Exception {
        int databaseSizeBeforeUpdate = freeUnitRepository.findAll().size();

        // Create the FreeUnit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFreeUnitMockMvc.perform(put("/api/free-units")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freeUnit)))
            .andExpect(status().isBadRequest());

        // Validate the FreeUnit in the database
        List<FreeUnit> freeUnitList = freeUnitRepository.findAll();
        assertThat(freeUnitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFreeUnit() throws Exception {
        // Initialize the database
        freeUnitService.save(freeUnit);

        int databaseSizeBeforeDelete = freeUnitRepository.findAll().size();

        // Get the freeUnit
        restFreeUnitMockMvc.perform(delete("/api/free-units/{id}", freeUnit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FreeUnit> freeUnitList = freeUnitRepository.findAll();
        assertThat(freeUnitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FreeUnit.class);
        FreeUnit freeUnit1 = new FreeUnit();
        freeUnit1.setId(1L);
        FreeUnit freeUnit2 = new FreeUnit();
        freeUnit2.setId(freeUnit1.getId());
        assertThat(freeUnit1).isEqualTo(freeUnit2);
        freeUnit2.setId(2L);
        assertThat(freeUnit1).isNotEqualTo(freeUnit2);
        freeUnit1.setId(null);
        assertThat(freeUnit1).isNotEqualTo(freeUnit2);
    }
}

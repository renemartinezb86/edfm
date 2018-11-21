package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.ChargingSystem;
import io.github.jhipster.application.domain.Service;
import io.github.jhipster.application.domain.FreeUnit;
import io.github.jhipster.application.repository.ChargingSystemRepository;
import io.github.jhipster.application.service.ChargingSystemService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.ChargingSystemCriteria;
import io.github.jhipster.application.service.ChargingSystemQueryService;

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

/**
 * Test class for the ChargingSystemResource REST controller.
 *
 * @see ChargingSystemResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class ChargingSystemResourceIntTest {

    private static final String DEFAULT_SERVICE_CLASS_ID = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_CLASS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OFFER_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_OFFER_TEMPLATE = "BBBBBBBBBB";

    private static final String DEFAULT_CHARACTERISTIC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHARACTERISTIC_NAME = "BBBBBBBBBB";

    @Autowired
    private ChargingSystemRepository chargingSystemRepository;

    @Autowired
    private ChargingSystemService chargingSystemService;

    @Autowired
    private ChargingSystemQueryService chargingSystemQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChargingSystemMockMvc;

    private ChargingSystem chargingSystem;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChargingSystemResource chargingSystemResource = new ChargingSystemResource(chargingSystemService, chargingSystemQueryService);
        this.restChargingSystemMockMvc = MockMvcBuilders.standaloneSetup(chargingSystemResource)
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
    public static ChargingSystem createEntity(EntityManager em) {
        ChargingSystem chargingSystem = new ChargingSystem()
            .serviceClassId(DEFAULT_SERVICE_CLASS_ID)
            .offerTemplate(DEFAULT_OFFER_TEMPLATE)
            .characteristicName(DEFAULT_CHARACTERISTIC_NAME);
        return chargingSystem;
    }

    @Before
    public void initTest() {
        chargingSystem = createEntity(em);
    }

    @Test
    @Transactional
    public void createChargingSystem() throws Exception {
        int databaseSizeBeforeCreate = chargingSystemRepository.findAll().size();

        // Create the ChargingSystem
        restChargingSystemMockMvc.perform(post("/api/charging-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargingSystem)))
            .andExpect(status().isCreated());

        // Validate the ChargingSystem in the database
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeCreate + 1);
        ChargingSystem testChargingSystem = chargingSystemList.get(chargingSystemList.size() - 1);
        assertThat(testChargingSystem.getServiceClassId()).isEqualTo(DEFAULT_SERVICE_CLASS_ID);
        assertThat(testChargingSystem.getOfferTemplate()).isEqualTo(DEFAULT_OFFER_TEMPLATE);
        assertThat(testChargingSystem.getCharacteristicName()).isEqualTo(DEFAULT_CHARACTERISTIC_NAME);
    }

    @Test
    @Transactional
    public void createChargingSystemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chargingSystemRepository.findAll().size();

        // Create the ChargingSystem with an existing ID
        chargingSystem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargingSystemMockMvc.perform(post("/api/charging-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargingSystem)))
            .andExpect(status().isBadRequest());

        // Validate the ChargingSystem in the database
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChargingSystems() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList
        restChargingSystemMockMvc.perform(get("/api/charging-systems?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargingSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceClassId").value(hasItem(DEFAULT_SERVICE_CLASS_ID.toString())))
            .andExpect(jsonPath("$.[*].offerTemplate").value(hasItem(DEFAULT_OFFER_TEMPLATE.toString())))
            .andExpect(jsonPath("$.[*].characteristicName").value(hasItem(DEFAULT_CHARACTERISTIC_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getChargingSystem() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get the chargingSystem
        restChargingSystemMockMvc.perform(get("/api/charging-systems/{id}", chargingSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chargingSystem.getId().intValue()))
            .andExpect(jsonPath("$.serviceClassId").value(DEFAULT_SERVICE_CLASS_ID.toString()))
            .andExpect(jsonPath("$.offerTemplate").value(DEFAULT_OFFER_TEMPLATE.toString()))
            .andExpect(jsonPath("$.characteristicName").value(DEFAULT_CHARACTERISTIC_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByServiceClassIdIsEqualToSomething() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList where serviceClassId equals to DEFAULT_SERVICE_CLASS_ID
        defaultChargingSystemShouldBeFound("serviceClassId.equals=" + DEFAULT_SERVICE_CLASS_ID);

        // Get all the chargingSystemList where serviceClassId equals to UPDATED_SERVICE_CLASS_ID
        defaultChargingSystemShouldNotBeFound("serviceClassId.equals=" + UPDATED_SERVICE_CLASS_ID);
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByServiceClassIdIsInShouldWork() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList where serviceClassId in DEFAULT_SERVICE_CLASS_ID or UPDATED_SERVICE_CLASS_ID
        defaultChargingSystemShouldBeFound("serviceClassId.in=" + DEFAULT_SERVICE_CLASS_ID + "," + UPDATED_SERVICE_CLASS_ID);

        // Get all the chargingSystemList where serviceClassId equals to UPDATED_SERVICE_CLASS_ID
        defaultChargingSystemShouldNotBeFound("serviceClassId.in=" + UPDATED_SERVICE_CLASS_ID);
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByServiceClassIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList where serviceClassId is not null
        defaultChargingSystemShouldBeFound("serviceClassId.specified=true");

        // Get all the chargingSystemList where serviceClassId is null
        defaultChargingSystemShouldNotBeFound("serviceClassId.specified=false");
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByOfferTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList where offerTemplate equals to DEFAULT_OFFER_TEMPLATE
        defaultChargingSystemShouldBeFound("offerTemplate.equals=" + DEFAULT_OFFER_TEMPLATE);

        // Get all the chargingSystemList where offerTemplate equals to UPDATED_OFFER_TEMPLATE
        defaultChargingSystemShouldNotBeFound("offerTemplate.equals=" + UPDATED_OFFER_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByOfferTemplateIsInShouldWork() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList where offerTemplate in DEFAULT_OFFER_TEMPLATE or UPDATED_OFFER_TEMPLATE
        defaultChargingSystemShouldBeFound("offerTemplate.in=" + DEFAULT_OFFER_TEMPLATE + "," + UPDATED_OFFER_TEMPLATE);

        // Get all the chargingSystemList where offerTemplate equals to UPDATED_OFFER_TEMPLATE
        defaultChargingSystemShouldNotBeFound("offerTemplate.in=" + UPDATED_OFFER_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByOfferTemplateIsNullOrNotNull() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList where offerTemplate is not null
        defaultChargingSystemShouldBeFound("offerTemplate.specified=true");

        // Get all the chargingSystemList where offerTemplate is null
        defaultChargingSystemShouldNotBeFound("offerTemplate.specified=false");
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByCharacteristicNameIsEqualToSomething() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList where characteristicName equals to DEFAULT_CHARACTERISTIC_NAME
        defaultChargingSystemShouldBeFound("characteristicName.equals=" + DEFAULT_CHARACTERISTIC_NAME);

        // Get all the chargingSystemList where characteristicName equals to UPDATED_CHARACTERISTIC_NAME
        defaultChargingSystemShouldNotBeFound("characteristicName.equals=" + UPDATED_CHARACTERISTIC_NAME);
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByCharacteristicNameIsInShouldWork() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList where characteristicName in DEFAULT_CHARACTERISTIC_NAME or UPDATED_CHARACTERISTIC_NAME
        defaultChargingSystemShouldBeFound("characteristicName.in=" + DEFAULT_CHARACTERISTIC_NAME + "," + UPDATED_CHARACTERISTIC_NAME);

        // Get all the chargingSystemList where characteristicName equals to UPDATED_CHARACTERISTIC_NAME
        defaultChargingSystemShouldNotBeFound("characteristicName.in=" + UPDATED_CHARACTERISTIC_NAME);
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByCharacteristicNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        chargingSystemRepository.saveAndFlush(chargingSystem);

        // Get all the chargingSystemList where characteristicName is not null
        defaultChargingSystemShouldBeFound("characteristicName.specified=true");

        // Get all the chargingSystemList where characteristicName is null
        defaultChargingSystemShouldNotBeFound("characteristicName.specified=false");
    }

    @Test
    @Transactional
    public void getAllChargingSystemsByServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        Service service = ServiceResourceIntTest.createEntity(em);
        em.persist(service);
        em.flush();
        chargingSystem.setService(service);
        chargingSystemRepository.saveAndFlush(chargingSystem);
        Long serviceId = service.getId();

        // Get all the chargingSystemList where service equals to serviceId
        defaultChargingSystemShouldBeFound("serviceId.equals=" + serviceId);

        // Get all the chargingSystemList where service equals to serviceId + 1
        defaultChargingSystemShouldNotBeFound("serviceId.equals=" + (serviceId + 1));
    }


    @Test
    @Transactional
    public void getAllChargingSystemsByFreeUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        FreeUnit freeUnit = FreeUnitResourceIntTest.createEntity(em);
        em.persist(freeUnit);
        em.flush();
        chargingSystem.setFreeUnit(freeUnit);
        chargingSystemRepository.saveAndFlush(chargingSystem);
        Long freeUnitId = freeUnit.getId();

        // Get all the chargingSystemList where freeUnit equals to freeUnitId
        defaultChargingSystemShouldBeFound("freeUnitId.equals=" + freeUnitId);

        // Get all the chargingSystemList where freeUnit equals to freeUnitId + 1
        defaultChargingSystemShouldNotBeFound("freeUnitId.equals=" + (freeUnitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultChargingSystemShouldBeFound(String filter) throws Exception {
        restChargingSystemMockMvc.perform(get("/api/charging-systems?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargingSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceClassId").value(hasItem(DEFAULT_SERVICE_CLASS_ID.toString())))
            .andExpect(jsonPath("$.[*].offerTemplate").value(hasItem(DEFAULT_OFFER_TEMPLATE.toString())))
            .andExpect(jsonPath("$.[*].characteristicName").value(hasItem(DEFAULT_CHARACTERISTIC_NAME.toString())));

        // Check, that the count call also returns 1
        restChargingSystemMockMvc.perform(get("/api/charging-systems/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultChargingSystemShouldNotBeFound(String filter) throws Exception {
        restChargingSystemMockMvc.perform(get("/api/charging-systems?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChargingSystemMockMvc.perform(get("/api/charging-systems/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingChargingSystem() throws Exception {
        // Get the chargingSystem
        restChargingSystemMockMvc.perform(get("/api/charging-systems/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChargingSystem() throws Exception {
        // Initialize the database
        chargingSystemService.save(chargingSystem);

        int databaseSizeBeforeUpdate = chargingSystemRepository.findAll().size();

        // Update the chargingSystem
        ChargingSystem updatedChargingSystem = chargingSystemRepository.findById(chargingSystem.getId()).get();
        // Disconnect from session so that the updates on updatedChargingSystem are not directly saved in db
        em.detach(updatedChargingSystem);
        updatedChargingSystem
            .serviceClassId(UPDATED_SERVICE_CLASS_ID)
            .offerTemplate(UPDATED_OFFER_TEMPLATE)
            .characteristicName(UPDATED_CHARACTERISTIC_NAME);

        restChargingSystemMockMvc.perform(put("/api/charging-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChargingSystem)))
            .andExpect(status().isOk());

        // Validate the ChargingSystem in the database
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeUpdate);
        ChargingSystem testChargingSystem = chargingSystemList.get(chargingSystemList.size() - 1);
        assertThat(testChargingSystem.getServiceClassId()).isEqualTo(UPDATED_SERVICE_CLASS_ID);
        assertThat(testChargingSystem.getOfferTemplate()).isEqualTo(UPDATED_OFFER_TEMPLATE);
        assertThat(testChargingSystem.getCharacteristicName()).isEqualTo(UPDATED_CHARACTERISTIC_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingChargingSystem() throws Exception {
        int databaseSizeBeforeUpdate = chargingSystemRepository.findAll().size();

        // Create the ChargingSystem

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChargingSystemMockMvc.perform(put("/api/charging-systems")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargingSystem)))
            .andExpect(status().isBadRequest());

        // Validate the ChargingSystem in the database
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChargingSystem() throws Exception {
        // Initialize the database
        chargingSystemService.save(chargingSystem);

        int databaseSizeBeforeDelete = chargingSystemRepository.findAll().size();

        // Get the chargingSystem
        restChargingSystemMockMvc.perform(delete("/api/charging-systems/{id}", chargingSystem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChargingSystem> chargingSystemList = chargingSystemRepository.findAll();
        assertThat(chargingSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargingSystem.class);
        ChargingSystem chargingSystem1 = new ChargingSystem();
        chargingSystem1.setId(1L);
        ChargingSystem chargingSystem2 = new ChargingSystem();
        chargingSystem2.setId(chargingSystem1.getId());
        assertThat(chargingSystem1).isEqualTo(chargingSystem2);
        chargingSystem2.setId(2L);
        assertThat(chargingSystem1).isNotEqualTo(chargingSystem2);
        chargingSystem1.setId(null);
        assertThat(chargingSystem1).isNotEqualTo(chargingSystem2);
    }
}

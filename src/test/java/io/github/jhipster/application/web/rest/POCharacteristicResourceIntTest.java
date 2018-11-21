package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.POCharacteristic;
import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.repository.POCharacteristicRepository;
import io.github.jhipster.application.service.POCharacteristicService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.POCharacteristicCriteria;
import io.github.jhipster.application.service.POCharacteristicQueryService;

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
 * Test class for the POCharacteristicResource REST controller.
 *
 * @see POCharacteristicResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class POCharacteristicResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private POCharacteristicRepository pOCharacteristicRepository;

    @Autowired
    private POCharacteristicService pOCharacteristicService;

    @Autowired
    private POCharacteristicQueryService pOCharacteristicQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPOCharacteristicMockMvc;

    private POCharacteristic pOCharacteristic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final POCharacteristicResource pOCharacteristicResource = new POCharacteristicResource(pOCharacteristicService, pOCharacteristicQueryService);
        this.restPOCharacteristicMockMvc = MockMvcBuilders.standaloneSetup(pOCharacteristicResource)
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
    public static POCharacteristic createEntity(EntityManager em) {
        POCharacteristic pOCharacteristic = new POCharacteristic()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return pOCharacteristic;
    }

    @Before
    public void initTest() {
        pOCharacteristic = createEntity(em);
    }

    @Test
    @Transactional
    public void createPOCharacteristic() throws Exception {
        int databaseSizeBeforeCreate = pOCharacteristicRepository.findAll().size();

        // Create the POCharacteristic
        restPOCharacteristicMockMvc.perform(post("/api/po-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOCharacteristic)))
            .andExpect(status().isCreated());

        // Validate the POCharacteristic in the database
        List<POCharacteristic> pOCharacteristicList = pOCharacteristicRepository.findAll();
        assertThat(pOCharacteristicList).hasSize(databaseSizeBeforeCreate + 1);
        POCharacteristic testPOCharacteristic = pOCharacteristicList.get(pOCharacteristicList.size() - 1);
        assertThat(testPOCharacteristic.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPOCharacteristic.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPOCharacteristicWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pOCharacteristicRepository.findAll().size();

        // Create the POCharacteristic with an existing ID
        pOCharacteristic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPOCharacteristicMockMvc.perform(post("/api/po-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOCharacteristic)))
            .andExpect(status().isBadRequest());

        // Validate the POCharacteristic in the database
        List<POCharacteristic> pOCharacteristicList = pOCharacteristicRepository.findAll();
        assertThat(pOCharacteristicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPOCharacteristics() throws Exception {
        // Initialize the database
        pOCharacteristicRepository.saveAndFlush(pOCharacteristic);

        // Get all the pOCharacteristicList
        restPOCharacteristicMockMvc.perform(get("/api/po-characteristics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pOCharacteristic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getPOCharacteristic() throws Exception {
        // Initialize the database
        pOCharacteristicRepository.saveAndFlush(pOCharacteristic);

        // Get the pOCharacteristic
        restPOCharacteristicMockMvc.perform(get("/api/po-characteristics/{id}", pOCharacteristic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pOCharacteristic.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllPOCharacteristicsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pOCharacteristicRepository.saveAndFlush(pOCharacteristic);

        // Get all the pOCharacteristicList where name equals to DEFAULT_NAME
        defaultPOCharacteristicShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the pOCharacteristicList where name equals to UPDATED_NAME
        defaultPOCharacteristicShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPOCharacteristicsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        pOCharacteristicRepository.saveAndFlush(pOCharacteristic);

        // Get all the pOCharacteristicList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPOCharacteristicShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the pOCharacteristicList where name equals to UPDATED_NAME
        defaultPOCharacteristicShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPOCharacteristicsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pOCharacteristicRepository.saveAndFlush(pOCharacteristic);

        // Get all the pOCharacteristicList where name is not null
        defaultPOCharacteristicShouldBeFound("name.specified=true");

        // Get all the pOCharacteristicList where name is null
        defaultPOCharacteristicShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllPOCharacteristicsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        pOCharacteristicRepository.saveAndFlush(pOCharacteristic);

        // Get all the pOCharacteristicList where value equals to DEFAULT_VALUE
        defaultPOCharacteristicShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the pOCharacteristicList where value equals to UPDATED_VALUE
        defaultPOCharacteristicShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPOCharacteristicsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        pOCharacteristicRepository.saveAndFlush(pOCharacteristic);

        // Get all the pOCharacteristicList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultPOCharacteristicShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the pOCharacteristicList where value equals to UPDATED_VALUE
        defaultPOCharacteristicShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllPOCharacteristicsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        pOCharacteristicRepository.saveAndFlush(pOCharacteristic);

        // Get all the pOCharacteristicList where value is not null
        defaultPOCharacteristicShouldBeFound("value.specified=true");

        // Get all the pOCharacteristicList where value is null
        defaultPOCharacteristicShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllPOCharacteristicsByProductOfferingIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductOffering productOffering = ProductOfferingResourceIntTest.createEntity(em);
        em.persist(productOffering);
        em.flush();
        pOCharacteristic.setProductOffering(productOffering);
        pOCharacteristicRepository.saveAndFlush(pOCharacteristic);
        Long productOfferingId = productOffering.getId();

        // Get all the pOCharacteristicList where productOffering equals to productOfferingId
        defaultPOCharacteristicShouldBeFound("productOfferingId.equals=" + productOfferingId);

        // Get all the pOCharacteristicList where productOffering equals to productOfferingId + 1
        defaultPOCharacteristicShouldNotBeFound("productOfferingId.equals=" + (productOfferingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPOCharacteristicShouldBeFound(String filter) throws Exception {
        restPOCharacteristicMockMvc.perform(get("/api/po-characteristics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pOCharacteristic.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));

        // Check, that the count call also returns 1
        restPOCharacteristicMockMvc.perform(get("/api/po-characteristics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPOCharacteristicShouldNotBeFound(String filter) throws Exception {
        restPOCharacteristicMockMvc.perform(get("/api/po-characteristics?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPOCharacteristicMockMvc.perform(get("/api/po-characteristics/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPOCharacteristic() throws Exception {
        // Get the pOCharacteristic
        restPOCharacteristicMockMvc.perform(get("/api/po-characteristics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePOCharacteristic() throws Exception {
        // Initialize the database
        pOCharacteristicService.save(pOCharacteristic);

        int databaseSizeBeforeUpdate = pOCharacteristicRepository.findAll().size();

        // Update the pOCharacteristic
        POCharacteristic updatedPOCharacteristic = pOCharacteristicRepository.findById(pOCharacteristic.getId()).get();
        // Disconnect from session so that the updates on updatedPOCharacteristic are not directly saved in db
        em.detach(updatedPOCharacteristic);
        updatedPOCharacteristic
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);

        restPOCharacteristicMockMvc.perform(put("/api/po-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPOCharacteristic)))
            .andExpect(status().isOk());

        // Validate the POCharacteristic in the database
        List<POCharacteristic> pOCharacteristicList = pOCharacteristicRepository.findAll();
        assertThat(pOCharacteristicList).hasSize(databaseSizeBeforeUpdate);
        POCharacteristic testPOCharacteristic = pOCharacteristicList.get(pOCharacteristicList.size() - 1);
        assertThat(testPOCharacteristic.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPOCharacteristic.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingPOCharacteristic() throws Exception {
        int databaseSizeBeforeUpdate = pOCharacteristicRepository.findAll().size();

        // Create the POCharacteristic

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPOCharacteristicMockMvc.perform(put("/api/po-characteristics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pOCharacteristic)))
            .andExpect(status().isBadRequest());

        // Validate the POCharacteristic in the database
        List<POCharacteristic> pOCharacteristicList = pOCharacteristicRepository.findAll();
        assertThat(pOCharacteristicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePOCharacteristic() throws Exception {
        // Initialize the database
        pOCharacteristicService.save(pOCharacteristic);

        int databaseSizeBeforeDelete = pOCharacteristicRepository.findAll().size();

        // Get the pOCharacteristic
        restPOCharacteristicMockMvc.perform(delete("/api/po-characteristics/{id}", pOCharacteristic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<POCharacteristic> pOCharacteristicList = pOCharacteristicRepository.findAll();
        assertThat(pOCharacteristicList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(POCharacteristic.class);
        POCharacteristic pOCharacteristic1 = new POCharacteristic();
        pOCharacteristic1.setId(1L);
        POCharacteristic pOCharacteristic2 = new POCharacteristic();
        pOCharacteristic2.setId(pOCharacteristic1.getId());
        assertThat(pOCharacteristic1).isEqualTo(pOCharacteristic2);
        pOCharacteristic2.setId(2L);
        assertThat(pOCharacteristic1).isNotEqualTo(pOCharacteristic2);
        pOCharacteristic1.setId(null);
        assertThat(pOCharacteristic1).isNotEqualTo(pOCharacteristic2);
    }
}

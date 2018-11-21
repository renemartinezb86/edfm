package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.BucoSheet;
import io.github.jhipster.application.domain.BucoVersion;
import io.github.jhipster.application.repository.BucoSheetRepository;
import io.github.jhipster.application.service.BucoSheetService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.BucoSheetCriteria;
import io.github.jhipster.application.service.BucoSheetQueryService;

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

import io.github.jhipster.application.domain.enumeration.SheetType;
/**
 * Test class for the BucoSheetResource REST controller.
 *
 * @see BucoSheetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class BucoSheetResourceIntTest {

    private static final String DEFAULT_SHEET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHEET_NAME = "BBBBBBBBBB";

    private static final SheetType DEFAULT_SHEET_TYPE = SheetType.PO;
    private static final SheetType UPDATED_SHEET_TYPE = SheetType.TEMPLATE;

    @Autowired
    private BucoSheetRepository bucoSheetRepository;

    @Autowired
    private BucoSheetService bucoSheetService;

    @Autowired
    private BucoSheetQueryService bucoSheetQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBucoSheetMockMvc;

    private BucoSheet bucoSheet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BucoSheetResource bucoSheetResource = new BucoSheetResource(bucoSheetService, bucoSheetQueryService);
        this.restBucoSheetMockMvc = MockMvcBuilders.standaloneSetup(bucoSheetResource)
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
    public static BucoSheet createEntity(EntityManager em) {
        BucoSheet bucoSheet = new BucoSheet()
            .sheetName(DEFAULT_SHEET_NAME)
            .sheetType(DEFAULT_SHEET_TYPE);
        return bucoSheet;
    }

    @Before
    public void initTest() {
        bucoSheet = createEntity(em);
    }

    @Test
    @Transactional
    public void createBucoSheet() throws Exception {
        int databaseSizeBeforeCreate = bucoSheetRepository.findAll().size();

        // Create the BucoSheet
        restBucoSheetMockMvc.perform(post("/api/buco-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bucoSheet)))
            .andExpect(status().isCreated());

        // Validate the BucoSheet in the database
        List<BucoSheet> bucoSheetList = bucoSheetRepository.findAll();
        assertThat(bucoSheetList).hasSize(databaseSizeBeforeCreate + 1);
        BucoSheet testBucoSheet = bucoSheetList.get(bucoSheetList.size() - 1);
        assertThat(testBucoSheet.getSheetName()).isEqualTo(DEFAULT_SHEET_NAME);
        assertThat(testBucoSheet.getSheetType()).isEqualTo(DEFAULT_SHEET_TYPE);
    }

    @Test
    @Transactional
    public void createBucoSheetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bucoSheetRepository.findAll().size();

        // Create the BucoSheet with an existing ID
        bucoSheet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBucoSheetMockMvc.perform(post("/api/buco-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bucoSheet)))
            .andExpect(status().isBadRequest());

        // Validate the BucoSheet in the database
        List<BucoSheet> bucoSheetList = bucoSheetRepository.findAll();
        assertThat(bucoSheetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBucoSheets() throws Exception {
        // Initialize the database
        bucoSheetRepository.saveAndFlush(bucoSheet);

        // Get all the bucoSheetList
        restBucoSheetMockMvc.perform(get("/api/buco-sheets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bucoSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].sheetName").value(hasItem(DEFAULT_SHEET_NAME.toString())))
            .andExpect(jsonPath("$.[*].sheetType").value(hasItem(DEFAULT_SHEET_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getBucoSheet() throws Exception {
        // Initialize the database
        bucoSheetRepository.saveAndFlush(bucoSheet);

        // Get the bucoSheet
        restBucoSheetMockMvc.perform(get("/api/buco-sheets/{id}", bucoSheet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bucoSheet.getId().intValue()))
            .andExpect(jsonPath("$.sheetName").value(DEFAULT_SHEET_NAME.toString()))
            .andExpect(jsonPath("$.sheetType").value(DEFAULT_SHEET_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllBucoSheetsBySheetNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bucoSheetRepository.saveAndFlush(bucoSheet);

        // Get all the bucoSheetList where sheetName equals to DEFAULT_SHEET_NAME
        defaultBucoSheetShouldBeFound("sheetName.equals=" + DEFAULT_SHEET_NAME);

        // Get all the bucoSheetList where sheetName equals to UPDATED_SHEET_NAME
        defaultBucoSheetShouldNotBeFound("sheetName.equals=" + UPDATED_SHEET_NAME);
    }

    @Test
    @Transactional
    public void getAllBucoSheetsBySheetNameIsInShouldWork() throws Exception {
        // Initialize the database
        bucoSheetRepository.saveAndFlush(bucoSheet);

        // Get all the bucoSheetList where sheetName in DEFAULT_SHEET_NAME or UPDATED_SHEET_NAME
        defaultBucoSheetShouldBeFound("sheetName.in=" + DEFAULT_SHEET_NAME + "," + UPDATED_SHEET_NAME);

        // Get all the bucoSheetList where sheetName equals to UPDATED_SHEET_NAME
        defaultBucoSheetShouldNotBeFound("sheetName.in=" + UPDATED_SHEET_NAME);
    }

    @Test
    @Transactional
    public void getAllBucoSheetsBySheetNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bucoSheetRepository.saveAndFlush(bucoSheet);

        // Get all the bucoSheetList where sheetName is not null
        defaultBucoSheetShouldBeFound("sheetName.specified=true");

        // Get all the bucoSheetList where sheetName is null
        defaultBucoSheetShouldNotBeFound("sheetName.specified=false");
    }

    @Test
    @Transactional
    public void getAllBucoSheetsBySheetTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bucoSheetRepository.saveAndFlush(bucoSheet);

        // Get all the bucoSheetList where sheetType equals to DEFAULT_SHEET_TYPE
        defaultBucoSheetShouldBeFound("sheetType.equals=" + DEFAULT_SHEET_TYPE);

        // Get all the bucoSheetList where sheetType equals to UPDATED_SHEET_TYPE
        defaultBucoSheetShouldNotBeFound("sheetType.equals=" + UPDATED_SHEET_TYPE);
    }

    @Test
    @Transactional
    public void getAllBucoSheetsBySheetTypeIsInShouldWork() throws Exception {
        // Initialize the database
        bucoSheetRepository.saveAndFlush(bucoSheet);

        // Get all the bucoSheetList where sheetType in DEFAULT_SHEET_TYPE or UPDATED_SHEET_TYPE
        defaultBucoSheetShouldBeFound("sheetType.in=" + DEFAULT_SHEET_TYPE + "," + UPDATED_SHEET_TYPE);

        // Get all the bucoSheetList where sheetType equals to UPDATED_SHEET_TYPE
        defaultBucoSheetShouldNotBeFound("sheetType.in=" + UPDATED_SHEET_TYPE);
    }

    @Test
    @Transactional
    public void getAllBucoSheetsBySheetTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bucoSheetRepository.saveAndFlush(bucoSheet);

        // Get all the bucoSheetList where sheetType is not null
        defaultBucoSheetShouldBeFound("sheetType.specified=true");

        // Get all the bucoSheetList where sheetType is null
        defaultBucoSheetShouldNotBeFound("sheetType.specified=false");
    }

    @Test
    @Transactional
    public void getAllBucoSheetsByBucoVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        BucoVersion bucoVersion = BucoVersionResourceIntTest.createEntity(em);
        em.persist(bucoVersion);
        em.flush();
        bucoSheet.setBucoVersion(bucoVersion);
        bucoSheetRepository.saveAndFlush(bucoSheet);
        Long bucoVersionId = bucoVersion.getId();

        // Get all the bucoSheetList where bucoVersion equals to bucoVersionId
        defaultBucoSheetShouldBeFound("bucoVersionId.equals=" + bucoVersionId);

        // Get all the bucoSheetList where bucoVersion equals to bucoVersionId + 1
        defaultBucoSheetShouldNotBeFound("bucoVersionId.equals=" + (bucoVersionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBucoSheetShouldBeFound(String filter) throws Exception {
        restBucoSheetMockMvc.perform(get("/api/buco-sheets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bucoSheet.getId().intValue())))
            .andExpect(jsonPath("$.[*].sheetName").value(hasItem(DEFAULT_SHEET_NAME.toString())))
            .andExpect(jsonPath("$.[*].sheetType").value(hasItem(DEFAULT_SHEET_TYPE.toString())));

        // Check, that the count call also returns 1
        restBucoSheetMockMvc.perform(get("/api/buco-sheets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBucoSheetShouldNotBeFound(String filter) throws Exception {
        restBucoSheetMockMvc.perform(get("/api/buco-sheets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBucoSheetMockMvc.perform(get("/api/buco-sheets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBucoSheet() throws Exception {
        // Get the bucoSheet
        restBucoSheetMockMvc.perform(get("/api/buco-sheets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBucoSheet() throws Exception {
        // Initialize the database
        bucoSheetService.save(bucoSheet);

        int databaseSizeBeforeUpdate = bucoSheetRepository.findAll().size();

        // Update the bucoSheet
        BucoSheet updatedBucoSheet = bucoSheetRepository.findById(bucoSheet.getId()).get();
        // Disconnect from session so that the updates on updatedBucoSheet are not directly saved in db
        em.detach(updatedBucoSheet);
        updatedBucoSheet
            .sheetName(UPDATED_SHEET_NAME)
            .sheetType(UPDATED_SHEET_TYPE);

        restBucoSheetMockMvc.perform(put("/api/buco-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBucoSheet)))
            .andExpect(status().isOk());

        // Validate the BucoSheet in the database
        List<BucoSheet> bucoSheetList = bucoSheetRepository.findAll();
        assertThat(bucoSheetList).hasSize(databaseSizeBeforeUpdate);
        BucoSheet testBucoSheet = bucoSheetList.get(bucoSheetList.size() - 1);
        assertThat(testBucoSheet.getSheetName()).isEqualTo(UPDATED_SHEET_NAME);
        assertThat(testBucoSheet.getSheetType()).isEqualTo(UPDATED_SHEET_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBucoSheet() throws Exception {
        int databaseSizeBeforeUpdate = bucoSheetRepository.findAll().size();

        // Create the BucoSheet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBucoSheetMockMvc.perform(put("/api/buco-sheets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bucoSheet)))
            .andExpect(status().isBadRequest());

        // Validate the BucoSheet in the database
        List<BucoSheet> bucoSheetList = bucoSheetRepository.findAll();
        assertThat(bucoSheetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBucoSheet() throws Exception {
        // Initialize the database
        bucoSheetService.save(bucoSheet);

        int databaseSizeBeforeDelete = bucoSheetRepository.findAll().size();

        // Get the bucoSheet
        restBucoSheetMockMvc.perform(delete("/api/buco-sheets/{id}", bucoSheet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BucoSheet> bucoSheetList = bucoSheetRepository.findAll();
        assertThat(bucoSheetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BucoSheet.class);
        BucoSheet bucoSheet1 = new BucoSheet();
        bucoSheet1.setId(1L);
        BucoSheet bucoSheet2 = new BucoSheet();
        bucoSheet2.setId(bucoSheet1.getId());
        assertThat(bucoSheet1).isEqualTo(bucoSheet2);
        bucoSheet2.setId(2L);
        assertThat(bucoSheet1).isNotEqualTo(bucoSheet2);
        bucoSheet1.setId(null);
        assertThat(bucoSheet1).isNotEqualTo(bucoSheet2);
    }
}

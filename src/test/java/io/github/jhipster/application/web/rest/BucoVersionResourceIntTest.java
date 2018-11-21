package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.BucoVersion;
import io.github.jhipster.application.repository.BucoVersionRepository;
import io.github.jhipster.application.service.BucoVersionService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.BucoVersionCriteria;
import io.github.jhipster.application.service.BucoVersionQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BucoVersionResource REST controller.
 *
 * @see BucoVersionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class BucoVersionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BucoVersionRepository bucoVersionRepository;

    @Autowired
    private BucoVersionService bucoVersionService;

    @Autowired
    private BucoVersionQueryService bucoVersionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBucoVersionMockMvc;

    private BucoVersion bucoVersion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BucoVersionResource bucoVersionResource = new BucoVersionResource(bucoVersionService, bucoVersionQueryService);
        this.restBucoVersionMockMvc = MockMvcBuilders.standaloneSetup(bucoVersionResource)
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
    public static BucoVersion createEntity(EntityManager em) {
        BucoVersion bucoVersion = new BucoVersion()
            .name(DEFAULT_NAME)
            .fileName(DEFAULT_FILE_NAME)
            .createdDate(DEFAULT_CREATED_DATE);
        return bucoVersion;
    }

    @Before
    public void initTest() {
        bucoVersion = createEntity(em);
    }

    @Test
    @Transactional
    public void createBucoVersion() throws Exception {
        int databaseSizeBeforeCreate = bucoVersionRepository.findAll().size();

        // Create the BucoVersion
        restBucoVersionMockMvc.perform(post("/api/buco-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bucoVersion)))
            .andExpect(status().isCreated());

        // Validate the BucoVersion in the database
        List<BucoVersion> bucoVersionList = bucoVersionRepository.findAll();
        assertThat(bucoVersionList).hasSize(databaseSizeBeforeCreate + 1);
        BucoVersion testBucoVersion = bucoVersionList.get(bucoVersionList.size() - 1);
        assertThat(testBucoVersion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBucoVersion.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testBucoVersion.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createBucoVersionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bucoVersionRepository.findAll().size();

        // Create the BucoVersion with an existing ID
        bucoVersion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBucoVersionMockMvc.perform(post("/api/buco-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bucoVersion)))
            .andExpect(status().isBadRequest());

        // Validate the BucoVersion in the database
        List<BucoVersion> bucoVersionList = bucoVersionRepository.findAll();
        assertThat(bucoVersionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBucoVersions() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList
        restBucoVersionMockMvc.perform(get("/api/buco-versions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bucoVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getBucoVersion() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get the bucoVersion
        restBucoVersionMockMvc.perform(get("/api/buco-versions/{id}", bucoVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bucoVersion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllBucoVersionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList where name equals to DEFAULT_NAME
        defaultBucoVersionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bucoVersionList where name equals to UPDATED_NAME
        defaultBucoVersionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBucoVersionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBucoVersionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bucoVersionList where name equals to UPDATED_NAME
        defaultBucoVersionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBucoVersionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList where name is not null
        defaultBucoVersionShouldBeFound("name.specified=true");

        // Get all the bucoVersionList where name is null
        defaultBucoVersionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllBucoVersionsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList where fileName equals to DEFAULT_FILE_NAME
        defaultBucoVersionShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the bucoVersionList where fileName equals to UPDATED_FILE_NAME
        defaultBucoVersionShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllBucoVersionsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultBucoVersionShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the bucoVersionList where fileName equals to UPDATED_FILE_NAME
        defaultBucoVersionShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllBucoVersionsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList where fileName is not null
        defaultBucoVersionShouldBeFound("fileName.specified=true");

        // Get all the bucoVersionList where fileName is null
        defaultBucoVersionShouldNotBeFound("fileName.specified=false");
    }

    @Test
    @Transactional
    public void getAllBucoVersionsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList where createdDate equals to DEFAULT_CREATED_DATE
        defaultBucoVersionShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the bucoVersionList where createdDate equals to UPDATED_CREATED_DATE
        defaultBucoVersionShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllBucoVersionsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultBucoVersionShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the bucoVersionList where createdDate equals to UPDATED_CREATED_DATE
        defaultBucoVersionShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void getAllBucoVersionsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bucoVersionRepository.saveAndFlush(bucoVersion);

        // Get all the bucoVersionList where createdDate is not null
        defaultBucoVersionShouldBeFound("createdDate.specified=true");

        // Get all the bucoVersionList where createdDate is null
        defaultBucoVersionShouldNotBeFound("createdDate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBucoVersionShouldBeFound(String filter) throws Exception {
        restBucoVersionMockMvc.perform(get("/api/buco-versions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bucoVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restBucoVersionMockMvc.perform(get("/api/buco-versions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBucoVersionShouldNotBeFound(String filter) throws Exception {
        restBucoVersionMockMvc.perform(get("/api/buco-versions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBucoVersionMockMvc.perform(get("/api/buco-versions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBucoVersion() throws Exception {
        // Get the bucoVersion
        restBucoVersionMockMvc.perform(get("/api/buco-versions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBucoVersion() throws Exception {
        // Initialize the database
        bucoVersionService.save(bucoVersion);

        int databaseSizeBeforeUpdate = bucoVersionRepository.findAll().size();

        // Update the bucoVersion
        BucoVersion updatedBucoVersion = bucoVersionRepository.findById(bucoVersion.getId()).get();
        // Disconnect from session so that the updates on updatedBucoVersion are not directly saved in db
        em.detach(updatedBucoVersion);
        updatedBucoVersion
            .name(UPDATED_NAME)
            .fileName(UPDATED_FILE_NAME)
            .createdDate(UPDATED_CREATED_DATE);

        restBucoVersionMockMvc.perform(put("/api/buco-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBucoVersion)))
            .andExpect(status().isOk());

        // Validate the BucoVersion in the database
        List<BucoVersion> bucoVersionList = bucoVersionRepository.findAll();
        assertThat(bucoVersionList).hasSize(databaseSizeBeforeUpdate);
        BucoVersion testBucoVersion = bucoVersionList.get(bucoVersionList.size() - 1);
        assertThat(testBucoVersion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBucoVersion.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testBucoVersion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBucoVersion() throws Exception {
        int databaseSizeBeforeUpdate = bucoVersionRepository.findAll().size();

        // Create the BucoVersion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBucoVersionMockMvc.perform(put("/api/buco-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bucoVersion)))
            .andExpect(status().isBadRequest());

        // Validate the BucoVersion in the database
        List<BucoVersion> bucoVersionList = bucoVersionRepository.findAll();
        assertThat(bucoVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBucoVersion() throws Exception {
        // Initialize the database
        bucoVersionService.save(bucoVersion);

        int databaseSizeBeforeDelete = bucoVersionRepository.findAll().size();

        // Get the bucoVersion
        restBucoVersionMockMvc.perform(delete("/api/buco-versions/{id}", bucoVersion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BucoVersion> bucoVersionList = bucoVersionRepository.findAll();
        assertThat(bucoVersionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BucoVersion.class);
        BucoVersion bucoVersion1 = new BucoVersion();
        bucoVersion1.setId(1L);
        BucoVersion bucoVersion2 = new BucoVersion();
        bucoVersion2.setId(bucoVersion1.getId());
        assertThat(bucoVersion1).isEqualTo(bucoVersion2);
        bucoVersion2.setId(2L);
        assertThat(bucoVersion1).isNotEqualTo(bucoVersion2);
        bucoVersion1.setId(null);
        assertThat(bucoVersion1).isNotEqualTo(bucoVersion2);
    }
}

package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.ApplicationVersionRelation;
import io.github.jhipster.application.repository.ApplicationVersionRelationRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

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
 * Test class for the ApplicationVersionRelationResource REST controller.
 *
 * @see ApplicationVersionRelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class ApplicationVersionRelationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    @Autowired
    private ApplicationVersionRelationRepository applicationVersionRelationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicationVersionRelationMockMvc;

    private ApplicationVersionRelation applicationVersionRelation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationVersionRelationResource applicationVersionRelationResource = new ApplicationVersionRelationResource(applicationVersionRelationRepository);
        this.restApplicationVersionRelationMockMvc = MockMvcBuilders.standaloneSetup(applicationVersionRelationResource)
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
    public static ApplicationVersionRelation createEntity(EntityManager em) {
        ApplicationVersionRelation applicationVersionRelation = new ApplicationVersionRelation()
            .name(DEFAULT_NAME)
            .version(DEFAULT_VERSION);
        return applicationVersionRelation;
    }

    @Before
    public void initTest() {
        applicationVersionRelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationVersionRelation() throws Exception {
        int databaseSizeBeforeCreate = applicationVersionRelationRepository.findAll().size();

        // Create the ApplicationVersionRelation
        restApplicationVersionRelationMockMvc.perform(post("/api/application-version-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationVersionRelation)))
            .andExpect(status().isCreated());

        // Validate the ApplicationVersionRelation in the database
        List<ApplicationVersionRelation> applicationVersionRelationList = applicationVersionRelationRepository.findAll();
        assertThat(applicationVersionRelationList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationVersionRelation testApplicationVersionRelation = applicationVersionRelationList.get(applicationVersionRelationList.size() - 1);
        assertThat(testApplicationVersionRelation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApplicationVersionRelation.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createApplicationVersionRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationVersionRelationRepository.findAll().size();

        // Create the ApplicationVersionRelation with an existing ID
        applicationVersionRelation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationVersionRelationMockMvc.perform(post("/api/application-version-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationVersionRelation)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationVersionRelation in the database
        List<ApplicationVersionRelation> applicationVersionRelationList = applicationVersionRelationRepository.findAll();
        assertThat(applicationVersionRelationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApplicationVersionRelations() throws Exception {
        // Initialize the database
        applicationVersionRelationRepository.saveAndFlush(applicationVersionRelation);

        // Get all the applicationVersionRelationList
        restApplicationVersionRelationMockMvc.perform(get("/api/application-version-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationVersionRelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationVersionRelation() throws Exception {
        // Initialize the database
        applicationVersionRelationRepository.saveAndFlush(applicationVersionRelation);

        // Get the applicationVersionRelation
        restApplicationVersionRelationMockMvc.perform(get("/api/application-version-relations/{id}", applicationVersionRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationVersionRelation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationVersionRelation() throws Exception {
        // Get the applicationVersionRelation
        restApplicationVersionRelationMockMvc.perform(get("/api/application-version-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationVersionRelation() throws Exception {
        // Initialize the database
        applicationVersionRelationRepository.saveAndFlush(applicationVersionRelation);

        int databaseSizeBeforeUpdate = applicationVersionRelationRepository.findAll().size();

        // Update the applicationVersionRelation
        ApplicationVersionRelation updatedApplicationVersionRelation = applicationVersionRelationRepository.findById(applicationVersionRelation.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationVersionRelation are not directly saved in db
        em.detach(updatedApplicationVersionRelation);
        updatedApplicationVersionRelation
            .name(UPDATED_NAME)
            .version(UPDATED_VERSION);

        restApplicationVersionRelationMockMvc.perform(put("/api/application-version-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicationVersionRelation)))
            .andExpect(status().isOk());

        // Validate the ApplicationVersionRelation in the database
        List<ApplicationVersionRelation> applicationVersionRelationList = applicationVersionRelationRepository.findAll();
        assertThat(applicationVersionRelationList).hasSize(databaseSizeBeforeUpdate);
        ApplicationVersionRelation testApplicationVersionRelation = applicationVersionRelationList.get(applicationVersionRelationList.size() - 1);
        assertThat(testApplicationVersionRelation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApplicationVersionRelation.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationVersionRelation() throws Exception {
        int databaseSizeBeforeUpdate = applicationVersionRelationRepository.findAll().size();

        // Create the ApplicationVersionRelation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationVersionRelationMockMvc.perform(put("/api/application-version-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationVersionRelation)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationVersionRelation in the database
        List<ApplicationVersionRelation> applicationVersionRelationList = applicationVersionRelationRepository.findAll();
        assertThat(applicationVersionRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationVersionRelation() throws Exception {
        // Initialize the database
        applicationVersionRelationRepository.saveAndFlush(applicationVersionRelation);

        int databaseSizeBeforeDelete = applicationVersionRelationRepository.findAll().size();

        // Get the applicationVersionRelation
        restApplicationVersionRelationMockMvc.perform(delete("/api/application-version-relations/{id}", applicationVersionRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationVersionRelation> applicationVersionRelationList = applicationVersionRelationRepository.findAll();
        assertThat(applicationVersionRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationVersionRelation.class);
        ApplicationVersionRelation applicationVersionRelation1 = new ApplicationVersionRelation();
        applicationVersionRelation1.setId(1L);
        ApplicationVersionRelation applicationVersionRelation2 = new ApplicationVersionRelation();
        applicationVersionRelation2.setId(applicationVersionRelation1.getId());
        assertThat(applicationVersionRelation1).isEqualTo(applicationVersionRelation2);
        applicationVersionRelation2.setId(2L);
        assertThat(applicationVersionRelation1).isNotEqualTo(applicationVersionRelation2);
        applicationVersionRelation1.setId(null);
        assertThat(applicationVersionRelation1).isNotEqualTo(applicationVersionRelation2);
    }
}

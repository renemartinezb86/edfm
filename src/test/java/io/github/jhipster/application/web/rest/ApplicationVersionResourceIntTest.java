package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.ApplicationVersion;
import io.github.jhipster.application.repository.ApplicationVersionRepository;
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
 * Test class for the ApplicationVersionResource REST controller.
 *
 * @see ApplicationVersionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class ApplicationVersionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ApplicationVersionRepository applicationVersionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplicationVersionMockMvc;

    private ApplicationVersion applicationVersion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplicationVersionResource applicationVersionResource = new ApplicationVersionResource(applicationVersionRepository);
        this.restApplicationVersionMockMvc = MockMvcBuilders.standaloneSetup(applicationVersionResource)
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
    public static ApplicationVersion createEntity(EntityManager em) {
        ApplicationVersion applicationVersion = new ApplicationVersion()
            .name(DEFAULT_NAME);
        return applicationVersion;
    }

    @Before
    public void initTest() {
        applicationVersion = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplicationVersion() throws Exception {
        int databaseSizeBeforeCreate = applicationVersionRepository.findAll().size();

        // Create the ApplicationVersion
        restApplicationVersionMockMvc.perform(post("/api/application-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationVersion)))
            .andExpect(status().isCreated());

        // Validate the ApplicationVersion in the database
        List<ApplicationVersion> applicationVersionList = applicationVersionRepository.findAll();
        assertThat(applicationVersionList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationVersion testApplicationVersion = applicationVersionList.get(applicationVersionList.size() - 1);
        assertThat(testApplicationVersion.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createApplicationVersionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applicationVersionRepository.findAll().size();

        // Create the ApplicationVersion with an existing ID
        applicationVersion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationVersionMockMvc.perform(post("/api/application-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationVersion)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationVersion in the database
        List<ApplicationVersion> applicationVersionList = applicationVersionRepository.findAll();
        assertThat(applicationVersionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApplicationVersions() throws Exception {
        // Initialize the database
        applicationVersionRepository.saveAndFlush(applicationVersion);

        // Get all the applicationVersionList
        restApplicationVersionMockMvc.perform(get("/api/application-versions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationVersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getApplicationVersion() throws Exception {
        // Initialize the database
        applicationVersionRepository.saveAndFlush(applicationVersion);

        // Get the applicationVersion
        restApplicationVersionMockMvc.perform(get("/api/application-versions/{id}", applicationVersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applicationVersion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplicationVersion() throws Exception {
        // Get the applicationVersion
        restApplicationVersionMockMvc.perform(get("/api/application-versions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplicationVersion() throws Exception {
        // Initialize the database
        applicationVersionRepository.saveAndFlush(applicationVersion);

        int databaseSizeBeforeUpdate = applicationVersionRepository.findAll().size();

        // Update the applicationVersion
        ApplicationVersion updatedApplicationVersion = applicationVersionRepository.findById(applicationVersion.getId()).get();
        // Disconnect from session so that the updates on updatedApplicationVersion are not directly saved in db
        em.detach(updatedApplicationVersion);
        updatedApplicationVersion
            .name(UPDATED_NAME);

        restApplicationVersionMockMvc.perform(put("/api/application-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApplicationVersion)))
            .andExpect(status().isOk());

        // Validate the ApplicationVersion in the database
        List<ApplicationVersion> applicationVersionList = applicationVersionRepository.findAll();
        assertThat(applicationVersionList).hasSize(databaseSizeBeforeUpdate);
        ApplicationVersion testApplicationVersion = applicationVersionList.get(applicationVersionList.size() - 1);
        assertThat(testApplicationVersion.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingApplicationVersion() throws Exception {
        int databaseSizeBeforeUpdate = applicationVersionRepository.findAll().size();

        // Create the ApplicationVersion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationVersionMockMvc.perform(put("/api/application-versions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applicationVersion)))
            .andExpect(status().isBadRequest());

        // Validate the ApplicationVersion in the database
        List<ApplicationVersion> applicationVersionList = applicationVersionRepository.findAll();
        assertThat(applicationVersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApplicationVersion() throws Exception {
        // Initialize the database
        applicationVersionRepository.saveAndFlush(applicationVersion);

        int databaseSizeBeforeDelete = applicationVersionRepository.findAll().size();

        // Get the applicationVersion
        restApplicationVersionMockMvc.perform(delete("/api/application-versions/{id}", applicationVersion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ApplicationVersion> applicationVersionList = applicationVersionRepository.findAll();
        assertThat(applicationVersionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationVersion.class);
        ApplicationVersion applicationVersion1 = new ApplicationVersion();
        applicationVersion1.setId(1L);
        ApplicationVersion applicationVersion2 = new ApplicationVersion();
        applicationVersion2.setId(applicationVersion1.getId());
        assertThat(applicationVersion1).isEqualTo(applicationVersion2);
        applicationVersion2.setId(2L);
        assertThat(applicationVersion1).isNotEqualTo(applicationVersion2);
        applicationVersion1.setId(null);
        assertThat(applicationVersion1).isNotEqualTo(applicationVersion2);
    }
}

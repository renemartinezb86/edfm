package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.DeployPipeline;
import io.github.jhipster.application.repository.DeployPipelineRepository;
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
 * Test class for the DeployPipelineResource REST controller.
 *
 * @see DeployPipelineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class DeployPipelineResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOB_NAME = "BBBBBBBBBB";

    @Autowired
    private DeployPipelineRepository deployPipelineRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeployPipelineMockMvc;

    private DeployPipeline deployPipeline;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeployPipelineResource deployPipelineResource = new DeployPipelineResource(deployPipelineRepository);
        this.restDeployPipelineMockMvc = MockMvcBuilders.standaloneSetup(deployPipelineResource)
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
    public static DeployPipeline createEntity(EntityManager em) {
        DeployPipeline deployPipeline = new DeployPipeline()
            .name(DEFAULT_NAME)
            .url(DEFAULT_URL)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .jobName(DEFAULT_JOB_NAME);
        return deployPipeline;
    }

    @Before
    public void initTest() {
        deployPipeline = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeployPipeline() throws Exception {
        int databaseSizeBeforeCreate = deployPipelineRepository.findAll().size();

        // Create the DeployPipeline
        restDeployPipelineMockMvc.perform(post("/api/deploy-pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deployPipeline)))
            .andExpect(status().isCreated());

        // Validate the DeployPipeline in the database
        List<DeployPipeline> deployPipelineList = deployPipelineRepository.findAll();
        assertThat(deployPipelineList).hasSize(databaseSizeBeforeCreate + 1);
        DeployPipeline testDeployPipeline = deployPipelineList.get(deployPipelineList.size() - 1);
        assertThat(testDeployPipeline.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeployPipeline.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testDeployPipeline.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testDeployPipeline.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDeployPipeline.getJobName()).isEqualTo(DEFAULT_JOB_NAME);
    }

    @Test
    @Transactional
    public void createDeployPipelineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deployPipelineRepository.findAll().size();

        // Create the DeployPipeline with an existing ID
        deployPipeline.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeployPipelineMockMvc.perform(post("/api/deploy-pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deployPipeline)))
            .andExpect(status().isBadRequest());

        // Validate the DeployPipeline in the database
        List<DeployPipeline> deployPipelineList = deployPipelineRepository.findAll();
        assertThat(deployPipelineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeployPipelines() throws Exception {
        // Initialize the database
        deployPipelineRepository.saveAndFlush(deployPipeline);

        // Get all the deployPipelineList
        restDeployPipelineMockMvc.perform(get("/api/deploy-pipelines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deployPipeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].jobName").value(hasItem(DEFAULT_JOB_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDeployPipeline() throws Exception {
        // Initialize the database
        deployPipelineRepository.saveAndFlush(deployPipeline);

        // Get the deployPipeline
        restDeployPipelineMockMvc.perform(get("/api/deploy-pipelines/{id}", deployPipeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deployPipeline.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.jobName").value(DEFAULT_JOB_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeployPipeline() throws Exception {
        // Get the deployPipeline
        restDeployPipelineMockMvc.perform(get("/api/deploy-pipelines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeployPipeline() throws Exception {
        // Initialize the database
        deployPipelineRepository.saveAndFlush(deployPipeline);

        int databaseSizeBeforeUpdate = deployPipelineRepository.findAll().size();

        // Update the deployPipeline
        DeployPipeline updatedDeployPipeline = deployPipelineRepository.findById(deployPipeline.getId()).get();
        // Disconnect from session so that the updates on updatedDeployPipeline are not directly saved in db
        em.detach(updatedDeployPipeline);
        updatedDeployPipeline
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .jobName(UPDATED_JOB_NAME);

        restDeployPipelineMockMvc.perform(put("/api/deploy-pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeployPipeline)))
            .andExpect(status().isOk());

        // Validate the DeployPipeline in the database
        List<DeployPipeline> deployPipelineList = deployPipelineRepository.findAll();
        assertThat(deployPipelineList).hasSize(databaseSizeBeforeUpdate);
        DeployPipeline testDeployPipeline = deployPipelineList.get(deployPipelineList.size() - 1);
        assertThat(testDeployPipeline.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeployPipeline.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testDeployPipeline.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDeployPipeline.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDeployPipeline.getJobName()).isEqualTo(UPDATED_JOB_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDeployPipeline() throws Exception {
        int databaseSizeBeforeUpdate = deployPipelineRepository.findAll().size();

        // Create the DeployPipeline

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeployPipelineMockMvc.perform(put("/api/deploy-pipelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deployPipeline)))
            .andExpect(status().isBadRequest());

        // Validate the DeployPipeline in the database
        List<DeployPipeline> deployPipelineList = deployPipelineRepository.findAll();
        assertThat(deployPipelineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeployPipeline() throws Exception {
        // Initialize the database
        deployPipelineRepository.saveAndFlush(deployPipeline);

        int databaseSizeBeforeDelete = deployPipelineRepository.findAll().size();

        // Get the deployPipeline
        restDeployPipelineMockMvc.perform(delete("/api/deploy-pipelines/{id}", deployPipeline.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeployPipeline> deployPipelineList = deployPipelineRepository.findAll();
        assertThat(deployPipelineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeployPipeline.class);
        DeployPipeline deployPipeline1 = new DeployPipeline();
        deployPipeline1.setId(1L);
        DeployPipeline deployPipeline2 = new DeployPipeline();
        deployPipeline2.setId(deployPipeline1.getId());
        assertThat(deployPipeline1).isEqualTo(deployPipeline2);
        deployPipeline2.setId(2L);
        assertThat(deployPipeline1).isNotEqualTo(deployPipeline2);
        deployPipeline1.setId(null);
        assertThat(deployPipeline1).isNotEqualTo(deployPipeline2);
    }
}

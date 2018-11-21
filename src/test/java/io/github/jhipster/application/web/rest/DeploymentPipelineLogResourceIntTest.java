package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.DeploymentPipelineLog;
import io.github.jhipster.application.repository.DeploymentPipelineLogRepository;
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
 * Test class for the DeploymentPipelineLogResource REST controller.
 *
 * @see DeploymentPipelineLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class DeploymentPipelineLogResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private DeploymentPipelineLogRepository deploymentPipelineLogRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeploymentPipelineLogMockMvc;

    private DeploymentPipelineLog deploymentPipelineLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeploymentPipelineLogResource deploymentPipelineLogResource = new DeploymentPipelineLogResource(deploymentPipelineLogRepository);
        this.restDeploymentPipelineLogMockMvc = MockMvcBuilders.standaloneSetup(deploymentPipelineLogResource)
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
    public static DeploymentPipelineLog createEntity(EntityManager em) {
        DeploymentPipelineLog deploymentPipelineLog = new DeploymentPipelineLog()
            .name(DEFAULT_NAME)
            .url(DEFAULT_URL)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return deploymentPipelineLog;
    }

    @Before
    public void initTest() {
        deploymentPipelineLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeploymentPipelineLog() throws Exception {
        int databaseSizeBeforeCreate = deploymentPipelineLogRepository.findAll().size();

        // Create the DeploymentPipelineLog
        restDeploymentPipelineLogMockMvc.perform(post("/api/deployment-pipeline-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deploymentPipelineLog)))
            .andExpect(status().isCreated());

        // Validate the DeploymentPipelineLog in the database
        List<DeploymentPipelineLog> deploymentPipelineLogList = deploymentPipelineLogRepository.findAll();
        assertThat(deploymentPipelineLogList).hasSize(databaseSizeBeforeCreate + 1);
        DeploymentPipelineLog testDeploymentPipelineLog = deploymentPipelineLogList.get(deploymentPipelineLogList.size() - 1);
        assertThat(testDeploymentPipelineLog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeploymentPipelineLog.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testDeploymentPipelineLog.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeploymentPipelineLog.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createDeploymentPipelineLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deploymentPipelineLogRepository.findAll().size();

        // Create the DeploymentPipelineLog with an existing ID
        deploymentPipelineLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeploymentPipelineLogMockMvc.perform(post("/api/deployment-pipeline-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deploymentPipelineLog)))
            .andExpect(status().isBadRequest());

        // Validate the DeploymentPipelineLog in the database
        List<DeploymentPipelineLog> deploymentPipelineLogList = deploymentPipelineLogRepository.findAll();
        assertThat(deploymentPipelineLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeploymentPipelineLogs() throws Exception {
        // Initialize the database
        deploymentPipelineLogRepository.saveAndFlush(deploymentPipelineLog);

        // Get all the deploymentPipelineLogList
        restDeploymentPipelineLogMockMvc.perform(get("/api/deployment-pipeline-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deploymentPipelineLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getDeploymentPipelineLog() throws Exception {
        // Initialize the database
        deploymentPipelineLogRepository.saveAndFlush(deploymentPipelineLog);

        // Get the deploymentPipelineLog
        restDeploymentPipelineLogMockMvc.perform(get("/api/deployment-pipeline-logs/{id}", deploymentPipelineLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deploymentPipelineLog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDeploymentPipelineLog() throws Exception {
        // Get the deploymentPipelineLog
        restDeploymentPipelineLogMockMvc.perform(get("/api/deployment-pipeline-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeploymentPipelineLog() throws Exception {
        // Initialize the database
        deploymentPipelineLogRepository.saveAndFlush(deploymentPipelineLog);

        int databaseSizeBeforeUpdate = deploymentPipelineLogRepository.findAll().size();

        // Update the deploymentPipelineLog
        DeploymentPipelineLog updatedDeploymentPipelineLog = deploymentPipelineLogRepository.findById(deploymentPipelineLog.getId()).get();
        // Disconnect from session so that the updates on updatedDeploymentPipelineLog are not directly saved in db
        em.detach(updatedDeploymentPipelineLog);
        updatedDeploymentPipelineLog
            .name(UPDATED_NAME)
            .url(UPDATED_URL)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restDeploymentPipelineLogMockMvc.perform(put("/api/deployment-pipeline-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeploymentPipelineLog)))
            .andExpect(status().isOk());

        // Validate the DeploymentPipelineLog in the database
        List<DeploymentPipelineLog> deploymentPipelineLogList = deploymentPipelineLogRepository.findAll();
        assertThat(deploymentPipelineLogList).hasSize(databaseSizeBeforeUpdate);
        DeploymentPipelineLog testDeploymentPipelineLog = deploymentPipelineLogList.get(deploymentPipelineLogList.size() - 1);
        assertThat(testDeploymentPipelineLog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeploymentPipelineLog.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testDeploymentPipelineLog.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeploymentPipelineLog.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingDeploymentPipelineLog() throws Exception {
        int databaseSizeBeforeUpdate = deploymentPipelineLogRepository.findAll().size();

        // Create the DeploymentPipelineLog

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeploymentPipelineLogMockMvc.perform(put("/api/deployment-pipeline-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deploymentPipelineLog)))
            .andExpect(status().isBadRequest());

        // Validate the DeploymentPipelineLog in the database
        List<DeploymentPipelineLog> deploymentPipelineLogList = deploymentPipelineLogRepository.findAll();
        assertThat(deploymentPipelineLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeploymentPipelineLog() throws Exception {
        // Initialize the database
        deploymentPipelineLogRepository.saveAndFlush(deploymentPipelineLog);

        int databaseSizeBeforeDelete = deploymentPipelineLogRepository.findAll().size();

        // Get the deploymentPipelineLog
        restDeploymentPipelineLogMockMvc.perform(delete("/api/deployment-pipeline-logs/{id}", deploymentPipelineLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DeploymentPipelineLog> deploymentPipelineLogList = deploymentPipelineLogRepository.findAll();
        assertThat(deploymentPipelineLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeploymentPipelineLog.class);
        DeploymentPipelineLog deploymentPipelineLog1 = new DeploymentPipelineLog();
        deploymentPipelineLog1.setId(1L);
        DeploymentPipelineLog deploymentPipelineLog2 = new DeploymentPipelineLog();
        deploymentPipelineLog2.setId(deploymentPipelineLog1.getId());
        assertThat(deploymentPipelineLog1).isEqualTo(deploymentPipelineLog2);
        deploymentPipelineLog2.setId(2L);
        assertThat(deploymentPipelineLog1).isNotEqualTo(deploymentPipelineLog2);
        deploymentPipelineLog1.setId(null);
        assertThat(deploymentPipelineLog1).isNotEqualTo(deploymentPipelineLog2);
    }
}

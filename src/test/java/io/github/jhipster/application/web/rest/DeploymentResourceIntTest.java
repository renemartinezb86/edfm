package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.Deployment;
import io.github.jhipster.application.domain.ApplicationVersion;
import io.github.jhipster.application.domain.Environment;
import io.github.jhipster.application.domain.DeploymentPipelineLog;
import io.github.jhipster.application.repository.DeploymentRepository;
import io.github.jhipster.application.service.DeploymentService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.DeploymentCriteria;
import io.github.jhipster.application.service.DeploymentQueryService;

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
 * Test class for the DeploymentResource REST controller.
 *
 * @see DeploymentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class DeploymentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DeploymentRepository deploymentRepository;

    @Autowired
    private DeploymentService deploymentService;

    @Autowired
    private DeploymentQueryService deploymentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDeploymentMockMvc;

    private Deployment deployment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeploymentResource deploymentResource = new DeploymentResource(deploymentService, deploymentQueryService);
        this.restDeploymentMockMvc = MockMvcBuilders.standaloneSetup(deploymentResource)
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
    public static Deployment createEntity(EntityManager em) {
        Deployment deployment = new Deployment()
            .name(DEFAULT_NAME)
            .date(DEFAULT_DATE);
        return deployment;
    }

    @Before
    public void initTest() {
        deployment = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeployment() throws Exception {
        int databaseSizeBeforeCreate = deploymentRepository.findAll().size();

        // Create the Deployment
        restDeploymentMockMvc.perform(post("/api/deployments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deployment)))
            .andExpect(status().isCreated());

        // Validate the Deployment in the database
        List<Deployment> deploymentList = deploymentRepository.findAll();
        assertThat(deploymentList).hasSize(databaseSizeBeforeCreate + 1);
        Deployment testDeployment = deploymentList.get(deploymentList.size() - 1);
        assertThat(testDeployment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeployment.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDeploymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deploymentRepository.findAll().size();

        // Create the Deployment with an existing ID
        deployment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeploymentMockMvc.perform(post("/api/deployments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deployment)))
            .andExpect(status().isBadRequest());

        // Validate the Deployment in the database
        List<Deployment> deploymentList = deploymentRepository.findAll();
        assertThat(deploymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDeployments() throws Exception {
        // Initialize the database
        deploymentRepository.saveAndFlush(deployment);

        // Get all the deploymentList
        restDeploymentMockMvc.perform(get("/api/deployments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deployment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDeployment() throws Exception {
        // Initialize the database
        deploymentRepository.saveAndFlush(deployment);

        // Get the deployment
        restDeploymentMockMvc.perform(get("/api/deployments/{id}", deployment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deployment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllDeploymentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deploymentRepository.saveAndFlush(deployment);

        // Get all the deploymentList where name equals to DEFAULT_NAME
        defaultDeploymentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the deploymentList where name equals to UPDATED_NAME
        defaultDeploymentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeploymentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        deploymentRepository.saveAndFlush(deployment);

        // Get all the deploymentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDeploymentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the deploymentList where name equals to UPDATED_NAME
        defaultDeploymentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeploymentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deploymentRepository.saveAndFlush(deployment);

        // Get all the deploymentList where name is not null
        defaultDeploymentShouldBeFound("name.specified=true");

        // Get all the deploymentList where name is null
        defaultDeploymentShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeploymentsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        deploymentRepository.saveAndFlush(deployment);

        // Get all the deploymentList where date equals to DEFAULT_DATE
        defaultDeploymentShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the deploymentList where date equals to UPDATED_DATE
        defaultDeploymentShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDeploymentsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        deploymentRepository.saveAndFlush(deployment);

        // Get all the deploymentList where date in DEFAULT_DATE or UPDATED_DATE
        defaultDeploymentShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the deploymentList where date equals to UPDATED_DATE
        defaultDeploymentShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllDeploymentsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        deploymentRepository.saveAndFlush(deployment);

        // Get all the deploymentList where date is not null
        defaultDeploymentShouldBeFound("date.specified=true");

        // Get all the deploymentList where date is null
        defaultDeploymentShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeploymentsByApplicationVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        ApplicationVersion applicationVersion = ApplicationVersionResourceIntTest.createEntity(em);
        em.persist(applicationVersion);
        em.flush();
        deployment.setApplicationVersion(applicationVersion);
        deploymentRepository.saveAndFlush(deployment);
        Long applicationVersionId = applicationVersion.getId();

        // Get all the deploymentList where applicationVersion equals to applicationVersionId
        defaultDeploymentShouldBeFound("applicationVersionId.equals=" + applicationVersionId);

        // Get all the deploymentList where applicationVersion equals to applicationVersionId + 1
        defaultDeploymentShouldNotBeFound("applicationVersionId.equals=" + (applicationVersionId + 1));
    }


    @Test
    @Transactional
    public void getAllDeploymentsByEnvironmentIsEqualToSomething() throws Exception {
        // Initialize the database
        Environment environment = EnvironmentResourceIntTest.createEntity(em);
        em.persist(environment);
        em.flush();
        deployment.setEnvironment(environment);
        deploymentRepository.saveAndFlush(deployment);
        Long environmentId = environment.getId();

        // Get all the deploymentList where environment equals to environmentId
        defaultDeploymentShouldBeFound("environmentId.equals=" + environmentId);

        // Get all the deploymentList where environment equals to environmentId + 1
        defaultDeploymentShouldNotBeFound("environmentId.equals=" + (environmentId + 1));
    }


    @Test
    @Transactional
    public void getAllDeploymentsByDeploymentLogsIsEqualToSomething() throws Exception {
        // Initialize the database
        DeploymentPipelineLog deploymentLogs = DeploymentPipelineLogResourceIntTest.createEntity(em);
        em.persist(deploymentLogs);
        em.flush();
        deployment.setDeploymentLogs(deploymentLogs);
        deploymentRepository.saveAndFlush(deployment);
        Long deploymentLogsId = deploymentLogs.getId();

        // Get all the deploymentList where deploymentLogs equals to deploymentLogsId
        defaultDeploymentShouldBeFound("deploymentLogsId.equals=" + deploymentLogsId);

        // Get all the deploymentList where deploymentLogs equals to deploymentLogsId + 1
        defaultDeploymentShouldNotBeFound("deploymentLogsId.equals=" + (deploymentLogsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDeploymentShouldBeFound(String filter) throws Exception {
        restDeploymentMockMvc.perform(get("/api/deployments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deployment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restDeploymentMockMvc.perform(get("/api/deployments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDeploymentShouldNotBeFound(String filter) throws Exception {
        restDeploymentMockMvc.perform(get("/api/deployments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeploymentMockMvc.perform(get("/api/deployments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDeployment() throws Exception {
        // Get the deployment
        restDeploymentMockMvc.perform(get("/api/deployments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeployment() throws Exception {
        // Initialize the database
        deploymentService.save(deployment);

        int databaseSizeBeforeUpdate = deploymentRepository.findAll().size();

        // Update the deployment
        Deployment updatedDeployment = deploymentRepository.findById(deployment.getId()).get();
        // Disconnect from session so that the updates on updatedDeployment are not directly saved in db
        em.detach(updatedDeployment);
        updatedDeployment
            .name(UPDATED_NAME)
            .date(UPDATED_DATE);

        restDeploymentMockMvc.perform(put("/api/deployments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeployment)))
            .andExpect(status().isOk());

        // Validate the Deployment in the database
        List<Deployment> deploymentList = deploymentRepository.findAll();
        assertThat(deploymentList).hasSize(databaseSizeBeforeUpdate);
        Deployment testDeployment = deploymentList.get(deploymentList.size() - 1);
        assertThat(testDeployment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeployment.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeployment() throws Exception {
        int databaseSizeBeforeUpdate = deploymentRepository.findAll().size();

        // Create the Deployment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeploymentMockMvc.perform(put("/api/deployments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deployment)))
            .andExpect(status().isBadRequest());

        // Validate the Deployment in the database
        List<Deployment> deploymentList = deploymentRepository.findAll();
        assertThat(deploymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeployment() throws Exception {
        // Initialize the database
        deploymentService.save(deployment);

        int databaseSizeBeforeDelete = deploymentRepository.findAll().size();

        // Get the deployment
        restDeploymentMockMvc.perform(delete("/api/deployments/{id}", deployment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Deployment> deploymentList = deploymentRepository.findAll();
        assertThat(deploymentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deployment.class);
        Deployment deployment1 = new Deployment();
        deployment1.setId(1L);
        Deployment deployment2 = new Deployment();
        deployment2.setId(deployment1.getId());
        assertThat(deployment1).isEqualTo(deployment2);
        deployment2.setId(2L);
        assertThat(deployment1).isNotEqualTo(deployment2);
        deployment1.setId(null);
        assertThat(deployment1).isNotEqualTo(deployment2);
    }
}

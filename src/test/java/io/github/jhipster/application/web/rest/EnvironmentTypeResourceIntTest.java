package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.EnvironmentType;
import io.github.jhipster.application.repository.EnvironmentTypeRepository;
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
 * Test class for the EnvironmentTypeResource REST controller.
 *
 * @see EnvironmentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class EnvironmentTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EnvironmentTypeRepository environmentTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnvironmentTypeMockMvc;

    private EnvironmentType environmentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EnvironmentTypeResource environmentTypeResource = new EnvironmentTypeResource(environmentTypeRepository);
        this.restEnvironmentTypeMockMvc = MockMvcBuilders.standaloneSetup(environmentTypeResource)
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
    public static EnvironmentType createEntity(EntityManager em) {
        EnvironmentType environmentType = new EnvironmentType()
            .name(DEFAULT_NAME);
        return environmentType;
    }

    @Before
    public void initTest() {
        environmentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnvironmentType() throws Exception {
        int databaseSizeBeforeCreate = environmentTypeRepository.findAll().size();

        // Create the EnvironmentType
        restEnvironmentTypeMockMvc.perform(post("/api/environment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environmentType)))
            .andExpect(status().isCreated());

        // Validate the EnvironmentType in the database
        List<EnvironmentType> environmentTypeList = environmentTypeRepository.findAll();
        assertThat(environmentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        EnvironmentType testEnvironmentType = environmentTypeList.get(environmentTypeList.size() - 1);
        assertThat(testEnvironmentType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEnvironmentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = environmentTypeRepository.findAll().size();

        // Create the EnvironmentType with an existing ID
        environmentType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvironmentTypeMockMvc.perform(post("/api/environment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environmentType)))
            .andExpect(status().isBadRequest());

        // Validate the EnvironmentType in the database
        List<EnvironmentType> environmentTypeList = environmentTypeRepository.findAll();
        assertThat(environmentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEnvironmentTypes() throws Exception {
        // Initialize the database
        environmentTypeRepository.saveAndFlush(environmentType);

        // Get all the environmentTypeList
        restEnvironmentTypeMockMvc.perform(get("/api/environment-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(environmentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getEnvironmentType() throws Exception {
        // Initialize the database
        environmentTypeRepository.saveAndFlush(environmentType);

        // Get the environmentType
        restEnvironmentTypeMockMvc.perform(get("/api/environment-types/{id}", environmentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(environmentType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnvironmentType() throws Exception {
        // Get the environmentType
        restEnvironmentTypeMockMvc.perform(get("/api/environment-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnvironmentType() throws Exception {
        // Initialize the database
        environmentTypeRepository.saveAndFlush(environmentType);

        int databaseSizeBeforeUpdate = environmentTypeRepository.findAll().size();

        // Update the environmentType
        EnvironmentType updatedEnvironmentType = environmentTypeRepository.findById(environmentType.getId()).get();
        // Disconnect from session so that the updates on updatedEnvironmentType are not directly saved in db
        em.detach(updatedEnvironmentType);
        updatedEnvironmentType
            .name(UPDATED_NAME);

        restEnvironmentTypeMockMvc.perform(put("/api/environment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEnvironmentType)))
            .andExpect(status().isOk());

        // Validate the EnvironmentType in the database
        List<EnvironmentType> environmentTypeList = environmentTypeRepository.findAll();
        assertThat(environmentTypeList).hasSize(databaseSizeBeforeUpdate);
        EnvironmentType testEnvironmentType = environmentTypeList.get(environmentTypeList.size() - 1);
        assertThat(testEnvironmentType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEnvironmentType() throws Exception {
        int databaseSizeBeforeUpdate = environmentTypeRepository.findAll().size();

        // Create the EnvironmentType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvironmentTypeMockMvc.perform(put("/api/environment-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(environmentType)))
            .andExpect(status().isBadRequest());

        // Validate the EnvironmentType in the database
        List<EnvironmentType> environmentTypeList = environmentTypeRepository.findAll();
        assertThat(environmentTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnvironmentType() throws Exception {
        // Initialize the database
        environmentTypeRepository.saveAndFlush(environmentType);

        int databaseSizeBeforeDelete = environmentTypeRepository.findAll().size();

        // Get the environmentType
        restEnvironmentTypeMockMvc.perform(delete("/api/environment-types/{id}", environmentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EnvironmentType> environmentTypeList = environmentTypeRepository.findAll();
        assertThat(environmentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnvironmentType.class);
        EnvironmentType environmentType1 = new EnvironmentType();
        environmentType1.setId(1L);
        EnvironmentType environmentType2 = new EnvironmentType();
        environmentType2.setId(environmentType1.getId());
        assertThat(environmentType1).isEqualTo(environmentType2);
        environmentType2.setId(2L);
        assertThat(environmentType1).isNotEqualTo(environmentType2);
        environmentType1.setId(null);
        assertThat(environmentType1).isNotEqualTo(environmentType2);
    }
}

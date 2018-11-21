package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.Bscs;
import io.github.jhipster.application.domain.Service;
import io.github.jhipster.application.repository.BscsRepository;
import io.github.jhipster.application.service.BscsService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.BscsCriteria;
import io.github.jhipster.application.service.BscsQueryService;

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
 * Test class for the BscsResource REST controller.
 *
 * @see BscsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class BscsResourceIntTest {

    private static final String DEFAULT_SERVICES = "AAAAAAAAAA";
    private static final String UPDATED_SERVICES = "BBBBBBBBBB";

    @Autowired
    private BscsRepository bscsRepository;

    @Autowired
    private BscsService bscsService;

    @Autowired
    private BscsQueryService bscsQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBscsMockMvc;

    private Bscs bscs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BscsResource bscsResource = new BscsResource(bscsService, bscsQueryService);
        this.restBscsMockMvc = MockMvcBuilders.standaloneSetup(bscsResource)
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
    public static Bscs createEntity(EntityManager em) {
        Bscs bscs = new Bscs()
            .services(DEFAULT_SERVICES);
        return bscs;
    }

    @Before
    public void initTest() {
        bscs = createEntity(em);
    }

    @Test
    @Transactional
    public void createBscs() throws Exception {
        int databaseSizeBeforeCreate = bscsRepository.findAll().size();

        // Create the Bscs
        restBscsMockMvc.perform(post("/api/bscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bscs)))
            .andExpect(status().isCreated());

        // Validate the Bscs in the database
        List<Bscs> bscsList = bscsRepository.findAll();
        assertThat(bscsList).hasSize(databaseSizeBeforeCreate + 1);
        Bscs testBscs = bscsList.get(bscsList.size() - 1);
        assertThat(testBscs.getServices()).isEqualTo(DEFAULT_SERVICES);
    }

    @Test
    @Transactional
    public void createBscsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bscsRepository.findAll().size();

        // Create the Bscs with an existing ID
        bscs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBscsMockMvc.perform(post("/api/bscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bscs)))
            .andExpect(status().isBadRequest());

        // Validate the Bscs in the database
        List<Bscs> bscsList = bscsRepository.findAll();
        assertThat(bscsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBscs() throws Exception {
        // Initialize the database
        bscsRepository.saveAndFlush(bscs);

        // Get all the bscsList
        restBscsMockMvc.perform(get("/api/bscs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bscs.getId().intValue())))
            .andExpect(jsonPath("$.[*].services").value(hasItem(DEFAULT_SERVICES.toString())));
    }
    
    @Test
    @Transactional
    public void getBscs() throws Exception {
        // Initialize the database
        bscsRepository.saveAndFlush(bscs);

        // Get the bscs
        restBscsMockMvc.perform(get("/api/bscs/{id}", bscs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bscs.getId().intValue()))
            .andExpect(jsonPath("$.services").value(DEFAULT_SERVICES.toString()));
    }

    @Test
    @Transactional
    public void getAllBscsByServicesIsEqualToSomething() throws Exception {
        // Initialize the database
        bscsRepository.saveAndFlush(bscs);

        // Get all the bscsList where services equals to DEFAULT_SERVICES
        defaultBscsShouldBeFound("services.equals=" + DEFAULT_SERVICES);

        // Get all the bscsList where services equals to UPDATED_SERVICES
        defaultBscsShouldNotBeFound("services.equals=" + UPDATED_SERVICES);
    }

    @Test
    @Transactional
    public void getAllBscsByServicesIsInShouldWork() throws Exception {
        // Initialize the database
        bscsRepository.saveAndFlush(bscs);

        // Get all the bscsList where services in DEFAULT_SERVICES or UPDATED_SERVICES
        defaultBscsShouldBeFound("services.in=" + DEFAULT_SERVICES + "," + UPDATED_SERVICES);

        // Get all the bscsList where services equals to UPDATED_SERVICES
        defaultBscsShouldNotBeFound("services.in=" + UPDATED_SERVICES);
    }

    @Test
    @Transactional
    public void getAllBscsByServicesIsNullOrNotNull() throws Exception {
        // Initialize the database
        bscsRepository.saveAndFlush(bscs);

        // Get all the bscsList where services is not null
        defaultBscsShouldBeFound("services.specified=true");

        // Get all the bscsList where services is null
        defaultBscsShouldNotBeFound("services.specified=false");
    }

    @Test
    @Transactional
    public void getAllBscsByServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        Service service = ServiceResourceIntTest.createEntity(em);
        em.persist(service);
        em.flush();
        bscs.setService(service);
        bscsRepository.saveAndFlush(bscs);
        Long serviceId = service.getId();

        // Get all the bscsList where service equals to serviceId
        defaultBscsShouldBeFound("serviceId.equals=" + serviceId);

        // Get all the bscsList where service equals to serviceId + 1
        defaultBscsShouldNotBeFound("serviceId.equals=" + (serviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBscsShouldBeFound(String filter) throws Exception {
        restBscsMockMvc.perform(get("/api/bscs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bscs.getId().intValue())))
            .andExpect(jsonPath("$.[*].services").value(hasItem(DEFAULT_SERVICES.toString())));

        // Check, that the count call also returns 1
        restBscsMockMvc.perform(get("/api/bscs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBscsShouldNotBeFound(String filter) throws Exception {
        restBscsMockMvc.perform(get("/api/bscs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBscsMockMvc.perform(get("/api/bscs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBscs() throws Exception {
        // Get the bscs
        restBscsMockMvc.perform(get("/api/bscs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBscs() throws Exception {
        // Initialize the database
        bscsService.save(bscs);

        int databaseSizeBeforeUpdate = bscsRepository.findAll().size();

        // Update the bscs
        Bscs updatedBscs = bscsRepository.findById(bscs.getId()).get();
        // Disconnect from session so that the updates on updatedBscs are not directly saved in db
        em.detach(updatedBscs);
        updatedBscs
            .services(UPDATED_SERVICES);

        restBscsMockMvc.perform(put("/api/bscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBscs)))
            .andExpect(status().isOk());

        // Validate the Bscs in the database
        List<Bscs> bscsList = bscsRepository.findAll();
        assertThat(bscsList).hasSize(databaseSizeBeforeUpdate);
        Bscs testBscs = bscsList.get(bscsList.size() - 1);
        assertThat(testBscs.getServices()).isEqualTo(UPDATED_SERVICES);
    }

    @Test
    @Transactional
    public void updateNonExistingBscs() throws Exception {
        int databaseSizeBeforeUpdate = bscsRepository.findAll().size();

        // Create the Bscs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBscsMockMvc.perform(put("/api/bscs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bscs)))
            .andExpect(status().isBadRequest());

        // Validate the Bscs in the database
        List<Bscs> bscsList = bscsRepository.findAll();
        assertThat(bscsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBscs() throws Exception {
        // Initialize the database
        bscsService.save(bscs);

        int databaseSizeBeforeDelete = bscsRepository.findAll().size();

        // Get the bscs
        restBscsMockMvc.perform(delete("/api/bscs/{id}", bscs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bscs> bscsList = bscsRepository.findAll();
        assertThat(bscsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bscs.class);
        Bscs bscs1 = new Bscs();
        bscs1.setId(1L);
        Bscs bscs2 = new Bscs();
        bscs2.setId(bscs1.getId());
        assertThat(bscs1).isEqualTo(bscs2);
        bscs2.setId(2L);
        assertThat(bscs1).isNotEqualTo(bscs2);
        bscs1.setId(null);
        assertThat(bscs1).isNotEqualTo(bscs2);
    }
}

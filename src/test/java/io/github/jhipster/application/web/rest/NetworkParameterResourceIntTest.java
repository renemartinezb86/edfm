package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.NetworkParameter;
import io.github.jhipster.application.domain.NetworkResource;
import io.github.jhipster.application.repository.NetworkParameterRepository;
import io.github.jhipster.application.service.NetworkParameterService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.NetworkParameterCriteria;
import io.github.jhipster.application.service.NetworkParameterQueryService;

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
 * Test class for the NetworkParameterResource REST controller.
 *
 * @see NetworkParameterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class NetworkParameterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private NetworkParameterRepository networkParameterRepository;

    @Autowired
    private NetworkParameterService networkParameterService;

    @Autowired
    private NetworkParameterQueryService networkParameterQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNetworkParameterMockMvc;

    private NetworkParameter networkParameter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NetworkParameterResource networkParameterResource = new NetworkParameterResource(networkParameterService, networkParameterQueryService);
        this.restNetworkParameterMockMvc = MockMvcBuilders.standaloneSetup(networkParameterResource)
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
    public static NetworkParameter createEntity(EntityManager em) {
        NetworkParameter networkParameter = new NetworkParameter()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE);
        return networkParameter;
    }

    @Before
    public void initTest() {
        networkParameter = createEntity(em);
    }

    @Test
    @Transactional
    public void createNetworkParameter() throws Exception {
        int databaseSizeBeforeCreate = networkParameterRepository.findAll().size();

        // Create the NetworkParameter
        restNetworkParameterMockMvc.perform(post("/api/network-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkParameter)))
            .andExpect(status().isCreated());

        // Validate the NetworkParameter in the database
        List<NetworkParameter> networkParameterList = networkParameterRepository.findAll();
        assertThat(networkParameterList).hasSize(databaseSizeBeforeCreate + 1);
        NetworkParameter testNetworkParameter = networkParameterList.get(networkParameterList.size() - 1);
        assertThat(testNetworkParameter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNetworkParameter.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNetworkParameter.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createNetworkParameterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = networkParameterRepository.findAll().size();

        // Create the NetworkParameter with an existing ID
        networkParameter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetworkParameterMockMvc.perform(post("/api/network-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkParameter)))
            .andExpect(status().isBadRequest());

        // Validate the NetworkParameter in the database
        List<NetworkParameter> networkParameterList = networkParameterRepository.findAll();
        assertThat(networkParameterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNetworkParameters() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList
        restNetworkParameterMockMvc.perform(get("/api/network-parameters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    
    @Test
    @Transactional
    public void getNetworkParameter() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get the networkParameter
        restNetworkParameterMockMvc.perform(get("/api/network-parameters/{id}", networkParameter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(networkParameter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList where name equals to DEFAULT_NAME
        defaultNetworkParameterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the networkParameterList where name equals to UPDATED_NAME
        defaultNetworkParameterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNetworkParameterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the networkParameterList where name equals to UPDATED_NAME
        defaultNetworkParameterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList where name is not null
        defaultNetworkParameterShouldBeFound("name.specified=true");

        // Get all the networkParameterList where name is null
        defaultNetworkParameterShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList where type equals to DEFAULT_TYPE
        defaultNetworkParameterShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the networkParameterList where type equals to UPDATED_TYPE
        defaultNetworkParameterShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultNetworkParameterShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the networkParameterList where type equals to UPDATED_TYPE
        defaultNetworkParameterShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList where type is not null
        defaultNetworkParameterShouldBeFound("type.specified=true");

        // Get all the networkParameterList where type is null
        defaultNetworkParameterShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList where value equals to DEFAULT_VALUE
        defaultNetworkParameterShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the networkParameterList where value equals to UPDATED_VALUE
        defaultNetworkParameterShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByValueIsInShouldWork() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultNetworkParameterShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the networkParameterList where value equals to UPDATED_VALUE
        defaultNetworkParameterShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkParameterRepository.saveAndFlush(networkParameter);

        // Get all the networkParameterList where value is not null
        defaultNetworkParameterShouldBeFound("value.specified=true");

        // Get all the networkParameterList where value is null
        defaultNetworkParameterShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllNetworkParametersByNetworkResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        NetworkResource networkResource = NetworkResourceResourceIntTest.createEntity(em);
        em.persist(networkResource);
        em.flush();
        networkParameter.addNetworkResource(networkResource);
        networkParameterRepository.saveAndFlush(networkParameter);
        Long networkResourceId = networkResource.getId();

        // Get all the networkParameterList where networkResource equals to networkResourceId
        defaultNetworkParameterShouldBeFound("networkResourceId.equals=" + networkResourceId);

        // Get all the networkParameterList where networkResource equals to networkResourceId + 1
        defaultNetworkParameterShouldNotBeFound("networkResourceId.equals=" + (networkResourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNetworkParameterShouldBeFound(String filter) throws Exception {
        restNetworkParameterMockMvc.perform(get("/api/network-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkParameter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));

        // Check, that the count call also returns 1
        restNetworkParameterMockMvc.perform(get("/api/network-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNetworkParameterShouldNotBeFound(String filter) throws Exception {
        restNetworkParameterMockMvc.perform(get("/api/network-parameters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNetworkParameterMockMvc.perform(get("/api/network-parameters/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNetworkParameter() throws Exception {
        // Get the networkParameter
        restNetworkParameterMockMvc.perform(get("/api/network-parameters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNetworkParameter() throws Exception {
        // Initialize the database
        networkParameterService.save(networkParameter);

        int databaseSizeBeforeUpdate = networkParameterRepository.findAll().size();

        // Update the networkParameter
        NetworkParameter updatedNetworkParameter = networkParameterRepository.findById(networkParameter.getId()).get();
        // Disconnect from session so that the updates on updatedNetworkParameter are not directly saved in db
        em.detach(updatedNetworkParameter);
        updatedNetworkParameter
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE);

        restNetworkParameterMockMvc.perform(put("/api/network-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNetworkParameter)))
            .andExpect(status().isOk());

        // Validate the NetworkParameter in the database
        List<NetworkParameter> networkParameterList = networkParameterRepository.findAll();
        assertThat(networkParameterList).hasSize(databaseSizeBeforeUpdate);
        NetworkParameter testNetworkParameter = networkParameterList.get(networkParameterList.size() - 1);
        assertThat(testNetworkParameter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNetworkParameter.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNetworkParameter.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingNetworkParameter() throws Exception {
        int databaseSizeBeforeUpdate = networkParameterRepository.findAll().size();

        // Create the NetworkParameter

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworkParameterMockMvc.perform(put("/api/network-parameters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkParameter)))
            .andExpect(status().isBadRequest());

        // Validate the NetworkParameter in the database
        List<NetworkParameter> networkParameterList = networkParameterRepository.findAll();
        assertThat(networkParameterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNetworkParameter() throws Exception {
        // Initialize the database
        networkParameterService.save(networkParameter);

        int databaseSizeBeforeDelete = networkParameterRepository.findAll().size();

        // Get the networkParameter
        restNetworkParameterMockMvc.perform(delete("/api/network-parameters/{id}", networkParameter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NetworkParameter> networkParameterList = networkParameterRepository.findAll();
        assertThat(networkParameterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetworkParameter.class);
        NetworkParameter networkParameter1 = new NetworkParameter();
        networkParameter1.setId(1L);
        NetworkParameter networkParameter2 = new NetworkParameter();
        networkParameter2.setId(networkParameter1.getId());
        assertThat(networkParameter1).isEqualTo(networkParameter2);
        networkParameter2.setId(2L);
        assertThat(networkParameter1).isNotEqualTo(networkParameter2);
        networkParameter1.setId(null);
        assertThat(networkParameter1).isNotEqualTo(networkParameter2);
    }
}

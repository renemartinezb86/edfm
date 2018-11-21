package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.NetworkResource;
import io.github.jhipster.application.domain.NetworkParameter;
import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.repository.NetworkResourceRepository;
import io.github.jhipster.application.service.NetworkResourceService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.NetworkResourceCriteria;
import io.github.jhipster.application.service.NetworkResourceQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NetworkResourceResource REST controller.
 *
 * @see NetworkResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class NetworkResourceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private NetworkResourceRepository networkResourceRepository;

    @Mock
    private NetworkResourceRepository networkResourceRepositoryMock;

    @Mock
    private NetworkResourceService networkResourceServiceMock;

    @Autowired
    private NetworkResourceService networkResourceService;

    @Autowired
    private NetworkResourceQueryService networkResourceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNetworkResourceMockMvc;

    private NetworkResource networkResource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NetworkResourceResource networkResourceResource = new NetworkResourceResource(networkResourceService, networkResourceQueryService);
        this.restNetworkResourceMockMvc = MockMvcBuilders.standaloneSetup(networkResourceResource)
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
    public static NetworkResource createEntity(EntityManager em) {
        NetworkResource networkResource = new NetworkResource()
            .name(DEFAULT_NAME);
        return networkResource;
    }

    @Before
    public void initTest() {
        networkResource = createEntity(em);
    }

    @Test
    @Transactional
    public void createNetworkResource() throws Exception {
        int databaseSizeBeforeCreate = networkResourceRepository.findAll().size();

        // Create the NetworkResource
        restNetworkResourceMockMvc.perform(post("/api/network-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkResource)))
            .andExpect(status().isCreated());

        // Validate the NetworkResource in the database
        List<NetworkResource> networkResourceList = networkResourceRepository.findAll();
        assertThat(networkResourceList).hasSize(databaseSizeBeforeCreate + 1);
        NetworkResource testNetworkResource = networkResourceList.get(networkResourceList.size() - 1);
        assertThat(testNetworkResource.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createNetworkResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = networkResourceRepository.findAll().size();

        // Create the NetworkResource with an existing ID
        networkResource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNetworkResourceMockMvc.perform(post("/api/network-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkResource)))
            .andExpect(status().isBadRequest());

        // Validate the NetworkResource in the database
        List<NetworkResource> networkResourceList = networkResourceRepository.findAll();
        assertThat(networkResourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNetworkResources() throws Exception {
        // Initialize the database
        networkResourceRepository.saveAndFlush(networkResource);

        // Get all the networkResourceList
        restNetworkResourceMockMvc.perform(get("/api/network-resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllNetworkResourcesWithEagerRelationshipsIsEnabled() throws Exception {
        NetworkResourceResource networkResourceResource = new NetworkResourceResource(networkResourceServiceMock, networkResourceQueryService);
        when(networkResourceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restNetworkResourceMockMvc = MockMvcBuilders.standaloneSetup(networkResourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restNetworkResourceMockMvc.perform(get("/api/network-resources?eagerload=true"))
        .andExpect(status().isOk());

        verify(networkResourceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllNetworkResourcesWithEagerRelationshipsIsNotEnabled() throws Exception {
        NetworkResourceResource networkResourceResource = new NetworkResourceResource(networkResourceServiceMock, networkResourceQueryService);
            when(networkResourceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restNetworkResourceMockMvc = MockMvcBuilders.standaloneSetup(networkResourceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restNetworkResourceMockMvc.perform(get("/api/network-resources?eagerload=true"))
        .andExpect(status().isOk());

            verify(networkResourceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getNetworkResource() throws Exception {
        // Initialize the database
        networkResourceRepository.saveAndFlush(networkResource);

        // Get the networkResource
        restNetworkResourceMockMvc.perform(get("/api/network-resources/{id}", networkResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(networkResource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllNetworkResourcesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        networkResourceRepository.saveAndFlush(networkResource);

        // Get all the networkResourceList where name equals to DEFAULT_NAME
        defaultNetworkResourceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the networkResourceList where name equals to UPDATED_NAME
        defaultNetworkResourceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNetworkResourcesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        networkResourceRepository.saveAndFlush(networkResource);

        // Get all the networkResourceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNetworkResourceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the networkResourceList where name equals to UPDATED_NAME
        defaultNetworkResourceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllNetworkResourcesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        networkResourceRepository.saveAndFlush(networkResource);

        // Get all the networkResourceList where name is not null
        defaultNetworkResourceShouldBeFound("name.specified=true");

        // Get all the networkResourceList where name is null
        defaultNetworkResourceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllNetworkResourcesByNetworkParameterIsEqualToSomething() throws Exception {
        // Initialize the database
        NetworkParameter networkParameter = NetworkParameterResourceIntTest.createEntity(em);
        em.persist(networkParameter);
        em.flush();
        networkResource.addNetworkParameter(networkParameter);
        networkResourceRepository.saveAndFlush(networkResource);
        Long networkParameterId = networkParameter.getId();

        // Get all the networkResourceList where networkParameter equals to networkParameterId
        defaultNetworkResourceShouldBeFound("networkParameterId.equals=" + networkParameterId);

        // Get all the networkResourceList where networkParameter equals to networkParameterId + 1
        defaultNetworkResourceShouldNotBeFound("networkParameterId.equals=" + (networkParameterId + 1));
    }


    @Test
    @Transactional
    public void getAllNetworkResourcesByProductOfferingIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductOffering productOffering = ProductOfferingResourceIntTest.createEntity(em);
        em.persist(productOffering);
        em.flush();
        networkResource.addProductOffering(productOffering);
        networkResourceRepository.saveAndFlush(networkResource);
        Long productOfferingId = productOffering.getId();

        // Get all the networkResourceList where productOffering equals to productOfferingId
        defaultNetworkResourceShouldBeFound("productOfferingId.equals=" + productOfferingId);

        // Get all the networkResourceList where productOffering equals to productOfferingId + 1
        defaultNetworkResourceShouldNotBeFound("productOfferingId.equals=" + (productOfferingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNetworkResourceShouldBeFound(String filter) throws Exception {
        restNetworkResourceMockMvc.perform(get("/api/network-resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(networkResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));

        // Check, that the count call also returns 1
        restNetworkResourceMockMvc.perform(get("/api/network-resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNetworkResourceShouldNotBeFound(String filter) throws Exception {
        restNetworkResourceMockMvc.perform(get("/api/network-resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNetworkResourceMockMvc.perform(get("/api/network-resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNetworkResource() throws Exception {
        // Get the networkResource
        restNetworkResourceMockMvc.perform(get("/api/network-resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNetworkResource() throws Exception {
        // Initialize the database
        networkResourceService.save(networkResource);

        int databaseSizeBeforeUpdate = networkResourceRepository.findAll().size();

        // Update the networkResource
        NetworkResource updatedNetworkResource = networkResourceRepository.findById(networkResource.getId()).get();
        // Disconnect from session so that the updates on updatedNetworkResource are not directly saved in db
        em.detach(updatedNetworkResource);
        updatedNetworkResource
            .name(UPDATED_NAME);

        restNetworkResourceMockMvc.perform(put("/api/network-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNetworkResource)))
            .andExpect(status().isOk());

        // Validate the NetworkResource in the database
        List<NetworkResource> networkResourceList = networkResourceRepository.findAll();
        assertThat(networkResourceList).hasSize(databaseSizeBeforeUpdate);
        NetworkResource testNetworkResource = networkResourceList.get(networkResourceList.size() - 1);
        assertThat(testNetworkResource.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNetworkResource() throws Exception {
        int databaseSizeBeforeUpdate = networkResourceRepository.findAll().size();

        // Create the NetworkResource

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNetworkResourceMockMvc.perform(put("/api/network-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(networkResource)))
            .andExpect(status().isBadRequest());

        // Validate the NetworkResource in the database
        List<NetworkResource> networkResourceList = networkResourceRepository.findAll();
        assertThat(networkResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNetworkResource() throws Exception {
        // Initialize the database
        networkResourceService.save(networkResource);

        int databaseSizeBeforeDelete = networkResourceRepository.findAll().size();

        // Get the networkResource
        restNetworkResourceMockMvc.perform(delete("/api/network-resources/{id}", networkResource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NetworkResource> networkResourceList = networkResourceRepository.findAll();
        assertThat(networkResourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NetworkResource.class);
        NetworkResource networkResource1 = new NetworkResource();
        networkResource1.setId(1L);
        NetworkResource networkResource2 = new NetworkResource();
        networkResource2.setId(networkResource1.getId());
        assertThat(networkResource1).isEqualTo(networkResource2);
        networkResource2.setId(2L);
        assertThat(networkResource1).isNotEqualTo(networkResource2);
        networkResource1.setId(null);
        assertThat(networkResource1).isNotEqualTo(networkResource2);
    }
}

package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.Service;
import io.github.jhipster.application.domain.ChargingSystem;
import io.github.jhipster.application.domain.Bscs;
import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.repository.ServiceRepository;
import io.github.jhipster.application.service.ServiceService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.ServiceCriteria;
import io.github.jhipster.application.service.ServiceQueryService;

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

import io.github.jhipster.application.domain.enumeration.ServiceType;
/**
 * Test class for the ServiceResource REST controller.
 *
 * @see ServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class ServiceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ServiceType DEFAULT_SERVICE_TYPE = ServiceType.Basic;
    private static final ServiceType UPDATED_SERVICE_TYPE = ServiceType.Barring;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ServiceQueryService serviceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restServiceMockMvc;

    private Service service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceResource serviceResource = new ServiceResource(serviceService, serviceQueryService);
        this.restServiceMockMvc = MockMvcBuilders.standaloneSetup(serviceResource)
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
    public static Service createEntity(EntityManager em) {
        Service service = new Service()
            .name(DEFAULT_NAME)
            .serviceType(DEFAULT_SERVICE_TYPE);
        return service;
    }

    @Before
    public void initTest() {
        service = createEntity(em);
    }

    @Test
    @Transactional
    public void createService() throws Exception {
        int databaseSizeBeforeCreate = serviceRepository.findAll().size();

        // Create the Service
        restServiceMockMvc.perform(post("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isCreated());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeCreate + 1);
        Service testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testService.getServiceType()).isEqualTo(DEFAULT_SERVICE_TYPE);
    }

    @Test
    @Transactional
    public void createServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceRepository.findAll().size();

        // Create the Service with an existing ID
        service.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceMockMvc.perform(post("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllServices() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList
        restServiceMockMvc.perform(get("/api/services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getService() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get the service
        restServiceMockMvc.perform(get("/api/services/{id}", service.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(service.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.serviceType").value(DEFAULT_SERVICE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllServicesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where name equals to DEFAULT_NAME
        defaultServiceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the serviceList where name equals to UPDATED_NAME
        defaultServiceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllServicesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultServiceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the serviceList where name equals to UPDATED_NAME
        defaultServiceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllServicesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where name is not null
        defaultServiceShouldBeFound("name.specified=true");

        // Get all the serviceList where name is null
        defaultServiceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllServicesByServiceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where serviceType equals to DEFAULT_SERVICE_TYPE
        defaultServiceShouldBeFound("serviceType.equals=" + DEFAULT_SERVICE_TYPE);

        // Get all the serviceList where serviceType equals to UPDATED_SERVICE_TYPE
        defaultServiceShouldNotBeFound("serviceType.equals=" + UPDATED_SERVICE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServicesByServiceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where serviceType in DEFAULT_SERVICE_TYPE or UPDATED_SERVICE_TYPE
        defaultServiceShouldBeFound("serviceType.in=" + DEFAULT_SERVICE_TYPE + "," + UPDATED_SERVICE_TYPE);

        // Get all the serviceList where serviceType equals to UPDATED_SERVICE_TYPE
        defaultServiceShouldNotBeFound("serviceType.in=" + UPDATED_SERVICE_TYPE);
    }

    @Test
    @Transactional
    public void getAllServicesByServiceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceRepository.saveAndFlush(service);

        // Get all the serviceList where serviceType is not null
        defaultServiceShouldBeFound("serviceType.specified=true");

        // Get all the serviceList where serviceType is null
        defaultServiceShouldNotBeFound("serviceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllServicesByChargingSystemIsEqualToSomething() throws Exception {
        // Initialize the database
        ChargingSystem chargingSystem = ChargingSystemResourceIntTest.createEntity(em);
        em.persist(chargingSystem);
        em.flush();
        service.setChargingSystem(chargingSystem);
        chargingSystem.setService(service);
        serviceRepository.saveAndFlush(service);
        Long chargingSystemId = chargingSystem.getId();

        // Get all the serviceList where chargingSystem equals to chargingSystemId
        defaultServiceShouldBeFound("chargingSystemId.equals=" + chargingSystemId);

        // Get all the serviceList where chargingSystem equals to chargingSystemId + 1
        defaultServiceShouldNotBeFound("chargingSystemId.equals=" + (chargingSystemId + 1));
    }


    @Test
    @Transactional
    public void getAllServicesByBscsIsEqualToSomething() throws Exception {
        // Initialize the database
        Bscs bscs = BscsResourceIntTest.createEntity(em);
        em.persist(bscs);
        em.flush();
        service.setBscs(bscs);
        bscs.setService(service);
        serviceRepository.saveAndFlush(service);
        Long bscsId = bscs.getId();

        // Get all the serviceList where bscs equals to bscsId
        defaultServiceShouldBeFound("bscsId.equals=" + bscsId);

        // Get all the serviceList where bscs equals to bscsId + 1
        defaultServiceShouldNotBeFound("bscsId.equals=" + (bscsId + 1));
    }


    @Test
    @Transactional
    public void getAllServicesByProductOfferingIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductOffering productOffering = ProductOfferingResourceIntTest.createEntity(em);
        em.persist(productOffering);
        em.flush();
        service.addProductOffering(productOffering);
        serviceRepository.saveAndFlush(service);
        Long productOfferingId = productOffering.getId();

        // Get all the serviceList where productOffering equals to productOfferingId
        defaultServiceShouldBeFound("productOfferingId.equals=" + productOfferingId);

        // Get all the serviceList where productOffering equals to productOfferingId + 1
        defaultServiceShouldNotBeFound("productOfferingId.equals=" + (productOfferingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultServiceShouldBeFound(String filter) throws Exception {
        restServiceMockMvc.perform(get("/api/services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(service.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceType").value(hasItem(DEFAULT_SERVICE_TYPE.toString())));

        // Check, that the count call also returns 1
        restServiceMockMvc.perform(get("/api/services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultServiceShouldNotBeFound(String filter) throws Exception {
        restServiceMockMvc.perform(get("/api/services?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceMockMvc.perform(get("/api/services/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingService() throws Exception {
        // Get the service
        restServiceMockMvc.perform(get("/api/services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateService() throws Exception {
        // Initialize the database
        serviceService.save(service);

        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Update the service
        Service updatedService = serviceRepository.findById(service.getId()).get();
        // Disconnect from session so that the updates on updatedService are not directly saved in db
        em.detach(updatedService);
        updatedService
            .name(UPDATED_NAME)
            .serviceType(UPDATED_SERVICE_TYPE);

        restServiceMockMvc.perform(put("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedService)))
            .andExpect(status().isOk());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
        Service testService = serviceList.get(serviceList.size() - 1);
        assertThat(testService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testService.getServiceType()).isEqualTo(UPDATED_SERVICE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingService() throws Exception {
        int databaseSizeBeforeUpdate = serviceRepository.findAll().size();

        // Create the Service

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceMockMvc.perform(put("/api/services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(service)))
            .andExpect(status().isBadRequest());

        // Validate the Service in the database
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteService() throws Exception {
        // Initialize the database
        serviceService.save(service);

        int databaseSizeBeforeDelete = serviceRepository.findAll().size();

        // Get the service
        restServiceMockMvc.perform(delete("/api/services/{id}", service.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Service> serviceList = serviceRepository.findAll();
        assertThat(serviceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Service.class);
        Service service1 = new Service();
        service1.setId(1L);
        Service service2 = new Service();
        service2.setId(service1.getId());
        assertThat(service1).isEqualTo(service2);
        service2.setId(2L);
        assertThat(service1).isNotEqualTo(service2);
        service1.setId(null);
        assertThat(service1).isNotEqualTo(service2);
    }
}

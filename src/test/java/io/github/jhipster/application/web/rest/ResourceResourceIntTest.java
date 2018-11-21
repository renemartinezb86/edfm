package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.Resource;
import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.repository.ResourceRepository;
import io.github.jhipster.application.service.ResourceService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.ResourceCriteria;
import io.github.jhipster.application.service.ResourceQueryService;

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

import io.github.jhipster.application.domain.enumeration.ResourceType;
/**
 * Test class for the ResourceResource REST controller.
 *
 * @see ResourceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class ResourceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ResourceType DEFAULT_RESOURCE_TYPE = ResourceType.Availability;
    private static final ResourceType UPDATED_RESOURCE_TYPE = ResourceType.Validation;

    private static final String DEFAULT_RELATED_ITEM = "AAAAAAAAAA";
    private static final String UPDATED_RELATED_ITEM = "BBBBBBBBBB";

    private static final String DEFAULT_RELATED_ITEM_CHARACTERISTIC = "AAAAAAAAAA";
    private static final String UPDATED_RELATED_ITEM_CHARACTERISTIC = "BBBBBBBBBB";

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceQueryService resourceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResourceMockMvc;

    private Resource resource;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourceResource resourceResource = new ResourceResource(resourceService, resourceQueryService);
        this.restResourceMockMvc = MockMvcBuilders.standaloneSetup(resourceResource)
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
    public static Resource createEntity(EntityManager em) {
        Resource resource = new Resource()
            .name(DEFAULT_NAME)
            .resourceType(DEFAULT_RESOURCE_TYPE)
            .relatedItem(DEFAULT_RELATED_ITEM)
            .relatedItemCharacteristic(DEFAULT_RELATED_ITEM_CHARACTERISTIC);
        return resource;
    }

    @Before
    public void initTest() {
        resource = createEntity(em);
    }

    @Test
    @Transactional
    public void createResource() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resource
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isCreated());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate + 1);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResource.getResourceType()).isEqualTo(DEFAULT_RESOURCE_TYPE);
        assertThat(testResource.getRelatedItem()).isEqualTo(DEFAULT_RELATED_ITEM);
        assertThat(testResource.getRelatedItemCharacteristic()).isEqualTo(DEFAULT_RELATED_ITEM_CHARACTERISTIC);
    }

    @Test
    @Transactional
    public void createResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resource with an existing ID
        resource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllResources() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].resourceType").value(hasItem(DEFAULT_RESOURCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relatedItem").value(hasItem(DEFAULT_RELATED_ITEM.toString())))
            .andExpect(jsonPath("$.[*].relatedItemCharacteristic").value(hasItem(DEFAULT_RELATED_ITEM_CHARACTERISTIC.toString())));
    }
    
    @Test
    @Transactional
    public void getResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", resource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.resourceType").value(DEFAULT_RESOURCE_TYPE.toString()))
            .andExpect(jsonPath("$.relatedItem").value(DEFAULT_RELATED_ITEM.toString()))
            .andExpect(jsonPath("$.relatedItemCharacteristic").value(DEFAULT_RELATED_ITEM_CHARACTERISTIC.toString()));
    }

    @Test
    @Transactional
    public void getAllResourcesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where name equals to DEFAULT_NAME
        defaultResourceShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the resourceList where name equals to UPDATED_NAME
        defaultResourceShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllResourcesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where name in DEFAULT_NAME or UPDATED_NAME
        defaultResourceShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the resourceList where name equals to UPDATED_NAME
        defaultResourceShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllResourcesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where name is not null
        defaultResourceShouldBeFound("name.specified=true");

        // Get all the resourceList where name is null
        defaultResourceShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResourceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resourceType equals to DEFAULT_RESOURCE_TYPE
        defaultResourceShouldBeFound("resourceType.equals=" + DEFAULT_RESOURCE_TYPE);

        // Get all the resourceList where resourceType equals to UPDATED_RESOURCE_TYPE
        defaultResourceShouldNotBeFound("resourceType.equals=" + UPDATED_RESOURCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResourceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resourceType in DEFAULT_RESOURCE_TYPE or UPDATED_RESOURCE_TYPE
        defaultResourceShouldBeFound("resourceType.in=" + DEFAULT_RESOURCE_TYPE + "," + UPDATED_RESOURCE_TYPE);

        // Get all the resourceList where resourceType equals to UPDATED_RESOURCE_TYPE
        defaultResourceShouldNotBeFound("resourceType.in=" + UPDATED_RESOURCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResourceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resourceType is not null
        defaultResourceShouldBeFound("resourceType.specified=true");

        // Get all the resourceList where resourceType is null
        defaultResourceShouldNotBeFound("resourceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByRelatedItemIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where relatedItem equals to DEFAULT_RELATED_ITEM
        defaultResourceShouldBeFound("relatedItem.equals=" + DEFAULT_RELATED_ITEM);

        // Get all the resourceList where relatedItem equals to UPDATED_RELATED_ITEM
        defaultResourceShouldNotBeFound("relatedItem.equals=" + UPDATED_RELATED_ITEM);
    }

    @Test
    @Transactional
    public void getAllResourcesByRelatedItemIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where relatedItem in DEFAULT_RELATED_ITEM or UPDATED_RELATED_ITEM
        defaultResourceShouldBeFound("relatedItem.in=" + DEFAULT_RELATED_ITEM + "," + UPDATED_RELATED_ITEM);

        // Get all the resourceList where relatedItem equals to UPDATED_RELATED_ITEM
        defaultResourceShouldNotBeFound("relatedItem.in=" + UPDATED_RELATED_ITEM);
    }

    @Test
    @Transactional
    public void getAllResourcesByRelatedItemIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where relatedItem is not null
        defaultResourceShouldBeFound("relatedItem.specified=true");

        // Get all the resourceList where relatedItem is null
        defaultResourceShouldNotBeFound("relatedItem.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByRelatedItemCharacteristicIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where relatedItemCharacteristic equals to DEFAULT_RELATED_ITEM_CHARACTERISTIC
        defaultResourceShouldBeFound("relatedItemCharacteristic.equals=" + DEFAULT_RELATED_ITEM_CHARACTERISTIC);

        // Get all the resourceList where relatedItemCharacteristic equals to UPDATED_RELATED_ITEM_CHARACTERISTIC
        defaultResourceShouldNotBeFound("relatedItemCharacteristic.equals=" + UPDATED_RELATED_ITEM_CHARACTERISTIC);
    }

    @Test
    @Transactional
    public void getAllResourcesByRelatedItemCharacteristicIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where relatedItemCharacteristic in DEFAULT_RELATED_ITEM_CHARACTERISTIC or UPDATED_RELATED_ITEM_CHARACTERISTIC
        defaultResourceShouldBeFound("relatedItemCharacteristic.in=" + DEFAULT_RELATED_ITEM_CHARACTERISTIC + "," + UPDATED_RELATED_ITEM_CHARACTERISTIC);

        // Get all the resourceList where relatedItemCharacteristic equals to UPDATED_RELATED_ITEM_CHARACTERISTIC
        defaultResourceShouldNotBeFound("relatedItemCharacteristic.in=" + UPDATED_RELATED_ITEM_CHARACTERISTIC);
    }

    @Test
    @Transactional
    public void getAllResourcesByRelatedItemCharacteristicIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where relatedItemCharacteristic is not null
        defaultResourceShouldBeFound("relatedItemCharacteristic.specified=true");

        // Get all the resourceList where relatedItemCharacteristic is null
        defaultResourceShouldNotBeFound("relatedItemCharacteristic.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByProductOfferingIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductOffering productOffering = ProductOfferingResourceIntTest.createEntity(em);
        em.persist(productOffering);
        em.flush();
        resource.addProductOffering(productOffering);
        resourceRepository.saveAndFlush(resource);
        Long productOfferingId = productOffering.getId();

        // Get all the resourceList where productOffering equals to productOfferingId
        defaultResourceShouldBeFound("productOfferingId.equals=" + productOfferingId);

        // Get all the resourceList where productOffering equals to productOfferingId + 1
        defaultResourceShouldNotBeFound("productOfferingId.equals=" + (productOfferingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultResourceShouldBeFound(String filter) throws Exception {
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].resourceType").value(hasItem(DEFAULT_RESOURCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].relatedItem").value(hasItem(DEFAULT_RELATED_ITEM.toString())))
            .andExpect(jsonPath("$.[*].relatedItemCharacteristic").value(hasItem(DEFAULT_RELATED_ITEM_CHARACTERISTIC.toString())));

        // Check, that the count call also returns 1
        restResourceMockMvc.perform(get("/api/resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultResourceShouldNotBeFound(String filter) throws Exception {
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResourceMockMvc.perform(get("/api/resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResource() throws Exception {
        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResource() throws Exception {
        // Initialize the database
        resourceService.save(resource);

        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Update the resource
        Resource updatedResource = resourceRepository.findById(resource.getId()).get();
        // Disconnect from session so that the updates on updatedResource are not directly saved in db
        em.detach(updatedResource);
        updatedResource
            .name(UPDATED_NAME)
            .resourceType(UPDATED_RESOURCE_TYPE)
            .relatedItem(UPDATED_RELATED_ITEM)
            .relatedItemCharacteristic(UPDATED_RELATED_ITEM_CHARACTERISTIC);

        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResource)))
            .andExpect(status().isOk());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResource.getResourceType()).isEqualTo(UPDATED_RESOURCE_TYPE);
        assertThat(testResource.getRelatedItem()).isEqualTo(UPDATED_RELATED_ITEM);
        assertThat(testResource.getRelatedItemCharacteristic()).isEqualTo(UPDATED_RELATED_ITEM_CHARACTERISTIC);
    }

    @Test
    @Transactional
    public void updateNonExistingResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Create the Resource

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResource() throws Exception {
        // Initialize the database
        resourceService.save(resource);

        int databaseSizeBeforeDelete = resourceRepository.findAll().size();

        // Get the resource
        restResourceMockMvc.perform(delete("/api/resources/{id}", resource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resource.class);
        Resource resource1 = new Resource();
        resource1.setId(1L);
        Resource resource2 = new Resource();
        resource2.setId(resource1.getId());
        assertThat(resource1).isEqualTo(resource2);
        resource2.setId(2L);
        assertThat(resource1).isNotEqualTo(resource2);
        resource1.setId(null);
        assertThat(resource1).isNotEqualTo(resource2);
    }
}

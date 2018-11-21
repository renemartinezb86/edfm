package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.domain.PoPrice;
import io.github.jhipster.application.domain.BucoSheet;
import io.github.jhipster.application.domain.Rule;
import io.github.jhipster.application.domain.Resource;
import io.github.jhipster.application.domain.NetworkResource;
import io.github.jhipster.application.domain.Service;
import io.github.jhipster.application.domain.CfssPop;
import io.github.jhipster.application.domain.FreeUnit;
import io.github.jhipster.application.repository.ProductOfferingRepository;
import io.github.jhipster.application.service.ProductOfferingService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.ProductOfferingCriteria;
import io.github.jhipster.application.service.ProductOfferingQueryService;

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
 * Test class for the ProductOfferingResource REST controller.
 *
 * @see ProductOfferingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class ProductOfferingResourceIntTest {

    private static final String DEFAULT_PO_ID = "AAAAAAAAAA";
    private static final String UPDATED_PO_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMERCIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMERCIAL_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductOfferingRepository productOfferingRepository;

    @Mock
    private ProductOfferingRepository productOfferingRepositoryMock;

    @Mock
    private ProductOfferingService productOfferingServiceMock;

    @Autowired
    private ProductOfferingService productOfferingService;

    @Autowired
    private ProductOfferingQueryService productOfferingQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProductOfferingMockMvc;

    private ProductOffering productOffering;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductOfferingResource productOfferingResource = new ProductOfferingResource(productOfferingService, productOfferingQueryService);
        this.restProductOfferingMockMvc = MockMvcBuilders.standaloneSetup(productOfferingResource)
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
    public static ProductOffering createEntity(EntityManager em) {
        ProductOffering productOffering = new ProductOffering()
            .poId(DEFAULT_PO_ID)
            .name(DEFAULT_NAME)
            .comercialName(DEFAULT_COMERCIAL_NAME);
        return productOffering;
    }

    @Before
    public void initTest() {
        productOffering = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOffering() throws Exception {
        int databaseSizeBeforeCreate = productOfferingRepository.findAll().size();

        // Create the ProductOffering
        restProductOfferingMockMvc.perform(post("/api/product-offerings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOffering)))
            .andExpect(status().isCreated());

        // Validate the ProductOffering in the database
        List<ProductOffering> productOfferingList = productOfferingRepository.findAll();
        assertThat(productOfferingList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOffering testProductOffering = productOfferingList.get(productOfferingList.size() - 1);
        assertThat(testProductOffering.getPoId()).isEqualTo(DEFAULT_PO_ID);
        assertThat(testProductOffering.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOffering.getComercialName()).isEqualTo(DEFAULT_COMERCIAL_NAME);
    }

    @Test
    @Transactional
    public void createProductOfferingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOfferingRepository.findAll().size();

        // Create the ProductOffering with an existing ID
        productOffering.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOfferingMockMvc.perform(post("/api/product-offerings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOffering)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOffering in the database
        List<ProductOffering> productOfferingList = productOfferingRepository.findAll();
        assertThat(productOfferingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProductOfferings() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList
        restProductOfferingMockMvc.perform(get("/api/product-offerings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOffering.getId().intValue())))
            .andExpect(jsonPath("$.[*].poId").value(hasItem(DEFAULT_PO_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comercialName").value(hasItem(DEFAULT_COMERCIAL_NAME.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProductOfferingsWithEagerRelationshipsIsEnabled() throws Exception {
        ProductOfferingResource productOfferingResource = new ProductOfferingResource(productOfferingServiceMock, productOfferingQueryService);
        when(productOfferingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProductOfferingMockMvc = MockMvcBuilders.standaloneSetup(productOfferingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductOfferingMockMvc.perform(get("/api/product-offerings?eagerload=true"))
        .andExpect(status().isOk());

        verify(productOfferingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProductOfferingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProductOfferingResource productOfferingResource = new ProductOfferingResource(productOfferingServiceMock, productOfferingQueryService);
            when(productOfferingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProductOfferingMockMvc = MockMvcBuilders.standaloneSetup(productOfferingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductOfferingMockMvc.perform(get("/api/product-offerings?eagerload=true"))
        .andExpect(status().isOk());

            verify(productOfferingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProductOffering() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get the productOffering
        restProductOfferingMockMvc.perform(get("/api/product-offerings/{id}", productOffering.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productOffering.getId().intValue()))
            .andExpect(jsonPath("$.poId").value(DEFAULT_PO_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.comercialName").value(DEFAULT_COMERCIAL_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByPoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList where poId equals to DEFAULT_PO_ID
        defaultProductOfferingShouldBeFound("poId.equals=" + DEFAULT_PO_ID);

        // Get all the productOfferingList where poId equals to UPDATED_PO_ID
        defaultProductOfferingShouldNotBeFound("poId.equals=" + UPDATED_PO_ID);
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByPoIdIsInShouldWork() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList where poId in DEFAULT_PO_ID or UPDATED_PO_ID
        defaultProductOfferingShouldBeFound("poId.in=" + DEFAULT_PO_ID + "," + UPDATED_PO_ID);

        // Get all the productOfferingList where poId equals to UPDATED_PO_ID
        defaultProductOfferingShouldNotBeFound("poId.in=" + UPDATED_PO_ID);
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByPoIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList where poId is not null
        defaultProductOfferingShouldBeFound("poId.specified=true");

        // Get all the productOfferingList where poId is null
        defaultProductOfferingShouldNotBeFound("poId.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList where name equals to DEFAULT_NAME
        defaultProductOfferingShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productOfferingList where name equals to UPDATED_NAME
        defaultProductOfferingShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductOfferingShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productOfferingList where name equals to UPDATED_NAME
        defaultProductOfferingShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList where name is not null
        defaultProductOfferingShouldBeFound("name.specified=true");

        // Get all the productOfferingList where name is null
        defaultProductOfferingShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByComercialNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList where comercialName equals to DEFAULT_COMERCIAL_NAME
        defaultProductOfferingShouldBeFound("comercialName.equals=" + DEFAULT_COMERCIAL_NAME);

        // Get all the productOfferingList where comercialName equals to UPDATED_COMERCIAL_NAME
        defaultProductOfferingShouldNotBeFound("comercialName.equals=" + UPDATED_COMERCIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByComercialNameIsInShouldWork() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList where comercialName in DEFAULT_COMERCIAL_NAME or UPDATED_COMERCIAL_NAME
        defaultProductOfferingShouldBeFound("comercialName.in=" + DEFAULT_COMERCIAL_NAME + "," + UPDATED_COMERCIAL_NAME);

        // Get all the productOfferingList where comercialName equals to UPDATED_COMERCIAL_NAME
        defaultProductOfferingShouldNotBeFound("comercialName.in=" + UPDATED_COMERCIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByComercialNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOfferingRepository.saveAndFlush(productOffering);

        // Get all the productOfferingList where comercialName is not null
        defaultProductOfferingShouldBeFound("comercialName.specified=true");

        // Get all the productOfferingList where comercialName is null
        defaultProductOfferingShouldNotBeFound("comercialName.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductOfferingsByPoPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        PoPrice poPrice = PoPriceResourceIntTest.createEntity(em);
        em.persist(poPrice);
        em.flush();
        productOffering.setPoPrice(poPrice);
        productOfferingRepository.saveAndFlush(productOffering);
        Long poPriceId = poPrice.getId();

        // Get all the productOfferingList where poPrice equals to poPriceId
        defaultProductOfferingShouldBeFound("poPriceId.equals=" + poPriceId);

        // Get all the productOfferingList where poPrice equals to poPriceId + 1
        defaultProductOfferingShouldNotBeFound("poPriceId.equals=" + (poPriceId + 1));
    }


    @Test
    @Transactional
    public void getAllProductOfferingsByBucoSheetIsEqualToSomething() throws Exception {
        // Initialize the database
        BucoSheet bucoSheet = BucoSheetResourceIntTest.createEntity(em);
        em.persist(bucoSheet);
        em.flush();
        productOffering.setBucoSheet(bucoSheet);
        productOfferingRepository.saveAndFlush(productOffering);
        Long bucoSheetId = bucoSheet.getId();

        // Get all the productOfferingList where bucoSheet equals to bucoSheetId
        defaultProductOfferingShouldBeFound("bucoSheetId.equals=" + bucoSheetId);

        // Get all the productOfferingList where bucoSheet equals to bucoSheetId + 1
        defaultProductOfferingShouldNotBeFound("bucoSheetId.equals=" + (bucoSheetId + 1));
    }


    @Test
    @Transactional
    public void getAllProductOfferingsByRulesIsEqualToSomething() throws Exception {
        // Initialize the database
        Rule rules = RuleResourceIntTest.createEntity(em);
        em.persist(rules);
        em.flush();
        productOffering.addRules(rules);
        productOfferingRepository.saveAndFlush(productOffering);
        Long rulesId = rules.getId();

        // Get all the productOfferingList where rules equals to rulesId
        defaultProductOfferingShouldBeFound("rulesId.equals=" + rulesId);

        // Get all the productOfferingList where rules equals to rulesId + 1
        defaultProductOfferingShouldNotBeFound("rulesId.equals=" + (rulesId + 1));
    }


    @Test
    @Transactional
    public void getAllProductOfferingsByResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        Resource resource = ResourceResourceIntTest.createEntity(em);
        em.persist(resource);
        em.flush();
        productOffering.addResource(resource);
        productOfferingRepository.saveAndFlush(productOffering);
        Long resourceId = resource.getId();

        // Get all the productOfferingList where resource equals to resourceId
        defaultProductOfferingShouldBeFound("resourceId.equals=" + resourceId);

        // Get all the productOfferingList where resource equals to resourceId + 1
        defaultProductOfferingShouldNotBeFound("resourceId.equals=" + (resourceId + 1));
    }


    @Test
    @Transactional
    public void getAllProductOfferingsByNetworkResourcesIsEqualToSomething() throws Exception {
        // Initialize the database
        NetworkResource networkResources = NetworkResourceResourceIntTest.createEntity(em);
        em.persist(networkResources);
        em.flush();
        productOffering.addNetworkResources(networkResources);
        productOfferingRepository.saveAndFlush(productOffering);
        Long networkResourcesId = networkResources.getId();

        // Get all the productOfferingList where networkResources equals to networkResourcesId
        defaultProductOfferingShouldBeFound("networkResourcesId.equals=" + networkResourcesId);

        // Get all the productOfferingList where networkResources equals to networkResourcesId + 1
        defaultProductOfferingShouldNotBeFound("networkResourcesId.equals=" + (networkResourcesId + 1));
    }


    @Test
    @Transactional
    public void getAllProductOfferingsByServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        Service service = ServiceResourceIntTest.createEntity(em);
        em.persist(service);
        em.flush();
        productOffering.addService(service);
        productOfferingRepository.saveAndFlush(productOffering);
        Long serviceId = service.getId();

        // Get all the productOfferingList where service equals to serviceId
        defaultProductOfferingShouldBeFound("serviceId.equals=" + serviceId);

        // Get all the productOfferingList where service equals to serviceId + 1
        defaultProductOfferingShouldNotBeFound("serviceId.equals=" + (serviceId + 1));
    }


    @Test
    @Transactional
    public void getAllProductOfferingsByCfssPopIsEqualToSomething() throws Exception {
        // Initialize the database
        CfssPop cfssPop = CfssPopResourceIntTest.createEntity(em);
        em.persist(cfssPop);
        em.flush();
        productOffering.addCfssPop(cfssPop);
        productOfferingRepository.saveAndFlush(productOffering);
        Long cfssPopId = cfssPop.getId();

        // Get all the productOfferingList where cfssPop equals to cfssPopId
        defaultProductOfferingShouldBeFound("cfssPopId.equals=" + cfssPopId);

        // Get all the productOfferingList where cfssPop equals to cfssPopId + 1
        defaultProductOfferingShouldNotBeFound("cfssPopId.equals=" + (cfssPopId + 1));
    }


    @Test
    @Transactional
    public void getAllProductOfferingsByFreeUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        FreeUnit freeUnit = FreeUnitResourceIntTest.createEntity(em);
        em.persist(freeUnit);
        em.flush();
        productOffering.addFreeUnit(freeUnit);
        productOfferingRepository.saveAndFlush(productOffering);
        Long freeUnitId = freeUnit.getId();

        // Get all the productOfferingList where freeUnit equals to freeUnitId
        defaultProductOfferingShouldBeFound("freeUnitId.equals=" + freeUnitId);

        // Get all the productOfferingList where freeUnit equals to freeUnitId + 1
        defaultProductOfferingShouldNotBeFound("freeUnitId.equals=" + (freeUnitId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProductOfferingShouldBeFound(String filter) throws Exception {
        restProductOfferingMockMvc.perform(get("/api/product-offerings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOffering.getId().intValue())))
            .andExpect(jsonPath("$.[*].poId").value(hasItem(DEFAULT_PO_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].comercialName").value(hasItem(DEFAULT_COMERCIAL_NAME.toString())));

        // Check, that the count call also returns 1
        restProductOfferingMockMvc.perform(get("/api/product-offerings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProductOfferingShouldNotBeFound(String filter) throws Exception {
        restProductOfferingMockMvc.perform(get("/api/product-offerings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOfferingMockMvc.perform(get("/api/product-offerings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductOffering() throws Exception {
        // Get the productOffering
        restProductOfferingMockMvc.perform(get("/api/product-offerings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOffering() throws Exception {
        // Initialize the database
        productOfferingService.save(productOffering);

        int databaseSizeBeforeUpdate = productOfferingRepository.findAll().size();

        // Update the productOffering
        ProductOffering updatedProductOffering = productOfferingRepository.findById(productOffering.getId()).get();
        // Disconnect from session so that the updates on updatedProductOffering are not directly saved in db
        em.detach(updatedProductOffering);
        updatedProductOffering
            .poId(UPDATED_PO_ID)
            .name(UPDATED_NAME)
            .comercialName(UPDATED_COMERCIAL_NAME);

        restProductOfferingMockMvc.perform(put("/api/product-offerings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductOffering)))
            .andExpect(status().isOk());

        // Validate the ProductOffering in the database
        List<ProductOffering> productOfferingList = productOfferingRepository.findAll();
        assertThat(productOfferingList).hasSize(databaseSizeBeforeUpdate);
        ProductOffering testProductOffering = productOfferingList.get(productOfferingList.size() - 1);
        assertThat(testProductOffering.getPoId()).isEqualTo(UPDATED_PO_ID);
        assertThat(testProductOffering.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOffering.getComercialName()).isEqualTo(UPDATED_COMERCIAL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOffering() throws Exception {
        int databaseSizeBeforeUpdate = productOfferingRepository.findAll().size();

        // Create the ProductOffering

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOfferingMockMvc.perform(put("/api/product-offerings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOffering)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOffering in the database
        List<ProductOffering> productOfferingList = productOfferingRepository.findAll();
        assertThat(productOfferingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductOffering() throws Exception {
        // Initialize the database
        productOfferingService.save(productOffering);

        int databaseSizeBeforeDelete = productOfferingRepository.findAll().size();

        // Get the productOffering
        restProductOfferingMockMvc.perform(delete("/api/product-offerings/{id}", productOffering.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProductOffering> productOfferingList = productOfferingRepository.findAll();
        assertThat(productOfferingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOffering.class);
        ProductOffering productOffering1 = new ProductOffering();
        productOffering1.setId(1L);
        ProductOffering productOffering2 = new ProductOffering();
        productOffering2.setId(productOffering1.getId());
        assertThat(productOffering1).isEqualTo(productOffering2);
        productOffering2.setId(2L);
        assertThat(productOffering1).isNotEqualTo(productOffering2);
        productOffering1.setId(null);
        assertThat(productOffering1).isNotEqualTo(productOffering2);
    }
}

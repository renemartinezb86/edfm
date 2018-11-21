package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.EdfmApp;

import io.github.jhipster.application.domain.PoPrice;
import io.github.jhipster.application.domain.ProductOffering;
import io.github.jhipster.application.repository.PoPriceRepository;
import io.github.jhipster.application.service.PoPriceService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;
import io.github.jhipster.application.service.dto.PoPriceCriteria;
import io.github.jhipster.application.service.PoPriceQueryService;

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

import io.github.jhipster.application.domain.enumeration.PoPriceType;
import io.github.jhipster.application.domain.enumeration.PaymentType;
/**
 * Test class for the PoPriceResource REST controller.
 *
 * @see PoPriceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EdfmApp.class)
public class PoPriceResourceIntTest {

    private static final PoPriceType DEFAULT_PO_PRICE_TYPE = PoPriceType.Recurring;
    private static final PoPriceType UPDATED_PO_PRICE_TYPE = PoPriceType.OneShot;

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.Cash;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.Bill;

    private static final Boolean DEFAULT_SHOW_IN_BILL = false;
    private static final Boolean UPDATED_SHOW_IN_BILL = true;

    private static final Boolean DEFAULT_PAY_IN_ADVANCE = false;
    private static final Boolean UPDATED_PAY_IN_ADVANCE = true;

    private static final Boolean DEFAULT_BILL_ON_SUSPENSION = false;
    private static final Boolean UPDATED_BILL_ON_SUSPENSION = true;

    private static final Boolean DEFAULT_MULTI_DISCOUNT = false;
    private static final Boolean UPDATED_MULTI_DISCOUNT = true;

    @Autowired
    private PoPriceRepository poPriceRepository;

    @Autowired
    private PoPriceService poPriceService;

    @Autowired
    private PoPriceQueryService poPriceQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPoPriceMockMvc;

    private PoPrice poPrice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoPriceResource poPriceResource = new PoPriceResource(poPriceService, poPriceQueryService);
        this.restPoPriceMockMvc = MockMvcBuilders.standaloneSetup(poPriceResource)
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
    public static PoPrice createEntity(EntityManager em) {
        PoPrice poPrice = new PoPrice()
            .poPriceType(DEFAULT_PO_PRICE_TYPE)
            .amount(DEFAULT_AMOUNT)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .showInBill(DEFAULT_SHOW_IN_BILL)
            .payInAdvance(DEFAULT_PAY_IN_ADVANCE)
            .billOnSuspension(DEFAULT_BILL_ON_SUSPENSION)
            .multiDiscount(DEFAULT_MULTI_DISCOUNT);
        return poPrice;
    }

    @Before
    public void initTest() {
        poPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoPrice() throws Exception {
        int databaseSizeBeforeCreate = poPriceRepository.findAll().size();

        // Create the PoPrice
        restPoPriceMockMvc.perform(post("/api/po-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poPrice)))
            .andExpect(status().isCreated());

        // Validate the PoPrice in the database
        List<PoPrice> poPriceList = poPriceRepository.findAll();
        assertThat(poPriceList).hasSize(databaseSizeBeforeCreate + 1);
        PoPrice testPoPrice = poPriceList.get(poPriceList.size() - 1);
        assertThat(testPoPrice.getPoPriceType()).isEqualTo(DEFAULT_PO_PRICE_TYPE);
        assertThat(testPoPrice.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPoPrice.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testPoPrice.isShowInBill()).isEqualTo(DEFAULT_SHOW_IN_BILL);
        assertThat(testPoPrice.isPayInAdvance()).isEqualTo(DEFAULT_PAY_IN_ADVANCE);
        assertThat(testPoPrice.isBillOnSuspension()).isEqualTo(DEFAULT_BILL_ON_SUSPENSION);
        assertThat(testPoPrice.isMultiDiscount()).isEqualTo(DEFAULT_MULTI_DISCOUNT);
    }

    @Test
    @Transactional
    public void createPoPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poPriceRepository.findAll().size();

        // Create the PoPrice with an existing ID
        poPrice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoPriceMockMvc.perform(post("/api/po-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poPrice)))
            .andExpect(status().isBadRequest());

        // Validate the PoPrice in the database
        List<PoPrice> poPriceList = poPriceRepository.findAll();
        assertThat(poPriceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPoPrices() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList
        restPoPriceMockMvc.perform(get("/api/po-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].poPriceType").value(hasItem(DEFAULT_PO_PRICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].showInBill").value(hasItem(DEFAULT_SHOW_IN_BILL.booleanValue())))
            .andExpect(jsonPath("$.[*].payInAdvance").value(hasItem(DEFAULT_PAY_IN_ADVANCE.booleanValue())))
            .andExpect(jsonPath("$.[*].billOnSuspension").value(hasItem(DEFAULT_BILL_ON_SUSPENSION.booleanValue())))
            .andExpect(jsonPath("$.[*].multiDiscount").value(hasItem(DEFAULT_MULTI_DISCOUNT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPoPrice() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get the poPrice
        restPoPriceMockMvc.perform(get("/api/po-prices/{id}", poPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poPrice.getId().intValue()))
            .andExpect(jsonPath("$.poPriceType").value(DEFAULT_PO_PRICE_TYPE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.showInBill").value(DEFAULT_SHOW_IN_BILL.booleanValue()))
            .andExpect(jsonPath("$.payInAdvance").value(DEFAULT_PAY_IN_ADVANCE.booleanValue()))
            .andExpect(jsonPath("$.billOnSuspension").value(DEFAULT_BILL_ON_SUSPENSION.booleanValue()))
            .andExpect(jsonPath("$.multiDiscount").value(DEFAULT_MULTI_DISCOUNT.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllPoPricesByPoPriceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where poPriceType equals to DEFAULT_PO_PRICE_TYPE
        defaultPoPriceShouldBeFound("poPriceType.equals=" + DEFAULT_PO_PRICE_TYPE);

        // Get all the poPriceList where poPriceType equals to UPDATED_PO_PRICE_TYPE
        defaultPoPriceShouldNotBeFound("poPriceType.equals=" + UPDATED_PO_PRICE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPoPricesByPoPriceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where poPriceType in DEFAULT_PO_PRICE_TYPE or UPDATED_PO_PRICE_TYPE
        defaultPoPriceShouldBeFound("poPriceType.in=" + DEFAULT_PO_PRICE_TYPE + "," + UPDATED_PO_PRICE_TYPE);

        // Get all the poPriceList where poPriceType equals to UPDATED_PO_PRICE_TYPE
        defaultPoPriceShouldNotBeFound("poPriceType.in=" + UPDATED_PO_PRICE_TYPE);
    }

    @Test
    @Transactional
    public void getAllPoPricesByPoPriceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where poPriceType is not null
        defaultPoPriceShouldBeFound("poPriceType.specified=true");

        // Get all the poPriceList where poPriceType is null
        defaultPoPriceShouldNotBeFound("poPriceType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoPricesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where amount equals to DEFAULT_AMOUNT
        defaultPoPriceShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the poPriceList where amount equals to UPDATED_AMOUNT
        defaultPoPriceShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPoPricesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultPoPriceShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the poPriceList where amount equals to UPDATED_AMOUNT
        defaultPoPriceShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPoPricesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where amount is not null
        defaultPoPriceShouldBeFound("amount.specified=true");

        // Get all the poPriceList where amount is null
        defaultPoPriceShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoPricesByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where paymentType equals to DEFAULT_PAYMENT_TYPE
        defaultPoPriceShouldBeFound("paymentType.equals=" + DEFAULT_PAYMENT_TYPE);

        // Get all the poPriceList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultPoPriceShouldNotBeFound("paymentType.equals=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllPoPricesByPaymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where paymentType in DEFAULT_PAYMENT_TYPE or UPDATED_PAYMENT_TYPE
        defaultPoPriceShouldBeFound("paymentType.in=" + DEFAULT_PAYMENT_TYPE + "," + UPDATED_PAYMENT_TYPE);

        // Get all the poPriceList where paymentType equals to UPDATED_PAYMENT_TYPE
        defaultPoPriceShouldNotBeFound("paymentType.in=" + UPDATED_PAYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllPoPricesByPaymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where paymentType is not null
        defaultPoPriceShouldBeFound("paymentType.specified=true");

        // Get all the poPriceList where paymentType is null
        defaultPoPriceShouldNotBeFound("paymentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoPricesByShowInBillIsEqualToSomething() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where showInBill equals to DEFAULT_SHOW_IN_BILL
        defaultPoPriceShouldBeFound("showInBill.equals=" + DEFAULT_SHOW_IN_BILL);

        // Get all the poPriceList where showInBill equals to UPDATED_SHOW_IN_BILL
        defaultPoPriceShouldNotBeFound("showInBill.equals=" + UPDATED_SHOW_IN_BILL);
    }

    @Test
    @Transactional
    public void getAllPoPricesByShowInBillIsInShouldWork() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where showInBill in DEFAULT_SHOW_IN_BILL or UPDATED_SHOW_IN_BILL
        defaultPoPriceShouldBeFound("showInBill.in=" + DEFAULT_SHOW_IN_BILL + "," + UPDATED_SHOW_IN_BILL);

        // Get all the poPriceList where showInBill equals to UPDATED_SHOW_IN_BILL
        defaultPoPriceShouldNotBeFound("showInBill.in=" + UPDATED_SHOW_IN_BILL);
    }

    @Test
    @Transactional
    public void getAllPoPricesByShowInBillIsNullOrNotNull() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where showInBill is not null
        defaultPoPriceShouldBeFound("showInBill.specified=true");

        // Get all the poPriceList where showInBill is null
        defaultPoPriceShouldNotBeFound("showInBill.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoPricesByPayInAdvanceIsEqualToSomething() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where payInAdvance equals to DEFAULT_PAY_IN_ADVANCE
        defaultPoPriceShouldBeFound("payInAdvance.equals=" + DEFAULT_PAY_IN_ADVANCE);

        // Get all the poPriceList where payInAdvance equals to UPDATED_PAY_IN_ADVANCE
        defaultPoPriceShouldNotBeFound("payInAdvance.equals=" + UPDATED_PAY_IN_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllPoPricesByPayInAdvanceIsInShouldWork() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where payInAdvance in DEFAULT_PAY_IN_ADVANCE or UPDATED_PAY_IN_ADVANCE
        defaultPoPriceShouldBeFound("payInAdvance.in=" + DEFAULT_PAY_IN_ADVANCE + "," + UPDATED_PAY_IN_ADVANCE);

        // Get all the poPriceList where payInAdvance equals to UPDATED_PAY_IN_ADVANCE
        defaultPoPriceShouldNotBeFound("payInAdvance.in=" + UPDATED_PAY_IN_ADVANCE);
    }

    @Test
    @Transactional
    public void getAllPoPricesByPayInAdvanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where payInAdvance is not null
        defaultPoPriceShouldBeFound("payInAdvance.specified=true");

        // Get all the poPriceList where payInAdvance is null
        defaultPoPriceShouldNotBeFound("payInAdvance.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoPricesByBillOnSuspensionIsEqualToSomething() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where billOnSuspension equals to DEFAULT_BILL_ON_SUSPENSION
        defaultPoPriceShouldBeFound("billOnSuspension.equals=" + DEFAULT_BILL_ON_SUSPENSION);

        // Get all the poPriceList where billOnSuspension equals to UPDATED_BILL_ON_SUSPENSION
        defaultPoPriceShouldNotBeFound("billOnSuspension.equals=" + UPDATED_BILL_ON_SUSPENSION);
    }

    @Test
    @Transactional
    public void getAllPoPricesByBillOnSuspensionIsInShouldWork() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where billOnSuspension in DEFAULT_BILL_ON_SUSPENSION or UPDATED_BILL_ON_SUSPENSION
        defaultPoPriceShouldBeFound("billOnSuspension.in=" + DEFAULT_BILL_ON_SUSPENSION + "," + UPDATED_BILL_ON_SUSPENSION);

        // Get all the poPriceList where billOnSuspension equals to UPDATED_BILL_ON_SUSPENSION
        defaultPoPriceShouldNotBeFound("billOnSuspension.in=" + UPDATED_BILL_ON_SUSPENSION);
    }

    @Test
    @Transactional
    public void getAllPoPricesByBillOnSuspensionIsNullOrNotNull() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where billOnSuspension is not null
        defaultPoPriceShouldBeFound("billOnSuspension.specified=true");

        // Get all the poPriceList where billOnSuspension is null
        defaultPoPriceShouldNotBeFound("billOnSuspension.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoPricesByMultiDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where multiDiscount equals to DEFAULT_MULTI_DISCOUNT
        defaultPoPriceShouldBeFound("multiDiscount.equals=" + DEFAULT_MULTI_DISCOUNT);

        // Get all the poPriceList where multiDiscount equals to UPDATED_MULTI_DISCOUNT
        defaultPoPriceShouldNotBeFound("multiDiscount.equals=" + UPDATED_MULTI_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllPoPricesByMultiDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where multiDiscount in DEFAULT_MULTI_DISCOUNT or UPDATED_MULTI_DISCOUNT
        defaultPoPriceShouldBeFound("multiDiscount.in=" + DEFAULT_MULTI_DISCOUNT + "," + UPDATED_MULTI_DISCOUNT);

        // Get all the poPriceList where multiDiscount equals to UPDATED_MULTI_DISCOUNT
        defaultPoPriceShouldNotBeFound("multiDiscount.in=" + UPDATED_MULTI_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllPoPricesByMultiDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        poPriceRepository.saveAndFlush(poPrice);

        // Get all the poPriceList where multiDiscount is not null
        defaultPoPriceShouldBeFound("multiDiscount.specified=true");

        // Get all the poPriceList where multiDiscount is null
        defaultPoPriceShouldNotBeFound("multiDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoPricesByProductOfferingIsEqualToSomething() throws Exception {
        // Initialize the database
        ProductOffering productOffering = ProductOfferingResourceIntTest.createEntity(em);
        em.persist(productOffering);
        em.flush();
        poPrice.setProductOffering(productOffering);
        productOffering.setPoPrice(poPrice);
        poPriceRepository.saveAndFlush(poPrice);
        Long productOfferingId = productOffering.getId();

        // Get all the poPriceList where productOffering equals to productOfferingId
        defaultPoPriceShouldBeFound("productOfferingId.equals=" + productOfferingId);

        // Get all the poPriceList where productOffering equals to productOfferingId + 1
        defaultPoPriceShouldNotBeFound("productOfferingId.equals=" + (productOfferingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPoPriceShouldBeFound(String filter) throws Exception {
        restPoPriceMockMvc.perform(get("/api/po-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].poPriceType").value(hasItem(DEFAULT_PO_PRICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].showInBill").value(hasItem(DEFAULT_SHOW_IN_BILL.booleanValue())))
            .andExpect(jsonPath("$.[*].payInAdvance").value(hasItem(DEFAULT_PAY_IN_ADVANCE.booleanValue())))
            .andExpect(jsonPath("$.[*].billOnSuspension").value(hasItem(DEFAULT_BILL_ON_SUSPENSION.booleanValue())))
            .andExpect(jsonPath("$.[*].multiDiscount").value(hasItem(DEFAULT_MULTI_DISCOUNT.booleanValue())));

        // Check, that the count call also returns 1
        restPoPriceMockMvc.perform(get("/api/po-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPoPriceShouldNotBeFound(String filter) throws Exception {
        restPoPriceMockMvc.perform(get("/api/po-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPoPriceMockMvc.perform(get("/api/po-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPoPrice() throws Exception {
        // Get the poPrice
        restPoPriceMockMvc.perform(get("/api/po-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoPrice() throws Exception {
        // Initialize the database
        poPriceService.save(poPrice);

        int databaseSizeBeforeUpdate = poPriceRepository.findAll().size();

        // Update the poPrice
        PoPrice updatedPoPrice = poPriceRepository.findById(poPrice.getId()).get();
        // Disconnect from session so that the updates on updatedPoPrice are not directly saved in db
        em.detach(updatedPoPrice);
        updatedPoPrice
            .poPriceType(UPDATED_PO_PRICE_TYPE)
            .amount(UPDATED_AMOUNT)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .showInBill(UPDATED_SHOW_IN_BILL)
            .payInAdvance(UPDATED_PAY_IN_ADVANCE)
            .billOnSuspension(UPDATED_BILL_ON_SUSPENSION)
            .multiDiscount(UPDATED_MULTI_DISCOUNT);

        restPoPriceMockMvc.perform(put("/api/po-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoPrice)))
            .andExpect(status().isOk());

        // Validate the PoPrice in the database
        List<PoPrice> poPriceList = poPriceRepository.findAll();
        assertThat(poPriceList).hasSize(databaseSizeBeforeUpdate);
        PoPrice testPoPrice = poPriceList.get(poPriceList.size() - 1);
        assertThat(testPoPrice.getPoPriceType()).isEqualTo(UPDATED_PO_PRICE_TYPE);
        assertThat(testPoPrice.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPoPrice.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testPoPrice.isShowInBill()).isEqualTo(UPDATED_SHOW_IN_BILL);
        assertThat(testPoPrice.isPayInAdvance()).isEqualTo(UPDATED_PAY_IN_ADVANCE);
        assertThat(testPoPrice.isBillOnSuspension()).isEqualTo(UPDATED_BILL_ON_SUSPENSION);
        assertThat(testPoPrice.isMultiDiscount()).isEqualTo(UPDATED_MULTI_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPoPrice() throws Exception {
        int databaseSizeBeforeUpdate = poPriceRepository.findAll().size();

        // Create the PoPrice

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoPriceMockMvc.perform(put("/api/po-prices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poPrice)))
            .andExpect(status().isBadRequest());

        // Validate the PoPrice in the database
        List<PoPrice> poPriceList = poPriceRepository.findAll();
        assertThat(poPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoPrice() throws Exception {
        // Initialize the database
        poPriceService.save(poPrice);

        int databaseSizeBeforeDelete = poPriceRepository.findAll().size();

        // Get the poPrice
        restPoPriceMockMvc.perform(delete("/api/po-prices/{id}", poPrice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PoPrice> poPriceList = poPriceRepository.findAll();
        assertThat(poPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PoPrice.class);
        PoPrice poPrice1 = new PoPrice();
        poPrice1.setId(1L);
        PoPrice poPrice2 = new PoPrice();
        poPrice2.setId(poPrice1.getId());
        assertThat(poPrice1).isEqualTo(poPrice2);
        poPrice2.setId(2L);
        assertThat(poPrice1).isNotEqualTo(poPrice2);
        poPrice1.setId(null);
        assertThat(poPrice1).isNotEqualTo(poPrice2);
    }
}

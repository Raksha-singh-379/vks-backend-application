package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.domain.GRInterestDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyLoanProduct;
import com.vgtech.vks.app.repository.SocietyLoanProductRepository;
import com.vgtech.vks.app.service.criteria.SocietyLoanProductCriteria;
import com.vgtech.vks.app.service.dto.SocietyLoanProductDTO;
import com.vgtech.vks.app.service.mapper.SocietyLoanProductMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SocietyLoanProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocietyLoanProductResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACC_HEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACC_HEAD_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_BORROWING_INTEREST_RATE = 1D;
    private static final Double UPDATED_BORROWING_INTEREST_RATE = 2D;
    private static final Double SMALLER_BORROWING_INTEREST_RATE = 1D - 1D;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;
    private static final Integer SMALLER_DURATION = 1 - 1;

    private static final Double DEFAULT_INTEREST_RATE = 1D;
    private static final Double UPDATED_INTEREST_RATE = 2D;
    private static final Double SMALLER_INTEREST_RATE = 1D - 1D;

    private static final Instant DEFAULT_LAST_DATE_OF_REPAYMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_DATE_OF_REPAYMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOAN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_MAX_LOAN_AMT = 1D;
    private static final Double UPDATED_MAX_LOAN_AMT = 2D;
    private static final Double SMALLER_MAX_LOAN_AMT = 1D - 1D;

    private static final Integer DEFAULT_NO_OF_DISBURSEMENT = 1;
    private static final Integer UPDATED_NO_OF_DISBURSEMENT = 2;
    private static final Integer SMALLER_NO_OF_DISBURSEMENT = 1 - 1;

    private static final Integer DEFAULT_NO_OF_INSTALLMENT = 1;
    private static final Integer UPDATED_NO_OF_INSTALLMENT = 2;
    private static final Integer SMALLER_NO_OF_INSTALLMENT = 1 - 1;

    private static final String DEFAULT_PARENT_ACC_HEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ACC_HEAD_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ACC_HEAD_ID = 1L;
    private static final Long UPDATED_PARENT_ACC_HEAD_ID = 2L;
    private static final Long SMALLER_PARENT_ACC_HEAD_ID = 1L - 1L;

    private static final Double DEFAULT_PENALTY_INTEREST = 1D;
    private static final Double UPDATED_PENALTY_INTEREST = 2D;
    private static final Double SMALLER_PENALTY_INTEREST = 1D - 1D;

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESOLUTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESOLUTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RESOLUTION_NO = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Double DEFAULT_SURCHARGE = 1D;
    private static final Double UPDATED_SURCHARGE = 2D;
    private static final Double SMALLER_SURCHARGE = 1D - 1D;

    private static final Long DEFAULT_UNIT_SIZE = 1L;
    private static final Long UPDATED_UNIT_SIZE = 2L;
    private static final Long SMALLER_UNIT_SIZE = 1L - 1L;

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVATE = false;
    private static final Boolean UPDATED_IS_ACTIVATE = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/society-loan-products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocietyLoanProductRepository societyLoanProductRepository;

    @Autowired
    private SocietyLoanProductMapper societyLoanProductMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocietyLoanProductMockMvc;

    private SocietyLoanProduct societyLoanProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyLoanProduct createEntity(EntityManager em) {
        SocietyLoanProduct societyLoanProduct = new SocietyLoanProduct()
            .productName(DEFAULT_PRODUCT_NAME)
            .accHeadCode(DEFAULT_ACC_HEAD_CODE)
            .borrowingInterestRate(DEFAULT_BORROWING_INTEREST_RATE)
            .duration(DEFAULT_DURATION)
            .interestRate(DEFAULT_INTEREST_RATE)
            .lastDateOfRepayment(DEFAULT_LAST_DATE_OF_REPAYMENT)
            .loanNumber(DEFAULT_LOAN_NUMBER)
            .maxLoanAmt(DEFAULT_MAX_LOAN_AMT)
            .noOfDisbursement(DEFAULT_NO_OF_DISBURSEMENT)
            .noOfInstallment(DEFAULT_NO_OF_INSTALLMENT)
            .parentAccHeadCode(DEFAULT_PARENT_ACC_HEAD_CODE)
            .parentAccHeadId(DEFAULT_PARENT_ACC_HEAD_ID)
            .penaltyInterest(DEFAULT_PENALTY_INTEREST)
            .productType(DEFAULT_PRODUCT_TYPE)
            .resolutionDate(DEFAULT_RESOLUTION_DATE)
            .resolutionNo(DEFAULT_RESOLUTION_NO)
            .status(DEFAULT_STATUS)
            .surcharge(DEFAULT_SURCHARGE)
            .unitSize(DEFAULT_UNIT_SIZE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .isActivate(DEFAULT_IS_ACTIVATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return societyLoanProduct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyLoanProduct createUpdatedEntity(EntityManager em) {
        SocietyLoanProduct societyLoanProduct = new SocietyLoanProduct()
            .productName(UPDATED_PRODUCT_NAME)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .borrowingInterestRate(UPDATED_BORROWING_INTEREST_RATE)
            .duration(UPDATED_DURATION)
            .interestRate(UPDATED_INTEREST_RATE)
            .lastDateOfRepayment(UPDATED_LAST_DATE_OF_REPAYMENT)
            .loanNumber(UPDATED_LOAN_NUMBER)
            .maxLoanAmt(UPDATED_MAX_LOAN_AMT)
            .noOfDisbursement(UPDATED_NO_OF_DISBURSEMENT)
            .noOfInstallment(UPDATED_NO_OF_INSTALLMENT)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .parentAccHeadId(UPDATED_PARENT_ACC_HEAD_ID)
            .penaltyInterest(UPDATED_PENALTY_INTEREST)
            .productType(UPDATED_PRODUCT_TYPE)
            .resolutionDate(UPDATED_RESOLUTION_DATE)
            .resolutionNo(UPDATED_RESOLUTION_NO)
            .status(UPDATED_STATUS)
            .surcharge(UPDATED_SURCHARGE)
            .unitSize(UPDATED_UNIT_SIZE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .isActivate(UPDATED_IS_ACTIVATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return societyLoanProduct;
    }

    @BeforeEach
    public void initTest() {
        societyLoanProduct = createEntity(em);
    }

    @Test
    @Transactional
    void createSocietyLoanProduct() throws Exception {
        int databaseSizeBeforeCreate = societyLoanProductRepository.findAll().size();
        // Create the SocietyLoanProduct
        SocietyLoanProductDTO societyLoanProductDTO = societyLoanProductMapper.toDto(societyLoanProduct);
        restSocietyLoanProductMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyLoanProductDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeCreate + 1);
        SocietyLoanProduct testSocietyLoanProduct = societyLoanProductList.get(societyLoanProductList.size() - 1);
        assertThat(testSocietyLoanProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testSocietyLoanProduct.getAccHeadCode()).isEqualTo(DEFAULT_ACC_HEAD_CODE);
        assertThat(testSocietyLoanProduct.getBorrowingInterestRate()).isEqualTo(DEFAULT_BORROWING_INTEREST_RATE);
        assertThat(testSocietyLoanProduct.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testSocietyLoanProduct.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testSocietyLoanProduct.getLastDateOfRepayment()).isEqualTo(DEFAULT_LAST_DATE_OF_REPAYMENT);
        assertThat(testSocietyLoanProduct.getLoanNumber()).isEqualTo(DEFAULT_LOAN_NUMBER);
        assertThat(testSocietyLoanProduct.getMaxLoanAmt()).isEqualTo(DEFAULT_MAX_LOAN_AMT);
        assertThat(testSocietyLoanProduct.getNoOfDisbursement()).isEqualTo(DEFAULT_NO_OF_DISBURSEMENT);
        assertThat(testSocietyLoanProduct.getNoOfInstallment()).isEqualTo(DEFAULT_NO_OF_INSTALLMENT);
        assertThat(testSocietyLoanProduct.getParentAccHeadCode()).isEqualTo(DEFAULT_PARENT_ACC_HEAD_CODE);
        assertThat(testSocietyLoanProduct.getParentAccHeadId()).isEqualTo(DEFAULT_PARENT_ACC_HEAD_ID);
        assertThat(testSocietyLoanProduct.getPenaltyInterest()).isEqualTo(DEFAULT_PENALTY_INTEREST);
        assertThat(testSocietyLoanProduct.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testSocietyLoanProduct.getResolutionDate()).isEqualTo(DEFAULT_RESOLUTION_DATE);
        assertThat(testSocietyLoanProduct.getResolutionNo()).isEqualTo(DEFAULT_RESOLUTION_NO);
        assertThat(testSocietyLoanProduct.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSocietyLoanProduct.getSurcharge()).isEqualTo(DEFAULT_SURCHARGE);
        assertThat(testSocietyLoanProduct.getUnitSize()).isEqualTo(DEFAULT_UNIT_SIZE);
        assertThat(testSocietyLoanProduct.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testSocietyLoanProduct.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testSocietyLoanProduct.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyLoanProduct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyLoanProduct.getIsActivate()).isEqualTo(DEFAULT_IS_ACTIVATE);
        assertThat(testSocietyLoanProduct.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyLoanProduct.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyLoanProduct.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyLoanProduct.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyLoanProduct.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createSocietyLoanProductWithExistingId() throws Exception {
        // Create the SocietyLoanProduct with an existing ID
        societyLoanProduct.setId(1L);
        SocietyLoanProductDTO societyLoanProductDTO = societyLoanProductMapper.toDto(societyLoanProduct);

        int databaseSizeBeforeCreate = societyLoanProductRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocietyLoanProductMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyLoanProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProducts() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList
        restSocietyLoanProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyLoanProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].accHeadCode").value(hasItem(DEFAULT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].borrowingInterestRate").value(hasItem(DEFAULT_BORROWING_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastDateOfRepayment").value(hasItem(DEFAULT_LAST_DATE_OF_REPAYMENT.toString())))
            .andExpect(jsonPath("$.[*].loanNumber").value(hasItem(DEFAULT_LOAN_NUMBER)))
            .andExpect(jsonPath("$.[*].maxLoanAmt").value(hasItem(DEFAULT_MAX_LOAN_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfDisbursement").value(hasItem(DEFAULT_NO_OF_DISBURSEMENT)))
            .andExpect(jsonPath("$.[*].noOfInstallment").value(hasItem(DEFAULT_NO_OF_INSTALLMENT)))
            .andExpect(jsonPath("$.[*].parentAccHeadCode").value(hasItem(DEFAULT_PARENT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].parentAccHeadId").value(hasItem(DEFAULT_PARENT_ACC_HEAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].penaltyInterest").value(hasItem(DEFAULT_PENALTY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].resolutionDate").value(hasItem(DEFAULT_RESOLUTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].resolutionNo").value(hasItem(DEFAULT_RESOLUTION_NO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].surcharge").value(hasItem(DEFAULT_SURCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitSize").value(hasItem(DEFAULT_UNIT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].isActivate").value(hasItem(DEFAULT_IS_ACTIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getSocietyLoanProduct() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get the societyLoanProduct
        restSocietyLoanProductMockMvc
            .perform(get(ENTITY_API_URL_ID, societyLoanProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societyLoanProduct.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.accHeadCode").value(DEFAULT_ACC_HEAD_CODE))
            .andExpect(jsonPath("$.borrowingInterestRate").value(DEFAULT_BORROWING_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.lastDateOfRepayment").value(DEFAULT_LAST_DATE_OF_REPAYMENT.toString()))
            .andExpect(jsonPath("$.loanNumber").value(DEFAULT_LOAN_NUMBER))
            .andExpect(jsonPath("$.maxLoanAmt").value(DEFAULT_MAX_LOAN_AMT.doubleValue()))
            .andExpect(jsonPath("$.noOfDisbursement").value(DEFAULT_NO_OF_DISBURSEMENT))
            .andExpect(jsonPath("$.noOfInstallment").value(DEFAULT_NO_OF_INSTALLMENT))
            .andExpect(jsonPath("$.parentAccHeadCode").value(DEFAULT_PARENT_ACC_HEAD_CODE))
            .andExpect(jsonPath("$.parentAccHeadId").value(DEFAULT_PARENT_ACC_HEAD_ID.intValue()))
            .andExpect(jsonPath("$.penaltyInterest").value(DEFAULT_PENALTY_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE))
            .andExpect(jsonPath("$.resolutionDate").value(DEFAULT_RESOLUTION_DATE.toString()))
            .andExpect(jsonPath("$.resolutionNo").value(DEFAULT_RESOLUTION_NO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.surcharge").value(DEFAULT_SURCHARGE.doubleValue()))
            .andExpect(jsonPath("$.unitSize").value(DEFAULT_UNIT_SIZE.intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.isActivate").value(DEFAULT_IS_ACTIVATE.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getSocietyLoanProductsByIdFiltering() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        Long id = societyLoanProduct.getId();

        defaultSocietyLoanProductShouldBeFound("id.equals=" + id);
        defaultSocietyLoanProductShouldNotBeFound("id.notEquals=" + id);

        defaultSocietyLoanProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocietyLoanProductShouldNotBeFound("id.greaterThan=" + id);

        defaultSocietyLoanProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocietyLoanProductShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productName equals to DEFAULT_PRODUCT_NAME
        defaultSocietyLoanProductShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the societyLoanProductList where productName equals to UPDATED_PRODUCT_NAME
        defaultSocietyLoanProductShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultSocietyLoanProductShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the societyLoanProductList where productName equals to UPDATED_PRODUCT_NAME
        defaultSocietyLoanProductShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productName is not null
        defaultSocietyLoanProductShouldBeFound("productName.specified=true");

        // Get all the societyLoanProductList where productName is null
        defaultSocietyLoanProductShouldNotBeFound("productName.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductNameContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productName contains DEFAULT_PRODUCT_NAME
        defaultSocietyLoanProductShouldBeFound("productName.contains=" + DEFAULT_PRODUCT_NAME);

        // Get all the societyLoanProductList where productName contains UPDATED_PRODUCT_NAME
        defaultSocietyLoanProductShouldNotBeFound("productName.contains=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductNameNotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productName does not contain DEFAULT_PRODUCT_NAME
        defaultSocietyLoanProductShouldNotBeFound("productName.doesNotContain=" + DEFAULT_PRODUCT_NAME);

        // Get all the societyLoanProductList where productName does not contain UPDATED_PRODUCT_NAME
        defaultSocietyLoanProductShouldBeFound("productName.doesNotContain=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByAccHeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where accHeadCode equals to DEFAULT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldBeFound("accHeadCode.equals=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the societyLoanProductList where accHeadCode equals to UPDATED_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldNotBeFound("accHeadCode.equals=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByAccHeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where accHeadCode in DEFAULT_ACC_HEAD_CODE or UPDATED_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldBeFound("accHeadCode.in=" + DEFAULT_ACC_HEAD_CODE + "," + UPDATED_ACC_HEAD_CODE);

        // Get all the societyLoanProductList where accHeadCode equals to UPDATED_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldNotBeFound("accHeadCode.in=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByAccHeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where accHeadCode is not null
        defaultSocietyLoanProductShouldBeFound("accHeadCode.specified=true");

        // Get all the societyLoanProductList where accHeadCode is null
        defaultSocietyLoanProductShouldNotBeFound("accHeadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByAccHeadCodeContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where accHeadCode contains DEFAULT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldBeFound("accHeadCode.contains=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the societyLoanProductList where accHeadCode contains UPDATED_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldNotBeFound("accHeadCode.contains=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByAccHeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where accHeadCode does not contain DEFAULT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldNotBeFound("accHeadCode.doesNotContain=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the societyLoanProductList where accHeadCode does not contain UPDATED_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldBeFound("accHeadCode.doesNotContain=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByBorrowingInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where borrowingInterestRate equals to DEFAULT_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("borrowingInterestRate.equals=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the societyLoanProductList where borrowingInterestRate equals to UPDATED_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("borrowingInterestRate.equals=" + UPDATED_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByBorrowingInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where borrowingInterestRate in DEFAULT_BORROWING_INTEREST_RATE or UPDATED_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound(
            "borrowingInterestRate.in=" + DEFAULT_BORROWING_INTEREST_RATE + "," + UPDATED_BORROWING_INTEREST_RATE
        );

        // Get all the societyLoanProductList where borrowingInterestRate equals to UPDATED_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("borrowingInterestRate.in=" + UPDATED_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByBorrowingInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where borrowingInterestRate is not null
        defaultSocietyLoanProductShouldBeFound("borrowingInterestRate.specified=true");

        // Get all the societyLoanProductList where borrowingInterestRate is null
        defaultSocietyLoanProductShouldNotBeFound("borrowingInterestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByBorrowingInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where borrowingInterestRate is greater than or equal to DEFAULT_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("borrowingInterestRate.greaterThanOrEqual=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the societyLoanProductList where borrowingInterestRate is greater than or equal to UPDATED_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("borrowingInterestRate.greaterThanOrEqual=" + UPDATED_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByBorrowingInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where borrowingInterestRate is less than or equal to DEFAULT_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("borrowingInterestRate.lessThanOrEqual=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the societyLoanProductList where borrowingInterestRate is less than or equal to SMALLER_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("borrowingInterestRate.lessThanOrEqual=" + SMALLER_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByBorrowingInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where borrowingInterestRate is less than DEFAULT_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("borrowingInterestRate.lessThan=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the societyLoanProductList where borrowingInterestRate is less than UPDATED_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("borrowingInterestRate.lessThan=" + UPDATED_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByBorrowingInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where borrowingInterestRate is greater than DEFAULT_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("borrowingInterestRate.greaterThan=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the societyLoanProductList where borrowingInterestRate is greater than SMALLER_BORROWING_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("borrowingInterestRate.greaterThan=" + SMALLER_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where duration equals to DEFAULT_DURATION
        defaultSocietyLoanProductShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the societyLoanProductList where duration equals to UPDATED_DURATION
        defaultSocietyLoanProductShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultSocietyLoanProductShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the societyLoanProductList where duration equals to UPDATED_DURATION
        defaultSocietyLoanProductShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where duration is not null
        defaultSocietyLoanProductShouldBeFound("duration.specified=true");

        // Get all the societyLoanProductList where duration is null
        defaultSocietyLoanProductShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where duration is greater than or equal to DEFAULT_DURATION
        defaultSocietyLoanProductShouldBeFound("duration.greaterThanOrEqual=" + DEFAULT_DURATION);

        // Get all the societyLoanProductList where duration is greater than or equal to UPDATED_DURATION
        defaultSocietyLoanProductShouldNotBeFound("duration.greaterThanOrEqual=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where duration is less than or equal to DEFAULT_DURATION
        defaultSocietyLoanProductShouldBeFound("duration.lessThanOrEqual=" + DEFAULT_DURATION);

        // Get all the societyLoanProductList where duration is less than or equal to SMALLER_DURATION
        defaultSocietyLoanProductShouldNotBeFound("duration.lessThanOrEqual=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where duration is less than DEFAULT_DURATION
        defaultSocietyLoanProductShouldNotBeFound("duration.lessThan=" + DEFAULT_DURATION);

        // Get all the societyLoanProductList where duration is less than UPDATED_DURATION
        defaultSocietyLoanProductShouldBeFound("duration.lessThan=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where duration is greater than DEFAULT_DURATION
        defaultSocietyLoanProductShouldNotBeFound("duration.greaterThan=" + DEFAULT_DURATION);

        // Get all the societyLoanProductList where duration is greater than SMALLER_DURATION
        defaultSocietyLoanProductShouldBeFound("duration.greaterThan=" + SMALLER_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where interestRate equals to DEFAULT_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("interestRate.equals=" + DEFAULT_INTEREST_RATE);

        // Get all the societyLoanProductList where interestRate equals to UPDATED_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("interestRate.equals=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where interestRate in DEFAULT_INTEREST_RATE or UPDATED_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("interestRate.in=" + DEFAULT_INTEREST_RATE + "," + UPDATED_INTEREST_RATE);

        // Get all the societyLoanProductList where interestRate equals to UPDATED_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("interestRate.in=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where interestRate is not null
        defaultSocietyLoanProductShouldBeFound("interestRate.specified=true");

        // Get all the societyLoanProductList where interestRate is null
        defaultSocietyLoanProductShouldNotBeFound("interestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where interestRate is greater than or equal to DEFAULT_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("interestRate.greaterThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the societyLoanProductList where interestRate is greater than or equal to UPDATED_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("interestRate.greaterThanOrEqual=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where interestRate is less than or equal to DEFAULT_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("interestRate.lessThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the societyLoanProductList where interestRate is less than or equal to SMALLER_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("interestRate.lessThanOrEqual=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where interestRate is less than DEFAULT_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("interestRate.lessThan=" + DEFAULT_INTEREST_RATE);

        // Get all the societyLoanProductList where interestRate is less than UPDATED_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("interestRate.lessThan=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where interestRate is greater than DEFAULT_INTEREST_RATE
        defaultSocietyLoanProductShouldNotBeFound("interestRate.greaterThan=" + DEFAULT_INTEREST_RATE);

        // Get all the societyLoanProductList where interestRate is greater than SMALLER_INTEREST_RATE
        defaultSocietyLoanProductShouldBeFound("interestRate.greaterThan=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastDateOfRepaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastDateOfRepayment equals to DEFAULT_LAST_DATE_OF_REPAYMENT
        defaultSocietyLoanProductShouldBeFound("lastDateOfRepayment.equals=" + DEFAULT_LAST_DATE_OF_REPAYMENT);

        // Get all the societyLoanProductList where lastDateOfRepayment equals to UPDATED_LAST_DATE_OF_REPAYMENT
        defaultSocietyLoanProductShouldNotBeFound("lastDateOfRepayment.equals=" + UPDATED_LAST_DATE_OF_REPAYMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastDateOfRepaymentIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastDateOfRepayment in DEFAULT_LAST_DATE_OF_REPAYMENT or UPDATED_LAST_DATE_OF_REPAYMENT
        defaultSocietyLoanProductShouldBeFound(
            "lastDateOfRepayment.in=" + DEFAULT_LAST_DATE_OF_REPAYMENT + "," + UPDATED_LAST_DATE_OF_REPAYMENT
        );

        // Get all the societyLoanProductList where lastDateOfRepayment equals to UPDATED_LAST_DATE_OF_REPAYMENT
        defaultSocietyLoanProductShouldNotBeFound("lastDateOfRepayment.in=" + UPDATED_LAST_DATE_OF_REPAYMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastDateOfRepaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastDateOfRepayment is not null
        defaultSocietyLoanProductShouldBeFound("lastDateOfRepayment.specified=true");

        // Get all the societyLoanProductList where lastDateOfRepayment is null
        defaultSocietyLoanProductShouldNotBeFound("lastDateOfRepayment.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLoanNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where loanNumber equals to DEFAULT_LOAN_NUMBER
        defaultSocietyLoanProductShouldBeFound("loanNumber.equals=" + DEFAULT_LOAN_NUMBER);

        // Get all the societyLoanProductList where loanNumber equals to UPDATED_LOAN_NUMBER
        defaultSocietyLoanProductShouldNotBeFound("loanNumber.equals=" + UPDATED_LOAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLoanNumberIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where loanNumber in DEFAULT_LOAN_NUMBER or UPDATED_LOAN_NUMBER
        defaultSocietyLoanProductShouldBeFound("loanNumber.in=" + DEFAULT_LOAN_NUMBER + "," + UPDATED_LOAN_NUMBER);

        // Get all the societyLoanProductList where loanNumber equals to UPDATED_LOAN_NUMBER
        defaultSocietyLoanProductShouldNotBeFound("loanNumber.in=" + UPDATED_LOAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLoanNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where loanNumber is not null
        defaultSocietyLoanProductShouldBeFound("loanNumber.specified=true");

        // Get all the societyLoanProductList where loanNumber is null
        defaultSocietyLoanProductShouldNotBeFound("loanNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLoanNumberContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where loanNumber contains DEFAULT_LOAN_NUMBER
        defaultSocietyLoanProductShouldBeFound("loanNumber.contains=" + DEFAULT_LOAN_NUMBER);

        // Get all the societyLoanProductList where loanNumber contains UPDATED_LOAN_NUMBER
        defaultSocietyLoanProductShouldNotBeFound("loanNumber.contains=" + UPDATED_LOAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLoanNumberNotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where loanNumber does not contain DEFAULT_LOAN_NUMBER
        defaultSocietyLoanProductShouldNotBeFound("loanNumber.doesNotContain=" + DEFAULT_LOAN_NUMBER);

        // Get all the societyLoanProductList where loanNumber does not contain UPDATED_LOAN_NUMBER
        defaultSocietyLoanProductShouldBeFound("loanNumber.doesNotContain=" + UPDATED_LOAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByMaxLoanAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where maxLoanAmt equals to DEFAULT_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldBeFound("maxLoanAmt.equals=" + DEFAULT_MAX_LOAN_AMT);

        // Get all the societyLoanProductList where maxLoanAmt equals to UPDATED_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldNotBeFound("maxLoanAmt.equals=" + UPDATED_MAX_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByMaxLoanAmtIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where maxLoanAmt in DEFAULT_MAX_LOAN_AMT or UPDATED_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldBeFound("maxLoanAmt.in=" + DEFAULT_MAX_LOAN_AMT + "," + UPDATED_MAX_LOAN_AMT);

        // Get all the societyLoanProductList where maxLoanAmt equals to UPDATED_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldNotBeFound("maxLoanAmt.in=" + UPDATED_MAX_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByMaxLoanAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where maxLoanAmt is not null
        defaultSocietyLoanProductShouldBeFound("maxLoanAmt.specified=true");

        // Get all the societyLoanProductList where maxLoanAmt is null
        defaultSocietyLoanProductShouldNotBeFound("maxLoanAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByMaxLoanAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where maxLoanAmt is greater than or equal to DEFAULT_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldBeFound("maxLoanAmt.greaterThanOrEqual=" + DEFAULT_MAX_LOAN_AMT);

        // Get all the societyLoanProductList where maxLoanAmt is greater than or equal to UPDATED_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldNotBeFound("maxLoanAmt.greaterThanOrEqual=" + UPDATED_MAX_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByMaxLoanAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where maxLoanAmt is less than or equal to DEFAULT_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldBeFound("maxLoanAmt.lessThanOrEqual=" + DEFAULT_MAX_LOAN_AMT);

        // Get all the societyLoanProductList where maxLoanAmt is less than or equal to SMALLER_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldNotBeFound("maxLoanAmt.lessThanOrEqual=" + SMALLER_MAX_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByMaxLoanAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where maxLoanAmt is less than DEFAULT_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldNotBeFound("maxLoanAmt.lessThan=" + DEFAULT_MAX_LOAN_AMT);

        // Get all the societyLoanProductList where maxLoanAmt is less than UPDATED_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldBeFound("maxLoanAmt.lessThan=" + UPDATED_MAX_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByMaxLoanAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where maxLoanAmt is greater than DEFAULT_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldNotBeFound("maxLoanAmt.greaterThan=" + DEFAULT_MAX_LOAN_AMT);

        // Get all the societyLoanProductList where maxLoanAmt is greater than SMALLER_MAX_LOAN_AMT
        defaultSocietyLoanProductShouldBeFound("maxLoanAmt.greaterThan=" + SMALLER_MAX_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfDisbursementIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfDisbursement equals to DEFAULT_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldBeFound("noOfDisbursement.equals=" + DEFAULT_NO_OF_DISBURSEMENT);

        // Get all the societyLoanProductList where noOfDisbursement equals to UPDATED_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfDisbursement.equals=" + UPDATED_NO_OF_DISBURSEMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfDisbursementIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfDisbursement in DEFAULT_NO_OF_DISBURSEMENT or UPDATED_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldBeFound("noOfDisbursement.in=" + DEFAULT_NO_OF_DISBURSEMENT + "," + UPDATED_NO_OF_DISBURSEMENT);

        // Get all the societyLoanProductList where noOfDisbursement equals to UPDATED_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfDisbursement.in=" + UPDATED_NO_OF_DISBURSEMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfDisbursementIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfDisbursement is not null
        defaultSocietyLoanProductShouldBeFound("noOfDisbursement.specified=true");

        // Get all the societyLoanProductList where noOfDisbursement is null
        defaultSocietyLoanProductShouldNotBeFound("noOfDisbursement.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfDisbursementIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfDisbursement is greater than or equal to DEFAULT_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldBeFound("noOfDisbursement.greaterThanOrEqual=" + DEFAULT_NO_OF_DISBURSEMENT);

        // Get all the societyLoanProductList where noOfDisbursement is greater than or equal to UPDATED_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfDisbursement.greaterThanOrEqual=" + UPDATED_NO_OF_DISBURSEMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfDisbursementIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfDisbursement is less than or equal to DEFAULT_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldBeFound("noOfDisbursement.lessThanOrEqual=" + DEFAULT_NO_OF_DISBURSEMENT);

        // Get all the societyLoanProductList where noOfDisbursement is less than or equal to SMALLER_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfDisbursement.lessThanOrEqual=" + SMALLER_NO_OF_DISBURSEMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfDisbursementIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfDisbursement is less than DEFAULT_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfDisbursement.lessThan=" + DEFAULT_NO_OF_DISBURSEMENT);

        // Get all the societyLoanProductList where noOfDisbursement is less than UPDATED_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldBeFound("noOfDisbursement.lessThan=" + UPDATED_NO_OF_DISBURSEMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfDisbursementIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfDisbursement is greater than DEFAULT_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfDisbursement.greaterThan=" + DEFAULT_NO_OF_DISBURSEMENT);

        // Get all the societyLoanProductList where noOfDisbursement is greater than SMALLER_NO_OF_DISBURSEMENT
        defaultSocietyLoanProductShouldBeFound("noOfDisbursement.greaterThan=" + SMALLER_NO_OF_DISBURSEMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfInstallmentIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfInstallment equals to DEFAULT_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldBeFound("noOfInstallment.equals=" + DEFAULT_NO_OF_INSTALLMENT);

        // Get all the societyLoanProductList where noOfInstallment equals to UPDATED_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfInstallment.equals=" + UPDATED_NO_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfInstallmentIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfInstallment in DEFAULT_NO_OF_INSTALLMENT or UPDATED_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldBeFound("noOfInstallment.in=" + DEFAULT_NO_OF_INSTALLMENT + "," + UPDATED_NO_OF_INSTALLMENT);

        // Get all the societyLoanProductList where noOfInstallment equals to UPDATED_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfInstallment.in=" + UPDATED_NO_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfInstallmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfInstallment is not null
        defaultSocietyLoanProductShouldBeFound("noOfInstallment.specified=true");

        // Get all the societyLoanProductList where noOfInstallment is null
        defaultSocietyLoanProductShouldNotBeFound("noOfInstallment.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfInstallmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfInstallment is greater than or equal to DEFAULT_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldBeFound("noOfInstallment.greaterThanOrEqual=" + DEFAULT_NO_OF_INSTALLMENT);

        // Get all the societyLoanProductList where noOfInstallment is greater than or equal to UPDATED_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfInstallment.greaterThanOrEqual=" + UPDATED_NO_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfInstallmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfInstallment is less than or equal to DEFAULT_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldBeFound("noOfInstallment.lessThanOrEqual=" + DEFAULT_NO_OF_INSTALLMENT);

        // Get all the societyLoanProductList where noOfInstallment is less than or equal to SMALLER_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfInstallment.lessThanOrEqual=" + SMALLER_NO_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfInstallmentIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfInstallment is less than DEFAULT_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfInstallment.lessThan=" + DEFAULT_NO_OF_INSTALLMENT);

        // Get all the societyLoanProductList where noOfInstallment is less than UPDATED_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldBeFound("noOfInstallment.lessThan=" + UPDATED_NO_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByNoOfInstallmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where noOfInstallment is greater than DEFAULT_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldNotBeFound("noOfInstallment.greaterThan=" + DEFAULT_NO_OF_INSTALLMENT);

        // Get all the societyLoanProductList where noOfInstallment is greater than SMALLER_NO_OF_INSTALLMENT
        defaultSocietyLoanProductShouldBeFound("noOfInstallment.greaterThan=" + SMALLER_NO_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadCode equals to DEFAULT_PARENT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldBeFound("parentAccHeadCode.equals=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the societyLoanProductList where parentAccHeadCode equals to UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadCode.equals=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadCode in DEFAULT_PARENT_ACC_HEAD_CODE or UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldBeFound("parentAccHeadCode.in=" + DEFAULT_PARENT_ACC_HEAD_CODE + "," + UPDATED_PARENT_ACC_HEAD_CODE);

        // Get all the societyLoanProductList where parentAccHeadCode equals to UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadCode.in=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadCode is not null
        defaultSocietyLoanProductShouldBeFound("parentAccHeadCode.specified=true");

        // Get all the societyLoanProductList where parentAccHeadCode is null
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadCodeContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadCode contains DEFAULT_PARENT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldBeFound("parentAccHeadCode.contains=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the societyLoanProductList where parentAccHeadCode contains UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadCode.contains=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadCode does not contain DEFAULT_PARENT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadCode.doesNotContain=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the societyLoanProductList where parentAccHeadCode does not contain UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyLoanProductShouldBeFound("parentAccHeadCode.doesNotContain=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadIdIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadId equals to DEFAULT_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldBeFound("parentAccHeadId.equals=" + DEFAULT_PARENT_ACC_HEAD_ID);

        // Get all the societyLoanProductList where parentAccHeadId equals to UPDATED_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadId.equals=" + UPDATED_PARENT_ACC_HEAD_ID);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadIdIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadId in DEFAULT_PARENT_ACC_HEAD_ID or UPDATED_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldBeFound("parentAccHeadId.in=" + DEFAULT_PARENT_ACC_HEAD_ID + "," + UPDATED_PARENT_ACC_HEAD_ID);

        // Get all the societyLoanProductList where parentAccHeadId equals to UPDATED_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadId.in=" + UPDATED_PARENT_ACC_HEAD_ID);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadId is not null
        defaultSocietyLoanProductShouldBeFound("parentAccHeadId.specified=true");

        // Get all the societyLoanProductList where parentAccHeadId is null
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadId.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadId is greater than or equal to DEFAULT_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldBeFound("parentAccHeadId.greaterThanOrEqual=" + DEFAULT_PARENT_ACC_HEAD_ID);

        // Get all the societyLoanProductList where parentAccHeadId is greater than or equal to UPDATED_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadId.greaterThanOrEqual=" + UPDATED_PARENT_ACC_HEAD_ID);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadId is less than or equal to DEFAULT_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldBeFound("parentAccHeadId.lessThanOrEqual=" + DEFAULT_PARENT_ACC_HEAD_ID);

        // Get all the societyLoanProductList where parentAccHeadId is less than or equal to SMALLER_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadId.lessThanOrEqual=" + SMALLER_PARENT_ACC_HEAD_ID);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadIdIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadId is less than DEFAULT_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadId.lessThan=" + DEFAULT_PARENT_ACC_HEAD_ID);

        // Get all the societyLoanProductList where parentAccHeadId is less than UPDATED_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldBeFound("parentAccHeadId.lessThan=" + UPDATED_PARENT_ACC_HEAD_ID);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByParentAccHeadIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where parentAccHeadId is greater than DEFAULT_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldNotBeFound("parentAccHeadId.greaterThan=" + DEFAULT_PARENT_ACC_HEAD_ID);

        // Get all the societyLoanProductList where parentAccHeadId is greater than SMALLER_PARENT_ACC_HEAD_ID
        defaultSocietyLoanProductShouldBeFound("parentAccHeadId.greaterThan=" + SMALLER_PARENT_ACC_HEAD_ID);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByPenaltyInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where penaltyInterest equals to DEFAULT_PENALTY_INTEREST
        defaultSocietyLoanProductShouldBeFound("penaltyInterest.equals=" + DEFAULT_PENALTY_INTEREST);

        // Get all the societyLoanProductList where penaltyInterest equals to UPDATED_PENALTY_INTEREST
        defaultSocietyLoanProductShouldNotBeFound("penaltyInterest.equals=" + UPDATED_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByPenaltyInterestIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where penaltyInterest in DEFAULT_PENALTY_INTEREST or UPDATED_PENALTY_INTEREST
        defaultSocietyLoanProductShouldBeFound("penaltyInterest.in=" + DEFAULT_PENALTY_INTEREST + "," + UPDATED_PENALTY_INTEREST);

        // Get all the societyLoanProductList where penaltyInterest equals to UPDATED_PENALTY_INTEREST
        defaultSocietyLoanProductShouldNotBeFound("penaltyInterest.in=" + UPDATED_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByPenaltyInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where penaltyInterest is not null
        defaultSocietyLoanProductShouldBeFound("penaltyInterest.specified=true");

        // Get all the societyLoanProductList where penaltyInterest is null
        defaultSocietyLoanProductShouldNotBeFound("penaltyInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByPenaltyInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where penaltyInterest is greater than or equal to DEFAULT_PENALTY_INTEREST
        defaultSocietyLoanProductShouldBeFound("penaltyInterest.greaterThanOrEqual=" + DEFAULT_PENALTY_INTEREST);

        // Get all the societyLoanProductList where penaltyInterest is greater than or equal to UPDATED_PENALTY_INTEREST
        defaultSocietyLoanProductShouldNotBeFound("penaltyInterest.greaterThanOrEqual=" + UPDATED_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByPenaltyInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where penaltyInterest is less than or equal to DEFAULT_PENALTY_INTEREST
        defaultSocietyLoanProductShouldBeFound("penaltyInterest.lessThanOrEqual=" + DEFAULT_PENALTY_INTEREST);

        // Get all the societyLoanProductList where penaltyInterest is less than or equal to SMALLER_PENALTY_INTEREST
        defaultSocietyLoanProductShouldNotBeFound("penaltyInterest.lessThanOrEqual=" + SMALLER_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByPenaltyInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where penaltyInterest is less than DEFAULT_PENALTY_INTEREST
        defaultSocietyLoanProductShouldNotBeFound("penaltyInterest.lessThan=" + DEFAULT_PENALTY_INTEREST);

        // Get all the societyLoanProductList where penaltyInterest is less than UPDATED_PENALTY_INTEREST
        defaultSocietyLoanProductShouldBeFound("penaltyInterest.lessThan=" + UPDATED_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByPenaltyInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where penaltyInterest is greater than DEFAULT_PENALTY_INTEREST
        defaultSocietyLoanProductShouldNotBeFound("penaltyInterest.greaterThan=" + DEFAULT_PENALTY_INTEREST);

        // Get all the societyLoanProductList where penaltyInterest is greater than SMALLER_PENALTY_INTEREST
        defaultSocietyLoanProductShouldBeFound("penaltyInterest.greaterThan=" + SMALLER_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productType equals to DEFAULT_PRODUCT_TYPE
        defaultSocietyLoanProductShouldBeFound("productType.equals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the societyLoanProductList where productType equals to UPDATED_PRODUCT_TYPE
        defaultSocietyLoanProductShouldNotBeFound("productType.equals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductTypeIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productType in DEFAULT_PRODUCT_TYPE or UPDATED_PRODUCT_TYPE
        defaultSocietyLoanProductShouldBeFound("productType.in=" + DEFAULT_PRODUCT_TYPE + "," + UPDATED_PRODUCT_TYPE);

        // Get all the societyLoanProductList where productType equals to UPDATED_PRODUCT_TYPE
        defaultSocietyLoanProductShouldNotBeFound("productType.in=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productType is not null
        defaultSocietyLoanProductShouldBeFound("productType.specified=true");

        // Get all the societyLoanProductList where productType is null
        defaultSocietyLoanProductShouldNotBeFound("productType.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductTypeContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productType contains DEFAULT_PRODUCT_TYPE
        defaultSocietyLoanProductShouldBeFound("productType.contains=" + DEFAULT_PRODUCT_TYPE);

        // Get all the societyLoanProductList where productType contains UPDATED_PRODUCT_TYPE
        defaultSocietyLoanProductShouldNotBeFound("productType.contains=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByProductTypeNotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where productType does not contain DEFAULT_PRODUCT_TYPE
        defaultSocietyLoanProductShouldNotBeFound("productType.doesNotContain=" + DEFAULT_PRODUCT_TYPE);

        // Get all the societyLoanProductList where productType does not contain UPDATED_PRODUCT_TYPE
        defaultSocietyLoanProductShouldBeFound("productType.doesNotContain=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByResolutionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where resolutionDate equals to DEFAULT_RESOLUTION_DATE
        defaultSocietyLoanProductShouldBeFound("resolutionDate.equals=" + DEFAULT_RESOLUTION_DATE);

        // Get all the societyLoanProductList where resolutionDate equals to UPDATED_RESOLUTION_DATE
        defaultSocietyLoanProductShouldNotBeFound("resolutionDate.equals=" + UPDATED_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByResolutionDateIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where resolutionDate in DEFAULT_RESOLUTION_DATE or UPDATED_RESOLUTION_DATE
        defaultSocietyLoanProductShouldBeFound("resolutionDate.in=" + DEFAULT_RESOLUTION_DATE + "," + UPDATED_RESOLUTION_DATE);

        // Get all the societyLoanProductList where resolutionDate equals to UPDATED_RESOLUTION_DATE
        defaultSocietyLoanProductShouldNotBeFound("resolutionDate.in=" + UPDATED_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByResolutionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where resolutionDate is not null
        defaultSocietyLoanProductShouldBeFound("resolutionDate.specified=true");

        // Get all the societyLoanProductList where resolutionDate is null
        defaultSocietyLoanProductShouldNotBeFound("resolutionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByResolutionNoIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where resolutionNo equals to DEFAULT_RESOLUTION_NO
        defaultSocietyLoanProductShouldBeFound("resolutionNo.equals=" + DEFAULT_RESOLUTION_NO);

        // Get all the societyLoanProductList where resolutionNo equals to UPDATED_RESOLUTION_NO
        defaultSocietyLoanProductShouldNotBeFound("resolutionNo.equals=" + UPDATED_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByResolutionNoIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where resolutionNo in DEFAULT_RESOLUTION_NO or UPDATED_RESOLUTION_NO
        defaultSocietyLoanProductShouldBeFound("resolutionNo.in=" + DEFAULT_RESOLUTION_NO + "," + UPDATED_RESOLUTION_NO);

        // Get all the societyLoanProductList where resolutionNo equals to UPDATED_RESOLUTION_NO
        defaultSocietyLoanProductShouldNotBeFound("resolutionNo.in=" + UPDATED_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByResolutionNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where resolutionNo is not null
        defaultSocietyLoanProductShouldBeFound("resolutionNo.specified=true");

        // Get all the societyLoanProductList where resolutionNo is null
        defaultSocietyLoanProductShouldNotBeFound("resolutionNo.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByResolutionNoContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where resolutionNo contains DEFAULT_RESOLUTION_NO
        defaultSocietyLoanProductShouldBeFound("resolutionNo.contains=" + DEFAULT_RESOLUTION_NO);

        // Get all the societyLoanProductList where resolutionNo contains UPDATED_RESOLUTION_NO
        defaultSocietyLoanProductShouldNotBeFound("resolutionNo.contains=" + UPDATED_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByResolutionNoNotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where resolutionNo does not contain DEFAULT_RESOLUTION_NO
        defaultSocietyLoanProductShouldNotBeFound("resolutionNo.doesNotContain=" + DEFAULT_RESOLUTION_NO);

        // Get all the societyLoanProductList where resolutionNo does not contain UPDATED_RESOLUTION_NO
        defaultSocietyLoanProductShouldBeFound("resolutionNo.doesNotContain=" + UPDATED_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where status equals to DEFAULT_STATUS
        defaultSocietyLoanProductShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the societyLoanProductList where status equals to UPDATED_STATUS
        defaultSocietyLoanProductShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSocietyLoanProductShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the societyLoanProductList where status equals to UPDATED_STATUS
        defaultSocietyLoanProductShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where status is not null
        defaultSocietyLoanProductShouldBeFound("status.specified=true");

        // Get all the societyLoanProductList where status is null
        defaultSocietyLoanProductShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByStatusContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where status contains DEFAULT_STATUS
        defaultSocietyLoanProductShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the societyLoanProductList where status contains UPDATED_STATUS
        defaultSocietyLoanProductShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where status does not contain DEFAULT_STATUS
        defaultSocietyLoanProductShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the societyLoanProductList where status does not contain UPDATED_STATUS
        defaultSocietyLoanProductShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsBySurchargeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where surcharge equals to DEFAULT_SURCHARGE
        defaultSocietyLoanProductShouldBeFound("surcharge.equals=" + DEFAULT_SURCHARGE);

        // Get all the societyLoanProductList where surcharge equals to UPDATED_SURCHARGE
        defaultSocietyLoanProductShouldNotBeFound("surcharge.equals=" + UPDATED_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsBySurchargeIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where surcharge in DEFAULT_SURCHARGE or UPDATED_SURCHARGE
        defaultSocietyLoanProductShouldBeFound("surcharge.in=" + DEFAULT_SURCHARGE + "," + UPDATED_SURCHARGE);

        // Get all the societyLoanProductList where surcharge equals to UPDATED_SURCHARGE
        defaultSocietyLoanProductShouldNotBeFound("surcharge.in=" + UPDATED_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsBySurchargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where surcharge is not null
        defaultSocietyLoanProductShouldBeFound("surcharge.specified=true");

        // Get all the societyLoanProductList where surcharge is null
        defaultSocietyLoanProductShouldNotBeFound("surcharge.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsBySurchargeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where surcharge is greater than or equal to DEFAULT_SURCHARGE
        defaultSocietyLoanProductShouldBeFound("surcharge.greaterThanOrEqual=" + DEFAULT_SURCHARGE);

        // Get all the societyLoanProductList where surcharge is greater than or equal to UPDATED_SURCHARGE
        defaultSocietyLoanProductShouldNotBeFound("surcharge.greaterThanOrEqual=" + UPDATED_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsBySurchargeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where surcharge is less than or equal to DEFAULT_SURCHARGE
        defaultSocietyLoanProductShouldBeFound("surcharge.lessThanOrEqual=" + DEFAULT_SURCHARGE);

        // Get all the societyLoanProductList where surcharge is less than or equal to SMALLER_SURCHARGE
        defaultSocietyLoanProductShouldNotBeFound("surcharge.lessThanOrEqual=" + SMALLER_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsBySurchargeIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where surcharge is less than DEFAULT_SURCHARGE
        defaultSocietyLoanProductShouldNotBeFound("surcharge.lessThan=" + DEFAULT_SURCHARGE);

        // Get all the societyLoanProductList where surcharge is less than UPDATED_SURCHARGE
        defaultSocietyLoanProductShouldBeFound("surcharge.lessThan=" + UPDATED_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsBySurchargeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where surcharge is greater than DEFAULT_SURCHARGE
        defaultSocietyLoanProductShouldNotBeFound("surcharge.greaterThan=" + DEFAULT_SURCHARGE);

        // Get all the societyLoanProductList where surcharge is greater than SMALLER_SURCHARGE
        defaultSocietyLoanProductShouldBeFound("surcharge.greaterThan=" + SMALLER_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByUnitSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where unitSize equals to DEFAULT_UNIT_SIZE
        defaultSocietyLoanProductShouldBeFound("unitSize.equals=" + DEFAULT_UNIT_SIZE);

        // Get all the societyLoanProductList where unitSize equals to UPDATED_UNIT_SIZE
        defaultSocietyLoanProductShouldNotBeFound("unitSize.equals=" + UPDATED_UNIT_SIZE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByUnitSizeIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where unitSize in DEFAULT_UNIT_SIZE or UPDATED_UNIT_SIZE
        defaultSocietyLoanProductShouldBeFound("unitSize.in=" + DEFAULT_UNIT_SIZE + "," + UPDATED_UNIT_SIZE);

        // Get all the societyLoanProductList where unitSize equals to UPDATED_UNIT_SIZE
        defaultSocietyLoanProductShouldNotBeFound("unitSize.in=" + UPDATED_UNIT_SIZE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByUnitSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where unitSize is not null
        defaultSocietyLoanProductShouldBeFound("unitSize.specified=true");

        // Get all the societyLoanProductList where unitSize is null
        defaultSocietyLoanProductShouldNotBeFound("unitSize.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByUnitSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where unitSize is greater than or equal to DEFAULT_UNIT_SIZE
        defaultSocietyLoanProductShouldBeFound("unitSize.greaterThanOrEqual=" + DEFAULT_UNIT_SIZE);

        // Get all the societyLoanProductList where unitSize is greater than or equal to UPDATED_UNIT_SIZE
        defaultSocietyLoanProductShouldNotBeFound("unitSize.greaterThanOrEqual=" + UPDATED_UNIT_SIZE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByUnitSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where unitSize is less than or equal to DEFAULT_UNIT_SIZE
        defaultSocietyLoanProductShouldBeFound("unitSize.lessThanOrEqual=" + DEFAULT_UNIT_SIZE);

        // Get all the societyLoanProductList where unitSize is less than or equal to SMALLER_UNIT_SIZE
        defaultSocietyLoanProductShouldNotBeFound("unitSize.lessThanOrEqual=" + SMALLER_UNIT_SIZE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByUnitSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where unitSize is less than DEFAULT_UNIT_SIZE
        defaultSocietyLoanProductShouldNotBeFound("unitSize.lessThan=" + DEFAULT_UNIT_SIZE);

        // Get all the societyLoanProductList where unitSize is less than UPDATED_UNIT_SIZE
        defaultSocietyLoanProductShouldBeFound("unitSize.lessThan=" + UPDATED_UNIT_SIZE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByUnitSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where unitSize is greater than DEFAULT_UNIT_SIZE
        defaultSocietyLoanProductShouldNotBeFound("unitSize.greaterThan=" + DEFAULT_UNIT_SIZE);

        // Get all the societyLoanProductList where unitSize is greater than SMALLER_UNIT_SIZE
        defaultSocietyLoanProductShouldBeFound("unitSize.greaterThan=" + SMALLER_UNIT_SIZE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where validFrom equals to DEFAULT_VALID_FROM
        defaultSocietyLoanProductShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the societyLoanProductList where validFrom equals to UPDATED_VALID_FROM
        defaultSocietyLoanProductShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultSocietyLoanProductShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the societyLoanProductList where validFrom equals to UPDATED_VALID_FROM
        defaultSocietyLoanProductShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where validFrom is not null
        defaultSocietyLoanProductShouldBeFound("validFrom.specified=true");

        // Get all the societyLoanProductList where validFrom is null
        defaultSocietyLoanProductShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where validTo equals to DEFAULT_VALID_TO
        defaultSocietyLoanProductShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the societyLoanProductList where validTo equals to UPDATED_VALID_TO
        defaultSocietyLoanProductShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultSocietyLoanProductShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the societyLoanProductList where validTo equals to UPDATED_VALID_TO
        defaultSocietyLoanProductShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where validTo is not null
        defaultSocietyLoanProductShouldBeFound("validTo.specified=true");

        // Get all the societyLoanProductList where validTo is null
        defaultSocietyLoanProductShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where createdOn equals to DEFAULT_CREATED_ON
        defaultSocietyLoanProductShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the societyLoanProductList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyLoanProductShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSocietyLoanProductShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the societyLoanProductList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyLoanProductShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where createdOn is not null
        defaultSocietyLoanProductShouldBeFound("createdOn.specified=true");

        // Get all the societyLoanProductList where createdOn is null
        defaultSocietyLoanProductShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where createdBy equals to DEFAULT_CREATED_BY
        defaultSocietyLoanProductShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the societyLoanProductList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyLoanProductShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSocietyLoanProductShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the societyLoanProductList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyLoanProductShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where createdBy is not null
        defaultSocietyLoanProductShouldBeFound("createdBy.specified=true");

        // Get all the societyLoanProductList where createdBy is null
        defaultSocietyLoanProductShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where createdBy contains DEFAULT_CREATED_BY
        defaultSocietyLoanProductShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the societyLoanProductList where createdBy contains UPDATED_CREATED_BY
        defaultSocietyLoanProductShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSocietyLoanProductShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the societyLoanProductList where createdBy does not contain UPDATED_CREATED_BY
        defaultSocietyLoanProductShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByIsActivateIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where isActivate equals to DEFAULT_IS_ACTIVATE
        defaultSocietyLoanProductShouldBeFound("isActivate.equals=" + DEFAULT_IS_ACTIVATE);

        // Get all the societyLoanProductList where isActivate equals to UPDATED_IS_ACTIVATE
        defaultSocietyLoanProductShouldNotBeFound("isActivate.equals=" + UPDATED_IS_ACTIVATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByIsActivateIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where isActivate in DEFAULT_IS_ACTIVATE or UPDATED_IS_ACTIVATE
        defaultSocietyLoanProductShouldBeFound("isActivate.in=" + DEFAULT_IS_ACTIVATE + "," + UPDATED_IS_ACTIVATE);

        // Get all the societyLoanProductList where isActivate equals to UPDATED_IS_ACTIVATE
        defaultSocietyLoanProductShouldNotBeFound("isActivate.in=" + UPDATED_IS_ACTIVATE);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByIsActivateIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where isActivate is not null
        defaultSocietyLoanProductShouldBeFound("isActivate.specified=true");

        // Get all the societyLoanProductList where isActivate is null
        defaultSocietyLoanProductShouldNotBeFound("isActivate.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSocietyLoanProductShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the societyLoanProductList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyLoanProductShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSocietyLoanProductShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the societyLoanProductList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyLoanProductShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastModified is not null
        defaultSocietyLoanProductShouldBeFound("lastModified.specified=true");

        // Get all the societyLoanProductList where lastModified is null
        defaultSocietyLoanProductShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSocietyLoanProductShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyLoanProductList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyLoanProductShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSocietyLoanProductShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the societyLoanProductList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyLoanProductShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastModifiedBy is not null
        defaultSocietyLoanProductShouldBeFound("lastModifiedBy.specified=true");

        // Get all the societyLoanProductList where lastModifiedBy is null
        defaultSocietyLoanProductShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSocietyLoanProductShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyLoanProductList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSocietyLoanProductShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSocietyLoanProductShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyLoanProductList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSocietyLoanProductShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSocietyLoanProductShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyLoanProductList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyLoanProductShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSocietyLoanProductShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the societyLoanProductList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyLoanProductShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField1 is not null
        defaultSocietyLoanProductShouldBeFound("freeField1.specified=true");

        // Get all the societyLoanProductList where freeField1 is null
        defaultSocietyLoanProductShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultSocietyLoanProductShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyLoanProductList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultSocietyLoanProductShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultSocietyLoanProductShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyLoanProductList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultSocietyLoanProductShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSocietyLoanProductShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyLoanProductList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyLoanProductShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSocietyLoanProductShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the societyLoanProductList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyLoanProductShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField2 is not null
        defaultSocietyLoanProductShouldBeFound("freeField2.specified=true");

        // Get all the societyLoanProductList where freeField2 is null
        defaultSocietyLoanProductShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultSocietyLoanProductShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyLoanProductList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultSocietyLoanProductShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultSocietyLoanProductShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyLoanProductList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultSocietyLoanProductShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSocietyLoanProductShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyLoanProductList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyLoanProductShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSocietyLoanProductShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the societyLoanProductList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyLoanProductShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField3 is not null
        defaultSocietyLoanProductShouldBeFound("freeField3.specified=true");

        // Get all the societyLoanProductList where freeField3 is null
        defaultSocietyLoanProductShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultSocietyLoanProductShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyLoanProductList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultSocietyLoanProductShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        // Get all the societyLoanProductList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultSocietyLoanProductShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyLoanProductList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultSocietyLoanProductShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            societyLoanProductRepository.saveAndFlush(societyLoanProduct);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        societyLoanProduct.setSociety(society);
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);
        Long societyId = society.getId();

        // Get all the societyLoanProductList where society equals to societyId
        defaultSocietyLoanProductShouldBeFound("societyId.equals=" + societyId);

        // Get all the societyLoanProductList where society equals to (societyId + 1)
        defaultSocietyLoanProductShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByBankDhoranDetailsIsEqualToSomething() throws Exception {
        BankDhoranDetails bankDhoranDetails;
        if (TestUtil.findAll(em, BankDhoranDetails.class).isEmpty()) {
            societyLoanProductRepository.saveAndFlush(societyLoanProduct);
            bankDhoranDetails = BankDhoranDetailsResourceIT.createEntity(em);
        } else {
            bankDhoranDetails = TestUtil.findAll(em, BankDhoranDetails.class).get(0);
        }
        em.persist(bankDhoranDetails);
        em.flush();
        societyLoanProduct.setBankDhoranDetails(bankDhoranDetails);
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);
        Long bankDhoranDetailsId = bankDhoranDetails.getId();

        // Get all the societyLoanProductList where bankDhoranDetails equals to bankDhoranDetailsId
        defaultSocietyLoanProductShouldBeFound("bankDhoranDetailsId.equals=" + bankDhoranDetailsId);

        // Get all the societyLoanProductList where bankDhoranDetails equals to (bankDhoranDetailsId + 1)
        defaultSocietyLoanProductShouldNotBeFound("bankDhoranDetailsId.equals=" + (bankDhoranDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllSocietyLoanProductsByGRInterestDetailsIsEqualToSomething() throws Exception {
        GRInterestDetails gRInterestDetails;
        if (TestUtil.findAll(em, GRInterestDetails.class).isEmpty()) {
            societyLoanProductRepository.saveAndFlush(societyLoanProduct);
            gRInterestDetails = GRInterestDetailsResourceIT.createEntity(em);
        } else {
            gRInterestDetails = TestUtil.findAll(em, GRInterestDetails.class).get(0);
        }
        em.persist(gRInterestDetails);
        em.flush();
        societyLoanProduct.setGRInterestDetails(gRInterestDetails);
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);
        Long gRInterestDetailsId = gRInterestDetails.getId();

        // Get all the societyLoanProductList where gRInterestDetails equals to gRInterestDetailsId
        defaultSocietyLoanProductShouldBeFound("gRInterestDetailsId.equals=" + gRInterestDetailsId);

        // Get all the societyLoanProductList where gRInterestDetails equals to (gRInterestDetailsId + 1)
        defaultSocietyLoanProductShouldNotBeFound("gRInterestDetailsId.equals=" + (gRInterestDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocietyLoanProductShouldBeFound(String filter) throws Exception {
        restSocietyLoanProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyLoanProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].accHeadCode").value(hasItem(DEFAULT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].borrowingInterestRate").value(hasItem(DEFAULT_BORROWING_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastDateOfRepayment").value(hasItem(DEFAULT_LAST_DATE_OF_REPAYMENT.toString())))
            .andExpect(jsonPath("$.[*].loanNumber").value(hasItem(DEFAULT_LOAN_NUMBER)))
            .andExpect(jsonPath("$.[*].maxLoanAmt").value(hasItem(DEFAULT_MAX_LOAN_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].noOfDisbursement").value(hasItem(DEFAULT_NO_OF_DISBURSEMENT)))
            .andExpect(jsonPath("$.[*].noOfInstallment").value(hasItem(DEFAULT_NO_OF_INSTALLMENT)))
            .andExpect(jsonPath("$.[*].parentAccHeadCode").value(hasItem(DEFAULT_PARENT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].parentAccHeadId").value(hasItem(DEFAULT_PARENT_ACC_HEAD_ID.intValue())))
            .andExpect(jsonPath("$.[*].penaltyInterest").value(hasItem(DEFAULT_PENALTY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].resolutionDate").value(hasItem(DEFAULT_RESOLUTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].resolutionNo").value(hasItem(DEFAULT_RESOLUTION_NO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].surcharge").value(hasItem(DEFAULT_SURCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitSize").value(hasItem(DEFAULT_UNIT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].isActivate").value(hasItem(DEFAULT_IS_ACTIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restSocietyLoanProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocietyLoanProductShouldNotBeFound(String filter) throws Exception {
        restSocietyLoanProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocietyLoanProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocietyLoanProduct() throws Exception {
        // Get the societyLoanProduct
        restSocietyLoanProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocietyLoanProduct() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        int databaseSizeBeforeUpdate = societyLoanProductRepository.findAll().size();

        // Update the societyLoanProduct
        SocietyLoanProduct updatedSocietyLoanProduct = societyLoanProductRepository.findById(societyLoanProduct.getId()).get();
        // Disconnect from session so that the updates on updatedSocietyLoanProduct are not directly saved in db
        em.detach(updatedSocietyLoanProduct);
        updatedSocietyLoanProduct
            .productName(UPDATED_PRODUCT_NAME)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .borrowingInterestRate(UPDATED_BORROWING_INTEREST_RATE)
            .duration(UPDATED_DURATION)
            .interestRate(UPDATED_INTEREST_RATE)
            .lastDateOfRepayment(UPDATED_LAST_DATE_OF_REPAYMENT)
            .loanNumber(UPDATED_LOAN_NUMBER)
            .maxLoanAmt(UPDATED_MAX_LOAN_AMT)
            .noOfDisbursement(UPDATED_NO_OF_DISBURSEMENT)
            .noOfInstallment(UPDATED_NO_OF_INSTALLMENT)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .parentAccHeadId(UPDATED_PARENT_ACC_HEAD_ID)
            .penaltyInterest(UPDATED_PENALTY_INTEREST)
            .productType(UPDATED_PRODUCT_TYPE)
            .resolutionDate(UPDATED_RESOLUTION_DATE)
            .resolutionNo(UPDATED_RESOLUTION_NO)
            .status(UPDATED_STATUS)
            .surcharge(UPDATED_SURCHARGE)
            .unitSize(UPDATED_UNIT_SIZE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .isActivate(UPDATED_IS_ACTIVATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        SocietyLoanProductDTO societyLoanProductDTO = societyLoanProductMapper.toDto(updatedSocietyLoanProduct);

        restSocietyLoanProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyLoanProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyLoanProductDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeUpdate);
        SocietyLoanProduct testSocietyLoanProduct = societyLoanProductList.get(societyLoanProductList.size() - 1);
        assertThat(testSocietyLoanProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testSocietyLoanProduct.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testSocietyLoanProduct.getBorrowingInterestRate()).isEqualTo(UPDATED_BORROWING_INTEREST_RATE);
        assertThat(testSocietyLoanProduct.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testSocietyLoanProduct.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testSocietyLoanProduct.getLastDateOfRepayment()).isEqualTo(UPDATED_LAST_DATE_OF_REPAYMENT);
        assertThat(testSocietyLoanProduct.getLoanNumber()).isEqualTo(UPDATED_LOAN_NUMBER);
        assertThat(testSocietyLoanProduct.getMaxLoanAmt()).isEqualTo(UPDATED_MAX_LOAN_AMT);
        assertThat(testSocietyLoanProduct.getNoOfDisbursement()).isEqualTo(UPDATED_NO_OF_DISBURSEMENT);
        assertThat(testSocietyLoanProduct.getNoOfInstallment()).isEqualTo(UPDATED_NO_OF_INSTALLMENT);
        assertThat(testSocietyLoanProduct.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testSocietyLoanProduct.getParentAccHeadId()).isEqualTo(UPDATED_PARENT_ACC_HEAD_ID);
        assertThat(testSocietyLoanProduct.getPenaltyInterest()).isEqualTo(UPDATED_PENALTY_INTEREST);
        assertThat(testSocietyLoanProduct.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testSocietyLoanProduct.getResolutionDate()).isEqualTo(UPDATED_RESOLUTION_DATE);
        assertThat(testSocietyLoanProduct.getResolutionNo()).isEqualTo(UPDATED_RESOLUTION_NO);
        assertThat(testSocietyLoanProduct.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSocietyLoanProduct.getSurcharge()).isEqualTo(UPDATED_SURCHARGE);
        assertThat(testSocietyLoanProduct.getUnitSize()).isEqualTo(UPDATED_UNIT_SIZE);
        assertThat(testSocietyLoanProduct.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testSocietyLoanProduct.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testSocietyLoanProduct.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyLoanProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyLoanProduct.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testSocietyLoanProduct.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyLoanProduct.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyLoanProduct.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyLoanProduct.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyLoanProduct.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingSocietyLoanProduct() throws Exception {
        int databaseSizeBeforeUpdate = societyLoanProductRepository.findAll().size();
        societyLoanProduct.setId(count.incrementAndGet());

        // Create the SocietyLoanProduct
        SocietyLoanProductDTO societyLoanProductDTO = societyLoanProductMapper.toDto(societyLoanProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyLoanProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyLoanProductDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyLoanProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocietyLoanProduct() throws Exception {
        int databaseSizeBeforeUpdate = societyLoanProductRepository.findAll().size();
        societyLoanProduct.setId(count.incrementAndGet());

        // Create the SocietyLoanProduct
        SocietyLoanProductDTO societyLoanProductDTO = societyLoanProductMapper.toDto(societyLoanProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyLoanProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyLoanProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocietyLoanProduct() throws Exception {
        int databaseSizeBeforeUpdate = societyLoanProductRepository.findAll().size();
        societyLoanProduct.setId(count.incrementAndGet());

        // Create the SocietyLoanProduct
        SocietyLoanProductDTO societyLoanProductDTO = societyLoanProductMapper.toDto(societyLoanProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyLoanProductMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyLoanProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocietyLoanProductWithPatch() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        int databaseSizeBeforeUpdate = societyLoanProductRepository.findAll().size();

        // Update the societyLoanProduct using partial update
        SocietyLoanProduct partialUpdatedSocietyLoanProduct = new SocietyLoanProduct();
        partialUpdatedSocietyLoanProduct.setId(societyLoanProduct.getId());

        partialUpdatedSocietyLoanProduct
            .maxLoanAmt(UPDATED_MAX_LOAN_AMT)
            .noOfDisbursement(UPDATED_NO_OF_DISBURSEMENT)
            .noOfInstallment(UPDATED_NO_OF_INSTALLMENT)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .penaltyInterest(UPDATED_PENALTY_INTEREST)
            .productType(UPDATED_PRODUCT_TYPE)
            .resolutionDate(UPDATED_RESOLUTION_DATE)
            .resolutionNo(UPDATED_RESOLUTION_NO)
            .surcharge(UPDATED_SURCHARGE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1);

        restSocietyLoanProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyLoanProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyLoanProduct))
            )
            .andExpect(status().isOk());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeUpdate);
        SocietyLoanProduct testSocietyLoanProduct = societyLoanProductList.get(societyLoanProductList.size() - 1);
        assertThat(testSocietyLoanProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testSocietyLoanProduct.getAccHeadCode()).isEqualTo(DEFAULT_ACC_HEAD_CODE);
        assertThat(testSocietyLoanProduct.getBorrowingInterestRate()).isEqualTo(DEFAULT_BORROWING_INTEREST_RATE);
        assertThat(testSocietyLoanProduct.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testSocietyLoanProduct.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testSocietyLoanProduct.getLastDateOfRepayment()).isEqualTo(DEFAULT_LAST_DATE_OF_REPAYMENT);
        assertThat(testSocietyLoanProduct.getLoanNumber()).isEqualTo(DEFAULT_LOAN_NUMBER);
        assertThat(testSocietyLoanProduct.getMaxLoanAmt()).isEqualTo(UPDATED_MAX_LOAN_AMT);
        assertThat(testSocietyLoanProduct.getNoOfDisbursement()).isEqualTo(UPDATED_NO_OF_DISBURSEMENT);
        assertThat(testSocietyLoanProduct.getNoOfInstallment()).isEqualTo(UPDATED_NO_OF_INSTALLMENT);
        assertThat(testSocietyLoanProduct.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testSocietyLoanProduct.getParentAccHeadId()).isEqualTo(DEFAULT_PARENT_ACC_HEAD_ID);
        assertThat(testSocietyLoanProduct.getPenaltyInterest()).isEqualTo(UPDATED_PENALTY_INTEREST);
        assertThat(testSocietyLoanProduct.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testSocietyLoanProduct.getResolutionDate()).isEqualTo(UPDATED_RESOLUTION_DATE);
        assertThat(testSocietyLoanProduct.getResolutionNo()).isEqualTo(UPDATED_RESOLUTION_NO);
        assertThat(testSocietyLoanProduct.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSocietyLoanProduct.getSurcharge()).isEqualTo(UPDATED_SURCHARGE);
        assertThat(testSocietyLoanProduct.getUnitSize()).isEqualTo(DEFAULT_UNIT_SIZE);
        assertThat(testSocietyLoanProduct.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testSocietyLoanProduct.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testSocietyLoanProduct.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyLoanProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyLoanProduct.getIsActivate()).isEqualTo(DEFAULT_IS_ACTIVATE);
        assertThat(testSocietyLoanProduct.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyLoanProduct.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyLoanProduct.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyLoanProduct.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyLoanProduct.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateSocietyLoanProductWithPatch() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        int databaseSizeBeforeUpdate = societyLoanProductRepository.findAll().size();

        // Update the societyLoanProduct using partial update
        SocietyLoanProduct partialUpdatedSocietyLoanProduct = new SocietyLoanProduct();
        partialUpdatedSocietyLoanProduct.setId(societyLoanProduct.getId());

        partialUpdatedSocietyLoanProduct
            .productName(UPDATED_PRODUCT_NAME)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .borrowingInterestRate(UPDATED_BORROWING_INTEREST_RATE)
            .duration(UPDATED_DURATION)
            .interestRate(UPDATED_INTEREST_RATE)
            .lastDateOfRepayment(UPDATED_LAST_DATE_OF_REPAYMENT)
            .loanNumber(UPDATED_LOAN_NUMBER)
            .maxLoanAmt(UPDATED_MAX_LOAN_AMT)
            .noOfDisbursement(UPDATED_NO_OF_DISBURSEMENT)
            .noOfInstallment(UPDATED_NO_OF_INSTALLMENT)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .parentAccHeadId(UPDATED_PARENT_ACC_HEAD_ID)
            .penaltyInterest(UPDATED_PENALTY_INTEREST)
            .productType(UPDATED_PRODUCT_TYPE)
            .resolutionDate(UPDATED_RESOLUTION_DATE)
            .resolutionNo(UPDATED_RESOLUTION_NO)
            .status(UPDATED_STATUS)
            .surcharge(UPDATED_SURCHARGE)
            .unitSize(UPDATED_UNIT_SIZE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .isActivate(UPDATED_IS_ACTIVATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyLoanProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyLoanProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyLoanProduct))
            )
            .andExpect(status().isOk());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeUpdate);
        SocietyLoanProduct testSocietyLoanProduct = societyLoanProductList.get(societyLoanProductList.size() - 1);
        assertThat(testSocietyLoanProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testSocietyLoanProduct.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testSocietyLoanProduct.getBorrowingInterestRate()).isEqualTo(UPDATED_BORROWING_INTEREST_RATE);
        assertThat(testSocietyLoanProduct.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testSocietyLoanProduct.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testSocietyLoanProduct.getLastDateOfRepayment()).isEqualTo(UPDATED_LAST_DATE_OF_REPAYMENT);
        assertThat(testSocietyLoanProduct.getLoanNumber()).isEqualTo(UPDATED_LOAN_NUMBER);
        assertThat(testSocietyLoanProduct.getMaxLoanAmt()).isEqualTo(UPDATED_MAX_LOAN_AMT);
        assertThat(testSocietyLoanProduct.getNoOfDisbursement()).isEqualTo(UPDATED_NO_OF_DISBURSEMENT);
        assertThat(testSocietyLoanProduct.getNoOfInstallment()).isEqualTo(UPDATED_NO_OF_INSTALLMENT);
        assertThat(testSocietyLoanProduct.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testSocietyLoanProduct.getParentAccHeadId()).isEqualTo(UPDATED_PARENT_ACC_HEAD_ID);
        assertThat(testSocietyLoanProduct.getPenaltyInterest()).isEqualTo(UPDATED_PENALTY_INTEREST);
        assertThat(testSocietyLoanProduct.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testSocietyLoanProduct.getResolutionDate()).isEqualTo(UPDATED_RESOLUTION_DATE);
        assertThat(testSocietyLoanProduct.getResolutionNo()).isEqualTo(UPDATED_RESOLUTION_NO);
        assertThat(testSocietyLoanProduct.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSocietyLoanProduct.getSurcharge()).isEqualTo(UPDATED_SURCHARGE);
        assertThat(testSocietyLoanProduct.getUnitSize()).isEqualTo(UPDATED_UNIT_SIZE);
        assertThat(testSocietyLoanProduct.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testSocietyLoanProduct.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testSocietyLoanProduct.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyLoanProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyLoanProduct.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testSocietyLoanProduct.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyLoanProduct.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyLoanProduct.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyLoanProduct.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyLoanProduct.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingSocietyLoanProduct() throws Exception {
        int databaseSizeBeforeUpdate = societyLoanProductRepository.findAll().size();
        societyLoanProduct.setId(count.incrementAndGet());

        // Create the SocietyLoanProduct
        SocietyLoanProductDTO societyLoanProductDTO = societyLoanProductMapper.toDto(societyLoanProduct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyLoanProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societyLoanProductDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyLoanProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocietyLoanProduct() throws Exception {
        int databaseSizeBeforeUpdate = societyLoanProductRepository.findAll().size();
        societyLoanProduct.setId(count.incrementAndGet());

        // Create the SocietyLoanProduct
        SocietyLoanProductDTO societyLoanProductDTO = societyLoanProductMapper.toDto(societyLoanProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyLoanProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyLoanProductDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocietyLoanProduct() throws Exception {
        int databaseSizeBeforeUpdate = societyLoanProductRepository.findAll().size();
        societyLoanProduct.setId(count.incrementAndGet());

        // Create the SocietyLoanProduct
        SocietyLoanProductDTO societyLoanProductDTO = societyLoanProductMapper.toDto(societyLoanProduct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyLoanProductMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyLoanProductDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyLoanProduct in the database
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocietyLoanProduct() throws Exception {
        // Initialize the database
        societyLoanProductRepository.saveAndFlush(societyLoanProduct);

        int databaseSizeBeforeDelete = societyLoanProductRepository.findAll().size();

        // Delete the societyLoanProduct
        restSocietyLoanProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, societyLoanProduct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocietyLoanProduct> societyLoanProductList = societyLoanProductRepository.findAll();
        assertThat(societyLoanProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

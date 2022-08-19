package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.domain.LoanDemand;
import com.vgtech.vks.app.domain.LoanDetails;
import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.domain.SocietyLoanProduct;
import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import com.vgtech.vks.app.domain.enumeration.LoanType;
import com.vgtech.vks.app.domain.enumeration.NpaClassification;
import com.vgtech.vks.app.repository.LoanDetailsRepository;
import com.vgtech.vks.app.service.criteria.LoanDetailsCriteria;
import com.vgtech.vks.app.service.dto.LoanDetailsDTO;
import com.vgtech.vks.app.service.mapper.LoanDetailsMapper;
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
 * Integration tests for the {@link LoanDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanDetailsResourceIT {

    private static final Double DEFAULT_LOAN_AMOUNT = 1D;
    private static final Double UPDATED_LOAN_AMOUNT = 2D;
    private static final Double SMALLER_LOAN_AMOUNT = 1D - 1D;

    private static final String DEFAULT_LOAN_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_ACCOUNT_NO = "BBBBBBBBBB";

    private static final LoanType DEFAULT_LOAN_TYPE = LoanType.SHORT_TERM;
    private static final LoanType UPDATED_LOAN_TYPE = LoanType.MID_TERM;

    private static final LoanStatus DEFAULT_STATUS = LoanStatus.APPLIED;
    private static final LoanStatus UPDATED_STATUS = LoanStatus.PENDING;

    private static final Instant DEFAULT_LOAN_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOAN_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOAN_PLANNED_CLOSURE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_PLANNED_CLOSURE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOAN_CLOSER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_CLOSER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOAN_EFFECTIVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_EFFECTIVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final NpaClassification DEFAULT_LOAN_CLASSIFICATION = NpaClassification.SUB_STANDARD_ASSESTS;
    private static final NpaClassification UPDATED_LOAN_CLASSIFICATION = NpaClassification.DOUBTFUL_1;

    private static final String DEFAULT_RESOLUTION_NO = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESOLUTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESOLUTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_COST_OF_INVESTMENT = 1D;
    private static final Double UPDATED_COST_OF_INVESTMENT = 2D;
    private static final Double SMALLER_COST_OF_INVESTMENT = 1D - 1D;

    private static final Double DEFAULT_LOAN_BENEFITING_AREA = 1D;
    private static final Double UPDATED_LOAN_BENEFITING_AREA = 2D;
    private static final Double SMALLER_LOAN_BENEFITING_AREA = 1D - 1D;

    private static final Long DEFAULT_DCCB_LOAN_NO = 1L;
    private static final Long UPDATED_DCCB_LOAN_NO = 2L;
    private static final Long SMALLER_DCCB_LOAN_NO = 1L - 1L;

    private static final Long DEFAULT_MORTGAGE_DEED_NO = 1L;
    private static final Long UPDATED_MORTGAGE_DEED_NO = 2L;
    private static final Long SMALLER_MORTGAGE_DEED_NO = 1L - 1L;

    private static final Instant DEFAULT_MORTGAGE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MORTGAGE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_EXTENT_MORGAGE_VALUE = 1D;
    private static final Double UPDATED_EXTENT_MORGAGE_VALUE = 2D;
    private static final Double SMALLER_EXTENT_MORGAGE_VALUE = 1D - 1D;

    private static final String DEFAULT_PARENT_ACC_HEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ACC_HEAD_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_DISBURSEMENT_AMT = 1D;
    private static final Double UPDATED_DISBURSEMENT_AMT = 2D;
    private static final Double SMALLER_DISBURSEMENT_AMT = 1D - 1D;

    private static final String DEFAULT_DISBURSEMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DISBURSEMENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/loan-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanDetailsRepository loanDetailsRepository;

    @Autowired
    private LoanDetailsMapper loanDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanDetailsMockMvc;

    private LoanDetails loanDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDetails createEntity(EntityManager em) {
        LoanDetails loanDetails = new LoanDetails()
            .loanAmount(DEFAULT_LOAN_AMOUNT)
            .loanAccountNo(DEFAULT_LOAN_ACCOUNT_NO)
            .loanType(DEFAULT_LOAN_TYPE)
            .status(DEFAULT_STATUS)
            .loanStartDate(DEFAULT_LOAN_START_DATE)
            .loanEndDate(DEFAULT_LOAN_END_DATE)
            .loanPlannedClosureDate(DEFAULT_LOAN_PLANNED_CLOSURE_DATE)
            .loanCloserDate(DEFAULT_LOAN_CLOSER_DATE)
            .loanEffectiveDate(DEFAULT_LOAN_EFFECTIVE_DATE)
            .loanClassification(DEFAULT_LOAN_CLASSIFICATION)
            .resolutionNo(DEFAULT_RESOLUTION_NO)
            .resolutionDate(DEFAULT_RESOLUTION_DATE)
            .costOfInvestment(DEFAULT_COST_OF_INVESTMENT)
            .loanBenefitingArea(DEFAULT_LOAN_BENEFITING_AREA)
            .dccbLoanNo(DEFAULT_DCCB_LOAN_NO)
            .mortgageDeedNo(DEFAULT_MORTGAGE_DEED_NO)
            .mortgageDate(DEFAULT_MORTGAGE_DATE)
            .extentMorgageValue(DEFAULT_EXTENT_MORGAGE_VALUE)
            .parentAccHeadCode(DEFAULT_PARENT_ACC_HEAD_CODE)
            .loanAccountName(DEFAULT_LOAN_ACCOUNT_NAME)
            .disbursementAmt(DEFAULT_DISBURSEMENT_AMT)
            .disbursementStatus(DEFAULT_DISBURSEMENT_STATUS)
            .year(DEFAULT_YEAR)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return loanDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDetails createUpdatedEntity(EntityManager em) {
        LoanDetails loanDetails = new LoanDetails()
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanAccountNo(UPDATED_LOAN_ACCOUNT_NO)
            .loanType(UPDATED_LOAN_TYPE)
            .status(UPDATED_STATUS)
            .loanStartDate(UPDATED_LOAN_START_DATE)
            .loanEndDate(UPDATED_LOAN_END_DATE)
            .loanPlannedClosureDate(UPDATED_LOAN_PLANNED_CLOSURE_DATE)
            .loanCloserDate(UPDATED_LOAN_CLOSER_DATE)
            .loanEffectiveDate(UPDATED_LOAN_EFFECTIVE_DATE)
            .loanClassification(UPDATED_LOAN_CLASSIFICATION)
            .resolutionNo(UPDATED_RESOLUTION_NO)
            .resolutionDate(UPDATED_RESOLUTION_DATE)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .loanBenefitingArea(UPDATED_LOAN_BENEFITING_AREA)
            .dccbLoanNo(UPDATED_DCCB_LOAN_NO)
            .mortgageDeedNo(UPDATED_MORTGAGE_DEED_NO)
            .mortgageDate(UPDATED_MORTGAGE_DATE)
            .extentMorgageValue(UPDATED_EXTENT_MORGAGE_VALUE)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .loanAccountName(UPDATED_LOAN_ACCOUNT_NAME)
            .disbursementAmt(UPDATED_DISBURSEMENT_AMT)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return loanDetails;
    }

    @BeforeEach
    public void initTest() {
        loanDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanDetails() throws Exception {
        int databaseSizeBeforeCreate = loanDetailsRepository.findAll().size();
        // Create the LoanDetails
        LoanDetailsDTO loanDetailsDTO = loanDetailsMapper.toDto(loanDetails);
        restLoanDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        LoanDetails testLoanDetails = loanDetailsList.get(loanDetailsList.size() - 1);
        assertThat(testLoanDetails.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testLoanDetails.getLoanAccountNo()).isEqualTo(DEFAULT_LOAN_ACCOUNT_NO);
        assertThat(testLoanDetails.getLoanType()).isEqualTo(DEFAULT_LOAN_TYPE);
        assertThat(testLoanDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoanDetails.getLoanStartDate()).isEqualTo(DEFAULT_LOAN_START_DATE);
        assertThat(testLoanDetails.getLoanEndDate()).isEqualTo(DEFAULT_LOAN_END_DATE);
        assertThat(testLoanDetails.getLoanPlannedClosureDate()).isEqualTo(DEFAULT_LOAN_PLANNED_CLOSURE_DATE);
        assertThat(testLoanDetails.getLoanCloserDate()).isEqualTo(DEFAULT_LOAN_CLOSER_DATE);
        assertThat(testLoanDetails.getLoanEffectiveDate()).isEqualTo(DEFAULT_LOAN_EFFECTIVE_DATE);
        assertThat(testLoanDetails.getLoanClassification()).isEqualTo(DEFAULT_LOAN_CLASSIFICATION);
        assertThat(testLoanDetails.getResolutionNo()).isEqualTo(DEFAULT_RESOLUTION_NO);
        assertThat(testLoanDetails.getResolutionDate()).isEqualTo(DEFAULT_RESOLUTION_DATE);
        assertThat(testLoanDetails.getCostOfInvestment()).isEqualTo(DEFAULT_COST_OF_INVESTMENT);
        assertThat(testLoanDetails.getLoanBenefitingArea()).isEqualTo(DEFAULT_LOAN_BENEFITING_AREA);
        assertThat(testLoanDetails.getDccbLoanNo()).isEqualTo(DEFAULT_DCCB_LOAN_NO);
        assertThat(testLoanDetails.getMortgageDeedNo()).isEqualTo(DEFAULT_MORTGAGE_DEED_NO);
        assertThat(testLoanDetails.getMortgageDate()).isEqualTo(DEFAULT_MORTGAGE_DATE);
        assertThat(testLoanDetails.getExtentMorgageValue()).isEqualTo(DEFAULT_EXTENT_MORGAGE_VALUE);
        assertThat(testLoanDetails.getParentAccHeadCode()).isEqualTo(DEFAULT_PARENT_ACC_HEAD_CODE);
        assertThat(testLoanDetails.getLoanAccountName()).isEqualTo(DEFAULT_LOAN_ACCOUNT_NAME);
        assertThat(testLoanDetails.getDisbursementAmt()).isEqualTo(DEFAULT_DISBURSEMENT_AMT);
        assertThat(testLoanDetails.getDisbursementStatus()).isEqualTo(DEFAULT_DISBURSEMENT_STATUS);
        assertThat(testLoanDetails.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLoanDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createLoanDetailsWithExistingId() throws Exception {
        // Create the LoanDetails with an existing ID
        loanDetails.setId(1L);
        LoanDetailsDTO loanDetailsDTO = loanDetailsMapper.toDto(loanDetails);

        int databaseSizeBeforeCreate = loanDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanDetails() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList
        restLoanDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanAccountNo").value(hasItem(DEFAULT_LOAN_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].loanType").value(hasItem(DEFAULT_LOAN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loanStartDate").value(hasItem(DEFAULT_LOAN_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanEndDate").value(hasItem(DEFAULT_LOAN_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanPlannedClosureDate").value(hasItem(DEFAULT_LOAN_PLANNED_CLOSURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanCloserDate").value(hasItem(DEFAULT_LOAN_CLOSER_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanEffectiveDate").value(hasItem(DEFAULT_LOAN_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanClassification").value(hasItem(DEFAULT_LOAN_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].resolutionNo").value(hasItem(DEFAULT_RESOLUTION_NO)))
            .andExpect(jsonPath("$.[*].resolutionDate").value(hasItem(DEFAULT_RESOLUTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].costOfInvestment").value(hasItem(DEFAULT_COST_OF_INVESTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanBenefitingArea").value(hasItem(DEFAULT_LOAN_BENEFITING_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].dccbLoanNo").value(hasItem(DEFAULT_DCCB_LOAN_NO.intValue())))
            .andExpect(jsonPath("$.[*].mortgageDeedNo").value(hasItem(DEFAULT_MORTGAGE_DEED_NO.intValue())))
            .andExpect(jsonPath("$.[*].mortgageDate").value(hasItem(DEFAULT_MORTGAGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].extentMorgageValue").value(hasItem(DEFAULT_EXTENT_MORGAGE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].parentAccHeadCode").value(hasItem(DEFAULT_PARENT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].loanAccountName").value(hasItem(DEFAULT_LOAN_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].disbursementAmt").value(hasItem(DEFAULT_DISBURSEMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].disbursementStatus").value(hasItem(DEFAULT_DISBURSEMENT_STATUS)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getLoanDetails() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get the loanDetails
        restLoanDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, loanDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanDetails.getId().intValue()))
            .andExpect(jsonPath("$.loanAmount").value(DEFAULT_LOAN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.loanAccountNo").value(DEFAULT_LOAN_ACCOUNT_NO))
            .andExpect(jsonPath("$.loanType").value(DEFAULT_LOAN_TYPE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.loanStartDate").value(DEFAULT_LOAN_START_DATE.toString()))
            .andExpect(jsonPath("$.loanEndDate").value(DEFAULT_LOAN_END_DATE.toString()))
            .andExpect(jsonPath("$.loanPlannedClosureDate").value(DEFAULT_LOAN_PLANNED_CLOSURE_DATE.toString()))
            .andExpect(jsonPath("$.loanCloserDate").value(DEFAULT_LOAN_CLOSER_DATE.toString()))
            .andExpect(jsonPath("$.loanEffectiveDate").value(DEFAULT_LOAN_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.loanClassification").value(DEFAULT_LOAN_CLASSIFICATION.toString()))
            .andExpect(jsonPath("$.resolutionNo").value(DEFAULT_RESOLUTION_NO))
            .andExpect(jsonPath("$.resolutionDate").value(DEFAULT_RESOLUTION_DATE.toString()))
            .andExpect(jsonPath("$.costOfInvestment").value(DEFAULT_COST_OF_INVESTMENT.doubleValue()))
            .andExpect(jsonPath("$.loanBenefitingArea").value(DEFAULT_LOAN_BENEFITING_AREA.doubleValue()))
            .andExpect(jsonPath("$.dccbLoanNo").value(DEFAULT_DCCB_LOAN_NO.intValue()))
            .andExpect(jsonPath("$.mortgageDeedNo").value(DEFAULT_MORTGAGE_DEED_NO.intValue()))
            .andExpect(jsonPath("$.mortgageDate").value(DEFAULT_MORTGAGE_DATE.toString()))
            .andExpect(jsonPath("$.extentMorgageValue").value(DEFAULT_EXTENT_MORGAGE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.parentAccHeadCode").value(DEFAULT_PARENT_ACC_HEAD_CODE))
            .andExpect(jsonPath("$.loanAccountName").value(DEFAULT_LOAN_ACCOUNT_NAME))
            .andExpect(jsonPath("$.disbursementAmt").value(DEFAULT_DISBURSEMENT_AMT.doubleValue()))
            .andExpect(jsonPath("$.disbursementStatus").value(DEFAULT_DISBURSEMENT_STATUS))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getLoanDetailsByIdFiltering() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        Long id = loanDetails.getId();

        defaultLoanDetailsShouldBeFound("id.equals=" + id);
        defaultLoanDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultLoanDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAmount equals to DEFAULT_LOAN_AMOUNT
        defaultLoanDetailsShouldBeFound("loanAmount.equals=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanDetailsList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultLoanDetailsShouldNotBeFound("loanAmount.equals=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAmount in DEFAULT_LOAN_AMOUNT or UPDATED_LOAN_AMOUNT
        defaultLoanDetailsShouldBeFound("loanAmount.in=" + DEFAULT_LOAN_AMOUNT + "," + UPDATED_LOAN_AMOUNT);

        // Get all the loanDetailsList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultLoanDetailsShouldNotBeFound("loanAmount.in=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAmount is not null
        defaultLoanDetailsShouldBeFound("loanAmount.specified=true");

        // Get all the loanDetailsList where loanAmount is null
        defaultLoanDetailsShouldNotBeFound("loanAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAmount is greater than or equal to DEFAULT_LOAN_AMOUNT
        defaultLoanDetailsShouldBeFound("loanAmount.greaterThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanDetailsList where loanAmount is greater than or equal to UPDATED_LOAN_AMOUNT
        defaultLoanDetailsShouldNotBeFound("loanAmount.greaterThanOrEqual=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAmount is less than or equal to DEFAULT_LOAN_AMOUNT
        defaultLoanDetailsShouldBeFound("loanAmount.lessThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanDetailsList where loanAmount is less than or equal to SMALLER_LOAN_AMOUNT
        defaultLoanDetailsShouldNotBeFound("loanAmount.lessThanOrEqual=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAmount is less than DEFAULT_LOAN_AMOUNT
        defaultLoanDetailsShouldNotBeFound("loanAmount.lessThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanDetailsList where loanAmount is less than UPDATED_LOAN_AMOUNT
        defaultLoanDetailsShouldBeFound("loanAmount.lessThan=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAmount is greater than DEFAULT_LOAN_AMOUNT
        defaultLoanDetailsShouldNotBeFound("loanAmount.greaterThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanDetailsList where loanAmount is greater than SMALLER_LOAN_AMOUNT
        defaultLoanDetailsShouldBeFound("loanAmount.greaterThan=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountNo equals to DEFAULT_LOAN_ACCOUNT_NO
        defaultLoanDetailsShouldBeFound("loanAccountNo.equals=" + DEFAULT_LOAN_ACCOUNT_NO);

        // Get all the loanDetailsList where loanAccountNo equals to UPDATED_LOAN_ACCOUNT_NO
        defaultLoanDetailsShouldNotBeFound("loanAccountNo.equals=" + UPDATED_LOAN_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountNo in DEFAULT_LOAN_ACCOUNT_NO or UPDATED_LOAN_ACCOUNT_NO
        defaultLoanDetailsShouldBeFound("loanAccountNo.in=" + DEFAULT_LOAN_ACCOUNT_NO + "," + UPDATED_LOAN_ACCOUNT_NO);

        // Get all the loanDetailsList where loanAccountNo equals to UPDATED_LOAN_ACCOUNT_NO
        defaultLoanDetailsShouldNotBeFound("loanAccountNo.in=" + UPDATED_LOAN_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountNo is not null
        defaultLoanDetailsShouldBeFound("loanAccountNo.specified=true");

        // Get all the loanDetailsList where loanAccountNo is null
        defaultLoanDetailsShouldNotBeFound("loanAccountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNoContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountNo contains DEFAULT_LOAN_ACCOUNT_NO
        defaultLoanDetailsShouldBeFound("loanAccountNo.contains=" + DEFAULT_LOAN_ACCOUNT_NO);

        // Get all the loanDetailsList where loanAccountNo contains UPDATED_LOAN_ACCOUNT_NO
        defaultLoanDetailsShouldNotBeFound("loanAccountNo.contains=" + UPDATED_LOAN_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNoNotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountNo does not contain DEFAULT_LOAN_ACCOUNT_NO
        defaultLoanDetailsShouldNotBeFound("loanAccountNo.doesNotContain=" + DEFAULT_LOAN_ACCOUNT_NO);

        // Get all the loanDetailsList where loanAccountNo does not contain UPDATED_LOAN_ACCOUNT_NO
        defaultLoanDetailsShouldBeFound("loanAccountNo.doesNotContain=" + UPDATED_LOAN_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanType equals to DEFAULT_LOAN_TYPE
        defaultLoanDetailsShouldBeFound("loanType.equals=" + DEFAULT_LOAN_TYPE);

        // Get all the loanDetailsList where loanType equals to UPDATED_LOAN_TYPE
        defaultLoanDetailsShouldNotBeFound("loanType.equals=" + UPDATED_LOAN_TYPE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanTypeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanType in DEFAULT_LOAN_TYPE or UPDATED_LOAN_TYPE
        defaultLoanDetailsShouldBeFound("loanType.in=" + DEFAULT_LOAN_TYPE + "," + UPDATED_LOAN_TYPE);

        // Get all the loanDetailsList where loanType equals to UPDATED_LOAN_TYPE
        defaultLoanDetailsShouldNotBeFound("loanType.in=" + UPDATED_LOAN_TYPE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanType is not null
        defaultLoanDetailsShouldBeFound("loanType.specified=true");

        // Get all the loanDetailsList where loanType is null
        defaultLoanDetailsShouldNotBeFound("loanType.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where status equals to DEFAULT_STATUS
        defaultLoanDetailsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the loanDetailsList where status equals to UPDATED_STATUS
        defaultLoanDetailsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLoanDetailsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the loanDetailsList where status equals to UPDATED_STATUS
        defaultLoanDetailsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where status is not null
        defaultLoanDetailsShouldBeFound("status.specified=true");

        // Get all the loanDetailsList where status is null
        defaultLoanDetailsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanStartDate equals to DEFAULT_LOAN_START_DATE
        defaultLoanDetailsShouldBeFound("loanStartDate.equals=" + DEFAULT_LOAN_START_DATE);

        // Get all the loanDetailsList where loanStartDate equals to UPDATED_LOAN_START_DATE
        defaultLoanDetailsShouldNotBeFound("loanStartDate.equals=" + UPDATED_LOAN_START_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanStartDate in DEFAULT_LOAN_START_DATE or UPDATED_LOAN_START_DATE
        defaultLoanDetailsShouldBeFound("loanStartDate.in=" + DEFAULT_LOAN_START_DATE + "," + UPDATED_LOAN_START_DATE);

        // Get all the loanDetailsList where loanStartDate equals to UPDATED_LOAN_START_DATE
        defaultLoanDetailsShouldNotBeFound("loanStartDate.in=" + UPDATED_LOAN_START_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanStartDate is not null
        defaultLoanDetailsShouldBeFound("loanStartDate.specified=true");

        // Get all the loanDetailsList where loanStartDate is null
        defaultLoanDetailsShouldNotBeFound("loanStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanEndDate equals to DEFAULT_LOAN_END_DATE
        defaultLoanDetailsShouldBeFound("loanEndDate.equals=" + DEFAULT_LOAN_END_DATE);

        // Get all the loanDetailsList where loanEndDate equals to UPDATED_LOAN_END_DATE
        defaultLoanDetailsShouldNotBeFound("loanEndDate.equals=" + UPDATED_LOAN_END_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanEndDate in DEFAULT_LOAN_END_DATE or UPDATED_LOAN_END_DATE
        defaultLoanDetailsShouldBeFound("loanEndDate.in=" + DEFAULT_LOAN_END_DATE + "," + UPDATED_LOAN_END_DATE);

        // Get all the loanDetailsList where loanEndDate equals to UPDATED_LOAN_END_DATE
        defaultLoanDetailsShouldNotBeFound("loanEndDate.in=" + UPDATED_LOAN_END_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanEndDate is not null
        defaultLoanDetailsShouldBeFound("loanEndDate.specified=true");

        // Get all the loanDetailsList where loanEndDate is null
        defaultLoanDetailsShouldNotBeFound("loanEndDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanPlannedClosureDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanPlannedClosureDate equals to DEFAULT_LOAN_PLANNED_CLOSURE_DATE
        defaultLoanDetailsShouldBeFound("loanPlannedClosureDate.equals=" + DEFAULT_LOAN_PLANNED_CLOSURE_DATE);

        // Get all the loanDetailsList where loanPlannedClosureDate equals to UPDATED_LOAN_PLANNED_CLOSURE_DATE
        defaultLoanDetailsShouldNotBeFound("loanPlannedClosureDate.equals=" + UPDATED_LOAN_PLANNED_CLOSURE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanPlannedClosureDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanPlannedClosureDate in DEFAULT_LOAN_PLANNED_CLOSURE_DATE or UPDATED_LOAN_PLANNED_CLOSURE_DATE
        defaultLoanDetailsShouldBeFound(
            "loanPlannedClosureDate.in=" + DEFAULT_LOAN_PLANNED_CLOSURE_DATE + "," + UPDATED_LOAN_PLANNED_CLOSURE_DATE
        );

        // Get all the loanDetailsList where loanPlannedClosureDate equals to UPDATED_LOAN_PLANNED_CLOSURE_DATE
        defaultLoanDetailsShouldNotBeFound("loanPlannedClosureDate.in=" + UPDATED_LOAN_PLANNED_CLOSURE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanPlannedClosureDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanPlannedClosureDate is not null
        defaultLoanDetailsShouldBeFound("loanPlannedClosureDate.specified=true");

        // Get all the loanDetailsList where loanPlannedClosureDate is null
        defaultLoanDetailsShouldNotBeFound("loanPlannedClosureDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanCloserDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanCloserDate equals to DEFAULT_LOAN_CLOSER_DATE
        defaultLoanDetailsShouldBeFound("loanCloserDate.equals=" + DEFAULT_LOAN_CLOSER_DATE);

        // Get all the loanDetailsList where loanCloserDate equals to UPDATED_LOAN_CLOSER_DATE
        defaultLoanDetailsShouldNotBeFound("loanCloserDate.equals=" + UPDATED_LOAN_CLOSER_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanCloserDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanCloserDate in DEFAULT_LOAN_CLOSER_DATE or UPDATED_LOAN_CLOSER_DATE
        defaultLoanDetailsShouldBeFound("loanCloserDate.in=" + DEFAULT_LOAN_CLOSER_DATE + "," + UPDATED_LOAN_CLOSER_DATE);

        // Get all the loanDetailsList where loanCloserDate equals to UPDATED_LOAN_CLOSER_DATE
        defaultLoanDetailsShouldNotBeFound("loanCloserDate.in=" + UPDATED_LOAN_CLOSER_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanCloserDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanCloserDate is not null
        defaultLoanDetailsShouldBeFound("loanCloserDate.specified=true");

        // Get all the loanDetailsList where loanCloserDate is null
        defaultLoanDetailsShouldNotBeFound("loanCloserDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanEffectiveDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanEffectiveDate equals to DEFAULT_LOAN_EFFECTIVE_DATE
        defaultLoanDetailsShouldBeFound("loanEffectiveDate.equals=" + DEFAULT_LOAN_EFFECTIVE_DATE);

        // Get all the loanDetailsList where loanEffectiveDate equals to UPDATED_LOAN_EFFECTIVE_DATE
        defaultLoanDetailsShouldNotBeFound("loanEffectiveDate.equals=" + UPDATED_LOAN_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanEffectiveDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanEffectiveDate in DEFAULT_LOAN_EFFECTIVE_DATE or UPDATED_LOAN_EFFECTIVE_DATE
        defaultLoanDetailsShouldBeFound("loanEffectiveDate.in=" + DEFAULT_LOAN_EFFECTIVE_DATE + "," + UPDATED_LOAN_EFFECTIVE_DATE);

        // Get all the loanDetailsList where loanEffectiveDate equals to UPDATED_LOAN_EFFECTIVE_DATE
        defaultLoanDetailsShouldNotBeFound("loanEffectiveDate.in=" + UPDATED_LOAN_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanEffectiveDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanEffectiveDate is not null
        defaultLoanDetailsShouldBeFound("loanEffectiveDate.specified=true");

        // Get all the loanDetailsList where loanEffectiveDate is null
        defaultLoanDetailsShouldNotBeFound("loanEffectiveDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanClassificationIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanClassification equals to DEFAULT_LOAN_CLASSIFICATION
        defaultLoanDetailsShouldBeFound("loanClassification.equals=" + DEFAULT_LOAN_CLASSIFICATION);

        // Get all the loanDetailsList where loanClassification equals to UPDATED_LOAN_CLASSIFICATION
        defaultLoanDetailsShouldNotBeFound("loanClassification.equals=" + UPDATED_LOAN_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanClassificationIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanClassification in DEFAULT_LOAN_CLASSIFICATION or UPDATED_LOAN_CLASSIFICATION
        defaultLoanDetailsShouldBeFound("loanClassification.in=" + DEFAULT_LOAN_CLASSIFICATION + "," + UPDATED_LOAN_CLASSIFICATION);

        // Get all the loanDetailsList where loanClassification equals to UPDATED_LOAN_CLASSIFICATION
        defaultLoanDetailsShouldNotBeFound("loanClassification.in=" + UPDATED_LOAN_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanClassificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanClassification is not null
        defaultLoanDetailsShouldBeFound("loanClassification.specified=true");

        // Get all the loanDetailsList where loanClassification is null
        defaultLoanDetailsShouldNotBeFound("loanClassification.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByResolutionNoIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where resolutionNo equals to DEFAULT_RESOLUTION_NO
        defaultLoanDetailsShouldBeFound("resolutionNo.equals=" + DEFAULT_RESOLUTION_NO);

        // Get all the loanDetailsList where resolutionNo equals to UPDATED_RESOLUTION_NO
        defaultLoanDetailsShouldNotBeFound("resolutionNo.equals=" + UPDATED_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByResolutionNoIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where resolutionNo in DEFAULT_RESOLUTION_NO or UPDATED_RESOLUTION_NO
        defaultLoanDetailsShouldBeFound("resolutionNo.in=" + DEFAULT_RESOLUTION_NO + "," + UPDATED_RESOLUTION_NO);

        // Get all the loanDetailsList where resolutionNo equals to UPDATED_RESOLUTION_NO
        defaultLoanDetailsShouldNotBeFound("resolutionNo.in=" + UPDATED_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByResolutionNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where resolutionNo is not null
        defaultLoanDetailsShouldBeFound("resolutionNo.specified=true");

        // Get all the loanDetailsList where resolutionNo is null
        defaultLoanDetailsShouldNotBeFound("resolutionNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByResolutionNoContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where resolutionNo contains DEFAULT_RESOLUTION_NO
        defaultLoanDetailsShouldBeFound("resolutionNo.contains=" + DEFAULT_RESOLUTION_NO);

        // Get all the loanDetailsList where resolutionNo contains UPDATED_RESOLUTION_NO
        defaultLoanDetailsShouldNotBeFound("resolutionNo.contains=" + UPDATED_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByResolutionNoNotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where resolutionNo does not contain DEFAULT_RESOLUTION_NO
        defaultLoanDetailsShouldNotBeFound("resolutionNo.doesNotContain=" + DEFAULT_RESOLUTION_NO);

        // Get all the loanDetailsList where resolutionNo does not contain UPDATED_RESOLUTION_NO
        defaultLoanDetailsShouldBeFound("resolutionNo.doesNotContain=" + UPDATED_RESOLUTION_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByResolutionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where resolutionDate equals to DEFAULT_RESOLUTION_DATE
        defaultLoanDetailsShouldBeFound("resolutionDate.equals=" + DEFAULT_RESOLUTION_DATE);

        // Get all the loanDetailsList where resolutionDate equals to UPDATED_RESOLUTION_DATE
        defaultLoanDetailsShouldNotBeFound("resolutionDate.equals=" + UPDATED_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByResolutionDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where resolutionDate in DEFAULT_RESOLUTION_DATE or UPDATED_RESOLUTION_DATE
        defaultLoanDetailsShouldBeFound("resolutionDate.in=" + DEFAULT_RESOLUTION_DATE + "," + UPDATED_RESOLUTION_DATE);

        // Get all the loanDetailsList where resolutionDate equals to UPDATED_RESOLUTION_DATE
        defaultLoanDetailsShouldNotBeFound("resolutionDate.in=" + UPDATED_RESOLUTION_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByResolutionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where resolutionDate is not null
        defaultLoanDetailsShouldBeFound("resolutionDate.specified=true");

        // Get all the loanDetailsList where resolutionDate is null
        defaultLoanDetailsShouldNotBeFound("resolutionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByCostOfInvestmentIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where costOfInvestment equals to DEFAULT_COST_OF_INVESTMENT
        defaultLoanDetailsShouldBeFound("costOfInvestment.equals=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDetailsList where costOfInvestment equals to UPDATED_COST_OF_INVESTMENT
        defaultLoanDetailsShouldNotBeFound("costOfInvestment.equals=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByCostOfInvestmentIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where costOfInvestment in DEFAULT_COST_OF_INVESTMENT or UPDATED_COST_OF_INVESTMENT
        defaultLoanDetailsShouldBeFound("costOfInvestment.in=" + DEFAULT_COST_OF_INVESTMENT + "," + UPDATED_COST_OF_INVESTMENT);

        // Get all the loanDetailsList where costOfInvestment equals to UPDATED_COST_OF_INVESTMENT
        defaultLoanDetailsShouldNotBeFound("costOfInvestment.in=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByCostOfInvestmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where costOfInvestment is not null
        defaultLoanDetailsShouldBeFound("costOfInvestment.specified=true");

        // Get all the loanDetailsList where costOfInvestment is null
        defaultLoanDetailsShouldNotBeFound("costOfInvestment.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByCostOfInvestmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where costOfInvestment is greater than or equal to DEFAULT_COST_OF_INVESTMENT
        defaultLoanDetailsShouldBeFound("costOfInvestment.greaterThanOrEqual=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDetailsList where costOfInvestment is greater than or equal to UPDATED_COST_OF_INVESTMENT
        defaultLoanDetailsShouldNotBeFound("costOfInvestment.greaterThanOrEqual=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByCostOfInvestmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where costOfInvestment is less than or equal to DEFAULT_COST_OF_INVESTMENT
        defaultLoanDetailsShouldBeFound("costOfInvestment.lessThanOrEqual=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDetailsList where costOfInvestment is less than or equal to SMALLER_COST_OF_INVESTMENT
        defaultLoanDetailsShouldNotBeFound("costOfInvestment.lessThanOrEqual=" + SMALLER_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByCostOfInvestmentIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where costOfInvestment is less than DEFAULT_COST_OF_INVESTMENT
        defaultLoanDetailsShouldNotBeFound("costOfInvestment.lessThan=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDetailsList where costOfInvestment is less than UPDATED_COST_OF_INVESTMENT
        defaultLoanDetailsShouldBeFound("costOfInvestment.lessThan=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByCostOfInvestmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where costOfInvestment is greater than DEFAULT_COST_OF_INVESTMENT
        defaultLoanDetailsShouldNotBeFound("costOfInvestment.greaterThan=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDetailsList where costOfInvestment is greater than SMALLER_COST_OF_INVESTMENT
        defaultLoanDetailsShouldBeFound("costOfInvestment.greaterThan=" + SMALLER_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanBenefitingAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanBenefitingArea equals to DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldBeFound("loanBenefitingArea.equals=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanDetailsList where loanBenefitingArea equals to UPDATED_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldNotBeFound("loanBenefitingArea.equals=" + UPDATED_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanBenefitingAreaIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanBenefitingArea in DEFAULT_LOAN_BENEFITING_AREA or UPDATED_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldBeFound("loanBenefitingArea.in=" + DEFAULT_LOAN_BENEFITING_AREA + "," + UPDATED_LOAN_BENEFITING_AREA);

        // Get all the loanDetailsList where loanBenefitingArea equals to UPDATED_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldNotBeFound("loanBenefitingArea.in=" + UPDATED_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanBenefitingAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanBenefitingArea is not null
        defaultLoanDetailsShouldBeFound("loanBenefitingArea.specified=true");

        // Get all the loanDetailsList where loanBenefitingArea is null
        defaultLoanDetailsShouldNotBeFound("loanBenefitingArea.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanBenefitingAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanBenefitingArea is greater than or equal to DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldBeFound("loanBenefitingArea.greaterThanOrEqual=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanDetailsList where loanBenefitingArea is greater than or equal to UPDATED_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldNotBeFound("loanBenefitingArea.greaterThanOrEqual=" + UPDATED_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanBenefitingAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanBenefitingArea is less than or equal to DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldBeFound("loanBenefitingArea.lessThanOrEqual=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanDetailsList where loanBenefitingArea is less than or equal to SMALLER_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldNotBeFound("loanBenefitingArea.lessThanOrEqual=" + SMALLER_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanBenefitingAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanBenefitingArea is less than DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldNotBeFound("loanBenefitingArea.lessThan=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanDetailsList where loanBenefitingArea is less than UPDATED_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldBeFound("loanBenefitingArea.lessThan=" + UPDATED_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanBenefitingAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanBenefitingArea is greater than DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldNotBeFound("loanBenefitingArea.greaterThan=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanDetailsList where loanBenefitingArea is greater than SMALLER_LOAN_BENEFITING_AREA
        defaultLoanDetailsShouldBeFound("loanBenefitingArea.greaterThan=" + SMALLER_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDccbLoanNoIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where dccbLoanNo equals to DEFAULT_DCCB_LOAN_NO
        defaultLoanDetailsShouldBeFound("dccbLoanNo.equals=" + DEFAULT_DCCB_LOAN_NO);

        // Get all the loanDetailsList where dccbLoanNo equals to UPDATED_DCCB_LOAN_NO
        defaultLoanDetailsShouldNotBeFound("dccbLoanNo.equals=" + UPDATED_DCCB_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDccbLoanNoIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where dccbLoanNo in DEFAULT_DCCB_LOAN_NO or UPDATED_DCCB_LOAN_NO
        defaultLoanDetailsShouldBeFound("dccbLoanNo.in=" + DEFAULT_DCCB_LOAN_NO + "," + UPDATED_DCCB_LOAN_NO);

        // Get all the loanDetailsList where dccbLoanNo equals to UPDATED_DCCB_LOAN_NO
        defaultLoanDetailsShouldNotBeFound("dccbLoanNo.in=" + UPDATED_DCCB_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDccbLoanNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where dccbLoanNo is not null
        defaultLoanDetailsShouldBeFound("dccbLoanNo.specified=true");

        // Get all the loanDetailsList where dccbLoanNo is null
        defaultLoanDetailsShouldNotBeFound("dccbLoanNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDccbLoanNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where dccbLoanNo is greater than or equal to DEFAULT_DCCB_LOAN_NO
        defaultLoanDetailsShouldBeFound("dccbLoanNo.greaterThanOrEqual=" + DEFAULT_DCCB_LOAN_NO);

        // Get all the loanDetailsList where dccbLoanNo is greater than or equal to UPDATED_DCCB_LOAN_NO
        defaultLoanDetailsShouldNotBeFound("dccbLoanNo.greaterThanOrEqual=" + UPDATED_DCCB_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDccbLoanNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where dccbLoanNo is less than or equal to DEFAULT_DCCB_LOAN_NO
        defaultLoanDetailsShouldBeFound("dccbLoanNo.lessThanOrEqual=" + DEFAULT_DCCB_LOAN_NO);

        // Get all the loanDetailsList where dccbLoanNo is less than or equal to SMALLER_DCCB_LOAN_NO
        defaultLoanDetailsShouldNotBeFound("dccbLoanNo.lessThanOrEqual=" + SMALLER_DCCB_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDccbLoanNoIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where dccbLoanNo is less than DEFAULT_DCCB_LOAN_NO
        defaultLoanDetailsShouldNotBeFound("dccbLoanNo.lessThan=" + DEFAULT_DCCB_LOAN_NO);

        // Get all the loanDetailsList where dccbLoanNo is less than UPDATED_DCCB_LOAN_NO
        defaultLoanDetailsShouldBeFound("dccbLoanNo.lessThan=" + UPDATED_DCCB_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDccbLoanNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where dccbLoanNo is greater than DEFAULT_DCCB_LOAN_NO
        defaultLoanDetailsShouldNotBeFound("dccbLoanNo.greaterThan=" + DEFAULT_DCCB_LOAN_NO);

        // Get all the loanDetailsList where dccbLoanNo is greater than SMALLER_DCCB_LOAN_NO
        defaultLoanDetailsShouldBeFound("dccbLoanNo.greaterThan=" + SMALLER_DCCB_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDeedNoIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDeedNo equals to DEFAULT_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldBeFound("mortgageDeedNo.equals=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanDetailsList where mortgageDeedNo equals to UPDATED_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldNotBeFound("mortgageDeedNo.equals=" + UPDATED_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDeedNoIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDeedNo in DEFAULT_MORTGAGE_DEED_NO or UPDATED_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldBeFound("mortgageDeedNo.in=" + DEFAULT_MORTGAGE_DEED_NO + "," + UPDATED_MORTGAGE_DEED_NO);

        // Get all the loanDetailsList where mortgageDeedNo equals to UPDATED_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldNotBeFound("mortgageDeedNo.in=" + UPDATED_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDeedNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDeedNo is not null
        defaultLoanDetailsShouldBeFound("mortgageDeedNo.specified=true");

        // Get all the loanDetailsList where mortgageDeedNo is null
        defaultLoanDetailsShouldNotBeFound("mortgageDeedNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDeedNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDeedNo is greater than or equal to DEFAULT_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldBeFound("mortgageDeedNo.greaterThanOrEqual=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanDetailsList where mortgageDeedNo is greater than or equal to UPDATED_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldNotBeFound("mortgageDeedNo.greaterThanOrEqual=" + UPDATED_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDeedNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDeedNo is less than or equal to DEFAULT_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldBeFound("mortgageDeedNo.lessThanOrEqual=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanDetailsList where mortgageDeedNo is less than or equal to SMALLER_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldNotBeFound("mortgageDeedNo.lessThanOrEqual=" + SMALLER_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDeedNoIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDeedNo is less than DEFAULT_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldNotBeFound("mortgageDeedNo.lessThan=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanDetailsList where mortgageDeedNo is less than UPDATED_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldBeFound("mortgageDeedNo.lessThan=" + UPDATED_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDeedNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDeedNo is greater than DEFAULT_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldNotBeFound("mortgageDeedNo.greaterThan=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanDetailsList where mortgageDeedNo is greater than SMALLER_MORTGAGE_DEED_NO
        defaultLoanDetailsShouldBeFound("mortgageDeedNo.greaterThan=" + SMALLER_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDate equals to DEFAULT_MORTGAGE_DATE
        defaultLoanDetailsShouldBeFound("mortgageDate.equals=" + DEFAULT_MORTGAGE_DATE);

        // Get all the loanDetailsList where mortgageDate equals to UPDATED_MORTGAGE_DATE
        defaultLoanDetailsShouldNotBeFound("mortgageDate.equals=" + UPDATED_MORTGAGE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDate in DEFAULT_MORTGAGE_DATE or UPDATED_MORTGAGE_DATE
        defaultLoanDetailsShouldBeFound("mortgageDate.in=" + DEFAULT_MORTGAGE_DATE + "," + UPDATED_MORTGAGE_DATE);

        // Get all the loanDetailsList where mortgageDate equals to UPDATED_MORTGAGE_DATE
        defaultLoanDetailsShouldNotBeFound("mortgageDate.in=" + UPDATED_MORTGAGE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMortgageDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where mortgageDate is not null
        defaultLoanDetailsShouldBeFound("mortgageDate.specified=true");

        // Get all the loanDetailsList where mortgageDate is null
        defaultLoanDetailsShouldNotBeFound("mortgageDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByExtentMorgageValueIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where extentMorgageValue equals to DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldBeFound("extentMorgageValue.equals=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanDetailsList where extentMorgageValue equals to UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldNotBeFound("extentMorgageValue.equals=" + UPDATED_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByExtentMorgageValueIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where extentMorgageValue in DEFAULT_EXTENT_MORGAGE_VALUE or UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldBeFound("extentMorgageValue.in=" + DEFAULT_EXTENT_MORGAGE_VALUE + "," + UPDATED_EXTENT_MORGAGE_VALUE);

        // Get all the loanDetailsList where extentMorgageValue equals to UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldNotBeFound("extentMorgageValue.in=" + UPDATED_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByExtentMorgageValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where extentMorgageValue is not null
        defaultLoanDetailsShouldBeFound("extentMorgageValue.specified=true");

        // Get all the loanDetailsList where extentMorgageValue is null
        defaultLoanDetailsShouldNotBeFound("extentMorgageValue.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByExtentMorgageValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where extentMorgageValue is greater than or equal to DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldBeFound("extentMorgageValue.greaterThanOrEqual=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanDetailsList where extentMorgageValue is greater than or equal to UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldNotBeFound("extentMorgageValue.greaterThanOrEqual=" + UPDATED_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByExtentMorgageValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where extentMorgageValue is less than or equal to DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldBeFound("extentMorgageValue.lessThanOrEqual=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanDetailsList where extentMorgageValue is less than or equal to SMALLER_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldNotBeFound("extentMorgageValue.lessThanOrEqual=" + SMALLER_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByExtentMorgageValueIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where extentMorgageValue is less than DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldNotBeFound("extentMorgageValue.lessThan=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanDetailsList where extentMorgageValue is less than UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldBeFound("extentMorgageValue.lessThan=" + UPDATED_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByExtentMorgageValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where extentMorgageValue is greater than DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldNotBeFound("extentMorgageValue.greaterThan=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanDetailsList where extentMorgageValue is greater than SMALLER_EXTENT_MORGAGE_VALUE
        defaultLoanDetailsShouldBeFound("extentMorgageValue.greaterThan=" + SMALLER_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByParentAccHeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where parentAccHeadCode equals to DEFAULT_PARENT_ACC_HEAD_CODE
        defaultLoanDetailsShouldBeFound("parentAccHeadCode.equals=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the loanDetailsList where parentAccHeadCode equals to UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanDetailsShouldNotBeFound("parentAccHeadCode.equals=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByParentAccHeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where parentAccHeadCode in DEFAULT_PARENT_ACC_HEAD_CODE or UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanDetailsShouldBeFound("parentAccHeadCode.in=" + DEFAULT_PARENT_ACC_HEAD_CODE + "," + UPDATED_PARENT_ACC_HEAD_CODE);

        // Get all the loanDetailsList where parentAccHeadCode equals to UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanDetailsShouldNotBeFound("parentAccHeadCode.in=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByParentAccHeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where parentAccHeadCode is not null
        defaultLoanDetailsShouldBeFound("parentAccHeadCode.specified=true");

        // Get all the loanDetailsList where parentAccHeadCode is null
        defaultLoanDetailsShouldNotBeFound("parentAccHeadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByParentAccHeadCodeContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where parentAccHeadCode contains DEFAULT_PARENT_ACC_HEAD_CODE
        defaultLoanDetailsShouldBeFound("parentAccHeadCode.contains=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the loanDetailsList where parentAccHeadCode contains UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanDetailsShouldNotBeFound("parentAccHeadCode.contains=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByParentAccHeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where parentAccHeadCode does not contain DEFAULT_PARENT_ACC_HEAD_CODE
        defaultLoanDetailsShouldNotBeFound("parentAccHeadCode.doesNotContain=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the loanDetailsList where parentAccHeadCode does not contain UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanDetailsShouldBeFound("parentAccHeadCode.doesNotContain=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountName equals to DEFAULT_LOAN_ACCOUNT_NAME
        defaultLoanDetailsShouldBeFound("loanAccountName.equals=" + DEFAULT_LOAN_ACCOUNT_NAME);

        // Get all the loanDetailsList where loanAccountName equals to UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanDetailsShouldNotBeFound("loanAccountName.equals=" + UPDATED_LOAN_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountName in DEFAULT_LOAN_ACCOUNT_NAME or UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanDetailsShouldBeFound("loanAccountName.in=" + DEFAULT_LOAN_ACCOUNT_NAME + "," + UPDATED_LOAN_ACCOUNT_NAME);

        // Get all the loanDetailsList where loanAccountName equals to UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanDetailsShouldNotBeFound("loanAccountName.in=" + UPDATED_LOAN_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountName is not null
        defaultLoanDetailsShouldBeFound("loanAccountName.specified=true");

        // Get all the loanDetailsList where loanAccountName is null
        defaultLoanDetailsShouldNotBeFound("loanAccountName.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNameContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountName contains DEFAULT_LOAN_ACCOUNT_NAME
        defaultLoanDetailsShouldBeFound("loanAccountName.contains=" + DEFAULT_LOAN_ACCOUNT_NAME);

        // Get all the loanDetailsList where loanAccountName contains UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanDetailsShouldNotBeFound("loanAccountName.contains=" + UPDATED_LOAN_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where loanAccountName does not contain DEFAULT_LOAN_ACCOUNT_NAME
        defaultLoanDetailsShouldNotBeFound("loanAccountName.doesNotContain=" + DEFAULT_LOAN_ACCOUNT_NAME);

        // Get all the loanDetailsList where loanAccountName does not contain UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanDetailsShouldBeFound("loanAccountName.doesNotContain=" + UPDATED_LOAN_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementAmt equals to DEFAULT_DISBURSEMENT_AMT
        defaultLoanDetailsShouldBeFound("disbursementAmt.equals=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanDetailsList where disbursementAmt equals to UPDATED_DISBURSEMENT_AMT
        defaultLoanDetailsShouldNotBeFound("disbursementAmt.equals=" + UPDATED_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementAmt in DEFAULT_DISBURSEMENT_AMT or UPDATED_DISBURSEMENT_AMT
        defaultLoanDetailsShouldBeFound("disbursementAmt.in=" + DEFAULT_DISBURSEMENT_AMT + "," + UPDATED_DISBURSEMENT_AMT);

        // Get all the loanDetailsList where disbursementAmt equals to UPDATED_DISBURSEMENT_AMT
        defaultLoanDetailsShouldNotBeFound("disbursementAmt.in=" + UPDATED_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementAmt is not null
        defaultLoanDetailsShouldBeFound("disbursementAmt.specified=true");

        // Get all the loanDetailsList where disbursementAmt is null
        defaultLoanDetailsShouldNotBeFound("disbursementAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementAmt is greater than or equal to DEFAULT_DISBURSEMENT_AMT
        defaultLoanDetailsShouldBeFound("disbursementAmt.greaterThanOrEqual=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanDetailsList where disbursementAmt is greater than or equal to UPDATED_DISBURSEMENT_AMT
        defaultLoanDetailsShouldNotBeFound("disbursementAmt.greaterThanOrEqual=" + UPDATED_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementAmt is less than or equal to DEFAULT_DISBURSEMENT_AMT
        defaultLoanDetailsShouldBeFound("disbursementAmt.lessThanOrEqual=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanDetailsList where disbursementAmt is less than or equal to SMALLER_DISBURSEMENT_AMT
        defaultLoanDetailsShouldNotBeFound("disbursementAmt.lessThanOrEqual=" + SMALLER_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementAmt is less than DEFAULT_DISBURSEMENT_AMT
        defaultLoanDetailsShouldNotBeFound("disbursementAmt.lessThan=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanDetailsList where disbursementAmt is less than UPDATED_DISBURSEMENT_AMT
        defaultLoanDetailsShouldBeFound("disbursementAmt.lessThan=" + UPDATED_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementAmt is greater than DEFAULT_DISBURSEMENT_AMT
        defaultLoanDetailsShouldNotBeFound("disbursementAmt.greaterThan=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanDetailsList where disbursementAmt is greater than SMALLER_DISBURSEMENT_AMT
        defaultLoanDetailsShouldBeFound("disbursementAmt.greaterThan=" + SMALLER_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementStatus equals to DEFAULT_DISBURSEMENT_STATUS
        defaultLoanDetailsShouldBeFound("disbursementStatus.equals=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanDetailsList where disbursementStatus equals to UPDATED_DISBURSEMENT_STATUS
        defaultLoanDetailsShouldNotBeFound("disbursementStatus.equals=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementStatus in DEFAULT_DISBURSEMENT_STATUS or UPDATED_DISBURSEMENT_STATUS
        defaultLoanDetailsShouldBeFound("disbursementStatus.in=" + DEFAULT_DISBURSEMENT_STATUS + "," + UPDATED_DISBURSEMENT_STATUS);

        // Get all the loanDetailsList where disbursementStatus equals to UPDATED_DISBURSEMENT_STATUS
        defaultLoanDetailsShouldNotBeFound("disbursementStatus.in=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementStatus is not null
        defaultLoanDetailsShouldBeFound("disbursementStatus.specified=true");

        // Get all the loanDetailsList where disbursementStatus is null
        defaultLoanDetailsShouldNotBeFound("disbursementStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementStatusContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementStatus contains DEFAULT_DISBURSEMENT_STATUS
        defaultLoanDetailsShouldBeFound("disbursementStatus.contains=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanDetailsList where disbursementStatus contains UPDATED_DISBURSEMENT_STATUS
        defaultLoanDetailsShouldNotBeFound("disbursementStatus.contains=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByDisbursementStatusNotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where disbursementStatus does not contain DEFAULT_DISBURSEMENT_STATUS
        defaultLoanDetailsShouldNotBeFound("disbursementStatus.doesNotContain=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanDetailsList where disbursementStatus does not contain UPDATED_DISBURSEMENT_STATUS
        defaultLoanDetailsShouldBeFound("disbursementStatus.doesNotContain=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where year equals to DEFAULT_YEAR
        defaultLoanDetailsShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the loanDetailsList where year equals to UPDATED_YEAR
        defaultLoanDetailsShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultLoanDetailsShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the loanDetailsList where year equals to UPDATED_YEAR
        defaultLoanDetailsShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where year is not null
        defaultLoanDetailsShouldBeFound("year.specified=true");

        // Get all the loanDetailsList where year is null
        defaultLoanDetailsShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByYearContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where year contains DEFAULT_YEAR
        defaultLoanDetailsShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the loanDetailsList where year contains UPDATED_YEAR
        defaultLoanDetailsShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where year does not contain DEFAULT_YEAR
        defaultLoanDetailsShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the loanDetailsList where year does not contain UPDATED_YEAR
        defaultLoanDetailsShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLoanDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the loanDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLoanDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the loanDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where lastModified is not null
        defaultLoanDetailsShouldBeFound("lastModified.specified=true");

        // Get all the loanDetailsList where lastModified is null
        defaultLoanDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLoanDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLoanDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the loanDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where lastModifiedBy is not null
        defaultLoanDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the loanDetailsList where lastModifiedBy is null
        defaultLoanDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLoanDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLoanDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLoanDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLoanDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLoanDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLoanDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the loanDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField1 is not null
        defaultLoanDetailsShouldBeFound("freeField1.specified=true");

        // Get all the loanDetailsList where freeField1 is null
        defaultLoanDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLoanDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLoanDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLoanDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLoanDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLoanDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLoanDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the loanDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField2 is not null
        defaultLoanDetailsShouldBeFound("freeField2.specified=true");

        // Get all the loanDetailsList where freeField2 is null
        defaultLoanDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLoanDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLoanDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLoanDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLoanDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLoanDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLoanDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the loanDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField3 is not null
        defaultLoanDetailsShouldBeFound("freeField3.specified=true");

        // Get all the loanDetailsList where freeField3 is null
        defaultLoanDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLoanDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLoanDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        // Get all the loanDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLoanDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLoanDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanDemandIsEqualToSomething() throws Exception {
        LoanDemand loanDemand;
        if (TestUtil.findAll(em, LoanDemand.class).isEmpty()) {
            loanDetailsRepository.saveAndFlush(loanDetails);
            loanDemand = LoanDemandResourceIT.createEntity(em);
        } else {
            loanDemand = TestUtil.findAll(em, LoanDemand.class).get(0);
        }
        em.persist(loanDemand);
        em.flush();
        loanDetails.setLoanDemand(loanDemand);
        loanDetailsRepository.saveAndFlush(loanDetails);
        Long loanDemandId = loanDemand.getId();

        // Get all the loanDetailsList where loanDemand equals to loanDemandId
        defaultLoanDetailsShouldBeFound("loanDemandId.equals=" + loanDemandId);

        // Get all the loanDetailsList where loanDemand equals to (loanDemandId + 1)
        defaultLoanDetailsShouldNotBeFound("loanDemandId.equals=" + (loanDemandId + 1));
    }

    @Test
    @Transactional
    void getAllLoanDetailsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            loanDetailsRepository.saveAndFlush(loanDetails);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        loanDetails.setMember(member);
        loanDetailsRepository.saveAndFlush(loanDetails);
        Long memberId = member.getId();

        // Get all the loanDetailsList where member equals to memberId
        defaultLoanDetailsShouldBeFound("memberId.equals=" + memberId);

        // Get all the loanDetailsList where member equals to (memberId + 1)
        defaultLoanDetailsShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    @Test
    @Transactional
    void getAllLoanDetailsByLoanDemandIsEqualToSomething() throws Exception {
        LoanDemand loanDemand;
        if (TestUtil.findAll(em, LoanDemand.class).isEmpty()) {
            loanDetailsRepository.saveAndFlush(loanDetails);
            loanDemand = LoanDemandResourceIT.createEntity(em);
        } else {
            loanDemand = TestUtil.findAll(em, LoanDemand.class).get(0);
        }
        em.persist(loanDemand);
        em.flush();
        loanDetails.setLoanDemand(loanDemand);
        loanDetailsRepository.saveAndFlush(loanDetails);
        Long loanDemandId = loanDemand.getId();

        // Get all the loanDetailsList where loanDemand equals to loanDemandId
        defaultLoanDetailsShouldBeFound("loanDemandId.equals=" + loanDemandId);

        // Get all the loanDetailsList where loanDemand equals to (loanDemandId + 1)
        defaultLoanDetailsShouldNotBeFound("loanDemandId.equals=" + (loanDemandId + 1));
    }

    @Test
    @Transactional
    void getAllLoanDetailsBySocietyLoanProductIsEqualToSomething() throws Exception {
        SocietyLoanProduct societyLoanProduct;
        if (TestUtil.findAll(em, SocietyLoanProduct.class).isEmpty()) {
            loanDetailsRepository.saveAndFlush(loanDetails);
            societyLoanProduct = SocietyLoanProductResourceIT.createEntity(em);
        } else {
            societyLoanProduct = TestUtil.findAll(em, SocietyLoanProduct.class).get(0);
        }
        em.persist(societyLoanProduct);
        em.flush();
        loanDetails.setSocietyLoanProduct(societyLoanProduct);
        loanDetailsRepository.saveAndFlush(loanDetails);
        Long societyLoanProductId = societyLoanProduct.getId();

        // Get all the loanDetailsList where societyLoanProduct equals to societyLoanProductId
        defaultLoanDetailsShouldBeFound("societyLoanProductId.equals=" + societyLoanProductId);

        // Get all the loanDetailsList where societyLoanProduct equals to (societyLoanProductId + 1)
        defaultLoanDetailsShouldNotBeFound("societyLoanProductId.equals=" + (societyLoanProductId + 1));
    }

    @Test
    @Transactional
    void getAllLoanDetailsByBankDhoranDetailsIsEqualToSomething() throws Exception {
        BankDhoranDetails bankDhoranDetails;
        if (TestUtil.findAll(em, BankDhoranDetails.class).isEmpty()) {
            loanDetailsRepository.saveAndFlush(loanDetails);
            bankDhoranDetails = BankDhoranDetailsResourceIT.createEntity(em);
        } else {
            bankDhoranDetails = TestUtil.findAll(em, BankDhoranDetails.class).get(0);
        }
        em.persist(bankDhoranDetails);
        em.flush();
        loanDetails.setBankDhoranDetails(bankDhoranDetails);
        loanDetailsRepository.saveAndFlush(loanDetails);
        Long bankDhoranDetailsId = bankDhoranDetails.getId();

        // Get all the loanDetailsList where bankDhoranDetails equals to bankDhoranDetailsId
        defaultLoanDetailsShouldBeFound("bankDhoranDetailsId.equals=" + bankDhoranDetailsId);

        // Get all the loanDetailsList where bankDhoranDetails equals to (bankDhoranDetailsId + 1)
        defaultLoanDetailsShouldNotBeFound("bankDhoranDetailsId.equals=" + (bankDhoranDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanDetailsShouldBeFound(String filter) throws Exception {
        restLoanDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanAccountNo").value(hasItem(DEFAULT_LOAN_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].loanType").value(hasItem(DEFAULT_LOAN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loanStartDate").value(hasItem(DEFAULT_LOAN_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanEndDate").value(hasItem(DEFAULT_LOAN_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanPlannedClosureDate").value(hasItem(DEFAULT_LOAN_PLANNED_CLOSURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanCloserDate").value(hasItem(DEFAULT_LOAN_CLOSER_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanEffectiveDate").value(hasItem(DEFAULT_LOAN_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanClassification").value(hasItem(DEFAULT_LOAN_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].resolutionNo").value(hasItem(DEFAULT_RESOLUTION_NO)))
            .andExpect(jsonPath("$.[*].resolutionDate").value(hasItem(DEFAULT_RESOLUTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].costOfInvestment").value(hasItem(DEFAULT_COST_OF_INVESTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanBenefitingArea").value(hasItem(DEFAULT_LOAN_BENEFITING_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].dccbLoanNo").value(hasItem(DEFAULT_DCCB_LOAN_NO.intValue())))
            .andExpect(jsonPath("$.[*].mortgageDeedNo").value(hasItem(DEFAULT_MORTGAGE_DEED_NO.intValue())))
            .andExpect(jsonPath("$.[*].mortgageDate").value(hasItem(DEFAULT_MORTGAGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].extentMorgageValue").value(hasItem(DEFAULT_EXTENT_MORGAGE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].parentAccHeadCode").value(hasItem(DEFAULT_PARENT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].loanAccountName").value(hasItem(DEFAULT_LOAN_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].disbursementAmt").value(hasItem(DEFAULT_DISBURSEMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].disbursementStatus").value(hasItem(DEFAULT_DISBURSEMENT_STATUS)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restLoanDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanDetailsShouldNotBeFound(String filter) throws Exception {
        restLoanDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanDetails() throws Exception {
        // Get the loanDetails
        restLoanDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoanDetails() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        int databaseSizeBeforeUpdate = loanDetailsRepository.findAll().size();

        // Update the loanDetails
        LoanDetails updatedLoanDetails = loanDetailsRepository.findById(loanDetails.getId()).get();
        // Disconnect from session so that the updates on updatedLoanDetails are not directly saved in db
        em.detach(updatedLoanDetails);
        updatedLoanDetails
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanAccountNo(UPDATED_LOAN_ACCOUNT_NO)
            .loanType(UPDATED_LOAN_TYPE)
            .status(UPDATED_STATUS)
            .loanStartDate(UPDATED_LOAN_START_DATE)
            .loanEndDate(UPDATED_LOAN_END_DATE)
            .loanPlannedClosureDate(UPDATED_LOAN_PLANNED_CLOSURE_DATE)
            .loanCloserDate(UPDATED_LOAN_CLOSER_DATE)
            .loanEffectiveDate(UPDATED_LOAN_EFFECTIVE_DATE)
            .loanClassification(UPDATED_LOAN_CLASSIFICATION)
            .resolutionNo(UPDATED_RESOLUTION_NO)
            .resolutionDate(UPDATED_RESOLUTION_DATE)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .loanBenefitingArea(UPDATED_LOAN_BENEFITING_AREA)
            .dccbLoanNo(UPDATED_DCCB_LOAN_NO)
            .mortgageDeedNo(UPDATED_MORTGAGE_DEED_NO)
            .mortgageDate(UPDATED_MORTGAGE_DATE)
            .extentMorgageValue(UPDATED_EXTENT_MORGAGE_VALUE)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .loanAccountName(UPDATED_LOAN_ACCOUNT_NAME)
            .disbursementAmt(UPDATED_DISBURSEMENT_AMT)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        LoanDetailsDTO loanDetailsDTO = loanDetailsMapper.toDto(updatedLoanDetails);

        restLoanDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanDetails testLoanDetails = loanDetailsList.get(loanDetailsList.size() - 1);
        assertThat(testLoanDetails.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testLoanDetails.getLoanAccountNo()).isEqualTo(UPDATED_LOAN_ACCOUNT_NO);
        assertThat(testLoanDetails.getLoanType()).isEqualTo(UPDATED_LOAN_TYPE);
        assertThat(testLoanDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanDetails.getLoanStartDate()).isEqualTo(UPDATED_LOAN_START_DATE);
        assertThat(testLoanDetails.getLoanEndDate()).isEqualTo(UPDATED_LOAN_END_DATE);
        assertThat(testLoanDetails.getLoanPlannedClosureDate()).isEqualTo(UPDATED_LOAN_PLANNED_CLOSURE_DATE);
        assertThat(testLoanDetails.getLoanCloserDate()).isEqualTo(UPDATED_LOAN_CLOSER_DATE);
        assertThat(testLoanDetails.getLoanEffectiveDate()).isEqualTo(UPDATED_LOAN_EFFECTIVE_DATE);
        assertThat(testLoanDetails.getLoanClassification()).isEqualTo(UPDATED_LOAN_CLASSIFICATION);
        assertThat(testLoanDetails.getResolutionNo()).isEqualTo(UPDATED_RESOLUTION_NO);
        assertThat(testLoanDetails.getResolutionDate()).isEqualTo(UPDATED_RESOLUTION_DATE);
        assertThat(testLoanDetails.getCostOfInvestment()).isEqualTo(UPDATED_COST_OF_INVESTMENT);
        assertThat(testLoanDetails.getLoanBenefitingArea()).isEqualTo(UPDATED_LOAN_BENEFITING_AREA);
        assertThat(testLoanDetails.getDccbLoanNo()).isEqualTo(UPDATED_DCCB_LOAN_NO);
        assertThat(testLoanDetails.getMortgageDeedNo()).isEqualTo(UPDATED_MORTGAGE_DEED_NO);
        assertThat(testLoanDetails.getMortgageDate()).isEqualTo(UPDATED_MORTGAGE_DATE);
        assertThat(testLoanDetails.getExtentMorgageValue()).isEqualTo(UPDATED_EXTENT_MORGAGE_VALUE);
        assertThat(testLoanDetails.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testLoanDetails.getLoanAccountName()).isEqualTo(UPDATED_LOAN_ACCOUNT_NAME);
        assertThat(testLoanDetails.getDisbursementAmt()).isEqualTo(UPDATED_DISBURSEMENT_AMT);
        assertThat(testLoanDetails.getDisbursementStatus()).isEqualTo(UPDATED_DISBURSEMENT_STATUS);
        assertThat(testLoanDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingLoanDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDetailsRepository.findAll().size();
        loanDetails.setId(count.incrementAndGet());

        // Create the LoanDetails
        LoanDetailsDTO loanDetailsDTO = loanDetailsMapper.toDto(loanDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDetailsRepository.findAll().size();
        loanDetails.setId(count.incrementAndGet());

        // Create the LoanDetails
        LoanDetailsDTO loanDetailsDTO = loanDetailsMapper.toDto(loanDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDetailsRepository.findAll().size();
        loanDetails.setId(count.incrementAndGet());

        // Create the LoanDetails
        LoanDetailsDTO loanDetailsDTO = loanDetailsMapper.toDto(loanDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanDetailsWithPatch() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        int databaseSizeBeforeUpdate = loanDetailsRepository.findAll().size();

        // Update the loanDetails using partial update
        LoanDetails partialUpdatedLoanDetails = new LoanDetails();
        partialUpdatedLoanDetails.setId(loanDetails.getId());

        partialUpdatedLoanDetails
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanAccountNo(UPDATED_LOAN_ACCOUNT_NO)
            .status(UPDATED_STATUS)
            .loanStartDate(UPDATED_LOAN_START_DATE)
            .loanPlannedClosureDate(UPDATED_LOAN_PLANNED_CLOSURE_DATE)
            .loanCloserDate(UPDATED_LOAN_CLOSER_DATE)
            .loanClassification(UPDATED_LOAN_CLASSIFICATION)
            .resolutionNo(UPDATED_RESOLUTION_NO)
            .resolutionDate(UPDATED_RESOLUTION_DATE)
            .loanBenefitingArea(UPDATED_LOAN_BENEFITING_AREA)
            .mortgageDeedNo(UPDATED_MORTGAGE_DEED_NO)
            .extentMorgageValue(UPDATED_EXTENT_MORGAGE_VALUE)
            .year(UPDATED_YEAR)
            .freeField1(UPDATED_FREE_FIELD_1);

        restLoanDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDetails))
            )
            .andExpect(status().isOk());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanDetails testLoanDetails = loanDetailsList.get(loanDetailsList.size() - 1);
        assertThat(testLoanDetails.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testLoanDetails.getLoanAccountNo()).isEqualTo(UPDATED_LOAN_ACCOUNT_NO);
        assertThat(testLoanDetails.getLoanType()).isEqualTo(DEFAULT_LOAN_TYPE);
        assertThat(testLoanDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanDetails.getLoanStartDate()).isEqualTo(UPDATED_LOAN_START_DATE);
        assertThat(testLoanDetails.getLoanEndDate()).isEqualTo(DEFAULT_LOAN_END_DATE);
        assertThat(testLoanDetails.getLoanPlannedClosureDate()).isEqualTo(UPDATED_LOAN_PLANNED_CLOSURE_DATE);
        assertThat(testLoanDetails.getLoanCloserDate()).isEqualTo(UPDATED_LOAN_CLOSER_DATE);
        assertThat(testLoanDetails.getLoanEffectiveDate()).isEqualTo(DEFAULT_LOAN_EFFECTIVE_DATE);
        assertThat(testLoanDetails.getLoanClassification()).isEqualTo(UPDATED_LOAN_CLASSIFICATION);
        assertThat(testLoanDetails.getResolutionNo()).isEqualTo(UPDATED_RESOLUTION_NO);
        assertThat(testLoanDetails.getResolutionDate()).isEqualTo(UPDATED_RESOLUTION_DATE);
        assertThat(testLoanDetails.getCostOfInvestment()).isEqualTo(DEFAULT_COST_OF_INVESTMENT);
        assertThat(testLoanDetails.getLoanBenefitingArea()).isEqualTo(UPDATED_LOAN_BENEFITING_AREA);
        assertThat(testLoanDetails.getDccbLoanNo()).isEqualTo(DEFAULT_DCCB_LOAN_NO);
        assertThat(testLoanDetails.getMortgageDeedNo()).isEqualTo(UPDATED_MORTGAGE_DEED_NO);
        assertThat(testLoanDetails.getMortgageDate()).isEqualTo(DEFAULT_MORTGAGE_DATE);
        assertThat(testLoanDetails.getExtentMorgageValue()).isEqualTo(UPDATED_EXTENT_MORGAGE_VALUE);
        assertThat(testLoanDetails.getParentAccHeadCode()).isEqualTo(DEFAULT_PARENT_ACC_HEAD_CODE);
        assertThat(testLoanDetails.getLoanAccountName()).isEqualTo(DEFAULT_LOAN_ACCOUNT_NAME);
        assertThat(testLoanDetails.getDisbursementAmt()).isEqualTo(DEFAULT_DISBURSEMENT_AMT);
        assertThat(testLoanDetails.getDisbursementStatus()).isEqualTo(DEFAULT_DISBURSEMENT_STATUS);
        assertThat(testLoanDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateLoanDetailsWithPatch() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        int databaseSizeBeforeUpdate = loanDetailsRepository.findAll().size();

        // Update the loanDetails using partial update
        LoanDetails partialUpdatedLoanDetails = new LoanDetails();
        partialUpdatedLoanDetails.setId(loanDetails.getId());

        partialUpdatedLoanDetails
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .loanAccountNo(UPDATED_LOAN_ACCOUNT_NO)
            .loanType(UPDATED_LOAN_TYPE)
            .status(UPDATED_STATUS)
            .loanStartDate(UPDATED_LOAN_START_DATE)
            .loanEndDate(UPDATED_LOAN_END_DATE)
            .loanPlannedClosureDate(UPDATED_LOAN_PLANNED_CLOSURE_DATE)
            .loanCloserDate(UPDATED_LOAN_CLOSER_DATE)
            .loanEffectiveDate(UPDATED_LOAN_EFFECTIVE_DATE)
            .loanClassification(UPDATED_LOAN_CLASSIFICATION)
            .resolutionNo(UPDATED_RESOLUTION_NO)
            .resolutionDate(UPDATED_RESOLUTION_DATE)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .loanBenefitingArea(UPDATED_LOAN_BENEFITING_AREA)
            .dccbLoanNo(UPDATED_DCCB_LOAN_NO)
            .mortgageDeedNo(UPDATED_MORTGAGE_DEED_NO)
            .mortgageDate(UPDATED_MORTGAGE_DATE)
            .extentMorgageValue(UPDATED_EXTENT_MORGAGE_VALUE)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .loanAccountName(UPDATED_LOAN_ACCOUNT_NAME)
            .disbursementAmt(UPDATED_DISBURSEMENT_AMT)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restLoanDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDetails))
            )
            .andExpect(status().isOk());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanDetails testLoanDetails = loanDetailsList.get(loanDetailsList.size() - 1);
        assertThat(testLoanDetails.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testLoanDetails.getLoanAccountNo()).isEqualTo(UPDATED_LOAN_ACCOUNT_NO);
        assertThat(testLoanDetails.getLoanType()).isEqualTo(UPDATED_LOAN_TYPE);
        assertThat(testLoanDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanDetails.getLoanStartDate()).isEqualTo(UPDATED_LOAN_START_DATE);
        assertThat(testLoanDetails.getLoanEndDate()).isEqualTo(UPDATED_LOAN_END_DATE);
        assertThat(testLoanDetails.getLoanPlannedClosureDate()).isEqualTo(UPDATED_LOAN_PLANNED_CLOSURE_DATE);
        assertThat(testLoanDetails.getLoanCloserDate()).isEqualTo(UPDATED_LOAN_CLOSER_DATE);
        assertThat(testLoanDetails.getLoanEffectiveDate()).isEqualTo(UPDATED_LOAN_EFFECTIVE_DATE);
        assertThat(testLoanDetails.getLoanClassification()).isEqualTo(UPDATED_LOAN_CLASSIFICATION);
        assertThat(testLoanDetails.getResolutionNo()).isEqualTo(UPDATED_RESOLUTION_NO);
        assertThat(testLoanDetails.getResolutionDate()).isEqualTo(UPDATED_RESOLUTION_DATE);
        assertThat(testLoanDetails.getCostOfInvestment()).isEqualTo(UPDATED_COST_OF_INVESTMENT);
        assertThat(testLoanDetails.getLoanBenefitingArea()).isEqualTo(UPDATED_LOAN_BENEFITING_AREA);
        assertThat(testLoanDetails.getDccbLoanNo()).isEqualTo(UPDATED_DCCB_LOAN_NO);
        assertThat(testLoanDetails.getMortgageDeedNo()).isEqualTo(UPDATED_MORTGAGE_DEED_NO);
        assertThat(testLoanDetails.getMortgageDate()).isEqualTo(UPDATED_MORTGAGE_DATE);
        assertThat(testLoanDetails.getExtentMorgageValue()).isEqualTo(UPDATED_EXTENT_MORGAGE_VALUE);
        assertThat(testLoanDetails.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testLoanDetails.getLoanAccountName()).isEqualTo(UPDATED_LOAN_ACCOUNT_NAME);
        assertThat(testLoanDetails.getDisbursementAmt()).isEqualTo(UPDATED_DISBURSEMENT_AMT);
        assertThat(testLoanDetails.getDisbursementStatus()).isEqualTo(UPDATED_DISBURSEMENT_STATUS);
        assertThat(testLoanDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingLoanDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDetailsRepository.findAll().size();
        loanDetails.setId(count.incrementAndGet());

        // Create the LoanDetails
        LoanDetailsDTO loanDetailsDTO = loanDetailsMapper.toDto(loanDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDetailsRepository.findAll().size();
        loanDetails.setId(count.incrementAndGet());

        // Create the LoanDetails
        LoanDetailsDTO loanDetailsDTO = loanDetailsMapper.toDto(loanDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDetailsRepository.findAll().size();
        loanDetails.setId(count.incrementAndGet());

        // Create the LoanDetails
        LoanDetailsDTO loanDetailsDTO = loanDetailsMapper.toDto(loanDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loanDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDetails in the database
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanDetails() throws Exception {
        // Initialize the database
        loanDetailsRepository.saveAndFlush(loanDetails);

        int databaseSizeBeforeDelete = loanDetailsRepository.findAll().size();

        // Delete the loanDetails
        restLoanDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanDetails> loanDetailsList = loanDetailsRepository.findAll();
        assertThat(loanDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

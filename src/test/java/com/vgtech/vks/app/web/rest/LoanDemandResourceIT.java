package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.LoanDemand;
import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.domain.MemberLandAssets;
import com.vgtech.vks.app.domain.SocietyCropRegistration;
import com.vgtech.vks.app.domain.SocietyLoanProduct;
import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import com.vgtech.vks.app.repository.LoanDemandRepository;
import com.vgtech.vks.app.service.criteria.LoanDemandCriteria;
import com.vgtech.vks.app.service.dto.LoanDemandDTO;
import com.vgtech.vks.app.service.mapper.LoanDemandMapper;
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
 * Integration tests for the {@link LoanDemandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanDemandResourceIT {

    private static final Double DEFAULT_LOAN_DEMAND_AMOUNT = 1D;
    private static final Double UPDATED_LOAN_DEMAND_AMOUNT = 2D;
    private static final Double SMALLER_LOAN_DEMAND_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_MAX_ALLOWED_AMOUNT = 1D;
    private static final Double UPDATED_MAX_ALLOWED_AMOUNT = 2D;
    private static final Double SMALLER_MAX_ALLOWED_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_ADJUSTED_DEMAND = 1D;
    private static final Double UPDATED_ADJUSTED_DEMAND = 2D;
    private static final Double SMALLER_ADJUSTED_DEMAND = 1D - 1D;

    private static final Double DEFAULT_ANNUAL_INCOME = 1D;
    private static final Double UPDATED_ANNUAL_INCOME = 2D;
    private static final Double SMALLER_ANNUAL_INCOME = 1D - 1D;

    private static final Double DEFAULT_COST_OF_INVESTMENT = 1D;
    private static final Double UPDATED_COST_OF_INVESTMENT = 2D;
    private static final Double SMALLER_COST_OF_INVESTMENT = 1D - 1D;

    private static final Integer DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR = 1;
    private static final Integer UPDATED_DEMANDED_LAND_AREA_IN_HECTOR = 2;
    private static final Integer SMALLER_DEMANDED_LAND_AREA_IN_HECTOR = 1 - 1;

    private static final LoanStatus DEFAULT_STATUS = LoanStatus.APPLIED;
    private static final LoanStatus UPDATED_STATUS = LoanStatus.PENDING;

    private static final String DEFAULT_SEASON_OF_CROP_LOAN = "AAAAAAAAAA";
    private static final String UPDATED_SEASON_OF_CROP_LOAN = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/loan-demands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanDemandRepository loanDemandRepository;

    @Autowired
    private LoanDemandMapper loanDemandMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanDemandMockMvc;

    private LoanDemand loanDemand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDemand createEntity(EntityManager em) {
        LoanDemand loanDemand = new LoanDemand()
            .loanDemandAmount(DEFAULT_LOAN_DEMAND_AMOUNT)
            .maxAllowedAmount(DEFAULT_MAX_ALLOWED_AMOUNT)
            .adjustedDemand(DEFAULT_ADJUSTED_DEMAND)
            .annualIncome(DEFAULT_ANNUAL_INCOME)
            .costOfInvestment(DEFAULT_COST_OF_INVESTMENT)
            .demandedLandAreaInHector(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR)
            .status(DEFAULT_STATUS)
            .seasonOfCropLoan(DEFAULT_SEASON_OF_CROP_LOAN)
            .year(DEFAULT_YEAR)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return loanDemand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDemand createUpdatedEntity(EntityManager em) {
        LoanDemand loanDemand = new LoanDemand()
            .loanDemandAmount(UPDATED_LOAN_DEMAND_AMOUNT)
            .maxAllowedAmount(UPDATED_MAX_ALLOWED_AMOUNT)
            .adjustedDemand(UPDATED_ADJUSTED_DEMAND)
            .annualIncome(UPDATED_ANNUAL_INCOME)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .demandedLandAreaInHector(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR)
            .status(UPDATED_STATUS)
            .seasonOfCropLoan(UPDATED_SEASON_OF_CROP_LOAN)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return loanDemand;
    }

    @BeforeEach
    public void initTest() {
        loanDemand = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanDemand() throws Exception {
        int databaseSizeBeforeCreate = loanDemandRepository.findAll().size();
        // Create the LoanDemand
        LoanDemandDTO loanDemandDTO = loanDemandMapper.toDto(loanDemand);
        restLoanDemandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDemandDTO)))
            .andExpect(status().isCreated());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeCreate + 1);
        LoanDemand testLoanDemand = loanDemandList.get(loanDemandList.size() - 1);
        assertThat(testLoanDemand.getLoanDemandAmount()).isEqualTo(DEFAULT_LOAN_DEMAND_AMOUNT);
        assertThat(testLoanDemand.getMaxAllowedAmount()).isEqualTo(DEFAULT_MAX_ALLOWED_AMOUNT);
        assertThat(testLoanDemand.getAdjustedDemand()).isEqualTo(DEFAULT_ADJUSTED_DEMAND);
        assertThat(testLoanDemand.getAnnualIncome()).isEqualTo(DEFAULT_ANNUAL_INCOME);
        assertThat(testLoanDemand.getCostOfInvestment()).isEqualTo(DEFAULT_COST_OF_INVESTMENT);
        assertThat(testLoanDemand.getDemandedLandAreaInHector()).isEqualTo(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);
        assertThat(testLoanDemand.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoanDemand.getSeasonOfCropLoan()).isEqualTo(DEFAULT_SEASON_OF_CROP_LOAN);
        assertThat(testLoanDemand.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLoanDemand.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanDemand.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanDemand.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanDemand.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanDemand.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createLoanDemandWithExistingId() throws Exception {
        // Create the LoanDemand with an existing ID
        loanDemand.setId(1L);
        LoanDemandDTO loanDemandDTO = loanDemandMapper.toDto(loanDemand);

        int databaseSizeBeforeCreate = loanDemandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanDemandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDemandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanDemands() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList
        restLoanDemandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDemand.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanDemandAmount").value(hasItem(DEFAULT_LOAN_DEMAND_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxAllowedAmount").value(hasItem(DEFAULT_MAX_ALLOWED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].adjustedDemand").value(hasItem(DEFAULT_ADJUSTED_DEMAND.doubleValue())))
            .andExpect(jsonPath("$.[*].annualIncome").value(hasItem(DEFAULT_ANNUAL_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].costOfInvestment").value(hasItem(DEFAULT_COST_OF_INVESTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].demandedLandAreaInHector").value(hasItem(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].seasonOfCropLoan").value(hasItem(DEFAULT_SEASON_OF_CROP_LOAN)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getLoanDemand() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get the loanDemand
        restLoanDemandMockMvc
            .perform(get(ENTITY_API_URL_ID, loanDemand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanDemand.getId().intValue()))
            .andExpect(jsonPath("$.loanDemandAmount").value(DEFAULT_LOAN_DEMAND_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.maxAllowedAmount").value(DEFAULT_MAX_ALLOWED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.adjustedDemand").value(DEFAULT_ADJUSTED_DEMAND.doubleValue()))
            .andExpect(jsonPath("$.annualIncome").value(DEFAULT_ANNUAL_INCOME.doubleValue()))
            .andExpect(jsonPath("$.costOfInvestment").value(DEFAULT_COST_OF_INVESTMENT.doubleValue()))
            .andExpect(jsonPath("$.demandedLandAreaInHector").value(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.seasonOfCropLoan").value(DEFAULT_SEASON_OF_CROP_LOAN))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getLoanDemandsByIdFiltering() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        Long id = loanDemand.getId();

        defaultLoanDemandShouldBeFound("id.equals=" + id);
        defaultLoanDemandShouldNotBeFound("id.notEquals=" + id);

        defaultLoanDemandShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanDemandShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanDemandShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanDemandShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLoanDemandAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where loanDemandAmount equals to DEFAULT_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldBeFound("loanDemandAmount.equals=" + DEFAULT_LOAN_DEMAND_AMOUNT);

        // Get all the loanDemandList where loanDemandAmount equals to UPDATED_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldNotBeFound("loanDemandAmount.equals=" + UPDATED_LOAN_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLoanDemandAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where loanDemandAmount in DEFAULT_LOAN_DEMAND_AMOUNT or UPDATED_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldBeFound("loanDemandAmount.in=" + DEFAULT_LOAN_DEMAND_AMOUNT + "," + UPDATED_LOAN_DEMAND_AMOUNT);

        // Get all the loanDemandList where loanDemandAmount equals to UPDATED_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldNotBeFound("loanDemandAmount.in=" + UPDATED_LOAN_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLoanDemandAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where loanDemandAmount is not null
        defaultLoanDemandShouldBeFound("loanDemandAmount.specified=true");

        // Get all the loanDemandList where loanDemandAmount is null
        defaultLoanDemandShouldNotBeFound("loanDemandAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLoanDemandAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where loanDemandAmount is greater than or equal to DEFAULT_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldBeFound("loanDemandAmount.greaterThanOrEqual=" + DEFAULT_LOAN_DEMAND_AMOUNT);

        // Get all the loanDemandList where loanDemandAmount is greater than or equal to UPDATED_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldNotBeFound("loanDemandAmount.greaterThanOrEqual=" + UPDATED_LOAN_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLoanDemandAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where loanDemandAmount is less than or equal to DEFAULT_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldBeFound("loanDemandAmount.lessThanOrEqual=" + DEFAULT_LOAN_DEMAND_AMOUNT);

        // Get all the loanDemandList where loanDemandAmount is less than or equal to SMALLER_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldNotBeFound("loanDemandAmount.lessThanOrEqual=" + SMALLER_LOAN_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLoanDemandAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where loanDemandAmount is less than DEFAULT_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldNotBeFound("loanDemandAmount.lessThan=" + DEFAULT_LOAN_DEMAND_AMOUNT);

        // Get all the loanDemandList where loanDemandAmount is less than UPDATED_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldBeFound("loanDemandAmount.lessThan=" + UPDATED_LOAN_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLoanDemandAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where loanDemandAmount is greater than DEFAULT_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldNotBeFound("loanDemandAmount.greaterThan=" + DEFAULT_LOAN_DEMAND_AMOUNT);

        // Get all the loanDemandList where loanDemandAmount is greater than SMALLER_LOAN_DEMAND_AMOUNT
        defaultLoanDemandShouldBeFound("loanDemandAmount.greaterThan=" + SMALLER_LOAN_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByMaxAllowedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where maxAllowedAmount equals to DEFAULT_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldBeFound("maxAllowedAmount.equals=" + DEFAULT_MAX_ALLOWED_AMOUNT);

        // Get all the loanDemandList where maxAllowedAmount equals to UPDATED_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldNotBeFound("maxAllowedAmount.equals=" + UPDATED_MAX_ALLOWED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByMaxAllowedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where maxAllowedAmount in DEFAULT_MAX_ALLOWED_AMOUNT or UPDATED_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldBeFound("maxAllowedAmount.in=" + DEFAULT_MAX_ALLOWED_AMOUNT + "," + UPDATED_MAX_ALLOWED_AMOUNT);

        // Get all the loanDemandList where maxAllowedAmount equals to UPDATED_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldNotBeFound("maxAllowedAmount.in=" + UPDATED_MAX_ALLOWED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByMaxAllowedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where maxAllowedAmount is not null
        defaultLoanDemandShouldBeFound("maxAllowedAmount.specified=true");

        // Get all the loanDemandList where maxAllowedAmount is null
        defaultLoanDemandShouldNotBeFound("maxAllowedAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByMaxAllowedAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where maxAllowedAmount is greater than or equal to DEFAULT_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldBeFound("maxAllowedAmount.greaterThanOrEqual=" + DEFAULT_MAX_ALLOWED_AMOUNT);

        // Get all the loanDemandList where maxAllowedAmount is greater than or equal to UPDATED_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldNotBeFound("maxAllowedAmount.greaterThanOrEqual=" + UPDATED_MAX_ALLOWED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByMaxAllowedAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where maxAllowedAmount is less than or equal to DEFAULT_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldBeFound("maxAllowedAmount.lessThanOrEqual=" + DEFAULT_MAX_ALLOWED_AMOUNT);

        // Get all the loanDemandList where maxAllowedAmount is less than or equal to SMALLER_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldNotBeFound("maxAllowedAmount.lessThanOrEqual=" + SMALLER_MAX_ALLOWED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByMaxAllowedAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where maxAllowedAmount is less than DEFAULT_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldNotBeFound("maxAllowedAmount.lessThan=" + DEFAULT_MAX_ALLOWED_AMOUNT);

        // Get all the loanDemandList where maxAllowedAmount is less than UPDATED_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldBeFound("maxAllowedAmount.lessThan=" + UPDATED_MAX_ALLOWED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByMaxAllowedAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where maxAllowedAmount is greater than DEFAULT_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldNotBeFound("maxAllowedAmount.greaterThan=" + DEFAULT_MAX_ALLOWED_AMOUNT);

        // Get all the loanDemandList where maxAllowedAmount is greater than SMALLER_MAX_ALLOWED_AMOUNT
        defaultLoanDemandShouldBeFound("maxAllowedAmount.greaterThan=" + SMALLER_MAX_ALLOWED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAdjustedDemandIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where adjustedDemand equals to DEFAULT_ADJUSTED_DEMAND
        defaultLoanDemandShouldBeFound("adjustedDemand.equals=" + DEFAULT_ADJUSTED_DEMAND);

        // Get all the loanDemandList where adjustedDemand equals to UPDATED_ADJUSTED_DEMAND
        defaultLoanDemandShouldNotBeFound("adjustedDemand.equals=" + UPDATED_ADJUSTED_DEMAND);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAdjustedDemandIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where adjustedDemand in DEFAULT_ADJUSTED_DEMAND or UPDATED_ADJUSTED_DEMAND
        defaultLoanDemandShouldBeFound("adjustedDemand.in=" + DEFAULT_ADJUSTED_DEMAND + "," + UPDATED_ADJUSTED_DEMAND);

        // Get all the loanDemandList where adjustedDemand equals to UPDATED_ADJUSTED_DEMAND
        defaultLoanDemandShouldNotBeFound("adjustedDemand.in=" + UPDATED_ADJUSTED_DEMAND);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAdjustedDemandIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where adjustedDemand is not null
        defaultLoanDemandShouldBeFound("adjustedDemand.specified=true");

        // Get all the loanDemandList where adjustedDemand is null
        defaultLoanDemandShouldNotBeFound("adjustedDemand.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAdjustedDemandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where adjustedDemand is greater than or equal to DEFAULT_ADJUSTED_DEMAND
        defaultLoanDemandShouldBeFound("adjustedDemand.greaterThanOrEqual=" + DEFAULT_ADJUSTED_DEMAND);

        // Get all the loanDemandList where adjustedDemand is greater than or equal to UPDATED_ADJUSTED_DEMAND
        defaultLoanDemandShouldNotBeFound("adjustedDemand.greaterThanOrEqual=" + UPDATED_ADJUSTED_DEMAND);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAdjustedDemandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where adjustedDemand is less than or equal to DEFAULT_ADJUSTED_DEMAND
        defaultLoanDemandShouldBeFound("adjustedDemand.lessThanOrEqual=" + DEFAULT_ADJUSTED_DEMAND);

        // Get all the loanDemandList where adjustedDemand is less than or equal to SMALLER_ADJUSTED_DEMAND
        defaultLoanDemandShouldNotBeFound("adjustedDemand.lessThanOrEqual=" + SMALLER_ADJUSTED_DEMAND);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAdjustedDemandIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where adjustedDemand is less than DEFAULT_ADJUSTED_DEMAND
        defaultLoanDemandShouldNotBeFound("adjustedDemand.lessThan=" + DEFAULT_ADJUSTED_DEMAND);

        // Get all the loanDemandList where adjustedDemand is less than UPDATED_ADJUSTED_DEMAND
        defaultLoanDemandShouldBeFound("adjustedDemand.lessThan=" + UPDATED_ADJUSTED_DEMAND);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAdjustedDemandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where adjustedDemand is greater than DEFAULT_ADJUSTED_DEMAND
        defaultLoanDemandShouldNotBeFound("adjustedDemand.greaterThan=" + DEFAULT_ADJUSTED_DEMAND);

        // Get all the loanDemandList where adjustedDemand is greater than SMALLER_ADJUSTED_DEMAND
        defaultLoanDemandShouldBeFound("adjustedDemand.greaterThan=" + SMALLER_ADJUSTED_DEMAND);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAnnualIncomeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where annualIncome equals to DEFAULT_ANNUAL_INCOME
        defaultLoanDemandShouldBeFound("annualIncome.equals=" + DEFAULT_ANNUAL_INCOME);

        // Get all the loanDemandList where annualIncome equals to UPDATED_ANNUAL_INCOME
        defaultLoanDemandShouldNotBeFound("annualIncome.equals=" + UPDATED_ANNUAL_INCOME);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAnnualIncomeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where annualIncome in DEFAULT_ANNUAL_INCOME or UPDATED_ANNUAL_INCOME
        defaultLoanDemandShouldBeFound("annualIncome.in=" + DEFAULT_ANNUAL_INCOME + "," + UPDATED_ANNUAL_INCOME);

        // Get all the loanDemandList where annualIncome equals to UPDATED_ANNUAL_INCOME
        defaultLoanDemandShouldNotBeFound("annualIncome.in=" + UPDATED_ANNUAL_INCOME);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAnnualIncomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where annualIncome is not null
        defaultLoanDemandShouldBeFound("annualIncome.specified=true");

        // Get all the loanDemandList where annualIncome is null
        defaultLoanDemandShouldNotBeFound("annualIncome.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAnnualIncomeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where annualIncome is greater than or equal to DEFAULT_ANNUAL_INCOME
        defaultLoanDemandShouldBeFound("annualIncome.greaterThanOrEqual=" + DEFAULT_ANNUAL_INCOME);

        // Get all the loanDemandList where annualIncome is greater than or equal to UPDATED_ANNUAL_INCOME
        defaultLoanDemandShouldNotBeFound("annualIncome.greaterThanOrEqual=" + UPDATED_ANNUAL_INCOME);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAnnualIncomeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where annualIncome is less than or equal to DEFAULT_ANNUAL_INCOME
        defaultLoanDemandShouldBeFound("annualIncome.lessThanOrEqual=" + DEFAULT_ANNUAL_INCOME);

        // Get all the loanDemandList where annualIncome is less than or equal to SMALLER_ANNUAL_INCOME
        defaultLoanDemandShouldNotBeFound("annualIncome.lessThanOrEqual=" + SMALLER_ANNUAL_INCOME);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAnnualIncomeIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where annualIncome is less than DEFAULT_ANNUAL_INCOME
        defaultLoanDemandShouldNotBeFound("annualIncome.lessThan=" + DEFAULT_ANNUAL_INCOME);

        // Get all the loanDemandList where annualIncome is less than UPDATED_ANNUAL_INCOME
        defaultLoanDemandShouldBeFound("annualIncome.lessThan=" + UPDATED_ANNUAL_INCOME);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByAnnualIncomeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where annualIncome is greater than DEFAULT_ANNUAL_INCOME
        defaultLoanDemandShouldNotBeFound("annualIncome.greaterThan=" + DEFAULT_ANNUAL_INCOME);

        // Get all the loanDemandList where annualIncome is greater than SMALLER_ANNUAL_INCOME
        defaultLoanDemandShouldBeFound("annualIncome.greaterThan=" + SMALLER_ANNUAL_INCOME);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByCostOfInvestmentIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where costOfInvestment equals to DEFAULT_COST_OF_INVESTMENT
        defaultLoanDemandShouldBeFound("costOfInvestment.equals=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDemandList where costOfInvestment equals to UPDATED_COST_OF_INVESTMENT
        defaultLoanDemandShouldNotBeFound("costOfInvestment.equals=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByCostOfInvestmentIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where costOfInvestment in DEFAULT_COST_OF_INVESTMENT or UPDATED_COST_OF_INVESTMENT
        defaultLoanDemandShouldBeFound("costOfInvestment.in=" + DEFAULT_COST_OF_INVESTMENT + "," + UPDATED_COST_OF_INVESTMENT);

        // Get all the loanDemandList where costOfInvestment equals to UPDATED_COST_OF_INVESTMENT
        defaultLoanDemandShouldNotBeFound("costOfInvestment.in=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByCostOfInvestmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where costOfInvestment is not null
        defaultLoanDemandShouldBeFound("costOfInvestment.specified=true");

        // Get all the loanDemandList where costOfInvestment is null
        defaultLoanDemandShouldNotBeFound("costOfInvestment.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByCostOfInvestmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where costOfInvestment is greater than or equal to DEFAULT_COST_OF_INVESTMENT
        defaultLoanDemandShouldBeFound("costOfInvestment.greaterThanOrEqual=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDemandList where costOfInvestment is greater than or equal to UPDATED_COST_OF_INVESTMENT
        defaultLoanDemandShouldNotBeFound("costOfInvestment.greaterThanOrEqual=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByCostOfInvestmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where costOfInvestment is less than or equal to DEFAULT_COST_OF_INVESTMENT
        defaultLoanDemandShouldBeFound("costOfInvestment.lessThanOrEqual=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDemandList where costOfInvestment is less than or equal to SMALLER_COST_OF_INVESTMENT
        defaultLoanDemandShouldNotBeFound("costOfInvestment.lessThanOrEqual=" + SMALLER_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByCostOfInvestmentIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where costOfInvestment is less than DEFAULT_COST_OF_INVESTMENT
        defaultLoanDemandShouldNotBeFound("costOfInvestment.lessThan=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDemandList where costOfInvestment is less than UPDATED_COST_OF_INVESTMENT
        defaultLoanDemandShouldBeFound("costOfInvestment.lessThan=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByCostOfInvestmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where costOfInvestment is greater than DEFAULT_COST_OF_INVESTMENT
        defaultLoanDemandShouldNotBeFound("costOfInvestment.greaterThan=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanDemandList where costOfInvestment is greater than SMALLER_COST_OF_INVESTMENT
        defaultLoanDemandShouldBeFound("costOfInvestment.greaterThan=" + SMALLER_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByDemandedLandAreaInHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where demandedLandAreaInHector equals to DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldBeFound("demandedLandAreaInHector.equals=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanDemandList where demandedLandAreaInHector equals to UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldNotBeFound("demandedLandAreaInHector.equals=" + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByDemandedLandAreaInHectorIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where demandedLandAreaInHector in DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR or UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldBeFound(
            "demandedLandAreaInHector.in=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR + "," + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        );

        // Get all the loanDemandList where demandedLandAreaInHector equals to UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldNotBeFound("demandedLandAreaInHector.in=" + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByDemandedLandAreaInHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where demandedLandAreaInHector is not null
        defaultLoanDemandShouldBeFound("demandedLandAreaInHector.specified=true");

        // Get all the loanDemandList where demandedLandAreaInHector is null
        defaultLoanDemandShouldNotBeFound("demandedLandAreaInHector.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByDemandedLandAreaInHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where demandedLandAreaInHector is greater than or equal to DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldBeFound("demandedLandAreaInHector.greaterThanOrEqual=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanDemandList where demandedLandAreaInHector is greater than or equal to UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldNotBeFound("demandedLandAreaInHector.greaterThanOrEqual=" + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByDemandedLandAreaInHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where demandedLandAreaInHector is less than or equal to DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldBeFound("demandedLandAreaInHector.lessThanOrEqual=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanDemandList where demandedLandAreaInHector is less than or equal to SMALLER_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldNotBeFound("demandedLandAreaInHector.lessThanOrEqual=" + SMALLER_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByDemandedLandAreaInHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where demandedLandAreaInHector is less than DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldNotBeFound("demandedLandAreaInHector.lessThan=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanDemandList where demandedLandAreaInHector is less than UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldBeFound("demandedLandAreaInHector.lessThan=" + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByDemandedLandAreaInHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where demandedLandAreaInHector is greater than DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldNotBeFound("demandedLandAreaInHector.greaterThan=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanDemandList where demandedLandAreaInHector is greater than SMALLER_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanDemandShouldBeFound("demandedLandAreaInHector.greaterThan=" + SMALLER_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where status equals to DEFAULT_STATUS
        defaultLoanDemandShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the loanDemandList where status equals to UPDATED_STATUS
        defaultLoanDemandShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLoanDemandShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the loanDemandList where status equals to UPDATED_STATUS
        defaultLoanDemandShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where status is not null
        defaultLoanDemandShouldBeFound("status.specified=true");

        // Get all the loanDemandList where status is null
        defaultLoanDemandShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsBySeasonOfCropLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where seasonOfCropLoan equals to DEFAULT_SEASON_OF_CROP_LOAN
        defaultLoanDemandShouldBeFound("seasonOfCropLoan.equals=" + DEFAULT_SEASON_OF_CROP_LOAN);

        // Get all the loanDemandList where seasonOfCropLoan equals to UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanDemandShouldNotBeFound("seasonOfCropLoan.equals=" + UPDATED_SEASON_OF_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllLoanDemandsBySeasonOfCropLoanIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where seasonOfCropLoan in DEFAULT_SEASON_OF_CROP_LOAN or UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanDemandShouldBeFound("seasonOfCropLoan.in=" + DEFAULT_SEASON_OF_CROP_LOAN + "," + UPDATED_SEASON_OF_CROP_LOAN);

        // Get all the loanDemandList where seasonOfCropLoan equals to UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanDemandShouldNotBeFound("seasonOfCropLoan.in=" + UPDATED_SEASON_OF_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllLoanDemandsBySeasonOfCropLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where seasonOfCropLoan is not null
        defaultLoanDemandShouldBeFound("seasonOfCropLoan.specified=true");

        // Get all the loanDemandList where seasonOfCropLoan is null
        defaultLoanDemandShouldNotBeFound("seasonOfCropLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsBySeasonOfCropLoanContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where seasonOfCropLoan contains DEFAULT_SEASON_OF_CROP_LOAN
        defaultLoanDemandShouldBeFound("seasonOfCropLoan.contains=" + DEFAULT_SEASON_OF_CROP_LOAN);

        // Get all the loanDemandList where seasonOfCropLoan contains UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanDemandShouldNotBeFound("seasonOfCropLoan.contains=" + UPDATED_SEASON_OF_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllLoanDemandsBySeasonOfCropLoanNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where seasonOfCropLoan does not contain DEFAULT_SEASON_OF_CROP_LOAN
        defaultLoanDemandShouldNotBeFound("seasonOfCropLoan.doesNotContain=" + DEFAULT_SEASON_OF_CROP_LOAN);

        // Get all the loanDemandList where seasonOfCropLoan does not contain UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanDemandShouldBeFound("seasonOfCropLoan.doesNotContain=" + UPDATED_SEASON_OF_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where year equals to DEFAULT_YEAR
        defaultLoanDemandShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the loanDemandList where year equals to UPDATED_YEAR
        defaultLoanDemandShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultLoanDemandShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the loanDemandList where year equals to UPDATED_YEAR
        defaultLoanDemandShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where year is not null
        defaultLoanDemandShouldBeFound("year.specified=true");

        // Get all the loanDemandList where year is null
        defaultLoanDemandShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByYearContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where year contains DEFAULT_YEAR
        defaultLoanDemandShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the loanDemandList where year contains UPDATED_YEAR
        defaultLoanDemandShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where year does not contain DEFAULT_YEAR
        defaultLoanDemandShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the loanDemandList where year does not contain UPDATED_YEAR
        defaultLoanDemandShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLoanDemandShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the loanDemandList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanDemandShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLoanDemandShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the loanDemandList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanDemandShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where lastModified is not null
        defaultLoanDemandShouldBeFound("lastModified.specified=true");

        // Get all the loanDemandList where lastModified is null
        defaultLoanDemandShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLoanDemandShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDemandList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanDemandShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLoanDemandShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the loanDemandList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanDemandShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where lastModifiedBy is not null
        defaultLoanDemandShouldBeFound("lastModifiedBy.specified=true");

        // Get all the loanDemandList where lastModifiedBy is null
        defaultLoanDemandShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLoanDemandShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDemandList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLoanDemandShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLoanDemandShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDemandList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLoanDemandShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLoanDemandShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDemandList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanDemandShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLoanDemandShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the loanDemandList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanDemandShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField1 is not null
        defaultLoanDemandShouldBeFound("freeField1.specified=true");

        // Get all the loanDemandList where freeField1 is null
        defaultLoanDemandShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLoanDemandShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDemandList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLoanDemandShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLoanDemandShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDemandList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLoanDemandShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLoanDemandShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDemandList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanDemandShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLoanDemandShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the loanDemandList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanDemandShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField2 is not null
        defaultLoanDemandShouldBeFound("freeField2.specified=true");

        // Get all the loanDemandList where freeField2 is null
        defaultLoanDemandShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLoanDemandShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDemandList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLoanDemandShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLoanDemandShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDemandList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLoanDemandShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLoanDemandShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDemandList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanDemandShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLoanDemandShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the loanDemandList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanDemandShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField3 is not null
        defaultLoanDemandShouldBeFound("freeField3.specified=true");

        // Get all the loanDemandList where freeField3 is null
        defaultLoanDemandShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLoanDemandShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDemandList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLoanDemandShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        // Get all the loanDemandList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLoanDemandShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDemandList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLoanDemandShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDemandsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            loanDemandRepository.saveAndFlush(loanDemand);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        loanDemand.setMember(member);
        loanDemandRepository.saveAndFlush(loanDemand);
        Long memberId = member.getId();

        // Get all the loanDemandList where member equals to memberId
        defaultLoanDemandShouldBeFound("memberId.equals=" + memberId);

        // Get all the loanDemandList where member equals to (memberId + 1)
        defaultLoanDemandShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    @Test
    @Transactional
    void getAllLoanDemandsBySocietyLoanProductIsEqualToSomething() throws Exception {
        SocietyLoanProduct societyLoanProduct;
        if (TestUtil.findAll(em, SocietyLoanProduct.class).isEmpty()) {
            loanDemandRepository.saveAndFlush(loanDemand);
            societyLoanProduct = SocietyLoanProductResourceIT.createEntity(em);
        } else {
            societyLoanProduct = TestUtil.findAll(em, SocietyLoanProduct.class).get(0);
        }
        em.persist(societyLoanProduct);
        em.flush();
        loanDemand.setSocietyLoanProduct(societyLoanProduct);
        loanDemandRepository.saveAndFlush(loanDemand);
        Long societyLoanProductId = societyLoanProduct.getId();

        // Get all the loanDemandList where societyLoanProduct equals to societyLoanProductId
        defaultLoanDemandShouldBeFound("societyLoanProductId.equals=" + societyLoanProductId);

        // Get all the loanDemandList where societyLoanProduct equals to (societyLoanProductId + 1)
        defaultLoanDemandShouldNotBeFound("societyLoanProductId.equals=" + (societyLoanProductId + 1));
    }

    @Test
    @Transactional
    void getAllLoanDemandsByMemberLandAssetsIsEqualToSomething() throws Exception {
        MemberLandAssets memberLandAssets;
        if (TestUtil.findAll(em, MemberLandAssets.class).isEmpty()) {
            loanDemandRepository.saveAndFlush(loanDemand);
            memberLandAssets = MemberLandAssetsResourceIT.createEntity(em);
        } else {
            memberLandAssets = TestUtil.findAll(em, MemberLandAssets.class).get(0);
        }
        em.persist(memberLandAssets);
        em.flush();
        loanDemand.setMemberLandAssets(memberLandAssets);
        loanDemandRepository.saveAndFlush(loanDemand);
        Long memberLandAssetsId = memberLandAssets.getId();

        // Get all the loanDemandList where memberLandAssets equals to memberLandAssetsId
        defaultLoanDemandShouldBeFound("memberLandAssetsId.equals=" + memberLandAssetsId);

        // Get all the loanDemandList where memberLandAssets equals to (memberLandAssetsId + 1)
        defaultLoanDemandShouldNotBeFound("memberLandAssetsId.equals=" + (memberLandAssetsId + 1));
    }

    @Test
    @Transactional
    void getAllLoanDemandsBySocietyCropRegistrationIsEqualToSomething() throws Exception {
        SocietyCropRegistration societyCropRegistration;
        if (TestUtil.findAll(em, SocietyCropRegistration.class).isEmpty()) {
            loanDemandRepository.saveAndFlush(loanDemand);
            societyCropRegistration = SocietyCropRegistrationResourceIT.createEntity(em);
        } else {
            societyCropRegistration = TestUtil.findAll(em, SocietyCropRegistration.class).get(0);
        }
        em.persist(societyCropRegistration);
        em.flush();
        loanDemand.setSocietyCropRegistration(societyCropRegistration);
        loanDemandRepository.saveAndFlush(loanDemand);
        Long societyCropRegistrationId = societyCropRegistration.getId();

        // Get all the loanDemandList where societyCropRegistration equals to societyCropRegistrationId
        defaultLoanDemandShouldBeFound("societyCropRegistrationId.equals=" + societyCropRegistrationId);

        // Get all the loanDemandList where societyCropRegistration equals to (societyCropRegistrationId + 1)
        defaultLoanDemandShouldNotBeFound("societyCropRegistrationId.equals=" + (societyCropRegistrationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanDemandShouldBeFound(String filter) throws Exception {
        restLoanDemandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDemand.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanDemandAmount").value(hasItem(DEFAULT_LOAN_DEMAND_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxAllowedAmount").value(hasItem(DEFAULT_MAX_ALLOWED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].adjustedDemand").value(hasItem(DEFAULT_ADJUSTED_DEMAND.doubleValue())))
            .andExpect(jsonPath("$.[*].annualIncome").value(hasItem(DEFAULT_ANNUAL_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].costOfInvestment").value(hasItem(DEFAULT_COST_OF_INVESTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].demandedLandAreaInHector").value(hasItem(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].seasonOfCropLoan").value(hasItem(DEFAULT_SEASON_OF_CROP_LOAN)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restLoanDemandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanDemandShouldNotBeFound(String filter) throws Exception {
        restLoanDemandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanDemandMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanDemand() throws Exception {
        // Get the loanDemand
        restLoanDemandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoanDemand() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        int databaseSizeBeforeUpdate = loanDemandRepository.findAll().size();

        // Update the loanDemand
        LoanDemand updatedLoanDemand = loanDemandRepository.findById(loanDemand.getId()).get();
        // Disconnect from session so that the updates on updatedLoanDemand are not directly saved in db
        em.detach(updatedLoanDemand);
        updatedLoanDemand
            .loanDemandAmount(UPDATED_LOAN_DEMAND_AMOUNT)
            .maxAllowedAmount(UPDATED_MAX_ALLOWED_AMOUNT)
            .adjustedDemand(UPDATED_ADJUSTED_DEMAND)
            .annualIncome(UPDATED_ANNUAL_INCOME)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .demandedLandAreaInHector(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR)
            .status(UPDATED_STATUS)
            .seasonOfCropLoan(UPDATED_SEASON_OF_CROP_LOAN)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        LoanDemandDTO loanDemandDTO = loanDemandMapper.toDto(updatedLoanDemand);

        restLoanDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanDemandDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeUpdate);
        LoanDemand testLoanDemand = loanDemandList.get(loanDemandList.size() - 1);
        assertThat(testLoanDemand.getLoanDemandAmount()).isEqualTo(UPDATED_LOAN_DEMAND_AMOUNT);
        assertThat(testLoanDemand.getMaxAllowedAmount()).isEqualTo(UPDATED_MAX_ALLOWED_AMOUNT);
        assertThat(testLoanDemand.getAdjustedDemand()).isEqualTo(UPDATED_ADJUSTED_DEMAND);
        assertThat(testLoanDemand.getAnnualIncome()).isEqualTo(UPDATED_ANNUAL_INCOME);
        assertThat(testLoanDemand.getCostOfInvestment()).isEqualTo(UPDATED_COST_OF_INVESTMENT);
        assertThat(testLoanDemand.getDemandedLandAreaInHector()).isEqualTo(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
        assertThat(testLoanDemand.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanDemand.getSeasonOfCropLoan()).isEqualTo(UPDATED_SEASON_OF_CROP_LOAN);
        assertThat(testLoanDemand.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanDemand.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanDemand.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanDemand.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDemand.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDemand.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingLoanDemand() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandRepository.findAll().size();
        loanDemand.setId(count.incrementAndGet());

        // Create the LoanDemand
        LoanDemandDTO loanDemandDTO = loanDemandMapper.toDto(loanDemand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanDemandDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanDemand() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandRepository.findAll().size();
        loanDemand.setId(count.incrementAndGet());

        // Create the LoanDemand
        LoanDemandDTO loanDemandDTO = loanDemandMapper.toDto(loanDemand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanDemand() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandRepository.findAll().size();
        loanDemand.setId(count.incrementAndGet());

        // Create the LoanDemand
        LoanDemandDTO loanDemandDTO = loanDemandMapper.toDto(loanDemand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDemandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDemandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanDemandWithPatch() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        int databaseSizeBeforeUpdate = loanDemandRepository.findAll().size();

        // Update the loanDemand using partial update
        LoanDemand partialUpdatedLoanDemand = new LoanDemand();
        partialUpdatedLoanDemand.setId(loanDemand.getId());

        partialUpdatedLoanDemand
            .maxAllowedAmount(UPDATED_MAX_ALLOWED_AMOUNT)
            .adjustedDemand(UPDATED_ADJUSTED_DEMAND)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .seasonOfCropLoan(UPDATED_SEASON_OF_CROP_LOAN)
            .freeField2(UPDATED_FREE_FIELD_2);

        restLoanDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDemand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDemand))
            )
            .andExpect(status().isOk());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeUpdate);
        LoanDemand testLoanDemand = loanDemandList.get(loanDemandList.size() - 1);
        assertThat(testLoanDemand.getLoanDemandAmount()).isEqualTo(DEFAULT_LOAN_DEMAND_AMOUNT);
        assertThat(testLoanDemand.getMaxAllowedAmount()).isEqualTo(UPDATED_MAX_ALLOWED_AMOUNT);
        assertThat(testLoanDemand.getAdjustedDemand()).isEqualTo(UPDATED_ADJUSTED_DEMAND);
        assertThat(testLoanDemand.getAnnualIncome()).isEqualTo(DEFAULT_ANNUAL_INCOME);
        assertThat(testLoanDemand.getCostOfInvestment()).isEqualTo(UPDATED_COST_OF_INVESTMENT);
        assertThat(testLoanDemand.getDemandedLandAreaInHector()).isEqualTo(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);
        assertThat(testLoanDemand.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoanDemand.getSeasonOfCropLoan()).isEqualTo(UPDATED_SEASON_OF_CROP_LOAN);
        assertThat(testLoanDemand.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLoanDemand.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanDemand.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanDemand.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanDemand.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDemand.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateLoanDemandWithPatch() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        int databaseSizeBeforeUpdate = loanDemandRepository.findAll().size();

        // Update the loanDemand using partial update
        LoanDemand partialUpdatedLoanDemand = new LoanDemand();
        partialUpdatedLoanDemand.setId(loanDemand.getId());

        partialUpdatedLoanDemand
            .loanDemandAmount(UPDATED_LOAN_DEMAND_AMOUNT)
            .maxAllowedAmount(UPDATED_MAX_ALLOWED_AMOUNT)
            .adjustedDemand(UPDATED_ADJUSTED_DEMAND)
            .annualIncome(UPDATED_ANNUAL_INCOME)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .demandedLandAreaInHector(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR)
            .status(UPDATED_STATUS)
            .seasonOfCropLoan(UPDATED_SEASON_OF_CROP_LOAN)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restLoanDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDemand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDemand))
            )
            .andExpect(status().isOk());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeUpdate);
        LoanDemand testLoanDemand = loanDemandList.get(loanDemandList.size() - 1);
        assertThat(testLoanDemand.getLoanDemandAmount()).isEqualTo(UPDATED_LOAN_DEMAND_AMOUNT);
        assertThat(testLoanDemand.getMaxAllowedAmount()).isEqualTo(UPDATED_MAX_ALLOWED_AMOUNT);
        assertThat(testLoanDemand.getAdjustedDemand()).isEqualTo(UPDATED_ADJUSTED_DEMAND);
        assertThat(testLoanDemand.getAnnualIncome()).isEqualTo(UPDATED_ANNUAL_INCOME);
        assertThat(testLoanDemand.getCostOfInvestment()).isEqualTo(UPDATED_COST_OF_INVESTMENT);
        assertThat(testLoanDemand.getDemandedLandAreaInHector()).isEqualTo(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
        assertThat(testLoanDemand.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanDemand.getSeasonOfCropLoan()).isEqualTo(UPDATED_SEASON_OF_CROP_LOAN);
        assertThat(testLoanDemand.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanDemand.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanDemand.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanDemand.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDemand.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDemand.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingLoanDemand() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandRepository.findAll().size();
        loanDemand.setId(count.incrementAndGet());

        // Create the LoanDemand
        LoanDemandDTO loanDemandDTO = loanDemandMapper.toDto(loanDemand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanDemandDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanDemand() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandRepository.findAll().size();
        loanDemand.setId(count.incrementAndGet());

        // Create the LoanDemand
        LoanDemandDTO loanDemandDTO = loanDemandMapper.toDto(loanDemand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDemandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanDemand() throws Exception {
        int databaseSizeBeforeUpdate = loanDemandRepository.findAll().size();
        loanDemand.setId(count.incrementAndGet());

        // Create the LoanDemand
        LoanDemandDTO loanDemandDTO = loanDemandMapper.toDto(loanDemand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDemandMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loanDemandDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDemand in the database
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanDemand() throws Exception {
        // Initialize the database
        loanDemandRepository.saveAndFlush(loanDemand);

        int databaseSizeBeforeDelete = loanDemandRepository.findAll().size();

        // Delete the loanDemand
        restLoanDemandMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanDemand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanDemand> loanDemandList = loanDemandRepository.findAll();
        assertThat(loanDemandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

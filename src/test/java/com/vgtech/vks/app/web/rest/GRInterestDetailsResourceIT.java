package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.GRInterestDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.repository.GRInterestDetailsRepository;
import com.vgtech.vks.app.service.criteria.GRInterestDetailsCriteria;
import com.vgtech.vks.app.service.dto.GRInterestDetailsDTO;
import com.vgtech.vks.app.service.mapper.GRInterestDetailsMapper;
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
 * Integration tests for the {@link GRInterestDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GRInterestDetailsResourceIT {

    private static final String DEFAULT_LOAN_GR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_GR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_CRITERIA = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVATED = false;
    private static final Boolean UPDATED_IS_ACTIVATED = true;

    private static final Double DEFAULT_BORROWING_INTEREST_RATE = 1D;
    private static final Double UPDATED_BORROWING_INTEREST_RATE = 2D;
    private static final Double SMALLER_BORROWING_INTEREST_RATE = 1D - 1D;

    private static final Double DEFAULT_INTEREST_ON_LOAN = 1D;
    private static final Double UPDATED_INTEREST_ON_LOAN = 2D;
    private static final Double SMALLER_INTEREST_ON_LOAN = 1D - 1D;

    private static final Double DEFAULT_PENALTY_INTEREST = 1D;
    private static final Double UPDATED_PENALTY_INTEREST = 2D;
    private static final Double SMALLER_PENALTY_INTEREST = 1D - 1D;

    private static final Double DEFAULT_SURCHARGE = 1D;
    private static final Double UPDATED_SURCHARGE = 2D;
    private static final Double SMALLER_SURCHARGE = 1D - 1D;

    private static final Double DEFAULT_LOAN_DURATION = 1D;
    private static final Double UPDATED_LOAN_DURATION = 2D;
    private static final Double SMALLER_LOAN_DURATION = 1D - 1D;

    private static final Integer DEFAULT_NUMBER_OF_INSTALLMENT = 1;
    private static final Integer UPDATED_NUMBER_OF_INSTALLMENT = 2;
    private static final Integer SMALLER_NUMBER_OF_INSTALLMENT = 1 - 1;

    private static final Double DEFAULT_EXTENDED_INTERST_RATE = 1D;
    private static final Double UPDATED_EXTENDED_INTERST_RATE = 2D;
    private static final Double SMALLER_EXTENDED_INTERST_RATE = 1D - 1D;

    private static final Double DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST = 1D;
    private static final Double UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST = 2D;
    private static final Double SMALLER_CENTRAL_GOV_SUBSIDY_INTEREST = 1D - 1D;

    private static final Double DEFAULT_DIST_BANK_SUBSIDY_INTEREST = 1D;
    private static final Double UPDATED_DIST_BANK_SUBSIDY_INTEREST = 2D;
    private static final Double SMALLER_DIST_BANK_SUBSIDY_INTEREST = 1D - 1D;

    private static final Double DEFAULT_BORROWER_INTEREST = 1D;
    private static final Double UPDATED_BORROWER_INTEREST = 2D;
    private static final Double SMALLER_BORROWER_INTEREST = 1D - 1D;

    private static final Double DEFAULT_STATE_GOV_SUBSIDY_INTEREST = 1D;
    private static final Double UPDATED_STATE_GOV_SUBSIDY_INTEREST = 2D;
    private static final Double SMALLER_STATE_GOV_SUBSIDY_INTEREST = 1D - 1D;

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gr-interest-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GRInterestDetailsRepository gRInterestDetailsRepository;

    @Autowired
    private GRInterestDetailsMapper gRInterestDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGRInterestDetailsMockMvc;

    private GRInterestDetails gRInterestDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GRInterestDetails createEntity(EntityManager em) {
        GRInterestDetails gRInterestDetails = new GRInterestDetails()
            .loanGrName(DEFAULT_LOAN_GR_NAME)
            .criteria(DEFAULT_CRITERIA)
            .productType(DEFAULT_PRODUCT_TYPE)
            .isActivated(DEFAULT_IS_ACTIVATED)
            .borrowingInterestRate(DEFAULT_BORROWING_INTEREST_RATE)
            .interestOnLoan(DEFAULT_INTEREST_ON_LOAN)
            .penaltyInterest(DEFAULT_PENALTY_INTEREST)
            .surcharge(DEFAULT_SURCHARGE)
            .loanDuration(DEFAULT_LOAN_DURATION)
            .numberOFInstallment(DEFAULT_NUMBER_OF_INSTALLMENT)
            .extendedInterstRate(DEFAULT_EXTENDED_INTERST_RATE)
            .centralGovSubsidyInterest(DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST)
            .distBankSubsidyInterest(DEFAULT_DIST_BANK_SUBSIDY_INTEREST)
            .borrowerInterest(DEFAULT_BORROWER_INTEREST)
            .stateGovSubsidyInterest(DEFAULT_STATE_GOV_SUBSIDY_INTEREST)
            .year(DEFAULT_YEAR)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return gRInterestDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GRInterestDetails createUpdatedEntity(EntityManager em) {
        GRInterestDetails gRInterestDetails = new GRInterestDetails()
            .loanGrName(UPDATED_LOAN_GR_NAME)
            .criteria(UPDATED_CRITERIA)
            .productType(UPDATED_PRODUCT_TYPE)
            .isActivated(UPDATED_IS_ACTIVATED)
            .borrowingInterestRate(UPDATED_BORROWING_INTEREST_RATE)
            .interestOnLoan(UPDATED_INTEREST_ON_LOAN)
            .penaltyInterest(UPDATED_PENALTY_INTEREST)
            .surcharge(UPDATED_SURCHARGE)
            .loanDuration(UPDATED_LOAN_DURATION)
            .numberOFInstallment(UPDATED_NUMBER_OF_INSTALLMENT)
            .extendedInterstRate(UPDATED_EXTENDED_INTERST_RATE)
            .centralGovSubsidyInterest(UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST)
            .distBankSubsidyInterest(UPDATED_DIST_BANK_SUBSIDY_INTEREST)
            .borrowerInterest(UPDATED_BORROWER_INTEREST)
            .stateGovSubsidyInterest(UPDATED_STATE_GOV_SUBSIDY_INTEREST)
            .year(UPDATED_YEAR)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return gRInterestDetails;
    }

    @BeforeEach
    public void initTest() {
        gRInterestDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createGRInterestDetails() throws Exception {
        int databaseSizeBeforeCreate = gRInterestDetailsRepository.findAll().size();
        // Create the GRInterestDetails
        GRInterestDetailsDTO gRInterestDetailsDTO = gRInterestDetailsMapper.toDto(gRInterestDetails);
        restGRInterestDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gRInterestDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        GRInterestDetails testGRInterestDetails = gRInterestDetailsList.get(gRInterestDetailsList.size() - 1);
        assertThat(testGRInterestDetails.getLoanGrName()).isEqualTo(DEFAULT_LOAN_GR_NAME);
        assertThat(testGRInterestDetails.getCriteria()).isEqualTo(DEFAULT_CRITERIA);
        assertThat(testGRInterestDetails.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testGRInterestDetails.getIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testGRInterestDetails.getBorrowingInterestRate()).isEqualTo(DEFAULT_BORROWING_INTEREST_RATE);
        assertThat(testGRInterestDetails.getInterestOnLoan()).isEqualTo(DEFAULT_INTEREST_ON_LOAN);
        assertThat(testGRInterestDetails.getPenaltyInterest()).isEqualTo(DEFAULT_PENALTY_INTEREST);
        assertThat(testGRInterestDetails.getSurcharge()).isEqualTo(DEFAULT_SURCHARGE);
        assertThat(testGRInterestDetails.getLoanDuration()).isEqualTo(DEFAULT_LOAN_DURATION);
        assertThat(testGRInterestDetails.getNumberOFInstallment()).isEqualTo(DEFAULT_NUMBER_OF_INSTALLMENT);
        assertThat(testGRInterestDetails.getExtendedInterstRate()).isEqualTo(DEFAULT_EXTENDED_INTERST_RATE);
        assertThat(testGRInterestDetails.getCentralGovSubsidyInterest()).isEqualTo(DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getDistBankSubsidyInterest()).isEqualTo(DEFAULT_DIST_BANK_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getBorrowerInterest()).isEqualTo(DEFAULT_BORROWER_INTEREST);
        assertThat(testGRInterestDetails.getStateGovSubsidyInterest()).isEqualTo(DEFAULT_STATE_GOV_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testGRInterestDetails.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testGRInterestDetails.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testGRInterestDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testGRInterestDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testGRInterestDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGRInterestDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testGRInterestDetails.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testGRInterestDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testGRInterestDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testGRInterestDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createGRInterestDetailsWithExistingId() throws Exception {
        // Create the GRInterestDetails with an existing ID
        gRInterestDetails.setId(1L);
        GRInterestDetailsDTO gRInterestDetailsDTO = gRInterestDetailsMapper.toDto(gRInterestDetails);

        int databaseSizeBeforeCreate = gRInterestDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGRInterestDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gRInterestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGRInterestDetails() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList
        restGRInterestDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gRInterestDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanGrName").value(hasItem(DEFAULT_LOAN_GR_NAME)))
            .andExpect(jsonPath("$.[*].criteria").value(hasItem(DEFAULT_CRITERIA)))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].borrowingInterestRate").value(hasItem(DEFAULT_BORROWING_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestOnLoan").value(hasItem(DEFAULT_INTEREST_ON_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].penaltyInterest").value(hasItem(DEFAULT_PENALTY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].surcharge").value(hasItem(DEFAULT_SURCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].loanDuration").value(hasItem(DEFAULT_LOAN_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOFInstallment").value(hasItem(DEFAULT_NUMBER_OF_INSTALLMENT)))
            .andExpect(jsonPath("$.[*].extendedInterstRate").value(hasItem(DEFAULT_EXTENDED_INTERST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].centralGovSubsidyInterest").value(hasItem(DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].distBankSubsidyInterest").value(hasItem(DEFAULT_DIST_BANK_SUBSIDY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].borrowerInterest").value(hasItem(DEFAULT_BORROWER_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].stateGovSubsidyInterest").value(hasItem(DEFAULT_STATE_GOV_SUBSIDY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getGRInterestDetails() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get the gRInterestDetails
        restGRInterestDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, gRInterestDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gRInterestDetails.getId().intValue()))
            .andExpect(jsonPath("$.loanGrName").value(DEFAULT_LOAN_GR_NAME))
            .andExpect(jsonPath("$.criteria").value(DEFAULT_CRITERIA))
            .andExpect(jsonPath("$.productType").value(DEFAULT_PRODUCT_TYPE))
            .andExpect(jsonPath("$.isActivated").value(DEFAULT_IS_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.borrowingInterestRate").value(DEFAULT_BORROWING_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.interestOnLoan").value(DEFAULT_INTEREST_ON_LOAN.doubleValue()))
            .andExpect(jsonPath("$.penaltyInterest").value(DEFAULT_PENALTY_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.surcharge").value(DEFAULT_SURCHARGE.doubleValue()))
            .andExpect(jsonPath("$.loanDuration").value(DEFAULT_LOAN_DURATION.doubleValue()))
            .andExpect(jsonPath("$.numberOFInstallment").value(DEFAULT_NUMBER_OF_INSTALLMENT))
            .andExpect(jsonPath("$.extendedInterstRate").value(DEFAULT_EXTENDED_INTERST_RATE.doubleValue()))
            .andExpect(jsonPath("$.centralGovSubsidyInterest").value(DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.distBankSubsidyInterest").value(DEFAULT_DIST_BANK_SUBSIDY_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.borrowerInterest").value(DEFAULT_BORROWER_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.stateGovSubsidyInterest").value(DEFAULT_STATE_GOV_SUBSIDY_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getGRInterestDetailsByIdFiltering() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        Long id = gRInterestDetails.getId();

        defaultGRInterestDetailsShouldBeFound("id.equals=" + id);
        defaultGRInterestDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultGRInterestDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGRInterestDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultGRInterestDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGRInterestDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanGrNameIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanGrName equals to DEFAULT_LOAN_GR_NAME
        defaultGRInterestDetailsShouldBeFound("loanGrName.equals=" + DEFAULT_LOAN_GR_NAME);

        // Get all the gRInterestDetailsList where loanGrName equals to UPDATED_LOAN_GR_NAME
        defaultGRInterestDetailsShouldNotBeFound("loanGrName.equals=" + UPDATED_LOAN_GR_NAME);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanGrNameIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanGrName in DEFAULT_LOAN_GR_NAME or UPDATED_LOAN_GR_NAME
        defaultGRInterestDetailsShouldBeFound("loanGrName.in=" + DEFAULT_LOAN_GR_NAME + "," + UPDATED_LOAN_GR_NAME);

        // Get all the gRInterestDetailsList where loanGrName equals to UPDATED_LOAN_GR_NAME
        defaultGRInterestDetailsShouldNotBeFound("loanGrName.in=" + UPDATED_LOAN_GR_NAME);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanGrNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanGrName is not null
        defaultGRInterestDetailsShouldBeFound("loanGrName.specified=true");

        // Get all the gRInterestDetailsList where loanGrName is null
        defaultGRInterestDetailsShouldNotBeFound("loanGrName.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanGrNameContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanGrName contains DEFAULT_LOAN_GR_NAME
        defaultGRInterestDetailsShouldBeFound("loanGrName.contains=" + DEFAULT_LOAN_GR_NAME);

        // Get all the gRInterestDetailsList where loanGrName contains UPDATED_LOAN_GR_NAME
        defaultGRInterestDetailsShouldNotBeFound("loanGrName.contains=" + UPDATED_LOAN_GR_NAME);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanGrNameNotContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanGrName does not contain DEFAULT_LOAN_GR_NAME
        defaultGRInterestDetailsShouldNotBeFound("loanGrName.doesNotContain=" + DEFAULT_LOAN_GR_NAME);

        // Get all the gRInterestDetailsList where loanGrName does not contain UPDATED_LOAN_GR_NAME
        defaultGRInterestDetailsShouldBeFound("loanGrName.doesNotContain=" + UPDATED_LOAN_GR_NAME);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCriteriaIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where criteria equals to DEFAULT_CRITERIA
        defaultGRInterestDetailsShouldBeFound("criteria.equals=" + DEFAULT_CRITERIA);

        // Get all the gRInterestDetailsList where criteria equals to UPDATED_CRITERIA
        defaultGRInterestDetailsShouldNotBeFound("criteria.equals=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCriteriaIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where criteria in DEFAULT_CRITERIA or UPDATED_CRITERIA
        defaultGRInterestDetailsShouldBeFound("criteria.in=" + DEFAULT_CRITERIA + "," + UPDATED_CRITERIA);

        // Get all the gRInterestDetailsList where criteria equals to UPDATED_CRITERIA
        defaultGRInterestDetailsShouldNotBeFound("criteria.in=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCriteriaIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where criteria is not null
        defaultGRInterestDetailsShouldBeFound("criteria.specified=true");

        // Get all the gRInterestDetailsList where criteria is null
        defaultGRInterestDetailsShouldNotBeFound("criteria.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCriteriaContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where criteria contains DEFAULT_CRITERIA
        defaultGRInterestDetailsShouldBeFound("criteria.contains=" + DEFAULT_CRITERIA);

        // Get all the gRInterestDetailsList where criteria contains UPDATED_CRITERIA
        defaultGRInterestDetailsShouldNotBeFound("criteria.contains=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCriteriaNotContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where criteria does not contain DEFAULT_CRITERIA
        defaultGRInterestDetailsShouldNotBeFound("criteria.doesNotContain=" + DEFAULT_CRITERIA);

        // Get all the gRInterestDetailsList where criteria does not contain UPDATED_CRITERIA
        defaultGRInterestDetailsShouldBeFound("criteria.doesNotContain=" + UPDATED_CRITERIA);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where productType equals to DEFAULT_PRODUCT_TYPE
        defaultGRInterestDetailsShouldBeFound("productType.equals=" + DEFAULT_PRODUCT_TYPE);

        // Get all the gRInterestDetailsList where productType equals to UPDATED_PRODUCT_TYPE
        defaultGRInterestDetailsShouldNotBeFound("productType.equals=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByProductTypeIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where productType in DEFAULT_PRODUCT_TYPE or UPDATED_PRODUCT_TYPE
        defaultGRInterestDetailsShouldBeFound("productType.in=" + DEFAULT_PRODUCT_TYPE + "," + UPDATED_PRODUCT_TYPE);

        // Get all the gRInterestDetailsList where productType equals to UPDATED_PRODUCT_TYPE
        defaultGRInterestDetailsShouldNotBeFound("productType.in=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByProductTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where productType is not null
        defaultGRInterestDetailsShouldBeFound("productType.specified=true");

        // Get all the gRInterestDetailsList where productType is null
        defaultGRInterestDetailsShouldNotBeFound("productType.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByProductTypeContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where productType contains DEFAULT_PRODUCT_TYPE
        defaultGRInterestDetailsShouldBeFound("productType.contains=" + DEFAULT_PRODUCT_TYPE);

        // Get all the gRInterestDetailsList where productType contains UPDATED_PRODUCT_TYPE
        defaultGRInterestDetailsShouldNotBeFound("productType.contains=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByProductTypeNotContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where productType does not contain DEFAULT_PRODUCT_TYPE
        defaultGRInterestDetailsShouldNotBeFound("productType.doesNotContain=" + DEFAULT_PRODUCT_TYPE);

        // Get all the gRInterestDetailsList where productType does not contain UPDATED_PRODUCT_TYPE
        defaultGRInterestDetailsShouldBeFound("productType.doesNotContain=" + UPDATED_PRODUCT_TYPE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByIsActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where isActivated equals to DEFAULT_IS_ACTIVATED
        defaultGRInterestDetailsShouldBeFound("isActivated.equals=" + DEFAULT_IS_ACTIVATED);

        // Get all the gRInterestDetailsList where isActivated equals to UPDATED_IS_ACTIVATED
        defaultGRInterestDetailsShouldNotBeFound("isActivated.equals=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByIsActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where isActivated in DEFAULT_IS_ACTIVATED or UPDATED_IS_ACTIVATED
        defaultGRInterestDetailsShouldBeFound("isActivated.in=" + DEFAULT_IS_ACTIVATED + "," + UPDATED_IS_ACTIVATED);

        // Get all the gRInterestDetailsList where isActivated equals to UPDATED_IS_ACTIVATED
        defaultGRInterestDetailsShouldNotBeFound("isActivated.in=" + UPDATED_IS_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByIsActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where isActivated is not null
        defaultGRInterestDetailsShouldBeFound("isActivated.specified=true");

        // Get all the gRInterestDetailsList where isActivated is null
        defaultGRInterestDetailsShouldNotBeFound("isActivated.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowingInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowingInterestRate equals to DEFAULT_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldBeFound("borrowingInterestRate.equals=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the gRInterestDetailsList where borrowingInterestRate equals to UPDATED_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldNotBeFound("borrowingInterestRate.equals=" + UPDATED_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowingInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowingInterestRate in DEFAULT_BORROWING_INTEREST_RATE or UPDATED_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldBeFound(
            "borrowingInterestRate.in=" + DEFAULT_BORROWING_INTEREST_RATE + "," + UPDATED_BORROWING_INTEREST_RATE
        );

        // Get all the gRInterestDetailsList where borrowingInterestRate equals to UPDATED_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldNotBeFound("borrowingInterestRate.in=" + UPDATED_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowingInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowingInterestRate is not null
        defaultGRInterestDetailsShouldBeFound("borrowingInterestRate.specified=true");

        // Get all the gRInterestDetailsList where borrowingInterestRate is null
        defaultGRInterestDetailsShouldNotBeFound("borrowingInterestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowingInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowingInterestRate is greater than or equal to DEFAULT_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldBeFound("borrowingInterestRate.greaterThanOrEqual=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the gRInterestDetailsList where borrowingInterestRate is greater than or equal to UPDATED_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldNotBeFound("borrowingInterestRate.greaterThanOrEqual=" + UPDATED_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowingInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowingInterestRate is less than or equal to DEFAULT_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldBeFound("borrowingInterestRate.lessThanOrEqual=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the gRInterestDetailsList where borrowingInterestRate is less than or equal to SMALLER_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldNotBeFound("borrowingInterestRate.lessThanOrEqual=" + SMALLER_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowingInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowingInterestRate is less than DEFAULT_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldNotBeFound("borrowingInterestRate.lessThan=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the gRInterestDetailsList where borrowingInterestRate is less than UPDATED_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldBeFound("borrowingInterestRate.lessThan=" + UPDATED_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowingInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowingInterestRate is greater than DEFAULT_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldNotBeFound("borrowingInterestRate.greaterThan=" + DEFAULT_BORROWING_INTEREST_RATE);

        // Get all the gRInterestDetailsList where borrowingInterestRate is greater than SMALLER_BORROWING_INTEREST_RATE
        defaultGRInterestDetailsShouldBeFound("borrowingInterestRate.greaterThan=" + SMALLER_BORROWING_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByInterestOnLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where interestOnLoan equals to DEFAULT_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldBeFound("interestOnLoan.equals=" + DEFAULT_INTEREST_ON_LOAN);

        // Get all the gRInterestDetailsList where interestOnLoan equals to UPDATED_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldNotBeFound("interestOnLoan.equals=" + UPDATED_INTEREST_ON_LOAN);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByInterestOnLoanIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where interestOnLoan in DEFAULT_INTEREST_ON_LOAN or UPDATED_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldBeFound("interestOnLoan.in=" + DEFAULT_INTEREST_ON_LOAN + "," + UPDATED_INTEREST_ON_LOAN);

        // Get all the gRInterestDetailsList where interestOnLoan equals to UPDATED_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldNotBeFound("interestOnLoan.in=" + UPDATED_INTEREST_ON_LOAN);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByInterestOnLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where interestOnLoan is not null
        defaultGRInterestDetailsShouldBeFound("interestOnLoan.specified=true");

        // Get all the gRInterestDetailsList where interestOnLoan is null
        defaultGRInterestDetailsShouldNotBeFound("interestOnLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByInterestOnLoanIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where interestOnLoan is greater than or equal to DEFAULT_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldBeFound("interestOnLoan.greaterThanOrEqual=" + DEFAULT_INTEREST_ON_LOAN);

        // Get all the gRInterestDetailsList where interestOnLoan is greater than or equal to UPDATED_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldNotBeFound("interestOnLoan.greaterThanOrEqual=" + UPDATED_INTEREST_ON_LOAN);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByInterestOnLoanIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where interestOnLoan is less than or equal to DEFAULT_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldBeFound("interestOnLoan.lessThanOrEqual=" + DEFAULT_INTEREST_ON_LOAN);

        // Get all the gRInterestDetailsList where interestOnLoan is less than or equal to SMALLER_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldNotBeFound("interestOnLoan.lessThanOrEqual=" + SMALLER_INTEREST_ON_LOAN);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByInterestOnLoanIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where interestOnLoan is less than DEFAULT_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldNotBeFound("interestOnLoan.lessThan=" + DEFAULT_INTEREST_ON_LOAN);

        // Get all the gRInterestDetailsList where interestOnLoan is less than UPDATED_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldBeFound("interestOnLoan.lessThan=" + UPDATED_INTEREST_ON_LOAN);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByInterestOnLoanIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where interestOnLoan is greater than DEFAULT_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldNotBeFound("interestOnLoan.greaterThan=" + DEFAULT_INTEREST_ON_LOAN);

        // Get all the gRInterestDetailsList where interestOnLoan is greater than SMALLER_INTEREST_ON_LOAN
        defaultGRInterestDetailsShouldBeFound("interestOnLoan.greaterThan=" + SMALLER_INTEREST_ON_LOAN);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByPenaltyInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where penaltyInterest equals to DEFAULT_PENALTY_INTEREST
        defaultGRInterestDetailsShouldBeFound("penaltyInterest.equals=" + DEFAULT_PENALTY_INTEREST);

        // Get all the gRInterestDetailsList where penaltyInterest equals to UPDATED_PENALTY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("penaltyInterest.equals=" + UPDATED_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByPenaltyInterestIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where penaltyInterest in DEFAULT_PENALTY_INTEREST or UPDATED_PENALTY_INTEREST
        defaultGRInterestDetailsShouldBeFound("penaltyInterest.in=" + DEFAULT_PENALTY_INTEREST + "," + UPDATED_PENALTY_INTEREST);

        // Get all the gRInterestDetailsList where penaltyInterest equals to UPDATED_PENALTY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("penaltyInterest.in=" + UPDATED_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByPenaltyInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where penaltyInterest is not null
        defaultGRInterestDetailsShouldBeFound("penaltyInterest.specified=true");

        // Get all the gRInterestDetailsList where penaltyInterest is null
        defaultGRInterestDetailsShouldNotBeFound("penaltyInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByPenaltyInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where penaltyInterest is greater than or equal to DEFAULT_PENALTY_INTEREST
        defaultGRInterestDetailsShouldBeFound("penaltyInterest.greaterThanOrEqual=" + DEFAULT_PENALTY_INTEREST);

        // Get all the gRInterestDetailsList where penaltyInterest is greater than or equal to UPDATED_PENALTY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("penaltyInterest.greaterThanOrEqual=" + UPDATED_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByPenaltyInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where penaltyInterest is less than or equal to DEFAULT_PENALTY_INTEREST
        defaultGRInterestDetailsShouldBeFound("penaltyInterest.lessThanOrEqual=" + DEFAULT_PENALTY_INTEREST);

        // Get all the gRInterestDetailsList where penaltyInterest is less than or equal to SMALLER_PENALTY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("penaltyInterest.lessThanOrEqual=" + SMALLER_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByPenaltyInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where penaltyInterest is less than DEFAULT_PENALTY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("penaltyInterest.lessThan=" + DEFAULT_PENALTY_INTEREST);

        // Get all the gRInterestDetailsList where penaltyInterest is less than UPDATED_PENALTY_INTEREST
        defaultGRInterestDetailsShouldBeFound("penaltyInterest.lessThan=" + UPDATED_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByPenaltyInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where penaltyInterest is greater than DEFAULT_PENALTY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("penaltyInterest.greaterThan=" + DEFAULT_PENALTY_INTEREST);

        // Get all the gRInterestDetailsList where penaltyInterest is greater than SMALLER_PENALTY_INTEREST
        defaultGRInterestDetailsShouldBeFound("penaltyInterest.greaterThan=" + SMALLER_PENALTY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsBySurchargeIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where surcharge equals to DEFAULT_SURCHARGE
        defaultGRInterestDetailsShouldBeFound("surcharge.equals=" + DEFAULT_SURCHARGE);

        // Get all the gRInterestDetailsList where surcharge equals to UPDATED_SURCHARGE
        defaultGRInterestDetailsShouldNotBeFound("surcharge.equals=" + UPDATED_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsBySurchargeIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where surcharge in DEFAULT_SURCHARGE or UPDATED_SURCHARGE
        defaultGRInterestDetailsShouldBeFound("surcharge.in=" + DEFAULT_SURCHARGE + "," + UPDATED_SURCHARGE);

        // Get all the gRInterestDetailsList where surcharge equals to UPDATED_SURCHARGE
        defaultGRInterestDetailsShouldNotBeFound("surcharge.in=" + UPDATED_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsBySurchargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where surcharge is not null
        defaultGRInterestDetailsShouldBeFound("surcharge.specified=true");

        // Get all the gRInterestDetailsList where surcharge is null
        defaultGRInterestDetailsShouldNotBeFound("surcharge.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsBySurchargeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where surcharge is greater than or equal to DEFAULT_SURCHARGE
        defaultGRInterestDetailsShouldBeFound("surcharge.greaterThanOrEqual=" + DEFAULT_SURCHARGE);

        // Get all the gRInterestDetailsList where surcharge is greater than or equal to UPDATED_SURCHARGE
        defaultGRInterestDetailsShouldNotBeFound("surcharge.greaterThanOrEqual=" + UPDATED_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsBySurchargeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where surcharge is less than or equal to DEFAULT_SURCHARGE
        defaultGRInterestDetailsShouldBeFound("surcharge.lessThanOrEqual=" + DEFAULT_SURCHARGE);

        // Get all the gRInterestDetailsList where surcharge is less than or equal to SMALLER_SURCHARGE
        defaultGRInterestDetailsShouldNotBeFound("surcharge.lessThanOrEqual=" + SMALLER_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsBySurchargeIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where surcharge is less than DEFAULT_SURCHARGE
        defaultGRInterestDetailsShouldNotBeFound("surcharge.lessThan=" + DEFAULT_SURCHARGE);

        // Get all the gRInterestDetailsList where surcharge is less than UPDATED_SURCHARGE
        defaultGRInterestDetailsShouldBeFound("surcharge.lessThan=" + UPDATED_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsBySurchargeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where surcharge is greater than DEFAULT_SURCHARGE
        defaultGRInterestDetailsShouldNotBeFound("surcharge.greaterThan=" + DEFAULT_SURCHARGE);

        // Get all the gRInterestDetailsList where surcharge is greater than SMALLER_SURCHARGE
        defaultGRInterestDetailsShouldBeFound("surcharge.greaterThan=" + SMALLER_SURCHARGE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanDuration equals to DEFAULT_LOAN_DURATION
        defaultGRInterestDetailsShouldBeFound("loanDuration.equals=" + DEFAULT_LOAN_DURATION);

        // Get all the gRInterestDetailsList where loanDuration equals to UPDATED_LOAN_DURATION
        defaultGRInterestDetailsShouldNotBeFound("loanDuration.equals=" + UPDATED_LOAN_DURATION);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanDurationIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanDuration in DEFAULT_LOAN_DURATION or UPDATED_LOAN_DURATION
        defaultGRInterestDetailsShouldBeFound("loanDuration.in=" + DEFAULT_LOAN_DURATION + "," + UPDATED_LOAN_DURATION);

        // Get all the gRInterestDetailsList where loanDuration equals to UPDATED_LOAN_DURATION
        defaultGRInterestDetailsShouldNotBeFound("loanDuration.in=" + UPDATED_LOAN_DURATION);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanDuration is not null
        defaultGRInterestDetailsShouldBeFound("loanDuration.specified=true");

        // Get all the gRInterestDetailsList where loanDuration is null
        defaultGRInterestDetailsShouldNotBeFound("loanDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanDuration is greater than or equal to DEFAULT_LOAN_DURATION
        defaultGRInterestDetailsShouldBeFound("loanDuration.greaterThanOrEqual=" + DEFAULT_LOAN_DURATION);

        // Get all the gRInterestDetailsList where loanDuration is greater than or equal to UPDATED_LOAN_DURATION
        defaultGRInterestDetailsShouldNotBeFound("loanDuration.greaterThanOrEqual=" + UPDATED_LOAN_DURATION);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanDuration is less than or equal to DEFAULT_LOAN_DURATION
        defaultGRInterestDetailsShouldBeFound("loanDuration.lessThanOrEqual=" + DEFAULT_LOAN_DURATION);

        // Get all the gRInterestDetailsList where loanDuration is less than or equal to SMALLER_LOAN_DURATION
        defaultGRInterestDetailsShouldNotBeFound("loanDuration.lessThanOrEqual=" + SMALLER_LOAN_DURATION);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanDuration is less than DEFAULT_LOAN_DURATION
        defaultGRInterestDetailsShouldNotBeFound("loanDuration.lessThan=" + DEFAULT_LOAN_DURATION);

        // Get all the gRInterestDetailsList where loanDuration is less than UPDATED_LOAN_DURATION
        defaultGRInterestDetailsShouldBeFound("loanDuration.lessThan=" + UPDATED_LOAN_DURATION);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLoanDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where loanDuration is greater than DEFAULT_LOAN_DURATION
        defaultGRInterestDetailsShouldNotBeFound("loanDuration.greaterThan=" + DEFAULT_LOAN_DURATION);

        // Get all the gRInterestDetailsList where loanDuration is greater than SMALLER_LOAN_DURATION
        defaultGRInterestDetailsShouldBeFound("loanDuration.greaterThan=" + SMALLER_LOAN_DURATION);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByNumberOFInstallmentIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where numberOFInstallment equals to DEFAULT_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldBeFound("numberOFInstallment.equals=" + DEFAULT_NUMBER_OF_INSTALLMENT);

        // Get all the gRInterestDetailsList where numberOFInstallment equals to UPDATED_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldNotBeFound("numberOFInstallment.equals=" + UPDATED_NUMBER_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByNumberOFInstallmentIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where numberOFInstallment in DEFAULT_NUMBER_OF_INSTALLMENT or UPDATED_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldBeFound(
            "numberOFInstallment.in=" + DEFAULT_NUMBER_OF_INSTALLMENT + "," + UPDATED_NUMBER_OF_INSTALLMENT
        );

        // Get all the gRInterestDetailsList where numberOFInstallment equals to UPDATED_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldNotBeFound("numberOFInstallment.in=" + UPDATED_NUMBER_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByNumberOFInstallmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where numberOFInstallment is not null
        defaultGRInterestDetailsShouldBeFound("numberOFInstallment.specified=true");

        // Get all the gRInterestDetailsList where numberOFInstallment is null
        defaultGRInterestDetailsShouldNotBeFound("numberOFInstallment.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByNumberOFInstallmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where numberOFInstallment is greater than or equal to DEFAULT_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldBeFound("numberOFInstallment.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_INSTALLMENT);

        // Get all the gRInterestDetailsList where numberOFInstallment is greater than or equal to UPDATED_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldNotBeFound("numberOFInstallment.greaterThanOrEqual=" + UPDATED_NUMBER_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByNumberOFInstallmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where numberOFInstallment is less than or equal to DEFAULT_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldBeFound("numberOFInstallment.lessThanOrEqual=" + DEFAULT_NUMBER_OF_INSTALLMENT);

        // Get all the gRInterestDetailsList where numberOFInstallment is less than or equal to SMALLER_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldNotBeFound("numberOFInstallment.lessThanOrEqual=" + SMALLER_NUMBER_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByNumberOFInstallmentIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where numberOFInstallment is less than DEFAULT_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldNotBeFound("numberOFInstallment.lessThan=" + DEFAULT_NUMBER_OF_INSTALLMENT);

        // Get all the gRInterestDetailsList where numberOFInstallment is less than UPDATED_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldBeFound("numberOFInstallment.lessThan=" + UPDATED_NUMBER_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByNumberOFInstallmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where numberOFInstallment is greater than DEFAULT_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldNotBeFound("numberOFInstallment.greaterThan=" + DEFAULT_NUMBER_OF_INSTALLMENT);

        // Get all the gRInterestDetailsList where numberOFInstallment is greater than SMALLER_NUMBER_OF_INSTALLMENT
        defaultGRInterestDetailsShouldBeFound("numberOFInstallment.greaterThan=" + SMALLER_NUMBER_OF_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByExtendedInterstRateIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where extendedInterstRate equals to DEFAULT_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldBeFound("extendedInterstRate.equals=" + DEFAULT_EXTENDED_INTERST_RATE);

        // Get all the gRInterestDetailsList where extendedInterstRate equals to UPDATED_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldNotBeFound("extendedInterstRate.equals=" + UPDATED_EXTENDED_INTERST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByExtendedInterstRateIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where extendedInterstRate in DEFAULT_EXTENDED_INTERST_RATE or UPDATED_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldBeFound(
            "extendedInterstRate.in=" + DEFAULT_EXTENDED_INTERST_RATE + "," + UPDATED_EXTENDED_INTERST_RATE
        );

        // Get all the gRInterestDetailsList where extendedInterstRate equals to UPDATED_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldNotBeFound("extendedInterstRate.in=" + UPDATED_EXTENDED_INTERST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByExtendedInterstRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where extendedInterstRate is not null
        defaultGRInterestDetailsShouldBeFound("extendedInterstRate.specified=true");

        // Get all the gRInterestDetailsList where extendedInterstRate is null
        defaultGRInterestDetailsShouldNotBeFound("extendedInterstRate.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByExtendedInterstRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where extendedInterstRate is greater than or equal to DEFAULT_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldBeFound("extendedInterstRate.greaterThanOrEqual=" + DEFAULT_EXTENDED_INTERST_RATE);

        // Get all the gRInterestDetailsList where extendedInterstRate is greater than or equal to UPDATED_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldNotBeFound("extendedInterstRate.greaterThanOrEqual=" + UPDATED_EXTENDED_INTERST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByExtendedInterstRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where extendedInterstRate is less than or equal to DEFAULT_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldBeFound("extendedInterstRate.lessThanOrEqual=" + DEFAULT_EXTENDED_INTERST_RATE);

        // Get all the gRInterestDetailsList where extendedInterstRate is less than or equal to SMALLER_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldNotBeFound("extendedInterstRate.lessThanOrEqual=" + SMALLER_EXTENDED_INTERST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByExtendedInterstRateIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where extendedInterstRate is less than DEFAULT_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldNotBeFound("extendedInterstRate.lessThan=" + DEFAULT_EXTENDED_INTERST_RATE);

        // Get all the gRInterestDetailsList where extendedInterstRate is less than UPDATED_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldBeFound("extendedInterstRate.lessThan=" + UPDATED_EXTENDED_INTERST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByExtendedInterstRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where extendedInterstRate is greater than DEFAULT_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldNotBeFound("extendedInterstRate.greaterThan=" + DEFAULT_EXTENDED_INTERST_RATE);

        // Get all the gRInterestDetailsList where extendedInterstRate is greater than SMALLER_EXTENDED_INTERST_RATE
        defaultGRInterestDetailsShouldBeFound("extendedInterstRate.greaterThan=" + SMALLER_EXTENDED_INTERST_RATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCentralGovSubsidyInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest equals to DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("centralGovSubsidyInterest.equals=" + DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest equals to UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("centralGovSubsidyInterest.equals=" + UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCentralGovSubsidyInterestIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest in DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST or UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound(
            "centralGovSubsidyInterest.in=" + DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST + "," + UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST
        );

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest equals to UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("centralGovSubsidyInterest.in=" + UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCentralGovSubsidyInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is not null
        defaultGRInterestDetailsShouldBeFound("centralGovSubsidyInterest.specified=true");

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is null
        defaultGRInterestDetailsShouldNotBeFound("centralGovSubsidyInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCentralGovSubsidyInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is greater than or equal to DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("centralGovSubsidyInterest.greaterThanOrEqual=" + DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is greater than or equal to UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("centralGovSubsidyInterest.greaterThanOrEqual=" + UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCentralGovSubsidyInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is less than or equal to DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("centralGovSubsidyInterest.lessThanOrEqual=" + DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is less than or equal to SMALLER_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("centralGovSubsidyInterest.lessThanOrEqual=" + SMALLER_CENTRAL_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCentralGovSubsidyInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is less than DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("centralGovSubsidyInterest.lessThan=" + DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is less than UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("centralGovSubsidyInterest.lessThan=" + UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCentralGovSubsidyInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is greater than DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("centralGovSubsidyInterest.greaterThan=" + DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where centralGovSubsidyInterest is greater than SMALLER_CENTRAL_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("centralGovSubsidyInterest.greaterThan=" + SMALLER_CENTRAL_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByDistBankSubsidyInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest equals to DEFAULT_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("distBankSubsidyInterest.equals=" + DEFAULT_DIST_BANK_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest equals to UPDATED_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("distBankSubsidyInterest.equals=" + UPDATED_DIST_BANK_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByDistBankSubsidyInterestIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest in DEFAULT_DIST_BANK_SUBSIDY_INTEREST or UPDATED_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound(
            "distBankSubsidyInterest.in=" + DEFAULT_DIST_BANK_SUBSIDY_INTEREST + "," + UPDATED_DIST_BANK_SUBSIDY_INTEREST
        );

        // Get all the gRInterestDetailsList where distBankSubsidyInterest equals to UPDATED_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("distBankSubsidyInterest.in=" + UPDATED_DIST_BANK_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByDistBankSubsidyInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is not null
        defaultGRInterestDetailsShouldBeFound("distBankSubsidyInterest.specified=true");

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is null
        defaultGRInterestDetailsShouldNotBeFound("distBankSubsidyInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByDistBankSubsidyInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is greater than or equal to DEFAULT_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("distBankSubsidyInterest.greaterThanOrEqual=" + DEFAULT_DIST_BANK_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is greater than or equal to UPDATED_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("distBankSubsidyInterest.greaterThanOrEqual=" + UPDATED_DIST_BANK_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByDistBankSubsidyInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is less than or equal to DEFAULT_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("distBankSubsidyInterest.lessThanOrEqual=" + DEFAULT_DIST_BANK_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is less than or equal to SMALLER_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("distBankSubsidyInterest.lessThanOrEqual=" + SMALLER_DIST_BANK_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByDistBankSubsidyInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is less than DEFAULT_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("distBankSubsidyInterest.lessThan=" + DEFAULT_DIST_BANK_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is less than UPDATED_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("distBankSubsidyInterest.lessThan=" + UPDATED_DIST_BANK_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByDistBankSubsidyInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is greater than DEFAULT_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("distBankSubsidyInterest.greaterThan=" + DEFAULT_DIST_BANK_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where distBankSubsidyInterest is greater than SMALLER_DIST_BANK_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("distBankSubsidyInterest.greaterThan=" + SMALLER_DIST_BANK_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowerInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowerInterest equals to DEFAULT_BORROWER_INTEREST
        defaultGRInterestDetailsShouldBeFound("borrowerInterest.equals=" + DEFAULT_BORROWER_INTEREST);

        // Get all the gRInterestDetailsList where borrowerInterest equals to UPDATED_BORROWER_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("borrowerInterest.equals=" + UPDATED_BORROWER_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowerInterestIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowerInterest in DEFAULT_BORROWER_INTEREST or UPDATED_BORROWER_INTEREST
        defaultGRInterestDetailsShouldBeFound("borrowerInterest.in=" + DEFAULT_BORROWER_INTEREST + "," + UPDATED_BORROWER_INTEREST);

        // Get all the gRInterestDetailsList where borrowerInterest equals to UPDATED_BORROWER_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("borrowerInterest.in=" + UPDATED_BORROWER_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowerInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowerInterest is not null
        defaultGRInterestDetailsShouldBeFound("borrowerInterest.specified=true");

        // Get all the gRInterestDetailsList where borrowerInterest is null
        defaultGRInterestDetailsShouldNotBeFound("borrowerInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowerInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowerInterest is greater than or equal to DEFAULT_BORROWER_INTEREST
        defaultGRInterestDetailsShouldBeFound("borrowerInterest.greaterThanOrEqual=" + DEFAULT_BORROWER_INTEREST);

        // Get all the gRInterestDetailsList where borrowerInterest is greater than or equal to UPDATED_BORROWER_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("borrowerInterest.greaterThanOrEqual=" + UPDATED_BORROWER_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowerInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowerInterest is less than or equal to DEFAULT_BORROWER_INTEREST
        defaultGRInterestDetailsShouldBeFound("borrowerInterest.lessThanOrEqual=" + DEFAULT_BORROWER_INTEREST);

        // Get all the gRInterestDetailsList where borrowerInterest is less than or equal to SMALLER_BORROWER_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("borrowerInterest.lessThanOrEqual=" + SMALLER_BORROWER_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowerInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowerInterest is less than DEFAULT_BORROWER_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("borrowerInterest.lessThan=" + DEFAULT_BORROWER_INTEREST);

        // Get all the gRInterestDetailsList where borrowerInterest is less than UPDATED_BORROWER_INTEREST
        defaultGRInterestDetailsShouldBeFound("borrowerInterest.lessThan=" + UPDATED_BORROWER_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByBorrowerInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where borrowerInterest is greater than DEFAULT_BORROWER_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("borrowerInterest.greaterThan=" + DEFAULT_BORROWER_INTEREST);

        // Get all the gRInterestDetailsList where borrowerInterest is greater than SMALLER_BORROWER_INTEREST
        defaultGRInterestDetailsShouldBeFound("borrowerInterest.greaterThan=" + SMALLER_BORROWER_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStateGovSubsidyInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest equals to DEFAULT_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("stateGovSubsidyInterest.equals=" + DEFAULT_STATE_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest equals to UPDATED_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("stateGovSubsidyInterest.equals=" + UPDATED_STATE_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStateGovSubsidyInterestIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest in DEFAULT_STATE_GOV_SUBSIDY_INTEREST or UPDATED_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound(
            "stateGovSubsidyInterest.in=" + DEFAULT_STATE_GOV_SUBSIDY_INTEREST + "," + UPDATED_STATE_GOV_SUBSIDY_INTEREST
        );

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest equals to UPDATED_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("stateGovSubsidyInterest.in=" + UPDATED_STATE_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStateGovSubsidyInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is not null
        defaultGRInterestDetailsShouldBeFound("stateGovSubsidyInterest.specified=true");

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is null
        defaultGRInterestDetailsShouldNotBeFound("stateGovSubsidyInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStateGovSubsidyInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is greater than or equal to DEFAULT_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("stateGovSubsidyInterest.greaterThanOrEqual=" + DEFAULT_STATE_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is greater than or equal to UPDATED_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("stateGovSubsidyInterest.greaterThanOrEqual=" + UPDATED_STATE_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStateGovSubsidyInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is less than or equal to DEFAULT_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("stateGovSubsidyInterest.lessThanOrEqual=" + DEFAULT_STATE_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is less than or equal to SMALLER_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("stateGovSubsidyInterest.lessThanOrEqual=" + SMALLER_STATE_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStateGovSubsidyInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is less than DEFAULT_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("stateGovSubsidyInterest.lessThan=" + DEFAULT_STATE_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is less than UPDATED_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("stateGovSubsidyInterest.lessThan=" + UPDATED_STATE_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStateGovSubsidyInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is greater than DEFAULT_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldNotBeFound("stateGovSubsidyInterest.greaterThan=" + DEFAULT_STATE_GOV_SUBSIDY_INTEREST);

        // Get all the gRInterestDetailsList where stateGovSubsidyInterest is greater than SMALLER_STATE_GOV_SUBSIDY_INTEREST
        defaultGRInterestDetailsShouldBeFound("stateGovSubsidyInterest.greaterThan=" + SMALLER_STATE_GOV_SUBSIDY_INTEREST);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where year equals to DEFAULT_YEAR
        defaultGRInterestDetailsShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the gRInterestDetailsList where year equals to UPDATED_YEAR
        defaultGRInterestDetailsShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultGRInterestDetailsShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the gRInterestDetailsList where year equals to UPDATED_YEAR
        defaultGRInterestDetailsShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where year is not null
        defaultGRInterestDetailsShouldBeFound("year.specified=true");

        // Get all the gRInterestDetailsList where year is null
        defaultGRInterestDetailsShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByYearContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where year contains DEFAULT_YEAR
        defaultGRInterestDetailsShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the gRInterestDetailsList where year contains UPDATED_YEAR
        defaultGRInterestDetailsShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where year does not contain DEFAULT_YEAR
        defaultGRInterestDetailsShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the gRInterestDetailsList where year does not contain UPDATED_YEAR
        defaultGRInterestDetailsShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where startDate equals to DEFAULT_START_DATE
        defaultGRInterestDetailsShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the gRInterestDetailsList where startDate equals to UPDATED_START_DATE
        defaultGRInterestDetailsShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultGRInterestDetailsShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the gRInterestDetailsList where startDate equals to UPDATED_START_DATE
        defaultGRInterestDetailsShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where startDate is not null
        defaultGRInterestDetailsShouldBeFound("startDate.specified=true");

        // Get all the gRInterestDetailsList where startDate is null
        defaultGRInterestDetailsShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where endDate equals to DEFAULT_END_DATE
        defaultGRInterestDetailsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the gRInterestDetailsList where endDate equals to UPDATED_END_DATE
        defaultGRInterestDetailsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultGRInterestDetailsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the gRInterestDetailsList where endDate equals to UPDATED_END_DATE
        defaultGRInterestDetailsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where endDate is not null
        defaultGRInterestDetailsShouldBeFound("endDate.specified=true");

        // Get all the gRInterestDetailsList where endDate is null
        defaultGRInterestDetailsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultGRInterestDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the gRInterestDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultGRInterestDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultGRInterestDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the gRInterestDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultGRInterestDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where lastModified is not null
        defaultGRInterestDetailsShouldBeFound("lastModified.specified=true");

        // Get all the gRInterestDetailsList where lastModified is null
        defaultGRInterestDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultGRInterestDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the gRInterestDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultGRInterestDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultGRInterestDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the gRInterestDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultGRInterestDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where lastModifiedBy is not null
        defaultGRInterestDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the gRInterestDetailsList where lastModifiedBy is null
        defaultGRInterestDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultGRInterestDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the gRInterestDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultGRInterestDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultGRInterestDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the gRInterestDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultGRInterestDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultGRInterestDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the gRInterestDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultGRInterestDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultGRInterestDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the gRInterestDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultGRInterestDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where createdBy is not null
        defaultGRInterestDetailsShouldBeFound("createdBy.specified=true");

        // Get all the gRInterestDetailsList where createdBy is null
        defaultGRInterestDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where createdBy contains DEFAULT_CREATED_BY
        defaultGRInterestDetailsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the gRInterestDetailsList where createdBy contains UPDATED_CREATED_BY
        defaultGRInterestDetailsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultGRInterestDetailsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the gRInterestDetailsList where createdBy does not contain UPDATED_CREATED_BY
        defaultGRInterestDetailsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultGRInterestDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the gRInterestDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultGRInterestDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultGRInterestDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the gRInterestDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultGRInterestDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where createdOn is not null
        defaultGRInterestDetailsShouldBeFound("createdOn.specified=true");

        // Get all the gRInterestDetailsList where createdOn is null
        defaultGRInterestDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultGRInterestDetailsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the gRInterestDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultGRInterestDetailsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultGRInterestDetailsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the gRInterestDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultGRInterestDetailsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where isDeleted is not null
        defaultGRInterestDetailsShouldBeFound("isDeleted.specified=true");

        // Get all the gRInterestDetailsList where isDeleted is null
        defaultGRInterestDetailsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultGRInterestDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the gRInterestDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultGRInterestDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultGRInterestDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the gRInterestDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultGRInterestDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField1 is not null
        defaultGRInterestDetailsShouldBeFound("freeField1.specified=true");

        // Get all the gRInterestDetailsList where freeField1 is null
        defaultGRInterestDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultGRInterestDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the gRInterestDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultGRInterestDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultGRInterestDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the gRInterestDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultGRInterestDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultGRInterestDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the gRInterestDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultGRInterestDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultGRInterestDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the gRInterestDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultGRInterestDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField2 is not null
        defaultGRInterestDetailsShouldBeFound("freeField2.specified=true");

        // Get all the gRInterestDetailsList where freeField2 is null
        defaultGRInterestDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultGRInterestDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the gRInterestDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultGRInterestDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultGRInterestDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the gRInterestDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultGRInterestDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultGRInterestDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the gRInterestDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultGRInterestDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultGRInterestDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the gRInterestDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultGRInterestDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField3 is not null
        defaultGRInterestDetailsShouldBeFound("freeField3.specified=true");

        // Get all the gRInterestDetailsList where freeField3 is null
        defaultGRInterestDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultGRInterestDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the gRInterestDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultGRInterestDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        // Get all the gRInterestDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultGRInterestDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the gRInterestDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultGRInterestDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGRInterestDetailsBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        gRInterestDetails.setSociety(society);
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);
        Long societyId = society.getId();

        // Get all the gRInterestDetailsList where society equals to societyId
        defaultGRInterestDetailsShouldBeFound("societyId.equals=" + societyId);

        // Get all the gRInterestDetailsList where society equals to (societyId + 1)
        defaultGRInterestDetailsShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGRInterestDetailsShouldBeFound(String filter) throws Exception {
        restGRInterestDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gRInterestDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanGrName").value(hasItem(DEFAULT_LOAN_GR_NAME)))
            .andExpect(jsonPath("$.[*].criteria").value(hasItem(DEFAULT_CRITERIA)))
            .andExpect(jsonPath("$.[*].productType").value(hasItem(DEFAULT_PRODUCT_TYPE)))
            .andExpect(jsonPath("$.[*].isActivated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].borrowingInterestRate").value(hasItem(DEFAULT_BORROWING_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].interestOnLoan").value(hasItem(DEFAULT_INTEREST_ON_LOAN.doubleValue())))
            .andExpect(jsonPath("$.[*].penaltyInterest").value(hasItem(DEFAULT_PENALTY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].surcharge").value(hasItem(DEFAULT_SURCHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].loanDuration").value(hasItem(DEFAULT_LOAN_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOFInstallment").value(hasItem(DEFAULT_NUMBER_OF_INSTALLMENT)))
            .andExpect(jsonPath("$.[*].extendedInterstRate").value(hasItem(DEFAULT_EXTENDED_INTERST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].centralGovSubsidyInterest").value(hasItem(DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].distBankSubsidyInterest").value(hasItem(DEFAULT_DIST_BANK_SUBSIDY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].borrowerInterest").value(hasItem(DEFAULT_BORROWER_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].stateGovSubsidyInterest").value(hasItem(DEFAULT_STATE_GOV_SUBSIDY_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restGRInterestDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGRInterestDetailsShouldNotBeFound(String filter) throws Exception {
        restGRInterestDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGRInterestDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGRInterestDetails() throws Exception {
        // Get the gRInterestDetails
        restGRInterestDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGRInterestDetails() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        int databaseSizeBeforeUpdate = gRInterestDetailsRepository.findAll().size();

        // Update the gRInterestDetails
        GRInterestDetails updatedGRInterestDetails = gRInterestDetailsRepository.findById(gRInterestDetails.getId()).get();
        // Disconnect from session so that the updates on updatedGRInterestDetails are not directly saved in db
        em.detach(updatedGRInterestDetails);
        updatedGRInterestDetails
            .loanGrName(UPDATED_LOAN_GR_NAME)
            .criteria(UPDATED_CRITERIA)
            .productType(UPDATED_PRODUCT_TYPE)
            .isActivated(UPDATED_IS_ACTIVATED)
            .borrowingInterestRate(UPDATED_BORROWING_INTEREST_RATE)
            .interestOnLoan(UPDATED_INTEREST_ON_LOAN)
            .penaltyInterest(UPDATED_PENALTY_INTEREST)
            .surcharge(UPDATED_SURCHARGE)
            .loanDuration(UPDATED_LOAN_DURATION)
            .numberOFInstallment(UPDATED_NUMBER_OF_INSTALLMENT)
            .extendedInterstRate(UPDATED_EXTENDED_INTERST_RATE)
            .centralGovSubsidyInterest(UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST)
            .distBankSubsidyInterest(UPDATED_DIST_BANK_SUBSIDY_INTEREST)
            .borrowerInterest(UPDATED_BORROWER_INTEREST)
            .stateGovSubsidyInterest(UPDATED_STATE_GOV_SUBSIDY_INTEREST)
            .year(UPDATED_YEAR)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        GRInterestDetailsDTO gRInterestDetailsDTO = gRInterestDetailsMapper.toDto(updatedGRInterestDetails);

        restGRInterestDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gRInterestDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gRInterestDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeUpdate);
        GRInterestDetails testGRInterestDetails = gRInterestDetailsList.get(gRInterestDetailsList.size() - 1);
        assertThat(testGRInterestDetails.getLoanGrName()).isEqualTo(UPDATED_LOAN_GR_NAME);
        assertThat(testGRInterestDetails.getCriteria()).isEqualTo(UPDATED_CRITERIA);
        assertThat(testGRInterestDetails.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testGRInterestDetails.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testGRInterestDetails.getBorrowingInterestRate()).isEqualTo(UPDATED_BORROWING_INTEREST_RATE);
        assertThat(testGRInterestDetails.getInterestOnLoan()).isEqualTo(UPDATED_INTEREST_ON_LOAN);
        assertThat(testGRInterestDetails.getPenaltyInterest()).isEqualTo(UPDATED_PENALTY_INTEREST);
        assertThat(testGRInterestDetails.getSurcharge()).isEqualTo(UPDATED_SURCHARGE);
        assertThat(testGRInterestDetails.getLoanDuration()).isEqualTo(UPDATED_LOAN_DURATION);
        assertThat(testGRInterestDetails.getNumberOFInstallment()).isEqualTo(UPDATED_NUMBER_OF_INSTALLMENT);
        assertThat(testGRInterestDetails.getExtendedInterstRate()).isEqualTo(UPDATED_EXTENDED_INTERST_RATE);
        assertThat(testGRInterestDetails.getCentralGovSubsidyInterest()).isEqualTo(UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getDistBankSubsidyInterest()).isEqualTo(UPDATED_DIST_BANK_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getBorrowerInterest()).isEqualTo(UPDATED_BORROWER_INTEREST);
        assertThat(testGRInterestDetails.getStateGovSubsidyInterest()).isEqualTo(UPDATED_STATE_GOV_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testGRInterestDetails.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testGRInterestDetails.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testGRInterestDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testGRInterestDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGRInterestDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGRInterestDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGRInterestDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testGRInterestDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testGRInterestDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testGRInterestDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingGRInterestDetails() throws Exception {
        int databaseSizeBeforeUpdate = gRInterestDetailsRepository.findAll().size();
        gRInterestDetails.setId(count.incrementAndGet());

        // Create the GRInterestDetails
        GRInterestDetailsDTO gRInterestDetailsDTO = gRInterestDetailsMapper.toDto(gRInterestDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGRInterestDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gRInterestDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gRInterestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGRInterestDetails() throws Exception {
        int databaseSizeBeforeUpdate = gRInterestDetailsRepository.findAll().size();
        gRInterestDetails.setId(count.incrementAndGet());

        // Create the GRInterestDetails
        GRInterestDetailsDTO gRInterestDetailsDTO = gRInterestDetailsMapper.toDto(gRInterestDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGRInterestDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gRInterestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGRInterestDetails() throws Exception {
        int databaseSizeBeforeUpdate = gRInterestDetailsRepository.findAll().size();
        gRInterestDetails.setId(count.incrementAndGet());

        // Create the GRInterestDetails
        GRInterestDetailsDTO gRInterestDetailsDTO = gRInterestDetailsMapper.toDto(gRInterestDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGRInterestDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gRInterestDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGRInterestDetailsWithPatch() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        int databaseSizeBeforeUpdate = gRInterestDetailsRepository.findAll().size();

        // Update the gRInterestDetails using partial update
        GRInterestDetails partialUpdatedGRInterestDetails = new GRInterestDetails();
        partialUpdatedGRInterestDetails.setId(gRInterestDetails.getId());

        partialUpdatedGRInterestDetails
            .borrowingInterestRate(UPDATED_BORROWING_INTEREST_RATE)
            .loanDuration(UPDATED_LOAN_DURATION)
            .numberOFInstallment(UPDATED_NUMBER_OF_INSTALLMENT)
            .extendedInterstRate(UPDATED_EXTENDED_INTERST_RATE)
            .distBankSubsidyInterest(UPDATED_DIST_BANK_SUBSIDY_INTEREST)
            .stateGovSubsidyInterest(UPDATED_STATE_GOV_SUBSIDY_INTEREST)
            .startDate(UPDATED_START_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField2(UPDATED_FREE_FIELD_2);

        restGRInterestDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGRInterestDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGRInterestDetails))
            )
            .andExpect(status().isOk());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeUpdate);
        GRInterestDetails testGRInterestDetails = gRInterestDetailsList.get(gRInterestDetailsList.size() - 1);
        assertThat(testGRInterestDetails.getLoanGrName()).isEqualTo(DEFAULT_LOAN_GR_NAME);
        assertThat(testGRInterestDetails.getCriteria()).isEqualTo(DEFAULT_CRITERIA);
        assertThat(testGRInterestDetails.getProductType()).isEqualTo(DEFAULT_PRODUCT_TYPE);
        assertThat(testGRInterestDetails.getIsActivated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testGRInterestDetails.getBorrowingInterestRate()).isEqualTo(UPDATED_BORROWING_INTEREST_RATE);
        assertThat(testGRInterestDetails.getInterestOnLoan()).isEqualTo(DEFAULT_INTEREST_ON_LOAN);
        assertThat(testGRInterestDetails.getPenaltyInterest()).isEqualTo(DEFAULT_PENALTY_INTEREST);
        assertThat(testGRInterestDetails.getSurcharge()).isEqualTo(DEFAULT_SURCHARGE);
        assertThat(testGRInterestDetails.getLoanDuration()).isEqualTo(UPDATED_LOAN_DURATION);
        assertThat(testGRInterestDetails.getNumberOFInstallment()).isEqualTo(UPDATED_NUMBER_OF_INSTALLMENT);
        assertThat(testGRInterestDetails.getExtendedInterstRate()).isEqualTo(UPDATED_EXTENDED_INTERST_RATE);
        assertThat(testGRInterestDetails.getCentralGovSubsidyInterest()).isEqualTo(DEFAULT_CENTRAL_GOV_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getDistBankSubsidyInterest()).isEqualTo(UPDATED_DIST_BANK_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getBorrowerInterest()).isEqualTo(DEFAULT_BORROWER_INTEREST);
        assertThat(testGRInterestDetails.getStateGovSubsidyInterest()).isEqualTo(UPDATED_STATE_GOV_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testGRInterestDetails.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testGRInterestDetails.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testGRInterestDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testGRInterestDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGRInterestDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGRInterestDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGRInterestDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testGRInterestDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testGRInterestDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testGRInterestDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateGRInterestDetailsWithPatch() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        int databaseSizeBeforeUpdate = gRInterestDetailsRepository.findAll().size();

        // Update the gRInterestDetails using partial update
        GRInterestDetails partialUpdatedGRInterestDetails = new GRInterestDetails();
        partialUpdatedGRInterestDetails.setId(gRInterestDetails.getId());

        partialUpdatedGRInterestDetails
            .loanGrName(UPDATED_LOAN_GR_NAME)
            .criteria(UPDATED_CRITERIA)
            .productType(UPDATED_PRODUCT_TYPE)
            .isActivated(UPDATED_IS_ACTIVATED)
            .borrowingInterestRate(UPDATED_BORROWING_INTEREST_RATE)
            .interestOnLoan(UPDATED_INTEREST_ON_LOAN)
            .penaltyInterest(UPDATED_PENALTY_INTEREST)
            .surcharge(UPDATED_SURCHARGE)
            .loanDuration(UPDATED_LOAN_DURATION)
            .numberOFInstallment(UPDATED_NUMBER_OF_INSTALLMENT)
            .extendedInterstRate(UPDATED_EXTENDED_INTERST_RATE)
            .centralGovSubsidyInterest(UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST)
            .distBankSubsidyInterest(UPDATED_DIST_BANK_SUBSIDY_INTEREST)
            .borrowerInterest(UPDATED_BORROWER_INTEREST)
            .stateGovSubsidyInterest(UPDATED_STATE_GOV_SUBSIDY_INTEREST)
            .year(UPDATED_YEAR)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restGRInterestDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGRInterestDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGRInterestDetails))
            )
            .andExpect(status().isOk());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeUpdate);
        GRInterestDetails testGRInterestDetails = gRInterestDetailsList.get(gRInterestDetailsList.size() - 1);
        assertThat(testGRInterestDetails.getLoanGrName()).isEqualTo(UPDATED_LOAN_GR_NAME);
        assertThat(testGRInterestDetails.getCriteria()).isEqualTo(UPDATED_CRITERIA);
        assertThat(testGRInterestDetails.getProductType()).isEqualTo(UPDATED_PRODUCT_TYPE);
        assertThat(testGRInterestDetails.getIsActivated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testGRInterestDetails.getBorrowingInterestRate()).isEqualTo(UPDATED_BORROWING_INTEREST_RATE);
        assertThat(testGRInterestDetails.getInterestOnLoan()).isEqualTo(UPDATED_INTEREST_ON_LOAN);
        assertThat(testGRInterestDetails.getPenaltyInterest()).isEqualTo(UPDATED_PENALTY_INTEREST);
        assertThat(testGRInterestDetails.getSurcharge()).isEqualTo(UPDATED_SURCHARGE);
        assertThat(testGRInterestDetails.getLoanDuration()).isEqualTo(UPDATED_LOAN_DURATION);
        assertThat(testGRInterestDetails.getNumberOFInstallment()).isEqualTo(UPDATED_NUMBER_OF_INSTALLMENT);
        assertThat(testGRInterestDetails.getExtendedInterstRate()).isEqualTo(UPDATED_EXTENDED_INTERST_RATE);
        assertThat(testGRInterestDetails.getCentralGovSubsidyInterest()).isEqualTo(UPDATED_CENTRAL_GOV_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getDistBankSubsidyInterest()).isEqualTo(UPDATED_DIST_BANK_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getBorrowerInterest()).isEqualTo(UPDATED_BORROWER_INTEREST);
        assertThat(testGRInterestDetails.getStateGovSubsidyInterest()).isEqualTo(UPDATED_STATE_GOV_SUBSIDY_INTEREST);
        assertThat(testGRInterestDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testGRInterestDetails.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testGRInterestDetails.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testGRInterestDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testGRInterestDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGRInterestDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGRInterestDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGRInterestDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testGRInterestDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testGRInterestDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testGRInterestDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingGRInterestDetails() throws Exception {
        int databaseSizeBeforeUpdate = gRInterestDetailsRepository.findAll().size();
        gRInterestDetails.setId(count.incrementAndGet());

        // Create the GRInterestDetails
        GRInterestDetailsDTO gRInterestDetailsDTO = gRInterestDetailsMapper.toDto(gRInterestDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGRInterestDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gRInterestDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gRInterestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGRInterestDetails() throws Exception {
        int databaseSizeBeforeUpdate = gRInterestDetailsRepository.findAll().size();
        gRInterestDetails.setId(count.incrementAndGet());

        // Create the GRInterestDetails
        GRInterestDetailsDTO gRInterestDetailsDTO = gRInterestDetailsMapper.toDto(gRInterestDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGRInterestDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gRInterestDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGRInterestDetails() throws Exception {
        int databaseSizeBeforeUpdate = gRInterestDetailsRepository.findAll().size();
        gRInterestDetails.setId(count.incrementAndGet());

        // Create the GRInterestDetails
        GRInterestDetailsDTO gRInterestDetailsDTO = gRInterestDetailsMapper.toDto(gRInterestDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGRInterestDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gRInterestDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GRInterestDetails in the database
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGRInterestDetails() throws Exception {
        // Initialize the database
        gRInterestDetailsRepository.saveAndFlush(gRInterestDetails);

        int databaseSizeBeforeDelete = gRInterestDetailsRepository.findAll().size();

        // Delete the gRInterestDetails
        restGRInterestDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, gRInterestDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GRInterestDetails> gRInterestDetailsList = gRInterestDetailsRepository.findAll();
        assertThat(gRInterestDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.LoanDemand;
import com.vgtech.vks.app.domain.LoanWatapDetails;
import com.vgtech.vks.app.domain.enumeration.LoanStatus;
import com.vgtech.vks.app.repository.LoanWatapDetailsRepository;
import com.vgtech.vks.app.service.criteria.LoanWatapDetailsCriteria;
import com.vgtech.vks.app.service.dto.LoanWatapDetailsDTO;
import com.vgtech.vks.app.service.mapper.LoanWatapDetailsMapper;
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
 * Integration tests for the {@link LoanWatapDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanWatapDetailsResourceIT {

    private static final Instant DEFAULT_LOAN_WATAP_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_WATAP_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_CROP_LAND_IN_HECTOR = 1D;
    private static final Double UPDATED_CROP_LAND_IN_HECTOR = 2D;
    private static final Double SMALLER_CROP_LAND_IN_HECTOR = 1D - 1D;

    private static final Integer DEFAULT_SLOT_NUMBER = 1;
    private static final Integer UPDATED_SLOT_NUMBER = 2;
    private static final Integer SMALLER_SLOT_NUMBER = 1 - 1;

    private static final Double DEFAULT_LOAN_AMOUNT = 1D;
    private static final Double UPDATED_LOAN_AMOUNT = 2D;
    private static final Double SMALLER_LOAN_AMOUNT = 1D - 1D;

    private static final String DEFAULT_SEASON = "AAAAAAAAAA";
    private static final String UPDATED_SEASON = "BBBBBBBBBB";

    private static final LoanStatus DEFAULT_STATUS = LoanStatus.APPLIED;
    private static final LoanStatus UPDATED_STATUS = LoanStatus.PENDING;

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

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

    private static final String ENTITY_API_URL = "/api/loan-watap-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanWatapDetailsRepository loanWatapDetailsRepository;

    @Autowired
    private LoanWatapDetailsMapper loanWatapDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanWatapDetailsMockMvc;

    private LoanWatapDetails loanWatapDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanWatapDetails createEntity(EntityManager em) {
        LoanWatapDetails loanWatapDetails = new LoanWatapDetails()
            .loanWatapDate(DEFAULT_LOAN_WATAP_DATE)
            .cropLandInHector(DEFAULT_CROP_LAND_IN_HECTOR)
            .slotNumber(DEFAULT_SLOT_NUMBER)
            .loanAmount(DEFAULT_LOAN_AMOUNT)
            .season(DEFAULT_SEASON)
            .status(DEFAULT_STATUS)
            .year(DEFAULT_YEAR)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return loanWatapDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanWatapDetails createUpdatedEntity(EntityManager em) {
        LoanWatapDetails loanWatapDetails = new LoanWatapDetails()
            .loanWatapDate(UPDATED_LOAN_WATAP_DATE)
            .cropLandInHector(UPDATED_CROP_LAND_IN_HECTOR)
            .slotNumber(UPDATED_SLOT_NUMBER)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .season(UPDATED_SEASON)
            .status(UPDATED_STATUS)
            .year(UPDATED_YEAR)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return loanWatapDetails;
    }

    @BeforeEach
    public void initTest() {
        loanWatapDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanWatapDetails() throws Exception {
        int databaseSizeBeforeCreate = loanWatapDetailsRepository.findAll().size();
        // Create the LoanWatapDetails
        LoanWatapDetailsDTO loanWatapDetailsDTO = loanWatapDetailsMapper.toDto(loanWatapDetails);
        restLoanWatapDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanWatapDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        LoanWatapDetails testLoanWatapDetails = loanWatapDetailsList.get(loanWatapDetailsList.size() - 1);
        assertThat(testLoanWatapDetails.getLoanWatapDate()).isEqualTo(DEFAULT_LOAN_WATAP_DATE);
        assertThat(testLoanWatapDetails.getCropLandInHector()).isEqualTo(DEFAULT_CROP_LAND_IN_HECTOR);
        assertThat(testLoanWatapDetails.getSlotNumber()).isEqualTo(DEFAULT_SLOT_NUMBER);
        assertThat(testLoanWatapDetails.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testLoanWatapDetails.getSeason()).isEqualTo(DEFAULT_SEASON);
        assertThat(testLoanWatapDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoanWatapDetails.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLoanWatapDetails.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testLoanWatapDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanWatapDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanWatapDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanWatapDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanWatapDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createLoanWatapDetailsWithExistingId() throws Exception {
        // Create the LoanWatapDetails with an existing ID
        loanWatapDetails.setId(1L);
        LoanWatapDetailsDTO loanWatapDetailsDTO = loanWatapDetailsMapper.toDto(loanWatapDetails);

        int databaseSizeBeforeCreate = loanWatapDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanWatapDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanWatapDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetails() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList
        restLoanWatapDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanWatapDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanWatapDate").value(hasItem(DEFAULT_LOAN_WATAP_DATE.toString())))
            .andExpect(jsonPath("$.[*].cropLandInHector").value(hasItem(DEFAULT_CROP_LAND_IN_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].slotNumber").value(hasItem(DEFAULT_SLOT_NUMBER)))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].season").value(hasItem(DEFAULT_SEASON)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getLoanWatapDetails() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get the loanWatapDetails
        restLoanWatapDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, loanWatapDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanWatapDetails.getId().intValue()))
            .andExpect(jsonPath("$.loanWatapDate").value(DEFAULT_LOAN_WATAP_DATE.toString()))
            .andExpect(jsonPath("$.cropLandInHector").value(DEFAULT_CROP_LAND_IN_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.slotNumber").value(DEFAULT_SLOT_NUMBER))
            .andExpect(jsonPath("$.loanAmount").value(DEFAULT_LOAN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.season").value(DEFAULT_SEASON))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getLoanWatapDetailsByIdFiltering() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        Long id = loanWatapDetails.getId();

        defaultLoanWatapDetailsShouldBeFound("id.equals=" + id);
        defaultLoanWatapDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultLoanWatapDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanWatapDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanWatapDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanWatapDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanWatapDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanWatapDate equals to DEFAULT_LOAN_WATAP_DATE
        defaultLoanWatapDetailsShouldBeFound("loanWatapDate.equals=" + DEFAULT_LOAN_WATAP_DATE);

        // Get all the loanWatapDetailsList where loanWatapDate equals to UPDATED_LOAN_WATAP_DATE
        defaultLoanWatapDetailsShouldNotBeFound("loanWatapDate.equals=" + UPDATED_LOAN_WATAP_DATE);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanWatapDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanWatapDate in DEFAULT_LOAN_WATAP_DATE or UPDATED_LOAN_WATAP_DATE
        defaultLoanWatapDetailsShouldBeFound("loanWatapDate.in=" + DEFAULT_LOAN_WATAP_DATE + "," + UPDATED_LOAN_WATAP_DATE);

        // Get all the loanWatapDetailsList where loanWatapDate equals to UPDATED_LOAN_WATAP_DATE
        defaultLoanWatapDetailsShouldNotBeFound("loanWatapDate.in=" + UPDATED_LOAN_WATAP_DATE);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanWatapDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanWatapDate is not null
        defaultLoanWatapDetailsShouldBeFound("loanWatapDate.specified=true");

        // Get all the loanWatapDetailsList where loanWatapDate is null
        defaultLoanWatapDetailsShouldNotBeFound("loanWatapDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByCropLandInHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where cropLandInHector equals to DEFAULT_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldBeFound("cropLandInHector.equals=" + DEFAULT_CROP_LAND_IN_HECTOR);

        // Get all the loanWatapDetailsList where cropLandInHector equals to UPDATED_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldNotBeFound("cropLandInHector.equals=" + UPDATED_CROP_LAND_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByCropLandInHectorIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where cropLandInHector in DEFAULT_CROP_LAND_IN_HECTOR or UPDATED_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldBeFound("cropLandInHector.in=" + DEFAULT_CROP_LAND_IN_HECTOR + "," + UPDATED_CROP_LAND_IN_HECTOR);

        // Get all the loanWatapDetailsList where cropLandInHector equals to UPDATED_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldNotBeFound("cropLandInHector.in=" + UPDATED_CROP_LAND_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByCropLandInHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where cropLandInHector is not null
        defaultLoanWatapDetailsShouldBeFound("cropLandInHector.specified=true");

        // Get all the loanWatapDetailsList where cropLandInHector is null
        defaultLoanWatapDetailsShouldNotBeFound("cropLandInHector.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByCropLandInHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where cropLandInHector is greater than or equal to DEFAULT_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldBeFound("cropLandInHector.greaterThanOrEqual=" + DEFAULT_CROP_LAND_IN_HECTOR);

        // Get all the loanWatapDetailsList where cropLandInHector is greater than or equal to UPDATED_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldNotBeFound("cropLandInHector.greaterThanOrEqual=" + UPDATED_CROP_LAND_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByCropLandInHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where cropLandInHector is less than or equal to DEFAULT_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldBeFound("cropLandInHector.lessThanOrEqual=" + DEFAULT_CROP_LAND_IN_HECTOR);

        // Get all the loanWatapDetailsList where cropLandInHector is less than or equal to SMALLER_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldNotBeFound("cropLandInHector.lessThanOrEqual=" + SMALLER_CROP_LAND_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByCropLandInHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where cropLandInHector is less than DEFAULT_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldNotBeFound("cropLandInHector.lessThan=" + DEFAULT_CROP_LAND_IN_HECTOR);

        // Get all the loanWatapDetailsList where cropLandInHector is less than UPDATED_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldBeFound("cropLandInHector.lessThan=" + UPDATED_CROP_LAND_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByCropLandInHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where cropLandInHector is greater than DEFAULT_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldNotBeFound("cropLandInHector.greaterThan=" + DEFAULT_CROP_LAND_IN_HECTOR);

        // Get all the loanWatapDetailsList where cropLandInHector is greater than SMALLER_CROP_LAND_IN_HECTOR
        defaultLoanWatapDetailsShouldBeFound("cropLandInHector.greaterThan=" + SMALLER_CROP_LAND_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySlotNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where slotNumber equals to DEFAULT_SLOT_NUMBER
        defaultLoanWatapDetailsShouldBeFound("slotNumber.equals=" + DEFAULT_SLOT_NUMBER);

        // Get all the loanWatapDetailsList where slotNumber equals to UPDATED_SLOT_NUMBER
        defaultLoanWatapDetailsShouldNotBeFound("slotNumber.equals=" + UPDATED_SLOT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySlotNumberIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where slotNumber in DEFAULT_SLOT_NUMBER or UPDATED_SLOT_NUMBER
        defaultLoanWatapDetailsShouldBeFound("slotNumber.in=" + DEFAULT_SLOT_NUMBER + "," + UPDATED_SLOT_NUMBER);

        // Get all the loanWatapDetailsList where slotNumber equals to UPDATED_SLOT_NUMBER
        defaultLoanWatapDetailsShouldNotBeFound("slotNumber.in=" + UPDATED_SLOT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySlotNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where slotNumber is not null
        defaultLoanWatapDetailsShouldBeFound("slotNumber.specified=true");

        // Get all the loanWatapDetailsList where slotNumber is null
        defaultLoanWatapDetailsShouldNotBeFound("slotNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySlotNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where slotNumber is greater than or equal to DEFAULT_SLOT_NUMBER
        defaultLoanWatapDetailsShouldBeFound("slotNumber.greaterThanOrEqual=" + DEFAULT_SLOT_NUMBER);

        // Get all the loanWatapDetailsList where slotNumber is greater than or equal to UPDATED_SLOT_NUMBER
        defaultLoanWatapDetailsShouldNotBeFound("slotNumber.greaterThanOrEqual=" + UPDATED_SLOT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySlotNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where slotNumber is less than or equal to DEFAULT_SLOT_NUMBER
        defaultLoanWatapDetailsShouldBeFound("slotNumber.lessThanOrEqual=" + DEFAULT_SLOT_NUMBER);

        // Get all the loanWatapDetailsList where slotNumber is less than or equal to SMALLER_SLOT_NUMBER
        defaultLoanWatapDetailsShouldNotBeFound("slotNumber.lessThanOrEqual=" + SMALLER_SLOT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySlotNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where slotNumber is less than DEFAULT_SLOT_NUMBER
        defaultLoanWatapDetailsShouldNotBeFound("slotNumber.lessThan=" + DEFAULT_SLOT_NUMBER);

        // Get all the loanWatapDetailsList where slotNumber is less than UPDATED_SLOT_NUMBER
        defaultLoanWatapDetailsShouldBeFound("slotNumber.lessThan=" + UPDATED_SLOT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySlotNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where slotNumber is greater than DEFAULT_SLOT_NUMBER
        defaultLoanWatapDetailsShouldNotBeFound("slotNumber.greaterThan=" + DEFAULT_SLOT_NUMBER);

        // Get all the loanWatapDetailsList where slotNumber is greater than SMALLER_SLOT_NUMBER
        defaultLoanWatapDetailsShouldBeFound("slotNumber.greaterThan=" + SMALLER_SLOT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanAmount equals to DEFAULT_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldBeFound("loanAmount.equals=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanWatapDetailsList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldNotBeFound("loanAmount.equals=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanAmount in DEFAULT_LOAN_AMOUNT or UPDATED_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldBeFound("loanAmount.in=" + DEFAULT_LOAN_AMOUNT + "," + UPDATED_LOAN_AMOUNT);

        // Get all the loanWatapDetailsList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldNotBeFound("loanAmount.in=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanAmount is not null
        defaultLoanWatapDetailsShouldBeFound("loanAmount.specified=true");

        // Get all the loanWatapDetailsList where loanAmount is null
        defaultLoanWatapDetailsShouldNotBeFound("loanAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanAmount is greater than or equal to DEFAULT_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldBeFound("loanAmount.greaterThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanWatapDetailsList where loanAmount is greater than or equal to UPDATED_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldNotBeFound("loanAmount.greaterThanOrEqual=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanAmount is less than or equal to DEFAULT_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldBeFound("loanAmount.lessThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanWatapDetailsList where loanAmount is less than or equal to SMALLER_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldNotBeFound("loanAmount.lessThanOrEqual=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanAmount is less than DEFAULT_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldNotBeFound("loanAmount.lessThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanWatapDetailsList where loanAmount is less than UPDATED_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldBeFound("loanAmount.lessThan=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where loanAmount is greater than DEFAULT_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldNotBeFound("loanAmount.greaterThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanWatapDetailsList where loanAmount is greater than SMALLER_LOAN_AMOUNT
        defaultLoanWatapDetailsShouldBeFound("loanAmount.greaterThan=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySeasonIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where season equals to DEFAULT_SEASON
        defaultLoanWatapDetailsShouldBeFound("season.equals=" + DEFAULT_SEASON);

        // Get all the loanWatapDetailsList where season equals to UPDATED_SEASON
        defaultLoanWatapDetailsShouldNotBeFound("season.equals=" + UPDATED_SEASON);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySeasonIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where season in DEFAULT_SEASON or UPDATED_SEASON
        defaultLoanWatapDetailsShouldBeFound("season.in=" + DEFAULT_SEASON + "," + UPDATED_SEASON);

        // Get all the loanWatapDetailsList where season equals to UPDATED_SEASON
        defaultLoanWatapDetailsShouldNotBeFound("season.in=" + UPDATED_SEASON);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySeasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where season is not null
        defaultLoanWatapDetailsShouldBeFound("season.specified=true");

        // Get all the loanWatapDetailsList where season is null
        defaultLoanWatapDetailsShouldNotBeFound("season.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySeasonContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where season contains DEFAULT_SEASON
        defaultLoanWatapDetailsShouldBeFound("season.contains=" + DEFAULT_SEASON);

        // Get all the loanWatapDetailsList where season contains UPDATED_SEASON
        defaultLoanWatapDetailsShouldNotBeFound("season.contains=" + UPDATED_SEASON);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsBySeasonNotContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where season does not contain DEFAULT_SEASON
        defaultLoanWatapDetailsShouldNotBeFound("season.doesNotContain=" + DEFAULT_SEASON);

        // Get all the loanWatapDetailsList where season does not contain UPDATED_SEASON
        defaultLoanWatapDetailsShouldBeFound("season.doesNotContain=" + UPDATED_SEASON);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where status equals to DEFAULT_STATUS
        defaultLoanWatapDetailsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the loanWatapDetailsList where status equals to UPDATED_STATUS
        defaultLoanWatapDetailsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLoanWatapDetailsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the loanWatapDetailsList where status equals to UPDATED_STATUS
        defaultLoanWatapDetailsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where status is not null
        defaultLoanWatapDetailsShouldBeFound("status.specified=true");

        // Get all the loanWatapDetailsList where status is null
        defaultLoanWatapDetailsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where year equals to DEFAULT_YEAR
        defaultLoanWatapDetailsShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the loanWatapDetailsList where year equals to UPDATED_YEAR
        defaultLoanWatapDetailsShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultLoanWatapDetailsShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the loanWatapDetailsList where year equals to UPDATED_YEAR
        defaultLoanWatapDetailsShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where year is not null
        defaultLoanWatapDetailsShouldBeFound("year.specified=true");

        // Get all the loanWatapDetailsList where year is null
        defaultLoanWatapDetailsShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByYearContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where year contains DEFAULT_YEAR
        defaultLoanWatapDetailsShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the loanWatapDetailsList where year contains UPDATED_YEAR
        defaultLoanWatapDetailsShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where year does not contain DEFAULT_YEAR
        defaultLoanWatapDetailsShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the loanWatapDetailsList where year does not contain UPDATED_YEAR
        defaultLoanWatapDetailsShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultLoanWatapDetailsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the loanWatapDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultLoanWatapDetailsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultLoanWatapDetailsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the loanWatapDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultLoanWatapDetailsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where isDeleted is not null
        defaultLoanWatapDetailsShouldBeFound("isDeleted.specified=true");

        // Get all the loanWatapDetailsList where isDeleted is null
        defaultLoanWatapDetailsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLoanWatapDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the loanWatapDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanWatapDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLoanWatapDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the loanWatapDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanWatapDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where lastModified is not null
        defaultLoanWatapDetailsShouldBeFound("lastModified.specified=true");

        // Get all the loanWatapDetailsList where lastModified is null
        defaultLoanWatapDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLoanWatapDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanWatapDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanWatapDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLoanWatapDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the loanWatapDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanWatapDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where lastModifiedBy is not null
        defaultLoanWatapDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the loanWatapDetailsList where lastModifiedBy is null
        defaultLoanWatapDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLoanWatapDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanWatapDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLoanWatapDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLoanWatapDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanWatapDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLoanWatapDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLoanWatapDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanWatapDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanWatapDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLoanWatapDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the loanWatapDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanWatapDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField1 is not null
        defaultLoanWatapDetailsShouldBeFound("freeField1.specified=true");

        // Get all the loanWatapDetailsList where freeField1 is null
        defaultLoanWatapDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLoanWatapDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanWatapDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLoanWatapDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLoanWatapDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanWatapDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLoanWatapDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLoanWatapDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanWatapDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanWatapDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLoanWatapDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the loanWatapDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanWatapDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField2 is not null
        defaultLoanWatapDetailsShouldBeFound("freeField2.specified=true");

        // Get all the loanWatapDetailsList where freeField2 is null
        defaultLoanWatapDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLoanWatapDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanWatapDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLoanWatapDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLoanWatapDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanWatapDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLoanWatapDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLoanWatapDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanWatapDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanWatapDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLoanWatapDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the loanWatapDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanWatapDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField3 is not null
        defaultLoanWatapDetailsShouldBeFound("freeField3.specified=true");

        // Get all the loanWatapDetailsList where freeField3 is null
        defaultLoanWatapDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLoanWatapDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanWatapDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLoanWatapDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        // Get all the loanWatapDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLoanWatapDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanWatapDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLoanWatapDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanWatapDetailsByLoanDemandIsEqualToSomething() throws Exception {
        LoanDemand loanDemand;
        if (TestUtil.findAll(em, LoanDemand.class).isEmpty()) {
            loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);
            loanDemand = LoanDemandResourceIT.createEntity(em);
        } else {
            loanDemand = TestUtil.findAll(em, LoanDemand.class).get(0);
        }
        em.persist(loanDemand);
        em.flush();
        loanWatapDetails.setLoanDemand(loanDemand);
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);
        Long loanDemandId = loanDemand.getId();

        // Get all the loanWatapDetailsList where loanDemand equals to loanDemandId
        defaultLoanWatapDetailsShouldBeFound("loanDemandId.equals=" + loanDemandId);

        // Get all the loanWatapDetailsList where loanDemand equals to (loanDemandId + 1)
        defaultLoanWatapDetailsShouldNotBeFound("loanDemandId.equals=" + (loanDemandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanWatapDetailsShouldBeFound(String filter) throws Exception {
        restLoanWatapDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanWatapDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanWatapDate").value(hasItem(DEFAULT_LOAN_WATAP_DATE.toString())))
            .andExpect(jsonPath("$.[*].cropLandInHector").value(hasItem(DEFAULT_CROP_LAND_IN_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].slotNumber").value(hasItem(DEFAULT_SLOT_NUMBER)))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].season").value(hasItem(DEFAULT_SEASON)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restLoanWatapDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanWatapDetailsShouldNotBeFound(String filter) throws Exception {
        restLoanWatapDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanWatapDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanWatapDetails() throws Exception {
        // Get the loanWatapDetails
        restLoanWatapDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoanWatapDetails() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        int databaseSizeBeforeUpdate = loanWatapDetailsRepository.findAll().size();

        // Update the loanWatapDetails
        LoanWatapDetails updatedLoanWatapDetails = loanWatapDetailsRepository.findById(loanWatapDetails.getId()).get();
        // Disconnect from session so that the updates on updatedLoanWatapDetails are not directly saved in db
        em.detach(updatedLoanWatapDetails);
        updatedLoanWatapDetails
            .loanWatapDate(UPDATED_LOAN_WATAP_DATE)
            .cropLandInHector(UPDATED_CROP_LAND_IN_HECTOR)
            .slotNumber(UPDATED_SLOT_NUMBER)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .season(UPDATED_SEASON)
            .status(UPDATED_STATUS)
            .year(UPDATED_YEAR)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        LoanWatapDetailsDTO loanWatapDetailsDTO = loanWatapDetailsMapper.toDto(updatedLoanWatapDetails);

        restLoanWatapDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanWatapDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanWatapDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanWatapDetails testLoanWatapDetails = loanWatapDetailsList.get(loanWatapDetailsList.size() - 1);
        assertThat(testLoanWatapDetails.getLoanWatapDate()).isEqualTo(UPDATED_LOAN_WATAP_DATE);
        assertThat(testLoanWatapDetails.getCropLandInHector()).isEqualTo(UPDATED_CROP_LAND_IN_HECTOR);
        assertThat(testLoanWatapDetails.getSlotNumber()).isEqualTo(UPDATED_SLOT_NUMBER);
        assertThat(testLoanWatapDetails.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testLoanWatapDetails.getSeason()).isEqualTo(UPDATED_SEASON);
        assertThat(testLoanWatapDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanWatapDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanWatapDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testLoanWatapDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanWatapDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanWatapDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanWatapDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanWatapDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingLoanWatapDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanWatapDetailsRepository.findAll().size();
        loanWatapDetails.setId(count.incrementAndGet());

        // Create the LoanWatapDetails
        LoanWatapDetailsDTO loanWatapDetailsDTO = loanWatapDetailsMapper.toDto(loanWatapDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanWatapDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanWatapDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanWatapDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanWatapDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanWatapDetailsRepository.findAll().size();
        loanWatapDetails.setId(count.incrementAndGet());

        // Create the LoanWatapDetails
        LoanWatapDetailsDTO loanWatapDetailsDTO = loanWatapDetailsMapper.toDto(loanWatapDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanWatapDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanWatapDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanWatapDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanWatapDetailsRepository.findAll().size();
        loanWatapDetails.setId(count.incrementAndGet());

        // Create the LoanWatapDetails
        LoanWatapDetailsDTO loanWatapDetailsDTO = loanWatapDetailsMapper.toDto(loanWatapDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanWatapDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanWatapDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanWatapDetailsWithPatch() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        int databaseSizeBeforeUpdate = loanWatapDetailsRepository.findAll().size();

        // Update the loanWatapDetails using partial update
        LoanWatapDetails partialUpdatedLoanWatapDetails = new LoanWatapDetails();
        partialUpdatedLoanWatapDetails.setId(loanWatapDetails.getId());

        partialUpdatedLoanWatapDetails
            .loanWatapDate(UPDATED_LOAN_WATAP_DATE)
            .cropLandInHector(UPDATED_CROP_LAND_IN_HECTOR)
            .season(UPDATED_SEASON)
            .status(UPDATED_STATUS)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restLoanWatapDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanWatapDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanWatapDetails))
            )
            .andExpect(status().isOk());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanWatapDetails testLoanWatapDetails = loanWatapDetailsList.get(loanWatapDetailsList.size() - 1);
        assertThat(testLoanWatapDetails.getLoanWatapDate()).isEqualTo(UPDATED_LOAN_WATAP_DATE);
        assertThat(testLoanWatapDetails.getCropLandInHector()).isEqualTo(UPDATED_CROP_LAND_IN_HECTOR);
        assertThat(testLoanWatapDetails.getSlotNumber()).isEqualTo(DEFAULT_SLOT_NUMBER);
        assertThat(testLoanWatapDetails.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        assertThat(testLoanWatapDetails.getSeason()).isEqualTo(UPDATED_SEASON);
        assertThat(testLoanWatapDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanWatapDetails.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLoanWatapDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testLoanWatapDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanWatapDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanWatapDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanWatapDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanWatapDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateLoanWatapDetailsWithPatch() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        int databaseSizeBeforeUpdate = loanWatapDetailsRepository.findAll().size();

        // Update the loanWatapDetails using partial update
        LoanWatapDetails partialUpdatedLoanWatapDetails = new LoanWatapDetails();
        partialUpdatedLoanWatapDetails.setId(loanWatapDetails.getId());

        partialUpdatedLoanWatapDetails
            .loanWatapDate(UPDATED_LOAN_WATAP_DATE)
            .cropLandInHector(UPDATED_CROP_LAND_IN_HECTOR)
            .slotNumber(UPDATED_SLOT_NUMBER)
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .season(UPDATED_SEASON)
            .status(UPDATED_STATUS)
            .year(UPDATED_YEAR)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restLoanWatapDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanWatapDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanWatapDetails))
            )
            .andExpect(status().isOk());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanWatapDetails testLoanWatapDetails = loanWatapDetailsList.get(loanWatapDetailsList.size() - 1);
        assertThat(testLoanWatapDetails.getLoanWatapDate()).isEqualTo(UPDATED_LOAN_WATAP_DATE);
        assertThat(testLoanWatapDetails.getCropLandInHector()).isEqualTo(UPDATED_CROP_LAND_IN_HECTOR);
        assertThat(testLoanWatapDetails.getSlotNumber()).isEqualTo(UPDATED_SLOT_NUMBER);
        assertThat(testLoanWatapDetails.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        assertThat(testLoanWatapDetails.getSeason()).isEqualTo(UPDATED_SEASON);
        assertThat(testLoanWatapDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanWatapDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanWatapDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testLoanWatapDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanWatapDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanWatapDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanWatapDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanWatapDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingLoanWatapDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanWatapDetailsRepository.findAll().size();
        loanWatapDetails.setId(count.incrementAndGet());

        // Create the LoanWatapDetails
        LoanWatapDetailsDTO loanWatapDetailsDTO = loanWatapDetailsMapper.toDto(loanWatapDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanWatapDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanWatapDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanWatapDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanWatapDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanWatapDetailsRepository.findAll().size();
        loanWatapDetails.setId(count.incrementAndGet());

        // Create the LoanWatapDetails
        LoanWatapDetailsDTO loanWatapDetailsDTO = loanWatapDetailsMapper.toDto(loanWatapDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanWatapDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanWatapDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanWatapDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanWatapDetailsRepository.findAll().size();
        loanWatapDetails.setId(count.incrementAndGet());

        // Create the LoanWatapDetails
        LoanWatapDetailsDTO loanWatapDetailsDTO = loanWatapDetailsMapper.toDto(loanWatapDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanWatapDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanWatapDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanWatapDetails in the database
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanWatapDetails() throws Exception {
        // Initialize the database
        loanWatapDetailsRepository.saveAndFlush(loanWatapDetails);

        int databaseSizeBeforeDelete = loanWatapDetailsRepository.findAll().size();

        // Delete the loanWatapDetails
        restLoanWatapDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanWatapDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanWatapDetails> loanWatapDetailsList = loanWatapDetailsRepository.findAll();
        assertThat(loanWatapDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

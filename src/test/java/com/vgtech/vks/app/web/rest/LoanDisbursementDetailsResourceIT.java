package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.LoanDetails;
import com.vgtech.vks.app.domain.LoanDisbursementDetails;
import com.vgtech.vks.app.domain.enumeration.LoanType;
import com.vgtech.vks.app.domain.enumeration.PaymentMode;
import com.vgtech.vks.app.repository.LoanDisbursementDetailsRepository;
import com.vgtech.vks.app.service.criteria.LoanDisbursementDetailsCriteria;
import com.vgtech.vks.app.service.dto.LoanDisbursementDetailsDTO;
import com.vgtech.vks.app.service.mapper.LoanDisbursementDetailsMapper;
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
 * Integration tests for the {@link LoanDisbursementDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanDisbursementDetailsResourceIT {

    private static final Instant DEFAULT_DISBURSEMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISBURSEMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_DISBURSEMENT_AMOUNT = 1D;
    private static final Double UPDATED_DISBURSEMENT_AMOUNT = 2D;
    private static final Double SMALLER_DISBURSEMENT_AMOUNT = 1D - 1D;

    private static final Integer DEFAULT_DISBURSEMENT_NUMBER = 1;
    private static final Integer UPDATED_DISBURSEMENT_NUMBER = 2;
    private static final Integer SMALLER_DISBURSEMENT_NUMBER = 1 - 1;

    private static final String DEFAULT_DISBURSEMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DISBURSEMENT_STATUS = "BBBBBBBBBB";

    private static final PaymentMode DEFAULT_PAYMENT_MODE = PaymentMode.ONLINE;
    private static final PaymentMode UPDATED_PAYMENT_MODE = PaymentMode.CASH;

    private static final LoanType DEFAULT_TYPE = LoanType.SHORT_TERM;
    private static final LoanType UPDATED_TYPE = LoanType.MID_TERM;

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

    private static final String ENTITY_API_URL = "/api/loan-disbursement-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanDisbursementDetailsRepository loanDisbursementDetailsRepository;

    @Autowired
    private LoanDisbursementDetailsMapper loanDisbursementDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanDisbursementDetailsMockMvc;

    private LoanDisbursementDetails loanDisbursementDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDisbursementDetails createEntity(EntityManager em) {
        LoanDisbursementDetails loanDisbursementDetails = new LoanDisbursementDetails()
            .disbursementDate(DEFAULT_DISBURSEMENT_DATE)
            .disbursementAmount(DEFAULT_DISBURSEMENT_AMOUNT)
            .disbursementNumber(DEFAULT_DISBURSEMENT_NUMBER)
            .disbursementStatus(DEFAULT_DISBURSEMENT_STATUS)
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .type(DEFAULT_TYPE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return loanDisbursementDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDisbursementDetails createUpdatedEntity(EntityManager em) {
        LoanDisbursementDetails loanDisbursementDetails = new LoanDisbursementDetails()
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .disbursementAmount(UPDATED_DISBURSEMENT_AMOUNT)
            .disbursementNumber(UPDATED_DISBURSEMENT_NUMBER)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .type(UPDATED_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return loanDisbursementDetails;
    }

    @BeforeEach
    public void initTest() {
        loanDisbursementDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanDisbursementDetails() throws Exception {
        int databaseSizeBeforeCreate = loanDisbursementDetailsRepository.findAll().size();
        // Create the LoanDisbursementDetails
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);
        restLoanDisbursementDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        LoanDisbursementDetails testLoanDisbursementDetails = loanDisbursementDetailsList.get(loanDisbursementDetailsList.size() - 1);
        assertThat(testLoanDisbursementDetails.getDisbursementDate()).isEqualTo(DEFAULT_DISBURSEMENT_DATE);
        assertThat(testLoanDisbursementDetails.getDisbursementAmount()).isEqualTo(DEFAULT_DISBURSEMENT_AMOUNT);
        assertThat(testLoanDisbursementDetails.getDisbursementNumber()).isEqualTo(DEFAULT_DISBURSEMENT_NUMBER);
        assertThat(testLoanDisbursementDetails.getDisbursementStatus()).isEqualTo(DEFAULT_DISBURSEMENT_STATUS);
        assertThat(testLoanDisbursementDetails.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testLoanDisbursementDetails.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLoanDisbursementDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanDisbursementDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanDisbursementDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanDisbursementDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanDisbursementDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createLoanDisbursementDetailsWithExistingId() throws Exception {
        // Create the LoanDisbursementDetails with an existing ID
        loanDisbursementDetails.setId(1L);
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);

        int databaseSizeBeforeCreate = loanDisbursementDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanDisbursementDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetails() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList
        restLoanDisbursementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDisbursementDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].disbursementDate").value(hasItem(DEFAULT_DISBURSEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].disbursementAmount").value(hasItem(DEFAULT_DISBURSEMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].disbursementNumber").value(hasItem(DEFAULT_DISBURSEMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].disbursementStatus").value(hasItem(DEFAULT_DISBURSEMENT_STATUS)))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getLoanDisbursementDetails() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get the loanDisbursementDetails
        restLoanDisbursementDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, loanDisbursementDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanDisbursementDetails.getId().intValue()))
            .andExpect(jsonPath("$.disbursementDate").value(DEFAULT_DISBURSEMENT_DATE.toString()))
            .andExpect(jsonPath("$.disbursementAmount").value(DEFAULT_DISBURSEMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.disbursementNumber").value(DEFAULT_DISBURSEMENT_NUMBER))
            .andExpect(jsonPath("$.disbursementStatus").value(DEFAULT_DISBURSEMENT_STATUS))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getLoanDisbursementDetailsByIdFiltering() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        Long id = loanDisbursementDetails.getId();

        defaultLoanDisbursementDetailsShouldBeFound("id.equals=" + id);
        defaultLoanDisbursementDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultLoanDisbursementDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanDisbursementDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanDisbursementDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanDisbursementDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementDate equals to DEFAULT_DISBURSEMENT_DATE
        defaultLoanDisbursementDetailsShouldBeFound("disbursementDate.equals=" + DEFAULT_DISBURSEMENT_DATE);

        // Get all the loanDisbursementDetailsList where disbursementDate equals to UPDATED_DISBURSEMENT_DATE
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementDate.equals=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementDate in DEFAULT_DISBURSEMENT_DATE or UPDATED_DISBURSEMENT_DATE
        defaultLoanDisbursementDetailsShouldBeFound("disbursementDate.in=" + DEFAULT_DISBURSEMENT_DATE + "," + UPDATED_DISBURSEMENT_DATE);

        // Get all the loanDisbursementDetailsList where disbursementDate equals to UPDATED_DISBURSEMENT_DATE
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementDate.in=" + UPDATED_DISBURSEMENT_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementDate is not null
        defaultLoanDisbursementDetailsShouldBeFound("disbursementDate.specified=true");

        // Get all the loanDisbursementDetailsList where disbursementDate is null
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementAmount equals to DEFAULT_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldBeFound("disbursementAmount.equals=" + DEFAULT_DISBURSEMENT_AMOUNT);

        // Get all the loanDisbursementDetailsList where disbursementAmount equals to UPDATED_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementAmount.equals=" + UPDATED_DISBURSEMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementAmount in DEFAULT_DISBURSEMENT_AMOUNT or UPDATED_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldBeFound(
            "disbursementAmount.in=" + DEFAULT_DISBURSEMENT_AMOUNT + "," + UPDATED_DISBURSEMENT_AMOUNT
        );

        // Get all the loanDisbursementDetailsList where disbursementAmount equals to UPDATED_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementAmount.in=" + UPDATED_DISBURSEMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementAmount is not null
        defaultLoanDisbursementDetailsShouldBeFound("disbursementAmount.specified=true");

        // Get all the loanDisbursementDetailsList where disbursementAmount is null
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementAmount is greater than or equal to DEFAULT_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldBeFound("disbursementAmount.greaterThanOrEqual=" + DEFAULT_DISBURSEMENT_AMOUNT);

        // Get all the loanDisbursementDetailsList where disbursementAmount is greater than or equal to UPDATED_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementAmount.greaterThanOrEqual=" + UPDATED_DISBURSEMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementAmount is less than or equal to DEFAULT_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldBeFound("disbursementAmount.lessThanOrEqual=" + DEFAULT_DISBURSEMENT_AMOUNT);

        // Get all the loanDisbursementDetailsList where disbursementAmount is less than or equal to SMALLER_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementAmount.lessThanOrEqual=" + SMALLER_DISBURSEMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementAmount is less than DEFAULT_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementAmount.lessThan=" + DEFAULT_DISBURSEMENT_AMOUNT);

        // Get all the loanDisbursementDetailsList where disbursementAmount is less than UPDATED_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldBeFound("disbursementAmount.lessThan=" + UPDATED_DISBURSEMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementAmount is greater than DEFAULT_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementAmount.greaterThan=" + DEFAULT_DISBURSEMENT_AMOUNT);

        // Get all the loanDisbursementDetailsList where disbursementAmount is greater than SMALLER_DISBURSEMENT_AMOUNT
        defaultLoanDisbursementDetailsShouldBeFound("disbursementAmount.greaterThan=" + SMALLER_DISBURSEMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementNumber equals to DEFAULT_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldBeFound("disbursementNumber.equals=" + DEFAULT_DISBURSEMENT_NUMBER);

        // Get all the loanDisbursementDetailsList where disbursementNumber equals to UPDATED_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementNumber.equals=" + UPDATED_DISBURSEMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementNumberIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementNumber in DEFAULT_DISBURSEMENT_NUMBER or UPDATED_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldBeFound(
            "disbursementNumber.in=" + DEFAULT_DISBURSEMENT_NUMBER + "," + UPDATED_DISBURSEMENT_NUMBER
        );

        // Get all the loanDisbursementDetailsList where disbursementNumber equals to UPDATED_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementNumber.in=" + UPDATED_DISBURSEMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementNumber is not null
        defaultLoanDisbursementDetailsShouldBeFound("disbursementNumber.specified=true");

        // Get all the loanDisbursementDetailsList where disbursementNumber is null
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementNumber is greater than or equal to DEFAULT_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldBeFound("disbursementNumber.greaterThanOrEqual=" + DEFAULT_DISBURSEMENT_NUMBER);

        // Get all the loanDisbursementDetailsList where disbursementNumber is greater than or equal to UPDATED_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementNumber.greaterThanOrEqual=" + UPDATED_DISBURSEMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementNumber is less than or equal to DEFAULT_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldBeFound("disbursementNumber.lessThanOrEqual=" + DEFAULT_DISBURSEMENT_NUMBER);

        // Get all the loanDisbursementDetailsList where disbursementNumber is less than or equal to SMALLER_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementNumber.lessThanOrEqual=" + SMALLER_DISBURSEMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementNumber is less than DEFAULT_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementNumber.lessThan=" + DEFAULT_DISBURSEMENT_NUMBER);

        // Get all the loanDisbursementDetailsList where disbursementNumber is less than UPDATED_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldBeFound("disbursementNumber.lessThan=" + UPDATED_DISBURSEMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementNumber is greater than DEFAULT_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementNumber.greaterThan=" + DEFAULT_DISBURSEMENT_NUMBER);

        // Get all the loanDisbursementDetailsList where disbursementNumber is greater than SMALLER_DISBURSEMENT_NUMBER
        defaultLoanDisbursementDetailsShouldBeFound("disbursementNumber.greaterThan=" + SMALLER_DISBURSEMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementStatus equals to DEFAULT_DISBURSEMENT_STATUS
        defaultLoanDisbursementDetailsShouldBeFound("disbursementStatus.equals=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanDisbursementDetailsList where disbursementStatus equals to UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementStatus.equals=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementStatus in DEFAULT_DISBURSEMENT_STATUS or UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementDetailsShouldBeFound(
            "disbursementStatus.in=" + DEFAULT_DISBURSEMENT_STATUS + "," + UPDATED_DISBURSEMENT_STATUS
        );

        // Get all the loanDisbursementDetailsList where disbursementStatus equals to UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementStatus.in=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementStatus is not null
        defaultLoanDisbursementDetailsShouldBeFound("disbursementStatus.specified=true");

        // Get all the loanDisbursementDetailsList where disbursementStatus is null
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementStatusContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementStatus contains DEFAULT_DISBURSEMENT_STATUS
        defaultLoanDisbursementDetailsShouldBeFound("disbursementStatus.contains=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanDisbursementDetailsList where disbursementStatus contains UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementStatus.contains=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByDisbursementStatusNotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where disbursementStatus does not contain DEFAULT_DISBURSEMENT_STATUS
        defaultLoanDisbursementDetailsShouldNotBeFound("disbursementStatus.doesNotContain=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanDisbursementDetailsList where disbursementStatus does not contain UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementDetailsShouldBeFound("disbursementStatus.doesNotContain=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByPaymentModeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where paymentMode equals to DEFAULT_PAYMENT_MODE
        defaultLoanDisbursementDetailsShouldBeFound("paymentMode.equals=" + DEFAULT_PAYMENT_MODE);

        // Get all the loanDisbursementDetailsList where paymentMode equals to UPDATED_PAYMENT_MODE
        defaultLoanDisbursementDetailsShouldNotBeFound("paymentMode.equals=" + UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByPaymentModeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where paymentMode in DEFAULT_PAYMENT_MODE or UPDATED_PAYMENT_MODE
        defaultLoanDisbursementDetailsShouldBeFound("paymentMode.in=" + DEFAULT_PAYMENT_MODE + "," + UPDATED_PAYMENT_MODE);

        // Get all the loanDisbursementDetailsList where paymentMode equals to UPDATED_PAYMENT_MODE
        defaultLoanDisbursementDetailsShouldNotBeFound("paymentMode.in=" + UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByPaymentModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where paymentMode is not null
        defaultLoanDisbursementDetailsShouldBeFound("paymentMode.specified=true");

        // Get all the loanDisbursementDetailsList where paymentMode is null
        defaultLoanDisbursementDetailsShouldNotBeFound("paymentMode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where type equals to DEFAULT_TYPE
        defaultLoanDisbursementDetailsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the loanDisbursementDetailsList where type equals to UPDATED_TYPE
        defaultLoanDisbursementDetailsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultLoanDisbursementDetailsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the loanDisbursementDetailsList where type equals to UPDATED_TYPE
        defaultLoanDisbursementDetailsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where type is not null
        defaultLoanDisbursementDetailsShouldBeFound("type.specified=true");

        // Get all the loanDisbursementDetailsList where type is null
        defaultLoanDisbursementDetailsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLoanDisbursementDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the loanDisbursementDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanDisbursementDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLoanDisbursementDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the loanDisbursementDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanDisbursementDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where lastModified is not null
        defaultLoanDisbursementDetailsShouldBeFound("lastModified.specified=true");

        // Get all the loanDisbursementDetailsList where lastModified is null
        defaultLoanDisbursementDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLoanDisbursementDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDisbursementDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the loanDisbursementDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where lastModifiedBy is not null
        defaultLoanDisbursementDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the loanDisbursementDetailsList where lastModifiedBy is null
        defaultLoanDisbursementDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLoanDisbursementDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDisbursementDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLoanDisbursementDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDisbursementDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLoanDisbursementDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDisbursementDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLoanDisbursementDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the loanDisbursementDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField1 is not null
        defaultLoanDisbursementDetailsShouldBeFound("freeField1.specified=true");

        // Get all the loanDisbursementDetailsList where freeField1 is null
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLoanDisbursementDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDisbursementDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDisbursementDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLoanDisbursementDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLoanDisbursementDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDisbursementDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLoanDisbursementDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the loanDisbursementDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField2 is not null
        defaultLoanDisbursementDetailsShouldBeFound("freeField2.specified=true");

        // Get all the loanDisbursementDetailsList where freeField2 is null
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLoanDisbursementDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDisbursementDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDisbursementDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLoanDisbursementDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLoanDisbursementDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDisbursementDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLoanDisbursementDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the loanDisbursementDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField3 is not null
        defaultLoanDisbursementDetailsShouldBeFound("freeField3.specified=true");

        // Get all the loanDisbursementDetailsList where freeField3 is null
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLoanDisbursementDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDisbursementDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        // Get all the loanDisbursementDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLoanDisbursementDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDisbursementDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLoanDisbursementDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementDetailsByLoanDetailsIsEqualToSomething() throws Exception {
        LoanDetails loanDetails;
        if (TestUtil.findAll(em, LoanDetails.class).isEmpty()) {
            loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);
            loanDetails = LoanDetailsResourceIT.createEntity(em);
        } else {
            loanDetails = TestUtil.findAll(em, LoanDetails.class).get(0);
        }
        em.persist(loanDetails);
        em.flush();
        loanDisbursementDetails.setLoanDetails(loanDetails);
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);
        Long loanDetailsId = loanDetails.getId();

        // Get all the loanDisbursementDetailsList where loanDetails equals to loanDetailsId
        defaultLoanDisbursementDetailsShouldBeFound("loanDetailsId.equals=" + loanDetailsId);

        // Get all the loanDisbursementDetailsList where loanDetails equals to (loanDetailsId + 1)
        defaultLoanDisbursementDetailsShouldNotBeFound("loanDetailsId.equals=" + (loanDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanDisbursementDetailsShouldBeFound(String filter) throws Exception {
        restLoanDisbursementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDisbursementDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].disbursementDate").value(hasItem(DEFAULT_DISBURSEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].disbursementAmount").value(hasItem(DEFAULT_DISBURSEMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].disbursementNumber").value(hasItem(DEFAULT_DISBURSEMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].disbursementStatus").value(hasItem(DEFAULT_DISBURSEMENT_STATUS)))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restLoanDisbursementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanDisbursementDetailsShouldNotBeFound(String filter) throws Exception {
        restLoanDisbursementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanDisbursementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanDisbursementDetails() throws Exception {
        // Get the loanDisbursementDetails
        restLoanDisbursementDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLoanDisbursementDetails() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        int databaseSizeBeforeUpdate = loanDisbursementDetailsRepository.findAll().size();

        // Update the loanDisbursementDetails
        LoanDisbursementDetails updatedLoanDisbursementDetails = loanDisbursementDetailsRepository
            .findById(loanDisbursementDetails.getId())
            .get();
        // Disconnect from session so that the updates on updatedLoanDisbursementDetails are not directly saved in db
        em.detach(updatedLoanDisbursementDetails);
        updatedLoanDisbursementDetails
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .disbursementAmount(UPDATED_DISBURSEMENT_AMOUNT)
            .disbursementNumber(UPDATED_DISBURSEMENT_NUMBER)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .type(UPDATED_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = loanDisbursementDetailsMapper.toDto(updatedLoanDisbursementDetails);

        restLoanDisbursementDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanDisbursementDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanDisbursementDetails testLoanDisbursementDetails = loanDisbursementDetailsList.get(loanDisbursementDetailsList.size() - 1);
        assertThat(testLoanDisbursementDetails.getDisbursementDate()).isEqualTo(UPDATED_DISBURSEMENT_DATE);
        assertThat(testLoanDisbursementDetails.getDisbursementAmount()).isEqualTo(UPDATED_DISBURSEMENT_AMOUNT);
        assertThat(testLoanDisbursementDetails.getDisbursementNumber()).isEqualTo(UPDATED_DISBURSEMENT_NUMBER);
        assertThat(testLoanDisbursementDetails.getDisbursementStatus()).isEqualTo(UPDATED_DISBURSEMENT_STATUS);
        assertThat(testLoanDisbursementDetails.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testLoanDisbursementDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLoanDisbursementDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanDisbursementDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanDisbursementDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDisbursementDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDisbursementDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingLoanDisbursementDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementDetailsRepository.findAll().size();
        loanDisbursementDetails.setId(count.incrementAndGet());

        // Create the LoanDisbursementDetails
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDisbursementDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanDisbursementDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanDisbursementDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementDetailsRepository.findAll().size();
        loanDisbursementDetails.setId(count.incrementAndGet());

        // Create the LoanDisbursementDetails
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDisbursementDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanDisbursementDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementDetailsRepository.findAll().size();
        loanDisbursementDetails.setId(count.incrementAndGet());

        // Create the LoanDisbursementDetails
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDisbursementDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanDisbursementDetailsWithPatch() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        int databaseSizeBeforeUpdate = loanDisbursementDetailsRepository.findAll().size();

        // Update the loanDisbursementDetails using partial update
        LoanDisbursementDetails partialUpdatedLoanDisbursementDetails = new LoanDisbursementDetails();
        partialUpdatedLoanDisbursementDetails.setId(loanDisbursementDetails.getId());

        partialUpdatedLoanDisbursementDetails
            .disbursementAmount(UPDATED_DISBURSEMENT_AMOUNT)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .type(UPDATED_TYPE)
            .freeField2(UPDATED_FREE_FIELD_2);

        restLoanDisbursementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDisbursementDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDisbursementDetails))
            )
            .andExpect(status().isOk());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanDisbursementDetails testLoanDisbursementDetails = loanDisbursementDetailsList.get(loanDisbursementDetailsList.size() - 1);
        assertThat(testLoanDisbursementDetails.getDisbursementDate()).isEqualTo(DEFAULT_DISBURSEMENT_DATE);
        assertThat(testLoanDisbursementDetails.getDisbursementAmount()).isEqualTo(UPDATED_DISBURSEMENT_AMOUNT);
        assertThat(testLoanDisbursementDetails.getDisbursementNumber()).isEqualTo(DEFAULT_DISBURSEMENT_NUMBER);
        assertThat(testLoanDisbursementDetails.getDisbursementStatus()).isEqualTo(DEFAULT_DISBURSEMENT_STATUS);
        assertThat(testLoanDisbursementDetails.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testLoanDisbursementDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLoanDisbursementDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanDisbursementDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanDisbursementDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanDisbursementDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDisbursementDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateLoanDisbursementDetailsWithPatch() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        int databaseSizeBeforeUpdate = loanDisbursementDetailsRepository.findAll().size();

        // Update the loanDisbursementDetails using partial update
        LoanDisbursementDetails partialUpdatedLoanDisbursementDetails = new LoanDisbursementDetails();
        partialUpdatedLoanDisbursementDetails.setId(loanDisbursementDetails.getId());

        partialUpdatedLoanDisbursementDetails
            .disbursementDate(UPDATED_DISBURSEMENT_DATE)
            .disbursementAmount(UPDATED_DISBURSEMENT_AMOUNT)
            .disbursementNumber(UPDATED_DISBURSEMENT_NUMBER)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .type(UPDATED_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restLoanDisbursementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDisbursementDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDisbursementDetails))
            )
            .andExpect(status().isOk());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanDisbursementDetails testLoanDisbursementDetails = loanDisbursementDetailsList.get(loanDisbursementDetailsList.size() - 1);
        assertThat(testLoanDisbursementDetails.getDisbursementDate()).isEqualTo(UPDATED_DISBURSEMENT_DATE);
        assertThat(testLoanDisbursementDetails.getDisbursementAmount()).isEqualTo(UPDATED_DISBURSEMENT_AMOUNT);
        assertThat(testLoanDisbursementDetails.getDisbursementNumber()).isEqualTo(UPDATED_DISBURSEMENT_NUMBER);
        assertThat(testLoanDisbursementDetails.getDisbursementStatus()).isEqualTo(UPDATED_DISBURSEMENT_STATUS);
        assertThat(testLoanDisbursementDetails.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testLoanDisbursementDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLoanDisbursementDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanDisbursementDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanDisbursementDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDisbursementDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDisbursementDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingLoanDisbursementDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementDetailsRepository.findAll().size();
        loanDisbursementDetails.setId(count.incrementAndGet());

        // Create the LoanDisbursementDetails
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDisbursementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanDisbursementDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanDisbursementDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementDetailsRepository.findAll().size();
        loanDisbursementDetails.setId(count.incrementAndGet());

        // Create the LoanDisbursementDetails
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDisbursementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanDisbursementDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementDetailsRepository.findAll().size();
        loanDisbursementDetails.setId(count.incrementAndGet());

        // Create the LoanDisbursementDetails
        LoanDisbursementDetailsDTO loanDisbursementDetailsDTO = loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDisbursementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDisbursementDetails in the database
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanDisbursementDetails() throws Exception {
        // Initialize the database
        loanDisbursementDetailsRepository.saveAndFlush(loanDisbursementDetails);

        int databaseSizeBeforeDelete = loanDisbursementDetailsRepository.findAll().size();

        // Delete the loanDisbursementDetails
        restLoanDisbursementDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanDisbursementDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanDisbursementDetails> loanDisbursementDetailsList = loanDisbursementDetailsRepository.findAll();
        assertThat(loanDisbursementDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

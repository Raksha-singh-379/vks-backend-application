package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.repository.BankDhoranDetailsRepository;
import com.vgtech.vks.app.service.criteria.BankDhoranDetailsCriteria;
import com.vgtech.vks.app.service.dto.BankDhoranDetailsDTO;
import com.vgtech.vks.app.service.mapper.BankDhoranDetailsMapper;
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
 * Integration tests for the {@link BankDhoranDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankDhoranDetailsResourceIT {

    private static final String DEFAULT_DHORAN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DHORAN_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/bank-dhoran-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankDhoranDetailsRepository bankDhoranDetailsRepository;

    @Autowired
    private BankDhoranDetailsMapper bankDhoranDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankDhoranDetailsMockMvc;

    private BankDhoranDetails bankDhoranDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankDhoranDetails createEntity(EntityManager em) {
        BankDhoranDetails bankDhoranDetails = new BankDhoranDetails()
            .dhoranName(DEFAULT_DHORAN_NAME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .year(DEFAULT_YEAR)
            .isActivate(DEFAULT_IS_ACTIVATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return bankDhoranDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankDhoranDetails createUpdatedEntity(EntityManager em) {
        BankDhoranDetails bankDhoranDetails = new BankDhoranDetails()
            .dhoranName(UPDATED_DHORAN_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .year(UPDATED_YEAR)
            .isActivate(UPDATED_IS_ACTIVATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return bankDhoranDetails;
    }

    @BeforeEach
    public void initTest() {
        bankDhoranDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createBankDhoranDetails() throws Exception {
        int databaseSizeBeforeCreate = bankDhoranDetailsRepository.findAll().size();
        // Create the BankDhoranDetails
        BankDhoranDetailsDTO bankDhoranDetailsDTO = bankDhoranDetailsMapper.toDto(bankDhoranDetails);
        restBankDhoranDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDhoranDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BankDhoranDetails testBankDhoranDetails = bankDhoranDetailsList.get(bankDhoranDetailsList.size() - 1);
        assertThat(testBankDhoranDetails.getDhoranName()).isEqualTo(DEFAULT_DHORAN_NAME);
        assertThat(testBankDhoranDetails.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBankDhoranDetails.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBankDhoranDetails.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testBankDhoranDetails.getIsActivate()).isEqualTo(DEFAULT_IS_ACTIVATE);
        assertThat(testBankDhoranDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBankDhoranDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testBankDhoranDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testBankDhoranDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testBankDhoranDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createBankDhoranDetailsWithExistingId() throws Exception {
        // Create the BankDhoranDetails with an existing ID
        bankDhoranDetails.setId(1L);
        BankDhoranDetailsDTO bankDhoranDetailsDTO = bankDhoranDetailsMapper.toDto(bankDhoranDetails);

        int databaseSizeBeforeCreate = bankDhoranDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankDhoranDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDhoranDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetails() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList
        restBankDhoranDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankDhoranDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].dhoranName").value(hasItem(DEFAULT_DHORAN_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].isActivate").value(hasItem(DEFAULT_IS_ACTIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getBankDhoranDetails() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get the bankDhoranDetails
        restBankDhoranDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, bankDhoranDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankDhoranDetails.getId().intValue()))
            .andExpect(jsonPath("$.dhoranName").value(DEFAULT_DHORAN_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.isActivate").value(DEFAULT_IS_ACTIVATE.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getBankDhoranDetailsByIdFiltering() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        Long id = bankDhoranDetails.getId();

        defaultBankDhoranDetailsShouldBeFound("id.equals=" + id);
        defaultBankDhoranDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultBankDhoranDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBankDhoranDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultBankDhoranDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBankDhoranDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByDhoranNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where dhoranName equals to DEFAULT_DHORAN_NAME
        defaultBankDhoranDetailsShouldBeFound("dhoranName.equals=" + DEFAULT_DHORAN_NAME);

        // Get all the bankDhoranDetailsList where dhoranName equals to UPDATED_DHORAN_NAME
        defaultBankDhoranDetailsShouldNotBeFound("dhoranName.equals=" + UPDATED_DHORAN_NAME);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByDhoranNameIsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where dhoranName in DEFAULT_DHORAN_NAME or UPDATED_DHORAN_NAME
        defaultBankDhoranDetailsShouldBeFound("dhoranName.in=" + DEFAULT_DHORAN_NAME + "," + UPDATED_DHORAN_NAME);

        // Get all the bankDhoranDetailsList where dhoranName equals to UPDATED_DHORAN_NAME
        defaultBankDhoranDetailsShouldNotBeFound("dhoranName.in=" + UPDATED_DHORAN_NAME);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByDhoranNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where dhoranName is not null
        defaultBankDhoranDetailsShouldBeFound("dhoranName.specified=true");

        // Get all the bankDhoranDetailsList where dhoranName is null
        defaultBankDhoranDetailsShouldNotBeFound("dhoranName.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByDhoranNameContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where dhoranName contains DEFAULT_DHORAN_NAME
        defaultBankDhoranDetailsShouldBeFound("dhoranName.contains=" + DEFAULT_DHORAN_NAME);

        // Get all the bankDhoranDetailsList where dhoranName contains UPDATED_DHORAN_NAME
        defaultBankDhoranDetailsShouldNotBeFound("dhoranName.contains=" + UPDATED_DHORAN_NAME);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByDhoranNameNotContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where dhoranName does not contain DEFAULT_DHORAN_NAME
        defaultBankDhoranDetailsShouldNotBeFound("dhoranName.doesNotContain=" + DEFAULT_DHORAN_NAME);

        // Get all the bankDhoranDetailsList where dhoranName does not contain UPDATED_DHORAN_NAME
        defaultBankDhoranDetailsShouldBeFound("dhoranName.doesNotContain=" + UPDATED_DHORAN_NAME);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where startDate equals to DEFAULT_START_DATE
        defaultBankDhoranDetailsShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the bankDhoranDetailsList where startDate equals to UPDATED_START_DATE
        defaultBankDhoranDetailsShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultBankDhoranDetailsShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the bankDhoranDetailsList where startDate equals to UPDATED_START_DATE
        defaultBankDhoranDetailsShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where startDate is not null
        defaultBankDhoranDetailsShouldBeFound("startDate.specified=true");

        // Get all the bankDhoranDetailsList where startDate is null
        defaultBankDhoranDetailsShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where endDate equals to DEFAULT_END_DATE
        defaultBankDhoranDetailsShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the bankDhoranDetailsList where endDate equals to UPDATED_END_DATE
        defaultBankDhoranDetailsShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultBankDhoranDetailsShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the bankDhoranDetailsList where endDate equals to UPDATED_END_DATE
        defaultBankDhoranDetailsShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where endDate is not null
        defaultBankDhoranDetailsShouldBeFound("endDate.specified=true");

        // Get all the bankDhoranDetailsList where endDate is null
        defaultBankDhoranDetailsShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where year equals to DEFAULT_YEAR
        defaultBankDhoranDetailsShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the bankDhoranDetailsList where year equals to UPDATED_YEAR
        defaultBankDhoranDetailsShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultBankDhoranDetailsShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the bankDhoranDetailsList where year equals to UPDATED_YEAR
        defaultBankDhoranDetailsShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where year is not null
        defaultBankDhoranDetailsShouldBeFound("year.specified=true");

        // Get all the bankDhoranDetailsList where year is null
        defaultBankDhoranDetailsShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByYearContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where year contains DEFAULT_YEAR
        defaultBankDhoranDetailsShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the bankDhoranDetailsList where year contains UPDATED_YEAR
        defaultBankDhoranDetailsShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where year does not contain DEFAULT_YEAR
        defaultBankDhoranDetailsShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the bankDhoranDetailsList where year does not contain UPDATED_YEAR
        defaultBankDhoranDetailsShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByIsActivateIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where isActivate equals to DEFAULT_IS_ACTIVATE
        defaultBankDhoranDetailsShouldBeFound("isActivate.equals=" + DEFAULT_IS_ACTIVATE);

        // Get all the bankDhoranDetailsList where isActivate equals to UPDATED_IS_ACTIVATE
        defaultBankDhoranDetailsShouldNotBeFound("isActivate.equals=" + UPDATED_IS_ACTIVATE);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByIsActivateIsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where isActivate in DEFAULT_IS_ACTIVATE or UPDATED_IS_ACTIVATE
        defaultBankDhoranDetailsShouldBeFound("isActivate.in=" + DEFAULT_IS_ACTIVATE + "," + UPDATED_IS_ACTIVATE);

        // Get all the bankDhoranDetailsList where isActivate equals to UPDATED_IS_ACTIVATE
        defaultBankDhoranDetailsShouldNotBeFound("isActivate.in=" + UPDATED_IS_ACTIVATE);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByIsActivateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where isActivate is not null
        defaultBankDhoranDetailsShouldBeFound("isActivate.specified=true");

        // Get all the bankDhoranDetailsList where isActivate is null
        defaultBankDhoranDetailsShouldNotBeFound("isActivate.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBankDhoranDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bankDhoranDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBankDhoranDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBankDhoranDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the bankDhoranDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBankDhoranDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where lastModified is not null
        defaultBankDhoranDetailsShouldBeFound("lastModified.specified=true");

        // Get all the bankDhoranDetailsList where lastModified is null
        defaultBankDhoranDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBankDhoranDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankDhoranDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBankDhoranDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBankDhoranDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the bankDhoranDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBankDhoranDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where lastModifiedBy is not null
        defaultBankDhoranDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the bankDhoranDetailsList where lastModifiedBy is null
        defaultBankDhoranDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBankDhoranDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankDhoranDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBankDhoranDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBankDhoranDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankDhoranDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBankDhoranDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultBankDhoranDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the bankDhoranDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultBankDhoranDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultBankDhoranDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the bankDhoranDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultBankDhoranDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField1 is not null
        defaultBankDhoranDetailsShouldBeFound("freeField1.specified=true");

        // Get all the bankDhoranDetailsList where freeField1 is null
        defaultBankDhoranDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultBankDhoranDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the bankDhoranDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultBankDhoranDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultBankDhoranDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the bankDhoranDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultBankDhoranDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultBankDhoranDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the bankDhoranDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultBankDhoranDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultBankDhoranDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the bankDhoranDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultBankDhoranDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField2 is not null
        defaultBankDhoranDetailsShouldBeFound("freeField2.specified=true");

        // Get all the bankDhoranDetailsList where freeField2 is null
        defaultBankDhoranDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultBankDhoranDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the bankDhoranDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultBankDhoranDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultBankDhoranDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the bankDhoranDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultBankDhoranDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultBankDhoranDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the bankDhoranDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultBankDhoranDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultBankDhoranDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the bankDhoranDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultBankDhoranDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField3 is not null
        defaultBankDhoranDetailsShouldBeFound("freeField3.specified=true");

        // Get all the bankDhoranDetailsList where freeField3 is null
        defaultBankDhoranDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultBankDhoranDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the bankDhoranDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultBankDhoranDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        // Get all the bankDhoranDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultBankDhoranDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the bankDhoranDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultBankDhoranDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllBankDhoranDetailsBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        bankDhoranDetails.setSociety(society);
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);
        Long societyId = society.getId();

        // Get all the bankDhoranDetailsList where society equals to societyId
        defaultBankDhoranDetailsShouldBeFound("societyId.equals=" + societyId);

        // Get all the bankDhoranDetailsList where society equals to (societyId + 1)
        defaultBankDhoranDetailsShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBankDhoranDetailsShouldBeFound(String filter) throws Exception {
        restBankDhoranDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankDhoranDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].dhoranName").value(hasItem(DEFAULT_DHORAN_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].isActivate").value(hasItem(DEFAULT_IS_ACTIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restBankDhoranDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBankDhoranDetailsShouldNotBeFound(String filter) throws Exception {
        restBankDhoranDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBankDhoranDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBankDhoranDetails() throws Exception {
        // Get the bankDhoranDetails
        restBankDhoranDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankDhoranDetails() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        int databaseSizeBeforeUpdate = bankDhoranDetailsRepository.findAll().size();

        // Update the bankDhoranDetails
        BankDhoranDetails updatedBankDhoranDetails = bankDhoranDetailsRepository.findById(bankDhoranDetails.getId()).get();
        // Disconnect from session so that the updates on updatedBankDhoranDetails are not directly saved in db
        em.detach(updatedBankDhoranDetails);
        updatedBankDhoranDetails
            .dhoranName(UPDATED_DHORAN_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .year(UPDATED_YEAR)
            .isActivate(UPDATED_IS_ACTIVATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        BankDhoranDetailsDTO bankDhoranDetailsDTO = bankDhoranDetailsMapper.toDto(updatedBankDhoranDetails);

        restBankDhoranDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDhoranDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDhoranDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankDhoranDetails testBankDhoranDetails = bankDhoranDetailsList.get(bankDhoranDetailsList.size() - 1);
        assertThat(testBankDhoranDetails.getDhoranName()).isEqualTo(UPDATED_DHORAN_NAME);
        assertThat(testBankDhoranDetails.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBankDhoranDetails.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBankDhoranDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testBankDhoranDetails.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testBankDhoranDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBankDhoranDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testBankDhoranDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBankDhoranDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBankDhoranDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingBankDhoranDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDhoranDetailsRepository.findAll().size();
        bankDhoranDetails.setId(count.incrementAndGet());

        // Create the BankDhoranDetails
        BankDhoranDetailsDTO bankDhoranDetailsDTO = bankDhoranDetailsMapper.toDto(bankDhoranDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankDhoranDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDhoranDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDhoranDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankDhoranDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDhoranDetailsRepository.findAll().size();
        bankDhoranDetails.setId(count.incrementAndGet());

        // Create the BankDhoranDetails
        BankDhoranDetailsDTO bankDhoranDetailsDTO = bankDhoranDetailsMapper.toDto(bankDhoranDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDhoranDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDhoranDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankDhoranDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDhoranDetailsRepository.findAll().size();
        bankDhoranDetails.setId(count.incrementAndGet());

        // Create the BankDhoranDetails
        BankDhoranDetailsDTO bankDhoranDetailsDTO = bankDhoranDetailsMapper.toDto(bankDhoranDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDhoranDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDhoranDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankDhoranDetailsWithPatch() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        int databaseSizeBeforeUpdate = bankDhoranDetailsRepository.findAll().size();

        // Update the bankDhoranDetails using partial update
        BankDhoranDetails partialUpdatedBankDhoranDetails = new BankDhoranDetails();
        partialUpdatedBankDhoranDetails.setId(bankDhoranDetails.getId());

        partialUpdatedBankDhoranDetails
            .startDate(UPDATED_START_DATE)
            .year(UPDATED_YEAR)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3);

        restBankDhoranDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankDhoranDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankDhoranDetails))
            )
            .andExpect(status().isOk());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankDhoranDetails testBankDhoranDetails = bankDhoranDetailsList.get(bankDhoranDetailsList.size() - 1);
        assertThat(testBankDhoranDetails.getDhoranName()).isEqualTo(DEFAULT_DHORAN_NAME);
        assertThat(testBankDhoranDetails.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBankDhoranDetails.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBankDhoranDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testBankDhoranDetails.getIsActivate()).isEqualTo(DEFAULT_IS_ACTIVATE);
        assertThat(testBankDhoranDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBankDhoranDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testBankDhoranDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBankDhoranDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testBankDhoranDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateBankDhoranDetailsWithPatch() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        int databaseSizeBeforeUpdate = bankDhoranDetailsRepository.findAll().size();

        // Update the bankDhoranDetails using partial update
        BankDhoranDetails partialUpdatedBankDhoranDetails = new BankDhoranDetails();
        partialUpdatedBankDhoranDetails.setId(bankDhoranDetails.getId());

        partialUpdatedBankDhoranDetails
            .dhoranName(UPDATED_DHORAN_NAME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .year(UPDATED_YEAR)
            .isActivate(UPDATED_IS_ACTIVATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restBankDhoranDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankDhoranDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankDhoranDetails))
            )
            .andExpect(status().isOk());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankDhoranDetails testBankDhoranDetails = bankDhoranDetailsList.get(bankDhoranDetailsList.size() - 1);
        assertThat(testBankDhoranDetails.getDhoranName()).isEqualTo(UPDATED_DHORAN_NAME);
        assertThat(testBankDhoranDetails.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBankDhoranDetails.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBankDhoranDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testBankDhoranDetails.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testBankDhoranDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBankDhoranDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testBankDhoranDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBankDhoranDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBankDhoranDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingBankDhoranDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDhoranDetailsRepository.findAll().size();
        bankDhoranDetails.setId(count.incrementAndGet());

        // Create the BankDhoranDetails
        BankDhoranDetailsDTO bankDhoranDetailsDTO = bankDhoranDetailsMapper.toDto(bankDhoranDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankDhoranDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankDhoranDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDhoranDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankDhoranDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDhoranDetailsRepository.findAll().size();
        bankDhoranDetails.setId(count.incrementAndGet());

        // Create the BankDhoranDetails
        BankDhoranDetailsDTO bankDhoranDetailsDTO = bankDhoranDetailsMapper.toDto(bankDhoranDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDhoranDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDhoranDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankDhoranDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDhoranDetailsRepository.findAll().size();
        bankDhoranDetails.setId(count.incrementAndGet());

        // Create the BankDhoranDetails
        BankDhoranDetailsDTO bankDhoranDetailsDTO = bankDhoranDetailsMapper.toDto(bankDhoranDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDhoranDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDhoranDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankDhoranDetails in the database
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankDhoranDetails() throws Exception {
        // Initialize the database
        bankDhoranDetailsRepository.saveAndFlush(bankDhoranDetails);

        int databaseSizeBeforeDelete = bankDhoranDetailsRepository.findAll().size();

        // Delete the bankDhoranDetails
        restBankDhoranDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankDhoranDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankDhoranDetails> bankDhoranDetailsList = bankDhoranDetailsRepository.findAll();
        assertThat(bankDhoranDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

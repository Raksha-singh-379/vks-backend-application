package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyBanksDetails;
import com.vgtech.vks.app.repository.SocietyBanksDetailsRepository;
import com.vgtech.vks.app.service.criteria.SocietyBanksDetailsCriteria;
import com.vgtech.vks.app.service.dto.SocietyBanksDetailsDTO;
import com.vgtech.vks.app.service.mapper.SocietyBanksDetailsMapper;
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
 * Integration tests for the {@link SocietyBanksDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocietyBanksDetailsResourceIT {

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IFSCCODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSCCODE = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACC_HEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACC_HEAD_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT_ACC_HEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ACC_HEAD_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/society-banks-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocietyBanksDetailsRepository societyBanksDetailsRepository;

    @Autowired
    private SocietyBanksDetailsMapper societyBanksDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocietyBanksDetailsMockMvc;

    private SocietyBanksDetails societyBanksDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyBanksDetails createEntity(EntityManager em) {
        SocietyBanksDetails societyBanksDetails = new SocietyBanksDetails()
            .bankName(DEFAULT_BANK_NAME)
            .ifsccode(DEFAULT_IFSCCODE)
            .branchName(DEFAULT_BRANCH_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .isActive(DEFAULT_IS_ACTIVE)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .accHeadCode(DEFAULT_ACC_HEAD_CODE)
            .parentAccHeadCode(DEFAULT_PARENT_ACC_HEAD_CODE)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return societyBanksDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyBanksDetails createUpdatedEntity(EntityManager em) {
        SocietyBanksDetails societyBanksDetails = new SocietyBanksDetails()
            .bankName(UPDATED_BANK_NAME)
            .ifsccode(UPDATED_IFSCCODE)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .isActive(UPDATED_IS_ACTIVE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .accountName(UPDATED_ACCOUNT_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return societyBanksDetails;
    }

    @BeforeEach
    public void initTest() {
        societyBanksDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createSocietyBanksDetails() throws Exception {
        int databaseSizeBeforeCreate = societyBanksDetailsRepository.findAll().size();
        // Create the SocietyBanksDetails
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(societyBanksDetails);
        restSocietyBanksDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        SocietyBanksDetails testSocietyBanksDetails = societyBanksDetailsList.get(societyBanksDetailsList.size() - 1);
        assertThat(testSocietyBanksDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testSocietyBanksDetails.getIfsccode()).isEqualTo(DEFAULT_IFSCCODE);
        assertThat(testSocietyBanksDetails.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testSocietyBanksDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testSocietyBanksDetails.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSocietyBanksDetails.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testSocietyBanksDetails.getAccHeadCode()).isEqualTo(DEFAULT_ACC_HEAD_CODE);
        assertThat(testSocietyBanksDetails.getParentAccHeadCode()).isEqualTo(DEFAULT_PARENT_ACC_HEAD_CODE);
        assertThat(testSocietyBanksDetails.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testSocietyBanksDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyBanksDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyBanksDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyBanksDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyBanksDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyBanksDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyBanksDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createSocietyBanksDetailsWithExistingId() throws Exception {
        // Create the SocietyBanksDetails with an existing ID
        societyBanksDetails.setId(1L);
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(societyBanksDetails);

        int databaseSizeBeforeCreate = societyBanksDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocietyBanksDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = societyBanksDetailsRepository.findAll().size();
        // set the field null
        societyBanksDetails.setAccountNumber(null);

        // Create the SocietyBanksDetails, which fails.
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(societyBanksDetails);

        restSocietyBanksDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetails() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList
        restSocietyBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyBanksDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].ifsccode").value(hasItem(DEFAULT_IFSCCODE)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].accHeadCode").value(hasItem(DEFAULT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].parentAccHeadCode").value(hasItem(DEFAULT_PARENT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getSocietyBanksDetails() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get the societyBanksDetails
        restSocietyBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, societyBanksDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societyBanksDetails.getId().intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.ifsccode").value(DEFAULT_IFSCCODE))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE))
            .andExpect(jsonPath("$.accHeadCode").value(DEFAULT_ACC_HEAD_CODE))
            .andExpect(jsonPath("$.parentAccHeadCode").value(DEFAULT_PARENT_ACC_HEAD_CODE))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getSocietyBanksDetailsByIdFiltering() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        Long id = societyBanksDetails.getId();

        defaultSocietyBanksDetailsShouldBeFound("id.equals=" + id);
        defaultSocietyBanksDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultSocietyBanksDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocietyBanksDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultSocietyBanksDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocietyBanksDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where bankName equals to DEFAULT_BANK_NAME
        defaultSocietyBanksDetailsShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the societyBanksDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultSocietyBanksDetailsShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the societyBanksDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where bankName is not null
        defaultSocietyBanksDetailsShouldBeFound("bankName.specified=true");

        // Get all the societyBanksDetailsList where bankName is null
        defaultSocietyBanksDetailsShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBankNameContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where bankName contains DEFAULT_BANK_NAME
        defaultSocietyBanksDetailsShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the societyBanksDetailsList where bankName contains UPDATED_BANK_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where bankName does not contain DEFAULT_BANK_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the societyBanksDetailsList where bankName does not contain UPDATED_BANK_NAME
        defaultSocietyBanksDetailsShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByIfsccodeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where ifsccode equals to DEFAULT_IFSCCODE
        defaultSocietyBanksDetailsShouldBeFound("ifsccode.equals=" + DEFAULT_IFSCCODE);

        // Get all the societyBanksDetailsList where ifsccode equals to UPDATED_IFSCCODE
        defaultSocietyBanksDetailsShouldNotBeFound("ifsccode.equals=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByIfsccodeIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where ifsccode in DEFAULT_IFSCCODE or UPDATED_IFSCCODE
        defaultSocietyBanksDetailsShouldBeFound("ifsccode.in=" + DEFAULT_IFSCCODE + "," + UPDATED_IFSCCODE);

        // Get all the societyBanksDetailsList where ifsccode equals to UPDATED_IFSCCODE
        defaultSocietyBanksDetailsShouldNotBeFound("ifsccode.in=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByIfsccodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where ifsccode is not null
        defaultSocietyBanksDetailsShouldBeFound("ifsccode.specified=true");

        // Get all the societyBanksDetailsList where ifsccode is null
        defaultSocietyBanksDetailsShouldNotBeFound("ifsccode.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByIfsccodeContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where ifsccode contains DEFAULT_IFSCCODE
        defaultSocietyBanksDetailsShouldBeFound("ifsccode.contains=" + DEFAULT_IFSCCODE);

        // Get all the societyBanksDetailsList where ifsccode contains UPDATED_IFSCCODE
        defaultSocietyBanksDetailsShouldNotBeFound("ifsccode.contains=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByIfsccodeNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where ifsccode does not contain DEFAULT_IFSCCODE
        defaultSocietyBanksDetailsShouldNotBeFound("ifsccode.doesNotContain=" + DEFAULT_IFSCCODE);

        // Get all the societyBanksDetailsList where ifsccode does not contain UPDATED_IFSCCODE
        defaultSocietyBanksDetailsShouldBeFound("ifsccode.doesNotContain=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where branchName equals to DEFAULT_BRANCH_NAME
        defaultSocietyBanksDetailsShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the societyBanksDetailsList where branchName equals to UPDATED_BRANCH_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultSocietyBanksDetailsShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the societyBanksDetailsList where branchName equals to UPDATED_BRANCH_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where branchName is not null
        defaultSocietyBanksDetailsShouldBeFound("branchName.specified=true");

        // Get all the societyBanksDetailsList where branchName is null
        defaultSocietyBanksDetailsShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where branchName contains DEFAULT_BRANCH_NAME
        defaultSocietyBanksDetailsShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the societyBanksDetailsList where branchName contains UPDATED_BRANCH_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the societyBanksDetailsList where branchName does not contain UPDATED_BRANCH_NAME
        defaultSocietyBanksDetailsShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultSocietyBanksDetailsShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the societyBanksDetailsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultSocietyBanksDetailsShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultSocietyBanksDetailsShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the societyBanksDetailsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultSocietyBanksDetailsShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountNumber is not null
        defaultSocietyBanksDetailsShouldBeFound("accountNumber.specified=true");

        // Get all the societyBanksDetailsList where accountNumber is null
        defaultSocietyBanksDetailsShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultSocietyBanksDetailsShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the societyBanksDetailsList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultSocietyBanksDetailsShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultSocietyBanksDetailsShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the societyBanksDetailsList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultSocietyBanksDetailsShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where isActive equals to DEFAULT_IS_ACTIVE
        defaultSocietyBanksDetailsShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the societyBanksDetailsList where isActive equals to UPDATED_IS_ACTIVE
        defaultSocietyBanksDetailsShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultSocietyBanksDetailsShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the societyBanksDetailsList where isActive equals to UPDATED_IS_ACTIVE
        defaultSocietyBanksDetailsShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where isActive is not null
        defaultSocietyBanksDetailsShouldBeFound("isActive.specified=true");

        // Get all the societyBanksDetailsList where isActive is null
        defaultSocietyBanksDetailsShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultSocietyBanksDetailsShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the societyBanksDetailsList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultSocietyBanksDetailsShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultSocietyBanksDetailsShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the societyBanksDetailsList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultSocietyBanksDetailsShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountType is not null
        defaultSocietyBanksDetailsShouldBeFound("accountType.specified=true");

        // Get all the societyBanksDetailsList where accountType is null
        defaultSocietyBanksDetailsShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountTypeContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountType contains DEFAULT_ACCOUNT_TYPE
        defaultSocietyBanksDetailsShouldBeFound("accountType.contains=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the societyBanksDetailsList where accountType contains UPDATED_ACCOUNT_TYPE
        defaultSocietyBanksDetailsShouldNotBeFound("accountType.contains=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountTypeNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountType does not contain DEFAULT_ACCOUNT_TYPE
        defaultSocietyBanksDetailsShouldNotBeFound("accountType.doesNotContain=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the societyBanksDetailsList where accountType does not contain UPDATED_ACCOUNT_TYPE
        defaultSocietyBanksDetailsShouldBeFound("accountType.doesNotContain=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccHeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accHeadCode equals to DEFAULT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldBeFound("accHeadCode.equals=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the societyBanksDetailsList where accHeadCode equals to UPDATED_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldNotBeFound("accHeadCode.equals=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccHeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accHeadCode in DEFAULT_ACC_HEAD_CODE or UPDATED_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldBeFound("accHeadCode.in=" + DEFAULT_ACC_HEAD_CODE + "," + UPDATED_ACC_HEAD_CODE);

        // Get all the societyBanksDetailsList where accHeadCode equals to UPDATED_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldNotBeFound("accHeadCode.in=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccHeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accHeadCode is not null
        defaultSocietyBanksDetailsShouldBeFound("accHeadCode.specified=true");

        // Get all the societyBanksDetailsList where accHeadCode is null
        defaultSocietyBanksDetailsShouldNotBeFound("accHeadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccHeadCodeContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accHeadCode contains DEFAULT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldBeFound("accHeadCode.contains=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the societyBanksDetailsList where accHeadCode contains UPDATED_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldNotBeFound("accHeadCode.contains=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccHeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accHeadCode does not contain DEFAULT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldNotBeFound("accHeadCode.doesNotContain=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the societyBanksDetailsList where accHeadCode does not contain UPDATED_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldBeFound("accHeadCode.doesNotContain=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByParentAccHeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where parentAccHeadCode equals to DEFAULT_PARENT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldBeFound("parentAccHeadCode.equals=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the societyBanksDetailsList where parentAccHeadCode equals to UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldNotBeFound("parentAccHeadCode.equals=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByParentAccHeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where parentAccHeadCode in DEFAULT_PARENT_ACC_HEAD_CODE or UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldBeFound(
            "parentAccHeadCode.in=" + DEFAULT_PARENT_ACC_HEAD_CODE + "," + UPDATED_PARENT_ACC_HEAD_CODE
        );

        // Get all the societyBanksDetailsList where parentAccHeadCode equals to UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldNotBeFound("parentAccHeadCode.in=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByParentAccHeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where parentAccHeadCode is not null
        defaultSocietyBanksDetailsShouldBeFound("parentAccHeadCode.specified=true");

        // Get all the societyBanksDetailsList where parentAccHeadCode is null
        defaultSocietyBanksDetailsShouldNotBeFound("parentAccHeadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByParentAccHeadCodeContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where parentAccHeadCode contains DEFAULT_PARENT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldBeFound("parentAccHeadCode.contains=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the societyBanksDetailsList where parentAccHeadCode contains UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldNotBeFound("parentAccHeadCode.contains=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByParentAccHeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where parentAccHeadCode does not contain DEFAULT_PARENT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldNotBeFound("parentAccHeadCode.doesNotContain=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the societyBanksDetailsList where parentAccHeadCode does not contain UPDATED_PARENT_ACC_HEAD_CODE
        defaultSocietyBanksDetailsShouldBeFound("parentAccHeadCode.doesNotContain=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultSocietyBanksDetailsShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the societyBanksDetailsList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultSocietyBanksDetailsShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the societyBanksDetailsList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountName is not null
        defaultSocietyBanksDetailsShouldBeFound("accountName.specified=true");

        // Get all the societyBanksDetailsList where accountName is null
        defaultSocietyBanksDetailsShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNameContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountName contains DEFAULT_ACCOUNT_NAME
        defaultSocietyBanksDetailsShouldBeFound("accountName.contains=" + DEFAULT_ACCOUNT_NAME);

        // Get all the societyBanksDetailsList where accountName contains UPDATED_ACCOUNT_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("accountName.contains=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where accountName does not contain DEFAULT_ACCOUNT_NAME
        defaultSocietyBanksDetailsShouldNotBeFound("accountName.doesNotContain=" + DEFAULT_ACCOUNT_NAME);

        // Get all the societyBanksDetailsList where accountName does not contain UPDATED_ACCOUNT_NAME
        defaultSocietyBanksDetailsShouldBeFound("accountName.doesNotContain=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSocietyBanksDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the societyBanksDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyBanksDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSocietyBanksDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the societyBanksDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyBanksDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where lastModified is not null
        defaultSocietyBanksDetailsShouldBeFound("lastModified.specified=true");

        // Get all the societyBanksDetailsList where lastModified is null
        defaultSocietyBanksDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSocietyBanksDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyBanksDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyBanksDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSocietyBanksDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the societyBanksDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyBanksDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where lastModifiedBy is not null
        defaultSocietyBanksDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the societyBanksDetailsList where lastModifiedBy is null
        defaultSocietyBanksDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSocietyBanksDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyBanksDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSocietyBanksDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSocietyBanksDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyBanksDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSocietyBanksDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultSocietyBanksDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the societyBanksDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyBanksDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSocietyBanksDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the societyBanksDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyBanksDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where createdBy is not null
        defaultSocietyBanksDetailsShouldBeFound("createdBy.specified=true");

        // Get all the societyBanksDetailsList where createdBy is null
        defaultSocietyBanksDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where createdBy contains DEFAULT_CREATED_BY
        defaultSocietyBanksDetailsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the societyBanksDetailsList where createdBy contains UPDATED_CREATED_BY
        defaultSocietyBanksDetailsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSocietyBanksDetailsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the societyBanksDetailsList where createdBy does not contain UPDATED_CREATED_BY
        defaultSocietyBanksDetailsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultSocietyBanksDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the societyBanksDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyBanksDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSocietyBanksDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the societyBanksDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyBanksDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where createdOn is not null
        defaultSocietyBanksDetailsShouldBeFound("createdOn.specified=true");

        // Get all the societyBanksDetailsList where createdOn is null
        defaultSocietyBanksDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSocietyBanksDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyBanksDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyBanksDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSocietyBanksDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the societyBanksDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyBanksDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField1 is not null
        defaultSocietyBanksDetailsShouldBeFound("freeField1.specified=true");

        // Get all the societyBanksDetailsList where freeField1 is null
        defaultSocietyBanksDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultSocietyBanksDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyBanksDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultSocietyBanksDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultSocietyBanksDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyBanksDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultSocietyBanksDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSocietyBanksDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyBanksDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyBanksDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSocietyBanksDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the societyBanksDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyBanksDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField2 is not null
        defaultSocietyBanksDetailsShouldBeFound("freeField2.specified=true");

        // Get all the societyBanksDetailsList where freeField2 is null
        defaultSocietyBanksDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultSocietyBanksDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyBanksDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultSocietyBanksDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultSocietyBanksDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyBanksDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultSocietyBanksDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSocietyBanksDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyBanksDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyBanksDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSocietyBanksDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the societyBanksDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyBanksDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField3 is not null
        defaultSocietyBanksDetailsShouldBeFound("freeField3.specified=true");

        // Get all the societyBanksDetailsList where freeField3 is null
        defaultSocietyBanksDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultSocietyBanksDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyBanksDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultSocietyBanksDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        // Get all the societyBanksDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultSocietyBanksDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyBanksDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultSocietyBanksDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyBanksDetailsBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        societyBanksDetails.setSociety(society);
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);
        Long societyId = society.getId();

        // Get all the societyBanksDetailsList where society equals to societyId
        defaultSocietyBanksDetailsShouldBeFound("societyId.equals=" + societyId);

        // Get all the societyBanksDetailsList where society equals to (societyId + 1)
        defaultSocietyBanksDetailsShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocietyBanksDetailsShouldBeFound(String filter) throws Exception {
        restSocietyBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyBanksDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].ifsccode").value(hasItem(DEFAULT_IFSCCODE)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].accHeadCode").value(hasItem(DEFAULT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].parentAccHeadCode").value(hasItem(DEFAULT_PARENT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restSocietyBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocietyBanksDetailsShouldNotBeFound(String filter) throws Exception {
        restSocietyBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocietyBanksDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocietyBanksDetails() throws Exception {
        // Get the societyBanksDetails
        restSocietyBanksDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocietyBanksDetails() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        int databaseSizeBeforeUpdate = societyBanksDetailsRepository.findAll().size();

        // Update the societyBanksDetails
        SocietyBanksDetails updatedSocietyBanksDetails = societyBanksDetailsRepository.findById(societyBanksDetails.getId()).get();
        // Disconnect from session so that the updates on updatedSocietyBanksDetails are not directly saved in db
        em.detach(updatedSocietyBanksDetails);
        updatedSocietyBanksDetails
            .bankName(UPDATED_BANK_NAME)
            .ifsccode(UPDATED_IFSCCODE)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .isActive(UPDATED_IS_ACTIVE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .accountName(UPDATED_ACCOUNT_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(updatedSocietyBanksDetails);

        restSocietyBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyBanksDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeUpdate);
        SocietyBanksDetails testSocietyBanksDetails = societyBanksDetailsList.get(societyBanksDetailsList.size() - 1);
        assertThat(testSocietyBanksDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testSocietyBanksDetails.getIfsccode()).isEqualTo(UPDATED_IFSCCODE);
        assertThat(testSocietyBanksDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testSocietyBanksDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testSocietyBanksDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSocietyBanksDetails.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testSocietyBanksDetails.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testSocietyBanksDetails.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testSocietyBanksDetails.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testSocietyBanksDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyBanksDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyBanksDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyBanksDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyBanksDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyBanksDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyBanksDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingSocietyBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = societyBanksDetailsRepository.findAll().size();
        societyBanksDetails.setId(count.incrementAndGet());

        // Create the SocietyBanksDetails
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(societyBanksDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyBanksDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocietyBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = societyBanksDetailsRepository.findAll().size();
        societyBanksDetails.setId(count.incrementAndGet());

        // Create the SocietyBanksDetails
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(societyBanksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocietyBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = societyBanksDetailsRepository.findAll().size();
        societyBanksDetails.setId(count.incrementAndGet());

        // Create the SocietyBanksDetails
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(societyBanksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyBanksDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocietyBanksDetailsWithPatch() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        int databaseSizeBeforeUpdate = societyBanksDetailsRepository.findAll().size();

        // Update the societyBanksDetails using partial update
        SocietyBanksDetails partialUpdatedSocietyBanksDetails = new SocietyBanksDetails();
        partialUpdatedSocietyBanksDetails.setId(societyBanksDetails.getId());

        partialUpdatedSocietyBanksDetails
            .ifsccode(UPDATED_IFSCCODE)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .accountName(UPDATED_ACCOUNT_NAME)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyBanksDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyBanksDetails))
            )
            .andExpect(status().isOk());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeUpdate);
        SocietyBanksDetails testSocietyBanksDetails = societyBanksDetailsList.get(societyBanksDetailsList.size() - 1);
        assertThat(testSocietyBanksDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testSocietyBanksDetails.getIfsccode()).isEqualTo(UPDATED_IFSCCODE);
        assertThat(testSocietyBanksDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testSocietyBanksDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testSocietyBanksDetails.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testSocietyBanksDetails.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testSocietyBanksDetails.getAccHeadCode()).isEqualTo(DEFAULT_ACC_HEAD_CODE);
        assertThat(testSocietyBanksDetails.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testSocietyBanksDetails.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testSocietyBanksDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyBanksDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyBanksDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyBanksDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyBanksDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyBanksDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyBanksDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateSocietyBanksDetailsWithPatch() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        int databaseSizeBeforeUpdate = societyBanksDetailsRepository.findAll().size();

        // Update the societyBanksDetails using partial update
        SocietyBanksDetails partialUpdatedSocietyBanksDetails = new SocietyBanksDetails();
        partialUpdatedSocietyBanksDetails.setId(societyBanksDetails.getId());

        partialUpdatedSocietyBanksDetails
            .bankName(UPDATED_BANK_NAME)
            .ifsccode(UPDATED_IFSCCODE)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .isActive(UPDATED_IS_ACTIVE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .accountName(UPDATED_ACCOUNT_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyBanksDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyBanksDetails))
            )
            .andExpect(status().isOk());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeUpdate);
        SocietyBanksDetails testSocietyBanksDetails = societyBanksDetailsList.get(societyBanksDetailsList.size() - 1);
        assertThat(testSocietyBanksDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testSocietyBanksDetails.getIfsccode()).isEqualTo(UPDATED_IFSCCODE);
        assertThat(testSocietyBanksDetails.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testSocietyBanksDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testSocietyBanksDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testSocietyBanksDetails.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testSocietyBanksDetails.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testSocietyBanksDetails.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testSocietyBanksDetails.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testSocietyBanksDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyBanksDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyBanksDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyBanksDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyBanksDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyBanksDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyBanksDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingSocietyBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = societyBanksDetailsRepository.findAll().size();
        societyBanksDetails.setId(count.incrementAndGet());

        // Create the SocietyBanksDetails
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(societyBanksDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societyBanksDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocietyBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = societyBanksDetailsRepository.findAll().size();
        societyBanksDetails.setId(count.incrementAndGet());

        // Create the SocietyBanksDetails
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(societyBanksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocietyBanksDetails() throws Exception {
        int databaseSizeBeforeUpdate = societyBanksDetailsRepository.findAll().size();
        societyBanksDetails.setId(count.incrementAndGet());

        // Create the SocietyBanksDetails
        SocietyBanksDetailsDTO societyBanksDetailsDTO = societyBanksDetailsMapper.toDto(societyBanksDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyBanksDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyBanksDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyBanksDetails in the database
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocietyBanksDetails() throws Exception {
        // Initialize the database
        societyBanksDetailsRepository.saveAndFlush(societyBanksDetails);

        int databaseSizeBeforeDelete = societyBanksDetailsRepository.findAll().size();

        // Delete the societyBanksDetails
        restSocietyBanksDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, societyBanksDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocietyBanksDetails> societyBanksDetailsList = societyBanksDetailsRepository.findAll();
        assertThat(societyBanksDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

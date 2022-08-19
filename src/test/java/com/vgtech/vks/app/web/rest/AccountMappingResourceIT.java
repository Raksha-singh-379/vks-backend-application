package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.AccountMapping;
import com.vgtech.vks.app.domain.LedgerAccounts;
import com.vgtech.vks.app.domain.enumeration.MappingType;
import com.vgtech.vks.app.repository.AccountMappingRepository;
import com.vgtech.vks.app.service.criteria.AccountMappingCriteria;
import com.vgtech.vks.app.service.dto.AccountMappingDTO;
import com.vgtech.vks.app.service.mapper.AccountMappingMapper;
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
 * Integration tests for the {@link AccountMappingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountMappingResourceIT {

    private static final MappingType DEFAULT_TYPE = MappingType.HEADOFFICE;
    private static final MappingType UPDATED_TYPE = MappingType.SHARE;

    private static final String DEFAULT_MAPPING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MAPPING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LEDGER_ACC_HEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LEDGER_ACC_HEAD_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_LEDGER_ACCOUNT_ID = 1L;
    private static final Long UPDATED_LEDGER_ACCOUNT_ID = 2L;
    private static final Long SMALLER_LEDGER_ACCOUNT_ID = 1L - 1L;

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

    private static final String ENTITY_API_URL = "/api/account-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountMappingRepository accountMappingRepository;

    @Autowired
    private AccountMappingMapper accountMappingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountMappingMockMvc;

    private AccountMapping accountMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountMapping createEntity(EntityManager em) {
        AccountMapping accountMapping = new AccountMapping()
            .type(DEFAULT_TYPE)
            .mappingName(DEFAULT_MAPPING_NAME)
            .ledgerAccHeadCode(DEFAULT_LEDGER_ACC_HEAD_CODE)
            .ledgerAccountId(DEFAULT_LEDGER_ACCOUNT_ID)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return accountMapping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountMapping createUpdatedEntity(EntityManager em) {
        AccountMapping accountMapping = new AccountMapping()
            .type(UPDATED_TYPE)
            .mappingName(UPDATED_MAPPING_NAME)
            .ledgerAccHeadCode(UPDATED_LEDGER_ACC_HEAD_CODE)
            .ledgerAccountId(UPDATED_LEDGER_ACCOUNT_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return accountMapping;
    }

    @BeforeEach
    public void initTest() {
        accountMapping = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountMapping() throws Exception {
        int databaseSizeBeforeCreate = accountMappingRepository.findAll().size();
        // Create the AccountMapping
        AccountMappingDTO accountMappingDTO = accountMappingMapper.toDto(accountMapping);
        restAccountMappingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountMappingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeCreate + 1);
        AccountMapping testAccountMapping = accountMappingList.get(accountMappingList.size() - 1);
        assertThat(testAccountMapping.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAccountMapping.getMappingName()).isEqualTo(DEFAULT_MAPPING_NAME);
        assertThat(testAccountMapping.getLedgerAccHeadCode()).isEqualTo(DEFAULT_LEDGER_ACC_HEAD_CODE);
        assertThat(testAccountMapping.getLedgerAccountId()).isEqualTo(DEFAULT_LEDGER_ACCOUNT_ID);
        assertThat(testAccountMapping.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAccountMapping.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAccountMapping.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAccountMapping.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAccountMapping.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAccountMapping.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAccountMapping.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAccountMapping.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createAccountMappingWithExistingId() throws Exception {
        // Create the AccountMapping with an existing ID
        accountMapping.setId(1L);
        AccountMappingDTO accountMappingDTO = accountMappingMapper.toDto(accountMapping);

        int databaseSizeBeforeCreate = accountMappingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountMappingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccountMappings() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList
        restAccountMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mappingName").value(hasItem(DEFAULT_MAPPING_NAME)))
            .andExpect(jsonPath("$.[*].ledgerAccHeadCode").value(hasItem(DEFAULT_LEDGER_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].ledgerAccountId").value(hasItem(DEFAULT_LEDGER_ACCOUNT_ID.intValue())))
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
    void getAccountMapping() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get the accountMapping
        restAccountMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, accountMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountMapping.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.mappingName").value(DEFAULT_MAPPING_NAME))
            .andExpect(jsonPath("$.ledgerAccHeadCode").value(DEFAULT_LEDGER_ACC_HEAD_CODE))
            .andExpect(jsonPath("$.ledgerAccountId").value(DEFAULT_LEDGER_ACCOUNT_ID.intValue()))
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
    void getAccountMappingsByIdFiltering() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        Long id = accountMapping.getId();

        defaultAccountMappingShouldBeFound("id.equals=" + id);
        defaultAccountMappingShouldNotBeFound("id.notEquals=" + id);

        defaultAccountMappingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccountMappingShouldNotBeFound("id.greaterThan=" + id);

        defaultAccountMappingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccountMappingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where type equals to DEFAULT_TYPE
        defaultAccountMappingShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the accountMappingList where type equals to UPDATED_TYPE
        defaultAccountMappingShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAccountMappingShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the accountMappingList where type equals to UPDATED_TYPE
        defaultAccountMappingShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where type is not null
        defaultAccountMappingShouldBeFound("type.specified=true");

        // Get all the accountMappingList where type is null
        defaultAccountMappingShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByMappingNameIsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where mappingName equals to DEFAULT_MAPPING_NAME
        defaultAccountMappingShouldBeFound("mappingName.equals=" + DEFAULT_MAPPING_NAME);

        // Get all the accountMappingList where mappingName equals to UPDATED_MAPPING_NAME
        defaultAccountMappingShouldNotBeFound("mappingName.equals=" + UPDATED_MAPPING_NAME);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByMappingNameIsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where mappingName in DEFAULT_MAPPING_NAME or UPDATED_MAPPING_NAME
        defaultAccountMappingShouldBeFound("mappingName.in=" + DEFAULT_MAPPING_NAME + "," + UPDATED_MAPPING_NAME);

        // Get all the accountMappingList where mappingName equals to UPDATED_MAPPING_NAME
        defaultAccountMappingShouldNotBeFound("mappingName.in=" + UPDATED_MAPPING_NAME);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByMappingNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where mappingName is not null
        defaultAccountMappingShouldBeFound("mappingName.specified=true");

        // Get all the accountMappingList where mappingName is null
        defaultAccountMappingShouldNotBeFound("mappingName.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByMappingNameContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where mappingName contains DEFAULT_MAPPING_NAME
        defaultAccountMappingShouldBeFound("mappingName.contains=" + DEFAULT_MAPPING_NAME);

        // Get all the accountMappingList where mappingName contains UPDATED_MAPPING_NAME
        defaultAccountMappingShouldNotBeFound("mappingName.contains=" + UPDATED_MAPPING_NAME);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByMappingNameNotContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where mappingName does not contain DEFAULT_MAPPING_NAME
        defaultAccountMappingShouldNotBeFound("mappingName.doesNotContain=" + DEFAULT_MAPPING_NAME);

        // Get all the accountMappingList where mappingName does not contain UPDATED_MAPPING_NAME
        defaultAccountMappingShouldBeFound("mappingName.doesNotContain=" + UPDATED_MAPPING_NAME);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccHeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccHeadCode equals to DEFAULT_LEDGER_ACC_HEAD_CODE
        defaultAccountMappingShouldBeFound("ledgerAccHeadCode.equals=" + DEFAULT_LEDGER_ACC_HEAD_CODE);

        // Get all the accountMappingList where ledgerAccHeadCode equals to UPDATED_LEDGER_ACC_HEAD_CODE
        defaultAccountMappingShouldNotBeFound("ledgerAccHeadCode.equals=" + UPDATED_LEDGER_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccHeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccHeadCode in DEFAULT_LEDGER_ACC_HEAD_CODE or UPDATED_LEDGER_ACC_HEAD_CODE
        defaultAccountMappingShouldBeFound("ledgerAccHeadCode.in=" + DEFAULT_LEDGER_ACC_HEAD_CODE + "," + UPDATED_LEDGER_ACC_HEAD_CODE);

        // Get all the accountMappingList where ledgerAccHeadCode equals to UPDATED_LEDGER_ACC_HEAD_CODE
        defaultAccountMappingShouldNotBeFound("ledgerAccHeadCode.in=" + UPDATED_LEDGER_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccHeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccHeadCode is not null
        defaultAccountMappingShouldBeFound("ledgerAccHeadCode.specified=true");

        // Get all the accountMappingList where ledgerAccHeadCode is null
        defaultAccountMappingShouldNotBeFound("ledgerAccHeadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccHeadCodeContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccHeadCode contains DEFAULT_LEDGER_ACC_HEAD_CODE
        defaultAccountMappingShouldBeFound("ledgerAccHeadCode.contains=" + DEFAULT_LEDGER_ACC_HEAD_CODE);

        // Get all the accountMappingList where ledgerAccHeadCode contains UPDATED_LEDGER_ACC_HEAD_CODE
        defaultAccountMappingShouldNotBeFound("ledgerAccHeadCode.contains=" + UPDATED_LEDGER_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccHeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccHeadCode does not contain DEFAULT_LEDGER_ACC_HEAD_CODE
        defaultAccountMappingShouldNotBeFound("ledgerAccHeadCode.doesNotContain=" + DEFAULT_LEDGER_ACC_HEAD_CODE);

        // Get all the accountMappingList where ledgerAccHeadCode does not contain UPDATED_LEDGER_ACC_HEAD_CODE
        defaultAccountMappingShouldBeFound("ledgerAccHeadCode.doesNotContain=" + UPDATED_LEDGER_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccountIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccountId equals to DEFAULT_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldBeFound("ledgerAccountId.equals=" + DEFAULT_LEDGER_ACCOUNT_ID);

        // Get all the accountMappingList where ledgerAccountId equals to UPDATED_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldNotBeFound("ledgerAccountId.equals=" + UPDATED_LEDGER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccountIdIsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccountId in DEFAULT_LEDGER_ACCOUNT_ID or UPDATED_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldBeFound("ledgerAccountId.in=" + DEFAULT_LEDGER_ACCOUNT_ID + "," + UPDATED_LEDGER_ACCOUNT_ID);

        // Get all the accountMappingList where ledgerAccountId equals to UPDATED_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldNotBeFound("ledgerAccountId.in=" + UPDATED_LEDGER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccountIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccountId is not null
        defaultAccountMappingShouldBeFound("ledgerAccountId.specified=true");

        // Get all the accountMappingList where ledgerAccountId is null
        defaultAccountMappingShouldNotBeFound("ledgerAccountId.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccountIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccountId is greater than or equal to DEFAULT_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldBeFound("ledgerAccountId.greaterThanOrEqual=" + DEFAULT_LEDGER_ACCOUNT_ID);

        // Get all the accountMappingList where ledgerAccountId is greater than or equal to UPDATED_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldNotBeFound("ledgerAccountId.greaterThanOrEqual=" + UPDATED_LEDGER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccountIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccountId is less than or equal to DEFAULT_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldBeFound("ledgerAccountId.lessThanOrEqual=" + DEFAULT_LEDGER_ACCOUNT_ID);

        // Get all the accountMappingList where ledgerAccountId is less than or equal to SMALLER_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldNotBeFound("ledgerAccountId.lessThanOrEqual=" + SMALLER_LEDGER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccountIdIsLessThanSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccountId is less than DEFAULT_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldNotBeFound("ledgerAccountId.lessThan=" + DEFAULT_LEDGER_ACCOUNT_ID);

        // Get all the accountMappingList where ledgerAccountId is less than UPDATED_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldBeFound("ledgerAccountId.lessThan=" + UPDATED_LEDGER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLedgerAccountIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where ledgerAccountId is greater than DEFAULT_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldNotBeFound("ledgerAccountId.greaterThan=" + DEFAULT_LEDGER_ACCOUNT_ID);

        // Get all the accountMappingList where ledgerAccountId is greater than SMALLER_LEDGER_ACCOUNT_ID
        defaultAccountMappingShouldBeFound("ledgerAccountId.greaterThan=" + SMALLER_LEDGER_ACCOUNT_ID);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAccountMappingShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the accountMappingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAccountMappingShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAccountMappingShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the accountMappingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAccountMappingShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where lastModified is not null
        defaultAccountMappingShouldBeFound("lastModified.specified=true");

        // Get all the accountMappingList where lastModified is null
        defaultAccountMappingShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAccountMappingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the accountMappingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAccountMappingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAccountMappingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the accountMappingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAccountMappingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where lastModifiedBy is not null
        defaultAccountMappingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the accountMappingList where lastModifiedBy is null
        defaultAccountMappingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAccountMappingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the accountMappingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAccountMappingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAccountMappingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the accountMappingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAccountMappingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where createdBy equals to DEFAULT_CREATED_BY
        defaultAccountMappingShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the accountMappingList where createdBy equals to UPDATED_CREATED_BY
        defaultAccountMappingShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAccountMappingShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the accountMappingList where createdBy equals to UPDATED_CREATED_BY
        defaultAccountMappingShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where createdBy is not null
        defaultAccountMappingShouldBeFound("createdBy.specified=true");

        // Get all the accountMappingList where createdBy is null
        defaultAccountMappingShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where createdBy contains DEFAULT_CREATED_BY
        defaultAccountMappingShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the accountMappingList where createdBy contains UPDATED_CREATED_BY
        defaultAccountMappingShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where createdBy does not contain DEFAULT_CREATED_BY
        defaultAccountMappingShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the accountMappingList where createdBy does not contain UPDATED_CREATED_BY
        defaultAccountMappingShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where createdOn equals to DEFAULT_CREATED_ON
        defaultAccountMappingShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the accountMappingList where createdOn equals to UPDATED_CREATED_ON
        defaultAccountMappingShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultAccountMappingShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the accountMappingList where createdOn equals to UPDATED_CREATED_ON
        defaultAccountMappingShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where createdOn is not null
        defaultAccountMappingShouldBeFound("createdOn.specified=true");

        // Get all the accountMappingList where createdOn is null
        defaultAccountMappingShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where isDeleted equals to DEFAULT_IS_DELETED
        defaultAccountMappingShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the accountMappingList where isDeleted equals to UPDATED_IS_DELETED
        defaultAccountMappingShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultAccountMappingShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the accountMappingList where isDeleted equals to UPDATED_IS_DELETED
        defaultAccountMappingShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where isDeleted is not null
        defaultAccountMappingShouldBeFound("isDeleted.specified=true");

        // Get all the accountMappingList where isDeleted is null
        defaultAccountMappingShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultAccountMappingShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the accountMappingList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAccountMappingShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultAccountMappingShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the accountMappingList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAccountMappingShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField1 is not null
        defaultAccountMappingShouldBeFound("freeField1.specified=true");

        // Get all the accountMappingList where freeField1 is null
        defaultAccountMappingShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultAccountMappingShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the accountMappingList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultAccountMappingShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultAccountMappingShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the accountMappingList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultAccountMappingShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultAccountMappingShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the accountMappingList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAccountMappingShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultAccountMappingShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the accountMappingList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAccountMappingShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField2 is not null
        defaultAccountMappingShouldBeFound("freeField2.specified=true");

        // Get all the accountMappingList where freeField2 is null
        defaultAccountMappingShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultAccountMappingShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the accountMappingList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultAccountMappingShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultAccountMappingShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the accountMappingList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultAccountMappingShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultAccountMappingShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the accountMappingList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAccountMappingShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultAccountMappingShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the accountMappingList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAccountMappingShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField3 is not null
        defaultAccountMappingShouldBeFound("freeField3.specified=true");

        // Get all the accountMappingList where freeField3 is null
        defaultAccountMappingShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultAccountMappingShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the accountMappingList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultAccountMappingShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        // Get all the accountMappingList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultAccountMappingShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the accountMappingList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultAccountMappingShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAccountMappingsByAccountMappingIsEqualToSomething() throws Exception {
        LedgerAccounts accountMapping;
        if (TestUtil.findAll(em, LedgerAccounts.class).isEmpty()) {
            accountMappingRepository.saveAndFlush(accountMapping);
            accountMapping = LedgerAccountsResourceIT.createEntity(em);
        } else {
            accountMapping = TestUtil.findAll(em, LedgerAccounts.class).get(0);
        }
        em.persist(accountMapping);
        em.flush();
        accountMapping.setAccountMapping(accountMapping);
        accountMappingRepository.saveAndFlush(accountMapping);
        Long accountMappingId = accountMapping.getId();

        // Get all the accountMappingList where accountMapping equals to accountMappingId
        defaultAccountMappingShouldBeFound("accountMappingId.equals=" + accountMappingId);

        // Get all the accountMappingList where accountMapping equals to (accountMappingId + 1)
        defaultAccountMappingShouldNotBeFound("accountMappingId.equals=" + (accountMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccountMappingShouldBeFound(String filter) throws Exception {
        restAccountMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mappingName").value(hasItem(DEFAULT_MAPPING_NAME)))
            .andExpect(jsonPath("$.[*].ledgerAccHeadCode").value(hasItem(DEFAULT_LEDGER_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].ledgerAccountId").value(hasItem(DEFAULT_LEDGER_ACCOUNT_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restAccountMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccountMappingShouldNotBeFound(String filter) throws Exception {
        restAccountMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccountMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccountMapping() throws Exception {
        // Get the accountMapping
        restAccountMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccountMapping() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        int databaseSizeBeforeUpdate = accountMappingRepository.findAll().size();

        // Update the accountMapping
        AccountMapping updatedAccountMapping = accountMappingRepository.findById(accountMapping.getId()).get();
        // Disconnect from session so that the updates on updatedAccountMapping are not directly saved in db
        em.detach(updatedAccountMapping);
        updatedAccountMapping
            .type(UPDATED_TYPE)
            .mappingName(UPDATED_MAPPING_NAME)
            .ledgerAccHeadCode(UPDATED_LEDGER_ACC_HEAD_CODE)
            .ledgerAccountId(UPDATED_LEDGER_ACCOUNT_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        AccountMappingDTO accountMappingDTO = accountMappingMapper.toDto(updatedAccountMapping);

        restAccountMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountMappingDTO))
            )
            .andExpect(status().isOk());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeUpdate);
        AccountMapping testAccountMapping = accountMappingList.get(accountMappingList.size() - 1);
        assertThat(testAccountMapping.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAccountMapping.getMappingName()).isEqualTo(UPDATED_MAPPING_NAME);
        assertThat(testAccountMapping.getLedgerAccHeadCode()).isEqualTo(UPDATED_LEDGER_ACC_HEAD_CODE);
        assertThat(testAccountMapping.getLedgerAccountId()).isEqualTo(UPDATED_LEDGER_ACCOUNT_ID);
        assertThat(testAccountMapping.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAccountMapping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAccountMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAccountMapping.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAccountMapping.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAccountMapping.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAccountMapping.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAccountMapping.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingAccountMapping() throws Exception {
        int databaseSizeBeforeUpdate = accountMappingRepository.findAll().size();
        accountMapping.setId(count.incrementAndGet());

        // Create the AccountMapping
        AccountMappingDTO accountMappingDTO = accountMappingMapper.toDto(accountMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountMapping() throws Exception {
        int databaseSizeBeforeUpdate = accountMappingRepository.findAll().size();
        accountMapping.setId(count.incrementAndGet());

        // Create the AccountMapping
        AccountMappingDTO accountMappingDTO = accountMappingMapper.toDto(accountMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountMapping() throws Exception {
        int databaseSizeBeforeUpdate = accountMappingRepository.findAll().size();
        accountMapping.setId(count.incrementAndGet());

        // Create the AccountMapping
        AccountMappingDTO accountMappingDTO = accountMappingMapper.toDto(accountMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountMappingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountMappingWithPatch() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        int databaseSizeBeforeUpdate = accountMappingRepository.findAll().size();

        // Update the accountMapping using partial update
        AccountMapping partialUpdatedAccountMapping = new AccountMapping();
        partialUpdatedAccountMapping.setId(accountMapping.getId());

        partialUpdatedAccountMapping
            .mappingName(UPDATED_MAPPING_NAME)
            .ledgerAccountId(UPDATED_LEDGER_ACCOUNT_ID)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2);

        restAccountMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountMapping))
            )
            .andExpect(status().isOk());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeUpdate);
        AccountMapping testAccountMapping = accountMappingList.get(accountMappingList.size() - 1);
        assertThat(testAccountMapping.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAccountMapping.getMappingName()).isEqualTo(UPDATED_MAPPING_NAME);
        assertThat(testAccountMapping.getLedgerAccHeadCode()).isEqualTo(DEFAULT_LEDGER_ACC_HEAD_CODE);
        assertThat(testAccountMapping.getLedgerAccountId()).isEqualTo(UPDATED_LEDGER_ACCOUNT_ID);
        assertThat(testAccountMapping.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAccountMapping.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAccountMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAccountMapping.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAccountMapping.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAccountMapping.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAccountMapping.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAccountMapping.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateAccountMappingWithPatch() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        int databaseSizeBeforeUpdate = accountMappingRepository.findAll().size();

        // Update the accountMapping using partial update
        AccountMapping partialUpdatedAccountMapping = new AccountMapping();
        partialUpdatedAccountMapping.setId(accountMapping.getId());

        partialUpdatedAccountMapping
            .type(UPDATED_TYPE)
            .mappingName(UPDATED_MAPPING_NAME)
            .ledgerAccHeadCode(UPDATED_LEDGER_ACC_HEAD_CODE)
            .ledgerAccountId(UPDATED_LEDGER_ACCOUNT_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restAccountMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountMapping))
            )
            .andExpect(status().isOk());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeUpdate);
        AccountMapping testAccountMapping = accountMappingList.get(accountMappingList.size() - 1);
        assertThat(testAccountMapping.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAccountMapping.getMappingName()).isEqualTo(UPDATED_MAPPING_NAME);
        assertThat(testAccountMapping.getLedgerAccHeadCode()).isEqualTo(UPDATED_LEDGER_ACC_HEAD_CODE);
        assertThat(testAccountMapping.getLedgerAccountId()).isEqualTo(UPDATED_LEDGER_ACCOUNT_ID);
        assertThat(testAccountMapping.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAccountMapping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAccountMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAccountMapping.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAccountMapping.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAccountMapping.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAccountMapping.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAccountMapping.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingAccountMapping() throws Exception {
        int databaseSizeBeforeUpdate = accountMappingRepository.findAll().size();
        accountMapping.setId(count.incrementAndGet());

        // Create the AccountMapping
        AccountMappingDTO accountMappingDTO = accountMappingMapper.toDto(accountMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountMappingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountMapping() throws Exception {
        int databaseSizeBeforeUpdate = accountMappingRepository.findAll().size();
        accountMapping.setId(count.incrementAndGet());

        // Create the AccountMapping
        AccountMappingDTO accountMappingDTO = accountMappingMapper.toDto(accountMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountMapping() throws Exception {
        int databaseSizeBeforeUpdate = accountMappingRepository.findAll().size();
        accountMapping.setId(count.incrementAndGet());

        // Create the AccountMapping
        AccountMappingDTO accountMappingDTO = accountMappingMapper.toDto(accountMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountMappingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountMapping in the database
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountMapping() throws Exception {
        // Initialize the database
        accountMappingRepository.saveAndFlush(accountMapping);

        int databaseSizeBeforeDelete = accountMappingRepository.findAll().size();

        // Delete the accountMapping
        restAccountMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountMapping> accountMappingList = accountMappingRepository.findAll();
        assertThat(accountMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.SocietyAssets;
import com.vgtech.vks.app.domain.SocietyAssetsData;
import com.vgtech.vks.app.repository.SocietyAssetsDataRepository;
import com.vgtech.vks.app.service.criteria.SocietyAssetsDataCriteria;
import com.vgtech.vks.app.service.dto.SocietyAssetsDataDTO;
import com.vgtech.vks.app.service.mapper.SocietyAssetsDataMapper;
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
 * Integration tests for the {@link SocietyAssetsDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocietyAssetsDataResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final Long DEFAULT_BALANCE_QUANTITY = 1L;
    private static final Long UPDATED_BALANCE_QUANTITY = 2L;
    private static final Long SMALLER_BALANCE_QUANTITY = 1L - 1L;

    private static final Double DEFAULT_BALANCE_VALUE = 1D;
    private static final Double UPDATED_BALANCE_VALUE = 2D;
    private static final Double SMALLER_BALANCE_VALUE = 1D - 1D;

    private static final String DEFAULT_BILL_NO = "AAAAAAAAAA";
    private static final String UPDATED_BILL_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MODE = "AAAAAAAAAA";
    private static final String UPDATED_MODE = "BBBBBBBBBB";

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;
    private static final Double SMALLER_COST = 1D - 1D;

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TRANSACTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/society-assets-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocietyAssetsDataRepository societyAssetsDataRepository;

    @Autowired
    private SocietyAssetsDataMapper societyAssetsDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocietyAssetsDataMockMvc;

    private SocietyAssetsData societyAssetsData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyAssetsData createEntity(EntityManager em) {
        SocietyAssetsData societyAssetsData = new SocietyAssetsData()
            .amount(DEFAULT_AMOUNT)
            .balanceQuantity(DEFAULT_BALANCE_QUANTITY)
            .balanceValue(DEFAULT_BALANCE_VALUE)
            .billNo(DEFAULT_BILL_NO)
            .mode(DEFAULT_MODE)
            .cost(DEFAULT_COST)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return societyAssetsData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyAssetsData createUpdatedEntity(EntityManager em) {
        SocietyAssetsData societyAssetsData = new SocietyAssetsData()
            .amount(UPDATED_AMOUNT)
            .balanceQuantity(UPDATED_BALANCE_QUANTITY)
            .balanceValue(UPDATED_BALANCE_VALUE)
            .billNo(UPDATED_BILL_NO)
            .mode(UPDATED_MODE)
            .cost(UPDATED_COST)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return societyAssetsData;
    }

    @BeforeEach
    public void initTest() {
        societyAssetsData = createEntity(em);
    }

    @Test
    @Transactional
    void createSocietyAssetsData() throws Exception {
        int databaseSizeBeforeCreate = societyAssetsDataRepository.findAll().size();
        // Create the SocietyAssetsData
        SocietyAssetsDataDTO societyAssetsDataDTO = societyAssetsDataMapper.toDto(societyAssetsData);
        restSocietyAssetsDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDataDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeCreate + 1);
        SocietyAssetsData testSocietyAssetsData = societyAssetsDataList.get(societyAssetsDataList.size() - 1);
        assertThat(testSocietyAssetsData.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testSocietyAssetsData.getBalanceQuantity()).isEqualTo(DEFAULT_BALANCE_QUANTITY);
        assertThat(testSocietyAssetsData.getBalanceValue()).isEqualTo(DEFAULT_BALANCE_VALUE);
        assertThat(testSocietyAssetsData.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testSocietyAssetsData.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testSocietyAssetsData.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testSocietyAssetsData.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testSocietyAssetsData.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testSocietyAssetsData.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyAssetsData.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyAssetsData.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyAssetsData.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyAssetsData.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSocietyAssetsData.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyAssetsData.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyAssetsData.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createSocietyAssetsDataWithExistingId() throws Exception {
        // Create the SocietyAssetsData with an existing ID
        societyAssetsData.setId(1L);
        SocietyAssetsDataDTO societyAssetsDataDTO = societyAssetsDataMapper.toDto(societyAssetsData);

        int databaseSizeBeforeCreate = societyAssetsDataRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocietyAssetsDataMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsData() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList
        restSocietyAssetsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyAssetsData.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].balanceQuantity").value(hasItem(DEFAULT_BALANCE_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].balanceValue").value(hasItem(DEFAULT_BALANCE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
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
    void getSocietyAssetsData() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get the societyAssetsData
        restSocietyAssetsDataMockMvc
            .perform(get(ENTITY_API_URL_ID, societyAssetsData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societyAssetsData.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.balanceQuantity").value(DEFAULT_BALANCE_QUANTITY.intValue()))
            .andExpect(jsonPath("$.balanceValue").value(DEFAULT_BALANCE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
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
    void getSocietyAssetsDataByIdFiltering() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        Long id = societyAssetsData.getId();

        defaultSocietyAssetsDataShouldBeFound("id.equals=" + id);
        defaultSocietyAssetsDataShouldNotBeFound("id.notEquals=" + id);

        defaultSocietyAssetsDataShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocietyAssetsDataShouldNotBeFound("id.greaterThan=" + id);

        defaultSocietyAssetsDataShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocietyAssetsDataShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where amount equals to DEFAULT_AMOUNT
        defaultSocietyAssetsDataShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the societyAssetsDataList where amount equals to UPDATED_AMOUNT
        defaultSocietyAssetsDataShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultSocietyAssetsDataShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the societyAssetsDataList where amount equals to UPDATED_AMOUNT
        defaultSocietyAssetsDataShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where amount is not null
        defaultSocietyAssetsDataShouldBeFound("amount.specified=true");

        // Get all the societyAssetsDataList where amount is null
        defaultSocietyAssetsDataShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultSocietyAssetsDataShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the societyAssetsDataList where amount is greater than or equal to UPDATED_AMOUNT
        defaultSocietyAssetsDataShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where amount is less than or equal to DEFAULT_AMOUNT
        defaultSocietyAssetsDataShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the societyAssetsDataList where amount is less than or equal to SMALLER_AMOUNT
        defaultSocietyAssetsDataShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where amount is less than DEFAULT_AMOUNT
        defaultSocietyAssetsDataShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the societyAssetsDataList where amount is less than UPDATED_AMOUNT
        defaultSocietyAssetsDataShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where amount is greater than DEFAULT_AMOUNT
        defaultSocietyAssetsDataShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the societyAssetsDataList where amount is greater than SMALLER_AMOUNT
        defaultSocietyAssetsDataShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceQuantity equals to DEFAULT_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldBeFound("balanceQuantity.equals=" + DEFAULT_BALANCE_QUANTITY);

        // Get all the societyAssetsDataList where balanceQuantity equals to UPDATED_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldNotBeFound("balanceQuantity.equals=" + UPDATED_BALANCE_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceQuantity in DEFAULT_BALANCE_QUANTITY or UPDATED_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldBeFound("balanceQuantity.in=" + DEFAULT_BALANCE_QUANTITY + "," + UPDATED_BALANCE_QUANTITY);

        // Get all the societyAssetsDataList where balanceQuantity equals to UPDATED_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldNotBeFound("balanceQuantity.in=" + UPDATED_BALANCE_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceQuantity is not null
        defaultSocietyAssetsDataShouldBeFound("balanceQuantity.specified=true");

        // Get all the societyAssetsDataList where balanceQuantity is null
        defaultSocietyAssetsDataShouldNotBeFound("balanceQuantity.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceQuantity is greater than or equal to DEFAULT_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldBeFound("balanceQuantity.greaterThanOrEqual=" + DEFAULT_BALANCE_QUANTITY);

        // Get all the societyAssetsDataList where balanceQuantity is greater than or equal to UPDATED_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldNotBeFound("balanceQuantity.greaterThanOrEqual=" + UPDATED_BALANCE_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceQuantity is less than or equal to DEFAULT_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldBeFound("balanceQuantity.lessThanOrEqual=" + DEFAULT_BALANCE_QUANTITY);

        // Get all the societyAssetsDataList where balanceQuantity is less than or equal to SMALLER_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldNotBeFound("balanceQuantity.lessThanOrEqual=" + SMALLER_BALANCE_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceQuantity is less than DEFAULT_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldNotBeFound("balanceQuantity.lessThan=" + DEFAULT_BALANCE_QUANTITY);

        // Get all the societyAssetsDataList where balanceQuantity is less than UPDATED_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldBeFound("balanceQuantity.lessThan=" + UPDATED_BALANCE_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceQuantity is greater than DEFAULT_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldNotBeFound("balanceQuantity.greaterThan=" + DEFAULT_BALANCE_QUANTITY);

        // Get all the societyAssetsDataList where balanceQuantity is greater than SMALLER_BALANCE_QUANTITY
        defaultSocietyAssetsDataShouldBeFound("balanceQuantity.greaterThan=" + SMALLER_BALANCE_QUANTITY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceValueIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceValue equals to DEFAULT_BALANCE_VALUE
        defaultSocietyAssetsDataShouldBeFound("balanceValue.equals=" + DEFAULT_BALANCE_VALUE);

        // Get all the societyAssetsDataList where balanceValue equals to UPDATED_BALANCE_VALUE
        defaultSocietyAssetsDataShouldNotBeFound("balanceValue.equals=" + UPDATED_BALANCE_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceValueIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceValue in DEFAULT_BALANCE_VALUE or UPDATED_BALANCE_VALUE
        defaultSocietyAssetsDataShouldBeFound("balanceValue.in=" + DEFAULT_BALANCE_VALUE + "," + UPDATED_BALANCE_VALUE);

        // Get all the societyAssetsDataList where balanceValue equals to UPDATED_BALANCE_VALUE
        defaultSocietyAssetsDataShouldNotBeFound("balanceValue.in=" + UPDATED_BALANCE_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceValue is not null
        defaultSocietyAssetsDataShouldBeFound("balanceValue.specified=true");

        // Get all the societyAssetsDataList where balanceValue is null
        defaultSocietyAssetsDataShouldNotBeFound("balanceValue.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceValue is greater than or equal to DEFAULT_BALANCE_VALUE
        defaultSocietyAssetsDataShouldBeFound("balanceValue.greaterThanOrEqual=" + DEFAULT_BALANCE_VALUE);

        // Get all the societyAssetsDataList where balanceValue is greater than or equal to UPDATED_BALANCE_VALUE
        defaultSocietyAssetsDataShouldNotBeFound("balanceValue.greaterThanOrEqual=" + UPDATED_BALANCE_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceValue is less than or equal to DEFAULT_BALANCE_VALUE
        defaultSocietyAssetsDataShouldBeFound("balanceValue.lessThanOrEqual=" + DEFAULT_BALANCE_VALUE);

        // Get all the societyAssetsDataList where balanceValue is less than or equal to SMALLER_BALANCE_VALUE
        defaultSocietyAssetsDataShouldNotBeFound("balanceValue.lessThanOrEqual=" + SMALLER_BALANCE_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceValueIsLessThanSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceValue is less than DEFAULT_BALANCE_VALUE
        defaultSocietyAssetsDataShouldNotBeFound("balanceValue.lessThan=" + DEFAULT_BALANCE_VALUE);

        // Get all the societyAssetsDataList where balanceValue is less than UPDATED_BALANCE_VALUE
        defaultSocietyAssetsDataShouldBeFound("balanceValue.lessThan=" + UPDATED_BALANCE_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBalanceValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where balanceValue is greater than DEFAULT_BALANCE_VALUE
        defaultSocietyAssetsDataShouldNotBeFound("balanceValue.greaterThan=" + DEFAULT_BALANCE_VALUE);

        // Get all the societyAssetsDataList where balanceValue is greater than SMALLER_BALANCE_VALUE
        defaultSocietyAssetsDataShouldBeFound("balanceValue.greaterThan=" + SMALLER_BALANCE_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBillNoIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where billNo equals to DEFAULT_BILL_NO
        defaultSocietyAssetsDataShouldBeFound("billNo.equals=" + DEFAULT_BILL_NO);

        // Get all the societyAssetsDataList where billNo equals to UPDATED_BILL_NO
        defaultSocietyAssetsDataShouldNotBeFound("billNo.equals=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBillNoIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where billNo in DEFAULT_BILL_NO or UPDATED_BILL_NO
        defaultSocietyAssetsDataShouldBeFound("billNo.in=" + DEFAULT_BILL_NO + "," + UPDATED_BILL_NO);

        // Get all the societyAssetsDataList where billNo equals to UPDATED_BILL_NO
        defaultSocietyAssetsDataShouldNotBeFound("billNo.in=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBillNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where billNo is not null
        defaultSocietyAssetsDataShouldBeFound("billNo.specified=true");

        // Get all the societyAssetsDataList where billNo is null
        defaultSocietyAssetsDataShouldNotBeFound("billNo.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBillNoContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where billNo contains DEFAULT_BILL_NO
        defaultSocietyAssetsDataShouldBeFound("billNo.contains=" + DEFAULT_BILL_NO);

        // Get all the societyAssetsDataList where billNo contains UPDATED_BILL_NO
        defaultSocietyAssetsDataShouldNotBeFound("billNo.contains=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByBillNoNotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where billNo does not contain DEFAULT_BILL_NO
        defaultSocietyAssetsDataShouldNotBeFound("billNo.doesNotContain=" + DEFAULT_BILL_NO);

        // Get all the societyAssetsDataList where billNo does not contain UPDATED_BILL_NO
        defaultSocietyAssetsDataShouldBeFound("billNo.doesNotContain=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByModeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where mode equals to DEFAULT_MODE
        defaultSocietyAssetsDataShouldBeFound("mode.equals=" + DEFAULT_MODE);

        // Get all the societyAssetsDataList where mode equals to UPDATED_MODE
        defaultSocietyAssetsDataShouldNotBeFound("mode.equals=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByModeIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where mode in DEFAULT_MODE or UPDATED_MODE
        defaultSocietyAssetsDataShouldBeFound("mode.in=" + DEFAULT_MODE + "," + UPDATED_MODE);

        // Get all the societyAssetsDataList where mode equals to UPDATED_MODE
        defaultSocietyAssetsDataShouldNotBeFound("mode.in=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where mode is not null
        defaultSocietyAssetsDataShouldBeFound("mode.specified=true");

        // Get all the societyAssetsDataList where mode is null
        defaultSocietyAssetsDataShouldNotBeFound("mode.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByModeContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where mode contains DEFAULT_MODE
        defaultSocietyAssetsDataShouldBeFound("mode.contains=" + DEFAULT_MODE);

        // Get all the societyAssetsDataList where mode contains UPDATED_MODE
        defaultSocietyAssetsDataShouldNotBeFound("mode.contains=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByModeNotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where mode does not contain DEFAULT_MODE
        defaultSocietyAssetsDataShouldNotBeFound("mode.doesNotContain=" + DEFAULT_MODE);

        // Get all the societyAssetsDataList where mode does not contain UPDATED_MODE
        defaultSocietyAssetsDataShouldBeFound("mode.doesNotContain=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where cost equals to DEFAULT_COST
        defaultSocietyAssetsDataShouldBeFound("cost.equals=" + DEFAULT_COST);

        // Get all the societyAssetsDataList where cost equals to UPDATED_COST
        defaultSocietyAssetsDataShouldNotBeFound("cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCostIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where cost in DEFAULT_COST or UPDATED_COST
        defaultSocietyAssetsDataShouldBeFound("cost.in=" + DEFAULT_COST + "," + UPDATED_COST);

        // Get all the societyAssetsDataList where cost equals to UPDATED_COST
        defaultSocietyAssetsDataShouldNotBeFound("cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where cost is not null
        defaultSocietyAssetsDataShouldBeFound("cost.specified=true");

        // Get all the societyAssetsDataList where cost is null
        defaultSocietyAssetsDataShouldNotBeFound("cost.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where cost is greater than or equal to DEFAULT_COST
        defaultSocietyAssetsDataShouldBeFound("cost.greaterThanOrEqual=" + DEFAULT_COST);

        // Get all the societyAssetsDataList where cost is greater than or equal to UPDATED_COST
        defaultSocietyAssetsDataShouldNotBeFound("cost.greaterThanOrEqual=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where cost is less than or equal to DEFAULT_COST
        defaultSocietyAssetsDataShouldBeFound("cost.lessThanOrEqual=" + DEFAULT_COST);

        // Get all the societyAssetsDataList where cost is less than or equal to SMALLER_COST
        defaultSocietyAssetsDataShouldNotBeFound("cost.lessThanOrEqual=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCostIsLessThanSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where cost is less than DEFAULT_COST
        defaultSocietyAssetsDataShouldNotBeFound("cost.lessThan=" + DEFAULT_COST);

        // Get all the societyAssetsDataList where cost is less than UPDATED_COST
        defaultSocietyAssetsDataShouldBeFound("cost.lessThan=" + UPDATED_COST);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where cost is greater than DEFAULT_COST
        defaultSocietyAssetsDataShouldNotBeFound("cost.greaterThan=" + DEFAULT_COST);

        // Get all the societyAssetsDataList where cost is greater than SMALLER_COST
        defaultSocietyAssetsDataShouldBeFound("cost.greaterThan=" + SMALLER_COST);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByTransactionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where transactionType equals to DEFAULT_TRANSACTION_TYPE
        defaultSocietyAssetsDataShouldBeFound("transactionType.equals=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the societyAssetsDataList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultSocietyAssetsDataShouldNotBeFound("transactionType.equals=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByTransactionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where transactionType in DEFAULT_TRANSACTION_TYPE or UPDATED_TRANSACTION_TYPE
        defaultSocietyAssetsDataShouldBeFound("transactionType.in=" + DEFAULT_TRANSACTION_TYPE + "," + UPDATED_TRANSACTION_TYPE);

        // Get all the societyAssetsDataList where transactionType equals to UPDATED_TRANSACTION_TYPE
        defaultSocietyAssetsDataShouldNotBeFound("transactionType.in=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByTransactionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where transactionType is not null
        defaultSocietyAssetsDataShouldBeFound("transactionType.specified=true");

        // Get all the societyAssetsDataList where transactionType is null
        defaultSocietyAssetsDataShouldNotBeFound("transactionType.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByTransactionTypeContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where transactionType contains DEFAULT_TRANSACTION_TYPE
        defaultSocietyAssetsDataShouldBeFound("transactionType.contains=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the societyAssetsDataList where transactionType contains UPDATED_TRANSACTION_TYPE
        defaultSocietyAssetsDataShouldNotBeFound("transactionType.contains=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByTransactionTypeNotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where transactionType does not contain DEFAULT_TRANSACTION_TYPE
        defaultSocietyAssetsDataShouldNotBeFound("transactionType.doesNotContain=" + DEFAULT_TRANSACTION_TYPE);

        // Get all the societyAssetsDataList where transactionType does not contain UPDATED_TRANSACTION_TYPE
        defaultSocietyAssetsDataShouldBeFound("transactionType.doesNotContain=" + UPDATED_TRANSACTION_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultSocietyAssetsDataShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the societyAssetsDataList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultSocietyAssetsDataShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultSocietyAssetsDataShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the societyAssetsDataList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultSocietyAssetsDataShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where transactionDate is not null
        defaultSocietyAssetsDataShouldBeFound("transactionDate.specified=true");

        // Get all the societyAssetsDataList where transactionDate is null
        defaultSocietyAssetsDataShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSocietyAssetsDataShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the societyAssetsDataList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyAssetsDataShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSocietyAssetsDataShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the societyAssetsDataList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyAssetsDataShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where lastModified is not null
        defaultSocietyAssetsDataShouldBeFound("lastModified.specified=true");

        // Get all the societyAssetsDataList where lastModified is null
        defaultSocietyAssetsDataShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSocietyAssetsDataShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyAssetsDataList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsDataShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsDataShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the societyAssetsDataList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsDataShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where lastModifiedBy is not null
        defaultSocietyAssetsDataShouldBeFound("lastModifiedBy.specified=true");

        // Get all the societyAssetsDataList where lastModifiedBy is null
        defaultSocietyAssetsDataShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSocietyAssetsDataShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyAssetsDataList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsDataShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSocietyAssetsDataShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyAssetsDataList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsDataShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where createdBy equals to DEFAULT_CREATED_BY
        defaultSocietyAssetsDataShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the societyAssetsDataList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyAssetsDataShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSocietyAssetsDataShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the societyAssetsDataList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyAssetsDataShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where createdBy is not null
        defaultSocietyAssetsDataShouldBeFound("createdBy.specified=true");

        // Get all the societyAssetsDataList where createdBy is null
        defaultSocietyAssetsDataShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where createdBy contains DEFAULT_CREATED_BY
        defaultSocietyAssetsDataShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the societyAssetsDataList where createdBy contains UPDATED_CREATED_BY
        defaultSocietyAssetsDataShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSocietyAssetsDataShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the societyAssetsDataList where createdBy does not contain UPDATED_CREATED_BY
        defaultSocietyAssetsDataShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where createdOn equals to DEFAULT_CREATED_ON
        defaultSocietyAssetsDataShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the societyAssetsDataList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyAssetsDataShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSocietyAssetsDataShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the societyAssetsDataList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyAssetsDataShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where createdOn is not null
        defaultSocietyAssetsDataShouldBeFound("createdOn.specified=true");

        // Get all the societyAssetsDataList where createdOn is null
        defaultSocietyAssetsDataShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where isDeleted equals to DEFAULT_IS_DELETED
        defaultSocietyAssetsDataShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the societyAssetsDataList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyAssetsDataShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultSocietyAssetsDataShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the societyAssetsDataList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyAssetsDataShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where isDeleted is not null
        defaultSocietyAssetsDataShouldBeFound("isDeleted.specified=true");

        // Get all the societyAssetsDataList where isDeleted is null
        defaultSocietyAssetsDataShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSocietyAssetsDataShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyAssetsDataList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyAssetsDataShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSocietyAssetsDataShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the societyAssetsDataList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyAssetsDataShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField1 is not null
        defaultSocietyAssetsDataShouldBeFound("freeField1.specified=true");

        // Get all the societyAssetsDataList where freeField1 is null
        defaultSocietyAssetsDataShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultSocietyAssetsDataShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyAssetsDataList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultSocietyAssetsDataShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultSocietyAssetsDataShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyAssetsDataList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultSocietyAssetsDataShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSocietyAssetsDataShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyAssetsDataList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyAssetsDataShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSocietyAssetsDataShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the societyAssetsDataList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyAssetsDataShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField2 is not null
        defaultSocietyAssetsDataShouldBeFound("freeField2.specified=true");

        // Get all the societyAssetsDataList where freeField2 is null
        defaultSocietyAssetsDataShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultSocietyAssetsDataShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyAssetsDataList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultSocietyAssetsDataShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultSocietyAssetsDataShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyAssetsDataList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultSocietyAssetsDataShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSocietyAssetsDataShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyAssetsDataList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyAssetsDataShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSocietyAssetsDataShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the societyAssetsDataList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyAssetsDataShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField3 is not null
        defaultSocietyAssetsDataShouldBeFound("freeField3.specified=true");

        // Get all the societyAssetsDataList where freeField3 is null
        defaultSocietyAssetsDataShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultSocietyAssetsDataShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyAssetsDataList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultSocietyAssetsDataShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        // Get all the societyAssetsDataList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultSocietyAssetsDataShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyAssetsDataList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultSocietyAssetsDataShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsDataBySocietyAssetsIsEqualToSomething() throws Exception {
        SocietyAssets societyAssets;
        if (TestUtil.findAll(em, SocietyAssets.class).isEmpty()) {
            societyAssetsDataRepository.saveAndFlush(societyAssetsData);
            societyAssets = SocietyAssetsResourceIT.createEntity(em);
        } else {
            societyAssets = TestUtil.findAll(em, SocietyAssets.class).get(0);
        }
        em.persist(societyAssets);
        em.flush();
        societyAssetsData.setSocietyAssets(societyAssets);
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);
        Long societyAssetsId = societyAssets.getId();

        // Get all the societyAssetsDataList where societyAssets equals to societyAssetsId
        defaultSocietyAssetsDataShouldBeFound("societyAssetsId.equals=" + societyAssetsId);

        // Get all the societyAssetsDataList where societyAssets equals to (societyAssetsId + 1)
        defaultSocietyAssetsDataShouldNotBeFound("societyAssetsId.equals=" + (societyAssetsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocietyAssetsDataShouldBeFound(String filter) throws Exception {
        restSocietyAssetsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyAssetsData.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].balanceQuantity").value(hasItem(DEFAULT_BALANCE_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].balanceValue").value(hasItem(DEFAULT_BALANCE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE)))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restSocietyAssetsDataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocietyAssetsDataShouldNotBeFound(String filter) throws Exception {
        restSocietyAssetsDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocietyAssetsDataMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocietyAssetsData() throws Exception {
        // Get the societyAssetsData
        restSocietyAssetsDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocietyAssetsData() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        int databaseSizeBeforeUpdate = societyAssetsDataRepository.findAll().size();

        // Update the societyAssetsData
        SocietyAssetsData updatedSocietyAssetsData = societyAssetsDataRepository.findById(societyAssetsData.getId()).get();
        // Disconnect from session so that the updates on updatedSocietyAssetsData are not directly saved in db
        em.detach(updatedSocietyAssetsData);
        updatedSocietyAssetsData
            .amount(UPDATED_AMOUNT)
            .balanceQuantity(UPDATED_BALANCE_QUANTITY)
            .balanceValue(UPDATED_BALANCE_VALUE)
            .billNo(UPDATED_BILL_NO)
            .mode(UPDATED_MODE)
            .cost(UPDATED_COST)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        SocietyAssetsDataDTO societyAssetsDataDTO = societyAssetsDataMapper.toDto(updatedSocietyAssetsData);

        restSocietyAssetsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyAssetsDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeUpdate);
        SocietyAssetsData testSocietyAssetsData = societyAssetsDataList.get(societyAssetsDataList.size() - 1);
        assertThat(testSocietyAssetsData.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSocietyAssetsData.getBalanceQuantity()).isEqualTo(UPDATED_BALANCE_QUANTITY);
        assertThat(testSocietyAssetsData.getBalanceValue()).isEqualTo(UPDATED_BALANCE_VALUE);
        assertThat(testSocietyAssetsData.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testSocietyAssetsData.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testSocietyAssetsData.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testSocietyAssetsData.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testSocietyAssetsData.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testSocietyAssetsData.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyAssetsData.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyAssetsData.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyAssetsData.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyAssetsData.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyAssetsData.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyAssetsData.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyAssetsData.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingSocietyAssetsData() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsDataRepository.findAll().size();
        societyAssetsData.setId(count.incrementAndGet());

        // Create the SocietyAssetsData
        SocietyAssetsDataDTO societyAssetsDataDTO = societyAssetsDataMapper.toDto(societyAssetsData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyAssetsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyAssetsDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocietyAssetsData() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsDataRepository.findAll().size();
        societyAssetsData.setId(count.incrementAndGet());

        // Create the SocietyAssetsData
        SocietyAssetsDataDTO societyAssetsDataDTO = societyAssetsDataMapper.toDto(societyAssetsData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyAssetsDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocietyAssetsData() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsDataRepository.findAll().size();
        societyAssetsData.setId(count.incrementAndGet());

        // Create the SocietyAssetsData
        SocietyAssetsDataDTO societyAssetsDataDTO = societyAssetsDataMapper.toDto(societyAssetsData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyAssetsDataMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societyAssetsDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocietyAssetsDataWithPatch() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        int databaseSizeBeforeUpdate = societyAssetsDataRepository.findAll().size();

        // Update the societyAssetsData using partial update
        SocietyAssetsData partialUpdatedSocietyAssetsData = new SocietyAssetsData();
        partialUpdatedSocietyAssetsData.setId(societyAssetsData.getId());

        partialUpdatedSocietyAssetsData
            .amount(UPDATED_AMOUNT)
            .balanceQuantity(UPDATED_BALANCE_QUANTITY)
            .billNo(UPDATED_BILL_NO)
            .mode(UPDATED_MODE)
            .cost(UPDATED_COST)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1);

        restSocietyAssetsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyAssetsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyAssetsData))
            )
            .andExpect(status().isOk());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeUpdate);
        SocietyAssetsData testSocietyAssetsData = societyAssetsDataList.get(societyAssetsDataList.size() - 1);
        assertThat(testSocietyAssetsData.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSocietyAssetsData.getBalanceQuantity()).isEqualTo(UPDATED_BALANCE_QUANTITY);
        assertThat(testSocietyAssetsData.getBalanceValue()).isEqualTo(DEFAULT_BALANCE_VALUE);
        assertThat(testSocietyAssetsData.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testSocietyAssetsData.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testSocietyAssetsData.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testSocietyAssetsData.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testSocietyAssetsData.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testSocietyAssetsData.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyAssetsData.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyAssetsData.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyAssetsData.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyAssetsData.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyAssetsData.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyAssetsData.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyAssetsData.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateSocietyAssetsDataWithPatch() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        int databaseSizeBeforeUpdate = societyAssetsDataRepository.findAll().size();

        // Update the societyAssetsData using partial update
        SocietyAssetsData partialUpdatedSocietyAssetsData = new SocietyAssetsData();
        partialUpdatedSocietyAssetsData.setId(societyAssetsData.getId());

        partialUpdatedSocietyAssetsData
            .amount(UPDATED_AMOUNT)
            .balanceQuantity(UPDATED_BALANCE_QUANTITY)
            .balanceValue(UPDATED_BALANCE_VALUE)
            .billNo(UPDATED_BILL_NO)
            .mode(UPDATED_MODE)
            .cost(UPDATED_COST)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyAssetsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyAssetsData.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyAssetsData))
            )
            .andExpect(status().isOk());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeUpdate);
        SocietyAssetsData testSocietyAssetsData = societyAssetsDataList.get(societyAssetsDataList.size() - 1);
        assertThat(testSocietyAssetsData.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testSocietyAssetsData.getBalanceQuantity()).isEqualTo(UPDATED_BALANCE_QUANTITY);
        assertThat(testSocietyAssetsData.getBalanceValue()).isEqualTo(UPDATED_BALANCE_VALUE);
        assertThat(testSocietyAssetsData.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testSocietyAssetsData.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testSocietyAssetsData.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testSocietyAssetsData.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testSocietyAssetsData.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testSocietyAssetsData.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyAssetsData.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyAssetsData.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyAssetsData.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyAssetsData.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyAssetsData.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyAssetsData.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyAssetsData.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingSocietyAssetsData() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsDataRepository.findAll().size();
        societyAssetsData.setId(count.incrementAndGet());

        // Create the SocietyAssetsData
        SocietyAssetsDataDTO societyAssetsDataDTO = societyAssetsDataMapper.toDto(societyAssetsData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyAssetsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societyAssetsDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocietyAssetsData() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsDataRepository.findAll().size();
        societyAssetsData.setId(count.incrementAndGet());

        // Create the SocietyAssetsData
        SocietyAssetsDataDTO societyAssetsDataDTO = societyAssetsDataMapper.toDto(societyAssetsData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyAssetsDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocietyAssetsData() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsDataRepository.findAll().size();
        societyAssetsData.setId(count.incrementAndGet());

        // Create the SocietyAssetsData
        SocietyAssetsDataDTO societyAssetsDataDTO = societyAssetsDataMapper.toDto(societyAssetsData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyAssetsDataMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDataDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyAssetsData in the database
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocietyAssetsData() throws Exception {
        // Initialize the database
        societyAssetsDataRepository.saveAndFlush(societyAssetsData);

        int databaseSizeBeforeDelete = societyAssetsDataRepository.findAll().size();

        // Delete the societyAssetsData
        restSocietyAssetsDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, societyAssetsData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocietyAssetsData> societyAssetsDataList = societyAssetsDataRepository.findAll();
        assertThat(societyAssetsDataList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

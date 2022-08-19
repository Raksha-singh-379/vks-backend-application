package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.ExpenditureType;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.repository.ExpenditureTypeRepository;
import com.vgtech.vks.app.service.criteria.ExpenditureTypeCriteria;
import com.vgtech.vks.app.service.dto.ExpenditureTypeDTO;
import com.vgtech.vks.app.service.mapper.ExpenditureTypeMapper;
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
 * Integration tests for the {@link ExpenditureTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExpenditureTypeResourceIT {

    private static final String DEFAULT_EXPENDITURE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_EXPENDITURE_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_EXPENDITURE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXPENDITURE_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXPENDITURE_CATEGORY = false;
    private static final Boolean UPDATED_EXPENDITURE_CATEGORY = true;

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

    private static final String ENTITY_API_URL = "/api/expenditure-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExpenditureTypeRepository expenditureTypeRepository;

    @Autowired
    private ExpenditureTypeMapper expenditureTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExpenditureTypeMockMvc;

    private ExpenditureType expenditureType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpenditureType createEntity(EntityManager em) {
        ExpenditureType expenditureType = new ExpenditureType()
            .expenditureDesc(DEFAULT_EXPENDITURE_DESC)
            .expenditureType(DEFAULT_EXPENDITURE_TYPE)
            .expenditureCategory(DEFAULT_EXPENDITURE_CATEGORY)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return expenditureType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExpenditureType createUpdatedEntity(EntityManager em) {
        ExpenditureType expenditureType = new ExpenditureType()
            .expenditureDesc(UPDATED_EXPENDITURE_DESC)
            .expenditureType(UPDATED_EXPENDITURE_TYPE)
            .expenditureCategory(UPDATED_EXPENDITURE_CATEGORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return expenditureType;
    }

    @BeforeEach
    public void initTest() {
        expenditureType = createEntity(em);
    }

    @Test
    @Transactional
    void createExpenditureType() throws Exception {
        int databaseSizeBeforeCreate = expenditureTypeRepository.findAll().size();
        // Create the ExpenditureType
        ExpenditureTypeDTO expenditureTypeDTO = expenditureTypeMapper.toDto(expenditureType);
        restExpenditureTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenditureTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ExpenditureType testExpenditureType = expenditureTypeList.get(expenditureTypeList.size() - 1);
        assertThat(testExpenditureType.getExpenditureDesc()).isEqualTo(DEFAULT_EXPENDITURE_DESC);
        assertThat(testExpenditureType.getExpenditureType()).isEqualTo(DEFAULT_EXPENDITURE_TYPE);
        assertThat(testExpenditureType.getExpenditureCategory()).isEqualTo(DEFAULT_EXPENDITURE_CATEGORY);
        assertThat(testExpenditureType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testExpenditureType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testExpenditureType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExpenditureType.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testExpenditureType.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testExpenditureType.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testExpenditureType.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testExpenditureType.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createExpenditureTypeWithExistingId() throws Exception {
        // Create the ExpenditureType with an existing ID
        expenditureType.setId(1L);
        ExpenditureTypeDTO expenditureTypeDTO = expenditureTypeMapper.toDto(expenditureType);

        int databaseSizeBeforeCreate = expenditureTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExpenditureTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenditureTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExpenditureTypes() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList
        restExpenditureTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenditureType.getId().intValue())))
            .andExpect(jsonPath("$.[*].expenditureDesc").value(hasItem(DEFAULT_EXPENDITURE_DESC)))
            .andExpect(jsonPath("$.[*].expenditureType").value(hasItem(DEFAULT_EXPENDITURE_TYPE)))
            .andExpect(jsonPath("$.[*].expenditureCategory").value(hasItem(DEFAULT_EXPENDITURE_CATEGORY.booleanValue())))
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
    void getExpenditureType() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get the expenditureType
        restExpenditureTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, expenditureType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(expenditureType.getId().intValue()))
            .andExpect(jsonPath("$.expenditureDesc").value(DEFAULT_EXPENDITURE_DESC))
            .andExpect(jsonPath("$.expenditureType").value(DEFAULT_EXPENDITURE_TYPE))
            .andExpect(jsonPath("$.expenditureCategory").value(DEFAULT_EXPENDITURE_CATEGORY.booleanValue()))
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
    void getExpenditureTypesByIdFiltering() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        Long id = expenditureType.getId();

        defaultExpenditureTypeShouldBeFound("id.equals=" + id);
        defaultExpenditureTypeShouldNotBeFound("id.notEquals=" + id);

        defaultExpenditureTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExpenditureTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultExpenditureTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExpenditureTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureDescIsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureDesc equals to DEFAULT_EXPENDITURE_DESC
        defaultExpenditureTypeShouldBeFound("expenditureDesc.equals=" + DEFAULT_EXPENDITURE_DESC);

        // Get all the expenditureTypeList where expenditureDesc equals to UPDATED_EXPENDITURE_DESC
        defaultExpenditureTypeShouldNotBeFound("expenditureDesc.equals=" + UPDATED_EXPENDITURE_DESC);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureDescIsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureDesc in DEFAULT_EXPENDITURE_DESC or UPDATED_EXPENDITURE_DESC
        defaultExpenditureTypeShouldBeFound("expenditureDesc.in=" + DEFAULT_EXPENDITURE_DESC + "," + UPDATED_EXPENDITURE_DESC);

        // Get all the expenditureTypeList where expenditureDesc equals to UPDATED_EXPENDITURE_DESC
        defaultExpenditureTypeShouldNotBeFound("expenditureDesc.in=" + UPDATED_EXPENDITURE_DESC);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureDesc is not null
        defaultExpenditureTypeShouldBeFound("expenditureDesc.specified=true");

        // Get all the expenditureTypeList where expenditureDesc is null
        defaultExpenditureTypeShouldNotBeFound("expenditureDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureDescContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureDesc contains DEFAULT_EXPENDITURE_DESC
        defaultExpenditureTypeShouldBeFound("expenditureDesc.contains=" + DEFAULT_EXPENDITURE_DESC);

        // Get all the expenditureTypeList where expenditureDesc contains UPDATED_EXPENDITURE_DESC
        defaultExpenditureTypeShouldNotBeFound("expenditureDesc.contains=" + UPDATED_EXPENDITURE_DESC);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureDescNotContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureDesc does not contain DEFAULT_EXPENDITURE_DESC
        defaultExpenditureTypeShouldNotBeFound("expenditureDesc.doesNotContain=" + DEFAULT_EXPENDITURE_DESC);

        // Get all the expenditureTypeList where expenditureDesc does not contain UPDATED_EXPENDITURE_DESC
        defaultExpenditureTypeShouldBeFound("expenditureDesc.doesNotContain=" + UPDATED_EXPENDITURE_DESC);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureType equals to DEFAULT_EXPENDITURE_TYPE
        defaultExpenditureTypeShouldBeFound("expenditureType.equals=" + DEFAULT_EXPENDITURE_TYPE);

        // Get all the expenditureTypeList where expenditureType equals to UPDATED_EXPENDITURE_TYPE
        defaultExpenditureTypeShouldNotBeFound("expenditureType.equals=" + UPDATED_EXPENDITURE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureTypeIsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureType in DEFAULT_EXPENDITURE_TYPE or UPDATED_EXPENDITURE_TYPE
        defaultExpenditureTypeShouldBeFound("expenditureType.in=" + DEFAULT_EXPENDITURE_TYPE + "," + UPDATED_EXPENDITURE_TYPE);

        // Get all the expenditureTypeList where expenditureType equals to UPDATED_EXPENDITURE_TYPE
        defaultExpenditureTypeShouldNotBeFound("expenditureType.in=" + UPDATED_EXPENDITURE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureType is not null
        defaultExpenditureTypeShouldBeFound("expenditureType.specified=true");

        // Get all the expenditureTypeList where expenditureType is null
        defaultExpenditureTypeShouldNotBeFound("expenditureType.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureTypeContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureType contains DEFAULT_EXPENDITURE_TYPE
        defaultExpenditureTypeShouldBeFound("expenditureType.contains=" + DEFAULT_EXPENDITURE_TYPE);

        // Get all the expenditureTypeList where expenditureType contains UPDATED_EXPENDITURE_TYPE
        defaultExpenditureTypeShouldNotBeFound("expenditureType.contains=" + UPDATED_EXPENDITURE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureTypeNotContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureType does not contain DEFAULT_EXPENDITURE_TYPE
        defaultExpenditureTypeShouldNotBeFound("expenditureType.doesNotContain=" + DEFAULT_EXPENDITURE_TYPE);

        // Get all the expenditureTypeList where expenditureType does not contain UPDATED_EXPENDITURE_TYPE
        defaultExpenditureTypeShouldBeFound("expenditureType.doesNotContain=" + UPDATED_EXPENDITURE_TYPE);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureCategory equals to DEFAULT_EXPENDITURE_CATEGORY
        defaultExpenditureTypeShouldBeFound("expenditureCategory.equals=" + DEFAULT_EXPENDITURE_CATEGORY);

        // Get all the expenditureTypeList where expenditureCategory equals to UPDATED_EXPENDITURE_CATEGORY
        defaultExpenditureTypeShouldNotBeFound("expenditureCategory.equals=" + UPDATED_EXPENDITURE_CATEGORY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureCategory in DEFAULT_EXPENDITURE_CATEGORY or UPDATED_EXPENDITURE_CATEGORY
        defaultExpenditureTypeShouldBeFound("expenditureCategory.in=" + DEFAULT_EXPENDITURE_CATEGORY + "," + UPDATED_EXPENDITURE_CATEGORY);

        // Get all the expenditureTypeList where expenditureCategory equals to UPDATED_EXPENDITURE_CATEGORY
        defaultExpenditureTypeShouldNotBeFound("expenditureCategory.in=" + UPDATED_EXPENDITURE_CATEGORY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByExpenditureCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where expenditureCategory is not null
        defaultExpenditureTypeShouldBeFound("expenditureCategory.specified=true");

        // Get all the expenditureTypeList where expenditureCategory is null
        defaultExpenditureTypeShouldNotBeFound("expenditureCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultExpenditureTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the expenditureTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultExpenditureTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultExpenditureTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the expenditureTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultExpenditureTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where lastModified is not null
        defaultExpenditureTypeShouldBeFound("lastModified.specified=true");

        // Get all the expenditureTypeList where lastModified is null
        defaultExpenditureTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultExpenditureTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the expenditureTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultExpenditureTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultExpenditureTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the expenditureTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultExpenditureTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where lastModifiedBy is not null
        defaultExpenditureTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the expenditureTypeList where lastModifiedBy is null
        defaultExpenditureTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultExpenditureTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the expenditureTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultExpenditureTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultExpenditureTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the expenditureTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultExpenditureTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where createdBy equals to DEFAULT_CREATED_BY
        defaultExpenditureTypeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the expenditureTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultExpenditureTypeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultExpenditureTypeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the expenditureTypeList where createdBy equals to UPDATED_CREATED_BY
        defaultExpenditureTypeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where createdBy is not null
        defaultExpenditureTypeShouldBeFound("createdBy.specified=true");

        // Get all the expenditureTypeList where createdBy is null
        defaultExpenditureTypeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where createdBy contains DEFAULT_CREATED_BY
        defaultExpenditureTypeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the expenditureTypeList where createdBy contains UPDATED_CREATED_BY
        defaultExpenditureTypeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultExpenditureTypeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the expenditureTypeList where createdBy does not contain UPDATED_CREATED_BY
        defaultExpenditureTypeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where createdOn equals to DEFAULT_CREATED_ON
        defaultExpenditureTypeShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the expenditureTypeList where createdOn equals to UPDATED_CREATED_ON
        defaultExpenditureTypeShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultExpenditureTypeShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the expenditureTypeList where createdOn equals to UPDATED_CREATED_ON
        defaultExpenditureTypeShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where createdOn is not null
        defaultExpenditureTypeShouldBeFound("createdOn.specified=true");

        // Get all the expenditureTypeList where createdOn is null
        defaultExpenditureTypeShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where isDeleted equals to DEFAULT_IS_DELETED
        defaultExpenditureTypeShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the expenditureTypeList where isDeleted equals to UPDATED_IS_DELETED
        defaultExpenditureTypeShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultExpenditureTypeShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the expenditureTypeList where isDeleted equals to UPDATED_IS_DELETED
        defaultExpenditureTypeShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where isDeleted is not null
        defaultExpenditureTypeShouldBeFound("isDeleted.specified=true");

        // Get all the expenditureTypeList where isDeleted is null
        defaultExpenditureTypeShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultExpenditureTypeShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the expenditureTypeList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultExpenditureTypeShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultExpenditureTypeShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the expenditureTypeList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultExpenditureTypeShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField1 is not null
        defaultExpenditureTypeShouldBeFound("freeField1.specified=true");

        // Get all the expenditureTypeList where freeField1 is null
        defaultExpenditureTypeShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultExpenditureTypeShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the expenditureTypeList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultExpenditureTypeShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultExpenditureTypeShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the expenditureTypeList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultExpenditureTypeShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultExpenditureTypeShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the expenditureTypeList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultExpenditureTypeShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultExpenditureTypeShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the expenditureTypeList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultExpenditureTypeShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField2 is not null
        defaultExpenditureTypeShouldBeFound("freeField2.specified=true");

        // Get all the expenditureTypeList where freeField2 is null
        defaultExpenditureTypeShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultExpenditureTypeShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the expenditureTypeList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultExpenditureTypeShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultExpenditureTypeShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the expenditureTypeList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultExpenditureTypeShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultExpenditureTypeShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the expenditureTypeList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultExpenditureTypeShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultExpenditureTypeShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the expenditureTypeList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultExpenditureTypeShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField3 is not null
        defaultExpenditureTypeShouldBeFound("freeField3.specified=true");

        // Get all the expenditureTypeList where freeField3 is null
        defaultExpenditureTypeShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultExpenditureTypeShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the expenditureTypeList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultExpenditureTypeShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        // Get all the expenditureTypeList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultExpenditureTypeShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the expenditureTypeList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultExpenditureTypeShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllExpenditureTypesBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            expenditureTypeRepository.saveAndFlush(expenditureType);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        expenditureType.setSociety(society);
        expenditureTypeRepository.saveAndFlush(expenditureType);
        Long societyId = society.getId();

        // Get all the expenditureTypeList where society equals to societyId
        defaultExpenditureTypeShouldBeFound("societyId.equals=" + societyId);

        // Get all the expenditureTypeList where society equals to (societyId + 1)
        defaultExpenditureTypeShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExpenditureTypeShouldBeFound(String filter) throws Exception {
        restExpenditureTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(expenditureType.getId().intValue())))
            .andExpect(jsonPath("$.[*].expenditureDesc").value(hasItem(DEFAULT_EXPENDITURE_DESC)))
            .andExpect(jsonPath("$.[*].expenditureType").value(hasItem(DEFAULT_EXPENDITURE_TYPE)))
            .andExpect(jsonPath("$.[*].expenditureCategory").value(hasItem(DEFAULT_EXPENDITURE_CATEGORY.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restExpenditureTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExpenditureTypeShouldNotBeFound(String filter) throws Exception {
        restExpenditureTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExpenditureTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingExpenditureType() throws Exception {
        // Get the expenditureType
        restExpenditureTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExpenditureType() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        int databaseSizeBeforeUpdate = expenditureTypeRepository.findAll().size();

        // Update the expenditureType
        ExpenditureType updatedExpenditureType = expenditureTypeRepository.findById(expenditureType.getId()).get();
        // Disconnect from session so that the updates on updatedExpenditureType are not directly saved in db
        em.detach(updatedExpenditureType);
        updatedExpenditureType
            .expenditureDesc(UPDATED_EXPENDITURE_DESC)
            .expenditureType(UPDATED_EXPENDITURE_TYPE)
            .expenditureCategory(UPDATED_EXPENDITURE_CATEGORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        ExpenditureTypeDTO expenditureTypeDTO = expenditureTypeMapper.toDto(updatedExpenditureType);

        restExpenditureTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expenditureTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expenditureTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeUpdate);
        ExpenditureType testExpenditureType = expenditureTypeList.get(expenditureTypeList.size() - 1);
        assertThat(testExpenditureType.getExpenditureDesc()).isEqualTo(UPDATED_EXPENDITURE_DESC);
        assertThat(testExpenditureType.getExpenditureType()).isEqualTo(UPDATED_EXPENDITURE_TYPE);
        assertThat(testExpenditureType.getExpenditureCategory()).isEqualTo(UPDATED_EXPENDITURE_CATEGORY);
        assertThat(testExpenditureType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testExpenditureType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testExpenditureType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExpenditureType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExpenditureType.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testExpenditureType.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testExpenditureType.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testExpenditureType.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingExpenditureType() throws Exception {
        int databaseSizeBeforeUpdate = expenditureTypeRepository.findAll().size();
        expenditureType.setId(count.incrementAndGet());

        // Create the ExpenditureType
        ExpenditureTypeDTO expenditureTypeDTO = expenditureTypeMapper.toDto(expenditureType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenditureTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, expenditureTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expenditureTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExpenditureType() throws Exception {
        int databaseSizeBeforeUpdate = expenditureTypeRepository.findAll().size();
        expenditureType.setId(count.incrementAndGet());

        // Create the ExpenditureType
        ExpenditureTypeDTO expenditureTypeDTO = expenditureTypeMapper.toDto(expenditureType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenditureTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(expenditureTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExpenditureType() throws Exception {
        int databaseSizeBeforeUpdate = expenditureTypeRepository.findAll().size();
        expenditureType.setId(count.incrementAndGet());

        // Create the ExpenditureType
        ExpenditureTypeDTO expenditureTypeDTO = expenditureTypeMapper.toDto(expenditureType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenditureTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(expenditureTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExpenditureTypeWithPatch() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        int databaseSizeBeforeUpdate = expenditureTypeRepository.findAll().size();

        // Update the expenditureType using partial update
        ExpenditureType partialUpdatedExpenditureType = new ExpenditureType();
        partialUpdatedExpenditureType.setId(expenditureType.getId());

        partialUpdatedExpenditureType
            .expenditureCategory(UPDATED_EXPENDITURE_CATEGORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restExpenditureTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpenditureType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpenditureType))
            )
            .andExpect(status().isOk());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeUpdate);
        ExpenditureType testExpenditureType = expenditureTypeList.get(expenditureTypeList.size() - 1);
        assertThat(testExpenditureType.getExpenditureDesc()).isEqualTo(DEFAULT_EXPENDITURE_DESC);
        assertThat(testExpenditureType.getExpenditureType()).isEqualTo(DEFAULT_EXPENDITURE_TYPE);
        assertThat(testExpenditureType.getExpenditureCategory()).isEqualTo(UPDATED_EXPENDITURE_CATEGORY);
        assertThat(testExpenditureType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testExpenditureType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testExpenditureType.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExpenditureType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExpenditureType.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testExpenditureType.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testExpenditureType.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testExpenditureType.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateExpenditureTypeWithPatch() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        int databaseSizeBeforeUpdate = expenditureTypeRepository.findAll().size();

        // Update the expenditureType using partial update
        ExpenditureType partialUpdatedExpenditureType = new ExpenditureType();
        partialUpdatedExpenditureType.setId(expenditureType.getId());

        partialUpdatedExpenditureType
            .expenditureDesc(UPDATED_EXPENDITURE_DESC)
            .expenditureType(UPDATED_EXPENDITURE_TYPE)
            .expenditureCategory(UPDATED_EXPENDITURE_CATEGORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restExpenditureTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExpenditureType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExpenditureType))
            )
            .andExpect(status().isOk());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeUpdate);
        ExpenditureType testExpenditureType = expenditureTypeList.get(expenditureTypeList.size() - 1);
        assertThat(testExpenditureType.getExpenditureDesc()).isEqualTo(UPDATED_EXPENDITURE_DESC);
        assertThat(testExpenditureType.getExpenditureType()).isEqualTo(UPDATED_EXPENDITURE_TYPE);
        assertThat(testExpenditureType.getExpenditureCategory()).isEqualTo(UPDATED_EXPENDITURE_CATEGORY);
        assertThat(testExpenditureType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testExpenditureType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testExpenditureType.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExpenditureType.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExpenditureType.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testExpenditureType.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testExpenditureType.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testExpenditureType.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingExpenditureType() throws Exception {
        int databaseSizeBeforeUpdate = expenditureTypeRepository.findAll().size();
        expenditureType.setId(count.incrementAndGet());

        // Create the ExpenditureType
        ExpenditureTypeDTO expenditureTypeDTO = expenditureTypeMapper.toDto(expenditureType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExpenditureTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, expenditureTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expenditureTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExpenditureType() throws Exception {
        int databaseSizeBeforeUpdate = expenditureTypeRepository.findAll().size();
        expenditureType.setId(count.incrementAndGet());

        // Create the ExpenditureType
        ExpenditureTypeDTO expenditureTypeDTO = expenditureTypeMapper.toDto(expenditureType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenditureTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expenditureTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExpenditureType() throws Exception {
        int databaseSizeBeforeUpdate = expenditureTypeRepository.findAll().size();
        expenditureType.setId(count.incrementAndGet());

        // Create the ExpenditureType
        ExpenditureTypeDTO expenditureTypeDTO = expenditureTypeMapper.toDto(expenditureType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExpenditureTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(expenditureTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExpenditureType in the database
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExpenditureType() throws Exception {
        // Initialize the database
        expenditureTypeRepository.saveAndFlush(expenditureType);

        int databaseSizeBeforeDelete = expenditureTypeRepository.findAll().size();

        // Delete the expenditureType
        restExpenditureTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, expenditureType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExpenditureType> expenditureTypeList = expenditureTypeRepository.findAll();
        assertThat(expenditureTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

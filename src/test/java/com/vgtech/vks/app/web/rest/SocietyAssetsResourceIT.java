package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyAssets;
import com.vgtech.vks.app.domain.enumeration.SocietyAssetsType;
import com.vgtech.vks.app.repository.SocietyAssetsRepository;
import com.vgtech.vks.app.service.criteria.SocietyAssetsCriteria;
import com.vgtech.vks.app.service.dto.SocietyAssetsDTO;
import com.vgtech.vks.app.service.mapper.SocietyAssetsMapper;
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
 * Integration tests for the {@link SocietyAssetsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocietyAssetsResourceIT {

    private static final String DEFAULT_SOCIETY_ASSETS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SOCIETY_ASSETS_NAME = "BBBBBBBBBB";

    private static final SocietyAssetsType DEFAULT_TYPE = SocietyAssetsType.EQUIPMENT;
    private static final SocietyAssetsType UPDATED_TYPE = SocietyAssetsType.FURNITURE;

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final Double DEFAULT_DEPRECIATION = 1D;
    private static final Double UPDATED_DEPRECIATION = 2D;
    private static final Double SMALLER_DEPRECIATION = 1D - 1D;

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

    private static final String DEFAULT_FREE_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_4 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/society-assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocietyAssetsRepository societyAssetsRepository;

    @Autowired
    private SocietyAssetsMapper societyAssetsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocietyAssetsMockMvc;

    private SocietyAssets societyAssets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyAssets createEntity(EntityManager em) {
        SocietyAssets societyAssets = new SocietyAssets()
            .societyAssetsName(DEFAULT_SOCIETY_ASSETS_NAME)
            .type(DEFAULT_TYPE)
            .category(DEFAULT_CATEGORY)
            .depreciation(DEFAULT_DEPRECIATION)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return societyAssets;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyAssets createUpdatedEntity(EntityManager em) {
        SocietyAssets societyAssets = new SocietyAssets()
            .societyAssetsName(UPDATED_SOCIETY_ASSETS_NAME)
            .type(UPDATED_TYPE)
            .category(UPDATED_CATEGORY)
            .depreciation(UPDATED_DEPRECIATION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return societyAssets;
    }

    @BeforeEach
    public void initTest() {
        societyAssets = createEntity(em);
    }

    @Test
    @Transactional
    void createSocietyAssets() throws Exception {
        int databaseSizeBeforeCreate = societyAssetsRepository.findAll().size();
        // Create the SocietyAssets
        SocietyAssetsDTO societyAssetsDTO = societyAssetsMapper.toDto(societyAssets);
        restSocietyAssetsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societyAssetsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeCreate + 1);
        SocietyAssets testSocietyAssets = societyAssetsList.get(societyAssetsList.size() - 1);
        assertThat(testSocietyAssets.getSocietyAssetsName()).isEqualTo(DEFAULT_SOCIETY_ASSETS_NAME);
        assertThat(testSocietyAssets.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSocietyAssets.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testSocietyAssets.getDepreciation()).isEqualTo(DEFAULT_DEPRECIATION);
        assertThat(testSocietyAssets.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyAssets.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyAssets.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyAssets.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyAssets.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSocietyAssets.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyAssets.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyAssets.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testSocietyAssets.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createSocietyAssetsWithExistingId() throws Exception {
        // Create the SocietyAssets with an existing ID
        societyAssets.setId(1L);
        SocietyAssetsDTO societyAssetsDTO = societyAssetsMapper.toDto(societyAssets);

        int databaseSizeBeforeCreate = societyAssetsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocietyAssetsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societyAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocietyAssets() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList
        restSocietyAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].societyAssetsName").value(hasItem(DEFAULT_SOCIETY_ASSETS_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].depreciation").value(hasItem(DEFAULT_DEPRECIATION.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));
    }

    @Test
    @Transactional
    void getSocietyAssets() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get the societyAssets
        restSocietyAssetsMockMvc
            .perform(get(ENTITY_API_URL_ID, societyAssets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societyAssets.getId().intValue()))
            .andExpect(jsonPath("$.societyAssetsName").value(DEFAULT_SOCIETY_ASSETS_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.depreciation").value(DEFAULT_DEPRECIATION.doubleValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4));
    }

    @Test
    @Transactional
    void getSocietyAssetsByIdFiltering() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        Long id = societyAssets.getId();

        defaultSocietyAssetsShouldBeFound("id.equals=" + id);
        defaultSocietyAssetsShouldNotBeFound("id.notEquals=" + id);

        defaultSocietyAssetsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocietyAssetsShouldNotBeFound("id.greaterThan=" + id);

        defaultSocietyAssetsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocietyAssetsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsBySocietyAssetsNameIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where societyAssetsName equals to DEFAULT_SOCIETY_ASSETS_NAME
        defaultSocietyAssetsShouldBeFound("societyAssetsName.equals=" + DEFAULT_SOCIETY_ASSETS_NAME);

        // Get all the societyAssetsList where societyAssetsName equals to UPDATED_SOCIETY_ASSETS_NAME
        defaultSocietyAssetsShouldNotBeFound("societyAssetsName.equals=" + UPDATED_SOCIETY_ASSETS_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsBySocietyAssetsNameIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where societyAssetsName in DEFAULT_SOCIETY_ASSETS_NAME or UPDATED_SOCIETY_ASSETS_NAME
        defaultSocietyAssetsShouldBeFound("societyAssetsName.in=" + DEFAULT_SOCIETY_ASSETS_NAME + "," + UPDATED_SOCIETY_ASSETS_NAME);

        // Get all the societyAssetsList where societyAssetsName equals to UPDATED_SOCIETY_ASSETS_NAME
        defaultSocietyAssetsShouldNotBeFound("societyAssetsName.in=" + UPDATED_SOCIETY_ASSETS_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsBySocietyAssetsNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where societyAssetsName is not null
        defaultSocietyAssetsShouldBeFound("societyAssetsName.specified=true");

        // Get all the societyAssetsList where societyAssetsName is null
        defaultSocietyAssetsShouldNotBeFound("societyAssetsName.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsBySocietyAssetsNameContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where societyAssetsName contains DEFAULT_SOCIETY_ASSETS_NAME
        defaultSocietyAssetsShouldBeFound("societyAssetsName.contains=" + DEFAULT_SOCIETY_ASSETS_NAME);

        // Get all the societyAssetsList where societyAssetsName contains UPDATED_SOCIETY_ASSETS_NAME
        defaultSocietyAssetsShouldNotBeFound("societyAssetsName.contains=" + UPDATED_SOCIETY_ASSETS_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsBySocietyAssetsNameNotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where societyAssetsName does not contain DEFAULT_SOCIETY_ASSETS_NAME
        defaultSocietyAssetsShouldNotBeFound("societyAssetsName.doesNotContain=" + DEFAULT_SOCIETY_ASSETS_NAME);

        // Get all the societyAssetsList where societyAssetsName does not contain UPDATED_SOCIETY_ASSETS_NAME
        defaultSocietyAssetsShouldBeFound("societyAssetsName.doesNotContain=" + UPDATED_SOCIETY_ASSETS_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where type equals to DEFAULT_TYPE
        defaultSocietyAssetsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the societyAssetsList where type equals to UPDATED_TYPE
        defaultSocietyAssetsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultSocietyAssetsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the societyAssetsList where type equals to UPDATED_TYPE
        defaultSocietyAssetsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where type is not null
        defaultSocietyAssetsShouldBeFound("type.specified=true");

        // Get all the societyAssetsList where type is null
        defaultSocietyAssetsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where category equals to DEFAULT_CATEGORY
        defaultSocietyAssetsShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the societyAssetsList where category equals to UPDATED_CATEGORY
        defaultSocietyAssetsShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultSocietyAssetsShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the societyAssetsList where category equals to UPDATED_CATEGORY
        defaultSocietyAssetsShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where category is not null
        defaultSocietyAssetsShouldBeFound("category.specified=true");

        // Get all the societyAssetsList where category is null
        defaultSocietyAssetsShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCategoryContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where category contains DEFAULT_CATEGORY
        defaultSocietyAssetsShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the societyAssetsList where category contains UPDATED_CATEGORY
        defaultSocietyAssetsShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where category does not contain DEFAULT_CATEGORY
        defaultSocietyAssetsShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the societyAssetsList where category does not contain UPDATED_CATEGORY
        defaultSocietyAssetsShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByDepreciationIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where depreciation equals to DEFAULT_DEPRECIATION
        defaultSocietyAssetsShouldBeFound("depreciation.equals=" + DEFAULT_DEPRECIATION);

        // Get all the societyAssetsList where depreciation equals to UPDATED_DEPRECIATION
        defaultSocietyAssetsShouldNotBeFound("depreciation.equals=" + UPDATED_DEPRECIATION);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByDepreciationIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where depreciation in DEFAULT_DEPRECIATION or UPDATED_DEPRECIATION
        defaultSocietyAssetsShouldBeFound("depreciation.in=" + DEFAULT_DEPRECIATION + "," + UPDATED_DEPRECIATION);

        // Get all the societyAssetsList where depreciation equals to UPDATED_DEPRECIATION
        defaultSocietyAssetsShouldNotBeFound("depreciation.in=" + UPDATED_DEPRECIATION);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByDepreciationIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where depreciation is not null
        defaultSocietyAssetsShouldBeFound("depreciation.specified=true");

        // Get all the societyAssetsList where depreciation is null
        defaultSocietyAssetsShouldNotBeFound("depreciation.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByDepreciationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where depreciation is greater than or equal to DEFAULT_DEPRECIATION
        defaultSocietyAssetsShouldBeFound("depreciation.greaterThanOrEqual=" + DEFAULT_DEPRECIATION);

        // Get all the societyAssetsList where depreciation is greater than or equal to UPDATED_DEPRECIATION
        defaultSocietyAssetsShouldNotBeFound("depreciation.greaterThanOrEqual=" + UPDATED_DEPRECIATION);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByDepreciationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where depreciation is less than or equal to DEFAULT_DEPRECIATION
        defaultSocietyAssetsShouldBeFound("depreciation.lessThanOrEqual=" + DEFAULT_DEPRECIATION);

        // Get all the societyAssetsList where depreciation is less than or equal to SMALLER_DEPRECIATION
        defaultSocietyAssetsShouldNotBeFound("depreciation.lessThanOrEqual=" + SMALLER_DEPRECIATION);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByDepreciationIsLessThanSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where depreciation is less than DEFAULT_DEPRECIATION
        defaultSocietyAssetsShouldNotBeFound("depreciation.lessThan=" + DEFAULT_DEPRECIATION);

        // Get all the societyAssetsList where depreciation is less than UPDATED_DEPRECIATION
        defaultSocietyAssetsShouldBeFound("depreciation.lessThan=" + UPDATED_DEPRECIATION);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByDepreciationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where depreciation is greater than DEFAULT_DEPRECIATION
        defaultSocietyAssetsShouldNotBeFound("depreciation.greaterThan=" + DEFAULT_DEPRECIATION);

        // Get all the societyAssetsList where depreciation is greater than SMALLER_DEPRECIATION
        defaultSocietyAssetsShouldBeFound("depreciation.greaterThan=" + SMALLER_DEPRECIATION);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSocietyAssetsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the societyAssetsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyAssetsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSocietyAssetsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the societyAssetsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyAssetsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where lastModified is not null
        defaultSocietyAssetsShouldBeFound("lastModified.specified=true");

        // Get all the societyAssetsList where lastModified is null
        defaultSocietyAssetsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSocietyAssetsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyAssetsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the societyAssetsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where lastModifiedBy is not null
        defaultSocietyAssetsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the societyAssetsList where lastModifiedBy is null
        defaultSocietyAssetsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSocietyAssetsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyAssetsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSocietyAssetsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyAssetsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSocietyAssetsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where createdBy equals to DEFAULT_CREATED_BY
        defaultSocietyAssetsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the societyAssetsList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyAssetsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSocietyAssetsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the societyAssetsList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyAssetsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where createdBy is not null
        defaultSocietyAssetsShouldBeFound("createdBy.specified=true");

        // Get all the societyAssetsList where createdBy is null
        defaultSocietyAssetsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where createdBy contains DEFAULT_CREATED_BY
        defaultSocietyAssetsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the societyAssetsList where createdBy contains UPDATED_CREATED_BY
        defaultSocietyAssetsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSocietyAssetsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the societyAssetsList where createdBy does not contain UPDATED_CREATED_BY
        defaultSocietyAssetsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where createdOn equals to DEFAULT_CREATED_ON
        defaultSocietyAssetsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the societyAssetsList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyAssetsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSocietyAssetsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the societyAssetsList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyAssetsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where createdOn is not null
        defaultSocietyAssetsShouldBeFound("createdOn.specified=true");

        // Get all the societyAssetsList where createdOn is null
        defaultSocietyAssetsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultSocietyAssetsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the societyAssetsList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyAssetsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultSocietyAssetsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the societyAssetsList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyAssetsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where isDeleted is not null
        defaultSocietyAssetsShouldBeFound("isDeleted.specified=true");

        // Get all the societyAssetsList where isDeleted is null
        defaultSocietyAssetsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSocietyAssetsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyAssetsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyAssetsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSocietyAssetsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the societyAssetsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyAssetsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField1 is not null
        defaultSocietyAssetsShouldBeFound("freeField1.specified=true");

        // Get all the societyAssetsList where freeField1 is null
        defaultSocietyAssetsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultSocietyAssetsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyAssetsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultSocietyAssetsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultSocietyAssetsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyAssetsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultSocietyAssetsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSocietyAssetsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyAssetsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyAssetsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSocietyAssetsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the societyAssetsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyAssetsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField2 is not null
        defaultSocietyAssetsShouldBeFound("freeField2.specified=true");

        // Get all the societyAssetsList where freeField2 is null
        defaultSocietyAssetsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultSocietyAssetsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyAssetsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultSocietyAssetsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultSocietyAssetsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyAssetsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultSocietyAssetsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSocietyAssetsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyAssetsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyAssetsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSocietyAssetsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the societyAssetsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyAssetsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField3 is not null
        defaultSocietyAssetsShouldBeFound("freeField3.specified=true");

        // Get all the societyAssetsList where freeField3 is null
        defaultSocietyAssetsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultSocietyAssetsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyAssetsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultSocietyAssetsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultSocietyAssetsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyAssetsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultSocietyAssetsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultSocietyAssetsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the societyAssetsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultSocietyAssetsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultSocietyAssetsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the societyAssetsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultSocietyAssetsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField4 is not null
        defaultSocietyAssetsShouldBeFound("freeField4.specified=true");

        // Get all the societyAssetsList where freeField4 is null
        defaultSocietyAssetsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultSocietyAssetsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the societyAssetsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultSocietyAssetsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        // Get all the societyAssetsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultSocietyAssetsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the societyAssetsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultSocietyAssetsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSocietyAssetsBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            societyAssetsRepository.saveAndFlush(societyAssets);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        societyAssets.setSociety(society);
        societyAssetsRepository.saveAndFlush(societyAssets);
        Long societyId = society.getId();

        // Get all the societyAssetsList where society equals to societyId
        defaultSocietyAssetsShouldBeFound("societyId.equals=" + societyId);

        // Get all the societyAssetsList where society equals to (societyId + 1)
        defaultSocietyAssetsShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocietyAssetsShouldBeFound(String filter) throws Exception {
        restSocietyAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].societyAssetsName").value(hasItem(DEFAULT_SOCIETY_ASSETS_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].depreciation").value(hasItem(DEFAULT_DEPRECIATION.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restSocietyAssetsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocietyAssetsShouldNotBeFound(String filter) throws Exception {
        restSocietyAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocietyAssetsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocietyAssets() throws Exception {
        // Get the societyAssets
        restSocietyAssetsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocietyAssets() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        int databaseSizeBeforeUpdate = societyAssetsRepository.findAll().size();

        // Update the societyAssets
        SocietyAssets updatedSocietyAssets = societyAssetsRepository.findById(societyAssets.getId()).get();
        // Disconnect from session so that the updates on updatedSocietyAssets are not directly saved in db
        em.detach(updatedSocietyAssets);
        updatedSocietyAssets
            .societyAssetsName(UPDATED_SOCIETY_ASSETS_NAME)
            .type(UPDATED_TYPE)
            .category(UPDATED_CATEGORY)
            .depreciation(UPDATED_DEPRECIATION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        SocietyAssetsDTO societyAssetsDTO = societyAssetsMapper.toDto(updatedSocietyAssets);

        restSocietyAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyAssetsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeUpdate);
        SocietyAssets testSocietyAssets = societyAssetsList.get(societyAssetsList.size() - 1);
        assertThat(testSocietyAssets.getSocietyAssetsName()).isEqualTo(UPDATED_SOCIETY_ASSETS_NAME);
        assertThat(testSocietyAssets.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSocietyAssets.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSocietyAssets.getDepreciation()).isEqualTo(UPDATED_DEPRECIATION);
        assertThat(testSocietyAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyAssets.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyAssets.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyAssets.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyAssets.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSocietyAssets.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingSocietyAssets() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsRepository.findAll().size();
        societyAssets.setId(count.incrementAndGet());

        // Create the SocietyAssets
        SocietyAssetsDTO societyAssetsDTO = societyAssetsMapper.toDto(societyAssets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyAssetsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocietyAssets() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsRepository.findAll().size();
        societyAssets.setId(count.incrementAndGet());

        // Create the SocietyAssets
        SocietyAssetsDTO societyAssetsDTO = societyAssetsMapper.toDto(societyAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocietyAssets() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsRepository.findAll().size();
        societyAssets.setId(count.incrementAndGet());

        // Create the SocietyAssets
        SocietyAssetsDTO societyAssetsDTO = societyAssetsMapper.toDto(societyAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyAssetsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societyAssetsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocietyAssetsWithPatch() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        int databaseSizeBeforeUpdate = societyAssetsRepository.findAll().size();

        // Update the societyAssets using partial update
        SocietyAssets partialUpdatedSocietyAssets = new SocietyAssets();
        partialUpdatedSocietyAssets.setId(societyAssets.getId());

        partialUpdatedSocietyAssets
            .category(UPDATED_CATEGORY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restSocietyAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyAssets))
            )
            .andExpect(status().isOk());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeUpdate);
        SocietyAssets testSocietyAssets = societyAssetsList.get(societyAssetsList.size() - 1);
        assertThat(testSocietyAssets.getSocietyAssetsName()).isEqualTo(DEFAULT_SOCIETY_ASSETS_NAME);
        assertThat(testSocietyAssets.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSocietyAssets.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSocietyAssets.getDepreciation()).isEqualTo(DEFAULT_DEPRECIATION);
        assertThat(testSocietyAssets.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyAssets.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyAssets.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyAssets.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyAssets.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyAssets.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSocietyAssets.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdateSocietyAssetsWithPatch() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        int databaseSizeBeforeUpdate = societyAssetsRepository.findAll().size();

        // Update the societyAssets using partial update
        SocietyAssets partialUpdatedSocietyAssets = new SocietyAssets();
        partialUpdatedSocietyAssets.setId(societyAssets.getId());

        partialUpdatedSocietyAssets
            .societyAssetsName(UPDATED_SOCIETY_ASSETS_NAME)
            .type(UPDATED_TYPE)
            .category(UPDATED_CATEGORY)
            .depreciation(UPDATED_DEPRECIATION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restSocietyAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyAssets))
            )
            .andExpect(status().isOk());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeUpdate);
        SocietyAssets testSocietyAssets = societyAssetsList.get(societyAssetsList.size() - 1);
        assertThat(testSocietyAssets.getSocietyAssetsName()).isEqualTo(UPDATED_SOCIETY_ASSETS_NAME);
        assertThat(testSocietyAssets.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSocietyAssets.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testSocietyAssets.getDepreciation()).isEqualTo(UPDATED_DEPRECIATION);
        assertThat(testSocietyAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyAssets.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyAssets.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyAssets.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyAssets.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSocietyAssets.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingSocietyAssets() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsRepository.findAll().size();
        societyAssets.setId(count.incrementAndGet());

        // Create the SocietyAssets
        SocietyAssetsDTO societyAssetsDTO = societyAssetsMapper.toDto(societyAssets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societyAssetsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocietyAssets() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsRepository.findAll().size();
        societyAssets.setId(count.incrementAndGet());

        // Create the SocietyAssets
        SocietyAssetsDTO societyAssetsDTO = societyAssetsMapper.toDto(societyAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocietyAssets() throws Exception {
        int databaseSizeBeforeUpdate = societyAssetsRepository.findAll().size();
        societyAssets.setId(count.incrementAndGet());

        // Create the SocietyAssets
        SocietyAssetsDTO societyAssetsDTO = societyAssetsMapper.toDto(societyAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyAssetsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyAssets in the database
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocietyAssets() throws Exception {
        // Initialize the database
        societyAssetsRepository.saveAndFlush(societyAssets);

        int databaseSizeBeforeDelete = societyAssetsRepository.findAll().size();

        // Delete the societyAssets
        restSocietyAssetsMockMvc
            .perform(delete(ENTITY_API_URL_ID, societyAssets.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocietyAssets> societyAssetsList = societyAssetsRepository.findAll();
        assertThat(societyAssetsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

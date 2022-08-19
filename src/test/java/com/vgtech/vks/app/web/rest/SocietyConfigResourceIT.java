package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyConfig;
import com.vgtech.vks.app.repository.SocietyConfigRepository;
import com.vgtech.vks.app.service.criteria.SocietyConfigCriteria;
import com.vgtech.vks.app.service.dto.SocietyConfigDTO;
import com.vgtech.vks.app.service.mapper.SocietyConfigMapper;
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
 * Integration tests for the {@link SocietyConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocietyConfigResourceIT {

    private static final String DEFAULT_CONFIG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;
    private static final Double SMALLER_VALUE = 1D - 1D;

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/society-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocietyConfigRepository societyConfigRepository;

    @Autowired
    private SocietyConfigMapper societyConfigMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocietyConfigMockMvc;

    private SocietyConfig societyConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyConfig createEntity(EntityManager em) {
        SocietyConfig societyConfig = new SocietyConfig()
            .configName(DEFAULT_CONFIG_NAME)
            .configType(DEFAULT_CONFIG_TYPE)
            .status(DEFAULT_STATUS)
            .value(DEFAULT_VALUE)
            .year(DEFAULT_YEAR)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return societyConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyConfig createUpdatedEntity(EntityManager em) {
        SocietyConfig societyConfig = new SocietyConfig()
            .configName(UPDATED_CONFIG_NAME)
            .configType(UPDATED_CONFIG_TYPE)
            .status(UPDATED_STATUS)
            .value(UPDATED_VALUE)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return societyConfig;
    }

    @BeforeEach
    public void initTest() {
        societyConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createSocietyConfig() throws Exception {
        int databaseSizeBeforeCreate = societyConfigRepository.findAll().size();
        // Create the SocietyConfig
        SocietyConfigDTO societyConfigDTO = societyConfigMapper.toDto(societyConfig);
        restSocietyConfigMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societyConfigDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeCreate + 1);
        SocietyConfig testSocietyConfig = societyConfigList.get(societyConfigList.size() - 1);
        assertThat(testSocietyConfig.getConfigName()).isEqualTo(DEFAULT_CONFIG_NAME);
        assertThat(testSocietyConfig.getConfigType()).isEqualTo(DEFAULT_CONFIG_TYPE);
        assertThat(testSocietyConfig.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testSocietyConfig.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testSocietyConfig.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSocietyConfig.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyConfig.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyConfig.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyConfig.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyConfig.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSocietyConfig.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyConfig.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyConfig.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createSocietyConfigWithExistingId() throws Exception {
        // Create the SocietyConfig with an existing ID
        societyConfig.setId(1L);
        SocietyConfigDTO societyConfigDTO = societyConfigMapper.toDto(societyConfig);

        int databaseSizeBeforeCreate = societyConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocietyConfigMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societyConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocietyConfigs() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList
        restSocietyConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].configName").value(hasItem(DEFAULT_CONFIG_NAME)))
            .andExpect(jsonPath("$.[*].configType").value(hasItem(DEFAULT_CONFIG_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
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
    void getSocietyConfig() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get the societyConfig
        restSocietyConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, societyConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societyConfig.getId().intValue()))
            .andExpect(jsonPath("$.configName").value(DEFAULT_CONFIG_NAME))
            .andExpect(jsonPath("$.configType").value(DEFAULT_CONFIG_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
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
    void getSocietyConfigsByIdFiltering() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        Long id = societyConfig.getId();

        defaultSocietyConfigShouldBeFound("id.equals=" + id);
        defaultSocietyConfigShouldNotBeFound("id.notEquals=" + id);

        defaultSocietyConfigShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocietyConfigShouldNotBeFound("id.greaterThan=" + id);

        defaultSocietyConfigShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocietyConfigShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigNameIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configName equals to DEFAULT_CONFIG_NAME
        defaultSocietyConfigShouldBeFound("configName.equals=" + DEFAULT_CONFIG_NAME);

        // Get all the societyConfigList where configName equals to UPDATED_CONFIG_NAME
        defaultSocietyConfigShouldNotBeFound("configName.equals=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigNameIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configName in DEFAULT_CONFIG_NAME or UPDATED_CONFIG_NAME
        defaultSocietyConfigShouldBeFound("configName.in=" + DEFAULT_CONFIG_NAME + "," + UPDATED_CONFIG_NAME);

        // Get all the societyConfigList where configName equals to UPDATED_CONFIG_NAME
        defaultSocietyConfigShouldNotBeFound("configName.in=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configName is not null
        defaultSocietyConfigShouldBeFound("configName.specified=true");

        // Get all the societyConfigList where configName is null
        defaultSocietyConfigShouldNotBeFound("configName.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigNameContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configName contains DEFAULT_CONFIG_NAME
        defaultSocietyConfigShouldBeFound("configName.contains=" + DEFAULT_CONFIG_NAME);

        // Get all the societyConfigList where configName contains UPDATED_CONFIG_NAME
        defaultSocietyConfigShouldNotBeFound("configName.contains=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigNameNotContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configName does not contain DEFAULT_CONFIG_NAME
        defaultSocietyConfigShouldNotBeFound("configName.doesNotContain=" + DEFAULT_CONFIG_NAME);

        // Get all the societyConfigList where configName does not contain UPDATED_CONFIG_NAME
        defaultSocietyConfigShouldBeFound("configName.doesNotContain=" + UPDATED_CONFIG_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configType equals to DEFAULT_CONFIG_TYPE
        defaultSocietyConfigShouldBeFound("configType.equals=" + DEFAULT_CONFIG_TYPE);

        // Get all the societyConfigList where configType equals to UPDATED_CONFIG_TYPE
        defaultSocietyConfigShouldNotBeFound("configType.equals=" + UPDATED_CONFIG_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigTypeIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configType in DEFAULT_CONFIG_TYPE or UPDATED_CONFIG_TYPE
        defaultSocietyConfigShouldBeFound("configType.in=" + DEFAULT_CONFIG_TYPE + "," + UPDATED_CONFIG_TYPE);

        // Get all the societyConfigList where configType equals to UPDATED_CONFIG_TYPE
        defaultSocietyConfigShouldNotBeFound("configType.in=" + UPDATED_CONFIG_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configType is not null
        defaultSocietyConfigShouldBeFound("configType.specified=true");

        // Get all the societyConfigList where configType is null
        defaultSocietyConfigShouldNotBeFound("configType.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigTypeContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configType contains DEFAULT_CONFIG_TYPE
        defaultSocietyConfigShouldBeFound("configType.contains=" + DEFAULT_CONFIG_TYPE);

        // Get all the societyConfigList where configType contains UPDATED_CONFIG_TYPE
        defaultSocietyConfigShouldNotBeFound("configType.contains=" + UPDATED_CONFIG_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByConfigTypeNotContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where configType does not contain DEFAULT_CONFIG_TYPE
        defaultSocietyConfigShouldNotBeFound("configType.doesNotContain=" + DEFAULT_CONFIG_TYPE);

        // Get all the societyConfigList where configType does not contain UPDATED_CONFIG_TYPE
        defaultSocietyConfigShouldBeFound("configType.doesNotContain=" + UPDATED_CONFIG_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where status equals to DEFAULT_STATUS
        defaultSocietyConfigShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the societyConfigList where status equals to UPDATED_STATUS
        defaultSocietyConfigShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSocietyConfigShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the societyConfigList where status equals to UPDATED_STATUS
        defaultSocietyConfigShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where status is not null
        defaultSocietyConfigShouldBeFound("status.specified=true");

        // Get all the societyConfigList where status is null
        defaultSocietyConfigShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByStatusContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where status contains DEFAULT_STATUS
        defaultSocietyConfigShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the societyConfigList where status contains UPDATED_STATUS
        defaultSocietyConfigShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where status does not contain DEFAULT_STATUS
        defaultSocietyConfigShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the societyConfigList where status does not contain UPDATED_STATUS
        defaultSocietyConfigShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where value equals to DEFAULT_VALUE
        defaultSocietyConfigShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the societyConfigList where value equals to UPDATED_VALUE
        defaultSocietyConfigShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultSocietyConfigShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the societyConfigList where value equals to UPDATED_VALUE
        defaultSocietyConfigShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where value is not null
        defaultSocietyConfigShouldBeFound("value.specified=true");

        // Get all the societyConfigList where value is null
        defaultSocietyConfigShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where value is greater than or equal to DEFAULT_VALUE
        defaultSocietyConfigShouldBeFound("value.greaterThanOrEqual=" + DEFAULT_VALUE);

        // Get all the societyConfigList where value is greater than or equal to UPDATED_VALUE
        defaultSocietyConfigShouldNotBeFound("value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where value is less than or equal to DEFAULT_VALUE
        defaultSocietyConfigShouldBeFound("value.lessThanOrEqual=" + DEFAULT_VALUE);

        // Get all the societyConfigList where value is less than or equal to SMALLER_VALUE
        defaultSocietyConfigShouldNotBeFound("value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where value is less than DEFAULT_VALUE
        defaultSocietyConfigShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the societyConfigList where value is less than UPDATED_VALUE
        defaultSocietyConfigShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where value is greater than DEFAULT_VALUE
        defaultSocietyConfigShouldNotBeFound("value.greaterThan=" + DEFAULT_VALUE);

        // Get all the societyConfigList where value is greater than SMALLER_VALUE
        defaultSocietyConfigShouldBeFound("value.greaterThan=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where year equals to DEFAULT_YEAR
        defaultSocietyConfigShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the societyConfigList where year equals to UPDATED_YEAR
        defaultSocietyConfigShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultSocietyConfigShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the societyConfigList where year equals to UPDATED_YEAR
        defaultSocietyConfigShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where year is not null
        defaultSocietyConfigShouldBeFound("year.specified=true");

        // Get all the societyConfigList where year is null
        defaultSocietyConfigShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByYearContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where year contains DEFAULT_YEAR
        defaultSocietyConfigShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the societyConfigList where year contains UPDATED_YEAR
        defaultSocietyConfigShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where year does not contain DEFAULT_YEAR
        defaultSocietyConfigShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the societyConfigList where year does not contain UPDATED_YEAR
        defaultSocietyConfigShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSocietyConfigShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the societyConfigList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyConfigShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSocietyConfigShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the societyConfigList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyConfigShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where lastModified is not null
        defaultSocietyConfigShouldBeFound("lastModified.specified=true");

        // Get all the societyConfigList where lastModified is null
        defaultSocietyConfigShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSocietyConfigShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyConfigList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyConfigShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSocietyConfigShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the societyConfigList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyConfigShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where lastModifiedBy is not null
        defaultSocietyConfigShouldBeFound("lastModifiedBy.specified=true");

        // Get all the societyConfigList where lastModifiedBy is null
        defaultSocietyConfigShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSocietyConfigShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyConfigList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSocietyConfigShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSocietyConfigShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyConfigList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSocietyConfigShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where createdBy equals to DEFAULT_CREATED_BY
        defaultSocietyConfigShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the societyConfigList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyConfigShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSocietyConfigShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the societyConfigList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyConfigShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where createdBy is not null
        defaultSocietyConfigShouldBeFound("createdBy.specified=true");

        // Get all the societyConfigList where createdBy is null
        defaultSocietyConfigShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where createdBy contains DEFAULT_CREATED_BY
        defaultSocietyConfigShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the societyConfigList where createdBy contains UPDATED_CREATED_BY
        defaultSocietyConfigShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSocietyConfigShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the societyConfigList where createdBy does not contain UPDATED_CREATED_BY
        defaultSocietyConfigShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where createdOn equals to DEFAULT_CREATED_ON
        defaultSocietyConfigShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the societyConfigList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyConfigShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSocietyConfigShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the societyConfigList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyConfigShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where createdOn is not null
        defaultSocietyConfigShouldBeFound("createdOn.specified=true");

        // Get all the societyConfigList where createdOn is null
        defaultSocietyConfigShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where isDeleted equals to DEFAULT_IS_DELETED
        defaultSocietyConfigShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the societyConfigList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyConfigShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultSocietyConfigShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the societyConfigList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyConfigShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where isDeleted is not null
        defaultSocietyConfigShouldBeFound("isDeleted.specified=true");

        // Get all the societyConfigList where isDeleted is null
        defaultSocietyConfigShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSocietyConfigShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyConfigList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyConfigShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSocietyConfigShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the societyConfigList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyConfigShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField1 is not null
        defaultSocietyConfigShouldBeFound("freeField1.specified=true");

        // Get all the societyConfigList where freeField1 is null
        defaultSocietyConfigShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultSocietyConfigShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyConfigList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultSocietyConfigShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultSocietyConfigShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyConfigList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultSocietyConfigShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSocietyConfigShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyConfigList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyConfigShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSocietyConfigShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the societyConfigList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyConfigShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField2 is not null
        defaultSocietyConfigShouldBeFound("freeField2.specified=true");

        // Get all the societyConfigList where freeField2 is null
        defaultSocietyConfigShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultSocietyConfigShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyConfigList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultSocietyConfigShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultSocietyConfigShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyConfigList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultSocietyConfigShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSocietyConfigShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyConfigList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyConfigShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSocietyConfigShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the societyConfigList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyConfigShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField3 is not null
        defaultSocietyConfigShouldBeFound("freeField3.specified=true");

        // Get all the societyConfigList where freeField3 is null
        defaultSocietyConfigShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultSocietyConfigShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyConfigList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultSocietyConfigShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        // Get all the societyConfigList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultSocietyConfigShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyConfigList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultSocietyConfigShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyConfigsBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            societyConfigRepository.saveAndFlush(societyConfig);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        societyConfig.setSociety(society);
        societyConfigRepository.saveAndFlush(societyConfig);
        Long societyId = society.getId();

        // Get all the societyConfigList where society equals to societyId
        defaultSocietyConfigShouldBeFound("societyId.equals=" + societyId);

        // Get all the societyConfigList where society equals to (societyId + 1)
        defaultSocietyConfigShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    @Test
    @Transactional
    void getAllSocietyConfigsByBankDhoranDetailsIsEqualToSomething() throws Exception {
        BankDhoranDetails bankDhoranDetails;
        if (TestUtil.findAll(em, BankDhoranDetails.class).isEmpty()) {
            societyConfigRepository.saveAndFlush(societyConfig);
            bankDhoranDetails = BankDhoranDetailsResourceIT.createEntity(em);
        } else {
            bankDhoranDetails = TestUtil.findAll(em, BankDhoranDetails.class).get(0);
        }
        em.persist(bankDhoranDetails);
        em.flush();
        societyConfig.setBankDhoranDetails(bankDhoranDetails);
        societyConfigRepository.saveAndFlush(societyConfig);
        Long bankDhoranDetailsId = bankDhoranDetails.getId();

        // Get all the societyConfigList where bankDhoranDetails equals to bankDhoranDetailsId
        defaultSocietyConfigShouldBeFound("bankDhoranDetailsId.equals=" + bankDhoranDetailsId);

        // Get all the societyConfigList where bankDhoranDetails equals to (bankDhoranDetailsId + 1)
        defaultSocietyConfigShouldNotBeFound("bankDhoranDetailsId.equals=" + (bankDhoranDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocietyConfigShouldBeFound(String filter) throws Exception {
        restSocietyConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].configName").value(hasItem(DEFAULT_CONFIG_NAME)))
            .andExpect(jsonPath("$.[*].configType").value(hasItem(DEFAULT_CONFIG_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restSocietyConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocietyConfigShouldNotBeFound(String filter) throws Exception {
        restSocietyConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocietyConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocietyConfig() throws Exception {
        // Get the societyConfig
        restSocietyConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocietyConfig() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        int databaseSizeBeforeUpdate = societyConfigRepository.findAll().size();

        // Update the societyConfig
        SocietyConfig updatedSocietyConfig = societyConfigRepository.findById(societyConfig.getId()).get();
        // Disconnect from session so that the updates on updatedSocietyConfig are not directly saved in db
        em.detach(updatedSocietyConfig);
        updatedSocietyConfig
            .configName(UPDATED_CONFIG_NAME)
            .configType(UPDATED_CONFIG_TYPE)
            .status(UPDATED_STATUS)
            .value(UPDATED_VALUE)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        SocietyConfigDTO societyConfigDTO = societyConfigMapper.toDto(updatedSocietyConfig);

        restSocietyConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyConfigDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeUpdate);
        SocietyConfig testSocietyConfig = societyConfigList.get(societyConfigList.size() - 1);
        assertThat(testSocietyConfig.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
        assertThat(testSocietyConfig.getConfigType()).isEqualTo(UPDATED_CONFIG_TYPE);
        assertThat(testSocietyConfig.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSocietyConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSocietyConfig.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSocietyConfig.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyConfig.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyConfig.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyConfig.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyConfig.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyConfig.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyConfig.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingSocietyConfig() throws Exception {
        int databaseSizeBeforeUpdate = societyConfigRepository.findAll().size();
        societyConfig.setId(count.incrementAndGet());

        // Create the SocietyConfig
        SocietyConfigDTO societyConfigDTO = societyConfigMapper.toDto(societyConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocietyConfig() throws Exception {
        int databaseSizeBeforeUpdate = societyConfigRepository.findAll().size();
        societyConfig.setId(count.incrementAndGet());

        // Create the SocietyConfig
        SocietyConfigDTO societyConfigDTO = societyConfigMapper.toDto(societyConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocietyConfig() throws Exception {
        int databaseSizeBeforeUpdate = societyConfigRepository.findAll().size();
        societyConfig.setId(count.incrementAndGet());

        // Create the SocietyConfig
        SocietyConfigDTO societyConfigDTO = societyConfigMapper.toDto(societyConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyConfigMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societyConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocietyConfigWithPatch() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        int databaseSizeBeforeUpdate = societyConfigRepository.findAll().size();

        // Update the societyConfig using partial update
        SocietyConfig partialUpdatedSocietyConfig = new SocietyConfig();
        partialUpdatedSocietyConfig.setId(societyConfig.getId());

        partialUpdatedSocietyConfig
            .configName(UPDATED_CONFIG_NAME)
            .status(UPDATED_STATUS)
            .value(UPDATED_VALUE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2);

        restSocietyConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyConfig))
            )
            .andExpect(status().isOk());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeUpdate);
        SocietyConfig testSocietyConfig = societyConfigList.get(societyConfigList.size() - 1);
        assertThat(testSocietyConfig.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
        assertThat(testSocietyConfig.getConfigType()).isEqualTo(DEFAULT_CONFIG_TYPE);
        assertThat(testSocietyConfig.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSocietyConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSocietyConfig.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSocietyConfig.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyConfig.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyConfig.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyConfig.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyConfig.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyConfig.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyConfig.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateSocietyConfigWithPatch() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        int databaseSizeBeforeUpdate = societyConfigRepository.findAll().size();

        // Update the societyConfig using partial update
        SocietyConfig partialUpdatedSocietyConfig = new SocietyConfig();
        partialUpdatedSocietyConfig.setId(societyConfig.getId());

        partialUpdatedSocietyConfig
            .configName(UPDATED_CONFIG_NAME)
            .configType(UPDATED_CONFIG_TYPE)
            .status(UPDATED_STATUS)
            .value(UPDATED_VALUE)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyConfig))
            )
            .andExpect(status().isOk());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeUpdate);
        SocietyConfig testSocietyConfig = societyConfigList.get(societyConfigList.size() - 1);
        assertThat(testSocietyConfig.getConfigName()).isEqualTo(UPDATED_CONFIG_NAME);
        assertThat(testSocietyConfig.getConfigType()).isEqualTo(UPDATED_CONFIG_TYPE);
        assertThat(testSocietyConfig.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testSocietyConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testSocietyConfig.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSocietyConfig.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyConfig.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyConfig.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyConfig.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyConfig.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyConfig.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyConfig.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingSocietyConfig() throws Exception {
        int databaseSizeBeforeUpdate = societyConfigRepository.findAll().size();
        societyConfig.setId(count.incrementAndGet());

        // Create the SocietyConfig
        SocietyConfigDTO societyConfigDTO = societyConfigMapper.toDto(societyConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societyConfigDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocietyConfig() throws Exception {
        int databaseSizeBeforeUpdate = societyConfigRepository.findAll().size();
        societyConfig.setId(count.incrementAndGet());

        // Create the SocietyConfig
        SocietyConfigDTO societyConfigDTO = societyConfigMapper.toDto(societyConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocietyConfig() throws Exception {
        int databaseSizeBeforeUpdate = societyConfigRepository.findAll().size();
        societyConfig.setId(count.incrementAndGet());

        // Create the SocietyConfig
        SocietyConfigDTO societyConfigDTO = societyConfigMapper.toDto(societyConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyConfigMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyConfig in the database
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocietyConfig() throws Exception {
        // Initialize the database
        societyConfigRepository.saveAndFlush(societyConfig);

        int databaseSizeBeforeDelete = societyConfigRepository.findAll().size();

        // Delete the societyConfig
        restSocietyConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, societyConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocietyConfig> societyConfigList = societyConfigRepository.findAll();
        assertThat(societyConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

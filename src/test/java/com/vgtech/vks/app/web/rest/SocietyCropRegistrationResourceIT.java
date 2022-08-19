package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyCropRegistration;
import com.vgtech.vks.app.domain.enumeration.Season;
import com.vgtech.vks.app.repository.SocietyCropRegistrationRepository;
import com.vgtech.vks.app.service.criteria.SocietyCropRegistrationCriteria;
import com.vgtech.vks.app.service.dto.SocietyCropRegistrationDTO;
import com.vgtech.vks.app.service.mapper.SocietyCropRegistrationMapper;
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
 * Integration tests for the {@link SocietyCropRegistrationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocietyCropRegistrationResourceIT {

    private static final String DEFAULT_CROP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CROP_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MONTH_DURATION = 1;
    private static final Integer UPDATED_MONTH_DURATION = 2;
    private static final Integer SMALLER_MONTH_DURATION = 1 - 1;

    private static final Season DEFAULT_SEASON = Season.KHARIP;
    private static final Season UPDATED_SEASON = Season.RABBI;

    private static final Double DEFAULT_CROP_LIMIT = 1D;
    private static final Double UPDATED_CROP_LIMIT = 2D;
    private static final Double SMALLER_CROP_LIMIT = 1D - 1D;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/society-crop-registrations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocietyCropRegistrationRepository societyCropRegistrationRepository;

    @Autowired
    private SocietyCropRegistrationMapper societyCropRegistrationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocietyCropRegistrationMockMvc;

    private SocietyCropRegistration societyCropRegistration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyCropRegistration createEntity(EntityManager em) {
        SocietyCropRegistration societyCropRegistration = new SocietyCropRegistration()
            .cropName(DEFAULT_CROP_NAME)
            .monthDuration(DEFAULT_MONTH_DURATION)
            .season(DEFAULT_SEASON)
            .cropLimit(DEFAULT_CROP_LIMIT)
            .year(DEFAULT_YEAR)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return societyCropRegistration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyCropRegistration createUpdatedEntity(EntityManager em) {
        SocietyCropRegistration societyCropRegistration = new SocietyCropRegistration()
            .cropName(UPDATED_CROP_NAME)
            .monthDuration(UPDATED_MONTH_DURATION)
            .season(UPDATED_SEASON)
            .cropLimit(UPDATED_CROP_LIMIT)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return societyCropRegistration;
    }

    @BeforeEach
    public void initTest() {
        societyCropRegistration = createEntity(em);
    }

    @Test
    @Transactional
    void createSocietyCropRegistration() throws Exception {
        int databaseSizeBeforeCreate = societyCropRegistrationRepository.findAll().size();
        // Create the SocietyCropRegistration
        SocietyCropRegistrationDTO societyCropRegistrationDTO = societyCropRegistrationMapper.toDto(societyCropRegistration);
        restSocietyCropRegistrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyCropRegistrationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeCreate + 1);
        SocietyCropRegistration testSocietyCropRegistration = societyCropRegistrationList.get(societyCropRegistrationList.size() - 1);
        assertThat(testSocietyCropRegistration.getCropName()).isEqualTo(DEFAULT_CROP_NAME);
        assertThat(testSocietyCropRegistration.getMonthDuration()).isEqualTo(DEFAULT_MONTH_DURATION);
        assertThat(testSocietyCropRegistration.getSeason()).isEqualTo(DEFAULT_SEASON);
        assertThat(testSocietyCropRegistration.getCropLimit()).isEqualTo(DEFAULT_CROP_LIMIT);
        assertThat(testSocietyCropRegistration.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSocietyCropRegistration.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyCropRegistration.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyCropRegistration.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyCropRegistration.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyCropRegistration.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSocietyCropRegistration.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyCropRegistration.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyCropRegistration.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createSocietyCropRegistrationWithExistingId() throws Exception {
        // Create the SocietyCropRegistration with an existing ID
        societyCropRegistration.setId(1L);
        SocietyCropRegistrationDTO societyCropRegistrationDTO = societyCropRegistrationMapper.toDto(societyCropRegistration);

        int databaseSizeBeforeCreate = societyCropRegistrationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocietyCropRegistrationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyCropRegistrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrations() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList
        restSocietyCropRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyCropRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].monthDuration").value(hasItem(DEFAULT_MONTH_DURATION)))
            .andExpect(jsonPath("$.[*].season").value(hasItem(DEFAULT_SEASON.toString())))
            .andExpect(jsonPath("$.[*].cropLimit").value(hasItem(DEFAULT_CROP_LIMIT.doubleValue())))
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
    void getSocietyCropRegistration() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get the societyCropRegistration
        restSocietyCropRegistrationMockMvc
            .perform(get(ENTITY_API_URL_ID, societyCropRegistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societyCropRegistration.getId().intValue()))
            .andExpect(jsonPath("$.cropName").value(DEFAULT_CROP_NAME))
            .andExpect(jsonPath("$.monthDuration").value(DEFAULT_MONTH_DURATION))
            .andExpect(jsonPath("$.season").value(DEFAULT_SEASON.toString()))
            .andExpect(jsonPath("$.cropLimit").value(DEFAULT_CROP_LIMIT.doubleValue()))
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
    void getSocietyCropRegistrationsByIdFiltering() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        Long id = societyCropRegistration.getId();

        defaultSocietyCropRegistrationShouldBeFound("id.equals=" + id);
        defaultSocietyCropRegistrationShouldNotBeFound("id.notEquals=" + id);

        defaultSocietyCropRegistrationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocietyCropRegistrationShouldNotBeFound("id.greaterThan=" + id);

        defaultSocietyCropRegistrationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocietyCropRegistrationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropNameIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropName equals to DEFAULT_CROP_NAME
        defaultSocietyCropRegistrationShouldBeFound("cropName.equals=" + DEFAULT_CROP_NAME);

        // Get all the societyCropRegistrationList where cropName equals to UPDATED_CROP_NAME
        defaultSocietyCropRegistrationShouldNotBeFound("cropName.equals=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropNameIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropName in DEFAULT_CROP_NAME or UPDATED_CROP_NAME
        defaultSocietyCropRegistrationShouldBeFound("cropName.in=" + DEFAULT_CROP_NAME + "," + UPDATED_CROP_NAME);

        // Get all the societyCropRegistrationList where cropName equals to UPDATED_CROP_NAME
        defaultSocietyCropRegistrationShouldNotBeFound("cropName.in=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropName is not null
        defaultSocietyCropRegistrationShouldBeFound("cropName.specified=true");

        // Get all the societyCropRegistrationList where cropName is null
        defaultSocietyCropRegistrationShouldNotBeFound("cropName.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropNameContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropName contains DEFAULT_CROP_NAME
        defaultSocietyCropRegistrationShouldBeFound("cropName.contains=" + DEFAULT_CROP_NAME);

        // Get all the societyCropRegistrationList where cropName contains UPDATED_CROP_NAME
        defaultSocietyCropRegistrationShouldNotBeFound("cropName.contains=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropNameNotContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropName does not contain DEFAULT_CROP_NAME
        defaultSocietyCropRegistrationShouldNotBeFound("cropName.doesNotContain=" + DEFAULT_CROP_NAME);

        // Get all the societyCropRegistrationList where cropName does not contain UPDATED_CROP_NAME
        defaultSocietyCropRegistrationShouldBeFound("cropName.doesNotContain=" + UPDATED_CROP_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByMonthDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where monthDuration equals to DEFAULT_MONTH_DURATION
        defaultSocietyCropRegistrationShouldBeFound("monthDuration.equals=" + DEFAULT_MONTH_DURATION);

        // Get all the societyCropRegistrationList where monthDuration equals to UPDATED_MONTH_DURATION
        defaultSocietyCropRegistrationShouldNotBeFound("monthDuration.equals=" + UPDATED_MONTH_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByMonthDurationIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where monthDuration in DEFAULT_MONTH_DURATION or UPDATED_MONTH_DURATION
        defaultSocietyCropRegistrationShouldBeFound("monthDuration.in=" + DEFAULT_MONTH_DURATION + "," + UPDATED_MONTH_DURATION);

        // Get all the societyCropRegistrationList where monthDuration equals to UPDATED_MONTH_DURATION
        defaultSocietyCropRegistrationShouldNotBeFound("monthDuration.in=" + UPDATED_MONTH_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByMonthDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where monthDuration is not null
        defaultSocietyCropRegistrationShouldBeFound("monthDuration.specified=true");

        // Get all the societyCropRegistrationList where monthDuration is null
        defaultSocietyCropRegistrationShouldNotBeFound("monthDuration.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByMonthDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where monthDuration is greater than or equal to DEFAULT_MONTH_DURATION
        defaultSocietyCropRegistrationShouldBeFound("monthDuration.greaterThanOrEqual=" + DEFAULT_MONTH_DURATION);

        // Get all the societyCropRegistrationList where monthDuration is greater than or equal to UPDATED_MONTH_DURATION
        defaultSocietyCropRegistrationShouldNotBeFound("monthDuration.greaterThanOrEqual=" + UPDATED_MONTH_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByMonthDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where monthDuration is less than or equal to DEFAULT_MONTH_DURATION
        defaultSocietyCropRegistrationShouldBeFound("monthDuration.lessThanOrEqual=" + DEFAULT_MONTH_DURATION);

        // Get all the societyCropRegistrationList where monthDuration is less than or equal to SMALLER_MONTH_DURATION
        defaultSocietyCropRegistrationShouldNotBeFound("monthDuration.lessThanOrEqual=" + SMALLER_MONTH_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByMonthDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where monthDuration is less than DEFAULT_MONTH_DURATION
        defaultSocietyCropRegistrationShouldNotBeFound("monthDuration.lessThan=" + DEFAULT_MONTH_DURATION);

        // Get all the societyCropRegistrationList where monthDuration is less than UPDATED_MONTH_DURATION
        defaultSocietyCropRegistrationShouldBeFound("monthDuration.lessThan=" + UPDATED_MONTH_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByMonthDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where monthDuration is greater than DEFAULT_MONTH_DURATION
        defaultSocietyCropRegistrationShouldNotBeFound("monthDuration.greaterThan=" + DEFAULT_MONTH_DURATION);

        // Get all the societyCropRegistrationList where monthDuration is greater than SMALLER_MONTH_DURATION
        defaultSocietyCropRegistrationShouldBeFound("monthDuration.greaterThan=" + SMALLER_MONTH_DURATION);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsBySeasonIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where season equals to DEFAULT_SEASON
        defaultSocietyCropRegistrationShouldBeFound("season.equals=" + DEFAULT_SEASON);

        // Get all the societyCropRegistrationList where season equals to UPDATED_SEASON
        defaultSocietyCropRegistrationShouldNotBeFound("season.equals=" + UPDATED_SEASON);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsBySeasonIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where season in DEFAULT_SEASON or UPDATED_SEASON
        defaultSocietyCropRegistrationShouldBeFound("season.in=" + DEFAULT_SEASON + "," + UPDATED_SEASON);

        // Get all the societyCropRegistrationList where season equals to UPDATED_SEASON
        defaultSocietyCropRegistrationShouldNotBeFound("season.in=" + UPDATED_SEASON);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsBySeasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where season is not null
        defaultSocietyCropRegistrationShouldBeFound("season.specified=true");

        // Get all the societyCropRegistrationList where season is null
        defaultSocietyCropRegistrationShouldNotBeFound("season.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropLimit equals to DEFAULT_CROP_LIMIT
        defaultSocietyCropRegistrationShouldBeFound("cropLimit.equals=" + DEFAULT_CROP_LIMIT);

        // Get all the societyCropRegistrationList where cropLimit equals to UPDATED_CROP_LIMIT
        defaultSocietyCropRegistrationShouldNotBeFound("cropLimit.equals=" + UPDATED_CROP_LIMIT);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropLimitIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropLimit in DEFAULT_CROP_LIMIT or UPDATED_CROP_LIMIT
        defaultSocietyCropRegistrationShouldBeFound("cropLimit.in=" + DEFAULT_CROP_LIMIT + "," + UPDATED_CROP_LIMIT);

        // Get all the societyCropRegistrationList where cropLimit equals to UPDATED_CROP_LIMIT
        defaultSocietyCropRegistrationShouldNotBeFound("cropLimit.in=" + UPDATED_CROP_LIMIT);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropLimit is not null
        defaultSocietyCropRegistrationShouldBeFound("cropLimit.specified=true");

        // Get all the societyCropRegistrationList where cropLimit is null
        defaultSocietyCropRegistrationShouldNotBeFound("cropLimit.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropLimitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropLimit is greater than or equal to DEFAULT_CROP_LIMIT
        defaultSocietyCropRegistrationShouldBeFound("cropLimit.greaterThanOrEqual=" + DEFAULT_CROP_LIMIT);

        // Get all the societyCropRegistrationList where cropLimit is greater than or equal to UPDATED_CROP_LIMIT
        defaultSocietyCropRegistrationShouldNotBeFound("cropLimit.greaterThanOrEqual=" + UPDATED_CROP_LIMIT);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropLimitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropLimit is less than or equal to DEFAULT_CROP_LIMIT
        defaultSocietyCropRegistrationShouldBeFound("cropLimit.lessThanOrEqual=" + DEFAULT_CROP_LIMIT);

        // Get all the societyCropRegistrationList where cropLimit is less than or equal to SMALLER_CROP_LIMIT
        defaultSocietyCropRegistrationShouldNotBeFound("cropLimit.lessThanOrEqual=" + SMALLER_CROP_LIMIT);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropLimitIsLessThanSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropLimit is less than DEFAULT_CROP_LIMIT
        defaultSocietyCropRegistrationShouldNotBeFound("cropLimit.lessThan=" + DEFAULT_CROP_LIMIT);

        // Get all the societyCropRegistrationList where cropLimit is less than UPDATED_CROP_LIMIT
        defaultSocietyCropRegistrationShouldBeFound("cropLimit.lessThan=" + UPDATED_CROP_LIMIT);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCropLimitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where cropLimit is greater than DEFAULT_CROP_LIMIT
        defaultSocietyCropRegistrationShouldNotBeFound("cropLimit.greaterThan=" + DEFAULT_CROP_LIMIT);

        // Get all the societyCropRegistrationList where cropLimit is greater than SMALLER_CROP_LIMIT
        defaultSocietyCropRegistrationShouldBeFound("cropLimit.greaterThan=" + SMALLER_CROP_LIMIT);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where year equals to DEFAULT_YEAR
        defaultSocietyCropRegistrationShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the societyCropRegistrationList where year equals to UPDATED_YEAR
        defaultSocietyCropRegistrationShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultSocietyCropRegistrationShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the societyCropRegistrationList where year equals to UPDATED_YEAR
        defaultSocietyCropRegistrationShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where year is not null
        defaultSocietyCropRegistrationShouldBeFound("year.specified=true");

        // Get all the societyCropRegistrationList where year is null
        defaultSocietyCropRegistrationShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where year is greater than or equal to DEFAULT_YEAR
        defaultSocietyCropRegistrationShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the societyCropRegistrationList where year is greater than or equal to UPDATED_YEAR
        defaultSocietyCropRegistrationShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where year is less than or equal to DEFAULT_YEAR
        defaultSocietyCropRegistrationShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the societyCropRegistrationList where year is less than or equal to SMALLER_YEAR
        defaultSocietyCropRegistrationShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where year is less than DEFAULT_YEAR
        defaultSocietyCropRegistrationShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the societyCropRegistrationList where year is less than UPDATED_YEAR
        defaultSocietyCropRegistrationShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where year is greater than DEFAULT_YEAR
        defaultSocietyCropRegistrationShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the societyCropRegistrationList where year is greater than SMALLER_YEAR
        defaultSocietyCropRegistrationShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSocietyCropRegistrationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the societyCropRegistrationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyCropRegistrationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSocietyCropRegistrationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the societyCropRegistrationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyCropRegistrationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where lastModified is not null
        defaultSocietyCropRegistrationShouldBeFound("lastModified.specified=true");

        // Get all the societyCropRegistrationList where lastModified is null
        defaultSocietyCropRegistrationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSocietyCropRegistrationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyCropRegistrationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyCropRegistrationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSocietyCropRegistrationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the societyCropRegistrationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyCropRegistrationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where lastModifiedBy is not null
        defaultSocietyCropRegistrationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the societyCropRegistrationList where lastModifiedBy is null
        defaultSocietyCropRegistrationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSocietyCropRegistrationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyCropRegistrationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSocietyCropRegistrationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSocietyCropRegistrationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyCropRegistrationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSocietyCropRegistrationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where createdBy equals to DEFAULT_CREATED_BY
        defaultSocietyCropRegistrationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the societyCropRegistrationList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyCropRegistrationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSocietyCropRegistrationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the societyCropRegistrationList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyCropRegistrationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where createdBy is not null
        defaultSocietyCropRegistrationShouldBeFound("createdBy.specified=true");

        // Get all the societyCropRegistrationList where createdBy is null
        defaultSocietyCropRegistrationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where createdBy contains DEFAULT_CREATED_BY
        defaultSocietyCropRegistrationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the societyCropRegistrationList where createdBy contains UPDATED_CREATED_BY
        defaultSocietyCropRegistrationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSocietyCropRegistrationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the societyCropRegistrationList where createdBy does not contain UPDATED_CREATED_BY
        defaultSocietyCropRegistrationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where createdOn equals to DEFAULT_CREATED_ON
        defaultSocietyCropRegistrationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the societyCropRegistrationList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyCropRegistrationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSocietyCropRegistrationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the societyCropRegistrationList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyCropRegistrationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where createdOn is not null
        defaultSocietyCropRegistrationShouldBeFound("createdOn.specified=true");

        // Get all the societyCropRegistrationList where createdOn is null
        defaultSocietyCropRegistrationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where isDeleted equals to DEFAULT_IS_DELETED
        defaultSocietyCropRegistrationShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the societyCropRegistrationList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyCropRegistrationShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultSocietyCropRegistrationShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the societyCropRegistrationList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyCropRegistrationShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where isDeleted is not null
        defaultSocietyCropRegistrationShouldBeFound("isDeleted.specified=true");

        // Get all the societyCropRegistrationList where isDeleted is null
        defaultSocietyCropRegistrationShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSocietyCropRegistrationShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyCropRegistrationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyCropRegistrationShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSocietyCropRegistrationShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the societyCropRegistrationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyCropRegistrationShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField1 is not null
        defaultSocietyCropRegistrationShouldBeFound("freeField1.specified=true");

        // Get all the societyCropRegistrationList where freeField1 is null
        defaultSocietyCropRegistrationShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultSocietyCropRegistrationShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyCropRegistrationList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultSocietyCropRegistrationShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultSocietyCropRegistrationShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyCropRegistrationList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultSocietyCropRegistrationShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSocietyCropRegistrationShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyCropRegistrationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyCropRegistrationShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSocietyCropRegistrationShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the societyCropRegistrationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyCropRegistrationShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField2 is not null
        defaultSocietyCropRegistrationShouldBeFound("freeField2.specified=true");

        // Get all the societyCropRegistrationList where freeField2 is null
        defaultSocietyCropRegistrationShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultSocietyCropRegistrationShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyCropRegistrationList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultSocietyCropRegistrationShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultSocietyCropRegistrationShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyCropRegistrationList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultSocietyCropRegistrationShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSocietyCropRegistrationShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyCropRegistrationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyCropRegistrationShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSocietyCropRegistrationShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the societyCropRegistrationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyCropRegistrationShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField3 is not null
        defaultSocietyCropRegistrationShouldBeFound("freeField3.specified=true");

        // Get all the societyCropRegistrationList where freeField3 is null
        defaultSocietyCropRegistrationShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultSocietyCropRegistrationShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyCropRegistrationList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultSocietyCropRegistrationShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        // Get all the societyCropRegistrationList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultSocietyCropRegistrationShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyCropRegistrationList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultSocietyCropRegistrationShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyCropRegistrationsBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        societyCropRegistration.setSociety(society);
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);
        Long societyId = society.getId();

        // Get all the societyCropRegistrationList where society equals to societyId
        defaultSocietyCropRegistrationShouldBeFound("societyId.equals=" + societyId);

        // Get all the societyCropRegistrationList where society equals to (societyId + 1)
        defaultSocietyCropRegistrationShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocietyCropRegistrationShouldBeFound(String filter) throws Exception {
        restSocietyCropRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyCropRegistration.getId().intValue())))
            .andExpect(jsonPath("$.[*].cropName").value(hasItem(DEFAULT_CROP_NAME)))
            .andExpect(jsonPath("$.[*].monthDuration").value(hasItem(DEFAULT_MONTH_DURATION)))
            .andExpect(jsonPath("$.[*].season").value(hasItem(DEFAULT_SEASON.toString())))
            .andExpect(jsonPath("$.[*].cropLimit").value(hasItem(DEFAULT_CROP_LIMIT.doubleValue())))
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
        restSocietyCropRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocietyCropRegistrationShouldNotBeFound(String filter) throws Exception {
        restSocietyCropRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocietyCropRegistrationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocietyCropRegistration() throws Exception {
        // Get the societyCropRegistration
        restSocietyCropRegistrationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocietyCropRegistration() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        int databaseSizeBeforeUpdate = societyCropRegistrationRepository.findAll().size();

        // Update the societyCropRegistration
        SocietyCropRegistration updatedSocietyCropRegistration = societyCropRegistrationRepository
            .findById(societyCropRegistration.getId())
            .get();
        // Disconnect from session so that the updates on updatedSocietyCropRegistration are not directly saved in db
        em.detach(updatedSocietyCropRegistration);
        updatedSocietyCropRegistration
            .cropName(UPDATED_CROP_NAME)
            .monthDuration(UPDATED_MONTH_DURATION)
            .season(UPDATED_SEASON)
            .cropLimit(UPDATED_CROP_LIMIT)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        SocietyCropRegistrationDTO societyCropRegistrationDTO = societyCropRegistrationMapper.toDto(updatedSocietyCropRegistration);

        restSocietyCropRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyCropRegistrationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyCropRegistrationDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeUpdate);
        SocietyCropRegistration testSocietyCropRegistration = societyCropRegistrationList.get(societyCropRegistrationList.size() - 1);
        assertThat(testSocietyCropRegistration.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testSocietyCropRegistration.getMonthDuration()).isEqualTo(UPDATED_MONTH_DURATION);
        assertThat(testSocietyCropRegistration.getSeason()).isEqualTo(UPDATED_SEASON);
        assertThat(testSocietyCropRegistration.getCropLimit()).isEqualTo(UPDATED_CROP_LIMIT);
        assertThat(testSocietyCropRegistration.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSocietyCropRegistration.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyCropRegistration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyCropRegistration.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyCropRegistration.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyCropRegistration.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyCropRegistration.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyCropRegistration.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyCropRegistration.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingSocietyCropRegistration() throws Exception {
        int databaseSizeBeforeUpdate = societyCropRegistrationRepository.findAll().size();
        societyCropRegistration.setId(count.incrementAndGet());

        // Create the SocietyCropRegistration
        SocietyCropRegistrationDTO societyCropRegistrationDTO = societyCropRegistrationMapper.toDto(societyCropRegistration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyCropRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyCropRegistrationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyCropRegistrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocietyCropRegistration() throws Exception {
        int databaseSizeBeforeUpdate = societyCropRegistrationRepository.findAll().size();
        societyCropRegistration.setId(count.incrementAndGet());

        // Create the SocietyCropRegistration
        SocietyCropRegistrationDTO societyCropRegistrationDTO = societyCropRegistrationMapper.toDto(societyCropRegistration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyCropRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyCropRegistrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocietyCropRegistration() throws Exception {
        int databaseSizeBeforeUpdate = societyCropRegistrationRepository.findAll().size();
        societyCropRegistration.setId(count.incrementAndGet());

        // Create the SocietyCropRegistration
        SocietyCropRegistrationDTO societyCropRegistrationDTO = societyCropRegistrationMapper.toDto(societyCropRegistration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyCropRegistrationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyCropRegistrationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocietyCropRegistrationWithPatch() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        int databaseSizeBeforeUpdate = societyCropRegistrationRepository.findAll().size();

        // Update the societyCropRegistration using partial update
        SocietyCropRegistration partialUpdatedSocietyCropRegistration = new SocietyCropRegistration();
        partialUpdatedSocietyCropRegistration.setId(societyCropRegistration.getId());

        partialUpdatedSocietyCropRegistration
            .cropName(UPDATED_CROP_NAME)
            .monthDuration(UPDATED_MONTH_DURATION)
            .season(UPDATED_SEASON)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyCropRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyCropRegistration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyCropRegistration))
            )
            .andExpect(status().isOk());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeUpdate);
        SocietyCropRegistration testSocietyCropRegistration = societyCropRegistrationList.get(societyCropRegistrationList.size() - 1);
        assertThat(testSocietyCropRegistration.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testSocietyCropRegistration.getMonthDuration()).isEqualTo(UPDATED_MONTH_DURATION);
        assertThat(testSocietyCropRegistration.getSeason()).isEqualTo(UPDATED_SEASON);
        assertThat(testSocietyCropRegistration.getCropLimit()).isEqualTo(DEFAULT_CROP_LIMIT);
        assertThat(testSocietyCropRegistration.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSocietyCropRegistration.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyCropRegistration.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyCropRegistration.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyCropRegistration.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyCropRegistration.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyCropRegistration.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyCropRegistration.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyCropRegistration.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateSocietyCropRegistrationWithPatch() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        int databaseSizeBeforeUpdate = societyCropRegistrationRepository.findAll().size();

        // Update the societyCropRegistration using partial update
        SocietyCropRegistration partialUpdatedSocietyCropRegistration = new SocietyCropRegistration();
        partialUpdatedSocietyCropRegistration.setId(societyCropRegistration.getId());

        partialUpdatedSocietyCropRegistration
            .cropName(UPDATED_CROP_NAME)
            .monthDuration(UPDATED_MONTH_DURATION)
            .season(UPDATED_SEASON)
            .cropLimit(UPDATED_CROP_LIMIT)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyCropRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyCropRegistration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyCropRegistration))
            )
            .andExpect(status().isOk());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeUpdate);
        SocietyCropRegistration testSocietyCropRegistration = societyCropRegistrationList.get(societyCropRegistrationList.size() - 1);
        assertThat(testSocietyCropRegistration.getCropName()).isEqualTo(UPDATED_CROP_NAME);
        assertThat(testSocietyCropRegistration.getMonthDuration()).isEqualTo(UPDATED_MONTH_DURATION);
        assertThat(testSocietyCropRegistration.getSeason()).isEqualTo(UPDATED_SEASON);
        assertThat(testSocietyCropRegistration.getCropLimit()).isEqualTo(UPDATED_CROP_LIMIT);
        assertThat(testSocietyCropRegistration.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSocietyCropRegistration.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyCropRegistration.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyCropRegistration.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyCropRegistration.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyCropRegistration.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyCropRegistration.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyCropRegistration.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyCropRegistration.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingSocietyCropRegistration() throws Exception {
        int databaseSizeBeforeUpdate = societyCropRegistrationRepository.findAll().size();
        societyCropRegistration.setId(count.incrementAndGet());

        // Create the SocietyCropRegistration
        SocietyCropRegistrationDTO societyCropRegistrationDTO = societyCropRegistrationMapper.toDto(societyCropRegistration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyCropRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societyCropRegistrationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyCropRegistrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocietyCropRegistration() throws Exception {
        int databaseSizeBeforeUpdate = societyCropRegistrationRepository.findAll().size();
        societyCropRegistration.setId(count.incrementAndGet());

        // Create the SocietyCropRegistration
        SocietyCropRegistrationDTO societyCropRegistrationDTO = societyCropRegistrationMapper.toDto(societyCropRegistration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyCropRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyCropRegistrationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocietyCropRegistration() throws Exception {
        int databaseSizeBeforeUpdate = societyCropRegistrationRepository.findAll().size();
        societyCropRegistration.setId(count.incrementAndGet());

        // Create the SocietyCropRegistration
        SocietyCropRegistrationDTO societyCropRegistrationDTO = societyCropRegistrationMapper.toDto(societyCropRegistration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyCropRegistrationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyCropRegistrationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyCropRegistration in the database
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocietyCropRegistration() throws Exception {
        // Initialize the database
        societyCropRegistrationRepository.saveAndFlush(societyCropRegistration);

        int databaseSizeBeforeDelete = societyCropRegistrationRepository.findAll().size();

        // Delete the societyCropRegistration
        restSocietyCropRegistrationMockMvc
            .perform(delete(ENTITY_API_URL_ID, societyCropRegistration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocietyCropRegistration> societyCropRegistrationList = societyCropRegistrationRepository.findAll();
        assertThat(societyCropRegistrationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

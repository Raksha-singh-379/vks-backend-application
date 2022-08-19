package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyNpaSetting;
import com.vgtech.vks.app.domain.enumeration.NpaClassification;
import com.vgtech.vks.app.repository.SocietyNpaSettingRepository;
import com.vgtech.vks.app.service.criteria.SocietyNpaSettingCriteria;
import com.vgtech.vks.app.service.dto.SocietyNpaSettingDTO;
import com.vgtech.vks.app.service.mapper.SocietyNpaSettingMapper;
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
 * Integration tests for the {@link SocietyNpaSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocietyNpaSettingResourceIT {

    private static final NpaClassification DEFAULT_NPA_CLASSIFICATION = NpaClassification.SUB_STANDARD_ASSESTS;
    private static final NpaClassification UPDATED_NPA_CLASSIFICATION = NpaClassification.DOUBTFUL_1;

    private static final Integer DEFAULT_DURATION_START = 1;
    private static final Integer UPDATED_DURATION_START = 2;
    private static final Integer SMALLER_DURATION_START = 1 - 1;

    private static final Integer DEFAULT_DURATION_END = 1;
    private static final Integer UPDATED_DURATION_END = 2;
    private static final Integer SMALLER_DURATION_END = 1 - 1;

    private static final Double DEFAULT_PROVISION = 1D;
    private static final Double UPDATED_PROVISION = 2D;
    private static final Double SMALLER_PROVISION = 1D - 1D;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final Integer DEFAULT_INTEREST_RATE = 1;
    private static final Integer UPDATED_INTEREST_RATE = 2;
    private static final Integer SMALLER_INTEREST_RATE = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/society-npa-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocietyNpaSettingRepository societyNpaSettingRepository;

    @Autowired
    private SocietyNpaSettingMapper societyNpaSettingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocietyNpaSettingMockMvc;

    private SocietyNpaSetting societyNpaSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyNpaSetting createEntity(EntityManager em) {
        SocietyNpaSetting societyNpaSetting = new SocietyNpaSetting()
            .npaClassification(DEFAULT_NPA_CLASSIFICATION)
            .durationStart(DEFAULT_DURATION_START)
            .durationEnd(DEFAULT_DURATION_END)
            .provision(DEFAULT_PROVISION)
            .year(DEFAULT_YEAR)
            .interestRate(DEFAULT_INTEREST_RATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return societyNpaSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyNpaSetting createUpdatedEntity(EntityManager em) {
        SocietyNpaSetting societyNpaSetting = new SocietyNpaSetting()
            .npaClassification(UPDATED_NPA_CLASSIFICATION)
            .durationStart(UPDATED_DURATION_START)
            .durationEnd(UPDATED_DURATION_END)
            .provision(UPDATED_PROVISION)
            .year(UPDATED_YEAR)
            .interestRate(UPDATED_INTEREST_RATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return societyNpaSetting;
    }

    @BeforeEach
    public void initTest() {
        societyNpaSetting = createEntity(em);
    }

    @Test
    @Transactional
    void createSocietyNpaSetting() throws Exception {
        int databaseSizeBeforeCreate = societyNpaSettingRepository.findAll().size();
        // Create the SocietyNpaSetting
        SocietyNpaSettingDTO societyNpaSettingDTO = societyNpaSettingMapper.toDto(societyNpaSetting);
        restSocietyNpaSettingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyNpaSettingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeCreate + 1);
        SocietyNpaSetting testSocietyNpaSetting = societyNpaSettingList.get(societyNpaSettingList.size() - 1);
        assertThat(testSocietyNpaSetting.getNpaClassification()).isEqualTo(DEFAULT_NPA_CLASSIFICATION);
        assertThat(testSocietyNpaSetting.getDurationStart()).isEqualTo(DEFAULT_DURATION_START);
        assertThat(testSocietyNpaSetting.getDurationEnd()).isEqualTo(DEFAULT_DURATION_END);
        assertThat(testSocietyNpaSetting.getProvision()).isEqualTo(DEFAULT_PROVISION);
        assertThat(testSocietyNpaSetting.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSocietyNpaSetting.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testSocietyNpaSetting.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyNpaSetting.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyNpaSetting.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyNpaSetting.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyNpaSetting.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSocietyNpaSetting.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyNpaSetting.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyNpaSetting.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createSocietyNpaSettingWithExistingId() throws Exception {
        // Create the SocietyNpaSetting with an existing ID
        societyNpaSetting.setId(1L);
        SocietyNpaSettingDTO societyNpaSettingDTO = societyNpaSettingMapper.toDto(societyNpaSetting);

        int databaseSizeBeforeCreate = societyNpaSettingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocietyNpaSettingMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyNpaSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettings() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList
        restSocietyNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyNpaSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].npaClassification").value(hasItem(DEFAULT_NPA_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].durationStart").value(hasItem(DEFAULT_DURATION_START)))
            .andExpect(jsonPath("$.[*].durationEnd").value(hasItem(DEFAULT_DURATION_END)))
            .andExpect(jsonPath("$.[*].provision").value(hasItem(DEFAULT_PROVISION.doubleValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE)))
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
    void getSocietyNpaSetting() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get the societyNpaSetting
        restSocietyNpaSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, societyNpaSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societyNpaSetting.getId().intValue()))
            .andExpect(jsonPath("$.npaClassification").value(DEFAULT_NPA_CLASSIFICATION.toString()))
            .andExpect(jsonPath("$.durationStart").value(DEFAULT_DURATION_START))
            .andExpect(jsonPath("$.durationEnd").value(DEFAULT_DURATION_END))
            .andExpect(jsonPath("$.provision").value(DEFAULT_PROVISION.doubleValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE))
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
    void getSocietyNpaSettingsByIdFiltering() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        Long id = societyNpaSetting.getId();

        defaultSocietyNpaSettingShouldBeFound("id.equals=" + id);
        defaultSocietyNpaSettingShouldNotBeFound("id.notEquals=" + id);

        defaultSocietyNpaSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocietyNpaSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultSocietyNpaSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocietyNpaSettingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByNpaClassificationIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where npaClassification equals to DEFAULT_NPA_CLASSIFICATION
        defaultSocietyNpaSettingShouldBeFound("npaClassification.equals=" + DEFAULT_NPA_CLASSIFICATION);

        // Get all the societyNpaSettingList where npaClassification equals to UPDATED_NPA_CLASSIFICATION
        defaultSocietyNpaSettingShouldNotBeFound("npaClassification.equals=" + UPDATED_NPA_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByNpaClassificationIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where npaClassification in DEFAULT_NPA_CLASSIFICATION or UPDATED_NPA_CLASSIFICATION
        defaultSocietyNpaSettingShouldBeFound("npaClassification.in=" + DEFAULT_NPA_CLASSIFICATION + "," + UPDATED_NPA_CLASSIFICATION);

        // Get all the societyNpaSettingList where npaClassification equals to UPDATED_NPA_CLASSIFICATION
        defaultSocietyNpaSettingShouldNotBeFound("npaClassification.in=" + UPDATED_NPA_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByNpaClassificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where npaClassification is not null
        defaultSocietyNpaSettingShouldBeFound("npaClassification.specified=true");

        // Get all the societyNpaSettingList where npaClassification is null
        defaultSocietyNpaSettingShouldNotBeFound("npaClassification.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationStartIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationStart equals to DEFAULT_DURATION_START
        defaultSocietyNpaSettingShouldBeFound("durationStart.equals=" + DEFAULT_DURATION_START);

        // Get all the societyNpaSettingList where durationStart equals to UPDATED_DURATION_START
        defaultSocietyNpaSettingShouldNotBeFound("durationStart.equals=" + UPDATED_DURATION_START);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationStartIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationStart in DEFAULT_DURATION_START or UPDATED_DURATION_START
        defaultSocietyNpaSettingShouldBeFound("durationStart.in=" + DEFAULT_DURATION_START + "," + UPDATED_DURATION_START);

        // Get all the societyNpaSettingList where durationStart equals to UPDATED_DURATION_START
        defaultSocietyNpaSettingShouldNotBeFound("durationStart.in=" + UPDATED_DURATION_START);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationStart is not null
        defaultSocietyNpaSettingShouldBeFound("durationStart.specified=true");

        // Get all the societyNpaSettingList where durationStart is null
        defaultSocietyNpaSettingShouldNotBeFound("durationStart.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationStart is greater than or equal to DEFAULT_DURATION_START
        defaultSocietyNpaSettingShouldBeFound("durationStart.greaterThanOrEqual=" + DEFAULT_DURATION_START);

        // Get all the societyNpaSettingList where durationStart is greater than or equal to UPDATED_DURATION_START
        defaultSocietyNpaSettingShouldNotBeFound("durationStart.greaterThanOrEqual=" + UPDATED_DURATION_START);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationStart is less than or equal to DEFAULT_DURATION_START
        defaultSocietyNpaSettingShouldBeFound("durationStart.lessThanOrEqual=" + DEFAULT_DURATION_START);

        // Get all the societyNpaSettingList where durationStart is less than or equal to SMALLER_DURATION_START
        defaultSocietyNpaSettingShouldNotBeFound("durationStart.lessThanOrEqual=" + SMALLER_DURATION_START);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationStartIsLessThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationStart is less than DEFAULT_DURATION_START
        defaultSocietyNpaSettingShouldNotBeFound("durationStart.lessThan=" + DEFAULT_DURATION_START);

        // Get all the societyNpaSettingList where durationStart is less than UPDATED_DURATION_START
        defaultSocietyNpaSettingShouldBeFound("durationStart.lessThan=" + UPDATED_DURATION_START);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationStart is greater than DEFAULT_DURATION_START
        defaultSocietyNpaSettingShouldNotBeFound("durationStart.greaterThan=" + DEFAULT_DURATION_START);

        // Get all the societyNpaSettingList where durationStart is greater than SMALLER_DURATION_START
        defaultSocietyNpaSettingShouldBeFound("durationStart.greaterThan=" + SMALLER_DURATION_START);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationEndIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationEnd equals to DEFAULT_DURATION_END
        defaultSocietyNpaSettingShouldBeFound("durationEnd.equals=" + DEFAULT_DURATION_END);

        // Get all the societyNpaSettingList where durationEnd equals to UPDATED_DURATION_END
        defaultSocietyNpaSettingShouldNotBeFound("durationEnd.equals=" + UPDATED_DURATION_END);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationEndIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationEnd in DEFAULT_DURATION_END or UPDATED_DURATION_END
        defaultSocietyNpaSettingShouldBeFound("durationEnd.in=" + DEFAULT_DURATION_END + "," + UPDATED_DURATION_END);

        // Get all the societyNpaSettingList where durationEnd equals to UPDATED_DURATION_END
        defaultSocietyNpaSettingShouldNotBeFound("durationEnd.in=" + UPDATED_DURATION_END);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationEnd is not null
        defaultSocietyNpaSettingShouldBeFound("durationEnd.specified=true");

        // Get all the societyNpaSettingList where durationEnd is null
        defaultSocietyNpaSettingShouldNotBeFound("durationEnd.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationEndIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationEnd is greater than or equal to DEFAULT_DURATION_END
        defaultSocietyNpaSettingShouldBeFound("durationEnd.greaterThanOrEqual=" + DEFAULT_DURATION_END);

        // Get all the societyNpaSettingList where durationEnd is greater than or equal to UPDATED_DURATION_END
        defaultSocietyNpaSettingShouldNotBeFound("durationEnd.greaterThanOrEqual=" + UPDATED_DURATION_END);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationEndIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationEnd is less than or equal to DEFAULT_DURATION_END
        defaultSocietyNpaSettingShouldBeFound("durationEnd.lessThanOrEqual=" + DEFAULT_DURATION_END);

        // Get all the societyNpaSettingList where durationEnd is less than or equal to SMALLER_DURATION_END
        defaultSocietyNpaSettingShouldNotBeFound("durationEnd.lessThanOrEqual=" + SMALLER_DURATION_END);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationEndIsLessThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationEnd is less than DEFAULT_DURATION_END
        defaultSocietyNpaSettingShouldNotBeFound("durationEnd.lessThan=" + DEFAULT_DURATION_END);

        // Get all the societyNpaSettingList where durationEnd is less than UPDATED_DURATION_END
        defaultSocietyNpaSettingShouldBeFound("durationEnd.lessThan=" + UPDATED_DURATION_END);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByDurationEndIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where durationEnd is greater than DEFAULT_DURATION_END
        defaultSocietyNpaSettingShouldNotBeFound("durationEnd.greaterThan=" + DEFAULT_DURATION_END);

        // Get all the societyNpaSettingList where durationEnd is greater than SMALLER_DURATION_END
        defaultSocietyNpaSettingShouldBeFound("durationEnd.greaterThan=" + SMALLER_DURATION_END);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByProvisionIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where provision equals to DEFAULT_PROVISION
        defaultSocietyNpaSettingShouldBeFound("provision.equals=" + DEFAULT_PROVISION);

        // Get all the societyNpaSettingList where provision equals to UPDATED_PROVISION
        defaultSocietyNpaSettingShouldNotBeFound("provision.equals=" + UPDATED_PROVISION);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByProvisionIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where provision in DEFAULT_PROVISION or UPDATED_PROVISION
        defaultSocietyNpaSettingShouldBeFound("provision.in=" + DEFAULT_PROVISION + "," + UPDATED_PROVISION);

        // Get all the societyNpaSettingList where provision equals to UPDATED_PROVISION
        defaultSocietyNpaSettingShouldNotBeFound("provision.in=" + UPDATED_PROVISION);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByProvisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where provision is not null
        defaultSocietyNpaSettingShouldBeFound("provision.specified=true");

        // Get all the societyNpaSettingList where provision is null
        defaultSocietyNpaSettingShouldNotBeFound("provision.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByProvisionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where provision is greater than or equal to DEFAULT_PROVISION
        defaultSocietyNpaSettingShouldBeFound("provision.greaterThanOrEqual=" + DEFAULT_PROVISION);

        // Get all the societyNpaSettingList where provision is greater than or equal to UPDATED_PROVISION
        defaultSocietyNpaSettingShouldNotBeFound("provision.greaterThanOrEqual=" + UPDATED_PROVISION);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByProvisionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where provision is less than or equal to DEFAULT_PROVISION
        defaultSocietyNpaSettingShouldBeFound("provision.lessThanOrEqual=" + DEFAULT_PROVISION);

        // Get all the societyNpaSettingList where provision is less than or equal to SMALLER_PROVISION
        defaultSocietyNpaSettingShouldNotBeFound("provision.lessThanOrEqual=" + SMALLER_PROVISION);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByProvisionIsLessThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where provision is less than DEFAULT_PROVISION
        defaultSocietyNpaSettingShouldNotBeFound("provision.lessThan=" + DEFAULT_PROVISION);

        // Get all the societyNpaSettingList where provision is less than UPDATED_PROVISION
        defaultSocietyNpaSettingShouldBeFound("provision.lessThan=" + UPDATED_PROVISION);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByProvisionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where provision is greater than DEFAULT_PROVISION
        defaultSocietyNpaSettingShouldNotBeFound("provision.greaterThan=" + DEFAULT_PROVISION);

        // Get all the societyNpaSettingList where provision is greater than SMALLER_PROVISION
        defaultSocietyNpaSettingShouldBeFound("provision.greaterThan=" + SMALLER_PROVISION);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where year equals to DEFAULT_YEAR
        defaultSocietyNpaSettingShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the societyNpaSettingList where year equals to UPDATED_YEAR
        defaultSocietyNpaSettingShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultSocietyNpaSettingShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the societyNpaSettingList where year equals to UPDATED_YEAR
        defaultSocietyNpaSettingShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where year is not null
        defaultSocietyNpaSettingShouldBeFound("year.specified=true");

        // Get all the societyNpaSettingList where year is null
        defaultSocietyNpaSettingShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where year is greater than or equal to DEFAULT_YEAR
        defaultSocietyNpaSettingShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the societyNpaSettingList where year is greater than or equal to UPDATED_YEAR
        defaultSocietyNpaSettingShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where year is less than or equal to DEFAULT_YEAR
        defaultSocietyNpaSettingShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the societyNpaSettingList where year is less than or equal to SMALLER_YEAR
        defaultSocietyNpaSettingShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where year is less than DEFAULT_YEAR
        defaultSocietyNpaSettingShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the societyNpaSettingList where year is less than UPDATED_YEAR
        defaultSocietyNpaSettingShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where year is greater than DEFAULT_YEAR
        defaultSocietyNpaSettingShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the societyNpaSettingList where year is greater than SMALLER_YEAR
        defaultSocietyNpaSettingShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where interestRate equals to DEFAULT_INTEREST_RATE
        defaultSocietyNpaSettingShouldBeFound("interestRate.equals=" + DEFAULT_INTEREST_RATE);

        // Get all the societyNpaSettingList where interestRate equals to UPDATED_INTEREST_RATE
        defaultSocietyNpaSettingShouldNotBeFound("interestRate.equals=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where interestRate in DEFAULT_INTEREST_RATE or UPDATED_INTEREST_RATE
        defaultSocietyNpaSettingShouldBeFound("interestRate.in=" + DEFAULT_INTEREST_RATE + "," + UPDATED_INTEREST_RATE);

        // Get all the societyNpaSettingList where interestRate equals to UPDATED_INTEREST_RATE
        defaultSocietyNpaSettingShouldNotBeFound("interestRate.in=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where interestRate is not null
        defaultSocietyNpaSettingShouldBeFound("interestRate.specified=true");

        // Get all the societyNpaSettingList where interestRate is null
        defaultSocietyNpaSettingShouldNotBeFound("interestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where interestRate is greater than or equal to DEFAULT_INTEREST_RATE
        defaultSocietyNpaSettingShouldBeFound("interestRate.greaterThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the societyNpaSettingList where interestRate is greater than or equal to UPDATED_INTEREST_RATE
        defaultSocietyNpaSettingShouldNotBeFound("interestRate.greaterThanOrEqual=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where interestRate is less than or equal to DEFAULT_INTEREST_RATE
        defaultSocietyNpaSettingShouldBeFound("interestRate.lessThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the societyNpaSettingList where interestRate is less than or equal to SMALLER_INTEREST_RATE
        defaultSocietyNpaSettingShouldNotBeFound("interestRate.lessThanOrEqual=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where interestRate is less than DEFAULT_INTEREST_RATE
        defaultSocietyNpaSettingShouldNotBeFound("interestRate.lessThan=" + DEFAULT_INTEREST_RATE);

        // Get all the societyNpaSettingList where interestRate is less than UPDATED_INTEREST_RATE
        defaultSocietyNpaSettingShouldBeFound("interestRate.lessThan=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where interestRate is greater than DEFAULT_INTEREST_RATE
        defaultSocietyNpaSettingShouldNotBeFound("interestRate.greaterThan=" + DEFAULT_INTEREST_RATE);

        // Get all the societyNpaSettingList where interestRate is greater than SMALLER_INTEREST_RATE
        defaultSocietyNpaSettingShouldBeFound("interestRate.greaterThan=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSocietyNpaSettingShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the societyNpaSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyNpaSettingShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSocietyNpaSettingShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the societyNpaSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyNpaSettingShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where lastModified is not null
        defaultSocietyNpaSettingShouldBeFound("lastModified.specified=true");

        // Get all the societyNpaSettingList where lastModified is null
        defaultSocietyNpaSettingShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSocietyNpaSettingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyNpaSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyNpaSettingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSocietyNpaSettingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the societyNpaSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyNpaSettingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where lastModifiedBy is not null
        defaultSocietyNpaSettingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the societyNpaSettingList where lastModifiedBy is null
        defaultSocietyNpaSettingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSocietyNpaSettingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyNpaSettingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSocietyNpaSettingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSocietyNpaSettingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyNpaSettingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSocietyNpaSettingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where createdBy equals to DEFAULT_CREATED_BY
        defaultSocietyNpaSettingShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the societyNpaSettingList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyNpaSettingShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSocietyNpaSettingShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the societyNpaSettingList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyNpaSettingShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where createdBy is not null
        defaultSocietyNpaSettingShouldBeFound("createdBy.specified=true");

        // Get all the societyNpaSettingList where createdBy is null
        defaultSocietyNpaSettingShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where createdBy contains DEFAULT_CREATED_BY
        defaultSocietyNpaSettingShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the societyNpaSettingList where createdBy contains UPDATED_CREATED_BY
        defaultSocietyNpaSettingShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSocietyNpaSettingShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the societyNpaSettingList where createdBy does not contain UPDATED_CREATED_BY
        defaultSocietyNpaSettingShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where createdOn equals to DEFAULT_CREATED_ON
        defaultSocietyNpaSettingShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the societyNpaSettingList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyNpaSettingShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSocietyNpaSettingShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the societyNpaSettingList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyNpaSettingShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where createdOn is not null
        defaultSocietyNpaSettingShouldBeFound("createdOn.specified=true");

        // Get all the societyNpaSettingList where createdOn is null
        defaultSocietyNpaSettingShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where isDeleted equals to DEFAULT_IS_DELETED
        defaultSocietyNpaSettingShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the societyNpaSettingList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyNpaSettingShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultSocietyNpaSettingShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the societyNpaSettingList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyNpaSettingShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where isDeleted is not null
        defaultSocietyNpaSettingShouldBeFound("isDeleted.specified=true");

        // Get all the societyNpaSettingList where isDeleted is null
        defaultSocietyNpaSettingShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSocietyNpaSettingShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyNpaSettingList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyNpaSettingShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSocietyNpaSettingShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the societyNpaSettingList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyNpaSettingShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField1 is not null
        defaultSocietyNpaSettingShouldBeFound("freeField1.specified=true");

        // Get all the societyNpaSettingList where freeField1 is null
        defaultSocietyNpaSettingShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultSocietyNpaSettingShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyNpaSettingList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultSocietyNpaSettingShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultSocietyNpaSettingShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyNpaSettingList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultSocietyNpaSettingShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSocietyNpaSettingShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyNpaSettingList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyNpaSettingShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSocietyNpaSettingShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the societyNpaSettingList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyNpaSettingShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField2 is not null
        defaultSocietyNpaSettingShouldBeFound("freeField2.specified=true");

        // Get all the societyNpaSettingList where freeField2 is null
        defaultSocietyNpaSettingShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultSocietyNpaSettingShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyNpaSettingList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultSocietyNpaSettingShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultSocietyNpaSettingShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyNpaSettingList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultSocietyNpaSettingShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSocietyNpaSettingShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyNpaSettingList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyNpaSettingShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSocietyNpaSettingShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the societyNpaSettingList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyNpaSettingShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField3 is not null
        defaultSocietyNpaSettingShouldBeFound("freeField3.specified=true");

        // Get all the societyNpaSettingList where freeField3 is null
        defaultSocietyNpaSettingShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultSocietyNpaSettingShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyNpaSettingList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultSocietyNpaSettingShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        // Get all the societyNpaSettingList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultSocietyNpaSettingShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyNpaSettingList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultSocietyNpaSettingShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyNpaSettingsBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            societyNpaSettingRepository.saveAndFlush(societyNpaSetting);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        societyNpaSetting.setSociety(society);
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);
        Long societyId = society.getId();

        // Get all the societyNpaSettingList where society equals to societyId
        defaultSocietyNpaSettingShouldBeFound("societyId.equals=" + societyId);

        // Get all the societyNpaSettingList where society equals to (societyId + 1)
        defaultSocietyNpaSettingShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocietyNpaSettingShouldBeFound(String filter) throws Exception {
        restSocietyNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyNpaSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].npaClassification").value(hasItem(DEFAULT_NPA_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].durationStart").value(hasItem(DEFAULT_DURATION_START)))
            .andExpect(jsonPath("$.[*].durationEnd").value(hasItem(DEFAULT_DURATION_END)))
            .andExpect(jsonPath("$.[*].provision").value(hasItem(DEFAULT_PROVISION.doubleValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restSocietyNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocietyNpaSettingShouldNotBeFound(String filter) throws Exception {
        restSocietyNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocietyNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocietyNpaSetting() throws Exception {
        // Get the societyNpaSetting
        restSocietyNpaSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocietyNpaSetting() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        int databaseSizeBeforeUpdate = societyNpaSettingRepository.findAll().size();

        // Update the societyNpaSetting
        SocietyNpaSetting updatedSocietyNpaSetting = societyNpaSettingRepository.findById(societyNpaSetting.getId()).get();
        // Disconnect from session so that the updates on updatedSocietyNpaSetting are not directly saved in db
        em.detach(updatedSocietyNpaSetting);
        updatedSocietyNpaSetting
            .npaClassification(UPDATED_NPA_CLASSIFICATION)
            .durationStart(UPDATED_DURATION_START)
            .durationEnd(UPDATED_DURATION_END)
            .provision(UPDATED_PROVISION)
            .year(UPDATED_YEAR)
            .interestRate(UPDATED_INTEREST_RATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        SocietyNpaSettingDTO societyNpaSettingDTO = societyNpaSettingMapper.toDto(updatedSocietyNpaSetting);

        restSocietyNpaSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyNpaSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyNpaSettingDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeUpdate);
        SocietyNpaSetting testSocietyNpaSetting = societyNpaSettingList.get(societyNpaSettingList.size() - 1);
        assertThat(testSocietyNpaSetting.getNpaClassification()).isEqualTo(UPDATED_NPA_CLASSIFICATION);
        assertThat(testSocietyNpaSetting.getDurationStart()).isEqualTo(UPDATED_DURATION_START);
        assertThat(testSocietyNpaSetting.getDurationEnd()).isEqualTo(UPDATED_DURATION_END);
        assertThat(testSocietyNpaSetting.getProvision()).isEqualTo(UPDATED_PROVISION);
        assertThat(testSocietyNpaSetting.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSocietyNpaSetting.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testSocietyNpaSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyNpaSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyNpaSetting.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyNpaSetting.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyNpaSetting.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyNpaSetting.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyNpaSetting.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyNpaSetting.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingSocietyNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = societyNpaSettingRepository.findAll().size();
        societyNpaSetting.setId(count.incrementAndGet());

        // Create the SocietyNpaSetting
        SocietyNpaSettingDTO societyNpaSettingDTO = societyNpaSettingMapper.toDto(societyNpaSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyNpaSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyNpaSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyNpaSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocietyNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = societyNpaSettingRepository.findAll().size();
        societyNpaSetting.setId(count.incrementAndGet());

        // Create the SocietyNpaSetting
        SocietyNpaSettingDTO societyNpaSettingDTO = societyNpaSettingMapper.toDto(societyNpaSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyNpaSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyNpaSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocietyNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = societyNpaSettingRepository.findAll().size();
        societyNpaSetting.setId(count.incrementAndGet());

        // Create the SocietyNpaSetting
        SocietyNpaSettingDTO societyNpaSettingDTO = societyNpaSettingMapper.toDto(societyNpaSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyNpaSettingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societyNpaSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocietyNpaSettingWithPatch() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        int databaseSizeBeforeUpdate = societyNpaSettingRepository.findAll().size();

        // Update the societyNpaSetting using partial update
        SocietyNpaSetting partialUpdatedSocietyNpaSetting = new SocietyNpaSetting();
        partialUpdatedSocietyNpaSetting.setId(societyNpaSetting.getId());

        partialUpdatedSocietyNpaSetting
            .durationStart(UPDATED_DURATION_START)
            .provision(UPDATED_PROVISION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyNpaSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyNpaSetting))
            )
            .andExpect(status().isOk());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeUpdate);
        SocietyNpaSetting testSocietyNpaSetting = societyNpaSettingList.get(societyNpaSettingList.size() - 1);
        assertThat(testSocietyNpaSetting.getNpaClassification()).isEqualTo(DEFAULT_NPA_CLASSIFICATION);
        assertThat(testSocietyNpaSetting.getDurationStart()).isEqualTo(UPDATED_DURATION_START);
        assertThat(testSocietyNpaSetting.getDurationEnd()).isEqualTo(DEFAULT_DURATION_END);
        assertThat(testSocietyNpaSetting.getProvision()).isEqualTo(UPDATED_PROVISION);
        assertThat(testSocietyNpaSetting.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testSocietyNpaSetting.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testSocietyNpaSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyNpaSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyNpaSetting.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyNpaSetting.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyNpaSetting.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyNpaSetting.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyNpaSetting.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyNpaSetting.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateSocietyNpaSettingWithPatch() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        int databaseSizeBeforeUpdate = societyNpaSettingRepository.findAll().size();

        // Update the societyNpaSetting using partial update
        SocietyNpaSetting partialUpdatedSocietyNpaSetting = new SocietyNpaSetting();
        partialUpdatedSocietyNpaSetting.setId(societyNpaSetting.getId());

        partialUpdatedSocietyNpaSetting
            .npaClassification(UPDATED_NPA_CLASSIFICATION)
            .durationStart(UPDATED_DURATION_START)
            .durationEnd(UPDATED_DURATION_END)
            .provision(UPDATED_PROVISION)
            .year(UPDATED_YEAR)
            .interestRate(UPDATED_INTEREST_RATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyNpaSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyNpaSetting))
            )
            .andExpect(status().isOk());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeUpdate);
        SocietyNpaSetting testSocietyNpaSetting = societyNpaSettingList.get(societyNpaSettingList.size() - 1);
        assertThat(testSocietyNpaSetting.getNpaClassification()).isEqualTo(UPDATED_NPA_CLASSIFICATION);
        assertThat(testSocietyNpaSetting.getDurationStart()).isEqualTo(UPDATED_DURATION_START);
        assertThat(testSocietyNpaSetting.getDurationEnd()).isEqualTo(UPDATED_DURATION_END);
        assertThat(testSocietyNpaSetting.getProvision()).isEqualTo(UPDATED_PROVISION);
        assertThat(testSocietyNpaSetting.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testSocietyNpaSetting.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testSocietyNpaSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyNpaSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyNpaSetting.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyNpaSetting.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyNpaSetting.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyNpaSetting.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyNpaSetting.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyNpaSetting.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingSocietyNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = societyNpaSettingRepository.findAll().size();
        societyNpaSetting.setId(count.incrementAndGet());

        // Create the SocietyNpaSetting
        SocietyNpaSettingDTO societyNpaSettingDTO = societyNpaSettingMapper.toDto(societyNpaSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societyNpaSettingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyNpaSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocietyNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = societyNpaSettingRepository.findAll().size();
        societyNpaSetting.setId(count.incrementAndGet());

        // Create the SocietyNpaSetting
        SocietyNpaSettingDTO societyNpaSettingDTO = societyNpaSettingMapper.toDto(societyNpaSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyNpaSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocietyNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = societyNpaSettingRepository.findAll().size();
        societyNpaSetting.setId(count.incrementAndGet());

        // Create the SocietyNpaSetting
        SocietyNpaSettingDTO societyNpaSettingDTO = societyNpaSettingMapper.toDto(societyNpaSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyNpaSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyNpaSetting in the database
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocietyNpaSetting() throws Exception {
        // Initialize the database
        societyNpaSettingRepository.saveAndFlush(societyNpaSetting);

        int databaseSizeBeforeDelete = societyNpaSettingRepository.findAll().size();

        // Delete the societyNpaSetting
        restSocietyNpaSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, societyNpaSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocietyNpaSetting> societyNpaSettingList = societyNpaSettingRepository.findAll();
        assertThat(societyNpaSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

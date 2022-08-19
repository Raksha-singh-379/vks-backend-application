package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.domain.MemberLandAssets;
import com.vgtech.vks.app.repository.MemberLandAssetsRepository;
import com.vgtech.vks.app.service.criteria.MemberLandAssetsCriteria;
import com.vgtech.vks.app.service.dto.MemberLandAssetsDTO;
import com.vgtech.vks.app.service.mapper.MemberLandAssetsMapper;
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
 * Integration tests for the {@link MemberLandAssetsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberLandAssetsResourceIT {

    private static final String DEFAULT_LAND_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LAND_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LAND_GAT_NO = "AAAAAAAAAA";
    private static final String UPDATED_LAND_GAT_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_LAND_AREA_IN_HECTOR = 1D;
    private static final Double UPDATED_LAND_AREA_IN_HECTOR = 2D;
    private static final Double SMALLER_LAND_AREA_IN_HECTOR = 1D - 1D;

    private static final String DEFAULT_JINDAGI_PATRAK_NO = "AAAAAAAAAA";
    private static final String UPDATED_JINDAGI_PATRAK_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_JINDAGI_AMOUNT = 1D;
    private static final Double UPDATED_JINDAGI_AMOUNT = 2D;
    private static final Double SMALLER_JINDAGI_AMOUNT = 1D - 1D;

    private static final String DEFAULT_ASSET_LAND_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_LAND_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE_OF_LAND = 1D;
    private static final Double UPDATED_VALUE_OF_LAND = 2D;
    private static final Double SMALLER_VALUE_OF_LAND = 1D - 1D;

    private static final Boolean DEFAULT_ASSIGNEE_OF_LAND = false;
    private static final Boolean UPDATED_ASSIGNEE_OF_LAND = true;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Long DEFAULT_M_L_LOAN_NO = 1L;
    private static final Long UPDATED_M_L_LOAN_NO = 2L;
    private static final Long SMALLER_M_L_LOAN_NO = 1L - 1L;

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

    private static final String ENTITY_API_URL = "/api/member-land-assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberLandAssetsRepository memberLandAssetsRepository;

    @Autowired
    private MemberLandAssetsMapper memberLandAssetsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberLandAssetsMockMvc;

    private MemberLandAssets memberLandAssets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberLandAssets createEntity(EntityManager em) {
        MemberLandAssets memberLandAssets = new MemberLandAssets()
            .landType(DEFAULT_LAND_TYPE)
            .landGatNo(DEFAULT_LAND_GAT_NO)
            .landAreaInHector(DEFAULT_LAND_AREA_IN_HECTOR)
            .jindagiPatrakNo(DEFAULT_JINDAGI_PATRAK_NO)
            .jindagiAmount(DEFAULT_JINDAGI_AMOUNT)
            .assetLandAddress(DEFAULT_ASSET_LAND_ADDRESS)
            .valueOfLand(DEFAULT_VALUE_OF_LAND)
            .assigneeOfLand(DEFAULT_ASSIGNEE_OF_LAND)
            .isDeleted(DEFAULT_IS_DELETED)
            .mLLoanNo(DEFAULT_M_L_LOAN_NO)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return memberLandAssets;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberLandAssets createUpdatedEntity(EntityManager em) {
        MemberLandAssets memberLandAssets = new MemberLandAssets()
            .landType(UPDATED_LAND_TYPE)
            .landGatNo(UPDATED_LAND_GAT_NO)
            .landAreaInHector(UPDATED_LAND_AREA_IN_HECTOR)
            .jindagiPatrakNo(UPDATED_JINDAGI_PATRAK_NO)
            .jindagiAmount(UPDATED_JINDAGI_AMOUNT)
            .assetLandAddress(UPDATED_ASSET_LAND_ADDRESS)
            .valueOfLand(UPDATED_VALUE_OF_LAND)
            .assigneeOfLand(UPDATED_ASSIGNEE_OF_LAND)
            .isDeleted(UPDATED_IS_DELETED)
            .mLLoanNo(UPDATED_M_L_LOAN_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return memberLandAssets;
    }

    @BeforeEach
    public void initTest() {
        memberLandAssets = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberLandAssets() throws Exception {
        int databaseSizeBeforeCreate = memberLandAssetsRepository.findAll().size();
        // Create the MemberLandAssets
        MemberLandAssetsDTO memberLandAssetsDTO = memberLandAssetsMapper.toDto(memberLandAssets);
        restMemberLandAssetsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberLandAssetsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeCreate + 1);
        MemberLandAssets testMemberLandAssets = memberLandAssetsList.get(memberLandAssetsList.size() - 1);
        assertThat(testMemberLandAssets.getLandType()).isEqualTo(DEFAULT_LAND_TYPE);
        assertThat(testMemberLandAssets.getLandGatNo()).isEqualTo(DEFAULT_LAND_GAT_NO);
        assertThat(testMemberLandAssets.getLandAreaInHector()).isEqualTo(DEFAULT_LAND_AREA_IN_HECTOR);
        assertThat(testMemberLandAssets.getJindagiPatrakNo()).isEqualTo(DEFAULT_JINDAGI_PATRAK_NO);
        assertThat(testMemberLandAssets.getJindagiAmount()).isEqualTo(DEFAULT_JINDAGI_AMOUNT);
        assertThat(testMemberLandAssets.getAssetLandAddress()).isEqualTo(DEFAULT_ASSET_LAND_ADDRESS);
        assertThat(testMemberLandAssets.getValueOfLand()).isEqualTo(DEFAULT_VALUE_OF_LAND);
        assertThat(testMemberLandAssets.getAssigneeOfLand()).isEqualTo(DEFAULT_ASSIGNEE_OF_LAND);
        assertThat(testMemberLandAssets.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMemberLandAssets.getmLLoanNo()).isEqualTo(DEFAULT_M_L_LOAN_NO);
        assertThat(testMemberLandAssets.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberLandAssets.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberLandAssets.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMemberLandAssets.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMemberLandAssets.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMemberLandAssets.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMemberLandAssets.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createMemberLandAssetsWithExistingId() throws Exception {
        // Create the MemberLandAssets with an existing ID
        memberLandAssets.setId(1L);
        MemberLandAssetsDTO memberLandAssetsDTO = memberLandAssetsMapper.toDto(memberLandAssets);

        int databaseSizeBeforeCreate = memberLandAssetsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberLandAssetsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberLandAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberLandAssets() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList
        restMemberLandAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberLandAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].landType").value(hasItem(DEFAULT_LAND_TYPE)))
            .andExpect(jsonPath("$.[*].landGatNo").value(hasItem(DEFAULT_LAND_GAT_NO)))
            .andExpect(jsonPath("$.[*].landAreaInHector").value(hasItem(DEFAULT_LAND_AREA_IN_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].jindagiPatrakNo").value(hasItem(DEFAULT_JINDAGI_PATRAK_NO)))
            .andExpect(jsonPath("$.[*].jindagiAmount").value(hasItem(DEFAULT_JINDAGI_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].assetLandAddress").value(hasItem(DEFAULT_ASSET_LAND_ADDRESS)))
            .andExpect(jsonPath("$.[*].valueOfLand").value(hasItem(DEFAULT_VALUE_OF_LAND.doubleValue())))
            .andExpect(jsonPath("$.[*].assigneeOfLand").value(hasItem(DEFAULT_ASSIGNEE_OF_LAND.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].mLLoanNo").value(hasItem(DEFAULT_M_L_LOAN_NO.intValue())))
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
    void getMemberLandAssets() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get the memberLandAssets
        restMemberLandAssetsMockMvc
            .perform(get(ENTITY_API_URL_ID, memberLandAssets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberLandAssets.getId().intValue()))
            .andExpect(jsonPath("$.landType").value(DEFAULT_LAND_TYPE))
            .andExpect(jsonPath("$.landGatNo").value(DEFAULT_LAND_GAT_NO))
            .andExpect(jsonPath("$.landAreaInHector").value(DEFAULT_LAND_AREA_IN_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.jindagiPatrakNo").value(DEFAULT_JINDAGI_PATRAK_NO))
            .andExpect(jsonPath("$.jindagiAmount").value(DEFAULT_JINDAGI_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.assetLandAddress").value(DEFAULT_ASSET_LAND_ADDRESS))
            .andExpect(jsonPath("$.valueOfLand").value(DEFAULT_VALUE_OF_LAND.doubleValue()))
            .andExpect(jsonPath("$.assigneeOfLand").value(DEFAULT_ASSIGNEE_OF_LAND.booleanValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.mLLoanNo").value(DEFAULT_M_L_LOAN_NO.intValue()))
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
    void getMemberLandAssetsByIdFiltering() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        Long id = memberLandAssets.getId();

        defaultMemberLandAssetsShouldBeFound("id.equals=" + id);
        defaultMemberLandAssetsShouldNotBeFound("id.notEquals=" + id);

        defaultMemberLandAssetsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberLandAssetsShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberLandAssetsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberLandAssetsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landType equals to DEFAULT_LAND_TYPE
        defaultMemberLandAssetsShouldBeFound("landType.equals=" + DEFAULT_LAND_TYPE);

        // Get all the memberLandAssetsList where landType equals to UPDATED_LAND_TYPE
        defaultMemberLandAssetsShouldNotBeFound("landType.equals=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandTypeIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landType in DEFAULT_LAND_TYPE or UPDATED_LAND_TYPE
        defaultMemberLandAssetsShouldBeFound("landType.in=" + DEFAULT_LAND_TYPE + "," + UPDATED_LAND_TYPE);

        // Get all the memberLandAssetsList where landType equals to UPDATED_LAND_TYPE
        defaultMemberLandAssetsShouldNotBeFound("landType.in=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landType is not null
        defaultMemberLandAssetsShouldBeFound("landType.specified=true");

        // Get all the memberLandAssetsList where landType is null
        defaultMemberLandAssetsShouldNotBeFound("landType.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandTypeContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landType contains DEFAULT_LAND_TYPE
        defaultMemberLandAssetsShouldBeFound("landType.contains=" + DEFAULT_LAND_TYPE);

        // Get all the memberLandAssetsList where landType contains UPDATED_LAND_TYPE
        defaultMemberLandAssetsShouldNotBeFound("landType.contains=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandTypeNotContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landType does not contain DEFAULT_LAND_TYPE
        defaultMemberLandAssetsShouldNotBeFound("landType.doesNotContain=" + DEFAULT_LAND_TYPE);

        // Get all the memberLandAssetsList where landType does not contain UPDATED_LAND_TYPE
        defaultMemberLandAssetsShouldBeFound("landType.doesNotContain=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandGatNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landGatNo equals to DEFAULT_LAND_GAT_NO
        defaultMemberLandAssetsShouldBeFound("landGatNo.equals=" + DEFAULT_LAND_GAT_NO);

        // Get all the memberLandAssetsList where landGatNo equals to UPDATED_LAND_GAT_NO
        defaultMemberLandAssetsShouldNotBeFound("landGatNo.equals=" + UPDATED_LAND_GAT_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandGatNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landGatNo in DEFAULT_LAND_GAT_NO or UPDATED_LAND_GAT_NO
        defaultMemberLandAssetsShouldBeFound("landGatNo.in=" + DEFAULT_LAND_GAT_NO + "," + UPDATED_LAND_GAT_NO);

        // Get all the memberLandAssetsList where landGatNo equals to UPDATED_LAND_GAT_NO
        defaultMemberLandAssetsShouldNotBeFound("landGatNo.in=" + UPDATED_LAND_GAT_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandGatNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landGatNo is not null
        defaultMemberLandAssetsShouldBeFound("landGatNo.specified=true");

        // Get all the memberLandAssetsList where landGatNo is null
        defaultMemberLandAssetsShouldNotBeFound("landGatNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandGatNoContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landGatNo contains DEFAULT_LAND_GAT_NO
        defaultMemberLandAssetsShouldBeFound("landGatNo.contains=" + DEFAULT_LAND_GAT_NO);

        // Get all the memberLandAssetsList where landGatNo contains UPDATED_LAND_GAT_NO
        defaultMemberLandAssetsShouldNotBeFound("landGatNo.contains=" + UPDATED_LAND_GAT_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandGatNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landGatNo does not contain DEFAULT_LAND_GAT_NO
        defaultMemberLandAssetsShouldNotBeFound("landGatNo.doesNotContain=" + DEFAULT_LAND_GAT_NO);

        // Get all the memberLandAssetsList where landGatNo does not contain UPDATED_LAND_GAT_NO
        defaultMemberLandAssetsShouldBeFound("landGatNo.doesNotContain=" + UPDATED_LAND_GAT_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandAreaInHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landAreaInHector equals to DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldBeFound("landAreaInHector.equals=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberLandAssetsList where landAreaInHector equals to UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldNotBeFound("landAreaInHector.equals=" + UPDATED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandAreaInHectorIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landAreaInHector in DEFAULT_LAND_AREA_IN_HECTOR or UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldBeFound("landAreaInHector.in=" + DEFAULT_LAND_AREA_IN_HECTOR + "," + UPDATED_LAND_AREA_IN_HECTOR);

        // Get all the memberLandAssetsList where landAreaInHector equals to UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldNotBeFound("landAreaInHector.in=" + UPDATED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandAreaInHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landAreaInHector is not null
        defaultMemberLandAssetsShouldBeFound("landAreaInHector.specified=true");

        // Get all the memberLandAssetsList where landAreaInHector is null
        defaultMemberLandAssetsShouldNotBeFound("landAreaInHector.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandAreaInHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landAreaInHector is greater than or equal to DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldBeFound("landAreaInHector.greaterThanOrEqual=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberLandAssetsList where landAreaInHector is greater than or equal to UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldNotBeFound("landAreaInHector.greaterThanOrEqual=" + UPDATED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandAreaInHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landAreaInHector is less than or equal to DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldBeFound("landAreaInHector.lessThanOrEqual=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberLandAssetsList where landAreaInHector is less than or equal to SMALLER_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldNotBeFound("landAreaInHector.lessThanOrEqual=" + SMALLER_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandAreaInHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landAreaInHector is less than DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldNotBeFound("landAreaInHector.lessThan=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberLandAssetsList where landAreaInHector is less than UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldBeFound("landAreaInHector.lessThan=" + UPDATED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLandAreaInHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where landAreaInHector is greater than DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldNotBeFound("landAreaInHector.greaterThan=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberLandAssetsList where landAreaInHector is greater than SMALLER_LAND_AREA_IN_HECTOR
        defaultMemberLandAssetsShouldBeFound("landAreaInHector.greaterThan=" + SMALLER_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiPatrakNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiPatrakNo equals to DEFAULT_JINDAGI_PATRAK_NO
        defaultMemberLandAssetsShouldBeFound("jindagiPatrakNo.equals=" + DEFAULT_JINDAGI_PATRAK_NO);

        // Get all the memberLandAssetsList where jindagiPatrakNo equals to UPDATED_JINDAGI_PATRAK_NO
        defaultMemberLandAssetsShouldNotBeFound("jindagiPatrakNo.equals=" + UPDATED_JINDAGI_PATRAK_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiPatrakNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiPatrakNo in DEFAULT_JINDAGI_PATRAK_NO or UPDATED_JINDAGI_PATRAK_NO
        defaultMemberLandAssetsShouldBeFound("jindagiPatrakNo.in=" + DEFAULT_JINDAGI_PATRAK_NO + "," + UPDATED_JINDAGI_PATRAK_NO);

        // Get all the memberLandAssetsList where jindagiPatrakNo equals to UPDATED_JINDAGI_PATRAK_NO
        defaultMemberLandAssetsShouldNotBeFound("jindagiPatrakNo.in=" + UPDATED_JINDAGI_PATRAK_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiPatrakNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiPatrakNo is not null
        defaultMemberLandAssetsShouldBeFound("jindagiPatrakNo.specified=true");

        // Get all the memberLandAssetsList where jindagiPatrakNo is null
        defaultMemberLandAssetsShouldNotBeFound("jindagiPatrakNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiPatrakNoContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiPatrakNo contains DEFAULT_JINDAGI_PATRAK_NO
        defaultMemberLandAssetsShouldBeFound("jindagiPatrakNo.contains=" + DEFAULT_JINDAGI_PATRAK_NO);

        // Get all the memberLandAssetsList where jindagiPatrakNo contains UPDATED_JINDAGI_PATRAK_NO
        defaultMemberLandAssetsShouldNotBeFound("jindagiPatrakNo.contains=" + UPDATED_JINDAGI_PATRAK_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiPatrakNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiPatrakNo does not contain DEFAULT_JINDAGI_PATRAK_NO
        defaultMemberLandAssetsShouldNotBeFound("jindagiPatrakNo.doesNotContain=" + DEFAULT_JINDAGI_PATRAK_NO);

        // Get all the memberLandAssetsList where jindagiPatrakNo does not contain UPDATED_JINDAGI_PATRAK_NO
        defaultMemberLandAssetsShouldBeFound("jindagiPatrakNo.doesNotContain=" + UPDATED_JINDAGI_PATRAK_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiAmount equals to DEFAULT_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldBeFound("jindagiAmount.equals=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberLandAssetsList where jindagiAmount equals to UPDATED_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldNotBeFound("jindagiAmount.equals=" + UPDATED_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiAmountIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiAmount in DEFAULT_JINDAGI_AMOUNT or UPDATED_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldBeFound("jindagiAmount.in=" + DEFAULT_JINDAGI_AMOUNT + "," + UPDATED_JINDAGI_AMOUNT);

        // Get all the memberLandAssetsList where jindagiAmount equals to UPDATED_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldNotBeFound("jindagiAmount.in=" + UPDATED_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiAmount is not null
        defaultMemberLandAssetsShouldBeFound("jindagiAmount.specified=true");

        // Get all the memberLandAssetsList where jindagiAmount is null
        defaultMemberLandAssetsShouldNotBeFound("jindagiAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiAmount is greater than or equal to DEFAULT_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldBeFound("jindagiAmount.greaterThanOrEqual=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberLandAssetsList where jindagiAmount is greater than or equal to UPDATED_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldNotBeFound("jindagiAmount.greaterThanOrEqual=" + UPDATED_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiAmount is less than or equal to DEFAULT_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldBeFound("jindagiAmount.lessThanOrEqual=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberLandAssetsList where jindagiAmount is less than or equal to SMALLER_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldNotBeFound("jindagiAmount.lessThanOrEqual=" + SMALLER_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiAmount is less than DEFAULT_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldNotBeFound("jindagiAmount.lessThan=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberLandAssetsList where jindagiAmount is less than UPDATED_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldBeFound("jindagiAmount.lessThan=" + UPDATED_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByJindagiAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where jindagiAmount is greater than DEFAULT_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldNotBeFound("jindagiAmount.greaterThan=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberLandAssetsList where jindagiAmount is greater than SMALLER_JINDAGI_AMOUNT
        defaultMemberLandAssetsShouldBeFound("jindagiAmount.greaterThan=" + SMALLER_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByAssetLandAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where assetLandAddress equals to DEFAULT_ASSET_LAND_ADDRESS
        defaultMemberLandAssetsShouldBeFound("assetLandAddress.equals=" + DEFAULT_ASSET_LAND_ADDRESS);

        // Get all the memberLandAssetsList where assetLandAddress equals to UPDATED_ASSET_LAND_ADDRESS
        defaultMemberLandAssetsShouldNotBeFound("assetLandAddress.equals=" + UPDATED_ASSET_LAND_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByAssetLandAddressIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where assetLandAddress in DEFAULT_ASSET_LAND_ADDRESS or UPDATED_ASSET_LAND_ADDRESS
        defaultMemberLandAssetsShouldBeFound("assetLandAddress.in=" + DEFAULT_ASSET_LAND_ADDRESS + "," + UPDATED_ASSET_LAND_ADDRESS);

        // Get all the memberLandAssetsList where assetLandAddress equals to UPDATED_ASSET_LAND_ADDRESS
        defaultMemberLandAssetsShouldNotBeFound("assetLandAddress.in=" + UPDATED_ASSET_LAND_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByAssetLandAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where assetLandAddress is not null
        defaultMemberLandAssetsShouldBeFound("assetLandAddress.specified=true");

        // Get all the memberLandAssetsList where assetLandAddress is null
        defaultMemberLandAssetsShouldNotBeFound("assetLandAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByAssetLandAddressContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where assetLandAddress contains DEFAULT_ASSET_LAND_ADDRESS
        defaultMemberLandAssetsShouldBeFound("assetLandAddress.contains=" + DEFAULT_ASSET_LAND_ADDRESS);

        // Get all the memberLandAssetsList where assetLandAddress contains UPDATED_ASSET_LAND_ADDRESS
        defaultMemberLandAssetsShouldNotBeFound("assetLandAddress.contains=" + UPDATED_ASSET_LAND_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByAssetLandAddressNotContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where assetLandAddress does not contain DEFAULT_ASSET_LAND_ADDRESS
        defaultMemberLandAssetsShouldNotBeFound("assetLandAddress.doesNotContain=" + DEFAULT_ASSET_LAND_ADDRESS);

        // Get all the memberLandAssetsList where assetLandAddress does not contain UPDATED_ASSET_LAND_ADDRESS
        defaultMemberLandAssetsShouldBeFound("assetLandAddress.doesNotContain=" + UPDATED_ASSET_LAND_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByValueOfLandIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where valueOfLand equals to DEFAULT_VALUE_OF_LAND
        defaultMemberLandAssetsShouldBeFound("valueOfLand.equals=" + DEFAULT_VALUE_OF_LAND);

        // Get all the memberLandAssetsList where valueOfLand equals to UPDATED_VALUE_OF_LAND
        defaultMemberLandAssetsShouldNotBeFound("valueOfLand.equals=" + UPDATED_VALUE_OF_LAND);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByValueOfLandIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where valueOfLand in DEFAULT_VALUE_OF_LAND or UPDATED_VALUE_OF_LAND
        defaultMemberLandAssetsShouldBeFound("valueOfLand.in=" + DEFAULT_VALUE_OF_LAND + "," + UPDATED_VALUE_OF_LAND);

        // Get all the memberLandAssetsList where valueOfLand equals to UPDATED_VALUE_OF_LAND
        defaultMemberLandAssetsShouldNotBeFound("valueOfLand.in=" + UPDATED_VALUE_OF_LAND);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByValueOfLandIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where valueOfLand is not null
        defaultMemberLandAssetsShouldBeFound("valueOfLand.specified=true");

        // Get all the memberLandAssetsList where valueOfLand is null
        defaultMemberLandAssetsShouldNotBeFound("valueOfLand.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByValueOfLandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where valueOfLand is greater than or equal to DEFAULT_VALUE_OF_LAND
        defaultMemberLandAssetsShouldBeFound("valueOfLand.greaterThanOrEqual=" + DEFAULT_VALUE_OF_LAND);

        // Get all the memberLandAssetsList where valueOfLand is greater than or equal to UPDATED_VALUE_OF_LAND
        defaultMemberLandAssetsShouldNotBeFound("valueOfLand.greaterThanOrEqual=" + UPDATED_VALUE_OF_LAND);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByValueOfLandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where valueOfLand is less than or equal to DEFAULT_VALUE_OF_LAND
        defaultMemberLandAssetsShouldBeFound("valueOfLand.lessThanOrEqual=" + DEFAULT_VALUE_OF_LAND);

        // Get all the memberLandAssetsList where valueOfLand is less than or equal to SMALLER_VALUE_OF_LAND
        defaultMemberLandAssetsShouldNotBeFound("valueOfLand.lessThanOrEqual=" + SMALLER_VALUE_OF_LAND);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByValueOfLandIsLessThanSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where valueOfLand is less than DEFAULT_VALUE_OF_LAND
        defaultMemberLandAssetsShouldNotBeFound("valueOfLand.lessThan=" + DEFAULT_VALUE_OF_LAND);

        // Get all the memberLandAssetsList where valueOfLand is less than UPDATED_VALUE_OF_LAND
        defaultMemberLandAssetsShouldBeFound("valueOfLand.lessThan=" + UPDATED_VALUE_OF_LAND);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByValueOfLandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where valueOfLand is greater than DEFAULT_VALUE_OF_LAND
        defaultMemberLandAssetsShouldNotBeFound("valueOfLand.greaterThan=" + DEFAULT_VALUE_OF_LAND);

        // Get all the memberLandAssetsList where valueOfLand is greater than SMALLER_VALUE_OF_LAND
        defaultMemberLandAssetsShouldBeFound("valueOfLand.greaterThan=" + SMALLER_VALUE_OF_LAND);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByAssigneeOfLandIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where assigneeOfLand equals to DEFAULT_ASSIGNEE_OF_LAND
        defaultMemberLandAssetsShouldBeFound("assigneeOfLand.equals=" + DEFAULT_ASSIGNEE_OF_LAND);

        // Get all the memberLandAssetsList where assigneeOfLand equals to UPDATED_ASSIGNEE_OF_LAND
        defaultMemberLandAssetsShouldNotBeFound("assigneeOfLand.equals=" + UPDATED_ASSIGNEE_OF_LAND);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByAssigneeOfLandIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where assigneeOfLand in DEFAULT_ASSIGNEE_OF_LAND or UPDATED_ASSIGNEE_OF_LAND
        defaultMemberLandAssetsShouldBeFound("assigneeOfLand.in=" + DEFAULT_ASSIGNEE_OF_LAND + "," + UPDATED_ASSIGNEE_OF_LAND);

        // Get all the memberLandAssetsList where assigneeOfLand equals to UPDATED_ASSIGNEE_OF_LAND
        defaultMemberLandAssetsShouldNotBeFound("assigneeOfLand.in=" + UPDATED_ASSIGNEE_OF_LAND);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByAssigneeOfLandIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where assigneeOfLand is not null
        defaultMemberLandAssetsShouldBeFound("assigneeOfLand.specified=true");

        // Get all the memberLandAssetsList where assigneeOfLand is null
        defaultMemberLandAssetsShouldNotBeFound("assigneeOfLand.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultMemberLandAssetsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the memberLandAssetsList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberLandAssetsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultMemberLandAssetsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the memberLandAssetsList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberLandAssetsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where isDeleted is not null
        defaultMemberLandAssetsShouldBeFound("isDeleted.specified=true");

        // Get all the memberLandAssetsList where isDeleted is null
        defaultMemberLandAssetsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsBymLLoanNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where mLLoanNo equals to DEFAULT_M_L_LOAN_NO
        defaultMemberLandAssetsShouldBeFound("mLLoanNo.equals=" + DEFAULT_M_L_LOAN_NO);

        // Get all the memberLandAssetsList where mLLoanNo equals to UPDATED_M_L_LOAN_NO
        defaultMemberLandAssetsShouldNotBeFound("mLLoanNo.equals=" + UPDATED_M_L_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsBymLLoanNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where mLLoanNo in DEFAULT_M_L_LOAN_NO or UPDATED_M_L_LOAN_NO
        defaultMemberLandAssetsShouldBeFound("mLLoanNo.in=" + DEFAULT_M_L_LOAN_NO + "," + UPDATED_M_L_LOAN_NO);

        // Get all the memberLandAssetsList where mLLoanNo equals to UPDATED_M_L_LOAN_NO
        defaultMemberLandAssetsShouldNotBeFound("mLLoanNo.in=" + UPDATED_M_L_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsBymLLoanNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where mLLoanNo is not null
        defaultMemberLandAssetsShouldBeFound("mLLoanNo.specified=true");

        // Get all the memberLandAssetsList where mLLoanNo is null
        defaultMemberLandAssetsShouldNotBeFound("mLLoanNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsBymLLoanNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where mLLoanNo is greater than or equal to DEFAULT_M_L_LOAN_NO
        defaultMemberLandAssetsShouldBeFound("mLLoanNo.greaterThanOrEqual=" + DEFAULT_M_L_LOAN_NO);

        // Get all the memberLandAssetsList where mLLoanNo is greater than or equal to UPDATED_M_L_LOAN_NO
        defaultMemberLandAssetsShouldNotBeFound("mLLoanNo.greaterThanOrEqual=" + UPDATED_M_L_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsBymLLoanNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where mLLoanNo is less than or equal to DEFAULT_M_L_LOAN_NO
        defaultMemberLandAssetsShouldBeFound("mLLoanNo.lessThanOrEqual=" + DEFAULT_M_L_LOAN_NO);

        // Get all the memberLandAssetsList where mLLoanNo is less than or equal to SMALLER_M_L_LOAN_NO
        defaultMemberLandAssetsShouldNotBeFound("mLLoanNo.lessThanOrEqual=" + SMALLER_M_L_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsBymLLoanNoIsLessThanSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where mLLoanNo is less than DEFAULT_M_L_LOAN_NO
        defaultMemberLandAssetsShouldNotBeFound("mLLoanNo.lessThan=" + DEFAULT_M_L_LOAN_NO);

        // Get all the memberLandAssetsList where mLLoanNo is less than UPDATED_M_L_LOAN_NO
        defaultMemberLandAssetsShouldBeFound("mLLoanNo.lessThan=" + UPDATED_M_L_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsBymLLoanNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where mLLoanNo is greater than DEFAULT_M_L_LOAN_NO
        defaultMemberLandAssetsShouldNotBeFound("mLLoanNo.greaterThan=" + DEFAULT_M_L_LOAN_NO);

        // Get all the memberLandAssetsList where mLLoanNo is greater than SMALLER_M_L_LOAN_NO
        defaultMemberLandAssetsShouldBeFound("mLLoanNo.greaterThan=" + SMALLER_M_L_LOAN_NO);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberLandAssetsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberLandAssetsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberLandAssetsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberLandAssetsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberLandAssetsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberLandAssetsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where lastModified is not null
        defaultMemberLandAssetsShouldBeFound("lastModified.specified=true");

        // Get all the memberLandAssetsList where lastModified is null
        defaultMemberLandAssetsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberLandAssetsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberLandAssetsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberLandAssetsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberLandAssetsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberLandAssetsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberLandAssetsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where lastModifiedBy is not null
        defaultMemberLandAssetsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberLandAssetsList where lastModifiedBy is null
        defaultMemberLandAssetsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberLandAssetsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberLandAssetsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberLandAssetsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberLandAssetsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberLandAssetsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberLandAssetsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where createdBy equals to DEFAULT_CREATED_BY
        defaultMemberLandAssetsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the memberLandAssetsList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberLandAssetsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultMemberLandAssetsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the memberLandAssetsList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberLandAssetsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where createdBy is not null
        defaultMemberLandAssetsShouldBeFound("createdBy.specified=true");

        // Get all the memberLandAssetsList where createdBy is null
        defaultMemberLandAssetsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where createdBy contains DEFAULT_CREATED_BY
        defaultMemberLandAssetsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the memberLandAssetsList where createdBy contains UPDATED_CREATED_BY
        defaultMemberLandAssetsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultMemberLandAssetsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the memberLandAssetsList where createdBy does not contain UPDATED_CREATED_BY
        defaultMemberLandAssetsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where createdOn equals to DEFAULT_CREATED_ON
        defaultMemberLandAssetsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the memberLandAssetsList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberLandAssetsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultMemberLandAssetsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the memberLandAssetsList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberLandAssetsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where createdOn is not null
        defaultMemberLandAssetsShouldBeFound("createdOn.specified=true");

        // Get all the memberLandAssetsList where createdOn is null
        defaultMemberLandAssetsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultMemberLandAssetsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberLandAssetsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberLandAssetsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultMemberLandAssetsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the memberLandAssetsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberLandAssetsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField1 is not null
        defaultMemberLandAssetsShouldBeFound("freeField1.specified=true");

        // Get all the memberLandAssetsList where freeField1 is null
        defaultMemberLandAssetsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultMemberLandAssetsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberLandAssetsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultMemberLandAssetsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultMemberLandAssetsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberLandAssetsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultMemberLandAssetsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultMemberLandAssetsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberLandAssetsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberLandAssetsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultMemberLandAssetsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the memberLandAssetsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberLandAssetsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField2 is not null
        defaultMemberLandAssetsShouldBeFound("freeField2.specified=true");

        // Get all the memberLandAssetsList where freeField2 is null
        defaultMemberLandAssetsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultMemberLandAssetsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberLandAssetsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultMemberLandAssetsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultMemberLandAssetsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberLandAssetsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultMemberLandAssetsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultMemberLandAssetsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberLandAssetsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberLandAssetsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultMemberLandAssetsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the memberLandAssetsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberLandAssetsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField3 is not null
        defaultMemberLandAssetsShouldBeFound("freeField3.specified=true");

        // Get all the memberLandAssetsList where freeField3 is null
        defaultMemberLandAssetsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultMemberLandAssetsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberLandAssetsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultMemberLandAssetsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        // Get all the memberLandAssetsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultMemberLandAssetsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberLandAssetsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultMemberLandAssetsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberLandAssetsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            memberLandAssetsRepository.saveAndFlush(memberLandAssets);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        memberLandAssets.setMember(member);
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);
        Long memberId = member.getId();

        // Get all the memberLandAssetsList where member equals to memberId
        defaultMemberLandAssetsShouldBeFound("memberId.equals=" + memberId);

        // Get all the memberLandAssetsList where member equals to (memberId + 1)
        defaultMemberLandAssetsShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberLandAssetsShouldBeFound(String filter) throws Exception {
        restMemberLandAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberLandAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].landType").value(hasItem(DEFAULT_LAND_TYPE)))
            .andExpect(jsonPath("$.[*].landGatNo").value(hasItem(DEFAULT_LAND_GAT_NO)))
            .andExpect(jsonPath("$.[*].landAreaInHector").value(hasItem(DEFAULT_LAND_AREA_IN_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].jindagiPatrakNo").value(hasItem(DEFAULT_JINDAGI_PATRAK_NO)))
            .andExpect(jsonPath("$.[*].jindagiAmount").value(hasItem(DEFAULT_JINDAGI_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].assetLandAddress").value(hasItem(DEFAULT_ASSET_LAND_ADDRESS)))
            .andExpect(jsonPath("$.[*].valueOfLand").value(hasItem(DEFAULT_VALUE_OF_LAND.doubleValue())))
            .andExpect(jsonPath("$.[*].assigneeOfLand").value(hasItem(DEFAULT_ASSIGNEE_OF_LAND.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].mLLoanNo").value(hasItem(DEFAULT_M_L_LOAN_NO.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restMemberLandAssetsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberLandAssetsShouldNotBeFound(String filter) throws Exception {
        restMemberLandAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberLandAssetsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMemberLandAssets() throws Exception {
        // Get the memberLandAssets
        restMemberLandAssetsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMemberLandAssets() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        int databaseSizeBeforeUpdate = memberLandAssetsRepository.findAll().size();

        // Update the memberLandAssets
        MemberLandAssets updatedMemberLandAssets = memberLandAssetsRepository.findById(memberLandAssets.getId()).get();
        // Disconnect from session so that the updates on updatedMemberLandAssets are not directly saved in db
        em.detach(updatedMemberLandAssets);
        updatedMemberLandAssets
            .landType(UPDATED_LAND_TYPE)
            .landGatNo(UPDATED_LAND_GAT_NO)
            .landAreaInHector(UPDATED_LAND_AREA_IN_HECTOR)
            .jindagiPatrakNo(UPDATED_JINDAGI_PATRAK_NO)
            .jindagiAmount(UPDATED_JINDAGI_AMOUNT)
            .assetLandAddress(UPDATED_ASSET_LAND_ADDRESS)
            .valueOfLand(UPDATED_VALUE_OF_LAND)
            .assigneeOfLand(UPDATED_ASSIGNEE_OF_LAND)
            .isDeleted(UPDATED_IS_DELETED)
            .mLLoanNo(UPDATED_M_L_LOAN_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        MemberLandAssetsDTO memberLandAssetsDTO = memberLandAssetsMapper.toDto(updatedMemberLandAssets);

        restMemberLandAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberLandAssetsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberLandAssetsDTO))
            )
            .andExpect(status().isOk());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeUpdate);
        MemberLandAssets testMemberLandAssets = memberLandAssetsList.get(memberLandAssetsList.size() - 1);
        assertThat(testMemberLandAssets.getLandType()).isEqualTo(UPDATED_LAND_TYPE);
        assertThat(testMemberLandAssets.getLandGatNo()).isEqualTo(UPDATED_LAND_GAT_NO);
        assertThat(testMemberLandAssets.getLandAreaInHector()).isEqualTo(UPDATED_LAND_AREA_IN_HECTOR);
        assertThat(testMemberLandAssets.getJindagiPatrakNo()).isEqualTo(UPDATED_JINDAGI_PATRAK_NO);
        assertThat(testMemberLandAssets.getJindagiAmount()).isEqualTo(UPDATED_JINDAGI_AMOUNT);
        assertThat(testMemberLandAssets.getAssetLandAddress()).isEqualTo(UPDATED_ASSET_LAND_ADDRESS);
        assertThat(testMemberLandAssets.getValueOfLand()).isEqualTo(UPDATED_VALUE_OF_LAND);
        assertThat(testMemberLandAssets.getAssigneeOfLand()).isEqualTo(UPDATED_ASSIGNEE_OF_LAND);
        assertThat(testMemberLandAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberLandAssets.getmLLoanNo()).isEqualTo(UPDATED_M_L_LOAN_NO);
        assertThat(testMemberLandAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberLandAssets.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberLandAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberLandAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberLandAssets.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberLandAssets.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberLandAssets.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingMemberLandAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberLandAssetsRepository.findAll().size();
        memberLandAssets.setId(count.incrementAndGet());

        // Create the MemberLandAssets
        MemberLandAssetsDTO memberLandAssetsDTO = memberLandAssetsMapper.toDto(memberLandAssets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberLandAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberLandAssetsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberLandAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberLandAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberLandAssetsRepository.findAll().size();
        memberLandAssets.setId(count.incrementAndGet());

        // Create the MemberLandAssets
        MemberLandAssetsDTO memberLandAssetsDTO = memberLandAssetsMapper.toDto(memberLandAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberLandAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberLandAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberLandAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberLandAssetsRepository.findAll().size();
        memberLandAssets.setId(count.incrementAndGet());

        // Create the MemberLandAssets
        MemberLandAssetsDTO memberLandAssetsDTO = memberLandAssetsMapper.toDto(memberLandAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberLandAssetsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberLandAssetsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberLandAssetsWithPatch() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        int databaseSizeBeforeUpdate = memberLandAssetsRepository.findAll().size();

        // Update the memberLandAssets using partial update
        MemberLandAssets partialUpdatedMemberLandAssets = new MemberLandAssets();
        partialUpdatedMemberLandAssets.setId(memberLandAssets.getId());

        partialUpdatedMemberLandAssets
            .jindagiPatrakNo(UPDATED_JINDAGI_PATRAK_NO)
            .jindagiAmount(UPDATED_JINDAGI_AMOUNT)
            .assetLandAddress(UPDATED_ASSET_LAND_ADDRESS)
            .assigneeOfLand(UPDATED_ASSIGNEE_OF_LAND)
            .mLLoanNo(UPDATED_M_L_LOAN_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdBy(UPDATED_CREATED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3);

        restMemberLandAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberLandAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberLandAssets))
            )
            .andExpect(status().isOk());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeUpdate);
        MemberLandAssets testMemberLandAssets = memberLandAssetsList.get(memberLandAssetsList.size() - 1);
        assertThat(testMemberLandAssets.getLandType()).isEqualTo(DEFAULT_LAND_TYPE);
        assertThat(testMemberLandAssets.getLandGatNo()).isEqualTo(DEFAULT_LAND_GAT_NO);
        assertThat(testMemberLandAssets.getLandAreaInHector()).isEqualTo(DEFAULT_LAND_AREA_IN_HECTOR);
        assertThat(testMemberLandAssets.getJindagiPatrakNo()).isEqualTo(UPDATED_JINDAGI_PATRAK_NO);
        assertThat(testMemberLandAssets.getJindagiAmount()).isEqualTo(UPDATED_JINDAGI_AMOUNT);
        assertThat(testMemberLandAssets.getAssetLandAddress()).isEqualTo(UPDATED_ASSET_LAND_ADDRESS);
        assertThat(testMemberLandAssets.getValueOfLand()).isEqualTo(DEFAULT_VALUE_OF_LAND);
        assertThat(testMemberLandAssets.getAssigneeOfLand()).isEqualTo(UPDATED_ASSIGNEE_OF_LAND);
        assertThat(testMemberLandAssets.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMemberLandAssets.getmLLoanNo()).isEqualTo(UPDATED_M_L_LOAN_NO);
        assertThat(testMemberLandAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberLandAssets.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberLandAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberLandAssets.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMemberLandAssets.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberLandAssets.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMemberLandAssets.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateMemberLandAssetsWithPatch() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        int databaseSizeBeforeUpdate = memberLandAssetsRepository.findAll().size();

        // Update the memberLandAssets using partial update
        MemberLandAssets partialUpdatedMemberLandAssets = new MemberLandAssets();
        partialUpdatedMemberLandAssets.setId(memberLandAssets.getId());

        partialUpdatedMemberLandAssets
            .landType(UPDATED_LAND_TYPE)
            .landGatNo(UPDATED_LAND_GAT_NO)
            .landAreaInHector(UPDATED_LAND_AREA_IN_HECTOR)
            .jindagiPatrakNo(UPDATED_JINDAGI_PATRAK_NO)
            .jindagiAmount(UPDATED_JINDAGI_AMOUNT)
            .assetLandAddress(UPDATED_ASSET_LAND_ADDRESS)
            .valueOfLand(UPDATED_VALUE_OF_LAND)
            .assigneeOfLand(UPDATED_ASSIGNEE_OF_LAND)
            .isDeleted(UPDATED_IS_DELETED)
            .mLLoanNo(UPDATED_M_L_LOAN_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restMemberLandAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberLandAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberLandAssets))
            )
            .andExpect(status().isOk());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeUpdate);
        MemberLandAssets testMemberLandAssets = memberLandAssetsList.get(memberLandAssetsList.size() - 1);
        assertThat(testMemberLandAssets.getLandType()).isEqualTo(UPDATED_LAND_TYPE);
        assertThat(testMemberLandAssets.getLandGatNo()).isEqualTo(UPDATED_LAND_GAT_NO);
        assertThat(testMemberLandAssets.getLandAreaInHector()).isEqualTo(UPDATED_LAND_AREA_IN_HECTOR);
        assertThat(testMemberLandAssets.getJindagiPatrakNo()).isEqualTo(UPDATED_JINDAGI_PATRAK_NO);
        assertThat(testMemberLandAssets.getJindagiAmount()).isEqualTo(UPDATED_JINDAGI_AMOUNT);
        assertThat(testMemberLandAssets.getAssetLandAddress()).isEqualTo(UPDATED_ASSET_LAND_ADDRESS);
        assertThat(testMemberLandAssets.getValueOfLand()).isEqualTo(UPDATED_VALUE_OF_LAND);
        assertThat(testMemberLandAssets.getAssigneeOfLand()).isEqualTo(UPDATED_ASSIGNEE_OF_LAND);
        assertThat(testMemberLandAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberLandAssets.getmLLoanNo()).isEqualTo(UPDATED_M_L_LOAN_NO);
        assertThat(testMemberLandAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberLandAssets.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberLandAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberLandAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberLandAssets.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberLandAssets.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberLandAssets.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingMemberLandAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberLandAssetsRepository.findAll().size();
        memberLandAssets.setId(count.incrementAndGet());

        // Create the MemberLandAssets
        MemberLandAssetsDTO memberLandAssetsDTO = memberLandAssetsMapper.toDto(memberLandAssets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberLandAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberLandAssetsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberLandAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberLandAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberLandAssetsRepository.findAll().size();
        memberLandAssets.setId(count.incrementAndGet());

        // Create the MemberLandAssets
        MemberLandAssetsDTO memberLandAssetsDTO = memberLandAssetsMapper.toDto(memberLandAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberLandAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberLandAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberLandAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberLandAssetsRepository.findAll().size();
        memberLandAssets.setId(count.incrementAndGet());

        // Create the MemberLandAssets
        MemberLandAssetsDTO memberLandAssetsDTO = memberLandAssetsMapper.toDto(memberLandAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberLandAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberLandAssetsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberLandAssets in the database
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberLandAssets() throws Exception {
        // Initialize the database
        memberLandAssetsRepository.saveAndFlush(memberLandAssets);

        int databaseSizeBeforeDelete = memberLandAssetsRepository.findAll().size();

        // Delete the memberLandAssets
        restMemberLandAssetsMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberLandAssets.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberLandAssets> memberLandAssetsList = memberLandAssetsRepository.findAll();
        assertThat(memberLandAssetsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

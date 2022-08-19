package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.domain.SocietyPrerequisite;
import com.vgtech.vks.app.domain.enumeration.LoanType;
import com.vgtech.vks.app.repository.SocietyPrerequisiteRepository;
import com.vgtech.vks.app.service.criteria.SocietyPrerequisiteCriteria;
import com.vgtech.vks.app.service.dto.SocietyPrerequisiteDTO;
import com.vgtech.vks.app.service.mapper.SocietyPrerequisiteMapper;
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
 * Integration tests for the {@link SocietyPrerequisiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocietyPrerequisiteResourceIT {

    private static final String DEFAULT_DOC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DOC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final LoanType DEFAULT_LOAN_TYPE = LoanType.SHORT_TERM;
    private static final LoanType UPDATED_LOAN_TYPE = LoanType.MID_TERM;

    private static final String DEFAULT_MANDATORY = "AAAAAAAAAA";
    private static final String UPDATED_MANDATORY = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/society-prerequisites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocietyPrerequisiteRepository societyPrerequisiteRepository;

    @Autowired
    private SocietyPrerequisiteMapper societyPrerequisiteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocietyPrerequisiteMockMvc;

    private SocietyPrerequisite societyPrerequisite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyPrerequisite createEntity(EntityManager em) {
        SocietyPrerequisite societyPrerequisite = new SocietyPrerequisite()
            .docType(DEFAULT_DOC_TYPE)
            .documentDesc(DEFAULT_DOCUMENT_DESC)
            .documentName(DEFAULT_DOCUMENT_NAME)
            .loanType(DEFAULT_LOAN_TYPE)
            .mandatory(DEFAULT_MANDATORY)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return societyPrerequisite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocietyPrerequisite createUpdatedEntity(EntityManager em) {
        SocietyPrerequisite societyPrerequisite = new SocietyPrerequisite()
            .docType(UPDATED_DOC_TYPE)
            .documentDesc(UPDATED_DOCUMENT_DESC)
            .documentName(UPDATED_DOCUMENT_NAME)
            .loanType(UPDATED_LOAN_TYPE)
            .mandatory(UPDATED_MANDATORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return societyPrerequisite;
    }

    @BeforeEach
    public void initTest() {
        societyPrerequisite = createEntity(em);
    }

    @Test
    @Transactional
    void createSocietyPrerequisite() throws Exception {
        int databaseSizeBeforeCreate = societyPrerequisiteRepository.findAll().size();
        // Create the SocietyPrerequisite
        SocietyPrerequisiteDTO societyPrerequisiteDTO = societyPrerequisiteMapper.toDto(societyPrerequisite);
        restSocietyPrerequisiteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyPrerequisiteDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeCreate + 1);
        SocietyPrerequisite testSocietyPrerequisite = societyPrerequisiteList.get(societyPrerequisiteList.size() - 1);
        assertThat(testSocietyPrerequisite.getDocType()).isEqualTo(DEFAULT_DOC_TYPE);
        assertThat(testSocietyPrerequisite.getDocumentDesc()).isEqualTo(DEFAULT_DOCUMENT_DESC);
        assertThat(testSocietyPrerequisite.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testSocietyPrerequisite.getLoanType()).isEqualTo(DEFAULT_LOAN_TYPE);
        assertThat(testSocietyPrerequisite.getMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testSocietyPrerequisite.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyPrerequisite.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyPrerequisite.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyPrerequisite.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyPrerequisite.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSocietyPrerequisite.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSocietyPrerequisite.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyPrerequisite.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createSocietyPrerequisiteWithExistingId() throws Exception {
        // Create the SocietyPrerequisite with an existing ID
        societyPrerequisite.setId(1L);
        SocietyPrerequisiteDTO societyPrerequisiteDTO = societyPrerequisiteMapper.toDto(societyPrerequisite);

        int databaseSizeBeforeCreate = societyPrerequisiteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocietyPrerequisiteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyPrerequisiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisites() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList
        restSocietyPrerequisiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyPrerequisite.getId().intValue())))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].documentDesc").value(hasItem(DEFAULT_DOCUMENT_DESC)))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].loanType").value(hasItem(DEFAULT_LOAN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY)))
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
    void getSocietyPrerequisite() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get the societyPrerequisite
        restSocietyPrerequisiteMockMvc
            .perform(get(ENTITY_API_URL_ID, societyPrerequisite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societyPrerequisite.getId().intValue()))
            .andExpect(jsonPath("$.docType").value(DEFAULT_DOC_TYPE))
            .andExpect(jsonPath("$.documentDesc").value(DEFAULT_DOCUMENT_DESC))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME))
            .andExpect(jsonPath("$.loanType").value(DEFAULT_LOAN_TYPE.toString()))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY))
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
    void getSocietyPrerequisitesByIdFiltering() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        Long id = societyPrerequisite.getId();

        defaultSocietyPrerequisiteShouldBeFound("id.equals=" + id);
        defaultSocietyPrerequisiteShouldNotBeFound("id.notEquals=" + id);

        defaultSocietyPrerequisiteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSocietyPrerequisiteShouldNotBeFound("id.greaterThan=" + id);

        defaultSocietyPrerequisiteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSocietyPrerequisiteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where docType equals to DEFAULT_DOC_TYPE
        defaultSocietyPrerequisiteShouldBeFound("docType.equals=" + DEFAULT_DOC_TYPE);

        // Get all the societyPrerequisiteList where docType equals to UPDATED_DOC_TYPE
        defaultSocietyPrerequisiteShouldNotBeFound("docType.equals=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocTypeIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where docType in DEFAULT_DOC_TYPE or UPDATED_DOC_TYPE
        defaultSocietyPrerequisiteShouldBeFound("docType.in=" + DEFAULT_DOC_TYPE + "," + UPDATED_DOC_TYPE);

        // Get all the societyPrerequisiteList where docType equals to UPDATED_DOC_TYPE
        defaultSocietyPrerequisiteShouldNotBeFound("docType.in=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where docType is not null
        defaultSocietyPrerequisiteShouldBeFound("docType.specified=true");

        // Get all the societyPrerequisiteList where docType is null
        defaultSocietyPrerequisiteShouldNotBeFound("docType.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocTypeContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where docType contains DEFAULT_DOC_TYPE
        defaultSocietyPrerequisiteShouldBeFound("docType.contains=" + DEFAULT_DOC_TYPE);

        // Get all the societyPrerequisiteList where docType contains UPDATED_DOC_TYPE
        defaultSocietyPrerequisiteShouldNotBeFound("docType.contains=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocTypeNotContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where docType does not contain DEFAULT_DOC_TYPE
        defaultSocietyPrerequisiteShouldNotBeFound("docType.doesNotContain=" + DEFAULT_DOC_TYPE);

        // Get all the societyPrerequisiteList where docType does not contain UPDATED_DOC_TYPE
        defaultSocietyPrerequisiteShouldBeFound("docType.doesNotContain=" + UPDATED_DOC_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentDescIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentDesc equals to DEFAULT_DOCUMENT_DESC
        defaultSocietyPrerequisiteShouldBeFound("documentDesc.equals=" + DEFAULT_DOCUMENT_DESC);

        // Get all the societyPrerequisiteList where documentDesc equals to UPDATED_DOCUMENT_DESC
        defaultSocietyPrerequisiteShouldNotBeFound("documentDesc.equals=" + UPDATED_DOCUMENT_DESC);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentDescIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentDesc in DEFAULT_DOCUMENT_DESC or UPDATED_DOCUMENT_DESC
        defaultSocietyPrerequisiteShouldBeFound("documentDesc.in=" + DEFAULT_DOCUMENT_DESC + "," + UPDATED_DOCUMENT_DESC);

        // Get all the societyPrerequisiteList where documentDesc equals to UPDATED_DOCUMENT_DESC
        defaultSocietyPrerequisiteShouldNotBeFound("documentDesc.in=" + UPDATED_DOCUMENT_DESC);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentDesc is not null
        defaultSocietyPrerequisiteShouldBeFound("documentDesc.specified=true");

        // Get all the societyPrerequisiteList where documentDesc is null
        defaultSocietyPrerequisiteShouldNotBeFound("documentDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentDescContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentDesc contains DEFAULT_DOCUMENT_DESC
        defaultSocietyPrerequisiteShouldBeFound("documentDesc.contains=" + DEFAULT_DOCUMENT_DESC);

        // Get all the societyPrerequisiteList where documentDesc contains UPDATED_DOCUMENT_DESC
        defaultSocietyPrerequisiteShouldNotBeFound("documentDesc.contains=" + UPDATED_DOCUMENT_DESC);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentDescNotContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentDesc does not contain DEFAULT_DOCUMENT_DESC
        defaultSocietyPrerequisiteShouldNotBeFound("documentDesc.doesNotContain=" + DEFAULT_DOCUMENT_DESC);

        // Get all the societyPrerequisiteList where documentDesc does not contain UPDATED_DOCUMENT_DESC
        defaultSocietyPrerequisiteShouldBeFound("documentDesc.doesNotContain=" + UPDATED_DOCUMENT_DESC);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentName equals to DEFAULT_DOCUMENT_NAME
        defaultSocietyPrerequisiteShouldBeFound("documentName.equals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the societyPrerequisiteList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultSocietyPrerequisiteShouldNotBeFound("documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentName in DEFAULT_DOCUMENT_NAME or UPDATED_DOCUMENT_NAME
        defaultSocietyPrerequisiteShouldBeFound("documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME);

        // Get all the societyPrerequisiteList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultSocietyPrerequisiteShouldNotBeFound("documentName.in=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentName is not null
        defaultSocietyPrerequisiteShouldBeFound("documentName.specified=true");

        // Get all the societyPrerequisiteList where documentName is null
        defaultSocietyPrerequisiteShouldNotBeFound("documentName.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentNameContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentName contains DEFAULT_DOCUMENT_NAME
        defaultSocietyPrerequisiteShouldBeFound("documentName.contains=" + DEFAULT_DOCUMENT_NAME);

        // Get all the societyPrerequisiteList where documentName contains UPDATED_DOCUMENT_NAME
        defaultSocietyPrerequisiteShouldNotBeFound("documentName.contains=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByDocumentNameNotContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where documentName does not contain DEFAULT_DOCUMENT_NAME
        defaultSocietyPrerequisiteShouldNotBeFound("documentName.doesNotContain=" + DEFAULT_DOCUMENT_NAME);

        // Get all the societyPrerequisiteList where documentName does not contain UPDATED_DOCUMENT_NAME
        defaultSocietyPrerequisiteShouldBeFound("documentName.doesNotContain=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLoanTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where loanType equals to DEFAULT_LOAN_TYPE
        defaultSocietyPrerequisiteShouldBeFound("loanType.equals=" + DEFAULT_LOAN_TYPE);

        // Get all the societyPrerequisiteList where loanType equals to UPDATED_LOAN_TYPE
        defaultSocietyPrerequisiteShouldNotBeFound("loanType.equals=" + UPDATED_LOAN_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLoanTypeIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where loanType in DEFAULT_LOAN_TYPE or UPDATED_LOAN_TYPE
        defaultSocietyPrerequisiteShouldBeFound("loanType.in=" + DEFAULT_LOAN_TYPE + "," + UPDATED_LOAN_TYPE);

        // Get all the societyPrerequisiteList where loanType equals to UPDATED_LOAN_TYPE
        defaultSocietyPrerequisiteShouldNotBeFound("loanType.in=" + UPDATED_LOAN_TYPE);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLoanTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where loanType is not null
        defaultSocietyPrerequisiteShouldBeFound("loanType.specified=true");

        // Get all the societyPrerequisiteList where loanType is null
        defaultSocietyPrerequisiteShouldNotBeFound("loanType.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByMandatoryIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where mandatory equals to DEFAULT_MANDATORY
        defaultSocietyPrerequisiteShouldBeFound("mandatory.equals=" + DEFAULT_MANDATORY);

        // Get all the societyPrerequisiteList where mandatory equals to UPDATED_MANDATORY
        defaultSocietyPrerequisiteShouldNotBeFound("mandatory.equals=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByMandatoryIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where mandatory in DEFAULT_MANDATORY or UPDATED_MANDATORY
        defaultSocietyPrerequisiteShouldBeFound("mandatory.in=" + DEFAULT_MANDATORY + "," + UPDATED_MANDATORY);

        // Get all the societyPrerequisiteList where mandatory equals to UPDATED_MANDATORY
        defaultSocietyPrerequisiteShouldNotBeFound("mandatory.in=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByMandatoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where mandatory is not null
        defaultSocietyPrerequisiteShouldBeFound("mandatory.specified=true");

        // Get all the societyPrerequisiteList where mandatory is null
        defaultSocietyPrerequisiteShouldNotBeFound("mandatory.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByMandatoryContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where mandatory contains DEFAULT_MANDATORY
        defaultSocietyPrerequisiteShouldBeFound("mandatory.contains=" + DEFAULT_MANDATORY);

        // Get all the societyPrerequisiteList where mandatory contains UPDATED_MANDATORY
        defaultSocietyPrerequisiteShouldNotBeFound("mandatory.contains=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByMandatoryNotContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where mandatory does not contain DEFAULT_MANDATORY
        defaultSocietyPrerequisiteShouldNotBeFound("mandatory.doesNotContain=" + DEFAULT_MANDATORY);

        // Get all the societyPrerequisiteList where mandatory does not contain UPDATED_MANDATORY
        defaultSocietyPrerequisiteShouldBeFound("mandatory.doesNotContain=" + UPDATED_MANDATORY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSocietyPrerequisiteShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the societyPrerequisiteList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyPrerequisiteShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSocietyPrerequisiteShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the societyPrerequisiteList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSocietyPrerequisiteShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where lastModified is not null
        defaultSocietyPrerequisiteShouldBeFound("lastModified.specified=true");

        // Get all the societyPrerequisiteList where lastModified is null
        defaultSocietyPrerequisiteShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSocietyPrerequisiteShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyPrerequisiteList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyPrerequisiteShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSocietyPrerequisiteShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the societyPrerequisiteList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSocietyPrerequisiteShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where lastModifiedBy is not null
        defaultSocietyPrerequisiteShouldBeFound("lastModifiedBy.specified=true");

        // Get all the societyPrerequisiteList where lastModifiedBy is null
        defaultSocietyPrerequisiteShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSocietyPrerequisiteShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyPrerequisiteList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSocietyPrerequisiteShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSocietyPrerequisiteShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the societyPrerequisiteList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSocietyPrerequisiteShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where createdBy equals to DEFAULT_CREATED_BY
        defaultSocietyPrerequisiteShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the societyPrerequisiteList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyPrerequisiteShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSocietyPrerequisiteShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the societyPrerequisiteList where createdBy equals to UPDATED_CREATED_BY
        defaultSocietyPrerequisiteShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where createdBy is not null
        defaultSocietyPrerequisiteShouldBeFound("createdBy.specified=true");

        // Get all the societyPrerequisiteList where createdBy is null
        defaultSocietyPrerequisiteShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where createdBy contains DEFAULT_CREATED_BY
        defaultSocietyPrerequisiteShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the societyPrerequisiteList where createdBy contains UPDATED_CREATED_BY
        defaultSocietyPrerequisiteShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSocietyPrerequisiteShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the societyPrerequisiteList where createdBy does not contain UPDATED_CREATED_BY
        defaultSocietyPrerequisiteShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where createdOn equals to DEFAULT_CREATED_ON
        defaultSocietyPrerequisiteShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the societyPrerequisiteList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyPrerequisiteShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSocietyPrerequisiteShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the societyPrerequisiteList where createdOn equals to UPDATED_CREATED_ON
        defaultSocietyPrerequisiteShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where createdOn is not null
        defaultSocietyPrerequisiteShouldBeFound("createdOn.specified=true");

        // Get all the societyPrerequisiteList where createdOn is null
        defaultSocietyPrerequisiteShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where isDeleted equals to DEFAULT_IS_DELETED
        defaultSocietyPrerequisiteShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the societyPrerequisiteList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyPrerequisiteShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultSocietyPrerequisiteShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the societyPrerequisiteList where isDeleted equals to UPDATED_IS_DELETED
        defaultSocietyPrerequisiteShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where isDeleted is not null
        defaultSocietyPrerequisiteShouldBeFound("isDeleted.specified=true");

        // Get all the societyPrerequisiteList where isDeleted is null
        defaultSocietyPrerequisiteShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSocietyPrerequisiteShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyPrerequisiteList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyPrerequisiteShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSocietyPrerequisiteShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the societyPrerequisiteList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSocietyPrerequisiteShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField1 is not null
        defaultSocietyPrerequisiteShouldBeFound("freeField1.specified=true");

        // Get all the societyPrerequisiteList where freeField1 is null
        defaultSocietyPrerequisiteShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultSocietyPrerequisiteShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyPrerequisiteList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultSocietyPrerequisiteShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultSocietyPrerequisiteShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the societyPrerequisiteList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultSocietyPrerequisiteShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSocietyPrerequisiteShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyPrerequisiteList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyPrerequisiteShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSocietyPrerequisiteShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the societyPrerequisiteList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSocietyPrerequisiteShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField2 is not null
        defaultSocietyPrerequisiteShouldBeFound("freeField2.specified=true");

        // Get all the societyPrerequisiteList where freeField2 is null
        defaultSocietyPrerequisiteShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultSocietyPrerequisiteShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyPrerequisiteList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultSocietyPrerequisiteShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultSocietyPrerequisiteShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the societyPrerequisiteList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultSocietyPrerequisiteShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSocietyPrerequisiteShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyPrerequisiteList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyPrerequisiteShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSocietyPrerequisiteShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the societyPrerequisiteList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSocietyPrerequisiteShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField3 is not null
        defaultSocietyPrerequisiteShouldBeFound("freeField3.specified=true");

        // Get all the societyPrerequisiteList where freeField3 is null
        defaultSocietyPrerequisiteShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultSocietyPrerequisiteShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyPrerequisiteList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultSocietyPrerequisiteShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        // Get all the societyPrerequisiteList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultSocietyPrerequisiteShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the societyPrerequisiteList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultSocietyPrerequisiteShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSocietyPrerequisitesBySocietyIsEqualToSomething() throws Exception {
        Society society;
        if (TestUtil.findAll(em, Society.class).isEmpty()) {
            societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);
            society = SocietyResourceIT.createEntity(em);
        } else {
            society = TestUtil.findAll(em, Society.class).get(0);
        }
        em.persist(society);
        em.flush();
        societyPrerequisite.setSociety(society);
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);
        Long societyId = society.getId();

        // Get all the societyPrerequisiteList where society equals to societyId
        defaultSocietyPrerequisiteShouldBeFound("societyId.equals=" + societyId);

        // Get all the societyPrerequisiteList where society equals to (societyId + 1)
        defaultSocietyPrerequisiteShouldNotBeFound("societyId.equals=" + (societyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSocietyPrerequisiteShouldBeFound(String filter) throws Exception {
        restSocietyPrerequisiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societyPrerequisite.getId().intValue())))
            .andExpect(jsonPath("$.[*].docType").value(hasItem(DEFAULT_DOC_TYPE)))
            .andExpect(jsonPath("$.[*].documentDesc").value(hasItem(DEFAULT_DOCUMENT_DESC)))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].loanType").value(hasItem(DEFAULT_LOAN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restSocietyPrerequisiteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSocietyPrerequisiteShouldNotBeFound(String filter) throws Exception {
        restSocietyPrerequisiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSocietyPrerequisiteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSocietyPrerequisite() throws Exception {
        // Get the societyPrerequisite
        restSocietyPrerequisiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocietyPrerequisite() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        int databaseSizeBeforeUpdate = societyPrerequisiteRepository.findAll().size();

        // Update the societyPrerequisite
        SocietyPrerequisite updatedSocietyPrerequisite = societyPrerequisiteRepository.findById(societyPrerequisite.getId()).get();
        // Disconnect from session so that the updates on updatedSocietyPrerequisite are not directly saved in db
        em.detach(updatedSocietyPrerequisite);
        updatedSocietyPrerequisite
            .docType(UPDATED_DOC_TYPE)
            .documentDesc(UPDATED_DOCUMENT_DESC)
            .documentName(UPDATED_DOCUMENT_NAME)
            .loanType(UPDATED_LOAN_TYPE)
            .mandatory(UPDATED_MANDATORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        SocietyPrerequisiteDTO societyPrerequisiteDTO = societyPrerequisiteMapper.toDto(updatedSocietyPrerequisite);

        restSocietyPrerequisiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyPrerequisiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyPrerequisiteDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
        SocietyPrerequisite testSocietyPrerequisite = societyPrerequisiteList.get(societyPrerequisiteList.size() - 1);
        assertThat(testSocietyPrerequisite.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testSocietyPrerequisite.getDocumentDesc()).isEqualTo(UPDATED_DOCUMENT_DESC);
        assertThat(testSocietyPrerequisite.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testSocietyPrerequisite.getLoanType()).isEqualTo(UPDATED_LOAN_TYPE);
        assertThat(testSocietyPrerequisite.getMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testSocietyPrerequisite.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyPrerequisite.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyPrerequisite.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyPrerequisite.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyPrerequisite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyPrerequisite.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyPrerequisite.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyPrerequisite.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingSocietyPrerequisite() throws Exception {
        int databaseSizeBeforeUpdate = societyPrerequisiteRepository.findAll().size();
        societyPrerequisite.setId(count.incrementAndGet());

        // Create the SocietyPrerequisite
        SocietyPrerequisiteDTO societyPrerequisiteDTO = societyPrerequisiteMapper.toDto(societyPrerequisite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyPrerequisiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societyPrerequisiteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyPrerequisiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocietyPrerequisite() throws Exception {
        int databaseSizeBeforeUpdate = societyPrerequisiteRepository.findAll().size();
        societyPrerequisite.setId(count.incrementAndGet());

        // Create the SocietyPrerequisite
        SocietyPrerequisiteDTO societyPrerequisiteDTO = societyPrerequisiteMapper.toDto(societyPrerequisite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyPrerequisiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyPrerequisiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocietyPrerequisite() throws Exception {
        int databaseSizeBeforeUpdate = societyPrerequisiteRepository.findAll().size();
        societyPrerequisite.setId(count.incrementAndGet());

        // Create the SocietyPrerequisite
        SocietyPrerequisiteDTO societyPrerequisiteDTO = societyPrerequisiteMapper.toDto(societyPrerequisite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyPrerequisiteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societyPrerequisiteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocietyPrerequisiteWithPatch() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        int databaseSizeBeforeUpdate = societyPrerequisiteRepository.findAll().size();

        // Update the societyPrerequisite using partial update
        SocietyPrerequisite partialUpdatedSocietyPrerequisite = new SocietyPrerequisite();
        partialUpdatedSocietyPrerequisite.setId(societyPrerequisite.getId());

        partialUpdatedSocietyPrerequisite
            .documentDesc(UPDATED_DOCUMENT_DESC)
            .documentName(UPDATED_DOCUMENT_NAME)
            .mandatory(UPDATED_MANDATORY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyPrerequisiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyPrerequisite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyPrerequisite))
            )
            .andExpect(status().isOk());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
        SocietyPrerequisite testSocietyPrerequisite = societyPrerequisiteList.get(societyPrerequisiteList.size() - 1);
        assertThat(testSocietyPrerequisite.getDocType()).isEqualTo(DEFAULT_DOC_TYPE);
        assertThat(testSocietyPrerequisite.getDocumentDesc()).isEqualTo(UPDATED_DOCUMENT_DESC);
        assertThat(testSocietyPrerequisite.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testSocietyPrerequisite.getLoanType()).isEqualTo(DEFAULT_LOAN_TYPE);
        assertThat(testSocietyPrerequisite.getMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testSocietyPrerequisite.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSocietyPrerequisite.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSocietyPrerequisite.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSocietyPrerequisite.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSocietyPrerequisite.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSocietyPrerequisite.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyPrerequisite.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSocietyPrerequisite.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateSocietyPrerequisiteWithPatch() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        int databaseSizeBeforeUpdate = societyPrerequisiteRepository.findAll().size();

        // Update the societyPrerequisite using partial update
        SocietyPrerequisite partialUpdatedSocietyPrerequisite = new SocietyPrerequisite();
        partialUpdatedSocietyPrerequisite.setId(societyPrerequisite.getId());

        partialUpdatedSocietyPrerequisite
            .docType(UPDATED_DOC_TYPE)
            .documentDesc(UPDATED_DOCUMENT_DESC)
            .documentName(UPDATED_DOCUMENT_NAME)
            .loanType(UPDATED_LOAN_TYPE)
            .mandatory(UPDATED_MANDATORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restSocietyPrerequisiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietyPrerequisite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietyPrerequisite))
            )
            .andExpect(status().isOk());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
        SocietyPrerequisite testSocietyPrerequisite = societyPrerequisiteList.get(societyPrerequisiteList.size() - 1);
        assertThat(testSocietyPrerequisite.getDocType()).isEqualTo(UPDATED_DOC_TYPE);
        assertThat(testSocietyPrerequisite.getDocumentDesc()).isEqualTo(UPDATED_DOCUMENT_DESC);
        assertThat(testSocietyPrerequisite.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testSocietyPrerequisite.getLoanType()).isEqualTo(UPDATED_LOAN_TYPE);
        assertThat(testSocietyPrerequisite.getMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testSocietyPrerequisite.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSocietyPrerequisite.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSocietyPrerequisite.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSocietyPrerequisite.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSocietyPrerequisite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSocietyPrerequisite.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSocietyPrerequisite.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSocietyPrerequisite.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingSocietyPrerequisite() throws Exception {
        int databaseSizeBeforeUpdate = societyPrerequisiteRepository.findAll().size();
        societyPrerequisite.setId(count.incrementAndGet());

        // Create the SocietyPrerequisite
        SocietyPrerequisiteDTO societyPrerequisiteDTO = societyPrerequisiteMapper.toDto(societyPrerequisite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietyPrerequisiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societyPrerequisiteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyPrerequisiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocietyPrerequisite() throws Exception {
        int databaseSizeBeforeUpdate = societyPrerequisiteRepository.findAll().size();
        societyPrerequisite.setId(count.incrementAndGet());

        // Create the SocietyPrerequisite
        SocietyPrerequisiteDTO societyPrerequisiteDTO = societyPrerequisiteMapper.toDto(societyPrerequisite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyPrerequisiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyPrerequisiteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocietyPrerequisite() throws Exception {
        int databaseSizeBeforeUpdate = societyPrerequisiteRepository.findAll().size();
        societyPrerequisite.setId(count.incrementAndGet());

        // Create the SocietyPrerequisite
        SocietyPrerequisiteDTO societyPrerequisiteDTO = societyPrerequisiteMapper.toDto(societyPrerequisite);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietyPrerequisiteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societyPrerequisiteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocietyPrerequisite in the database
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocietyPrerequisite() throws Exception {
        // Initialize the database
        societyPrerequisiteRepository.saveAndFlush(societyPrerequisite);

        int databaseSizeBeforeDelete = societyPrerequisiteRepository.findAll().size();

        // Delete the societyPrerequisite
        restSocietyPrerequisiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, societyPrerequisite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocietyPrerequisite> societyPrerequisiteList = societyPrerequisiteRepository.findAll();
        assertThat(societyPrerequisiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

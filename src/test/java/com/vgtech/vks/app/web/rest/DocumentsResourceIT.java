package com.vgtech.vks.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vgtech.vks.app.IntegrationTest;
import com.vgtech.vks.app.domain.Documents;
import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.domain.enumeration.DocumentType;
import com.vgtech.vks.app.repository.DocumentsRepository;
import com.vgtech.vks.app.service.criteria.DocumentsCriteria;
import com.vgtech.vks.app.service.dto.DocumentsDTO;
import com.vgtech.vks.app.service.mapper.DocumentsMapper;
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
 * Integration tests for the {@link DocumentsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentsResourceIT {

    private static final DocumentType DEFAULT_TYPE = DocumentType.PROFILE_PICTURE;
    private static final DocumentType UPDATED_TYPE = DocumentType.SIGNATURE;

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILE_URL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/documents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private DocumentsMapper documentsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentsMockMvc;

    private Documents documents;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documents createEntity(EntityManager em) {
        Documents documents = new Documents()
            .type(DEFAULT_TYPE)
            .fileName(DEFAULT_FILE_NAME)
            .filePath(DEFAULT_FILE_PATH)
            .fileUrl(DEFAULT_FILE_URL)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return documents;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Documents createUpdatedEntity(EntityManager em) {
        Documents documents = new Documents()
            .type(UPDATED_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileUrl(UPDATED_FILE_URL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return documents;
    }

    @BeforeEach
    public void initTest() {
        documents = createEntity(em);
    }

    @Test
    @Transactional
    void createDocuments() throws Exception {
        int databaseSizeBeforeCreate = documentsRepository.findAll().size();
        // Create the Documents
        DocumentsDTO documentsDTO = documentsMapper.toDto(documents);
        restDocumentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentsDTO)))
            .andExpect(status().isCreated());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeCreate + 1);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDocuments.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testDocuments.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testDocuments.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
        assertThat(testDocuments.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDocuments.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testDocuments.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDocuments.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDocuments.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testDocuments.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testDocuments.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testDocuments.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createDocumentsWithExistingId() throws Exception {
        // Create the Documents with an existing ID
        documents.setId(1L);
        DocumentsDTO documentsDTO = documentsMapper.toDto(documents);

        int databaseSizeBeforeCreate = documentsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documents.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
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
    void getDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get the documents
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL_ID, documents.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documents.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH))
            .andExpect(jsonPath("$.fileUrl").value(DEFAULT_FILE_URL))
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
    void getDocumentsByIdFiltering() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        Long id = documents.getId();

        defaultDocumentsShouldBeFound("id.equals=" + id);
        defaultDocumentsShouldNotBeFound("id.notEquals=" + id);

        defaultDocumentsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDocumentsShouldNotBeFound("id.greaterThan=" + id);

        defaultDocumentsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDocumentsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where type equals to DEFAULT_TYPE
        defaultDocumentsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the documentsList where type equals to UPDATED_TYPE
        defaultDocumentsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDocumentsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the documentsList where type equals to UPDATED_TYPE
        defaultDocumentsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDocumentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where type is not null
        defaultDocumentsShouldBeFound("type.specified=true");

        // Get all the documentsList where type is null
        defaultDocumentsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileName equals to DEFAULT_FILE_NAME
        defaultDocumentsShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the documentsList where fileName equals to UPDATED_FILE_NAME
        defaultDocumentsShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultDocumentsShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the documentsList where fileName equals to UPDATED_FILE_NAME
        defaultDocumentsShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileName is not null
        defaultDocumentsShouldBeFound("fileName.specified=true");

        // Get all the documentsList where fileName is null
        defaultDocumentsShouldNotBeFound("fileName.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileName contains DEFAULT_FILE_NAME
        defaultDocumentsShouldBeFound("fileName.contains=" + DEFAULT_FILE_NAME);

        // Get all the documentsList where fileName contains UPDATED_FILE_NAME
        defaultDocumentsShouldNotBeFound("fileName.contains=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileNameNotContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileName does not contain DEFAULT_FILE_NAME
        defaultDocumentsShouldNotBeFound("fileName.doesNotContain=" + DEFAULT_FILE_NAME);

        // Get all the documentsList where fileName does not contain UPDATED_FILE_NAME
        defaultDocumentsShouldBeFound("fileName.doesNotContain=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    void getAllDocumentsByFilePathIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where filePath equals to DEFAULT_FILE_PATH
        defaultDocumentsShouldBeFound("filePath.equals=" + DEFAULT_FILE_PATH);

        // Get all the documentsList where filePath equals to UPDATED_FILE_PATH
        defaultDocumentsShouldNotBeFound("filePath.equals=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllDocumentsByFilePathIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where filePath in DEFAULT_FILE_PATH or UPDATED_FILE_PATH
        defaultDocumentsShouldBeFound("filePath.in=" + DEFAULT_FILE_PATH + "," + UPDATED_FILE_PATH);

        // Get all the documentsList where filePath equals to UPDATED_FILE_PATH
        defaultDocumentsShouldNotBeFound("filePath.in=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllDocumentsByFilePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where filePath is not null
        defaultDocumentsShouldBeFound("filePath.specified=true");

        // Get all the documentsList where filePath is null
        defaultDocumentsShouldNotBeFound("filePath.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFilePathContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where filePath contains DEFAULT_FILE_PATH
        defaultDocumentsShouldBeFound("filePath.contains=" + DEFAULT_FILE_PATH);

        // Get all the documentsList where filePath contains UPDATED_FILE_PATH
        defaultDocumentsShouldNotBeFound("filePath.contains=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllDocumentsByFilePathNotContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where filePath does not contain DEFAULT_FILE_PATH
        defaultDocumentsShouldNotBeFound("filePath.doesNotContain=" + DEFAULT_FILE_PATH);

        // Get all the documentsList where filePath does not contain UPDATED_FILE_PATH
        defaultDocumentsShouldBeFound("filePath.doesNotContain=" + UPDATED_FILE_PATH);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileUrl equals to DEFAULT_FILE_URL
        defaultDocumentsShouldBeFound("fileUrl.equals=" + DEFAULT_FILE_URL);

        // Get all the documentsList where fileUrl equals to UPDATED_FILE_URL
        defaultDocumentsShouldNotBeFound("fileUrl.equals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileUrl in DEFAULT_FILE_URL or UPDATED_FILE_URL
        defaultDocumentsShouldBeFound("fileUrl.in=" + DEFAULT_FILE_URL + "," + UPDATED_FILE_URL);

        // Get all the documentsList where fileUrl equals to UPDATED_FILE_URL
        defaultDocumentsShouldNotBeFound("fileUrl.in=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileUrl is not null
        defaultDocumentsShouldBeFound("fileUrl.specified=true");

        // Get all the documentsList where fileUrl is null
        defaultDocumentsShouldNotBeFound("fileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUrlContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileUrl contains DEFAULT_FILE_URL
        defaultDocumentsShouldBeFound("fileUrl.contains=" + DEFAULT_FILE_URL);

        // Get all the documentsList where fileUrl contains UPDATED_FILE_URL
        defaultDocumentsShouldNotBeFound("fileUrl.contains=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    void getAllDocumentsByFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where fileUrl does not contain DEFAULT_FILE_URL
        defaultDocumentsShouldNotBeFound("fileUrl.doesNotContain=" + DEFAULT_FILE_URL);

        // Get all the documentsList where fileUrl does not contain UPDATED_FILE_URL
        defaultDocumentsShouldBeFound("fileUrl.doesNotContain=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDocumentsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the documentsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDocumentsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDocumentsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the documentsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDocumentsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where lastModified is not null
        defaultDocumentsShouldBeFound("lastModified.specified=true");

        // Get all the documentsList where lastModified is null
        defaultDocumentsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDocumentsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the documentsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDocumentsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDocumentsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the documentsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDocumentsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where lastModifiedBy is not null
        defaultDocumentsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the documentsList where lastModifiedBy is null
        defaultDocumentsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDocumentsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the documentsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDocumentsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDocumentsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the documentsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDocumentsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdBy equals to DEFAULT_CREATED_BY
        defaultDocumentsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the documentsList where createdBy equals to UPDATED_CREATED_BY
        defaultDocumentsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultDocumentsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the documentsList where createdBy equals to UPDATED_CREATED_BY
        defaultDocumentsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdBy is not null
        defaultDocumentsShouldBeFound("createdBy.specified=true");

        // Get all the documentsList where createdBy is null
        defaultDocumentsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdBy contains DEFAULT_CREATED_BY
        defaultDocumentsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the documentsList where createdBy contains UPDATED_CREATED_BY
        defaultDocumentsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultDocumentsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the documentsList where createdBy does not contain UPDATED_CREATED_BY
        defaultDocumentsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdOn equals to DEFAULT_CREATED_ON
        defaultDocumentsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the documentsList where createdOn equals to UPDATED_CREATED_ON
        defaultDocumentsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultDocumentsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the documentsList where createdOn equals to UPDATED_CREATED_ON
        defaultDocumentsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDocumentsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where createdOn is not null
        defaultDocumentsShouldBeFound("createdOn.specified=true");

        // Get all the documentsList where createdOn is null
        defaultDocumentsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultDocumentsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the documentsList where isDeleted equals to UPDATED_IS_DELETED
        defaultDocumentsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllDocumentsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultDocumentsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the documentsList where isDeleted equals to UPDATED_IS_DELETED
        defaultDocumentsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllDocumentsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where isDeleted is not null
        defaultDocumentsShouldBeFound("isDeleted.specified=true");

        // Get all the documentsList where isDeleted is null
        defaultDocumentsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultDocumentsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the documentsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultDocumentsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultDocumentsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the documentsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultDocumentsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField1 is not null
        defaultDocumentsShouldBeFound("freeField1.specified=true");

        // Get all the documentsList where freeField1 is null
        defaultDocumentsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultDocumentsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the documentsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultDocumentsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultDocumentsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the documentsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultDocumentsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultDocumentsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the documentsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultDocumentsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultDocumentsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the documentsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultDocumentsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField2 is not null
        defaultDocumentsShouldBeFound("freeField2.specified=true");

        // Get all the documentsList where freeField2 is null
        defaultDocumentsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultDocumentsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the documentsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultDocumentsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultDocumentsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the documentsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultDocumentsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultDocumentsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the documentsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultDocumentsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultDocumentsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the documentsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultDocumentsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField3 is not null
        defaultDocumentsShouldBeFound("freeField3.specified=true");

        // Get all the documentsList where freeField3 is null
        defaultDocumentsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultDocumentsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the documentsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultDocumentsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDocumentsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        // Get all the documentsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultDocumentsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the documentsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultDocumentsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDocumentsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            documentsRepository.saveAndFlush(documents);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        documents.setMember(member);
        documentsRepository.saveAndFlush(documents);
        Long memberId = member.getId();

        // Get all the documentsList where member equals to memberId
        defaultDocumentsShouldBeFound("memberId.equals=" + memberId);

        // Get all the documentsList where member equals to (memberId + 1)
        defaultDocumentsShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentsShouldBeFound(String filter) throws Exception {
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documents.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentsShouldNotBeFound(String filter) throws Exception {
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocuments() throws Exception {
        // Get the documents
        restDocumentsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();

        // Update the documents
        Documents updatedDocuments = documentsRepository.findById(documents.getId()).get();
        // Disconnect from session so that the updates on updatedDocuments are not directly saved in db
        em.detach(updatedDocuments);
        updatedDocuments
            .type(UPDATED_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileUrl(UPDATED_FILE_URL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        DocumentsDTO documentsDTO = documentsMapper.toDto(updatedDocuments);

        restDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDocuments.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testDocuments.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testDocuments.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testDocuments.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDocuments.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDocuments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDocuments.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDocuments.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDocuments.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testDocuments.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testDocuments.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // Create the Documents
        DocumentsDTO documentsDTO = documentsMapper.toDto(documents);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // Create the Documents
        DocumentsDTO documentsDTO = documentsMapper.toDto(documents);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // Create the Documents
        DocumentsDTO documentsDTO = documentsMapper.toDto(documents);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documentsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentsWithPatch() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();

        // Update the documents using partial update
        Documents partialUpdatedDocuments = new Documents();
        partialUpdatedDocuments.setId(documents.getId());

        partialUpdatedDocuments
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1);

        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocuments))
            )
            .andExpect(status().isOk());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDocuments.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testDocuments.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testDocuments.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
        assertThat(testDocuments.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDocuments.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDocuments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDocuments.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDocuments.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDocuments.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testDocuments.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testDocuments.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateDocumentsWithPatch() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();

        // Update the documents using partial update
        Documents partialUpdatedDocuments = new Documents();
        partialUpdatedDocuments.setId(documents.getId());

        partialUpdatedDocuments
            .type(UPDATED_TYPE)
            .fileName(UPDATED_FILE_NAME)
            .filePath(UPDATED_FILE_PATH)
            .fileUrl(UPDATED_FILE_URL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocuments.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocuments))
            )
            .andExpect(status().isOk());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
        Documents testDocuments = documentsList.get(documentsList.size() - 1);
        assertThat(testDocuments.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDocuments.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testDocuments.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testDocuments.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testDocuments.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDocuments.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDocuments.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDocuments.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDocuments.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDocuments.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testDocuments.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testDocuments.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // Create the Documents
        DocumentsDTO documentsDTO = documentsMapper.toDto(documents);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // Create the Documents
        DocumentsDTO documentsDTO = documentsMapper.toDto(documents);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documentsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocuments() throws Exception {
        int databaseSizeBeforeUpdate = documentsRepository.findAll().size();
        documents.setId(count.incrementAndGet());

        // Create the Documents
        DocumentsDTO documentsDTO = documentsMapper.toDto(documents);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(documentsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Documents in the database
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocuments() throws Exception {
        // Initialize the database
        documentsRepository.saveAndFlush(documents);

        int databaseSizeBeforeDelete = documentsRepository.findAll().size();

        // Delete the documents
        restDocumentsMockMvc
            .perform(delete(ENTITY_API_URL_ID, documents.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Documents> documentsList = documentsRepository.findAll();
        assertThat(documentsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

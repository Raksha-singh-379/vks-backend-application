package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.ExpenditureTypeRepository;
import com.vgtech.vks.app.service.ExpenditureTypeQueryService;
import com.vgtech.vks.app.service.ExpenditureTypeService;
import com.vgtech.vks.app.service.criteria.ExpenditureTypeCriteria;
import com.vgtech.vks.app.service.dto.ExpenditureTypeDTO;
import com.vgtech.vks.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.vgtech.vks.app.domain.ExpenditureType}.
 */
@RestController
@RequestMapping("/api")
public class ExpenditureTypeResource {

    private final Logger log = LoggerFactory.getLogger(ExpenditureTypeResource.class);

    private static final String ENTITY_NAME = "expenditureType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExpenditureTypeService expenditureTypeService;

    private final ExpenditureTypeRepository expenditureTypeRepository;

    private final ExpenditureTypeQueryService expenditureTypeQueryService;

    public ExpenditureTypeResource(
        ExpenditureTypeService expenditureTypeService,
        ExpenditureTypeRepository expenditureTypeRepository,
        ExpenditureTypeQueryService expenditureTypeQueryService
    ) {
        this.expenditureTypeService = expenditureTypeService;
        this.expenditureTypeRepository = expenditureTypeRepository;
        this.expenditureTypeQueryService = expenditureTypeQueryService;
    }

    /**
     * {@code POST  /expenditure-types} : Create a new expenditureType.
     *
     * @param expenditureTypeDTO the expenditureTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new expenditureTypeDTO, or with status {@code 400 (Bad Request)} if the expenditureType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/expenditure-types")
    public ResponseEntity<ExpenditureTypeDTO> createExpenditureType(@RequestBody ExpenditureTypeDTO expenditureTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save ExpenditureType : {}", expenditureTypeDTO);
        if (expenditureTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new expenditureType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExpenditureTypeDTO result = expenditureTypeService.save(expenditureTypeDTO);
        return ResponseEntity
            .created(new URI("/api/expenditure-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /expenditure-types/:id} : Updates an existing expenditureType.
     *
     * @param id the id of the expenditureTypeDTO to save.
     * @param expenditureTypeDTO the expenditureTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenditureTypeDTO,
     * or with status {@code 400 (Bad Request)} if the expenditureTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the expenditureTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/expenditure-types/{id}")
    public ResponseEntity<ExpenditureTypeDTO> updateExpenditureType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExpenditureTypeDTO expenditureTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ExpenditureType : {}, {}", id, expenditureTypeDTO);
        if (expenditureTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expenditureTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expenditureTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExpenditureTypeDTO result = expenditureTypeService.update(expenditureTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenditureTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /expenditure-types/:id} : Partial updates given fields of an existing expenditureType, field will ignore if it is null
     *
     * @param id the id of the expenditureTypeDTO to save.
     * @param expenditureTypeDTO the expenditureTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated expenditureTypeDTO,
     * or with status {@code 400 (Bad Request)} if the expenditureTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the expenditureTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the expenditureTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/expenditure-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ExpenditureTypeDTO> partialUpdateExpenditureType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ExpenditureTypeDTO expenditureTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ExpenditureType partially : {}, {}", id, expenditureTypeDTO);
        if (expenditureTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, expenditureTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!expenditureTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExpenditureTypeDTO> result = expenditureTypeService.partialUpdate(expenditureTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, expenditureTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /expenditure-types} : get all the expenditureTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of expenditureTypes in body.
     */
    @GetMapping("/expenditure-types")
    public ResponseEntity<List<ExpenditureTypeDTO>> getAllExpenditureTypes(
        ExpenditureTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ExpenditureTypes by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<ExpenditureTypeDTO> page = expenditureTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /expenditure-types/count} : count all the expenditureTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/expenditure-types/count")
    public ResponseEntity<Long> countExpenditureTypes(ExpenditureTypeCriteria criteria) {
        log.debug("REST request to count ExpenditureTypes by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(expenditureTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /expenditure-types/:id} : get the "id" expenditureType.
     *
     * @param id the id of the expenditureTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the expenditureTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/expenditure-types/{id}")
    public ResponseEntity<ExpenditureTypeDTO> getExpenditureType(@PathVariable Long id) {
        log.debug("REST request to get ExpenditureType : {}", id);
        Optional<ExpenditureTypeDTO> expenditureTypeDTO = expenditureTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(expenditureTypeDTO);
    }

    /**
     * {@code DELETE  /expenditure-types/:id} : delete the "id" expenditureType.
     *
     * @param id the id of the expenditureTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/expenditure-types/{id}")
    public ResponseEntity<Void> deleteExpenditureType(@PathVariable Long id) {
        log.debug("REST request to delete ExpenditureType : {}", id);
        expenditureTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

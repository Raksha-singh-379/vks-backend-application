package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.SocietyAssetsDataRepository;
import com.vgtech.vks.app.service.SocietyAssetsDataQueryService;
import com.vgtech.vks.app.service.SocietyAssetsDataService;
import com.vgtech.vks.app.service.criteria.SocietyAssetsDataCriteria;
import com.vgtech.vks.app.service.dto.SocietyAssetsDataDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.SocietyAssetsData}.
 */
@RestController
@RequestMapping("/api")
public class SocietyAssetsDataResource {

    private final Logger log = LoggerFactory.getLogger(SocietyAssetsDataResource.class);

    private static final String ENTITY_NAME = "societyAssetsData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocietyAssetsDataService societyAssetsDataService;

    private final SocietyAssetsDataRepository societyAssetsDataRepository;

    private final SocietyAssetsDataQueryService societyAssetsDataQueryService;

    public SocietyAssetsDataResource(
        SocietyAssetsDataService societyAssetsDataService,
        SocietyAssetsDataRepository societyAssetsDataRepository,
        SocietyAssetsDataQueryService societyAssetsDataQueryService
    ) {
        this.societyAssetsDataService = societyAssetsDataService;
        this.societyAssetsDataRepository = societyAssetsDataRepository;
        this.societyAssetsDataQueryService = societyAssetsDataQueryService;
    }

    /**
     * {@code POST  /society-assets-data} : Create a new societyAssetsData.
     *
     * @param societyAssetsDataDTO the societyAssetsDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societyAssetsDataDTO, or with status {@code 400 (Bad Request)} if the societyAssetsData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/society-assets-data")
    public ResponseEntity<SocietyAssetsDataDTO> createSocietyAssetsData(@RequestBody SocietyAssetsDataDTO societyAssetsDataDTO)
        throws URISyntaxException {
        log.debug("REST request to save SocietyAssetsData : {}", societyAssetsDataDTO);
        if (societyAssetsDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new societyAssetsData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocietyAssetsDataDTO result = societyAssetsDataService.save(societyAssetsDataDTO);
        return ResponseEntity
            .created(new URI("/api/society-assets-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /society-assets-data/:id} : Updates an existing societyAssetsData.
     *
     * @param id the id of the societyAssetsDataDTO to save.
     * @param societyAssetsDataDTO the societyAssetsDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyAssetsDataDTO,
     * or with status {@code 400 (Bad Request)} if the societyAssetsDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societyAssetsDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/society-assets-data/{id}")
    public ResponseEntity<SocietyAssetsDataDTO> updateSocietyAssetsData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyAssetsDataDTO societyAssetsDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocietyAssetsData : {}, {}", id, societyAssetsDataDTO);
        if (societyAssetsDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyAssetsDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyAssetsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocietyAssetsDataDTO result = societyAssetsDataService.update(societyAssetsDataDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyAssetsDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /society-assets-data/:id} : Partial updates given fields of an existing societyAssetsData, field will ignore if it is null
     *
     * @param id the id of the societyAssetsDataDTO to save.
     * @param societyAssetsDataDTO the societyAssetsDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyAssetsDataDTO,
     * or with status {@code 400 (Bad Request)} if the societyAssetsDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societyAssetsDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societyAssetsDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/society-assets-data/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocietyAssetsDataDTO> partialUpdateSocietyAssetsData(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyAssetsDataDTO societyAssetsDataDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocietyAssetsData partially : {}, {}", id, societyAssetsDataDTO);
        if (societyAssetsDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyAssetsDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyAssetsDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocietyAssetsDataDTO> result = societyAssetsDataService.partialUpdate(societyAssetsDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyAssetsDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /society-assets-data} : get all the societyAssetsData.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societyAssetsData in body.
     */
    @GetMapping("/society-assets-data")
    public ResponseEntity<List<SocietyAssetsDataDTO>> getAllSocietyAssetsData(
        SocietyAssetsDataCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocietyAssetsData by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<SocietyAssetsDataDTO> page = societyAssetsDataQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /society-assets-data/count} : count all the societyAssetsData.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/society-assets-data/count")
    public ResponseEntity<Long> countSocietyAssetsData(SocietyAssetsDataCriteria criteria) {
        log.debug("REST request to count SocietyAssetsData by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(societyAssetsDataQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /society-assets-data/:id} : get the "id" societyAssetsData.
     *
     * @param id the id of the societyAssetsDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societyAssetsDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/society-assets-data/{id}")
    public ResponseEntity<SocietyAssetsDataDTO> getSocietyAssetsData(@PathVariable Long id) {
        log.debug("REST request to get SocietyAssetsData : {}", id);
        Optional<SocietyAssetsDataDTO> societyAssetsDataDTO = societyAssetsDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societyAssetsDataDTO);
    }

    /**
     * {@code DELETE  /society-assets-data/:id} : delete the "id" societyAssetsData.
     *
     * @param id the id of the societyAssetsDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/society-assets-data/{id}")
    public ResponseEntity<Void> deleteSocietyAssetsData(@PathVariable Long id) {
        log.debug("REST request to delete SocietyAssetsData : {}", id);
        societyAssetsDataService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.SocietyAssetsRepository;
import com.vgtech.vks.app.service.SocietyAssetsQueryService;
import com.vgtech.vks.app.service.SocietyAssetsService;
import com.vgtech.vks.app.service.criteria.SocietyAssetsCriteria;
import com.vgtech.vks.app.service.dto.SocietyAssetsDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.SocietyAssets}.
 */
@RestController
@RequestMapping("/api")
public class SocietyAssetsResource {

    private final Logger log = LoggerFactory.getLogger(SocietyAssetsResource.class);

    private static final String ENTITY_NAME = "societyAssets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocietyAssetsService societyAssetsService;

    private final SocietyAssetsRepository societyAssetsRepository;

    private final SocietyAssetsQueryService societyAssetsQueryService;

    public SocietyAssetsResource(
        SocietyAssetsService societyAssetsService,
        SocietyAssetsRepository societyAssetsRepository,
        SocietyAssetsQueryService societyAssetsQueryService
    ) {
        this.societyAssetsService = societyAssetsService;
        this.societyAssetsRepository = societyAssetsRepository;
        this.societyAssetsQueryService = societyAssetsQueryService;
    }

    /**
     * {@code POST  /society-assets} : Create a new societyAssets.
     *
     * @param societyAssetsDTO the societyAssetsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societyAssetsDTO, or with status {@code 400 (Bad Request)} if the societyAssets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/society-assets")
    public ResponseEntity<SocietyAssetsDTO> createSocietyAssets(@RequestBody SocietyAssetsDTO societyAssetsDTO) throws URISyntaxException {
        log.debug("REST request to save SocietyAssets : {}", societyAssetsDTO);
        if (societyAssetsDTO.getId() != null) {
            throw new BadRequestAlertException("A new societyAssets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocietyAssetsDTO result = societyAssetsService.save(societyAssetsDTO);
        return ResponseEntity
            .created(new URI("/api/society-assets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /society-assets/:id} : Updates an existing societyAssets.
     *
     * @param id the id of the societyAssetsDTO to save.
     * @param societyAssetsDTO the societyAssetsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyAssetsDTO,
     * or with status {@code 400 (Bad Request)} if the societyAssetsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societyAssetsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/society-assets/{id}")
    public ResponseEntity<SocietyAssetsDTO> updateSocietyAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyAssetsDTO societyAssetsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocietyAssets : {}, {}", id, societyAssetsDTO);
        if (societyAssetsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyAssetsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocietyAssetsDTO result = societyAssetsService.update(societyAssetsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyAssetsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /society-assets/:id} : Partial updates given fields of an existing societyAssets, field will ignore if it is null
     *
     * @param id the id of the societyAssetsDTO to save.
     * @param societyAssetsDTO the societyAssetsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyAssetsDTO,
     * or with status {@code 400 (Bad Request)} if the societyAssetsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societyAssetsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societyAssetsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/society-assets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocietyAssetsDTO> partialUpdateSocietyAssets(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyAssetsDTO societyAssetsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocietyAssets partially : {}, {}", id, societyAssetsDTO);
        if (societyAssetsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyAssetsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyAssetsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocietyAssetsDTO> result = societyAssetsService.partialUpdate(societyAssetsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyAssetsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /society-assets} : get all the societyAssets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societyAssets in body.
     */
    @GetMapping("/society-assets")
    public ResponseEntity<List<SocietyAssetsDTO>> getAllSocietyAssets(
        SocietyAssetsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocietyAssets by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<SocietyAssetsDTO> page = societyAssetsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /society-assets/count} : count all the societyAssets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/society-assets/count")
    public ResponseEntity<Long> countSocietyAssets(SocietyAssetsCriteria criteria) {
        log.debug("REST request to count SocietyAssets by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(societyAssetsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /society-assets/:id} : get the "id" societyAssets.
     *
     * @param id the id of the societyAssetsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societyAssetsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/society-assets/{id}")
    public ResponseEntity<SocietyAssetsDTO> getSocietyAssets(@PathVariable Long id) {
        log.debug("REST request to get SocietyAssets : {}", id);
        Optional<SocietyAssetsDTO> societyAssetsDTO = societyAssetsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societyAssetsDTO);
    }

    /**
     * {@code DELETE  /society-assets/:id} : delete the "id" societyAssets.
     *
     * @param id the id of the societyAssetsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/society-assets/{id}")
    public ResponseEntity<Void> deleteSocietyAssets(@PathVariable Long id) {
        log.debug("REST request to delete SocietyAssets : {}", id);
        societyAssetsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

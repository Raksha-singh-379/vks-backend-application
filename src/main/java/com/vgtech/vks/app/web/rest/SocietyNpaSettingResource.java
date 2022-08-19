package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.SocietyNpaSettingRepository;
import com.vgtech.vks.app.service.SocietyNpaSettingQueryService;
import com.vgtech.vks.app.service.SocietyNpaSettingService;
import com.vgtech.vks.app.service.criteria.SocietyNpaSettingCriteria;
import com.vgtech.vks.app.service.dto.SocietyNpaSettingDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.SocietyNpaSetting}.
 */
@RestController
@RequestMapping("/api")
public class SocietyNpaSettingResource {

    private final Logger log = LoggerFactory.getLogger(SocietyNpaSettingResource.class);

    private static final String ENTITY_NAME = "societyNpaSetting";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocietyNpaSettingService societyNpaSettingService;

    private final SocietyNpaSettingRepository societyNpaSettingRepository;

    private final SocietyNpaSettingQueryService societyNpaSettingQueryService;

    public SocietyNpaSettingResource(
        SocietyNpaSettingService societyNpaSettingService,
        SocietyNpaSettingRepository societyNpaSettingRepository,
        SocietyNpaSettingQueryService societyNpaSettingQueryService
    ) {
        this.societyNpaSettingService = societyNpaSettingService;
        this.societyNpaSettingRepository = societyNpaSettingRepository;
        this.societyNpaSettingQueryService = societyNpaSettingQueryService;
    }

    /**
     * {@code POST  /society-npa-settings} : Create a new societyNpaSetting.
     *
     * @param societyNpaSettingDTO the societyNpaSettingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societyNpaSettingDTO, or with status {@code 400 (Bad Request)} if the societyNpaSetting has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/society-npa-settings")
    public ResponseEntity<SocietyNpaSettingDTO> createSocietyNpaSetting(@RequestBody SocietyNpaSettingDTO societyNpaSettingDTO)
        throws URISyntaxException {
        log.debug("REST request to save SocietyNpaSetting : {}", societyNpaSettingDTO);
        if (societyNpaSettingDTO.getId() != null) {
            throw new BadRequestAlertException("A new societyNpaSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocietyNpaSettingDTO result = societyNpaSettingService.save(societyNpaSettingDTO);
        return ResponseEntity
            .created(new URI("/api/society-npa-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /society-npa-settings/:id} : Updates an existing societyNpaSetting.
     *
     * @param id the id of the societyNpaSettingDTO to save.
     * @param societyNpaSettingDTO the societyNpaSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyNpaSettingDTO,
     * or with status {@code 400 (Bad Request)} if the societyNpaSettingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societyNpaSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/society-npa-settings/{id}")
    public ResponseEntity<SocietyNpaSettingDTO> updateSocietyNpaSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyNpaSettingDTO societyNpaSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocietyNpaSetting : {}, {}", id, societyNpaSettingDTO);
        if (societyNpaSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyNpaSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyNpaSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocietyNpaSettingDTO result = societyNpaSettingService.update(societyNpaSettingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyNpaSettingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /society-npa-settings/:id} : Partial updates given fields of an existing societyNpaSetting, field will ignore if it is null
     *
     * @param id the id of the societyNpaSettingDTO to save.
     * @param societyNpaSettingDTO the societyNpaSettingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyNpaSettingDTO,
     * or with status {@code 400 (Bad Request)} if the societyNpaSettingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societyNpaSettingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societyNpaSettingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/society-npa-settings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocietyNpaSettingDTO> partialUpdateSocietyNpaSetting(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyNpaSettingDTO societyNpaSettingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocietyNpaSetting partially : {}, {}", id, societyNpaSettingDTO);
        if (societyNpaSettingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyNpaSettingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyNpaSettingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocietyNpaSettingDTO> result = societyNpaSettingService.partialUpdate(societyNpaSettingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyNpaSettingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /society-npa-settings} : get all the societyNpaSettings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societyNpaSettings in body.
     */
    @GetMapping("/society-npa-settings")
    public ResponseEntity<List<SocietyNpaSettingDTO>> getAllSocietyNpaSettings(
        SocietyNpaSettingCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocietyNpaSettings by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<SocietyNpaSettingDTO> page = societyNpaSettingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /society-npa-settings/count} : count all the societyNpaSettings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/society-npa-settings/count")
    public ResponseEntity<Long> countSocietyNpaSettings(SocietyNpaSettingCriteria criteria) {
        log.debug("REST request to count SocietyNpaSettings by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(societyNpaSettingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /society-npa-settings/:id} : get the "id" societyNpaSetting.
     *
     * @param id the id of the societyNpaSettingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societyNpaSettingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/society-npa-settings/{id}")
    public ResponseEntity<SocietyNpaSettingDTO> getSocietyNpaSetting(@PathVariable Long id) {
        log.debug("REST request to get SocietyNpaSetting : {}", id);
        Optional<SocietyNpaSettingDTO> societyNpaSettingDTO = societyNpaSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societyNpaSettingDTO);
    }

    /**
     * {@code DELETE  /society-npa-settings/:id} : delete the "id" societyNpaSetting.
     *
     * @param id the id of the societyNpaSettingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/society-npa-settings/{id}")
    public ResponseEntity<Void> deleteSocietyNpaSetting(@PathVariable Long id) {
        log.debug("REST request to delete SocietyNpaSetting : {}", id);
        societyNpaSettingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

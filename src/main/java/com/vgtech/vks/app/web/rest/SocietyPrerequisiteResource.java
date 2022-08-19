package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.SocietyPrerequisiteRepository;
import com.vgtech.vks.app.service.SocietyPrerequisiteQueryService;
import com.vgtech.vks.app.service.SocietyPrerequisiteService;
import com.vgtech.vks.app.service.criteria.SocietyPrerequisiteCriteria;
import com.vgtech.vks.app.service.dto.SocietyPrerequisiteDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.SocietyPrerequisite}.
 */
@RestController
@RequestMapping("/api")
public class SocietyPrerequisiteResource {

    private final Logger log = LoggerFactory.getLogger(SocietyPrerequisiteResource.class);

    private static final String ENTITY_NAME = "societyPrerequisite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocietyPrerequisiteService societyPrerequisiteService;

    private final SocietyPrerequisiteRepository societyPrerequisiteRepository;

    private final SocietyPrerequisiteQueryService societyPrerequisiteQueryService;

    public SocietyPrerequisiteResource(
        SocietyPrerequisiteService societyPrerequisiteService,
        SocietyPrerequisiteRepository societyPrerequisiteRepository,
        SocietyPrerequisiteQueryService societyPrerequisiteQueryService
    ) {
        this.societyPrerequisiteService = societyPrerequisiteService;
        this.societyPrerequisiteRepository = societyPrerequisiteRepository;
        this.societyPrerequisiteQueryService = societyPrerequisiteQueryService;
    }

    /**
     * {@code POST  /society-prerequisites} : Create a new societyPrerequisite.
     *
     * @param societyPrerequisiteDTO the societyPrerequisiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societyPrerequisiteDTO, or with status {@code 400 (Bad Request)} if the societyPrerequisite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/society-prerequisites")
    public ResponseEntity<SocietyPrerequisiteDTO> createSocietyPrerequisite(@RequestBody SocietyPrerequisiteDTO societyPrerequisiteDTO)
        throws URISyntaxException {
        log.debug("REST request to save SocietyPrerequisite : {}", societyPrerequisiteDTO);
        if (societyPrerequisiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new societyPrerequisite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocietyPrerequisiteDTO result = societyPrerequisiteService.save(societyPrerequisiteDTO);
        return ResponseEntity
            .created(new URI("/api/society-prerequisites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /society-prerequisites/:id} : Updates an existing societyPrerequisite.
     *
     * @param id the id of the societyPrerequisiteDTO to save.
     * @param societyPrerequisiteDTO the societyPrerequisiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyPrerequisiteDTO,
     * or with status {@code 400 (Bad Request)} if the societyPrerequisiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societyPrerequisiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/society-prerequisites/{id}")
    public ResponseEntity<SocietyPrerequisiteDTO> updateSocietyPrerequisite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyPrerequisiteDTO societyPrerequisiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocietyPrerequisite : {}, {}", id, societyPrerequisiteDTO);
        if (societyPrerequisiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyPrerequisiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyPrerequisiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocietyPrerequisiteDTO result = societyPrerequisiteService.update(societyPrerequisiteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyPrerequisiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /society-prerequisites/:id} : Partial updates given fields of an existing societyPrerequisite, field will ignore if it is null
     *
     * @param id the id of the societyPrerequisiteDTO to save.
     * @param societyPrerequisiteDTO the societyPrerequisiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyPrerequisiteDTO,
     * or with status {@code 400 (Bad Request)} if the societyPrerequisiteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societyPrerequisiteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societyPrerequisiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/society-prerequisites/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocietyPrerequisiteDTO> partialUpdateSocietyPrerequisite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyPrerequisiteDTO societyPrerequisiteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocietyPrerequisite partially : {}, {}", id, societyPrerequisiteDTO);
        if (societyPrerequisiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyPrerequisiteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyPrerequisiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocietyPrerequisiteDTO> result = societyPrerequisiteService.partialUpdate(societyPrerequisiteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyPrerequisiteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /society-prerequisites} : get all the societyPrerequisites.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societyPrerequisites in body.
     */
    @GetMapping("/society-prerequisites")
    public ResponseEntity<List<SocietyPrerequisiteDTO>> getAllSocietyPrerequisites(
        SocietyPrerequisiteCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocietyPrerequisites by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<SocietyPrerequisiteDTO> page = societyPrerequisiteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /society-prerequisites/count} : count all the societyPrerequisites.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/society-prerequisites/count")
    public ResponseEntity<Long> countSocietyPrerequisites(SocietyPrerequisiteCriteria criteria) {
        log.debug("REST request to count SocietyPrerequisites by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(societyPrerequisiteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /society-prerequisites/:id} : get the "id" societyPrerequisite.
     *
     * @param id the id of the societyPrerequisiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societyPrerequisiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/society-prerequisites/{id}")
    public ResponseEntity<SocietyPrerequisiteDTO> getSocietyPrerequisite(@PathVariable Long id) {
        log.debug("REST request to get SocietyPrerequisite : {}", id);
        Optional<SocietyPrerequisiteDTO> societyPrerequisiteDTO = societyPrerequisiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societyPrerequisiteDTO);
    }

    /**
     * {@code DELETE  /society-prerequisites/:id} : delete the "id" societyPrerequisite.
     *
     * @param id the id of the societyPrerequisiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/society-prerequisites/{id}")
    public ResponseEntity<Void> deleteSocietyPrerequisite(@PathVariable Long id) {
        log.debug("REST request to delete SocietyPrerequisite : {}", id);
        societyPrerequisiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

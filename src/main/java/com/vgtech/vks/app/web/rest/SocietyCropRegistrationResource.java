package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.SocietyCropRegistrationRepository;
import com.vgtech.vks.app.service.SocietyCropRegistrationQueryService;
import com.vgtech.vks.app.service.SocietyCropRegistrationService;
import com.vgtech.vks.app.service.criteria.SocietyCropRegistrationCriteria;
import com.vgtech.vks.app.service.dto.SocietyCropRegistrationDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.SocietyCropRegistration}.
 */
@RestController
@RequestMapping("/api")
public class SocietyCropRegistrationResource {

    private final Logger log = LoggerFactory.getLogger(SocietyCropRegistrationResource.class);

    private static final String ENTITY_NAME = "societyCropRegistration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocietyCropRegistrationService societyCropRegistrationService;

    private final SocietyCropRegistrationRepository societyCropRegistrationRepository;

    private final SocietyCropRegistrationQueryService societyCropRegistrationQueryService;

    public SocietyCropRegistrationResource(
        SocietyCropRegistrationService societyCropRegistrationService,
        SocietyCropRegistrationRepository societyCropRegistrationRepository,
        SocietyCropRegistrationQueryService societyCropRegistrationQueryService
    ) {
        this.societyCropRegistrationService = societyCropRegistrationService;
        this.societyCropRegistrationRepository = societyCropRegistrationRepository;
        this.societyCropRegistrationQueryService = societyCropRegistrationQueryService;
    }

    /**
     * {@code POST  /society-crop-registrations} : Create a new societyCropRegistration.
     *
     * @param societyCropRegistrationDTO the societyCropRegistrationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societyCropRegistrationDTO, or with status {@code 400 (Bad Request)} if the societyCropRegistration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/society-crop-registrations")
    public ResponseEntity<SocietyCropRegistrationDTO> createSocietyCropRegistration(
        @RequestBody SocietyCropRegistrationDTO societyCropRegistrationDTO
    ) throws URISyntaxException {
        log.debug("REST request to save SocietyCropRegistration : {}", societyCropRegistrationDTO);
        if (societyCropRegistrationDTO.getId() != null) {
            throw new BadRequestAlertException("A new societyCropRegistration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocietyCropRegistrationDTO result = societyCropRegistrationService.save(societyCropRegistrationDTO);
        return ResponseEntity
            .created(new URI("/api/society-crop-registrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /society-crop-registrations/:id} : Updates an existing societyCropRegistration.
     *
     * @param id the id of the societyCropRegistrationDTO to save.
     * @param societyCropRegistrationDTO the societyCropRegistrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyCropRegistrationDTO,
     * or with status {@code 400 (Bad Request)} if the societyCropRegistrationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societyCropRegistrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/society-crop-registrations/{id}")
    public ResponseEntity<SocietyCropRegistrationDTO> updateSocietyCropRegistration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyCropRegistrationDTO societyCropRegistrationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocietyCropRegistration : {}, {}", id, societyCropRegistrationDTO);
        if (societyCropRegistrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyCropRegistrationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyCropRegistrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocietyCropRegistrationDTO result = societyCropRegistrationService.update(societyCropRegistrationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyCropRegistrationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /society-crop-registrations/:id} : Partial updates given fields of an existing societyCropRegistration, field will ignore if it is null
     *
     * @param id the id of the societyCropRegistrationDTO to save.
     * @param societyCropRegistrationDTO the societyCropRegistrationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyCropRegistrationDTO,
     * or with status {@code 400 (Bad Request)} if the societyCropRegistrationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societyCropRegistrationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societyCropRegistrationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/society-crop-registrations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocietyCropRegistrationDTO> partialUpdateSocietyCropRegistration(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyCropRegistrationDTO societyCropRegistrationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocietyCropRegistration partially : {}, {}", id, societyCropRegistrationDTO);
        if (societyCropRegistrationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyCropRegistrationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyCropRegistrationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocietyCropRegistrationDTO> result = societyCropRegistrationService.partialUpdate(societyCropRegistrationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyCropRegistrationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /society-crop-registrations} : get all the societyCropRegistrations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societyCropRegistrations in body.
     */
    @GetMapping("/society-crop-registrations")
    public ResponseEntity<List<SocietyCropRegistrationDTO>> getAllSocietyCropRegistrations(
        SocietyCropRegistrationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocietyCropRegistrations by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<SocietyCropRegistrationDTO> page = societyCropRegistrationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /society-crop-registrations/count} : count all the societyCropRegistrations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/society-crop-registrations/count")
    public ResponseEntity<Long> countSocietyCropRegistrations(SocietyCropRegistrationCriteria criteria) {
        log.debug("REST request to count SocietyCropRegistrations by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(societyCropRegistrationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /society-crop-registrations/:id} : get the "id" societyCropRegistration.
     *
     * @param id the id of the societyCropRegistrationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societyCropRegistrationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/society-crop-registrations/{id}")
    public ResponseEntity<SocietyCropRegistrationDTO> getSocietyCropRegistration(@PathVariable Long id) {
        log.debug("REST request to get SocietyCropRegistration : {}", id);
        Optional<SocietyCropRegistrationDTO> societyCropRegistrationDTO = societyCropRegistrationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societyCropRegistrationDTO);
    }

    /**
     * {@code DELETE  /society-crop-registrations/:id} : delete the "id" societyCropRegistration.
     *
     * @param id the id of the societyCropRegistrationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/society-crop-registrations/{id}")
    public ResponseEntity<Void> deleteSocietyCropRegistration(@PathVariable Long id) {
        log.debug("REST request to delete SocietyCropRegistration : {}", id);
        societyCropRegistrationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.SocietyBanksDetailsRepository;
import com.vgtech.vks.app.service.SocietyBanksDetailsQueryService;
import com.vgtech.vks.app.service.SocietyBanksDetailsService;
import com.vgtech.vks.app.service.criteria.SocietyBanksDetailsCriteria;
import com.vgtech.vks.app.service.dto.SocietyBanksDetailsDTO;
import com.vgtech.vks.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.SocietyBanksDetails}.
 */
@RestController
@RequestMapping("/api")
public class SocietyBanksDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SocietyBanksDetailsResource.class);

    private static final String ENTITY_NAME = "societyBanksDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocietyBanksDetailsService societyBanksDetailsService;

    private final SocietyBanksDetailsRepository societyBanksDetailsRepository;

    private final SocietyBanksDetailsQueryService societyBanksDetailsQueryService;

    public SocietyBanksDetailsResource(
        SocietyBanksDetailsService societyBanksDetailsService,
        SocietyBanksDetailsRepository societyBanksDetailsRepository,
        SocietyBanksDetailsQueryService societyBanksDetailsQueryService
    ) {
        this.societyBanksDetailsService = societyBanksDetailsService;
        this.societyBanksDetailsRepository = societyBanksDetailsRepository;
        this.societyBanksDetailsQueryService = societyBanksDetailsQueryService;
    }

    /**
     * {@code POST  /society-banks-details} : Create a new societyBanksDetails.
     *
     * @param societyBanksDetailsDTO the societyBanksDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societyBanksDetailsDTO, or with status {@code 400 (Bad Request)} if the societyBanksDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/society-banks-details")
    public ResponseEntity<SocietyBanksDetailsDTO> createSocietyBanksDetails(
        @Valid @RequestBody SocietyBanksDetailsDTO societyBanksDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save SocietyBanksDetails : {}", societyBanksDetailsDTO);
        if (societyBanksDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new societyBanksDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocietyBanksDetailsDTO result = societyBanksDetailsService.save(societyBanksDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/society-banks-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /society-banks-details/:id} : Updates an existing societyBanksDetails.
     *
     * @param id the id of the societyBanksDetailsDTO to save.
     * @param societyBanksDetailsDTO the societyBanksDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyBanksDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the societyBanksDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societyBanksDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/society-banks-details/{id}")
    public ResponseEntity<SocietyBanksDetailsDTO> updateSocietyBanksDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SocietyBanksDetailsDTO societyBanksDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocietyBanksDetails : {}, {}", id, societyBanksDetailsDTO);
        if (societyBanksDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyBanksDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyBanksDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocietyBanksDetailsDTO result = societyBanksDetailsService.update(societyBanksDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyBanksDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /society-banks-details/:id} : Partial updates given fields of an existing societyBanksDetails, field will ignore if it is null
     *
     * @param id the id of the societyBanksDetailsDTO to save.
     * @param societyBanksDetailsDTO the societyBanksDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyBanksDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the societyBanksDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societyBanksDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societyBanksDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/society-banks-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocietyBanksDetailsDTO> partialUpdateSocietyBanksDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SocietyBanksDetailsDTO societyBanksDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocietyBanksDetails partially : {}, {}", id, societyBanksDetailsDTO);
        if (societyBanksDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyBanksDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyBanksDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocietyBanksDetailsDTO> result = societyBanksDetailsService.partialUpdate(societyBanksDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyBanksDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /society-banks-details} : get all the societyBanksDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societyBanksDetails in body.
     */
    @GetMapping("/society-banks-details")
    public ResponseEntity<List<SocietyBanksDetailsDTO>> getAllSocietyBanksDetails(
        SocietyBanksDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocietyBanksDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<SocietyBanksDetailsDTO> page = societyBanksDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /society-banks-details/count} : count all the societyBanksDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/society-banks-details/count")
    public ResponseEntity<Long> countSocietyBanksDetails(SocietyBanksDetailsCriteria criteria) {
        log.debug("REST request to count SocietyBanksDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(societyBanksDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /society-banks-details/:id} : get the "id" societyBanksDetails.
     *
     * @param id the id of the societyBanksDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societyBanksDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/society-banks-details/{id}")
    public ResponseEntity<SocietyBanksDetailsDTO> getSocietyBanksDetails(@PathVariable Long id) {
        log.debug("REST request to get SocietyBanksDetails : {}", id);
        Optional<SocietyBanksDetailsDTO> societyBanksDetailsDTO = societyBanksDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societyBanksDetailsDTO);
    }

    /**
     * {@code DELETE  /society-banks-details/:id} : delete the "id" societyBanksDetails.
     *
     * @param id the id of the societyBanksDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/society-banks-details/{id}")
    public ResponseEntity<Void> deleteSocietyBanksDetails(@PathVariable Long id) {
        log.debug("REST request to delete SocietyBanksDetails : {}", id);
        societyBanksDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

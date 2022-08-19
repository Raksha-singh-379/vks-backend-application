package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.LoanWatapDetailsRepository;
import com.vgtech.vks.app.service.LoanWatapDetailsQueryService;
import com.vgtech.vks.app.service.LoanWatapDetailsService;
import com.vgtech.vks.app.service.criteria.LoanWatapDetailsCriteria;
import com.vgtech.vks.app.service.dto.LoanWatapDetailsDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.LoanWatapDetails}.
 */
@RestController
@RequestMapping("/api")
public class LoanWatapDetailsResource {

    private final Logger log = LoggerFactory.getLogger(LoanWatapDetailsResource.class);

    private static final String ENTITY_NAME = "loanWatapDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanWatapDetailsService loanWatapDetailsService;

    private final LoanWatapDetailsRepository loanWatapDetailsRepository;

    private final LoanWatapDetailsQueryService loanWatapDetailsQueryService;

    public LoanWatapDetailsResource(
        LoanWatapDetailsService loanWatapDetailsService,
        LoanWatapDetailsRepository loanWatapDetailsRepository,
        LoanWatapDetailsQueryService loanWatapDetailsQueryService
    ) {
        this.loanWatapDetailsService = loanWatapDetailsService;
        this.loanWatapDetailsRepository = loanWatapDetailsRepository;
        this.loanWatapDetailsQueryService = loanWatapDetailsQueryService;
    }

    /**
     * {@code POST  /loan-watap-details} : Create a new loanWatapDetails.
     *
     * @param loanWatapDetailsDTO the loanWatapDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanWatapDetailsDTO, or with status {@code 400 (Bad Request)} if the loanWatapDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-watap-details")
    public ResponseEntity<LoanWatapDetailsDTO> createLoanWatapDetails(@RequestBody LoanWatapDetailsDTO loanWatapDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save LoanWatapDetails : {}", loanWatapDetailsDTO);
        if (loanWatapDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanWatapDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanWatapDetailsDTO result = loanWatapDetailsService.save(loanWatapDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/loan-watap-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-watap-details/:id} : Updates an existing loanWatapDetails.
     *
     * @param id the id of the loanWatapDetailsDTO to save.
     * @param loanWatapDetailsDTO the loanWatapDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanWatapDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the loanWatapDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanWatapDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-watap-details/{id}")
    public ResponseEntity<LoanWatapDetailsDTO> updateLoanWatapDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanWatapDetailsDTO loanWatapDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanWatapDetails : {}, {}", id, loanWatapDetailsDTO);
        if (loanWatapDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanWatapDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanWatapDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanWatapDetailsDTO result = loanWatapDetailsService.update(loanWatapDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanWatapDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-watap-details/:id} : Partial updates given fields of an existing loanWatapDetails, field will ignore if it is null
     *
     * @param id the id of the loanWatapDetailsDTO to save.
     * @param loanWatapDetailsDTO the loanWatapDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanWatapDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the loanWatapDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loanWatapDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanWatapDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-watap-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanWatapDetailsDTO> partialUpdateLoanWatapDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanWatapDetailsDTO loanWatapDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanWatapDetails partially : {}, {}", id, loanWatapDetailsDTO);
        if (loanWatapDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanWatapDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanWatapDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanWatapDetailsDTO> result = loanWatapDetailsService.partialUpdate(loanWatapDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanWatapDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-watap-details} : get all the loanWatapDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanWatapDetails in body.
     */
    @GetMapping("/loan-watap-details")
    public ResponseEntity<List<LoanWatapDetailsDTO>> getAllLoanWatapDetails(
        LoanWatapDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanWatapDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<LoanWatapDetailsDTO> page = loanWatapDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-watap-details/count} : count all the loanWatapDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/loan-watap-details/count")
    public ResponseEntity<Long> countLoanWatapDetails(LoanWatapDetailsCriteria criteria) {
        log.debug("REST request to count LoanWatapDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(loanWatapDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /loan-watap-details/:id} : get the "id" loanWatapDetails.
     *
     * @param id the id of the loanWatapDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanWatapDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-watap-details/{id}")
    public ResponseEntity<LoanWatapDetailsDTO> getLoanWatapDetails(@PathVariable Long id) {
        log.debug("REST request to get LoanWatapDetails : {}", id);
        Optional<LoanWatapDetailsDTO> loanWatapDetailsDTO = loanWatapDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanWatapDetailsDTO);
    }

    /**
     * {@code DELETE  /loan-watap-details/:id} : delete the "id" loanWatapDetails.
     *
     * @param id the id of the loanWatapDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-watap-details/{id}")
    public ResponseEntity<Void> deleteLoanWatapDetails(@PathVariable Long id) {
        log.debug("REST request to delete LoanWatapDetails : {}", id);
        loanWatapDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

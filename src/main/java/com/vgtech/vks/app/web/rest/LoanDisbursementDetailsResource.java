package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.LoanDisbursementDetailsRepository;
import com.vgtech.vks.app.service.LoanDisbursementDetailsQueryService;
import com.vgtech.vks.app.service.LoanDisbursementDetailsService;
import com.vgtech.vks.app.service.criteria.LoanDisbursementDetailsCriteria;
import com.vgtech.vks.app.service.dto.LoanDisbursementDetailsDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.LoanDisbursementDetails}.
 */
@RestController
@RequestMapping("/api")
public class LoanDisbursementDetailsResource {

    private final Logger log = LoggerFactory.getLogger(LoanDisbursementDetailsResource.class);

    private static final String ENTITY_NAME = "loanDisbursementDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanDisbursementDetailsService loanDisbursementDetailsService;

    private final LoanDisbursementDetailsRepository loanDisbursementDetailsRepository;

    private final LoanDisbursementDetailsQueryService loanDisbursementDetailsQueryService;

    public LoanDisbursementDetailsResource(
        LoanDisbursementDetailsService loanDisbursementDetailsService,
        LoanDisbursementDetailsRepository loanDisbursementDetailsRepository,
        LoanDisbursementDetailsQueryService loanDisbursementDetailsQueryService
    ) {
        this.loanDisbursementDetailsService = loanDisbursementDetailsService;
        this.loanDisbursementDetailsRepository = loanDisbursementDetailsRepository;
        this.loanDisbursementDetailsQueryService = loanDisbursementDetailsQueryService;
    }

    /**
     * {@code POST  /loan-disbursement-details} : Create a new loanDisbursementDetails.
     *
     * @param loanDisbursementDetailsDTO the loanDisbursementDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanDisbursementDetailsDTO, or with status {@code 400 (Bad Request)} if the loanDisbursementDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-disbursement-details")
    public ResponseEntity<LoanDisbursementDetailsDTO> createLoanDisbursementDetails(
        @RequestBody LoanDisbursementDetailsDTO loanDisbursementDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save LoanDisbursementDetails : {}", loanDisbursementDetailsDTO);
        if (loanDisbursementDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanDisbursementDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanDisbursementDetailsDTO result = loanDisbursementDetailsService.save(loanDisbursementDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/loan-disbursement-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-disbursement-details/:id} : Updates an existing loanDisbursementDetails.
     *
     * @param id the id of the loanDisbursementDetailsDTO to save.
     * @param loanDisbursementDetailsDTO the loanDisbursementDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDisbursementDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the loanDisbursementDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanDisbursementDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-disbursement-details/{id}")
    public ResponseEntity<LoanDisbursementDetailsDTO> updateLoanDisbursementDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDisbursementDetailsDTO loanDisbursementDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanDisbursementDetails : {}, {}", id, loanDisbursementDetailsDTO);
        if (loanDisbursementDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDisbursementDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDisbursementDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanDisbursementDetailsDTO result = loanDisbursementDetailsService.update(loanDisbursementDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDisbursementDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-disbursement-details/:id} : Partial updates given fields of an existing loanDisbursementDetails, field will ignore if it is null
     *
     * @param id the id of the loanDisbursementDetailsDTO to save.
     * @param loanDisbursementDetailsDTO the loanDisbursementDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDisbursementDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the loanDisbursementDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loanDisbursementDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanDisbursementDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-disbursement-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanDisbursementDetailsDTO> partialUpdateLoanDisbursementDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDisbursementDetailsDTO loanDisbursementDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanDisbursementDetails partially : {}, {}", id, loanDisbursementDetailsDTO);
        if (loanDisbursementDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDisbursementDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDisbursementDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanDisbursementDetailsDTO> result = loanDisbursementDetailsService.partialUpdate(loanDisbursementDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDisbursementDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-disbursement-details} : get all the loanDisbursementDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanDisbursementDetails in body.
     */
    @GetMapping("/loan-disbursement-details")
    public ResponseEntity<List<LoanDisbursementDetailsDTO>> getAllLoanDisbursementDetails(
        LoanDisbursementDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanDisbursementDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<LoanDisbursementDetailsDTO> page = loanDisbursementDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-disbursement-details/count} : count all the loanDisbursementDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/loan-disbursement-details/count")
    public ResponseEntity<Long> countLoanDisbursementDetails(LoanDisbursementDetailsCriteria criteria) {
        log.debug("REST request to count LoanDisbursementDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(loanDisbursementDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /loan-disbursement-details/:id} : get the "id" loanDisbursementDetails.
     *
     * @param id the id of the loanDisbursementDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanDisbursementDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-disbursement-details/{id}")
    public ResponseEntity<LoanDisbursementDetailsDTO> getLoanDisbursementDetails(@PathVariable Long id) {
        log.debug("REST request to get LoanDisbursementDetails : {}", id);
        Optional<LoanDisbursementDetailsDTO> loanDisbursementDetailsDTO = loanDisbursementDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanDisbursementDetailsDTO);
    }

    /**
     * {@code DELETE  /loan-disbursement-details/:id} : delete the "id" loanDisbursementDetails.
     *
     * @param id the id of the loanDisbursementDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-disbursement-details/{id}")
    public ResponseEntity<Void> deleteLoanDisbursementDetails(@PathVariable Long id) {
        log.debug("REST request to delete LoanDisbursementDetails : {}", id);
        loanDisbursementDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

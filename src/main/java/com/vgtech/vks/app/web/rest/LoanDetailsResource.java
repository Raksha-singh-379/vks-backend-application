package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.LoanDetailsRepository;
import com.vgtech.vks.app.service.LoanDetailsQueryService;
import com.vgtech.vks.app.service.LoanDetailsService;
import com.vgtech.vks.app.service.criteria.LoanDetailsCriteria;
import com.vgtech.vks.app.service.dto.LoanDetailsDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.LoanDetails}.
 */
@RestController
@RequestMapping("/api")
public class LoanDetailsResource {

    private final Logger log = LoggerFactory.getLogger(LoanDetailsResource.class);

    private static final String ENTITY_NAME = "loanDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanDetailsService loanDetailsService;

    private final LoanDetailsRepository loanDetailsRepository;

    private final LoanDetailsQueryService loanDetailsQueryService;

    public LoanDetailsResource(
        LoanDetailsService loanDetailsService,
        LoanDetailsRepository loanDetailsRepository,
        LoanDetailsQueryService loanDetailsQueryService
    ) {
        this.loanDetailsService = loanDetailsService;
        this.loanDetailsRepository = loanDetailsRepository;
        this.loanDetailsQueryService = loanDetailsQueryService;
    }

    /**
     * {@code POST  /loan-details} : Create a new loanDetails.
     *
     * @param loanDetailsDTO the loanDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanDetailsDTO, or with status {@code 400 (Bad Request)} if the loanDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-details")
    public ResponseEntity<LoanDetailsDTO> createLoanDetails(@RequestBody LoanDetailsDTO loanDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save LoanDetails : {}", loanDetailsDTO);
        if (loanDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanDetailsDTO result = loanDetailsService.save(loanDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/loan-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-details/:id} : Updates an existing loanDetails.
     *
     * @param id the id of the loanDetailsDTO to save.
     * @param loanDetailsDTO the loanDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the loanDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-details/{id}")
    public ResponseEntity<LoanDetailsDTO> updateLoanDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDetailsDTO loanDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanDetails : {}, {}", id, loanDetailsDTO);
        if (loanDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanDetailsDTO result = loanDetailsService.update(loanDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-details/:id} : Partial updates given fields of an existing loanDetails, field will ignore if it is null
     *
     * @param id the id of the loanDetailsDTO to save.
     * @param loanDetailsDTO the loanDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the loanDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loanDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanDetailsDTO> partialUpdateLoanDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDetailsDTO loanDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanDetails partially : {}, {}", id, loanDetailsDTO);
        if (loanDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanDetailsDTO> result = loanDetailsService.partialUpdate(loanDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-details} : get all the loanDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanDetails in body.
     */
    @GetMapping("/loan-details")
    public ResponseEntity<List<LoanDetailsDTO>> getAllLoanDetails(
        LoanDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<LoanDetailsDTO> page = loanDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-details/count} : count all the loanDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/loan-details/count")
    public ResponseEntity<Long> countLoanDetails(LoanDetailsCriteria criteria) {
        log.debug("REST request to count LoanDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(loanDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /loan-details/:id} : get the "id" loanDetails.
     *
     * @param id the id of the loanDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-details/{id}")
    public ResponseEntity<LoanDetailsDTO> getLoanDetails(@PathVariable Long id) {
        log.debug("REST request to get LoanDetails : {}", id);
        Optional<LoanDetailsDTO> loanDetailsDTO = loanDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanDetailsDTO);
    }

    /**
     * {@code DELETE  /loan-details/:id} : delete the "id" loanDetails.
     *
     * @param id the id of the loanDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-details/{id}")
    public ResponseEntity<Void> deleteLoanDetails(@PathVariable Long id) {
        log.debug("REST request to delete LoanDetails : {}", id);
        loanDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

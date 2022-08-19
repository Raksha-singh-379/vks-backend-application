package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.LoanDemandRepository;
import com.vgtech.vks.app.service.LoanDemandQueryService;
import com.vgtech.vks.app.service.LoanDemandService;
import com.vgtech.vks.app.service.criteria.LoanDemandCriteria;
import com.vgtech.vks.app.service.dto.LoanDemandDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.LoanDemand}.
 */
@RestController
@RequestMapping("/api")
public class LoanDemandResource {

    private final Logger log = LoggerFactory.getLogger(LoanDemandResource.class);

    private static final String ENTITY_NAME = "loanDemand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LoanDemandService loanDemandService;

    private final LoanDemandRepository loanDemandRepository;

    private final LoanDemandQueryService loanDemandQueryService;

    public LoanDemandResource(
        LoanDemandService loanDemandService,
        LoanDemandRepository loanDemandRepository,
        LoanDemandQueryService loanDemandQueryService
    ) {
        this.loanDemandService = loanDemandService;
        this.loanDemandRepository = loanDemandRepository;
        this.loanDemandQueryService = loanDemandQueryService;
    }

    /**
     * {@code POST  /loan-demands} : Create a new loanDemand.
     *
     * @param loanDemandDTO the loanDemandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanDemandDTO, or with status {@code 400 (Bad Request)} if the loanDemand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-demands")
    public ResponseEntity<LoanDemandDTO> createLoanDemand(@RequestBody LoanDemandDTO loanDemandDTO) throws URISyntaxException {
        log.debug("REST request to save LoanDemand : {}", loanDemandDTO);
        if (loanDemandDTO.getId() != null) {
            throw new BadRequestAlertException("A new loanDemand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanDemandDTO result = loanDemandService.save(loanDemandDTO);
        return ResponseEntity
            .created(new URI("/api/loan-demands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /loan-demands/:id} : Updates an existing loanDemand.
     *
     * @param id the id of the loanDemandDTO to save.
     * @param loanDemandDTO the loanDemandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDemandDTO,
     * or with status {@code 400 (Bad Request)} if the loanDemandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanDemandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-demands/{id}")
    public ResponseEntity<LoanDemandDTO> updateLoanDemand(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDemandDTO loanDemandDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LoanDemand : {}, {}", id, loanDemandDTO);
        if (loanDemandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDemandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDemandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanDemandDTO result = loanDemandService.update(loanDemandDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDemandDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /loan-demands/:id} : Partial updates given fields of an existing loanDemand, field will ignore if it is null
     *
     * @param id the id of the loanDemandDTO to save.
     * @param loanDemandDTO the loanDemandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDemandDTO,
     * or with status {@code 400 (Bad Request)} if the loanDemandDTO is not valid,
     * or with status {@code 404 (Not Found)} if the loanDemandDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the loanDemandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/loan-demands/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LoanDemandDTO> partialUpdateLoanDemand(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LoanDemandDTO loanDemandDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LoanDemand partially : {}, {}", id, loanDemandDTO);
        if (loanDemandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDemandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanDemandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LoanDemandDTO> result = loanDemandService.partialUpdate(loanDemandDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, loanDemandDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /loan-demands} : get all the loanDemands.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanDemands in body.
     */
    @GetMapping("/loan-demands")
    public ResponseEntity<List<LoanDemandDTO>> getAllLoanDemands(
        LoanDemandCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get LoanDemands by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<LoanDemandDTO> page = loanDemandQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /loan-demands/count} : count all the loanDemands.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/loan-demands/count")
    public ResponseEntity<Long> countLoanDemands(LoanDemandCriteria criteria) {
        log.debug("REST request to count LoanDemands by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(loanDemandQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /loan-demands/:id} : get the "id" loanDemand.
     *
     * @param id the id of the loanDemandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanDemandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-demands/{id}")
    public ResponseEntity<LoanDemandDTO> getLoanDemand(@PathVariable Long id) {
        log.debug("REST request to get LoanDemand : {}", id);
        Optional<LoanDemandDTO> loanDemandDTO = loanDemandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(loanDemandDTO);
    }

    /**
     * {@code DELETE  /loan-demands/:id} : delete the "id" loanDemand.
     *
     * @param id the id of the loanDemandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-demands/{id}")
    public ResponseEntity<Void> deleteLoanDemand(@PathVariable Long id) {
        log.debug("REST request to delete LoanDemand : {}", id);
        loanDemandService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

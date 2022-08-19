package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.BankDhoranDetailsRepository;
import com.vgtech.vks.app.service.BankDhoranDetailsQueryService;
import com.vgtech.vks.app.service.BankDhoranDetailsService;
import com.vgtech.vks.app.service.criteria.BankDhoranDetailsCriteria;
import com.vgtech.vks.app.service.dto.BankDhoranDetailsDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.BankDhoranDetails}.
 */
@RestController
@RequestMapping("/api")
public class BankDhoranDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BankDhoranDetailsResource.class);

    private static final String ENTITY_NAME = "bankDhoranDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankDhoranDetailsService bankDhoranDetailsService;

    private final BankDhoranDetailsRepository bankDhoranDetailsRepository;

    private final BankDhoranDetailsQueryService bankDhoranDetailsQueryService;

    public BankDhoranDetailsResource(
        BankDhoranDetailsService bankDhoranDetailsService,
        BankDhoranDetailsRepository bankDhoranDetailsRepository,
        BankDhoranDetailsQueryService bankDhoranDetailsQueryService
    ) {
        this.bankDhoranDetailsService = bankDhoranDetailsService;
        this.bankDhoranDetailsRepository = bankDhoranDetailsRepository;
        this.bankDhoranDetailsQueryService = bankDhoranDetailsQueryService;
    }

    /**
     * {@code POST  /bank-dhoran-details} : Create a new bankDhoranDetails.
     *
     * @param bankDhoranDetailsDTO the bankDhoranDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankDhoranDetailsDTO, or with status {@code 400 (Bad Request)} if the bankDhoranDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-dhoran-details")
    public ResponseEntity<BankDhoranDetailsDTO> createBankDhoranDetails(@RequestBody BankDhoranDetailsDTO bankDhoranDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save BankDhoranDetails : {}", bankDhoranDetailsDTO);
        if (bankDhoranDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankDhoranDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankDhoranDetailsDTO result = bankDhoranDetailsService.save(bankDhoranDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/bank-dhoran-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-dhoran-details/:id} : Updates an existing bankDhoranDetails.
     *
     * @param id the id of the bankDhoranDetailsDTO to save.
     * @param bankDhoranDetailsDTO the bankDhoranDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankDhoranDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the bankDhoranDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankDhoranDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-dhoran-details/{id}")
    public ResponseEntity<BankDhoranDetailsDTO> updateBankDhoranDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankDhoranDetailsDTO bankDhoranDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankDhoranDetails : {}, {}", id, bankDhoranDetailsDTO);
        if (bankDhoranDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankDhoranDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankDhoranDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankDhoranDetailsDTO result = bankDhoranDetailsService.update(bankDhoranDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankDhoranDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-dhoran-details/:id} : Partial updates given fields of an existing bankDhoranDetails, field will ignore if it is null
     *
     * @param id the id of the bankDhoranDetailsDTO to save.
     * @param bankDhoranDetailsDTO the bankDhoranDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankDhoranDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the bankDhoranDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankDhoranDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankDhoranDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-dhoran-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BankDhoranDetailsDTO> partialUpdateBankDhoranDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BankDhoranDetailsDTO bankDhoranDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankDhoranDetails partially : {}, {}", id, bankDhoranDetailsDTO);
        if (bankDhoranDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankDhoranDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankDhoranDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankDhoranDetailsDTO> result = bankDhoranDetailsService.partialUpdate(bankDhoranDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankDhoranDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-dhoran-details} : get all the bankDhoranDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankDhoranDetails in body.
     */
    @GetMapping("/bank-dhoran-details")
    public ResponseEntity<List<BankDhoranDetailsDTO>> getAllBankDhoranDetails(
        BankDhoranDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BankDhoranDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<BankDhoranDetailsDTO> page = bankDhoranDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bank-dhoran-details/count} : count all the bankDhoranDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bank-dhoran-details/count")
    public ResponseEntity<Long> countBankDhoranDetails(BankDhoranDetailsCriteria criteria) {
        log.debug("REST request to count BankDhoranDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(bankDhoranDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bank-dhoran-details/:id} : get the "id" bankDhoranDetails.
     *
     * @param id the id of the bankDhoranDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankDhoranDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-dhoran-details/{id}")
    public ResponseEntity<BankDhoranDetailsDTO> getBankDhoranDetails(@PathVariable Long id) {
        log.debug("REST request to get BankDhoranDetails : {}", id);
        Optional<BankDhoranDetailsDTO> bankDhoranDetailsDTO = bankDhoranDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankDhoranDetailsDTO);
    }

    /**
     * {@code DELETE  /bank-dhoran-details/:id} : delete the "id" bankDhoranDetails.
     *
     * @param id the id of the bankDhoranDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-dhoran-details/{id}")
    public ResponseEntity<Void> deleteBankDhoranDetails(@PathVariable Long id) {
        log.debug("REST request to delete BankDhoranDetails : {}", id);
        bankDhoranDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.AccountMappingRepository;
import com.vgtech.vks.app.service.AccountMappingQueryService;
import com.vgtech.vks.app.service.AccountMappingService;
import com.vgtech.vks.app.service.criteria.AccountMappingCriteria;
import com.vgtech.vks.app.service.dto.AccountMappingDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.AccountMapping}.
 */
@RestController
@RequestMapping("/api")
public class AccountMappingResource {

    private final Logger log = LoggerFactory.getLogger(AccountMappingResource.class);

    private static final String ENTITY_NAME = "accountMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountMappingService accountMappingService;

    private final AccountMappingRepository accountMappingRepository;

    private final AccountMappingQueryService accountMappingQueryService;

    public AccountMappingResource(
        AccountMappingService accountMappingService,
        AccountMappingRepository accountMappingRepository,
        AccountMappingQueryService accountMappingQueryService
    ) {
        this.accountMappingService = accountMappingService;
        this.accountMappingRepository = accountMappingRepository;
        this.accountMappingQueryService = accountMappingQueryService;
    }

    /**
     * {@code POST  /account-mappings} : Create a new accountMapping.
     *
     * @param accountMappingDTO the accountMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accountMappingDTO, or with status {@code 400 (Bad Request)} if the accountMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/account-mappings")
    public ResponseEntity<AccountMappingDTO> createAccountMapping(@RequestBody AccountMappingDTO accountMappingDTO)
        throws URISyntaxException {
        log.debug("REST request to save AccountMapping : {}", accountMappingDTO);
        if (accountMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new accountMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AccountMappingDTO result = accountMappingService.save(accountMappingDTO);
        return ResponseEntity
            .created(new URI("/api/account-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /account-mappings/:id} : Updates an existing accountMapping.
     *
     * @param id the id of the accountMappingDTO to save.
     * @param accountMappingDTO the accountMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountMappingDTO,
     * or with status {@code 400 (Bad Request)} if the accountMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accountMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/account-mappings/{id}")
    public ResponseEntity<AccountMappingDTO> updateAccountMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountMappingDTO accountMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AccountMapping : {}, {}", id, accountMappingDTO);
        if (accountMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AccountMappingDTO result = accountMappingService.update(accountMappingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /account-mappings/:id} : Partial updates given fields of an existing accountMapping, field will ignore if it is null
     *
     * @param id the id of the accountMappingDTO to save.
     * @param accountMappingDTO the accountMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accountMappingDTO,
     * or with status {@code 400 (Bad Request)} if the accountMappingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accountMappingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accountMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/account-mappings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccountMappingDTO> partialUpdateAccountMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AccountMappingDTO accountMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AccountMapping partially : {}, {}", id, accountMappingDTO);
        if (accountMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accountMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accountMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccountMappingDTO> result = accountMappingService.partialUpdate(accountMappingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accountMappingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /account-mappings} : get all the accountMappings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountMappings in body.
     */
    @GetMapping("/account-mappings")
    public ResponseEntity<List<AccountMappingDTO>> getAllAccountMappings(
        AccountMappingCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AccountMappings by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<AccountMappingDTO> page = accountMappingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /account-mappings/count} : count all the accountMappings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/account-mappings/count")
    public ResponseEntity<Long> countAccountMappings(AccountMappingCriteria criteria) {
        log.debug("REST request to count AccountMappings by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(accountMappingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /account-mappings/:id} : get the "id" accountMapping.
     *
     * @param id the id of the accountMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accountMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/account-mappings/{id}")
    public ResponseEntity<AccountMappingDTO> getAccountMapping(@PathVariable Long id) {
        log.debug("REST request to get AccountMapping : {}", id);
        Optional<AccountMappingDTO> accountMappingDTO = accountMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accountMappingDTO);
    }

    /**
     * {@code DELETE  /account-mappings/:id} : delete the "id" accountMapping.
     *
     * @param id the id of the accountMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/account-mappings/{id}")
    public ResponseEntity<Void> deleteAccountMapping(@PathVariable Long id) {
        log.debug("REST request to delete AccountMapping : {}", id);
        accountMappingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

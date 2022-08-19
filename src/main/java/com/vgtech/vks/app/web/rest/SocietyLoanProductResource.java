package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.SocietyLoanProductRepository;
import com.vgtech.vks.app.service.SocietyLoanProductQueryService;
import com.vgtech.vks.app.service.SocietyLoanProductService;
import com.vgtech.vks.app.service.criteria.SocietyLoanProductCriteria;
import com.vgtech.vks.app.service.dto.SocietyLoanProductDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.SocietyLoanProduct}.
 */
@RestController
@RequestMapping("/api")
public class SocietyLoanProductResource {

    private final Logger log = LoggerFactory.getLogger(SocietyLoanProductResource.class);

    private static final String ENTITY_NAME = "societyLoanProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocietyLoanProductService societyLoanProductService;

    private final SocietyLoanProductRepository societyLoanProductRepository;

    private final SocietyLoanProductQueryService societyLoanProductQueryService;

    public SocietyLoanProductResource(
        SocietyLoanProductService societyLoanProductService,
        SocietyLoanProductRepository societyLoanProductRepository,
        SocietyLoanProductQueryService societyLoanProductQueryService
    ) {
        this.societyLoanProductService = societyLoanProductService;
        this.societyLoanProductRepository = societyLoanProductRepository;
        this.societyLoanProductQueryService = societyLoanProductQueryService;
    }

    /**
     * {@code POST  /society-loan-products} : Create a new societyLoanProduct.
     *
     * @param societyLoanProductDTO the societyLoanProductDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societyLoanProductDTO, or with status {@code 400 (Bad Request)} if the societyLoanProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/society-loan-products")
    public ResponseEntity<SocietyLoanProductDTO> createSocietyLoanProduct(@RequestBody SocietyLoanProductDTO societyLoanProductDTO)
        throws URISyntaxException {
        log.debug("REST request to save SocietyLoanProduct : {}", societyLoanProductDTO);
        if (societyLoanProductDTO.getId() != null) {
            throw new BadRequestAlertException("A new societyLoanProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocietyLoanProductDTO result = societyLoanProductService.save(societyLoanProductDTO);
        return ResponseEntity
            .created(new URI("/api/society-loan-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /society-loan-products/:id} : Updates an existing societyLoanProduct.
     *
     * @param id the id of the societyLoanProductDTO to save.
     * @param societyLoanProductDTO the societyLoanProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyLoanProductDTO,
     * or with status {@code 400 (Bad Request)} if the societyLoanProductDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societyLoanProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/society-loan-products/{id}")
    public ResponseEntity<SocietyLoanProductDTO> updateSocietyLoanProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyLoanProductDTO societyLoanProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocietyLoanProduct : {}, {}", id, societyLoanProductDTO);
        if (societyLoanProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyLoanProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyLoanProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocietyLoanProductDTO result = societyLoanProductService.update(societyLoanProductDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyLoanProductDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /society-loan-products/:id} : Partial updates given fields of an existing societyLoanProduct, field will ignore if it is null
     *
     * @param id the id of the societyLoanProductDTO to save.
     * @param societyLoanProductDTO the societyLoanProductDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyLoanProductDTO,
     * or with status {@code 400 (Bad Request)} if the societyLoanProductDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societyLoanProductDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societyLoanProductDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/society-loan-products/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocietyLoanProductDTO> partialUpdateSocietyLoanProduct(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyLoanProductDTO societyLoanProductDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocietyLoanProduct partially : {}, {}", id, societyLoanProductDTO);
        if (societyLoanProductDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyLoanProductDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyLoanProductRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocietyLoanProductDTO> result = societyLoanProductService.partialUpdate(societyLoanProductDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyLoanProductDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /society-loan-products} : get all the societyLoanProducts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societyLoanProducts in body.
     */
    @GetMapping("/society-loan-products")
    public ResponseEntity<List<SocietyLoanProductDTO>> getAllSocietyLoanProducts(
        SocietyLoanProductCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocietyLoanProducts by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<SocietyLoanProductDTO> page = societyLoanProductQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /society-loan-products/count} : count all the societyLoanProducts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/society-loan-products/count")
    public ResponseEntity<Long> countSocietyLoanProducts(SocietyLoanProductCriteria criteria) {
        log.debug("REST request to count SocietyLoanProducts by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(societyLoanProductQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /society-loan-products/:id} : get the "id" societyLoanProduct.
     *
     * @param id the id of the societyLoanProductDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societyLoanProductDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/society-loan-products/{id}")
    public ResponseEntity<SocietyLoanProductDTO> getSocietyLoanProduct(@PathVariable Long id) {
        log.debug("REST request to get SocietyLoanProduct : {}", id);
        Optional<SocietyLoanProductDTO> societyLoanProductDTO = societyLoanProductService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societyLoanProductDTO);
    }

    /**
     * {@code DELETE  /society-loan-products/:id} : delete the "id" societyLoanProduct.
     *
     * @param id the id of the societyLoanProductDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/society-loan-products/{id}")
    public ResponseEntity<Void> deleteSocietyLoanProduct(@PathVariable Long id) {
        log.debug("REST request to delete SocietyLoanProduct : {}", id);
        societyLoanProductService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

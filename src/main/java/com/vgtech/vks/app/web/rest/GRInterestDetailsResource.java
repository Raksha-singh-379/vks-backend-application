package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.GRInterestDetailsRepository;
import com.vgtech.vks.app.service.GRInterestDetailsQueryService;
import com.vgtech.vks.app.service.GRInterestDetailsService;
import com.vgtech.vks.app.service.criteria.GRInterestDetailsCriteria;
import com.vgtech.vks.app.service.dto.GRInterestDetailsDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.GRInterestDetails}.
 */
@RestController
@RequestMapping("/api")
public class GRInterestDetailsResource {

    private final Logger log = LoggerFactory.getLogger(GRInterestDetailsResource.class);

    private static final String ENTITY_NAME = "gRInterestDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GRInterestDetailsService gRInterestDetailsService;

    private final GRInterestDetailsRepository gRInterestDetailsRepository;

    private final GRInterestDetailsQueryService gRInterestDetailsQueryService;

    public GRInterestDetailsResource(
        GRInterestDetailsService gRInterestDetailsService,
        GRInterestDetailsRepository gRInterestDetailsRepository,
        GRInterestDetailsQueryService gRInterestDetailsQueryService
    ) {
        this.gRInterestDetailsService = gRInterestDetailsService;
        this.gRInterestDetailsRepository = gRInterestDetailsRepository;
        this.gRInterestDetailsQueryService = gRInterestDetailsQueryService;
    }

    /**
     * {@code POST  /gr-interest-details} : Create a new gRInterestDetails.
     *
     * @param gRInterestDetailsDTO the gRInterestDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gRInterestDetailsDTO, or with status {@code 400 (Bad Request)} if the gRInterestDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gr-interest-details")
    public ResponseEntity<GRInterestDetailsDTO> createGRInterestDetails(@RequestBody GRInterestDetailsDTO gRInterestDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save GRInterestDetails : {}", gRInterestDetailsDTO);
        if (gRInterestDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new gRInterestDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GRInterestDetailsDTO result = gRInterestDetailsService.save(gRInterestDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/gr-interest-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gr-interest-details/:id} : Updates an existing gRInterestDetails.
     *
     * @param id the id of the gRInterestDetailsDTO to save.
     * @param gRInterestDetailsDTO the gRInterestDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gRInterestDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the gRInterestDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gRInterestDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gr-interest-details/{id}")
    public ResponseEntity<GRInterestDetailsDTO> updateGRInterestDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GRInterestDetailsDTO gRInterestDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GRInterestDetails : {}, {}", id, gRInterestDetailsDTO);
        if (gRInterestDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gRInterestDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gRInterestDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GRInterestDetailsDTO result = gRInterestDetailsService.update(gRInterestDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gRInterestDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gr-interest-details/:id} : Partial updates given fields of an existing gRInterestDetails, field will ignore if it is null
     *
     * @param id the id of the gRInterestDetailsDTO to save.
     * @param gRInterestDetailsDTO the gRInterestDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gRInterestDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the gRInterestDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gRInterestDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gRInterestDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gr-interest-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GRInterestDetailsDTO> partialUpdateGRInterestDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody GRInterestDetailsDTO gRInterestDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GRInterestDetails partially : {}, {}", id, gRInterestDetailsDTO);
        if (gRInterestDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gRInterestDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gRInterestDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GRInterestDetailsDTO> result = gRInterestDetailsService.partialUpdate(gRInterestDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gRInterestDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gr-interest-details} : get all the gRInterestDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gRInterestDetails in body.
     */
    @GetMapping("/gr-interest-details")
    public ResponseEntity<List<GRInterestDetailsDTO>> getAllGRInterestDetails(
        GRInterestDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get GRInterestDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<GRInterestDetailsDTO> page = gRInterestDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gr-interest-details/count} : count all the gRInterestDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/gr-interest-details/count")
    public ResponseEntity<Long> countGRInterestDetails(GRInterestDetailsCriteria criteria) {
        log.debug("REST request to count GRInterestDetails by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(gRInterestDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /gr-interest-details/:id} : get the "id" gRInterestDetails.
     *
     * @param id the id of the gRInterestDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gRInterestDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gr-interest-details/{id}")
    public ResponseEntity<GRInterestDetailsDTO> getGRInterestDetails(@PathVariable Long id) {
        log.debug("REST request to get GRInterestDetails : {}", id);
        Optional<GRInterestDetailsDTO> gRInterestDetailsDTO = gRInterestDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gRInterestDetailsDTO);
    }

    /**
     * {@code DELETE  /gr-interest-details/:id} : delete the "id" gRInterestDetails.
     *
     * @param id the id of the gRInterestDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gr-interest-details/{id}")
    public ResponseEntity<Void> deleteGRInterestDetails(@PathVariable Long id) {
        log.debug("REST request to delete GRInterestDetails : {}", id);
        gRInterestDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

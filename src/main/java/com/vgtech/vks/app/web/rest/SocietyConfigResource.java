package com.vgtech.vks.app.web.rest;

import com.vgtech.vks.app.repository.SocietyConfigRepository;
import com.vgtech.vks.app.service.SocietyConfigQueryService;
import com.vgtech.vks.app.service.SocietyConfigService;
import com.vgtech.vks.app.service.criteria.SocietyConfigCriteria;
import com.vgtech.vks.app.service.dto.SocietyConfigDTO;
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
 * REST controller for managing {@link com.vgtech.vks.app.domain.SocietyConfig}.
 */
@RestController
@RequestMapping("/api")
public class SocietyConfigResource {

    private final Logger log = LoggerFactory.getLogger(SocietyConfigResource.class);

    private static final String ENTITY_NAME = "societyConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocietyConfigService societyConfigService;

    private final SocietyConfigRepository societyConfigRepository;

    private final SocietyConfigQueryService societyConfigQueryService;

    public SocietyConfigResource(
        SocietyConfigService societyConfigService,
        SocietyConfigRepository societyConfigRepository,
        SocietyConfigQueryService societyConfigQueryService
    ) {
        this.societyConfigService = societyConfigService;
        this.societyConfigRepository = societyConfigRepository;
        this.societyConfigQueryService = societyConfigQueryService;
    }

    /**
     * {@code POST  /society-configs} : Create a new societyConfig.
     *
     * @param societyConfigDTO the societyConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societyConfigDTO, or with status {@code 400 (Bad Request)} if the societyConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/society-configs")
    public ResponseEntity<SocietyConfigDTO> createSocietyConfig(@RequestBody SocietyConfigDTO societyConfigDTO) throws URISyntaxException {
        log.debug("REST request to save SocietyConfig : {}", societyConfigDTO);
        if (societyConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new societyConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocietyConfigDTO result = societyConfigService.save(societyConfigDTO);
        return ResponseEntity
            .created(new URI("/api/society-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /society-configs/:id} : Updates an existing societyConfig.
     *
     * @param id the id of the societyConfigDTO to save.
     * @param societyConfigDTO the societyConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyConfigDTO,
     * or with status {@code 400 (Bad Request)} if the societyConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societyConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/society-configs/{id}")
    public ResponseEntity<SocietyConfigDTO> updateSocietyConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyConfigDTO societyConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocietyConfig : {}, {}", id, societyConfigDTO);
        if (societyConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocietyConfigDTO result = societyConfigService.update(societyConfigDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /society-configs/:id} : Partial updates given fields of an existing societyConfig, field will ignore if it is null
     *
     * @param id the id of the societyConfigDTO to save.
     * @param societyConfigDTO the societyConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societyConfigDTO,
     * or with status {@code 400 (Bad Request)} if the societyConfigDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societyConfigDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societyConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/society-configs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocietyConfigDTO> partialUpdateSocietyConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocietyConfigDTO societyConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocietyConfig partially : {}, {}", id, societyConfigDTO);
        if (societyConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societyConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societyConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocietyConfigDTO> result = societyConfigService.partialUpdate(societyConfigDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societyConfigDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /society-configs} : get all the societyConfigs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societyConfigs in body.
     */
    @GetMapping("/society-configs")
    public ResponseEntity<List<SocietyConfigDTO>> getAllSocietyConfigs(
        SocietyConfigCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SocietyConfigs by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        Page<SocietyConfigDTO> page = societyConfigQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /society-configs/count} : count all the societyConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/society-configs/count")
    public ResponseEntity<Long> countSocietyConfigs(SocietyConfigCriteria criteria) {
        log.debug("REST request to count SocietyConfigs by criteria: {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        return ResponseEntity.ok().body(societyConfigQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /society-configs/:id} : get the "id" societyConfig.
     *
     * @param id the id of the societyConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societyConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/society-configs/{id}")
    public ResponseEntity<SocietyConfigDTO> getSocietyConfig(@PathVariable Long id) {
        log.debug("REST request to get SocietyConfig : {}", id);
        Optional<SocietyConfigDTO> societyConfigDTO = societyConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societyConfigDTO);
    }

    /**
     * {@code DELETE  /society-configs/:id} : delete the "id" societyConfig.
     *
     * @param id the id of the societyConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/society-configs/{id}")
    public ResponseEntity<Void> deleteSocietyConfig(@PathVariable Long id) {
        log.debug("REST request to delete SocietyConfig : {}", id);
        societyConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

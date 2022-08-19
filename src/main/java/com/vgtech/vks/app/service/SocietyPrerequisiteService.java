package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.SocietyPrerequisite;
import com.vgtech.vks.app.repository.SocietyPrerequisiteRepository;
import com.vgtech.vks.app.service.dto.SocietyPrerequisiteDTO;
import com.vgtech.vks.app.service.mapper.SocietyPrerequisiteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocietyPrerequisite}.
 */
@Service
@Transactional
public class SocietyPrerequisiteService {

    private final Logger log = LoggerFactory.getLogger(SocietyPrerequisiteService.class);

    private final SocietyPrerequisiteRepository societyPrerequisiteRepository;

    private final SocietyPrerequisiteMapper societyPrerequisiteMapper;

    public SocietyPrerequisiteService(
        SocietyPrerequisiteRepository societyPrerequisiteRepository,
        SocietyPrerequisiteMapper societyPrerequisiteMapper
    ) {
        this.societyPrerequisiteRepository = societyPrerequisiteRepository;
        this.societyPrerequisiteMapper = societyPrerequisiteMapper;
    }

    /**
     * Save a societyPrerequisite.
     *
     * @param societyPrerequisiteDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyPrerequisiteDTO save(SocietyPrerequisiteDTO societyPrerequisiteDTO) {
        log.debug("Request to save SocietyPrerequisite : {}", societyPrerequisiteDTO);
        SocietyPrerequisite societyPrerequisite = societyPrerequisiteMapper.toEntity(societyPrerequisiteDTO);
        societyPrerequisite = societyPrerequisiteRepository.save(societyPrerequisite);
        return societyPrerequisiteMapper.toDto(societyPrerequisite);
    }

    /**
     * Update a societyPrerequisite.
     *
     * @param societyPrerequisiteDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyPrerequisiteDTO update(SocietyPrerequisiteDTO societyPrerequisiteDTO) {
        log.debug("Request to save SocietyPrerequisite : {}", societyPrerequisiteDTO);
        SocietyPrerequisite societyPrerequisite = societyPrerequisiteMapper.toEntity(societyPrerequisiteDTO);
        societyPrerequisite = societyPrerequisiteRepository.save(societyPrerequisite);
        return societyPrerequisiteMapper.toDto(societyPrerequisite);
    }

    /**
     * Partially update a societyPrerequisite.
     *
     * @param societyPrerequisiteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocietyPrerequisiteDTO> partialUpdate(SocietyPrerequisiteDTO societyPrerequisiteDTO) {
        log.debug("Request to partially update SocietyPrerequisite : {}", societyPrerequisiteDTO);

        return societyPrerequisiteRepository
            .findById(societyPrerequisiteDTO.getId())
            .map(existingSocietyPrerequisite -> {
                societyPrerequisiteMapper.partialUpdate(existingSocietyPrerequisite, societyPrerequisiteDTO);

                return existingSocietyPrerequisite;
            })
            .map(societyPrerequisiteRepository::save)
            .map(societyPrerequisiteMapper::toDto);
    }

    /**
     * Get all the societyPrerequisites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyPrerequisiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocietyPrerequisites");
        return societyPrerequisiteRepository.findAll(pageable).map(societyPrerequisiteMapper::toDto);
    }

    /**
     * Get one societyPrerequisite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocietyPrerequisiteDTO> findOne(Long id) {
        log.debug("Request to get SocietyPrerequisite : {}", id);
        return societyPrerequisiteRepository.findById(id).map(societyPrerequisiteMapper::toDto);
    }

    /**
     * Delete the societyPrerequisite by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SocietyPrerequisite : {}", id);
        societyPrerequisiteRepository.deleteById(id);
    }
}

package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.SocietyAssets;
import com.vgtech.vks.app.repository.SocietyAssetsRepository;
import com.vgtech.vks.app.service.dto.SocietyAssetsDTO;
import com.vgtech.vks.app.service.mapper.SocietyAssetsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocietyAssets}.
 */
@Service
@Transactional
public class SocietyAssetsService {

    private final Logger log = LoggerFactory.getLogger(SocietyAssetsService.class);

    private final SocietyAssetsRepository societyAssetsRepository;

    private final SocietyAssetsMapper societyAssetsMapper;

    public SocietyAssetsService(SocietyAssetsRepository societyAssetsRepository, SocietyAssetsMapper societyAssetsMapper) {
        this.societyAssetsRepository = societyAssetsRepository;
        this.societyAssetsMapper = societyAssetsMapper;
    }

    /**
     * Save a societyAssets.
     *
     * @param societyAssetsDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyAssetsDTO save(SocietyAssetsDTO societyAssetsDTO) {
        log.debug("Request to save SocietyAssets : {}", societyAssetsDTO);
        SocietyAssets societyAssets = societyAssetsMapper.toEntity(societyAssetsDTO);
        societyAssets = societyAssetsRepository.save(societyAssets);
        return societyAssetsMapper.toDto(societyAssets);
    }

    /**
     * Update a societyAssets.
     *
     * @param societyAssetsDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyAssetsDTO update(SocietyAssetsDTO societyAssetsDTO) {
        log.debug("Request to save SocietyAssets : {}", societyAssetsDTO);
        SocietyAssets societyAssets = societyAssetsMapper.toEntity(societyAssetsDTO);
        societyAssets = societyAssetsRepository.save(societyAssets);
        return societyAssetsMapper.toDto(societyAssets);
    }

    /**
     * Partially update a societyAssets.
     *
     * @param societyAssetsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocietyAssetsDTO> partialUpdate(SocietyAssetsDTO societyAssetsDTO) {
        log.debug("Request to partially update SocietyAssets : {}", societyAssetsDTO);

        return societyAssetsRepository
            .findById(societyAssetsDTO.getId())
            .map(existingSocietyAssets -> {
                societyAssetsMapper.partialUpdate(existingSocietyAssets, societyAssetsDTO);

                return existingSocietyAssets;
            })
            .map(societyAssetsRepository::save)
            .map(societyAssetsMapper::toDto);
    }

    /**
     * Get all the societyAssets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyAssetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocietyAssets");
        return societyAssetsRepository.findAll(pageable).map(societyAssetsMapper::toDto);
    }

    /**
     * Get one societyAssets by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocietyAssetsDTO> findOne(Long id) {
        log.debug("Request to get SocietyAssets : {}", id);
        return societyAssetsRepository.findById(id).map(societyAssetsMapper::toDto);
    }

    /**
     * Delete the societyAssets by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SocietyAssets : {}", id);
        societyAssetsRepository.deleteById(id);
    }
}

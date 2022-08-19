package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.SocietyAssetsData;
import com.vgtech.vks.app.repository.SocietyAssetsDataRepository;
import com.vgtech.vks.app.service.dto.SocietyAssetsDataDTO;
import com.vgtech.vks.app.service.mapper.SocietyAssetsDataMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocietyAssetsData}.
 */
@Service
@Transactional
public class SocietyAssetsDataService {

    private final Logger log = LoggerFactory.getLogger(SocietyAssetsDataService.class);

    private final SocietyAssetsDataRepository societyAssetsDataRepository;

    private final SocietyAssetsDataMapper societyAssetsDataMapper;

    public SocietyAssetsDataService(
        SocietyAssetsDataRepository societyAssetsDataRepository,
        SocietyAssetsDataMapper societyAssetsDataMapper
    ) {
        this.societyAssetsDataRepository = societyAssetsDataRepository;
        this.societyAssetsDataMapper = societyAssetsDataMapper;
    }

    /**
     * Save a societyAssetsData.
     *
     * @param societyAssetsDataDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyAssetsDataDTO save(SocietyAssetsDataDTO societyAssetsDataDTO) {
        log.debug("Request to save SocietyAssetsData : {}", societyAssetsDataDTO);
        SocietyAssetsData societyAssetsData = societyAssetsDataMapper.toEntity(societyAssetsDataDTO);
        societyAssetsData = societyAssetsDataRepository.save(societyAssetsData);
        return societyAssetsDataMapper.toDto(societyAssetsData);
    }

    /**
     * Update a societyAssetsData.
     *
     * @param societyAssetsDataDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyAssetsDataDTO update(SocietyAssetsDataDTO societyAssetsDataDTO) {
        log.debug("Request to save SocietyAssetsData : {}", societyAssetsDataDTO);
        SocietyAssetsData societyAssetsData = societyAssetsDataMapper.toEntity(societyAssetsDataDTO);
        societyAssetsData = societyAssetsDataRepository.save(societyAssetsData);
        return societyAssetsDataMapper.toDto(societyAssetsData);
    }

    /**
     * Partially update a societyAssetsData.
     *
     * @param societyAssetsDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocietyAssetsDataDTO> partialUpdate(SocietyAssetsDataDTO societyAssetsDataDTO) {
        log.debug("Request to partially update SocietyAssetsData : {}", societyAssetsDataDTO);

        return societyAssetsDataRepository
            .findById(societyAssetsDataDTO.getId())
            .map(existingSocietyAssetsData -> {
                societyAssetsDataMapper.partialUpdate(existingSocietyAssetsData, societyAssetsDataDTO);

                return existingSocietyAssetsData;
            })
            .map(societyAssetsDataRepository::save)
            .map(societyAssetsDataMapper::toDto);
    }

    /**
     * Get all the societyAssetsData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyAssetsDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocietyAssetsData");
        return societyAssetsDataRepository.findAll(pageable).map(societyAssetsDataMapper::toDto);
    }

    /**
     * Get one societyAssetsData by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocietyAssetsDataDTO> findOne(Long id) {
        log.debug("Request to get SocietyAssetsData : {}", id);
        return societyAssetsDataRepository.findById(id).map(societyAssetsDataMapper::toDto);
    }

    /**
     * Delete the societyAssetsData by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SocietyAssetsData : {}", id);
        societyAssetsDataRepository.deleteById(id);
    }
}

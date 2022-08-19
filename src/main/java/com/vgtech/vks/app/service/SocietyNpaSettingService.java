package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.SocietyNpaSetting;
import com.vgtech.vks.app.repository.SocietyNpaSettingRepository;
import com.vgtech.vks.app.service.dto.SocietyNpaSettingDTO;
import com.vgtech.vks.app.service.mapper.SocietyNpaSettingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocietyNpaSetting}.
 */
@Service
@Transactional
public class SocietyNpaSettingService {

    private final Logger log = LoggerFactory.getLogger(SocietyNpaSettingService.class);

    private final SocietyNpaSettingRepository societyNpaSettingRepository;

    private final SocietyNpaSettingMapper societyNpaSettingMapper;

    public SocietyNpaSettingService(
        SocietyNpaSettingRepository societyNpaSettingRepository,
        SocietyNpaSettingMapper societyNpaSettingMapper
    ) {
        this.societyNpaSettingRepository = societyNpaSettingRepository;
        this.societyNpaSettingMapper = societyNpaSettingMapper;
    }

    /**
     * Save a societyNpaSetting.
     *
     * @param societyNpaSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyNpaSettingDTO save(SocietyNpaSettingDTO societyNpaSettingDTO) {
        log.debug("Request to save SocietyNpaSetting : {}", societyNpaSettingDTO);
        SocietyNpaSetting societyNpaSetting = societyNpaSettingMapper.toEntity(societyNpaSettingDTO);
        societyNpaSetting = societyNpaSettingRepository.save(societyNpaSetting);
        return societyNpaSettingMapper.toDto(societyNpaSetting);
    }

    /**
     * Update a societyNpaSetting.
     *
     * @param societyNpaSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyNpaSettingDTO update(SocietyNpaSettingDTO societyNpaSettingDTO) {
        log.debug("Request to save SocietyNpaSetting : {}", societyNpaSettingDTO);
        SocietyNpaSetting societyNpaSetting = societyNpaSettingMapper.toEntity(societyNpaSettingDTO);
        societyNpaSetting = societyNpaSettingRepository.save(societyNpaSetting);
        return societyNpaSettingMapper.toDto(societyNpaSetting);
    }

    /**
     * Partially update a societyNpaSetting.
     *
     * @param societyNpaSettingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocietyNpaSettingDTO> partialUpdate(SocietyNpaSettingDTO societyNpaSettingDTO) {
        log.debug("Request to partially update SocietyNpaSetting : {}", societyNpaSettingDTO);

        return societyNpaSettingRepository
            .findById(societyNpaSettingDTO.getId())
            .map(existingSocietyNpaSetting -> {
                societyNpaSettingMapper.partialUpdate(existingSocietyNpaSetting, societyNpaSettingDTO);

                return existingSocietyNpaSetting;
            })
            .map(societyNpaSettingRepository::save)
            .map(societyNpaSettingMapper::toDto);
    }

    /**
     * Get all the societyNpaSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyNpaSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocietyNpaSettings");
        return societyNpaSettingRepository.findAll(pageable).map(societyNpaSettingMapper::toDto);
    }

    /**
     * Get one societyNpaSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocietyNpaSettingDTO> findOne(Long id) {
        log.debug("Request to get SocietyNpaSetting : {}", id);
        return societyNpaSettingRepository.findById(id).map(societyNpaSettingMapper::toDto);
    }

    /**
     * Delete the societyNpaSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SocietyNpaSetting : {}", id);
        societyNpaSettingRepository.deleteById(id);
    }
}

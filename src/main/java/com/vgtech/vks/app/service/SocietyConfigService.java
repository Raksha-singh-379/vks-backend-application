package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.SocietyConfig;
import com.vgtech.vks.app.repository.SocietyConfigRepository;
import com.vgtech.vks.app.service.dto.SocietyConfigDTO;
import com.vgtech.vks.app.service.mapper.SocietyConfigMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocietyConfig}.
 */
@Service
@Transactional
public class SocietyConfigService {

    private final Logger log = LoggerFactory.getLogger(SocietyConfigService.class);

    private final SocietyConfigRepository societyConfigRepository;

    private final SocietyConfigMapper societyConfigMapper;

    public SocietyConfigService(SocietyConfigRepository societyConfigRepository, SocietyConfigMapper societyConfigMapper) {
        this.societyConfigRepository = societyConfigRepository;
        this.societyConfigMapper = societyConfigMapper;
    }

    /**
     * Save a societyConfig.
     *
     * @param societyConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyConfigDTO save(SocietyConfigDTO societyConfigDTO) {
        log.debug("Request to save SocietyConfig : {}", societyConfigDTO);
        SocietyConfig societyConfig = societyConfigMapper.toEntity(societyConfigDTO);
        societyConfig = societyConfigRepository.save(societyConfig);
        return societyConfigMapper.toDto(societyConfig);
    }

    /**
     * Update a societyConfig.
     *
     * @param societyConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyConfigDTO update(SocietyConfigDTO societyConfigDTO) {
        log.debug("Request to save SocietyConfig : {}", societyConfigDTO);
        SocietyConfig societyConfig = societyConfigMapper.toEntity(societyConfigDTO);
        societyConfig = societyConfigRepository.save(societyConfig);
        return societyConfigMapper.toDto(societyConfig);
    }

    /**
     * Partially update a societyConfig.
     *
     * @param societyConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocietyConfigDTO> partialUpdate(SocietyConfigDTO societyConfigDTO) {
        log.debug("Request to partially update SocietyConfig : {}", societyConfigDTO);

        return societyConfigRepository
            .findById(societyConfigDTO.getId())
            .map(existingSocietyConfig -> {
                societyConfigMapper.partialUpdate(existingSocietyConfig, societyConfigDTO);

                return existingSocietyConfig;
            })
            .map(societyConfigRepository::save)
            .map(societyConfigMapper::toDto);
    }

    /**
     * Get all the societyConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocietyConfigs");
        return societyConfigRepository.findAll(pageable).map(societyConfigMapper::toDto);
    }

    /**
     * Get one societyConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocietyConfigDTO> findOne(Long id) {
        log.debug("Request to get SocietyConfig : {}", id);
        return societyConfigRepository.findById(id).map(societyConfigMapper::toDto);
    }

    /**
     * Delete the societyConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SocietyConfig : {}", id);
        societyConfigRepository.deleteById(id);
    }
}

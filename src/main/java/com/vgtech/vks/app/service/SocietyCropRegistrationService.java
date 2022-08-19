package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.SocietyCropRegistration;
import com.vgtech.vks.app.repository.SocietyCropRegistrationRepository;
import com.vgtech.vks.app.service.dto.SocietyCropRegistrationDTO;
import com.vgtech.vks.app.service.mapper.SocietyCropRegistrationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocietyCropRegistration}.
 */
@Service
@Transactional
public class SocietyCropRegistrationService {

    private final Logger log = LoggerFactory.getLogger(SocietyCropRegistrationService.class);

    private final SocietyCropRegistrationRepository societyCropRegistrationRepository;

    private final SocietyCropRegistrationMapper societyCropRegistrationMapper;

    public SocietyCropRegistrationService(
        SocietyCropRegistrationRepository societyCropRegistrationRepository,
        SocietyCropRegistrationMapper societyCropRegistrationMapper
    ) {
        this.societyCropRegistrationRepository = societyCropRegistrationRepository;
        this.societyCropRegistrationMapper = societyCropRegistrationMapper;
    }

    /**
     * Save a societyCropRegistration.
     *
     * @param societyCropRegistrationDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyCropRegistrationDTO save(SocietyCropRegistrationDTO societyCropRegistrationDTO) {
        log.debug("Request to save SocietyCropRegistration : {}", societyCropRegistrationDTO);
        SocietyCropRegistration societyCropRegistration = societyCropRegistrationMapper.toEntity(societyCropRegistrationDTO);
        societyCropRegistration = societyCropRegistrationRepository.save(societyCropRegistration);
        return societyCropRegistrationMapper.toDto(societyCropRegistration);
    }

    /**
     * Update a societyCropRegistration.
     *
     * @param societyCropRegistrationDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyCropRegistrationDTO update(SocietyCropRegistrationDTO societyCropRegistrationDTO) {
        log.debug("Request to save SocietyCropRegistration : {}", societyCropRegistrationDTO);
        SocietyCropRegistration societyCropRegistration = societyCropRegistrationMapper.toEntity(societyCropRegistrationDTO);
        societyCropRegistration = societyCropRegistrationRepository.save(societyCropRegistration);
        return societyCropRegistrationMapper.toDto(societyCropRegistration);
    }

    /**
     * Partially update a societyCropRegistration.
     *
     * @param societyCropRegistrationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocietyCropRegistrationDTO> partialUpdate(SocietyCropRegistrationDTO societyCropRegistrationDTO) {
        log.debug("Request to partially update SocietyCropRegistration : {}", societyCropRegistrationDTO);

        return societyCropRegistrationRepository
            .findById(societyCropRegistrationDTO.getId())
            .map(existingSocietyCropRegistration -> {
                societyCropRegistrationMapper.partialUpdate(existingSocietyCropRegistration, societyCropRegistrationDTO);

                return existingSocietyCropRegistration;
            })
            .map(societyCropRegistrationRepository::save)
            .map(societyCropRegistrationMapper::toDto);
    }

    /**
     * Get all the societyCropRegistrations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyCropRegistrationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocietyCropRegistrations");
        return societyCropRegistrationRepository.findAll(pageable).map(societyCropRegistrationMapper::toDto);
    }

    /**
     * Get one societyCropRegistration by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocietyCropRegistrationDTO> findOne(Long id) {
        log.debug("Request to get SocietyCropRegistration : {}", id);
        return societyCropRegistrationRepository.findById(id).map(societyCropRegistrationMapper::toDto);
    }

    /**
     * Delete the societyCropRegistration by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SocietyCropRegistration : {}", id);
        societyCropRegistrationRepository.deleteById(id);
    }
}

package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.SocietyBanksDetails;
import com.vgtech.vks.app.repository.SocietyBanksDetailsRepository;
import com.vgtech.vks.app.service.dto.SocietyBanksDetailsDTO;
import com.vgtech.vks.app.service.mapper.SocietyBanksDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocietyBanksDetails}.
 */
@Service
@Transactional
public class SocietyBanksDetailsService {

    private final Logger log = LoggerFactory.getLogger(SocietyBanksDetailsService.class);

    private final SocietyBanksDetailsRepository societyBanksDetailsRepository;

    private final SocietyBanksDetailsMapper societyBanksDetailsMapper;

    public SocietyBanksDetailsService(
        SocietyBanksDetailsRepository societyBanksDetailsRepository,
        SocietyBanksDetailsMapper societyBanksDetailsMapper
    ) {
        this.societyBanksDetailsRepository = societyBanksDetailsRepository;
        this.societyBanksDetailsMapper = societyBanksDetailsMapper;
    }

    /**
     * Save a societyBanksDetails.
     *
     * @param societyBanksDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyBanksDetailsDTO save(SocietyBanksDetailsDTO societyBanksDetailsDTO) {
        log.debug("Request to save SocietyBanksDetails : {}", societyBanksDetailsDTO);
        SocietyBanksDetails societyBanksDetails = societyBanksDetailsMapper.toEntity(societyBanksDetailsDTO);
        societyBanksDetails = societyBanksDetailsRepository.save(societyBanksDetails);
        return societyBanksDetailsMapper.toDto(societyBanksDetails);
    }

    /**
     * Update a societyBanksDetails.
     *
     * @param societyBanksDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyBanksDetailsDTO update(SocietyBanksDetailsDTO societyBanksDetailsDTO) {
        log.debug("Request to save SocietyBanksDetails : {}", societyBanksDetailsDTO);
        SocietyBanksDetails societyBanksDetails = societyBanksDetailsMapper.toEntity(societyBanksDetailsDTO);
        societyBanksDetails = societyBanksDetailsRepository.save(societyBanksDetails);
        return societyBanksDetailsMapper.toDto(societyBanksDetails);
    }

    /**
     * Partially update a societyBanksDetails.
     *
     * @param societyBanksDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocietyBanksDetailsDTO> partialUpdate(SocietyBanksDetailsDTO societyBanksDetailsDTO) {
        log.debug("Request to partially update SocietyBanksDetails : {}", societyBanksDetailsDTO);

        return societyBanksDetailsRepository
            .findById(societyBanksDetailsDTO.getId())
            .map(existingSocietyBanksDetails -> {
                societyBanksDetailsMapper.partialUpdate(existingSocietyBanksDetails, societyBanksDetailsDTO);

                return existingSocietyBanksDetails;
            })
            .map(societyBanksDetailsRepository::save)
            .map(societyBanksDetailsMapper::toDto);
    }

    /**
     * Get all the societyBanksDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyBanksDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocietyBanksDetails");
        return societyBanksDetailsRepository.findAll(pageable).map(societyBanksDetailsMapper::toDto);
    }

    /**
     * Get one societyBanksDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocietyBanksDetailsDTO> findOne(Long id) {
        log.debug("Request to get SocietyBanksDetails : {}", id);
        return societyBanksDetailsRepository.findById(id).map(societyBanksDetailsMapper::toDto);
    }

    /**
     * Delete the societyBanksDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SocietyBanksDetails : {}", id);
        societyBanksDetailsRepository.deleteById(id);
    }
}

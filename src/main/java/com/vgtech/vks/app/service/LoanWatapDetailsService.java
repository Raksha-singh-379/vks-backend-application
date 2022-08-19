package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.LoanWatapDetails;
import com.vgtech.vks.app.repository.LoanWatapDetailsRepository;
import com.vgtech.vks.app.service.dto.LoanWatapDetailsDTO;
import com.vgtech.vks.app.service.mapper.LoanWatapDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoanWatapDetails}.
 */
@Service
@Transactional
public class LoanWatapDetailsService {

    private final Logger log = LoggerFactory.getLogger(LoanWatapDetailsService.class);

    private final LoanWatapDetailsRepository loanWatapDetailsRepository;

    private final LoanWatapDetailsMapper loanWatapDetailsMapper;

    public LoanWatapDetailsService(LoanWatapDetailsRepository loanWatapDetailsRepository, LoanWatapDetailsMapper loanWatapDetailsMapper) {
        this.loanWatapDetailsRepository = loanWatapDetailsRepository;
        this.loanWatapDetailsMapper = loanWatapDetailsMapper;
    }

    /**
     * Save a loanWatapDetails.
     *
     * @param loanWatapDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanWatapDetailsDTO save(LoanWatapDetailsDTO loanWatapDetailsDTO) {
        log.debug("Request to save LoanWatapDetails : {}", loanWatapDetailsDTO);
        LoanWatapDetails loanWatapDetails = loanWatapDetailsMapper.toEntity(loanWatapDetailsDTO);
        loanWatapDetails = loanWatapDetailsRepository.save(loanWatapDetails);
        return loanWatapDetailsMapper.toDto(loanWatapDetails);
    }

    /**
     * Update a loanWatapDetails.
     *
     * @param loanWatapDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanWatapDetailsDTO update(LoanWatapDetailsDTO loanWatapDetailsDTO) {
        log.debug("Request to save LoanWatapDetails : {}", loanWatapDetailsDTO);
        LoanWatapDetails loanWatapDetails = loanWatapDetailsMapper.toEntity(loanWatapDetailsDTO);
        loanWatapDetails = loanWatapDetailsRepository.save(loanWatapDetails);
        return loanWatapDetailsMapper.toDto(loanWatapDetails);
    }

    /**
     * Partially update a loanWatapDetails.
     *
     * @param loanWatapDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanWatapDetailsDTO> partialUpdate(LoanWatapDetailsDTO loanWatapDetailsDTO) {
        log.debug("Request to partially update LoanWatapDetails : {}", loanWatapDetailsDTO);

        return loanWatapDetailsRepository
            .findById(loanWatapDetailsDTO.getId())
            .map(existingLoanWatapDetails -> {
                loanWatapDetailsMapper.partialUpdate(existingLoanWatapDetails, loanWatapDetailsDTO);

                return existingLoanWatapDetails;
            })
            .map(loanWatapDetailsRepository::save)
            .map(loanWatapDetailsMapper::toDto);
    }

    /**
     * Get all the loanWatapDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanWatapDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanWatapDetails");
        return loanWatapDetailsRepository.findAll(pageable).map(loanWatapDetailsMapper::toDto);
    }

    /**
     * Get one loanWatapDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanWatapDetailsDTO> findOne(Long id) {
        log.debug("Request to get LoanWatapDetails : {}", id);
        return loanWatapDetailsRepository.findById(id).map(loanWatapDetailsMapper::toDto);
    }

    /**
     * Delete the loanWatapDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanWatapDetails : {}", id);
        loanWatapDetailsRepository.deleteById(id);
    }
}

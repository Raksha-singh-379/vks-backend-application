package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.LoanDetails;
import com.vgtech.vks.app.repository.LoanDetailsRepository;
import com.vgtech.vks.app.service.dto.LoanDetailsDTO;
import com.vgtech.vks.app.service.mapper.LoanDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoanDetails}.
 */
@Service
@Transactional
public class LoanDetailsService {

    private final Logger log = LoggerFactory.getLogger(LoanDetailsService.class);

    private final LoanDetailsRepository loanDetailsRepository;

    private final LoanDetailsMapper loanDetailsMapper;

    public LoanDetailsService(LoanDetailsRepository loanDetailsRepository, LoanDetailsMapper loanDetailsMapper) {
        this.loanDetailsRepository = loanDetailsRepository;
        this.loanDetailsMapper = loanDetailsMapper;
    }

    /**
     * Save a loanDetails.
     *
     * @param loanDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanDetailsDTO save(LoanDetailsDTO loanDetailsDTO) {
        log.debug("Request to save LoanDetails : {}", loanDetailsDTO);
        LoanDetails loanDetails = loanDetailsMapper.toEntity(loanDetailsDTO);
        loanDetails = loanDetailsRepository.save(loanDetails);
        return loanDetailsMapper.toDto(loanDetails);
    }

    /**
     * Update a loanDetails.
     *
     * @param loanDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanDetailsDTO update(LoanDetailsDTO loanDetailsDTO) {
        log.debug("Request to save LoanDetails : {}", loanDetailsDTO);
        LoanDetails loanDetails = loanDetailsMapper.toEntity(loanDetailsDTO);
        loanDetails = loanDetailsRepository.save(loanDetails);
        return loanDetailsMapper.toDto(loanDetails);
    }

    /**
     * Partially update a loanDetails.
     *
     * @param loanDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanDetailsDTO> partialUpdate(LoanDetailsDTO loanDetailsDTO) {
        log.debug("Request to partially update LoanDetails : {}", loanDetailsDTO);

        return loanDetailsRepository
            .findById(loanDetailsDTO.getId())
            .map(existingLoanDetails -> {
                loanDetailsMapper.partialUpdate(existingLoanDetails, loanDetailsDTO);

                return existingLoanDetails;
            })
            .map(loanDetailsRepository::save)
            .map(loanDetailsMapper::toDto);
    }

    /**
     * Get all the loanDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanDetails");
        return loanDetailsRepository.findAll(pageable).map(loanDetailsMapper::toDto);
    }

    /**
     * Get one loanDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanDetailsDTO> findOne(Long id) {
        log.debug("Request to get LoanDetails : {}", id);
        return loanDetailsRepository.findById(id).map(loanDetailsMapper::toDto);
    }

    /**
     * Delete the loanDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanDetails : {}", id);
        loanDetailsRepository.deleteById(id);
    }
}

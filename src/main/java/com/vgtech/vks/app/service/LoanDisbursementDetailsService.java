package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.LoanDisbursementDetails;
import com.vgtech.vks.app.repository.LoanDisbursementDetailsRepository;
import com.vgtech.vks.app.service.dto.LoanDisbursementDetailsDTO;
import com.vgtech.vks.app.service.mapper.LoanDisbursementDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoanDisbursementDetails}.
 */
@Service
@Transactional
public class LoanDisbursementDetailsService {

    private final Logger log = LoggerFactory.getLogger(LoanDisbursementDetailsService.class);

    private final LoanDisbursementDetailsRepository loanDisbursementDetailsRepository;

    private final LoanDisbursementDetailsMapper loanDisbursementDetailsMapper;

    public LoanDisbursementDetailsService(
        LoanDisbursementDetailsRepository loanDisbursementDetailsRepository,
        LoanDisbursementDetailsMapper loanDisbursementDetailsMapper
    ) {
        this.loanDisbursementDetailsRepository = loanDisbursementDetailsRepository;
        this.loanDisbursementDetailsMapper = loanDisbursementDetailsMapper;
    }

    /**
     * Save a loanDisbursementDetails.
     *
     * @param loanDisbursementDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanDisbursementDetailsDTO save(LoanDisbursementDetailsDTO loanDisbursementDetailsDTO) {
        log.debug("Request to save LoanDisbursementDetails : {}", loanDisbursementDetailsDTO);
        LoanDisbursementDetails loanDisbursementDetails = loanDisbursementDetailsMapper.toEntity(loanDisbursementDetailsDTO);
        loanDisbursementDetails = loanDisbursementDetailsRepository.save(loanDisbursementDetails);
        return loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);
    }

    /**
     * Update a loanDisbursementDetails.
     *
     * @param loanDisbursementDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanDisbursementDetailsDTO update(LoanDisbursementDetailsDTO loanDisbursementDetailsDTO) {
        log.debug("Request to save LoanDisbursementDetails : {}", loanDisbursementDetailsDTO);
        LoanDisbursementDetails loanDisbursementDetails = loanDisbursementDetailsMapper.toEntity(loanDisbursementDetailsDTO);
        loanDisbursementDetails = loanDisbursementDetailsRepository.save(loanDisbursementDetails);
        return loanDisbursementDetailsMapper.toDto(loanDisbursementDetails);
    }

    /**
     * Partially update a loanDisbursementDetails.
     *
     * @param loanDisbursementDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanDisbursementDetailsDTO> partialUpdate(LoanDisbursementDetailsDTO loanDisbursementDetailsDTO) {
        log.debug("Request to partially update LoanDisbursementDetails : {}", loanDisbursementDetailsDTO);

        return loanDisbursementDetailsRepository
            .findById(loanDisbursementDetailsDTO.getId())
            .map(existingLoanDisbursementDetails -> {
                loanDisbursementDetailsMapper.partialUpdate(existingLoanDisbursementDetails, loanDisbursementDetailsDTO);

                return existingLoanDisbursementDetails;
            })
            .map(loanDisbursementDetailsRepository::save)
            .map(loanDisbursementDetailsMapper::toDto);
    }

    /**
     * Get all the loanDisbursementDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDisbursementDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanDisbursementDetails");
        return loanDisbursementDetailsRepository.findAll(pageable).map(loanDisbursementDetailsMapper::toDto);
    }

    /**
     * Get one loanDisbursementDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanDisbursementDetailsDTO> findOne(Long id) {
        log.debug("Request to get LoanDisbursementDetails : {}", id);
        return loanDisbursementDetailsRepository.findById(id).map(loanDisbursementDetailsMapper::toDto);
    }

    /**
     * Delete the loanDisbursementDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanDisbursementDetails : {}", id);
        loanDisbursementDetailsRepository.deleteById(id);
    }
}

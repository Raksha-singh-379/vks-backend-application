package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.LoanDemand;
import com.vgtech.vks.app.repository.LoanDemandRepository;
import com.vgtech.vks.app.service.dto.LoanDemandDTO;
import com.vgtech.vks.app.service.mapper.LoanDemandMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoanDemand}.
 */
@Service
@Transactional
public class LoanDemandService {

    private final Logger log = LoggerFactory.getLogger(LoanDemandService.class);

    private final LoanDemandRepository loanDemandRepository;

    private final LoanDemandMapper loanDemandMapper;

    public LoanDemandService(LoanDemandRepository loanDemandRepository, LoanDemandMapper loanDemandMapper) {
        this.loanDemandRepository = loanDemandRepository;
        this.loanDemandMapper = loanDemandMapper;
    }

    /**
     * Save a loanDemand.
     *
     * @param loanDemandDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanDemandDTO save(LoanDemandDTO loanDemandDTO) {
        log.debug("Request to save LoanDemand : {}", loanDemandDTO);
        LoanDemand loanDemand = loanDemandMapper.toEntity(loanDemandDTO);
        loanDemand = loanDemandRepository.save(loanDemand);
        return loanDemandMapper.toDto(loanDemand);
    }

    /**
     * Update a loanDemand.
     *
     * @param loanDemandDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanDemandDTO update(LoanDemandDTO loanDemandDTO) {
        log.debug("Request to save LoanDemand : {}", loanDemandDTO);
        LoanDemand loanDemand = loanDemandMapper.toEntity(loanDemandDTO);
        loanDemand = loanDemandRepository.save(loanDemand);
        return loanDemandMapper.toDto(loanDemand);
    }

    /**
     * Partially update a loanDemand.
     *
     * @param loanDemandDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanDemandDTO> partialUpdate(LoanDemandDTO loanDemandDTO) {
        log.debug("Request to partially update LoanDemand : {}", loanDemandDTO);

        return loanDemandRepository
            .findById(loanDemandDTO.getId())
            .map(existingLoanDemand -> {
                loanDemandMapper.partialUpdate(existingLoanDemand, loanDemandDTO);

                return existingLoanDemand;
            })
            .map(loanDemandRepository::save)
            .map(loanDemandMapper::toDto);
    }

    /**
     * Get all the loanDemands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDemandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanDemands");
        return loanDemandRepository.findAll(pageable).map(loanDemandMapper::toDto);
    }

    /**
     * Get one loanDemand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanDemandDTO> findOne(Long id) {
        log.debug("Request to get LoanDemand : {}", id);
        return loanDemandRepository.findById(id).map(loanDemandMapper::toDto);
    }

    /**
     * Delete the loanDemand by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanDemand : {}", id);
        loanDemandRepository.deleteById(id);
    }
}

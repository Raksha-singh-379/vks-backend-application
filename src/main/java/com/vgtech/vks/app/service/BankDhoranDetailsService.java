package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.repository.BankDhoranDetailsRepository;
import com.vgtech.vks.app.service.dto.BankDhoranDetailsDTO;
import com.vgtech.vks.app.service.mapper.BankDhoranDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankDhoranDetails}.
 */
@Service
@Transactional
public class BankDhoranDetailsService {

    private final Logger log = LoggerFactory.getLogger(BankDhoranDetailsService.class);

    private final BankDhoranDetailsRepository bankDhoranDetailsRepository;

    private final BankDhoranDetailsMapper bankDhoranDetailsMapper;

    public BankDhoranDetailsService(
        BankDhoranDetailsRepository bankDhoranDetailsRepository,
        BankDhoranDetailsMapper bankDhoranDetailsMapper
    ) {
        this.bankDhoranDetailsRepository = bankDhoranDetailsRepository;
        this.bankDhoranDetailsMapper = bankDhoranDetailsMapper;
    }

    /**
     * Save a bankDhoranDetails.
     *
     * @param bankDhoranDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public BankDhoranDetailsDTO save(BankDhoranDetailsDTO bankDhoranDetailsDTO) {
        log.debug("Request to save BankDhoranDetails : {}", bankDhoranDetailsDTO);
        BankDhoranDetails bankDhoranDetails = bankDhoranDetailsMapper.toEntity(bankDhoranDetailsDTO);
        bankDhoranDetails = bankDhoranDetailsRepository.save(bankDhoranDetails);
        return bankDhoranDetailsMapper.toDto(bankDhoranDetails);
    }

    /**
     * Update a bankDhoranDetails.
     *
     * @param bankDhoranDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public BankDhoranDetailsDTO update(BankDhoranDetailsDTO bankDhoranDetailsDTO) {
        log.debug("Request to save BankDhoranDetails : {}", bankDhoranDetailsDTO);
        BankDhoranDetails bankDhoranDetails = bankDhoranDetailsMapper.toEntity(bankDhoranDetailsDTO);
        bankDhoranDetails = bankDhoranDetailsRepository.save(bankDhoranDetails);
        return bankDhoranDetailsMapper.toDto(bankDhoranDetails);
    }

    /**
     * Partially update a bankDhoranDetails.
     *
     * @param bankDhoranDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BankDhoranDetailsDTO> partialUpdate(BankDhoranDetailsDTO bankDhoranDetailsDTO) {
        log.debug("Request to partially update BankDhoranDetails : {}", bankDhoranDetailsDTO);

        return bankDhoranDetailsRepository
            .findById(bankDhoranDetailsDTO.getId())
            .map(existingBankDhoranDetails -> {
                bankDhoranDetailsMapper.partialUpdate(existingBankDhoranDetails, bankDhoranDetailsDTO);

                return existingBankDhoranDetails;
            })
            .map(bankDhoranDetailsRepository::save)
            .map(bankDhoranDetailsMapper::toDto);
    }

    /**
     * Get all the bankDhoranDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BankDhoranDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BankDhoranDetails");
        return bankDhoranDetailsRepository.findAll(pageable).map(bankDhoranDetailsMapper::toDto);
    }

    /**
     * Get one bankDhoranDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BankDhoranDetailsDTO> findOne(Long id) {
        log.debug("Request to get BankDhoranDetails : {}", id);
        return bankDhoranDetailsRepository.findById(id).map(bankDhoranDetailsMapper::toDto);
    }

    /**
     * Delete the bankDhoranDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BankDhoranDetails : {}", id);
        bankDhoranDetailsRepository.deleteById(id);
    }
}

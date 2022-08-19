package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.AccountMapping;
import com.vgtech.vks.app.repository.AccountMappingRepository;
import com.vgtech.vks.app.service.dto.AccountMappingDTO;
import com.vgtech.vks.app.service.mapper.AccountMappingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountMapping}.
 */
@Service
@Transactional
public class AccountMappingService {

    private final Logger log = LoggerFactory.getLogger(AccountMappingService.class);

    private final AccountMappingRepository accountMappingRepository;

    private final AccountMappingMapper accountMappingMapper;

    public AccountMappingService(AccountMappingRepository accountMappingRepository, AccountMappingMapper accountMappingMapper) {
        this.accountMappingRepository = accountMappingRepository;
        this.accountMappingMapper = accountMappingMapper;
    }

    /**
     * Save a accountMapping.
     *
     * @param accountMappingDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountMappingDTO save(AccountMappingDTO accountMappingDTO) {
        log.debug("Request to save AccountMapping : {}", accountMappingDTO);
        AccountMapping accountMapping = accountMappingMapper.toEntity(accountMappingDTO);
        accountMapping = accountMappingRepository.save(accountMapping);
        return accountMappingMapper.toDto(accountMapping);
    }

    /**
     * Update a accountMapping.
     *
     * @param accountMappingDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountMappingDTO update(AccountMappingDTO accountMappingDTO) {
        log.debug("Request to save AccountMapping : {}", accountMappingDTO);
        AccountMapping accountMapping = accountMappingMapper.toEntity(accountMappingDTO);
        accountMapping = accountMappingRepository.save(accountMapping);
        return accountMappingMapper.toDto(accountMapping);
    }

    /**
     * Partially update a accountMapping.
     *
     * @param accountMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccountMappingDTO> partialUpdate(AccountMappingDTO accountMappingDTO) {
        log.debug("Request to partially update AccountMapping : {}", accountMappingDTO);

        return accountMappingRepository
            .findById(accountMappingDTO.getId())
            .map(existingAccountMapping -> {
                accountMappingMapper.partialUpdate(existingAccountMapping, accountMappingDTO);

                return existingAccountMapping;
            })
            .map(accountMappingRepository::save)
            .map(accountMappingMapper::toDto);
    }

    /**
     * Get all the accountMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountMappings");
        return accountMappingRepository.findAll(pageable).map(accountMappingMapper::toDto);
    }

    /**
     * Get one accountMapping by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccountMappingDTO> findOne(Long id) {
        log.debug("Request to get AccountMapping : {}", id);
        return accountMappingRepository.findById(id).map(accountMappingMapper::toDto);
    }

    /**
     * Delete the accountMapping by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AccountMapping : {}", id);
        accountMappingRepository.deleteById(id);
    }
}

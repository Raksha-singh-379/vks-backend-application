package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.ExpenditureType;
import com.vgtech.vks.app.repository.ExpenditureTypeRepository;
import com.vgtech.vks.app.service.dto.ExpenditureTypeDTO;
import com.vgtech.vks.app.service.mapper.ExpenditureTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ExpenditureType}.
 */
@Service
@Transactional
public class ExpenditureTypeService {

    private final Logger log = LoggerFactory.getLogger(ExpenditureTypeService.class);

    private final ExpenditureTypeRepository expenditureTypeRepository;

    private final ExpenditureTypeMapper expenditureTypeMapper;

    public ExpenditureTypeService(ExpenditureTypeRepository expenditureTypeRepository, ExpenditureTypeMapper expenditureTypeMapper) {
        this.expenditureTypeRepository = expenditureTypeRepository;
        this.expenditureTypeMapper = expenditureTypeMapper;
    }

    /**
     * Save a expenditureType.
     *
     * @param expenditureTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ExpenditureTypeDTO save(ExpenditureTypeDTO expenditureTypeDTO) {
        log.debug("Request to save ExpenditureType : {}", expenditureTypeDTO);
        ExpenditureType expenditureType = expenditureTypeMapper.toEntity(expenditureTypeDTO);
        expenditureType = expenditureTypeRepository.save(expenditureType);
        return expenditureTypeMapper.toDto(expenditureType);
    }

    /**
     * Update a expenditureType.
     *
     * @param expenditureTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ExpenditureTypeDTO update(ExpenditureTypeDTO expenditureTypeDTO) {
        log.debug("Request to save ExpenditureType : {}", expenditureTypeDTO);
        ExpenditureType expenditureType = expenditureTypeMapper.toEntity(expenditureTypeDTO);
        expenditureType = expenditureTypeRepository.save(expenditureType);
        return expenditureTypeMapper.toDto(expenditureType);
    }

    /**
     * Partially update a expenditureType.
     *
     * @param expenditureTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ExpenditureTypeDTO> partialUpdate(ExpenditureTypeDTO expenditureTypeDTO) {
        log.debug("Request to partially update ExpenditureType : {}", expenditureTypeDTO);

        return expenditureTypeRepository
            .findById(expenditureTypeDTO.getId())
            .map(existingExpenditureType -> {
                expenditureTypeMapper.partialUpdate(existingExpenditureType, expenditureTypeDTO);

                return existingExpenditureType;
            })
            .map(expenditureTypeRepository::save)
            .map(expenditureTypeMapper::toDto);
    }

    /**
     * Get all the expenditureTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ExpenditureTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExpenditureTypes");
        return expenditureTypeRepository.findAll(pageable).map(expenditureTypeMapper::toDto);
    }

    /**
     * Get one expenditureType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ExpenditureTypeDTO> findOne(Long id) {
        log.debug("Request to get ExpenditureType : {}", id);
        return expenditureTypeRepository.findById(id).map(expenditureTypeMapper::toDto);
    }

    /**
     * Delete the expenditureType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ExpenditureType : {}", id);
        expenditureTypeRepository.deleteById(id);
    }
}

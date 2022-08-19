package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.SocietyLoanProduct;
import com.vgtech.vks.app.repository.SocietyLoanProductRepository;
import com.vgtech.vks.app.service.dto.SocietyLoanProductDTO;
import com.vgtech.vks.app.service.mapper.SocietyLoanProductMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocietyLoanProduct}.
 */
@Service
@Transactional
public class SocietyLoanProductService {

    private final Logger log = LoggerFactory.getLogger(SocietyLoanProductService.class);

    private final SocietyLoanProductRepository societyLoanProductRepository;

    private final SocietyLoanProductMapper societyLoanProductMapper;

    public SocietyLoanProductService(
        SocietyLoanProductRepository societyLoanProductRepository,
        SocietyLoanProductMapper societyLoanProductMapper
    ) {
        this.societyLoanProductRepository = societyLoanProductRepository;
        this.societyLoanProductMapper = societyLoanProductMapper;
    }

    /**
     * Save a societyLoanProduct.
     *
     * @param societyLoanProductDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyLoanProductDTO save(SocietyLoanProductDTO societyLoanProductDTO) {
        log.debug("Request to save SocietyLoanProduct : {}", societyLoanProductDTO);
        SocietyLoanProduct societyLoanProduct = societyLoanProductMapper.toEntity(societyLoanProductDTO);
        societyLoanProduct = societyLoanProductRepository.save(societyLoanProduct);
        return societyLoanProductMapper.toDto(societyLoanProduct);
    }

    /**
     * Update a societyLoanProduct.
     *
     * @param societyLoanProductDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietyLoanProductDTO update(SocietyLoanProductDTO societyLoanProductDTO) {
        log.debug("Request to save SocietyLoanProduct : {}", societyLoanProductDTO);
        SocietyLoanProduct societyLoanProduct = societyLoanProductMapper.toEntity(societyLoanProductDTO);
        societyLoanProduct = societyLoanProductRepository.save(societyLoanProduct);
        return societyLoanProductMapper.toDto(societyLoanProduct);
    }

    /**
     * Partially update a societyLoanProduct.
     *
     * @param societyLoanProductDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocietyLoanProductDTO> partialUpdate(SocietyLoanProductDTO societyLoanProductDTO) {
        log.debug("Request to partially update SocietyLoanProduct : {}", societyLoanProductDTO);

        return societyLoanProductRepository
            .findById(societyLoanProductDTO.getId())
            .map(existingSocietyLoanProduct -> {
                societyLoanProductMapper.partialUpdate(existingSocietyLoanProduct, societyLoanProductDTO);

                return existingSocietyLoanProduct;
            })
            .map(societyLoanProductRepository::save)
            .map(societyLoanProductMapper::toDto);
    }

    /**
     * Get all the societyLoanProducts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyLoanProductDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SocietyLoanProducts");
        return societyLoanProductRepository.findAll(pageable).map(societyLoanProductMapper::toDto);
    }

    /**
     * Get one societyLoanProduct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocietyLoanProductDTO> findOne(Long id) {
        log.debug("Request to get SocietyLoanProduct : {}", id);
        return societyLoanProductRepository.findById(id).map(societyLoanProductMapper::toDto);
    }

    /**
     * Delete the societyLoanProduct by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SocietyLoanProduct : {}", id);
        societyLoanProductRepository.deleteById(id);
    }
}

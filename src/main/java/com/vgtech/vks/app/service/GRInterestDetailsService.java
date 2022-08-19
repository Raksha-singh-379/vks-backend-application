package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.GRInterestDetails;
import com.vgtech.vks.app.repository.GRInterestDetailsRepository;
import com.vgtech.vks.app.service.dto.GRInterestDetailsDTO;
import com.vgtech.vks.app.service.mapper.GRInterestDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GRInterestDetails}.
 */
@Service
@Transactional
public class GRInterestDetailsService {

    private final Logger log = LoggerFactory.getLogger(GRInterestDetailsService.class);

    private final GRInterestDetailsRepository gRInterestDetailsRepository;

    private final GRInterestDetailsMapper gRInterestDetailsMapper;

    public GRInterestDetailsService(
        GRInterestDetailsRepository gRInterestDetailsRepository,
        GRInterestDetailsMapper gRInterestDetailsMapper
    ) {
        this.gRInterestDetailsRepository = gRInterestDetailsRepository;
        this.gRInterestDetailsMapper = gRInterestDetailsMapper;
    }

    /**
     * Save a gRInterestDetails.
     *
     * @param gRInterestDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public GRInterestDetailsDTO save(GRInterestDetailsDTO gRInterestDetailsDTO) {
        log.debug("Request to save GRInterestDetails : {}", gRInterestDetailsDTO);
        GRInterestDetails gRInterestDetails = gRInterestDetailsMapper.toEntity(gRInterestDetailsDTO);
        gRInterestDetails = gRInterestDetailsRepository.save(gRInterestDetails);
        return gRInterestDetailsMapper.toDto(gRInterestDetails);
    }

    /**
     * Update a gRInterestDetails.
     *
     * @param gRInterestDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public GRInterestDetailsDTO update(GRInterestDetailsDTO gRInterestDetailsDTO) {
        log.debug("Request to save GRInterestDetails : {}", gRInterestDetailsDTO);
        GRInterestDetails gRInterestDetails = gRInterestDetailsMapper.toEntity(gRInterestDetailsDTO);
        gRInterestDetails = gRInterestDetailsRepository.save(gRInterestDetails);
        return gRInterestDetailsMapper.toDto(gRInterestDetails);
    }

    /**
     * Partially update a gRInterestDetails.
     *
     * @param gRInterestDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GRInterestDetailsDTO> partialUpdate(GRInterestDetailsDTO gRInterestDetailsDTO) {
        log.debug("Request to partially update GRInterestDetails : {}", gRInterestDetailsDTO);

        return gRInterestDetailsRepository
            .findById(gRInterestDetailsDTO.getId())
            .map(existingGRInterestDetails -> {
                gRInterestDetailsMapper.partialUpdate(existingGRInterestDetails, gRInterestDetailsDTO);

                return existingGRInterestDetails;
            })
            .map(gRInterestDetailsRepository::save)
            .map(gRInterestDetailsMapper::toDto);
    }

    /**
     * Get all the gRInterestDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GRInterestDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GRInterestDetails");
        return gRInterestDetailsRepository.findAll(pageable).map(gRInterestDetailsMapper::toDto);
    }

    /**
     * Get one gRInterestDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GRInterestDetailsDTO> findOne(Long id) {
        log.debug("Request to get GRInterestDetails : {}", id);
        return gRInterestDetailsRepository.findById(id).map(gRInterestDetailsMapper::toDto);
    }

    /**
     * Delete the gRInterestDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GRInterestDetails : {}", id);
        gRInterestDetailsRepository.deleteById(id);
    }
}

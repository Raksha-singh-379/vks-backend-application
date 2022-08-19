package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.LoanWatapDetails;
import com.vgtech.vks.app.repository.LoanWatapDetailsRepository;
import com.vgtech.vks.app.service.criteria.LoanWatapDetailsCriteria;
import com.vgtech.vks.app.service.dto.LoanWatapDetailsDTO;
import com.vgtech.vks.app.service.mapper.LoanWatapDetailsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link LoanWatapDetails} entities in the database.
 * The main input is a {@link LoanWatapDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanWatapDetailsDTO} or a {@link Page} of {@link LoanWatapDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanWatapDetailsQueryService extends QueryService<LoanWatapDetails> {

    private final Logger log = LoggerFactory.getLogger(LoanWatapDetailsQueryService.class);

    private final LoanWatapDetailsRepository loanWatapDetailsRepository;

    private final LoanWatapDetailsMapper loanWatapDetailsMapper;

    public LoanWatapDetailsQueryService(
        LoanWatapDetailsRepository loanWatapDetailsRepository,
        LoanWatapDetailsMapper loanWatapDetailsMapper
    ) {
        this.loanWatapDetailsRepository = loanWatapDetailsRepository;
        this.loanWatapDetailsMapper = loanWatapDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link LoanWatapDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanWatapDetailsDTO> findByCriteria(LoanWatapDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<LoanWatapDetails> specification = createSpecification(criteria);
        return loanWatapDetailsMapper.toDto(loanWatapDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanWatapDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanWatapDetailsDTO> findByCriteria(LoanWatapDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<LoanWatapDetails> specification = createSpecification(criteria);
        return loanWatapDetailsRepository.findAll(specification, page).map(loanWatapDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanWatapDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<LoanWatapDetails> specification = createSpecification(criteria);
        return loanWatapDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanWatapDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanWatapDetails> createSpecification(LoanWatapDetailsCriteria criteria) {
        Specification<LoanWatapDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanWatapDetails_.id));
            }
            if (criteria.getLoanWatapDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanWatapDate(), LoanWatapDetails_.loanWatapDate));
            }
            if (criteria.getCropLandInHector() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCropLandInHector(), LoanWatapDetails_.cropLandInHector));
            }
            if (criteria.getSlotNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSlotNumber(), LoanWatapDetails_.slotNumber));
            }
            if (criteria.getLoanAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanAmount(), LoanWatapDetails_.loanAmount));
            }
            if (criteria.getSeason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSeason(), LoanWatapDetails_.season));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LoanWatapDetails_.status));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), LoanWatapDetails_.year));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), LoanWatapDetails_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LoanWatapDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanWatapDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LoanWatapDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), LoanWatapDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LoanWatapDetails_.freeField3));
            }
            if (criteria.getLoanDemandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanDemandId(),
                            root -> root.join(LoanWatapDetails_.loanDemand, JoinType.LEFT).get(LoanDemand_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

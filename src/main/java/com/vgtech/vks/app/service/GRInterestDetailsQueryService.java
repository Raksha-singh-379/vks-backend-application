package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.GRInterestDetails;
import com.vgtech.vks.app.repository.GRInterestDetailsRepository;
import com.vgtech.vks.app.service.criteria.GRInterestDetailsCriteria;
import com.vgtech.vks.app.service.dto.GRInterestDetailsDTO;
import com.vgtech.vks.app.service.mapper.GRInterestDetailsMapper;
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
 * Service for executing complex queries for {@link GRInterestDetails} entities in the database.
 * The main input is a {@link GRInterestDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GRInterestDetailsDTO} or a {@link Page} of {@link GRInterestDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GRInterestDetailsQueryService extends QueryService<GRInterestDetails> {

    private final Logger log = LoggerFactory.getLogger(GRInterestDetailsQueryService.class);

    private final GRInterestDetailsRepository gRInterestDetailsRepository;

    private final GRInterestDetailsMapper gRInterestDetailsMapper;

    public GRInterestDetailsQueryService(
        GRInterestDetailsRepository gRInterestDetailsRepository,
        GRInterestDetailsMapper gRInterestDetailsMapper
    ) {
        this.gRInterestDetailsRepository = gRInterestDetailsRepository;
        this.gRInterestDetailsMapper = gRInterestDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link GRInterestDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GRInterestDetailsDTO> findByCriteria(GRInterestDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<GRInterestDetails> specification = createSpecification(criteria);
        return gRInterestDetailsMapper.toDto(gRInterestDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GRInterestDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GRInterestDetailsDTO> findByCriteria(GRInterestDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<GRInterestDetails> specification = createSpecification(criteria);
        return gRInterestDetailsRepository.findAll(specification, page).map(gRInterestDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GRInterestDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<GRInterestDetails> specification = createSpecification(criteria);
        return gRInterestDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link GRInterestDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GRInterestDetails> createSpecification(GRInterestDetailsCriteria criteria) {
        Specification<GRInterestDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GRInterestDetails_.id));
            }
            if (criteria.getLoanGrName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanGrName(), GRInterestDetails_.loanGrName));
            }
            if (criteria.getCriteria() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCriteria(), GRInterestDetails_.criteria));
            }
            if (criteria.getProductType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductType(), GRInterestDetails_.productType));
            }
            if (criteria.getIsActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActivated(), GRInterestDetails_.isActivated));
            }
            if (criteria.getBorrowingInterestRate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getBorrowingInterestRate(), GRInterestDetails_.borrowingInterestRate)
                    );
            }
            if (criteria.getInterestOnLoan() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterestOnLoan(), GRInterestDetails_.interestOnLoan));
            }
            if (criteria.getPenaltyInterest() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPenaltyInterest(), GRInterestDetails_.penaltyInterest));
            }
            if (criteria.getSurcharge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurcharge(), GRInterestDetails_.surcharge));
            }
            if (criteria.getLoanDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanDuration(), GRInterestDetails_.loanDuration));
            }
            if (criteria.getNumberOFInstallment() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNumberOFInstallment(), GRInterestDetails_.numberOFInstallment));
            }
            if (criteria.getExtendedInterstRate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExtendedInterstRate(), GRInterestDetails_.extendedInterstRate));
            }
            if (criteria.getCentralGovSubsidyInterest() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getCentralGovSubsidyInterest(), GRInterestDetails_.centralGovSubsidyInterest)
                    );
            }
            if (criteria.getDistBankSubsidyInterest() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDistBankSubsidyInterest(), GRInterestDetails_.distBankSubsidyInterest)
                    );
            }
            if (criteria.getBorrowerInterest() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBorrowerInterest(), GRInterestDetails_.borrowerInterest));
            }
            if (criteria.getStateGovSubsidyInterest() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getStateGovSubsidyInterest(), GRInterestDetails_.stateGovSubsidyInterest)
                    );
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), GRInterestDetails_.year));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), GRInterestDetails_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), GRInterestDetails_.endDate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), GRInterestDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), GRInterestDetails_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), GRInterestDetails_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), GRInterestDetails_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), GRInterestDetails_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), GRInterestDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), GRInterestDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), GRInterestDetails_.freeField3));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(GRInterestDetails_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

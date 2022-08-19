package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.LoanDemand;
import com.vgtech.vks.app.repository.LoanDemandRepository;
import com.vgtech.vks.app.service.criteria.LoanDemandCriteria;
import com.vgtech.vks.app.service.dto.LoanDemandDTO;
import com.vgtech.vks.app.service.mapper.LoanDemandMapper;
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
 * Service for executing complex queries for {@link LoanDemand} entities in the database.
 * The main input is a {@link LoanDemandCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanDemandDTO} or a {@link Page} of {@link LoanDemandDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanDemandQueryService extends QueryService<LoanDemand> {

    private final Logger log = LoggerFactory.getLogger(LoanDemandQueryService.class);

    private final LoanDemandRepository loanDemandRepository;

    private final LoanDemandMapper loanDemandMapper;

    public LoanDemandQueryService(LoanDemandRepository loanDemandRepository, LoanDemandMapper loanDemandMapper) {
        this.loanDemandRepository = loanDemandRepository;
        this.loanDemandMapper = loanDemandMapper;
    }

    /**
     * Return a {@link List} of {@link LoanDemandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanDemandDTO> findByCriteria(LoanDemandCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<LoanDemand> specification = createSpecification(criteria);
        return loanDemandMapper.toDto(loanDemandRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanDemandDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDemandDTO> findByCriteria(LoanDemandCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<LoanDemand> specification = createSpecification(criteria);
        return loanDemandRepository.findAll(specification, page).map(loanDemandMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanDemandCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<LoanDemand> specification = createSpecification(criteria);
        return loanDemandRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanDemandCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanDemand> createSpecification(LoanDemandCriteria criteria) {
        Specification<LoanDemand> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanDemand_.id));
            }
            if (criteria.getLoanDemandAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanDemandAmount(), LoanDemand_.loanDemandAmount));
            }
            if (criteria.getMaxAllowedAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxAllowedAmount(), LoanDemand_.maxAllowedAmount));
            }
            if (criteria.getAdjustedDemand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAdjustedDemand(), LoanDemand_.adjustedDemand));
            }
            if (criteria.getAnnualIncome() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnualIncome(), LoanDemand_.annualIncome));
            }
            if (criteria.getCostOfInvestment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostOfInvestment(), LoanDemand_.costOfInvestment));
            }
            if (criteria.getDemandedLandAreaInHector() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDemandedLandAreaInHector(), LoanDemand_.demandedLandAreaInHector)
                    );
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LoanDemand_.status));
            }
            if (criteria.getSeasonOfCropLoan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSeasonOfCropLoan(), LoanDemand_.seasonOfCropLoan));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), LoanDemand_.year));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LoanDemand_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanDemand_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LoanDemand_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), LoanDemand_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LoanDemand_.freeField3));
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMemberId(), root -> root.join(LoanDemand_.member, JoinType.LEFT).get(Member_.id))
                    );
            }
            if (criteria.getSocietyLoanProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyLoanProductId(),
                            root -> root.join(LoanDemand_.societyLoanProduct, JoinType.LEFT).get(SocietyLoanProduct_.id)
                        )
                    );
            }
            if (criteria.getMemberLandAssetsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberLandAssetsId(),
                            root -> root.join(LoanDemand_.memberLandAssets, JoinType.LEFT).get(MemberLandAssets_.id)
                        )
                    );
            }
            if (criteria.getSocietyCropRegistrationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyCropRegistrationId(),
                            root -> root.join(LoanDemand_.societyCropRegistration, JoinType.LEFT).get(SocietyCropRegistration_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.LoanDetails;
import com.vgtech.vks.app.repository.LoanDetailsRepository;
import com.vgtech.vks.app.service.criteria.LoanDetailsCriteria;
import com.vgtech.vks.app.service.dto.LoanDetailsDTO;
import com.vgtech.vks.app.service.mapper.LoanDetailsMapper;
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
 * Service for executing complex queries for {@link LoanDetails} entities in the database.
 * The main input is a {@link LoanDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanDetailsDTO} or a {@link Page} of {@link LoanDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanDetailsQueryService extends QueryService<LoanDetails> {

    private final Logger log = LoggerFactory.getLogger(LoanDetailsQueryService.class);

    private final LoanDetailsRepository loanDetailsRepository;

    private final LoanDetailsMapper loanDetailsMapper;

    public LoanDetailsQueryService(LoanDetailsRepository loanDetailsRepository, LoanDetailsMapper loanDetailsMapper) {
        this.loanDetailsRepository = loanDetailsRepository;
        this.loanDetailsMapper = loanDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link LoanDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanDetailsDTO> findByCriteria(LoanDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<LoanDetails> specification = createSpecification(criteria);
        return loanDetailsMapper.toDto(loanDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDetailsDTO> findByCriteria(LoanDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<LoanDetails> specification = createSpecification(criteria);
        return loanDetailsRepository.findAll(specification, page).map(loanDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<LoanDetails> specification = createSpecification(criteria);
        return loanDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanDetails> createSpecification(LoanDetailsCriteria criteria) {
        Specification<LoanDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanDetails_.id));
            }
            if (criteria.getLoanAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanAmount(), LoanDetails_.loanAmount));
            }
            if (criteria.getLoanAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanAccountNo(), LoanDetails_.loanAccountNo));
            }
            if (criteria.getLoanType() != null) {
                specification = specification.and(buildSpecification(criteria.getLoanType(), LoanDetails_.loanType));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LoanDetails_.status));
            }
            if (criteria.getLoanStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanStartDate(), LoanDetails_.loanStartDate));
            }
            if (criteria.getLoanEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanEndDate(), LoanDetails_.loanEndDate));
            }
            if (criteria.getLoanPlannedClosureDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLoanPlannedClosureDate(), LoanDetails_.loanPlannedClosureDate));
            }
            if (criteria.getLoanCloserDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanCloserDate(), LoanDetails_.loanCloserDate));
            }
            if (criteria.getLoanEffectiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanEffectiveDate(), LoanDetails_.loanEffectiveDate));
            }
            if (criteria.getLoanClassification() != null) {
                specification = specification.and(buildSpecification(criteria.getLoanClassification(), LoanDetails_.loanClassification));
            }
            if (criteria.getResolutionNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResolutionNo(), LoanDetails_.resolutionNo));
            }
            if (criteria.getResolutionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResolutionDate(), LoanDetails_.resolutionDate));
            }
            if (criteria.getCostOfInvestment() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCostOfInvestment(), LoanDetails_.costOfInvestment));
            }
            if (criteria.getLoanBenefitingArea() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLoanBenefitingArea(), LoanDetails_.loanBenefitingArea));
            }
            if (criteria.getDccbLoanNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDccbLoanNo(), LoanDetails_.dccbLoanNo));
            }
            if (criteria.getMortgageDeedNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMortgageDeedNo(), LoanDetails_.mortgageDeedNo));
            }
            if (criteria.getMortgageDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMortgageDate(), LoanDetails_.mortgageDate));
            }
            if (criteria.getExtentMorgageValue() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExtentMorgageValue(), LoanDetails_.extentMorgageValue));
            }
            if (criteria.getParentAccHeadCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getParentAccHeadCode(), LoanDetails_.parentAccHeadCode));
            }
            if (criteria.getLoanAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanAccountName(), LoanDetails_.loanAccountName));
            }
            if (criteria.getDisbursementAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisbursementAmt(), LoanDetails_.disbursementAmt));
            }
            if (criteria.getDisbursementStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDisbursementStatus(), LoanDetails_.disbursementStatus));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), LoanDetails_.year));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LoanDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LoanDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), LoanDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LoanDetails_.freeField3));
            }
            if (criteria.getLoanDemandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanDemandId(),
                            root -> root.join(LoanDetails_.loanDemand, JoinType.LEFT).get(LoanDemand_.id)
                        )
                    );
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMemberId(), root -> root.join(LoanDetails_.member, JoinType.LEFT).get(Member_.id))
                    );
            }
            if (criteria.getLoanDemandId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanDemandId(),
                            root -> root.join(LoanDetails_.loanDemand, JoinType.LEFT).get(LoanDemand_.id)
                        )
                    );
            }
            if (criteria.getSocietyLoanProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyLoanProductId(),
                            root -> root.join(LoanDetails_.societyLoanProduct, JoinType.LEFT).get(SocietyLoanProduct_.id)
                        )
                    );
            }
            if (criteria.getBankDhoranDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBankDhoranDetailsId(),
                            root -> root.join(LoanDetails_.bankDhoranDetails, JoinType.LEFT).get(BankDhoranDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.SocietyLoanProduct;
import com.vgtech.vks.app.repository.SocietyLoanProductRepository;
import com.vgtech.vks.app.service.criteria.SocietyLoanProductCriteria;
import com.vgtech.vks.app.service.dto.SocietyLoanProductDTO;
import com.vgtech.vks.app.service.mapper.SocietyLoanProductMapper;
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
 * Service for executing complex queries for {@link SocietyLoanProduct} entities in the database.
 * The main input is a {@link SocietyLoanProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocietyLoanProductDTO} or a {@link Page} of {@link SocietyLoanProductDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocietyLoanProductQueryService extends QueryService<SocietyLoanProduct> {

    private final Logger log = LoggerFactory.getLogger(SocietyLoanProductQueryService.class);

    private final SocietyLoanProductRepository societyLoanProductRepository;

    private final SocietyLoanProductMapper societyLoanProductMapper;

    public SocietyLoanProductQueryService(
        SocietyLoanProductRepository societyLoanProductRepository,
        SocietyLoanProductMapper societyLoanProductMapper
    ) {
        this.societyLoanProductRepository = societyLoanProductRepository;
        this.societyLoanProductMapper = societyLoanProductMapper;
    }

    /**
     * Return a {@link List} of {@link SocietyLoanProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocietyLoanProductDTO> findByCriteria(SocietyLoanProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyLoanProduct> specification = createSpecification(criteria);
        return societyLoanProductMapper.toDto(societyLoanProductRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SocietyLoanProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyLoanProductDTO> findByCriteria(SocietyLoanProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<SocietyLoanProduct> specification = createSpecification(criteria);
        return societyLoanProductRepository.findAll(specification, page).map(societyLoanProductMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocietyLoanProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyLoanProduct> specification = createSpecification(criteria);
        return societyLoanProductRepository.count(specification);
    }

    /**
     * Function to convert {@link SocietyLoanProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocietyLoanProduct> createSpecification(SocietyLoanProductCriteria criteria) {
        Specification<SocietyLoanProduct> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocietyLoanProduct_.id));
            }
            if (criteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductName(), SocietyLoanProduct_.productName));
            }
            if (criteria.getAccHeadCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccHeadCode(), SocietyLoanProduct_.accHeadCode));
            }
            if (criteria.getBorrowingInterestRate() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getBorrowingInterestRate(), SocietyLoanProduct_.borrowingInterestRate)
                    );
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDuration(), SocietyLoanProduct_.duration));
            }
            if (criteria.getInterestRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterestRate(), SocietyLoanProduct_.interestRate));
            }
            if (criteria.getLastDateOfRepayment() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastDateOfRepayment(), SocietyLoanProduct_.lastDateOfRepayment));
            }
            if (criteria.getLoanNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanNumber(), SocietyLoanProduct_.loanNumber));
            }
            if (criteria.getMaxLoanAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxLoanAmt(), SocietyLoanProduct_.maxLoanAmt));
            }
            if (criteria.getNoOfDisbursement() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNoOfDisbursement(), SocietyLoanProduct_.noOfDisbursement));
            }
            if (criteria.getNoOfInstallment() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNoOfInstallment(), SocietyLoanProduct_.noOfInstallment));
            }
            if (criteria.getParentAccHeadCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getParentAccHeadCode(), SocietyLoanProduct_.parentAccHeadCode));
            }
            if (criteria.getParentAccHeadId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getParentAccHeadId(), SocietyLoanProduct_.parentAccHeadId));
            }
            if (criteria.getPenaltyInterest() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPenaltyInterest(), SocietyLoanProduct_.penaltyInterest));
            }
            if (criteria.getProductType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductType(), SocietyLoanProduct_.productType));
            }
            if (criteria.getResolutionDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getResolutionDate(), SocietyLoanProduct_.resolutionDate));
            }
            if (criteria.getResolutionNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResolutionNo(), SocietyLoanProduct_.resolutionNo));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), SocietyLoanProduct_.status));
            }
            if (criteria.getSurcharge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurcharge(), SocietyLoanProduct_.surcharge));
            }
            if (criteria.getUnitSize() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUnitSize(), SocietyLoanProduct_.unitSize));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), SocietyLoanProduct_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), SocietyLoanProduct_.validTo));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SocietyLoanProduct_.createdOn));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SocietyLoanProduct_.createdBy));
            }
            if (criteria.getIsActivate() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActivate(), SocietyLoanProduct_.isActivate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SocietyLoanProduct_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SocietyLoanProduct_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), SocietyLoanProduct_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), SocietyLoanProduct_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), SocietyLoanProduct_.freeField3));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(SocietyLoanProduct_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
            if (criteria.getBankDhoranDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBankDhoranDetailsId(),
                            root -> root.join(SocietyLoanProduct_.bankDhoranDetails, JoinType.LEFT).get(BankDhoranDetails_.id)
                        )
                    );
            }
            if (criteria.getGRInterestDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGRInterestDetailsId(),
                            root -> root.join(SocietyLoanProduct_.gRInterestDetails, JoinType.LEFT).get(GRInterestDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.LoanDisbursementDetails;
import com.vgtech.vks.app.repository.LoanDisbursementDetailsRepository;
import com.vgtech.vks.app.service.criteria.LoanDisbursementDetailsCriteria;
import com.vgtech.vks.app.service.dto.LoanDisbursementDetailsDTO;
import com.vgtech.vks.app.service.mapper.LoanDisbursementDetailsMapper;
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
 * Service for executing complex queries for {@link LoanDisbursementDetails} entities in the database.
 * The main input is a {@link LoanDisbursementDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanDisbursementDetailsDTO} or a {@link Page} of {@link LoanDisbursementDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanDisbursementDetailsQueryService extends QueryService<LoanDisbursementDetails> {

    private final Logger log = LoggerFactory.getLogger(LoanDisbursementDetailsQueryService.class);

    private final LoanDisbursementDetailsRepository loanDisbursementDetailsRepository;

    private final LoanDisbursementDetailsMapper loanDisbursementDetailsMapper;

    public LoanDisbursementDetailsQueryService(
        LoanDisbursementDetailsRepository loanDisbursementDetailsRepository,
        LoanDisbursementDetailsMapper loanDisbursementDetailsMapper
    ) {
        this.loanDisbursementDetailsRepository = loanDisbursementDetailsRepository;
        this.loanDisbursementDetailsMapper = loanDisbursementDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link LoanDisbursementDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanDisbursementDetailsDTO> findByCriteria(LoanDisbursementDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<LoanDisbursementDetails> specification = createSpecification(criteria);
        return loanDisbursementDetailsMapper.toDto(loanDisbursementDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanDisbursementDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDisbursementDetailsDTO> findByCriteria(LoanDisbursementDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<LoanDisbursementDetails> specification = createSpecification(criteria);
        return loanDisbursementDetailsRepository.findAll(specification, page).map(loanDisbursementDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanDisbursementDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<LoanDisbursementDetails> specification = createSpecification(criteria);
        return loanDisbursementDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanDisbursementDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanDisbursementDetails> createSpecification(LoanDisbursementDetailsCriteria criteria) {
        Specification<LoanDisbursementDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanDisbursementDetails_.id));
            }
            if (criteria.getDisbursementDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDisbursementDate(), LoanDisbursementDetails_.disbursementDate));
            }
            if (criteria.getDisbursementAmount() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDisbursementAmount(), LoanDisbursementDetails_.disbursementAmount)
                    );
            }
            if (criteria.getDisbursementNumber() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDisbursementNumber(), LoanDisbursementDetails_.disbursementNumber)
                    );
            }
            if (criteria.getDisbursementStatus() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getDisbursementStatus(), LoanDisbursementDetails_.disbursementStatus)
                    );
            }
            if (criteria.getPaymentMode() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMode(), LoanDisbursementDetails_.paymentMode));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), LoanDisbursementDetails_.type));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModified(), LoanDisbursementDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanDisbursementDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LoanDisbursementDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), LoanDisbursementDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LoanDisbursementDetails_.freeField3));
            }
            if (criteria.getLoanDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanDetailsId(),
                            root -> root.join(LoanDisbursementDetails_.loanDetails, JoinType.LEFT).get(LoanDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

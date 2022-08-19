package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.AccountMapping;
import com.vgtech.vks.app.repository.AccountMappingRepository;
import com.vgtech.vks.app.service.criteria.AccountMappingCriteria;
import com.vgtech.vks.app.service.dto.AccountMappingDTO;
import com.vgtech.vks.app.service.mapper.AccountMappingMapper;
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
 * Service for executing complex queries for {@link AccountMapping} entities in the database.
 * The main input is a {@link AccountMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccountMappingDTO} or a {@link Page} of {@link AccountMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccountMappingQueryService extends QueryService<AccountMapping> {

    private final Logger log = LoggerFactory.getLogger(AccountMappingQueryService.class);

    private final AccountMappingRepository accountMappingRepository;

    private final AccountMappingMapper accountMappingMapper;

    public AccountMappingQueryService(AccountMappingRepository accountMappingRepository, AccountMappingMapper accountMappingMapper) {
        this.accountMappingRepository = accountMappingRepository;
        this.accountMappingMapper = accountMappingMapper;
    }

    /**
     * Return a {@link List} of {@link AccountMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccountMappingDTO> findByCriteria(AccountMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<AccountMapping> specification = createSpecification(criteria);
        return accountMappingMapper.toDto(accountMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccountMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountMappingDTO> findByCriteria(AccountMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<AccountMapping> specification = createSpecification(criteria);
        return accountMappingRepository.findAll(specification, page).map(accountMappingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccountMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<AccountMapping> specification = createSpecification(criteria);
        return accountMappingRepository.count(specification);
    }

    /**
     * Function to convert {@link AccountMappingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AccountMapping> createSpecification(AccountMappingCriteria criteria) {
        Specification<AccountMapping> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AccountMapping_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), AccountMapping_.type));
            }
            if (criteria.getMappingName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMappingName(), AccountMapping_.mappingName));
            }
            if (criteria.getLedgerAccHeadCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLedgerAccHeadCode(), AccountMapping_.ledgerAccHeadCode));
            }
            if (criteria.getLedgerAccountId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLedgerAccountId(), AccountMapping_.ledgerAccountId));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AccountMapping_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AccountMapping_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), AccountMapping_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), AccountMapping_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), AccountMapping_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), AccountMapping_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), AccountMapping_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), AccountMapping_.freeField3));
            }
            if (criteria.getAccountMappingId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAccountMappingId(),
                            root -> root.join(AccountMapping_.accountMapping, JoinType.LEFT).get(LedgerAccounts_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

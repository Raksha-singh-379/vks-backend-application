package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.SocietyAssetsData;
import com.vgtech.vks.app.repository.SocietyAssetsDataRepository;
import com.vgtech.vks.app.service.criteria.SocietyAssetsDataCriteria;
import com.vgtech.vks.app.service.dto.SocietyAssetsDataDTO;
import com.vgtech.vks.app.service.mapper.SocietyAssetsDataMapper;
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
 * Service for executing complex queries for {@link SocietyAssetsData} entities in the database.
 * The main input is a {@link SocietyAssetsDataCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocietyAssetsDataDTO} or a {@link Page} of {@link SocietyAssetsDataDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocietyAssetsDataQueryService extends QueryService<SocietyAssetsData> {

    private final Logger log = LoggerFactory.getLogger(SocietyAssetsDataQueryService.class);

    private final SocietyAssetsDataRepository societyAssetsDataRepository;

    private final SocietyAssetsDataMapper societyAssetsDataMapper;

    public SocietyAssetsDataQueryService(
        SocietyAssetsDataRepository societyAssetsDataRepository,
        SocietyAssetsDataMapper societyAssetsDataMapper
    ) {
        this.societyAssetsDataRepository = societyAssetsDataRepository;
        this.societyAssetsDataMapper = societyAssetsDataMapper;
    }

    /**
     * Return a {@link List} of {@link SocietyAssetsDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocietyAssetsDataDTO> findByCriteria(SocietyAssetsDataCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyAssetsData> specification = createSpecification(criteria);
        return societyAssetsDataMapper.toDto(societyAssetsDataRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SocietyAssetsDataDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyAssetsDataDTO> findByCriteria(SocietyAssetsDataCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<SocietyAssetsData> specification = createSpecification(criteria);
        return societyAssetsDataRepository.findAll(specification, page).map(societyAssetsDataMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocietyAssetsDataCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyAssetsData> specification = createSpecification(criteria);
        return societyAssetsDataRepository.count(specification);
    }

    /**
     * Function to convert {@link SocietyAssetsDataCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocietyAssetsData> createSpecification(SocietyAssetsDataCriteria criteria) {
        Specification<SocietyAssetsData> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocietyAssetsData_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), SocietyAssetsData_.amount));
            }
            if (criteria.getBalanceQuantity() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBalanceQuantity(), SocietyAssetsData_.balanceQuantity));
            }
            if (criteria.getBalanceValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBalanceValue(), SocietyAssetsData_.balanceValue));
            }
            if (criteria.getBillNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBillNo(), SocietyAssetsData_.billNo));
            }
            if (criteria.getMode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMode(), SocietyAssetsData_.mode));
            }
            if (criteria.getCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCost(), SocietyAssetsData_.cost));
            }
            if (criteria.getTransactionType() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTransactionType(), SocietyAssetsData_.transactionType));
            }
            if (criteria.getTransactionDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTransactionDate(), SocietyAssetsData_.transactionDate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SocietyAssetsData_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SocietyAssetsData_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SocietyAssetsData_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SocietyAssetsData_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), SocietyAssetsData_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), SocietyAssetsData_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), SocietyAssetsData_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), SocietyAssetsData_.freeField3));
            }
            if (criteria.getSocietyAssetsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyAssetsId(),
                            root -> root.join(SocietyAssetsData_.societyAssets, JoinType.LEFT).get(SocietyAssets_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

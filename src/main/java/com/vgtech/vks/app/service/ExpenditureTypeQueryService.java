package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.ExpenditureType;
import com.vgtech.vks.app.repository.ExpenditureTypeRepository;
import com.vgtech.vks.app.service.criteria.ExpenditureTypeCriteria;
import com.vgtech.vks.app.service.dto.ExpenditureTypeDTO;
import com.vgtech.vks.app.service.mapper.ExpenditureTypeMapper;
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
 * Service for executing complex queries for {@link ExpenditureType} entities in the database.
 * The main input is a {@link ExpenditureTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExpenditureTypeDTO} or a {@link Page} of {@link ExpenditureTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExpenditureTypeQueryService extends QueryService<ExpenditureType> {

    private final Logger log = LoggerFactory.getLogger(ExpenditureTypeQueryService.class);

    private final ExpenditureTypeRepository expenditureTypeRepository;

    private final ExpenditureTypeMapper expenditureTypeMapper;

    public ExpenditureTypeQueryService(ExpenditureTypeRepository expenditureTypeRepository, ExpenditureTypeMapper expenditureTypeMapper) {
        this.expenditureTypeRepository = expenditureTypeRepository;
        this.expenditureTypeMapper = expenditureTypeMapper;
    }

    /**
     * Return a {@link List} of {@link ExpenditureTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExpenditureTypeDTO> findByCriteria(ExpenditureTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<ExpenditureType> specification = createSpecification(criteria);
        return expenditureTypeMapper.toDto(expenditureTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExpenditureTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExpenditureTypeDTO> findByCriteria(ExpenditureTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<ExpenditureType> specification = createSpecification(criteria);
        return expenditureTypeRepository.findAll(specification, page).map(expenditureTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExpenditureTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<ExpenditureType> specification = createSpecification(criteria);
        return expenditureTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link ExpenditureTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExpenditureType> createSpecification(ExpenditureTypeCriteria criteria) {
        Specification<ExpenditureType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExpenditureType_.id));
            }
            if (criteria.getExpenditureDesc() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getExpenditureDesc(), ExpenditureType_.expenditureDesc));
            }
            if (criteria.getExpenditureType() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getExpenditureType(), ExpenditureType_.expenditureType));
            }
            if (criteria.getExpenditureCategory() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getExpenditureCategory(), ExpenditureType_.expenditureCategory));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), ExpenditureType_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ExpenditureType_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ExpenditureType_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), ExpenditureType_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), ExpenditureType_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), ExpenditureType_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), ExpenditureType_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), ExpenditureType_.freeField3));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(ExpenditureType_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

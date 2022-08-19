package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.SocietyPrerequisite;
import com.vgtech.vks.app.repository.SocietyPrerequisiteRepository;
import com.vgtech.vks.app.service.criteria.SocietyPrerequisiteCriteria;
import com.vgtech.vks.app.service.dto.SocietyPrerequisiteDTO;
import com.vgtech.vks.app.service.mapper.SocietyPrerequisiteMapper;
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
 * Service for executing complex queries for {@link SocietyPrerequisite} entities in the database.
 * The main input is a {@link SocietyPrerequisiteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocietyPrerequisiteDTO} or a {@link Page} of {@link SocietyPrerequisiteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocietyPrerequisiteQueryService extends QueryService<SocietyPrerequisite> {

    private final Logger log = LoggerFactory.getLogger(SocietyPrerequisiteQueryService.class);

    private final SocietyPrerequisiteRepository societyPrerequisiteRepository;

    private final SocietyPrerequisiteMapper societyPrerequisiteMapper;

    public SocietyPrerequisiteQueryService(
        SocietyPrerequisiteRepository societyPrerequisiteRepository,
        SocietyPrerequisiteMapper societyPrerequisiteMapper
    ) {
        this.societyPrerequisiteRepository = societyPrerequisiteRepository;
        this.societyPrerequisiteMapper = societyPrerequisiteMapper;
    }

    /**
     * Return a {@link List} of {@link SocietyPrerequisiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocietyPrerequisiteDTO> findByCriteria(SocietyPrerequisiteCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyPrerequisite> specification = createSpecification(criteria);
        return societyPrerequisiteMapper.toDto(societyPrerequisiteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SocietyPrerequisiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyPrerequisiteDTO> findByCriteria(SocietyPrerequisiteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<SocietyPrerequisite> specification = createSpecification(criteria);
        return societyPrerequisiteRepository.findAll(specification, page).map(societyPrerequisiteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocietyPrerequisiteCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyPrerequisite> specification = createSpecification(criteria);
        return societyPrerequisiteRepository.count(specification);
    }

    /**
     * Function to convert {@link SocietyPrerequisiteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocietyPrerequisite> createSpecification(SocietyPrerequisiteCriteria criteria) {
        Specification<SocietyPrerequisite> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocietyPrerequisite_.id));
            }
            if (criteria.getDocType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocType(), SocietyPrerequisite_.docType));
            }
            if (criteria.getDocumentDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentDesc(), SocietyPrerequisite_.documentDesc));
            }
            if (criteria.getDocumentName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumentName(), SocietyPrerequisite_.documentName));
            }
            if (criteria.getLoanType() != null) {
                specification = specification.and(buildSpecification(criteria.getLoanType(), SocietyPrerequisite_.loanType));
            }
            if (criteria.getMandatory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMandatory(), SocietyPrerequisite_.mandatory));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SocietyPrerequisite_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SocietyPrerequisite_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SocietyPrerequisite_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SocietyPrerequisite_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), SocietyPrerequisite_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), SocietyPrerequisite_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), SocietyPrerequisite_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), SocietyPrerequisite_.freeField3));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(SocietyPrerequisite_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

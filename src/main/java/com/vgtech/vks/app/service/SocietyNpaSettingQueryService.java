package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.SocietyNpaSetting;
import com.vgtech.vks.app.repository.SocietyNpaSettingRepository;
import com.vgtech.vks.app.service.criteria.SocietyNpaSettingCriteria;
import com.vgtech.vks.app.service.dto.SocietyNpaSettingDTO;
import com.vgtech.vks.app.service.mapper.SocietyNpaSettingMapper;
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
 * Service for executing complex queries for {@link SocietyNpaSetting} entities in the database.
 * The main input is a {@link SocietyNpaSettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocietyNpaSettingDTO} or a {@link Page} of {@link SocietyNpaSettingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocietyNpaSettingQueryService extends QueryService<SocietyNpaSetting> {

    private final Logger log = LoggerFactory.getLogger(SocietyNpaSettingQueryService.class);

    private final SocietyNpaSettingRepository societyNpaSettingRepository;

    private final SocietyNpaSettingMapper societyNpaSettingMapper;

    public SocietyNpaSettingQueryService(
        SocietyNpaSettingRepository societyNpaSettingRepository,
        SocietyNpaSettingMapper societyNpaSettingMapper
    ) {
        this.societyNpaSettingRepository = societyNpaSettingRepository;
        this.societyNpaSettingMapper = societyNpaSettingMapper;
    }

    /**
     * Return a {@link List} of {@link SocietyNpaSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocietyNpaSettingDTO> findByCriteria(SocietyNpaSettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyNpaSetting> specification = createSpecification(criteria);
        return societyNpaSettingMapper.toDto(societyNpaSettingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SocietyNpaSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyNpaSettingDTO> findByCriteria(SocietyNpaSettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<SocietyNpaSetting> specification = createSpecification(criteria);
        return societyNpaSettingRepository.findAll(specification, page).map(societyNpaSettingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocietyNpaSettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyNpaSetting> specification = createSpecification(criteria);
        return societyNpaSettingRepository.count(specification);
    }

    /**
     * Function to convert {@link SocietyNpaSettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocietyNpaSetting> createSpecification(SocietyNpaSettingCriteria criteria) {
        Specification<SocietyNpaSetting> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocietyNpaSetting_.id));
            }
            if (criteria.getNpaClassification() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getNpaClassification(), SocietyNpaSetting_.npaClassification));
            }
            if (criteria.getDurationStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDurationStart(), SocietyNpaSetting_.durationStart));
            }
            if (criteria.getDurationEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDurationEnd(), SocietyNpaSetting_.durationEnd));
            }
            if (criteria.getProvision() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProvision(), SocietyNpaSetting_.provision));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), SocietyNpaSetting_.year));
            }
            if (criteria.getInterestRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterestRate(), SocietyNpaSetting_.interestRate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SocietyNpaSetting_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SocietyNpaSetting_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SocietyNpaSetting_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SocietyNpaSetting_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), SocietyNpaSetting_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), SocietyNpaSetting_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), SocietyNpaSetting_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), SocietyNpaSetting_.freeField3));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(SocietyNpaSetting_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

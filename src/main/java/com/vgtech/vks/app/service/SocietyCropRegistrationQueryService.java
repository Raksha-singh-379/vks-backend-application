package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.SocietyCropRegistration;
import com.vgtech.vks.app.repository.SocietyCropRegistrationRepository;
import com.vgtech.vks.app.service.criteria.SocietyCropRegistrationCriteria;
import com.vgtech.vks.app.service.dto.SocietyCropRegistrationDTO;
import com.vgtech.vks.app.service.mapper.SocietyCropRegistrationMapper;
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
 * Service for executing complex queries for {@link SocietyCropRegistration} entities in the database.
 * The main input is a {@link SocietyCropRegistrationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocietyCropRegistrationDTO} or a {@link Page} of {@link SocietyCropRegistrationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocietyCropRegistrationQueryService extends QueryService<SocietyCropRegistration> {

    private final Logger log = LoggerFactory.getLogger(SocietyCropRegistrationQueryService.class);

    private final SocietyCropRegistrationRepository societyCropRegistrationRepository;

    private final SocietyCropRegistrationMapper societyCropRegistrationMapper;

    public SocietyCropRegistrationQueryService(
        SocietyCropRegistrationRepository societyCropRegistrationRepository,
        SocietyCropRegistrationMapper societyCropRegistrationMapper
    ) {
        this.societyCropRegistrationRepository = societyCropRegistrationRepository;
        this.societyCropRegistrationMapper = societyCropRegistrationMapper;
    }

    /**
     * Return a {@link List} of {@link SocietyCropRegistrationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocietyCropRegistrationDTO> findByCriteria(SocietyCropRegistrationCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyCropRegistration> specification = createSpecification(criteria);
        return societyCropRegistrationMapper.toDto(societyCropRegistrationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SocietyCropRegistrationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyCropRegistrationDTO> findByCriteria(SocietyCropRegistrationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<SocietyCropRegistration> specification = createSpecification(criteria);
        return societyCropRegistrationRepository.findAll(specification, page).map(societyCropRegistrationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocietyCropRegistrationCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyCropRegistration> specification = createSpecification(criteria);
        return societyCropRegistrationRepository.count(specification);
    }

    /**
     * Function to convert {@link SocietyCropRegistrationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocietyCropRegistration> createSpecification(SocietyCropRegistrationCriteria criteria) {
        Specification<SocietyCropRegistration> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocietyCropRegistration_.id));
            }
            if (criteria.getCropName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCropName(), SocietyCropRegistration_.cropName));
            }
            if (criteria.getMonthDuration() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMonthDuration(), SocietyCropRegistration_.monthDuration));
            }
            if (criteria.getSeason() != null) {
                specification = specification.and(buildSpecification(criteria.getSeason(), SocietyCropRegistration_.season));
            }
            if (criteria.getCropLimit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCropLimit(), SocietyCropRegistration_.cropLimit));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), SocietyCropRegistration_.year));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModified(), SocietyCropRegistration_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SocietyCropRegistration_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SocietyCropRegistration_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SocietyCropRegistration_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), SocietyCropRegistration_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), SocietyCropRegistration_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), SocietyCropRegistration_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), SocietyCropRegistration_.freeField3));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(SocietyCropRegistration_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

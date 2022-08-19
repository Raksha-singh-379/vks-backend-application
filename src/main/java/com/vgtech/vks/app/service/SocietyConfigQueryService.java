package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.SocietyConfig;
import com.vgtech.vks.app.repository.SocietyConfigRepository;
import com.vgtech.vks.app.service.criteria.SocietyConfigCriteria;
import com.vgtech.vks.app.service.dto.SocietyConfigDTO;
import com.vgtech.vks.app.service.mapper.SocietyConfigMapper;
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
 * Service for executing complex queries for {@link SocietyConfig} entities in the database.
 * The main input is a {@link SocietyConfigCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocietyConfigDTO} or a {@link Page} of {@link SocietyConfigDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocietyConfigQueryService extends QueryService<SocietyConfig> {

    private final Logger log = LoggerFactory.getLogger(SocietyConfigQueryService.class);

    private final SocietyConfigRepository societyConfigRepository;

    private final SocietyConfigMapper societyConfigMapper;

    public SocietyConfigQueryService(SocietyConfigRepository societyConfigRepository, SocietyConfigMapper societyConfigMapper) {
        this.societyConfigRepository = societyConfigRepository;
        this.societyConfigMapper = societyConfigMapper;
    }

    /**
     * Return a {@link List} of {@link SocietyConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocietyConfigDTO> findByCriteria(SocietyConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyConfig> specification = createSpecification(criteria);
        return societyConfigMapper.toDto(societyConfigRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SocietyConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyConfigDTO> findByCriteria(SocietyConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<SocietyConfig> specification = createSpecification(criteria);
        return societyConfigRepository.findAll(specification, page).map(societyConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocietyConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyConfig> specification = createSpecification(criteria);
        return societyConfigRepository.count(specification);
    }

    /**
     * Function to convert {@link SocietyConfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocietyConfig> createSpecification(SocietyConfigCriteria criteria) {
        Specification<SocietyConfig> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocietyConfig_.id));
            }
            if (criteria.getConfigName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfigName(), SocietyConfig_.configName));
            }
            if (criteria.getConfigType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfigType(), SocietyConfig_.configType));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), SocietyConfig_.status));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), SocietyConfig_.value));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), SocietyConfig_.year));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SocietyConfig_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SocietyConfig_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SocietyConfig_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SocietyConfig_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), SocietyConfig_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), SocietyConfig_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), SocietyConfig_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), SocietyConfig_.freeField3));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(SocietyConfig_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
            if (criteria.getBankDhoranDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBankDhoranDetailsId(),
                            root -> root.join(SocietyConfig_.bankDhoranDetails, JoinType.LEFT).get(BankDhoranDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.SocietyAssets;
import com.vgtech.vks.app.repository.SocietyAssetsRepository;
import com.vgtech.vks.app.service.criteria.SocietyAssetsCriteria;
import com.vgtech.vks.app.service.dto.SocietyAssetsDTO;
import com.vgtech.vks.app.service.mapper.SocietyAssetsMapper;
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
 * Service for executing complex queries for {@link SocietyAssets} entities in the database.
 * The main input is a {@link SocietyAssetsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocietyAssetsDTO} or a {@link Page} of {@link SocietyAssetsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocietyAssetsQueryService extends QueryService<SocietyAssets> {

    private final Logger log = LoggerFactory.getLogger(SocietyAssetsQueryService.class);

    private final SocietyAssetsRepository societyAssetsRepository;

    private final SocietyAssetsMapper societyAssetsMapper;

    public SocietyAssetsQueryService(SocietyAssetsRepository societyAssetsRepository, SocietyAssetsMapper societyAssetsMapper) {
        this.societyAssetsRepository = societyAssetsRepository;
        this.societyAssetsMapper = societyAssetsMapper;
    }

    /**
     * Return a {@link List} of {@link SocietyAssetsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocietyAssetsDTO> findByCriteria(SocietyAssetsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyAssets> specification = createSpecification(criteria);
        return societyAssetsMapper.toDto(societyAssetsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SocietyAssetsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyAssetsDTO> findByCriteria(SocietyAssetsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<SocietyAssets> specification = createSpecification(criteria);
        return societyAssetsRepository.findAll(specification, page).map(societyAssetsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocietyAssetsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyAssets> specification = createSpecification(criteria);
        return societyAssetsRepository.count(specification);
    }

    /**
     * Function to convert {@link SocietyAssetsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocietyAssets> createSpecification(SocietyAssetsCriteria criteria) {
        Specification<SocietyAssets> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocietyAssets_.id));
            }
            if (criteria.getSocietyAssetsName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSocietyAssetsName(), SocietyAssets_.societyAssetsName));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), SocietyAssets_.type));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), SocietyAssets_.category));
            }
            if (criteria.getDepreciation() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepreciation(), SocietyAssets_.depreciation));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SocietyAssets_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SocietyAssets_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SocietyAssets_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SocietyAssets_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), SocietyAssets_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), SocietyAssets_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), SocietyAssets_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), SocietyAssets_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), SocietyAssets_.freeField4));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(SocietyAssets_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

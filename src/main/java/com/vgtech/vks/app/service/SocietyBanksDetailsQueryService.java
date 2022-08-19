package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.SocietyBanksDetails;
import com.vgtech.vks.app.repository.SocietyBanksDetailsRepository;
import com.vgtech.vks.app.service.criteria.SocietyBanksDetailsCriteria;
import com.vgtech.vks.app.service.dto.SocietyBanksDetailsDTO;
import com.vgtech.vks.app.service.mapper.SocietyBanksDetailsMapper;
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
 * Service for executing complex queries for {@link SocietyBanksDetails} entities in the database.
 * The main input is a {@link SocietyBanksDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SocietyBanksDetailsDTO} or a {@link Page} of {@link SocietyBanksDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SocietyBanksDetailsQueryService extends QueryService<SocietyBanksDetails> {

    private final Logger log = LoggerFactory.getLogger(SocietyBanksDetailsQueryService.class);

    private final SocietyBanksDetailsRepository societyBanksDetailsRepository;

    private final SocietyBanksDetailsMapper societyBanksDetailsMapper;

    public SocietyBanksDetailsQueryService(
        SocietyBanksDetailsRepository societyBanksDetailsRepository,
        SocietyBanksDetailsMapper societyBanksDetailsMapper
    ) {
        this.societyBanksDetailsRepository = societyBanksDetailsRepository;
        this.societyBanksDetailsMapper = societyBanksDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link SocietyBanksDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SocietyBanksDetailsDTO> findByCriteria(SocietyBanksDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyBanksDetails> specification = createSpecification(criteria);
        return societyBanksDetailsMapper.toDto(societyBanksDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SocietyBanksDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietyBanksDetailsDTO> findByCriteria(SocietyBanksDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<SocietyBanksDetails> specification = createSpecification(criteria);
        return societyBanksDetailsRepository.findAll(specification, page).map(societyBanksDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SocietyBanksDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<SocietyBanksDetails> specification = createSpecification(criteria);
        return societyBanksDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link SocietyBanksDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SocietyBanksDetails> createSpecification(SocietyBanksDetailsCriteria criteria) {
        Specification<SocietyBanksDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SocietyBanksDetails_.id));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), SocietyBanksDetails_.bankName));
            }
            if (criteria.getIfsccode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfsccode(), SocietyBanksDetails_.ifsccode));
            }
            if (criteria.getBranchName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchName(), SocietyBanksDetails_.branchName));
            }
            if (criteria.getAccountNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getAccountNumber(), SocietyBanksDetails_.accountNumber));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), SocietyBanksDetails_.isActive));
            }
            if (criteria.getAccountType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountType(), SocietyBanksDetails_.accountType));
            }
            if (criteria.getAccHeadCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccHeadCode(), SocietyBanksDetails_.accHeadCode));
            }
            if (criteria.getParentAccHeadCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getParentAccHeadCode(), SocietyBanksDetails_.parentAccHeadCode));
            }
            if (criteria.getAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountName(), SocietyBanksDetails_.accountName));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SocietyBanksDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SocietyBanksDetails_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SocietyBanksDetails_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SocietyBanksDetails_.createdOn));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), SocietyBanksDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), SocietyBanksDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), SocietyBanksDetails_.freeField3));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(SocietyBanksDetails_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

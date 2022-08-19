package com.vgtech.vks.app.service;

import com.vgtech.vks.app.domain.*; // for static metamodels
import com.vgtech.vks.app.domain.BankDhoranDetails;
import com.vgtech.vks.app.repository.BankDhoranDetailsRepository;
import com.vgtech.vks.app.service.criteria.BankDhoranDetailsCriteria;
import com.vgtech.vks.app.service.dto.BankDhoranDetailsDTO;
import com.vgtech.vks.app.service.mapper.BankDhoranDetailsMapper;
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
 * Service for executing complex queries for {@link BankDhoranDetails} entities in the database.
 * The main input is a {@link BankDhoranDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BankDhoranDetailsDTO} or a {@link Page} of {@link BankDhoranDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BankDhoranDetailsQueryService extends QueryService<BankDhoranDetails> {

    private final Logger log = LoggerFactory.getLogger(BankDhoranDetailsQueryService.class);

    private final BankDhoranDetailsRepository bankDhoranDetailsRepository;

    private final BankDhoranDetailsMapper bankDhoranDetailsMapper;

    public BankDhoranDetailsQueryService(
        BankDhoranDetailsRepository bankDhoranDetailsRepository,
        BankDhoranDetailsMapper bankDhoranDetailsMapper
    ) {
        this.bankDhoranDetailsRepository = bankDhoranDetailsRepository;
        this.bankDhoranDetailsMapper = bankDhoranDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link BankDhoranDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BankDhoranDetailsDTO> findByCriteria(BankDhoranDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<BankDhoranDetails> specification = createSpecification(criteria);
        return bankDhoranDetailsMapper.toDto(bankDhoranDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BankDhoranDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BankDhoranDetailsDTO> findByCriteria(BankDhoranDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria.toString().replaceAll("[\n\r\t]", "_"), page);
        final Specification<BankDhoranDetails> specification = createSpecification(criteria);
        return bankDhoranDetailsRepository.findAll(specification, page).map(bankDhoranDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BankDhoranDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria.toString().replaceAll("[\n\r\t]", "_"));
        final Specification<BankDhoranDetails> specification = createSpecification(criteria);
        return bankDhoranDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link BankDhoranDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BankDhoranDetails> createSpecification(BankDhoranDetailsCriteria criteria) {
        Specification<BankDhoranDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BankDhoranDetails_.id));
            }
            if (criteria.getDhoranName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDhoranName(), BankDhoranDetails_.dhoranName));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), BankDhoranDetails_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), BankDhoranDetails_.endDate));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), BankDhoranDetails_.year));
            }
            if (criteria.getIsActivate() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActivate(), BankDhoranDetails_.isActivate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), BankDhoranDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), BankDhoranDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), BankDhoranDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), BankDhoranDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), BankDhoranDetails_.freeField3));
            }
            if (criteria.getSocietyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSocietyId(),
                            root -> root.join(BankDhoranDetails_.society, JoinType.LEFT).get(Society_.id)
                        )
                    );
            }
        }
        return specification;
    }
}

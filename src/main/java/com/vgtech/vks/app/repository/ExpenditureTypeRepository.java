package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.ExpenditureType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ExpenditureType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExpenditureTypeRepository extends JpaRepository<ExpenditureType, Long>, JpaSpecificationExecutor<ExpenditureType> {}

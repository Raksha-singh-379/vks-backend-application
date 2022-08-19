package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.LoanDemand;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanDemand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanDemandRepository extends JpaRepository<LoanDemand, Long>, JpaSpecificationExecutor<LoanDemand> {}

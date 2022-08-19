package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.LoanDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanDetailsRepository extends JpaRepository<LoanDetails, Long>, JpaSpecificationExecutor<LoanDetails> {}

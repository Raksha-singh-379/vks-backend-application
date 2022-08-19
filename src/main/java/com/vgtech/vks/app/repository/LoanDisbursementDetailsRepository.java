package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.LoanDisbursementDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanDisbursementDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanDisbursementDetailsRepository
    extends JpaRepository<LoanDisbursementDetails, Long>, JpaSpecificationExecutor<LoanDisbursementDetails> {}

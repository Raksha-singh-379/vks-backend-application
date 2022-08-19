package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.LoanWatapDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanWatapDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanWatapDetailsRepository extends JpaRepository<LoanWatapDetails, Long>, JpaSpecificationExecutor<LoanWatapDetails> {}

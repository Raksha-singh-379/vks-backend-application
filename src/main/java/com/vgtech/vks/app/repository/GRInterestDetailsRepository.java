package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.GRInterestDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GRInterestDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GRInterestDetailsRepository extends JpaRepository<GRInterestDetails, Long>, JpaSpecificationExecutor<GRInterestDetails> {}

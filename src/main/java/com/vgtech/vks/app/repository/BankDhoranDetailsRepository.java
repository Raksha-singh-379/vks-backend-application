package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.BankDhoranDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankDhoranDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankDhoranDetailsRepository extends JpaRepository<BankDhoranDetails, Long>, JpaSpecificationExecutor<BankDhoranDetails> {}

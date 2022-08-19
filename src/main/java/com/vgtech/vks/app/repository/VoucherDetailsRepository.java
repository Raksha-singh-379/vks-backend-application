package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.VoucherDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VoucherDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherDetailsRepository extends JpaRepository<VoucherDetails, Long>, JpaSpecificationExecutor<VoucherDetails> {}

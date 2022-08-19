package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.VouchersHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VouchersHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VouchersHistoryRepository extends JpaRepository<VouchersHistory, Long>, JpaSpecificationExecutor<VouchersHistory> {}

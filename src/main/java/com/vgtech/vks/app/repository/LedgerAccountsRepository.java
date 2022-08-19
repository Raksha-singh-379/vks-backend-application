package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.LedgerAccounts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LedgerAccounts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LedgerAccountsRepository extends JpaRepository<LedgerAccounts, Long>, JpaSpecificationExecutor<LedgerAccounts> {}

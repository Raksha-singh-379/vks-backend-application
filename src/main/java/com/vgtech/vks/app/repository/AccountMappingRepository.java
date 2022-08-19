package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.AccountMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountMappingRepository extends JpaRepository<AccountMapping, Long>, JpaSpecificationExecutor<AccountMapping> {}

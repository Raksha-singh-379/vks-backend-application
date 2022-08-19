package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.SocietyBanksDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocietyBanksDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocietyBanksDetailsRepository
    extends JpaRepository<SocietyBanksDetails, Long>, JpaSpecificationExecutor<SocietyBanksDetails> {}

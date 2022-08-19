package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.SocietyCropRegistration;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocietyCropRegistration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocietyCropRegistrationRepository
    extends JpaRepository<SocietyCropRegistration, Long>, JpaSpecificationExecutor<SocietyCropRegistration> {}

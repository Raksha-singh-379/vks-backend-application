package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.SocietyNpaSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocietyNpaSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocietyNpaSettingRepository extends JpaRepository<SocietyNpaSetting, Long>, JpaSpecificationExecutor<SocietyNpaSetting> {}

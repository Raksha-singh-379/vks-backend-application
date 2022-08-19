package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.SocietyAssetsData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocietyAssetsData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocietyAssetsDataRepository extends JpaRepository<SocietyAssetsData, Long>, JpaSpecificationExecutor<SocietyAssetsData> {}

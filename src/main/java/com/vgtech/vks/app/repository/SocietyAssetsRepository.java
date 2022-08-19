package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.SocietyAssets;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocietyAssets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocietyAssetsRepository extends JpaRepository<SocietyAssets, Long>, JpaSpecificationExecutor<SocietyAssets> {}

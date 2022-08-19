package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.Village;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Village entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VillageRepository extends JpaRepository<Village, Long>, JpaSpecificationExecutor<Village> {}

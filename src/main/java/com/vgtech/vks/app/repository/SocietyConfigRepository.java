package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.SocietyConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocietyConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocietyConfigRepository extends JpaRepository<SocietyConfig, Long>, JpaSpecificationExecutor<SocietyConfig> {}

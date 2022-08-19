package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.SocietyPrerequisite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocietyPrerequisite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocietyPrerequisiteRepository
    extends JpaRepository<SocietyPrerequisite, Long>, JpaSpecificationExecutor<SocietyPrerequisite> {}

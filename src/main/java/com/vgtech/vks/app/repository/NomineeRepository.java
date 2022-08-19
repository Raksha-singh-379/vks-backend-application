package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.Nominee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nominee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NomineeRepository extends JpaRepository<Nominee, Long>, JpaSpecificationExecutor<Nominee> {}

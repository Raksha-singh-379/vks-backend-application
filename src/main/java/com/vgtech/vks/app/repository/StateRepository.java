package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.State;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the State entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateRepository extends JpaRepository<State, Long>, JpaSpecificationExecutor<State> {}

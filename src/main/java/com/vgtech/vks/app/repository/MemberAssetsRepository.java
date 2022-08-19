package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.MemberAssets;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberAssets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberAssetsRepository extends JpaRepository<MemberAssets, Long>, JpaSpecificationExecutor<MemberAssets> {}

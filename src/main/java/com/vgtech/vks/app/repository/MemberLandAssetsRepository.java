package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.MemberLandAssets;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberLandAssets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberLandAssetsRepository extends JpaRepository<MemberLandAssets, Long>, JpaSpecificationExecutor<MemberLandAssets> {}

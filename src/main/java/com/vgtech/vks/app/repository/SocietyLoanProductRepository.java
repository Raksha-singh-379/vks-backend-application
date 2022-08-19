package com.vgtech.vks.app.repository;

import com.vgtech.vks.app.domain.SocietyLoanProduct;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SocietyLoanProduct entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocietyLoanProductRepository
    extends JpaRepository<SocietyLoanProduct, Long>, JpaSpecificationExecutor<SocietyLoanProduct> {}

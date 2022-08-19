package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.VoucherDetails;
import com.vgtech.vks.app.service.dto.VoucherDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VoucherDetails} and its DTO {@link VoucherDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface VoucherDetailsMapper extends EntityMapper<VoucherDetailsDTO, VoucherDetails> {}

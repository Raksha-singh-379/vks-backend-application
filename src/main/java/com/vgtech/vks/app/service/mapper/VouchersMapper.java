package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Vouchers;
import com.vgtech.vks.app.service.dto.VouchersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vouchers} and its DTO {@link VouchersDTO}.
 */
@Mapper(componentModel = "spring")
public interface VouchersMapper extends EntityMapper<VouchersDTO, Vouchers> {}

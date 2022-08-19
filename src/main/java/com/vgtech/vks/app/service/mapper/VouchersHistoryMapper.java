package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.VouchersHistory;
import com.vgtech.vks.app.service.dto.VouchersHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VouchersHistory} and its DTO {@link VouchersHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface VouchersHistoryMapper extends EntityMapper<VouchersHistoryDTO, VouchersHistory> {}

package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Taluka;
import com.vgtech.vks.app.service.dto.TalukaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Taluka} and its DTO {@link TalukaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TalukaMapper extends EntityMapper<TalukaDTO, Taluka> {}

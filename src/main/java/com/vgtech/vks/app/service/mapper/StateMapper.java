package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.State;
import com.vgtech.vks.app.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring")
public interface StateMapper extends EntityMapper<StateDTO, State> {}

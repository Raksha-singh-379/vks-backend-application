package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.domain.Society;
import com.vgtech.vks.app.service.dto.MemberDTO;
import com.vgtech.vks.app.service.dto.SocietyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Member} and its DTO {@link MemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {
    @Mapping(target = "society", source = "society", qualifiedByName = "societyId")
    MemberDTO toDto(Member s);

    @Named("societyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SocietyDTO toDtoSocietyId(Society society);
}

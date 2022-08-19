package com.vgtech.vks.app.service.mapper;

import com.vgtech.vks.app.domain.Member;
import com.vgtech.vks.app.domain.MemberLandAssets;
import com.vgtech.vks.app.service.dto.MemberDTO;
import com.vgtech.vks.app.service.dto.MemberLandAssetsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MemberLandAssets} and its DTO {@link MemberLandAssetsDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberLandAssetsMapper extends EntityMapper<MemberLandAssetsDTO, MemberLandAssets> {
    @Mapping(target = "member", source = "member", qualifiedByName = "memberId")
    MemberLandAssetsDTO toDto(MemberLandAssets s);

    @Named("memberId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MemberDTO toDtoMemberId(Member member);
}

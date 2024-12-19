package com.javangers.hunters_league.web.vm.mapper;


import com.javangers.hunters_league.domain.Hunt;
import com.javangers.hunters_league.web.vm.HuntResultDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HuntMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "participation.id", source = "participantId")
    @Mapping(target = "species.id", source = "speciesId")
    Hunt toEntity(HuntResultDTO dto);

}

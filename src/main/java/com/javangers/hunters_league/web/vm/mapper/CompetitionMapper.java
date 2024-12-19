package com.javangers.hunters_league.web.vm.mapper;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.web.vm.CompetitionRequestVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "openRegistration", constant = "true")
    Competition toEntity(CompetitionRequestVM vm);

}


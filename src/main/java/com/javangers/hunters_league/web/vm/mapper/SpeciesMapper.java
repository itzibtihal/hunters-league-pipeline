package com.javangers.hunters_league.web.vm.mapper;

import com.javangers.hunters_league.domain.Species;
import com.javangers.hunters_league.web.vm.SpeciesVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SpeciesMapper {

//    @Mapping(source = "name", target = "name")
//    @Mapping(source = "category", target = "category")
//    @Mapping(source = "minimumWeight", target = "minimumWeight")
//    @Mapping(source = "difficulty", target = "difficulty")
//    @Mapping(source = "points", target = "points")
//    Species toEntity(SpeciesVM speciesVM);

    Species toEntity(SpeciesVM speciesVM);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromVM(SpeciesVM speciesVM, @MappingTarget Species species);


}
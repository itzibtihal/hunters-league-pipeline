package com.javangers.hunters_league.web.vm.mapper;

import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.web.vm.RegisterRequestVM;
import com.javangers.hunters_league.web.vm.UserResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "nationality", target = "nationality")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "licenseExpirationDate", target = "licenseExpirationDate")
    User toEntity(RegisterRequestVM registerRequestVM);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "licenseExpirationDate", target = "licenseExpirationDate")
    @Mapping(source = "role", target = "role")
    UserResponseVM toResponseVM(User user);


}

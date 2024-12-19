package com.javangers.hunters_league.web.vm.mapper;

import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.web.vm.LoginRequestVM;
import com.javangers.hunters_league.web.vm.UserResponseVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface LoginMapper {

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User toEntity(LoginRequestVM loginRequestVM);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    UserResponseVM toResponseVM(User user);
}
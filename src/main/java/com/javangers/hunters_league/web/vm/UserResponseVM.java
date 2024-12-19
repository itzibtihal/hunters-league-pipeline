package com.javangers.hunters_league.web.vm;

import com.javangers.hunters_league.domain.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserResponseVM {
    private String id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String nationality;
    private String role;
    private LocalDateTime licenseExpirationDate;
}

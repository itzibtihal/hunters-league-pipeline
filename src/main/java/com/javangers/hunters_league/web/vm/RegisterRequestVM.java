package com.javangers.hunters_league.web.vm;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterRequestVM {
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not in a valid format")
    private String email;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;


    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%.!*?&])[A-Za-z\\d@$!%.!*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    private String password;

    @NotBlank(message = "FirstName is required")
    @Size(min = 2, max = 20, message = "FirstName must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "LastName is required")
    @Size(min = 2, max = 20, message = "LastName must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "cin is required")
    @Size(min = 8, max = 8, message = "cin must be exactly 8 characters")
    private String cin;

    @NotBlank(message = "Nationality is required")
    @Size(min = 2, max = 20, message = "Nationality must be between 2 and 50 characters")
    private String nationality;

    @NotNull(message = "license Expiration Date is required")
    @Future(message = "license Expiration Date must be in the future")
    LocalDateTime licenseExpirationDate;
}
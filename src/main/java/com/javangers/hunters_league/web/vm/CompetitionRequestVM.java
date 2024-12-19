package com.javangers.hunters_league.web.vm;

import com.javangers.hunters_league.domain.enumeration.SpeciesType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompetitionRequestVM {
    @NotBlank(message = "Code is required")
    @Pattern(regexp = "^[A-Za-z]+-\\d{4}-\\d{2}-\\d{2}$",
            message = "Code must match pattern: location-yyyy-MM-dd")
    String code;

    @NotBlank(message = "Location is required")
    String location;

    @NotNull(message = "Date is required")
    @Future(message = "Competition date must be in the future")
    LocalDateTime date;

    @NotNull(message = "Species type is required")
    SpeciesType speciesType;

    @NotNull(message = "Minimum participants is required")
    @Min(value = 2, message = "Minimum participants must be at least 1")
    Integer minParticipants;

    @NotNull(message = "Maximum participants is required")
    Integer maxParticipants;
}

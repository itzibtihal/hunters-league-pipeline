package com.javangers.hunters_league.web.vm;

import com.javangers.hunters_league.domain.enumeration.Difficulty;
import com.javangers.hunters_league.domain.enumeration.SpeciesType;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class SpeciesVM {
    @NotBlank(message = "Species name cannot be blank")
    @Size(min = 2, max = 100, message = "Species name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Species category is required")
    private SpeciesType category;

    @Min(value = 0, message = "Minimum weight must be non-negative")
    @Max(value = 1000, message = "Minimum weight cannot exceed 1000")
    private Double minimumWeight;

    @NotNull(message = "Difficulty level is required")
    private Difficulty difficulty;

    @Min(value = 0, message = "Points must be non-negative")
    @Max(value = 1000, message = "Points cannot exceed 1000")
    private Integer points;
}

package com.javangers.hunters_league.web.vm;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class HuntResultDTO {
    @NotNull(message = "Participant ID is required")
    private UUID participantId;

    @NotNull(message = "Species ID is required")
    private UUID speciesId;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    private Double weight;
}
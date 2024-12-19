package com.javangers.hunters_league.web.vm;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class ParticipationRequestVM {
    @NotNull(message = "User ID is required")
    UUID userId;

}

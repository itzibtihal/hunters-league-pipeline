package com.javangers.hunters_league.service;

import com.javangers.hunters_league.service.dto.ParticipationDTO;

import java.util.UUID;

public interface ParticipationService {
    ParticipationDTO registerForCompetition(UUID competitionId, UUID userId);
}

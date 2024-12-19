package com.javangers.hunters_league.service;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.service.dto.LeaderboardPositionDTO;

import java.util.List;
import java.util.UUID;

public interface CompetitionService {
    Competition createCompetition(Competition competition);
    Competition getCompetition(UUID id);
    List<LeaderboardPositionDTO> getCompetitionLeaderboard(UUID competitionId);
}

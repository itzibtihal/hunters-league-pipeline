package com.javangers.hunters_league.service.dto;

import java.util.UUID;

public interface LeaderboardProjectionInterface {
    String getUsername();

    String getCompetitionCode();

    UUID getCompetitionId();

    Double getScore();
}
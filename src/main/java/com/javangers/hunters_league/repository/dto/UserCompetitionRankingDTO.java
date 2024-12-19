package com.javangers.hunters_league.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCompetitionRankingDTO {
    private UUID competitionId;
    private Date competitionDate;
    private double userTotalScore;
    private int userRank;
}

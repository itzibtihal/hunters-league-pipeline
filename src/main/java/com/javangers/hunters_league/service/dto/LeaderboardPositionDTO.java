package com.javangers.hunters_league.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardPositionDTO {
    private String userFullName;
    private Double score;
    private Integer rank;
    private String competitionCode;
}
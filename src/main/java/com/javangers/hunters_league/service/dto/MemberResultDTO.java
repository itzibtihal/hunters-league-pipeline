package com.javangers.hunters_league.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResultDTO {
    private UUID userId;
    private String userName;
    private UUID participationId;
    private String competitionCode;
    private Long totalHunts;
    private Double score;
}
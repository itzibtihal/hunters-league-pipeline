package com.javangers.hunters_league.service.dto;


import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDTO {
    private UUID id;
    private UUID user_id;
    private UUID competition_id;
    private Double score;
}
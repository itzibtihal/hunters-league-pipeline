package com.javangers.hunters_league.service.dto.mapper;

import com.javangers.hunters_league.domain.Participation;
import com.javangers.hunters_league.service.dto.ParticipationDTO;

public class ParticipationMapper {
    public static ParticipationDTO toDTO(Participation participation) {
        return ParticipationDTO.builder()
                .id(participation.getId())
                .user_id(participation.getUser().getId())
                .competition_id(participation.getCompetition().getId())
                .score(participation.getScore())
                .build();
    }
}
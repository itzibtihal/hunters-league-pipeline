package com.javangers.hunters_league.repository;

import com.javangers.hunters_league.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation, UUID> {
    boolean existsByUserIdAndCompetitionId(UUID userId, UUID competitionId);
    long countByCompetitionId(UUID competitionId);
}

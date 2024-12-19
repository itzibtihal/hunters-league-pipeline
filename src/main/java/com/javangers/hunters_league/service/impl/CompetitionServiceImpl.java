package com.javangers.hunters_league.service.impl;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.repository.CompetitionRepository;
import com.javangers.hunters_league.service.CompetitionService;
import com.javangers.hunters_league.service.dto.LeaderboardPositionDTO;
import com.javangers.hunters_league.service.dto.LeaderboardProjectionInterface;
import com.javangers.hunters_league.web.errors.BusinessValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;


    @Override
    public Competition createCompetition(Competition competition) {
        validateCompetition(competition);
        return competitionRepository.save(competition);
    }


    @Override
    public Competition getCompetition(UUID id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Competition not found with id: " + id));
    }

    private void validateCompetition(Competition competition) {
        // Validate min/max participants
        if (competition.getMinParticipants() >= competition.getMaxParticipants()) {
            throw new BusinessValidationException("Minimum participants must be less than maximum participants");
        }

        // Validate code format
        String expectedCode = String.format("%s-%s", competition.getLocation(), competition.getDate().toLocalDate());
        if (!competition.getCode().equals(expectedCode)) {
            throw new BusinessValidationException("Invalid competition code format");
        }

        // Validate one competition per week
        LocalDateTime weekStart = competition.getDate().truncatedTo(ChronoUnit.DAYS).with(DayOfWeek.MONDAY);
        LocalDateTime weekEnd = weekStart.plusDays(6);

        if (competitionRepository.existsInWeek(weekStart, weekEnd)) {
            throw new BusinessValidationException("Only one competition per week is allowed");
        }
    }

    @Override
    @Transactional
    public List<LeaderboardPositionDTO> getCompetitionLeaderboard(UUID competitionId) {
        List<LeaderboardProjectionInterface> leaderboard = competitionRepository.findTop3ByCompetitionIdNative(competitionId);

        if (leaderboard.isEmpty()) {
            throw new BusinessValidationException("No participants found in this competition");
        }

        return leaderboard.stream()
                .map(projection -> LeaderboardPositionDTO.builder()
                        .userFullName(projection.getUsername())
                        .score(projection.getScore())
                        .rank(leaderboard.indexOf(projection) + 1)
                        .competitionCode(projection.getCompetitionCode())
                        .build())
                .collect(Collectors.toList());
    }

    public List<LeaderboardPositionDTO> getCompetitiondfgdfgLeaderboard(UUID competitionId) {
        List<LeaderboardProjectionInterface> leaderboard = competitionRepository.findTop3ByCompetitionIdNative(competitionId);

        if (leaderboard.isEmpty()) {
            throw new BusinessValidationException("No particvbcipants found in this competition");
        }
        // hada wkan
        // hada wkan 2
        return leaderboard.stream()
                .map(projection -> LeaderboardPositionDTO.builder()
                        .userFullName(projection.getUsername())
                        .score(projection.getScore())
                        .rank(leaderboard.indexOf(projection) + 1)
                        .competitionCode(projection.getCompetitionCode())
                        .build())
                .collect(Collectors.toList());
    }

    public List<LeaderboardPositionDTO> getCompesqsdqsdtitiondfgdfgLeaderboard(UUID competitionId) {
        List<LeaderboardProjectionInterface> leaderboard = competitionRepository.findTop3ByCompetitionIdNative(competitionId);

        if (leaderboard.isEmpty()) {
            throw new BusinessValidationException("No particvbcipants found in this competition");
        }
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        // hada wkan
        // hada wkan 2
        return leaderboard.stream()
                .map(projection -> LeaderboardPositionDTO.builder()
                        .userFullName(projection.getUsername())
                        .score(projection.getScore())
                        .rank(leaderboard.indexOf(projection) + 1)
                        .competitionCode(projection.getCompetitionCode())
                        .build())
                .collect(Collectors.toList());
    }

}
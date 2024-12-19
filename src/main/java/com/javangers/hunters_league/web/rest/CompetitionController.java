package com.javangers.hunters_league.web.rest;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.Participation;
import com.javangers.hunters_league.service.CompetitionService;
import com.javangers.hunters_league.service.ParticipationService;
import com.javangers.hunters_league.service.dto.LeaderboardPositionDTO;
import com.javangers.hunters_league.service.dto.ParticipationDTO;
import com.javangers.hunters_league.web.vm.CompetitionRequestVM;
import com.javangers.hunters_league.web.vm.ParticipationRequestVM;
import com.javangers.hunters_league.web.vm.mapper.CompetitionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class CompetitionController {

    private final CompetitionService competitionService;
    private final CompetitionMapper competitionMapper;
    private final ParticipationService participationService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Competition> CreateSpecies(@Valid @RequestBody CompetitionRequestVM request) {
        Competition competition = competitionMapper.toEntity(request);
        Competition created = competitionService.createCompetition(competition);
        return ResponseEntity.created(URI.create("/api/competitions/" + created.getId()))
                .body(created);

    }

    @GetMapping("/{competitionId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<Competition> getCompetition(
            @PathVariable UUID competitionId) {
        Competition competition = competitionService.getCompetition(competitionId);
        return ResponseEntity.ok(competition);
    }

    @PostMapping("/{competitionId}/register")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<ParticipationDTO> registerForCompetition(
            @PathVariable UUID competitionId,
            @Valid @RequestBody ParticipationRequestVM request) {

        ParticipationDTO participation = participationService.registerForCompetition(
                competitionId,
                request.getUserId()
        );

        return ResponseEntity.ok(participation);
    }

    @GetMapping("/{competitionId}/leaderboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEMBER')")
    public ResponseEntity<List<LeaderboardPositionDTO>> getCompetitionLeaderboard(
            @PathVariable UUID competitionId) {
        List<LeaderboardPositionDTO> leaderboard = competitionService.getCompetitionLeaderboard(competitionId);
        return ResponseEntity.ok(leaderboard);
    }

}

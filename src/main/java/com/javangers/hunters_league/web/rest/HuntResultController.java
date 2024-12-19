package com.javangers.hunters_league.web.rest;


import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.Hunt;
import com.javangers.hunters_league.repository.dto.UserCompetitionRankingDTO;
import com.javangers.hunters_league.service.HuntService;
import com.javangers.hunters_league.service.MemberResultsService;
import com.javangers.hunters_league.service.dto.MemberResultDTO;
import com.javangers.hunters_league.web.vm.HuntResultDTO;
import com.javangers.hunters_league.web.vm.MemberResultsFilterDTO;
import com.javangers.hunters_league.web.vm.PageResponse;
import com.javangers.hunters_league.web.vm.mapper.HuntMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class HuntResultController {

    private final HuntService huntService;
    private final MemberResultsService memberResultsService;
    private final HuntMapper huntMapper;

    @PostMapping("/competition/{competitionId}")
    public ResponseEntity<Hunt> submitCompetitionResult(
            @PathVariable UUID competitionId,
            @Valid @RequestBody HuntResultDTO resultDTO) {

        Hunt hunt = huntMapper.toEntity(resultDTO);
        hunt.getParticipation().setCompetition(Competition.builder().id(competitionId).build());
        Hunt savedHunt = huntService.submitCompetitionResult(hunt);

        return ResponseEntity.ok(savedHunt);
    }

    @GetMapping("/member/{userId}")
    public ResponseEntity<PageResponse<MemberResultDTO>> getMemberResults(
            @PathVariable UUID userId,
            @Valid MemberResultsFilterDTO filterDTO) {

        Page<MemberResultDTO> results = memberResultsService.getMemberResults(userId, filterDTO);
        return ResponseEntity.ok(PageResponse.of(results));
    }

    @GetMapping("/member/{userId}/rank")
    public ResponseEntity<PageResponse<UserCompetitionRankingDTO>> getMemberRankResults(
            @PathVariable UUID userId,
            @Valid MemberResultsFilterDTO filterDTO) {

        Page<UserCompetitionRankingDTO> results = memberResultsService.getMemberRankResults(userId, filterDTO);
        return ResponseEntity.ok(PageResponse.of(results));
    }

}

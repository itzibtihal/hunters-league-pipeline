package com.javangers.hunters_league.service;

import com.javangers.hunters_league.repository.dto.UserCompetitionRankingDTO;
import com.javangers.hunters_league.service.dto.MemberResultDTO;
import com.javangers.hunters_league.web.vm.MemberResultsFilterDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface MemberResultsService {
    Page<MemberResultDTO> getMemberResults(UUID userId, MemberResultsFilterDTO filterDTO);
    Page<UserCompetitionRankingDTO> getMemberRankResults(UUID userId, MemberResultsFilterDTO filterDTO);
}

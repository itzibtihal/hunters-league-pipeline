package com.javangers.hunters_league.service.impl;


import com.javangers.hunters_league.repository.MemberResultsRepository;
import com.javangers.hunters_league.repository.UserRepository;
import com.javangers.hunters_league.repository.dto.UserCompetitionRankingDTO;
import com.javangers.hunters_league.service.MemberResultsService;
import com.javangers.hunters_league.service.dto.MemberResultDTO;
import com.javangers.hunters_league.web.errors.BusinessValidationException;
import com.javangers.hunters_league.web.errors.UserNotFoundException;
import com.javangers.hunters_league.web.vm.MemberResultsFilterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberResultsServiceImpl implements MemberResultsService {
    private final MemberResultsRepository memberResultsRepository;
    private final UserRepository userRepository;

    @Override
    public Page<MemberResultDTO> getMemberResults(UUID userId, MemberResultsFilterDTO filterDTO) {
        validateUser(userId);

        // Convert dates and set time boundaries
        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (filterDTO.getStartDate() != null) {
            startDate = filterDTO.getStartDate().atStartOfDay();
        }

        if (filterDTO.getEndDate() != null) {
            endDate = filterDTO.getEndDate().atTime(23, 59, 59);
        }

        validateDateRange(startDate, endDate);

        return memberResultsRepository.findMemberResults(
                userId,
                startDate,
                endDate,
                PageRequest.of(filterDTO.getPage(), filterDTO.getSize())
        );
    }



    public void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BusinessValidationException("Start date must be before end date");
        }
    }

    public Page<UserCompetitionRankingDTO> getMemberRankResults(UUID userId, MemberResultsFilterDTO filterDTO) {
        validateUser(userId);

        return memberResultsRepository.findMemberRankResults(
                userId,
                PageRequest.of(filterDTO.getPage(), filterDTO.getSize())
        );
    }

    private void validateUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
    }

}
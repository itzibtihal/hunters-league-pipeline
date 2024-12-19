package com.javangers.hunters_league;

import com.javangers.hunters_league.repository.MemberResultsRepository;
import com.javangers.hunters_league.repository.UserRepository;
import com.javangers.hunters_league.repository.dto.UserCompetitionRankingDTO;
import com.javangers.hunters_league.service.dto.MemberResultDTO;
import com.javangers.hunters_league.service.impl.MemberResultsServiceImpl;
import com.javangers.hunters_league.web.errors.BusinessValidationException;
import com.javangers.hunters_league.web.errors.UserNotFoundException;
import com.javangers.hunters_league.web.vm.MemberResultsFilterDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberResultsServiceImplTest {

    @Mock
    private MemberResultsRepository memberResultsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MemberResultsServiceImpl memberResultsService;

    private UUID userId;
    private MemberResultsFilterDTO filterDTO;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        filterDTO = new MemberResultsFilterDTO();
        filterDTO.setPage(0);
        filterDTO.setSize(10);
    }

    @Test
    void getMemberResultsUserExistsNoDateFilter() {
        // Arrange
        when(userRepository.existsById(userId)).thenReturn(true);

        Page<MemberResultDTO> mockPage = mock(Page.class);
        when(memberResultsRepository.findMemberResults(
                eq(userId),
                isNull(),
                isNull(),
                any(Pageable.class)
        )).thenReturn(mockPage);

        // Act
        Page<MemberResultDTO> results = memberResultsService.getMemberResults(userId, filterDTO);

        // Assert
        assertNotNull(results);
        verify(userRepository).existsById(userId);
        verify(memberResultsRepository).findMemberResults(
                eq(userId),
                isNull(),
                isNull(),
                any(Pageable.class)
        );
    }

    @Test
    void getMemberResultsWithDateFilter() {
        // Arrange
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();

        filterDTO.setStartDate(startDate);
        filterDTO.setEndDate(endDate);

        when(userRepository.existsById(userId)).thenReturn(true);

        Page<MemberResultDTO> mockPage = mock(Page.class);
        when(memberResultsRepository.findMemberResults(
                eq(userId),
                eq(startDate.atStartOfDay()),
                eq(endDate.atTime(23, 59, 59)),
                any(Pageable.class)
        )).thenReturn(mockPage);

        // Act
        Page<MemberResultDTO> results = memberResultsService.getMemberResults(userId, filterDTO);

        // Assert
        assertNotNull(results);
        verify(userRepository).existsById(userId);
        verify(memberResultsRepository).findMemberResults(
                eq(userId),
                eq(startDate.atStartOfDay()),
                eq(endDate.atTime(23, 59, 59)),
                any(Pageable.class)
        );
    }

    @Test
    void getMemberResultsUserNotFound() {
        // Arrange
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        assertThrows(UserNotFoundException.class,
                () -> memberResultsService.getMemberResults(userId, filterDTO)
        );
    }

    @Test
    void validateDateRangeStartDateAfterEndDate() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now();

        // Act & Assert
        BusinessValidationException exception = assertThrows(
                BusinessValidationException.class,
                () -> memberResultsService.validateDateRange(startDate, endDate)
        );
        assertEquals("Start date must be before end date", exception.getMessage());
    }

    @Test
    void validateDateRangeNullDates() {
        // Act & Assert
        assertDoesNotThrow(() ->
                memberResultsService.validateDateRange(null, null)
        );
    }

    @Test
    void getMemberRankResultsUserExists() {
        // Arrange
        when(userRepository.existsById(userId)).thenReturn(true);

        Page<UserCompetitionRankingDTO> mockPage = mock(Page.class);
        when(memberResultsRepository.findMemberRankResults(
                eq(userId),
                any(Pageable.class)
        )).thenReturn(mockPage);

        // Act
        Page<UserCompetitionRankingDTO> results = memberResultsService.getMemberRankResults(userId, filterDTO);

        // Assert
        assertNotNull(results);
        verify(userRepository).existsById(userId);
        verify(memberResultsRepository).findMemberRankResults(
                eq(userId),
                any(Pageable.class)
        );
    }

    @Test
    void getMemberRankResultsUserNotFound() {
        // Arrange
        when(userRepository.existsById(userId)).thenReturn(false);

        // Act & Assert
        assertThrows(UserNotFoundException.class,
                () -> memberResultsService.getMemberRankResults(userId, filterDTO)
        );
    }
}
package com.javangers.hunters_league;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.repository.CompetitionRepository;
import com.javangers.hunters_league.service.dto.LeaderboardPositionDTO;
import com.javangers.hunters_league.service.dto.LeaderboardProjectionInterface;
import com.javangers.hunters_league.service.impl.CompetitionServiceImpl;
import com.javangers.hunters_league.web.errors.BusinessValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompetitionServiceImplTest {

    @Mock
    private CompetitionRepository competitionRepository;

    @InjectMocks
    private CompetitionServiceImpl competitionService;

    private Competition validCompetition;

    @BeforeEach
    void setUp() {
        // Create a valid competition for testing
        validCompetition = new Competition();
        validCompetition.setId(UUID.randomUUID());
        validCompetition.setLocation("CITY");
        validCompetition.setDate(LocalDateTime.now());
        validCompetition.setMinParticipants(10);
        validCompetition.setMaxParticipants(50);
        validCompetition.setCode(String.format("%s-%s",
                validCompetition.getLocation(),
                validCompetition.getDate().toLocalDate()));
    }

    @Test
    void createCompetitionValidCompetition_Success() {
        // Arrange
        when(competitionRepository.existsInWeek(any(), any())).thenReturn(false);
        when(competitionRepository.save(any(Competition.class))).thenReturn(validCompetition);

        // Act
        Competition createdCompetition = competitionService.createCompetition(validCompetition);

        // Assert
        assertNotNull(createdCompetition);
        verify(competitionRepository).save(validCompetition);
    }

    @Test
    void createCompetitionInvalidMinMaxParticipants_ThrowsException() {
        // Arrange
        validCompetition.setMinParticipants(60);
        validCompetition.setMaxParticipants(50);

        // Act & Assert
        assertThrows(BusinessValidationException.class, () ->
                competitionService.createCompetition(validCompetition));
    }

    @Test
    void createCompetitionInvalidCompetitionCode_ThrowsException() {
        // Arrange
        validCompetition.setCode("INVALID-CODE");

        // Act & Assert
        assertThrows(BusinessValidationException.class, () ->
                competitionService.createCompetition(validCompetition));
    }

    @Test
    void createCompetitionCompetitionAlreadyExistsInWeek_ThrowsException() {
        // Arrange
        when(competitionRepository.existsInWeek(any(), any())).thenReturn(true);

        // Act & Assert
        assertThrows(BusinessValidationException.class, () ->
                competitionService.createCompetition(validCompetition));
    }

    @Test
    void getCompetitionExistingCompetition_ReturnsCompetition() {
        // Arrange
        UUID competitionId = validCompetition.getId();
        when(competitionRepository.findById(competitionId))
                .thenReturn(Optional.of(validCompetition));

        // Act
        Competition retrievedCompetition = competitionService.getCompetition(competitionId);

        // Assert
        assertNotNull(retrievedCompetition);
        assertEquals(validCompetition, retrievedCompetition);
    }

    @Test
    void getCompetitionNonExistingCompetition_ThrowsEntityNotFoundException() {
        // Arrange
        UUID randomId = UUID.randomUUID();
        when(competitionRepository.findById(randomId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () ->
                competitionService.getCompetition(randomId));
    }

    @Test
    void getCompetitionLeaderboardValidCompetition_ReturnsLeaderboard() {
        // Arrange
        UUID competitionId = validCompetition.getId();

        // Mock leaderboard projection
        LeaderboardProjectionInterface projection1 = mock(LeaderboardProjectionInterface.class);
        when(projection1.getUsername()).thenReturn("User1");
        when(projection1.getScore()).thenReturn(100.0);
        when(projection1.getCompetitionCode()).thenReturn(validCompetition.getCode());

        LeaderboardProjectionInterface projection2 = mock(LeaderboardProjectionInterface.class);
        when(projection2.getUsername()).thenReturn("User2");
        when(projection2.getScore()).thenReturn(90.0);
        when(projection2.getCompetitionCode()).thenReturn(validCompetition.getCode());

        when(competitionRepository.findTop3ByCompetitionIdNative(competitionId))
                .thenReturn(Arrays.asList(projection1, projection2));

        // Act
        List<LeaderboardPositionDTO> leaderboard = competitionService.getCompetitionLeaderboard(competitionId);

        // Assert
        assertNotNull(leaderboard);
        assertEquals(2, leaderboard.size());
        assertEquals("User1", leaderboard.get(0).getUserFullName());
        assertEquals(100, leaderboard.get(0).getScore());
        assertEquals(1, leaderboard.get(0).getRank());
        assertEquals("User2", leaderboard.get(1).getUserFullName());
        assertEquals(90, leaderboard.get(1).getScore());
        assertEquals(2, leaderboard.get(1).getRank());
    }

    @Test
    void getCompetitionLeaderboardNoParticipants_ThrowsException() {
        // Arrange
        UUID competitionId = validCompetition.getId();
        when(competitionRepository.findTop3ByCompetitionIdNative(competitionId))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(BusinessValidationException.class, () ->
                competitionService.getCompetitionLeaderboard(competitionId));
    }
}
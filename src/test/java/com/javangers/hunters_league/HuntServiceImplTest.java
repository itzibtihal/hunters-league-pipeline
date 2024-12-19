package com.javangers.hunters_league;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.Hunt;
import com.javangers.hunters_league.domain.Participation;
import com.javangers.hunters_league.domain.Species;
import com.javangers.hunters_league.domain.enumeration.Difficulty;
import com.javangers.hunters_league.domain.enumeration.SpeciesType;
import com.javangers.hunters_league.repository.HuntRepository;
import com.javangers.hunters_league.repository.ParticipationRepository;
import com.javangers.hunters_league.repository.SpeciesRepository;
import com.javangers.hunters_league.service.impl.HuntServiceImpl;
import com.javangers.hunters_league.web.errors.BusinessValidationException;
import com.javangers.hunters_league.web.vm.mapper.HuntMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HuntServiceImplTest {

    @Mock
    private HuntRepository huntRepository;

    @Mock
    private ParticipationRepository participationRepository;

    @Mock
    private SpeciesRepository speciesRepository;

    @Mock
    private HuntMapper huntMapper;

    @InjectMocks
    private HuntServiceImpl huntService;

    private Participation participation;
    private Species species;
    private Hunt hunt;

    @BeforeEach
    void setUp() {
        // Create a mock competition
        Competition competition = new Competition();
        competition.setId(UUID.randomUUID());

        // Create a mock participation
        participation = new Participation();
        participation.setId(UUID.randomUUID());
        participation.setCompetition(competition);
        participation.setHunts(new ArrayList<>());
        participation.setScore(0.0);

        // Create a mock species
        species = new Species();
        species.setId(UUID.randomUUID());
        species.setName("Test Species");
        species.setMinimumWeight(5.0);
        species.setPoints(10);

        // Species Category and Difficulty setup
        species.setCategory(SpeciesType.BIRD);
        species.setDifficulty(Difficulty.EPIC);

        // Create a mock hunt
        hunt = new Hunt();
        hunt.setId(UUID.randomUUID());
        hunt.setWeight(7.0);
        hunt.setParticipation(participation);
        hunt.setSpecies(species);
    }

    @Test
    void submitCompetitionResultSuccessful() {
        // Arrange
        when(participationRepository.findById(participation.getId()))
                .thenReturn(Optional.of(participation));
        when(speciesRepository.findById(species.getId()))
                .thenReturn(Optional.of(species));
        when(huntRepository.save(any(Hunt.class)))
                .thenReturn(hunt);

        // Act
        Hunt savedHunt = huntService.submitCompetitionResult(hunt);

        // Assert
        assertNotNull(savedHunt);
        verify(huntRepository).save(hunt);
        verify(participationRepository).save(participation);

        // Verify score calculation
        double expectedPoints = calculateExpectedPoints(hunt);
        assertEquals(expectedPoints, participation.getScore(), 0.001);
    }

    @Test
    void submitCompetitionResultParticipantNotFound() {
        // Arrange
        when(participationRepository.findById(participation.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        BusinessValidationException exception = assertThrows(
                BusinessValidationException.class,
                () -> huntService.submitCompetitionResult(hunt)
        );
        assertEquals("Participant not found", exception.getMessage());
    }

    @Test
    void submitCompetitionResultCompetitionMismatch() {
        // Arrange
        Participation wrongParticipation = new Participation();
        Competition wrongCompetition = new Competition();
        wrongCompetition.setId(UUID.randomUUID());
        wrongParticipation.setId(UUID.randomUUID());
        wrongParticipation.setCompetition(wrongCompetition);

        hunt.setParticipation(wrongParticipation);

        when(participationRepository.findById(wrongParticipation.getId()))
                .thenReturn(Optional.of(wrongParticipation));

        // Act & Assert
        assertThrows(
                BusinessValidationException.class,
                () -> huntService.submitCompetitionResult(hunt)
        );
    }

    @Test
    void submitCompetitionResultSpeciesNotFound() {
        // Arrange
        when(participationRepository.findById(participation.getId()))
                .thenReturn(Optional.of(participation));
        when(speciesRepository.findById(species.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        BusinessValidationException exception = assertThrows(
                BusinessValidationException.class,
                () -> huntService.submitCompetitionResult(hunt)
        );
        assertEquals("Species not found", exception.getMessage());
    }

    @Test
    void submitCompetitionResultWeightTooLow() {
        // Arrange
        hunt.setWeight(4.0); // Below minimum weight

        when(participationRepository.findById(participation.getId()))
                .thenReturn(Optional.of(participation));
        when(speciesRepository.findById(species.getId()))
                .thenReturn(Optional.of(species));

        // Act & Assert
        BusinessValidationException exception = assertThrows(
                BusinessValidationException.class,
                () -> huntService.submitCompetitionResult(hunt)
        );
        assertTrue(exception.getMessage().contains("Weight must be at least"));
    }

    // Helper method to calculate expected points (matching service logic)
    private double calculateExpectedPoints(Hunt hunt) {
        Species s = hunt.getSpecies();
        double pointsAssociated = s.getPoints();
        double weight = hunt.getWeight();
        int speciesFactor = s.getCategory().getValue();
        int difficultyFactor = s.getDifficulty().getValue();

        return pointsAssociated + (weight * speciesFactor) * difficultyFactor;
    }
}
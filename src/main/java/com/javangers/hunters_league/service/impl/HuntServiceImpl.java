package com.javangers.hunters_league.service.impl;

import com.javangers.hunters_league.domain.Hunt;
import com.javangers.hunters_league.domain.Participation;
import com.javangers.hunters_league.domain.Species;
import com.javangers.hunters_league.repository.HuntRepository;
import com.javangers.hunters_league.repository.ParticipationRepository;
import com.javangers.hunters_league.repository.SpeciesRepository;
import com.javangers.hunters_league.service.HuntService;
import com.javangers.hunters_league.service.ParticipationService;
import com.javangers.hunters_league.web.errors.BusinessValidationException;
import com.javangers.hunters_league.web.vm.mapper.HuntMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HuntServiceImpl implements HuntService {
    private final HuntRepository huntRepository;
    private final ParticipationRepository participationRepository;
    private final SpeciesRepository speciesRepository;
    private final HuntMapper huntMapper;


    @Transactional
    public Hunt submitCompetitionResult(Hunt hunt) {
        // Validate participation
        Participation participation = participationRepository.findById(hunt.getParticipation().getId())
                .orElseThrow(() -> new BusinessValidationException("Participant not found"));

        // Validate competition match
        if (!participation.getCompetition().getId().equals(hunt.getParticipation().getCompetition().getId())) {
            throw new BusinessValidationException("Participant is not registered for this competition");
        }


        // Get and validate species
        Species species = speciesRepository.findById(hunt.getSpecies().getId())
                .orElseThrow(() -> new BusinessValidationException("Species not found"));

        // Validate weight
        if (hunt.getWeight() < species.getMinimumWeight()) {
            throw new BusinessValidationException(
                    String.format("Weight must be at least %.2f for species %s",
                            species.getMinimumWeight(),
                            species.getName())
            );
        }

        hunt.setParticipation(participation);
        hunt.setSpecies(species);

//      Calculate and update score
        calculateAndUpdateScore(participation, hunt);
        return huntRepository.save(hunt);

    }

    private void calculateAndUpdateScore(Participation participation, Hunt newHunt) {
        // Add new hunt to participation's hunts
        participation.getHunts().add(newHunt);

        // Calculate new score based on all hunts
//        double totalScore = participation.getHunts().stream()
//                .mapToDouble(this::calculateHuntPoints)
//                .sum();

        double newHuntPoints = calculateHuntPoints(newHunt);
        double updatedScore = participation.getScore() + newHuntPoints;
        participation.setScore(updatedScore);


//        participation.setScore(totalScore);
        participationRepository.save(participation);
    }

    private double calculateHuntPoints(Hunt hunt) {
        Species species = hunt.getSpecies();

        // Get the base points associated with the species
        double pointsAssociated = species.getPoints();

        // Get the weight from the hunt
        double weight = hunt.getWeight();

        // Get the species factor from the category (SpeciesType)
        int speciesFactor = species.getCategory().getValue();

        // Get the difficulty factor
        int difficultyFactor = species.getDifficulty().getValue();

        // Apply the formula: (Points Associated) + (Weight × Species Factor) × Difficulty Factor
        return pointsAssociated + (weight * speciesFactor) * difficultyFactor;
    }

}

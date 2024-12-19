package com.javangers.hunters_league.service.impl;

import com.javangers.hunters_league.domain.Competition;
import com.javangers.hunters_league.domain.Participation;
import com.javangers.hunters_league.domain.User;
import com.javangers.hunters_league.repository.CompetitionRepository;
import com.javangers.hunters_league.repository.ParticipationRepository;
import com.javangers.hunters_league.repository.UserRepository;
import com.javangers.hunters_league.service.dto.ParticipationDTO;
import com.javangers.hunters_league.service.ParticipationService;
import com.javangers.hunters_league.service.dto.mapper.ParticipationMapper;
import com.javangers.hunters_league.web.errors.BusinessValidationException;
import com.javangers.hunters_league.web.errors.UserNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final CompetitionRepository competitionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ParticipationDTO registerForCompetition(UUID competitionId, UUID userId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new EntityNotFoundException("Competition not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        validateRegistration(competition, user);

        Participation participation = Participation.builder()
                .user(user)
                .competition(competition)
                .score(0.0)
                .hunts(new ArrayList<>())
                .build();

        Participation savedParticipation = participationRepository.save(participation);

        return ParticipationMapper.toDTO(savedParticipation);
    }


    private void validateRegistration(Competition competition, User user) {
        // Check if already registered
        if (participationRepository.existsByUserIdAndCompetitionId(user.getId(), competition.getId())) {
            throw new BusinessValidationException("User is already registered for this competition");
        }

        // Check if registration is open
        if (!competition.getOpenRegistration()) {
            throw new BusinessValidationException("Registration is closed for this competition");
        }

        // Check registration deadline (24 hours before start)
        LocalDateTime registrationDeadline = competition.getDate().minusHours(24);
        if (LocalDateTime.now().isAfter(registrationDeadline)) {
            throw new BusinessValidationException("Registration is closed (24 hours before competition start)");
        }

        // Check license validity
        if (user.getLicenseExpirationDate() == null ||
            user.getLicenseExpirationDate().isBefore(LocalDateTime.now())) {
            throw new BusinessValidationException("User's license has expired");
        }

        // Check maximum participants
        long currentParticipants = participationRepository.countByCompetitionId(competition.getId());
        if (currentParticipants >= competition.getMaxParticipants()) {
            throw new BusinessValidationException("Competition has reached maximum number of participants");
        }
    }
}
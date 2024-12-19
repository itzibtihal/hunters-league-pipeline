package com.javangers.hunters_league.repository;

import com.javangers.hunters_league.domain.Participation;
import com.javangers.hunters_league.repository.dto.UserCompetitionRankingDTO;
import com.javangers.hunters_league.service.dto.MemberResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MemberResultsRepository extends JpaRepository<Participation, UUID> {
    @Query(value = """
            SELECT new com.javangers.hunters_league.service.dto.MemberResultDTO(
                u.id,
                u.username,
                p.id,
                c.code,
                COUNT(h),
                p.score)
            FROM User u
            JOIN u.participations p
            LEFT JOIN p.hunts h
            LEFT JOIN p.competition c
            WHERE u.id = :userId
            AND (CAST(:startDate AS timestamp) IS NULL OR c.date >= :startDate)
            AND (CAST(:endDate AS timestamp) IS NULL OR c.date <= :endDate)
            GROUP BY u.id, u.username, p.id, c.code, p.score
            """,
            countQuery = """
                    SELECT COUNT(DISTINCT p.id)
                    FROM User u
                    JOIN u.participations p
                    LEFT JOIN p.competition c
                    WHERE u.id = :userId
                    AND (CAST(:startDate AS timestamp) IS NULL OR c.date >= :startDate)
                    AND (CAST(:endDate AS timestamp) IS NULL OR c.date <= :endDate)
                    """)
    Page<MemberResultDTO> findMemberResults(
            @Param("userId") UUID userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query(value = "SELECT * FROM get_user_competition_rankings(:userId)", nativeQuery = true)
    Page<UserCompetitionRankingDTO> findMemberRankResults(@Param("userId") UUID userId, Pageable pageable);

}
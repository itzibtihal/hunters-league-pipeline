package com.javangers.hunters_league.repository;

import com.javangers.hunters_league.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpeciesRepository extends JpaRepository<Species, UUID> {
    List<Species> findAll();
    Optional<Species> findByName(String name);
    boolean existsByNameIgnoreCase(String name);
}

package com.javangers.hunters_league.service;

import com.javangers.hunters_league.domain.Species;
import java.util.List;

public interface SpeciesService {
    List<Species> findAll();
    Species createSpecies(Species species);
    Species updateSpecies(Species species);
}

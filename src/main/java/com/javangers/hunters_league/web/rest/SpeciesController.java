package com.javangers.hunters_league.web.rest;

import com.javangers.hunters_league.domain.Species;
import com.javangers.hunters_league.service.SpeciesService;
import com.javangers.hunters_league.web.vm.SpeciesVM;
import com.javangers.hunters_league.web.vm.mapper.SpeciesMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/species")
public class SpeciesController {

    private final SpeciesService speciesService;
    private final SpeciesMapper speciesMapper;


    @GetMapping("/")
    public ResponseEntity<List<Species>> getAllSpecies() {
        List<Species> createdUser = speciesService.findAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/")
    public ResponseEntity<Species> CreateSpecies(@Valid @RequestBody SpeciesVM speciesVM) {
        Species species = speciesMapper.toEntity(speciesVM);
        Species createdSpecies = speciesService.createSpecies(species);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpecies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Species> updateSpecies(
            @PathVariable UUID id,
            @Valid @RequestBody SpeciesVM speciesVM) {

        Species species = speciesMapper.toEntity(speciesVM);
        species.setId(id);
        Species updatedSpecies = speciesService.updateSpecies(species);
        return ResponseEntity.ok(updatedSpecies);

    }


}

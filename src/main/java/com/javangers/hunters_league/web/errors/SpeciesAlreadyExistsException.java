package com.javangers.hunters_league.web.errors;

public class SpeciesAlreadyExistsException extends RuntimeException {
    public SpeciesAlreadyExistsException(String message) {
        super(message);
    }
}
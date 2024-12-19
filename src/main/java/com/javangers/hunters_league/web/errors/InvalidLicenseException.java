package com.javangers.hunters_league.web.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidLicenseException extends RuntimeException {
    public InvalidLicenseException(String message) {
        super(message);
    }
}
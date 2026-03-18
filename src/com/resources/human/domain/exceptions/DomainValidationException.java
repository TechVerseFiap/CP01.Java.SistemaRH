package com.resources.human.domain.exceptions;

import java.lang.Exception;

public class DomainValidationException extends Exception {
    public DomainValidationException(String message) {
        super(message);
    }
}

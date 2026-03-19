package com.resources.human.domain.exceptions;

public class AlreadyExistsByIdException extends Exception {
    public AlreadyExistsByIdException(String message) {
        super(message);
    }
}

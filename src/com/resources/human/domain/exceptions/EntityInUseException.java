package com.resources.human.domain.exceptions;

public class EntityInUseException extends Exception {
    public EntityInUseException(String message) {
        super(message);
    }
}

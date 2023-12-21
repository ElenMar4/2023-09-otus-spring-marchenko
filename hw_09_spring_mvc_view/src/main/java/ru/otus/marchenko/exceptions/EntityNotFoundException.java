package ru.otus.marchenko.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
    }
}
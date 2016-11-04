package ru.sbt.services;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

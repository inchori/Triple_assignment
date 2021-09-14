package com.triple.assignment.exception;

public class TripIsNotFutureException extends RuntimeException {
    public TripIsNotFutureException(String message) {
        super(message);
    }
}

package com.triple.assignment.service.trip.exception;

import com.triple.assignment.exception.BusinessException;

public class TripIsNotFutureException extends BusinessException {
    public TripIsNotFutureException(String message) {
        super(message);
    }
}

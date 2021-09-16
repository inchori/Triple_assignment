package com.triple.assignment.service.trip.exception;

import com.triple.assignment.exception.EntityNotFoundException;

public class TripNotFoundException extends EntityNotFoundException {
    public TripNotFoundException() {
        super("해당 여행이 존재하지 않습니다.");
    }
}

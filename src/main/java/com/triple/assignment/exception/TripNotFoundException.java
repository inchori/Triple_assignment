package com.triple.assignment.exception;

public class TripNotFoundException extends RuntimeException {
    public TripNotFoundException() {
        super("해당 여행이 존재하지 않습니다.");
    }
}

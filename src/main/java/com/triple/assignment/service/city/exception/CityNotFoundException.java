package com.triple.assignment.service.city.exception;

import com.triple.assignment.exception.EntityNotFoundException;

public class CityNotFoundException extends EntityNotFoundException {
    public CityNotFoundException() {
        super("해당 도시가 존재하지 않습니다.");
    }
}

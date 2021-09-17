package com.triple.assignment.service.city.exception;

import com.triple.assignment.exception.BusinessException;

public class CityExistedException extends BusinessException {
    public CityExistedException() {
        super("이미 해당 도시가 존재합니다.");
    }
}

package com.triple.assignment.service.city.repository;


import com.triple.assignment.service.city.domain.City;

import java.time.LocalDateTime;
import java.util.List;

public interface CityCustomRepository {
    List<City> findTenCities();
}

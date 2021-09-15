package com.triple.assignment.repository;

import com.triple.assignment.entity.City;

import java.util.List;

public interface CityCustomRepository {
    List<City> findTenCities();
}

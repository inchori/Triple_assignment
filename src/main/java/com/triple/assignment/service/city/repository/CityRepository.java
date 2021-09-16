package com.triple.assignment.service.city.repository;

import com.triple.assignment.service.city.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long>, CityCustomRepository {

    Optional<City> findByName(String name);
    List<City> findTenCities();
}

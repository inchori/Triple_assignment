package com.triple.assignment.repository;

import com.triple.assignment.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long>, CityCustomRepository {

    Optional<City> findByName(String name);
    List<City> findTenCities();
}

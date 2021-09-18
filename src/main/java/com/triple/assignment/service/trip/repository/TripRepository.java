package com.triple.assignment.service.trip.repository;

import com.triple.assignment.service.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long>  {
}

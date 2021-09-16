package com.triple.assignment.service.trip.repository;

import com.triple.assignment.service.trip.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long>  {
}

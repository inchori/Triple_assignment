package com.triple.assignment.service;
import com.triple.assignment.dto.TripCreateRequestDto;
import com.triple.assignment.dto.TripCreateResponseDto;
import com.triple.assignment.entity.Trip;
import com.triple.assignment.repository.CityRepository;
import com.triple.assignment.repository.TripRepository;
import com.triple.assignment.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TripServiceTest {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityService cityService;

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private TripService tripService;

    @BeforeEach
    public void setUp() {
        tripRepository.deleteAll();
    }

    @Test
    @DisplayName("여행 등록")
    public void 여행_등록() {
        //given
        Trip trip = createTrip();

        TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                .tripName("친구와 여행")
                .tripStartDate(LocalDateTime.of(2021, 9, 13, 0, 0))
                .tripEndDate(LocalDateTime.of(2021, 9, 14, 0, 0))
                .build();

        //when
        TripCreateResponseDto result = tripService.createTrip(tripCreateRequestDto);

        //then
        assertEquals(result.getTripName(), trip.getName());
        assertEquals(result.getTripStartDate(), trip.getStartTripDate());
        assertEquals(result.getTripEndDate(), trip.getEndTripDate());
    }

    @Test
    @DisplayName("여행 하나 조회")
    public void 여행_하나_조회() {
        //given

        //when

        //then
    }

    public Trip createTrip() {
        Trip trip = Trip.builder()
                .name("친구와 여행")
                .startTripDate(LocalDateTime.of(2021, 9, 13, 0, 0))
                .endTripDate(LocalDateTime.of(2021, 9, 14, 0, 0))
                .build();

        tripRepository.save(trip);
        return trip;
    }
}
package com.triple.assignment.service;
import com.triple.assignment.service.city.domain.City;
import com.triple.assignment.service.city.repository.CityRepository;
import com.triple.assignment.service.trip.domain.Trip;
import com.triple.assignment.service.trip.exception.TripIsNotFutureException;
import com.triple.assignment.service.trip.repository.TripRepository;
import com.triple.assignment.service.trip.TripService;
import com.triple.assignment.web.trip.TripCreateRequestDto;
import com.triple.assignment.web.trip.TripCreateResponseDto;
import com.triple.assignment.web.trip.TripGetOneResponseDto;
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
    private TripRepository tripRepository;
    @Autowired
    private TripService tripService;

    @BeforeEach
    public void setUp() {
        tripRepository.deleteAll();
        cityRepository.deleteAll();
    }

    @Test
    @DisplayName("여행 등록")
    public void 여행_등록() {
        //given
        Trip trip = createTrip();

        TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                .tripName("친구와 여행")
                .tripStartDate(LocalDateTime.of(2022, 9, 13, 0, 0))
                .tripEndDate(LocalDateTime.of(2022, 9, 14, 0, 0))
                .cityName("런던")
                .build();

        //when
        TripCreateResponseDto result = tripService.createTrip(tripCreateRequestDto);

        //then
        assertEquals(result.getTripName(), trip.getName());
        assertEquals(result.getTripStartDate(), trip.getStartTripDate());
        assertEquals(result.getTripEndDate(), trip.getEndTripDate());
        assertEquals(result.getCityInfo(), trip.getCity().getInfo());
    }

    @Test
    @DisplayName("여행 등록 실패")
    public void 여행_등록_실패(){
        //given
        TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                .tripName("친구와 여행")
                .tripStartDate(LocalDateTime.of(2021, 9, 13, 0, 0))
                .tripEndDate(LocalDateTime.of(2021, 9, 14, 0, 0))
                .cityName("런던")
                .build();

        //when
        TripIsNotFutureException thrown = assertThrows(TripIsNotFutureException.class, () -> tripService.createTrip(tripCreateRequestDto));

        //then
        assertEquals("여행 시작은 미래 날짜만 가능합니다.", thrown.getMessage());
    }

    @Test
    @DisplayName("여행 하나 조회")
    public void 여행_하나_조회() {
        //given
        Trip trip = createTrip();

        TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                .tripName("친구와 여행")
                .tripStartDate(LocalDateTime.of(2022, 9, 13, 0, 0))
                .tripEndDate(LocalDateTime.of(2022, 9, 14, 0, 0))
                .cityName("런던")
                .build();

        TripCreateResponseDto savedTrip = tripService.createTrip(tripCreateRequestDto);

        //when
        Long findTripId = savedTrip.getTripId();

        //then
        TripGetOneResponseDto result = tripService.getOneTrip(findTripId);

        assertEquals(result.getTripId(), savedTrip.getTripId());
        assertEquals(result.getTripName(), savedTrip.getTripName());
        assertEquals(result.getTripStartDate(), savedTrip.getTripStartDate());
        assertEquals(result.getTripEndDate(), savedTrip.getTripEndDate());
//        assertEquals(result.getCityInfo(), savedTrip.getCityInfo());
    }

    public Trip createTrip() {
        City city = createCity("런던", "영국의 수도");
        Trip trip = Trip.builder()
                .name("친구와 여행")
                .startTripDate(LocalDateTime.of(2022, 9, 13, 0, 0))
                .endTripDate(LocalDateTime.of(2022, 9, 14, 0, 0))
                .city(city)
                .build();
        tripRepository.save(trip);
        return trip;
    }

    public City createCity(String name, String info) {
        City city = City.builder()
                .name(name)
                .info(info)
                .registerDate(LocalDateTime.now())
                .build();
        return cityRepository.save(city);
    }
}
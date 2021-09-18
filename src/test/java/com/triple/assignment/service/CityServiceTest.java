package com.triple.assignment.service;

import com.triple.assignment.service.city.CityService;
import com.triple.assignment.service.city.domain.City;
import com.triple.assignment.service.city.exception.CityExistedException;
import com.triple.assignment.service.city.repository.CityRepository;
import com.triple.assignment.service.trip.TripService;
import com.triple.assignment.service.trip.repository.TripRepository;
import com.triple.assignment.web.city.CityCreateRequestDto;
import com.triple.assignment.web.city.CityCreateResponseDto;
import com.triple.assignment.web.city.CityGetOneResponseDto;
import com.triple.assignment.web.city.CityTripResponseDto;
import com.triple.assignment.web.trip.TripCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CityServiceTest {

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
        cityRepository.deleteAll();
    }

    @Test
    @DisplayName("도시 등록 성공")
    public void 도시_등록() {
        //given

        CityCreateRequestDto cityCreateRequestDto = CityCreateRequestDto.builder()
                .cityName("런던")
                .cityInfo("영국의 수도")
                .build();

        //when
        CityCreateResponseDto resultCity = cityService.create(cityCreateRequestDto);

        //then
        assertEquals(resultCity.getCityName(), cityCreateRequestDto.getCityName());
        assertEquals(resultCity.getCityInfo(), cityCreateRequestDto.getCityInfo());
    }

    @Test
    @DisplayName("예외 처리: 도시 중복 등록 실패")
    public void 도시_중복_등록_실패() {
        //given

        //when
        City city2 = createCity("런던", "영국의 수도");

        CityCreateRequestDto cityCreateRequestDto2 = CityCreateRequestDto.builder()
                .cityName("런던")
                .cityInfo("영국의 수도")
                .build();


        CityExistedException cityExistedException = assertThrows(CityExistedException.class, () -> cityService.create(cityCreateRequestDto2));

//        then
        assertEquals("이미 해당 도시가 존재합니다.", cityExistedException.getMessage());
    }

    @Test
    @DisplayName("도시 하나 조회")
    public void 도시_하나_조회() {
        //given
        CityCreateRequestDto cityCreateRequestDto = CityCreateRequestDto.builder()
                .cityName("런던")
                .cityInfo("영국의 수도")
                .build();
        CityCreateResponseDto savedCity = cityService.create(cityCreateRequestDto);

        //when
        Long findCityId = savedCity.getCityId();

        //then
        CityGetOneResponseDto result = cityService.getOneCity(findCityId);
        assertEquals(result.getCityId(), savedCity.getCityId());
        assertEquals(result.getCityName(), savedCity.getCityName());
        assertEquals(result.getCityInfo(), savedCity.getCityInfo());
    }

    @Test
    @DisplayName("도시 리스트 조회")
    public void 도시_리스트_조회() {
        //given
        City city1 = createCity("런던", "영국의 수도");

        City city2 = createCity("서울", "대한민국의 수도");
        for (int i = 0; i < 5; i++) {
            TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                    .tripName("친구와 여행")
                    .tripStartDate(LocalDate.of(2022, 9, 13).plusDays(i))
                    .tripEndDate(LocalDate.of(2022, 9, 14).plusDays(i))
                    .cityName(city1.getName())
                    .build();
            tripService.createTrip(tripCreateRequestDto);
        }

        for (int i = 0; i < 5; i++) {
            TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                    .tripName("친구와 여행")
                    .tripStartDate(LocalDate.of(2022, 4, 13).plusDays(i))
                    .tripEndDate(LocalDate.of(2022, 4, 14).plusDays(i))
                    .cityName(city2.getName())
                    .build();
            tripService.createTrip(tripCreateRequestDto);
        }

        //when
        CityTripResponseDto cities = cityService.getCities();
        //then
        assertEquals(cities.getCities().size(), 10);
    }

    @Test
    @DisplayName("도시 리스트 조회 시 10개만 나오는 조회")
    public void 도시_리스트_조회_시_10개만_나오는_조회() {
        //given
        City city1 = createCity("런던", "영국의 수도");

        City city2 = createCity("서울", "대한민국의 수도");
        City city3 = createCity("워싱턴", "미국의 수도");
        for (int i = 0; i < 5; i++) {
            TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                    .tripName("친구와 여행")
                    .tripStartDate(LocalDate.of(2022, 9, 13).plusDays(i))
                    .tripEndDate(LocalDate.of(2022, 9, 14).plusDays(i))
                    .cityName(city1.getName())
                    .build();
            tripService.createTrip(tripCreateRequestDto);
        }

        for (int i = 0; i < 5; i++) {
            TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                    .tripName("친구와 여행")
                    .tripStartDate(LocalDate.of(2022, 4, 13).plusDays(i))
                    .tripEndDate(LocalDate.of(2022, 4, 14).plusDays(i))
                    .cityName(city2.getName())
                    .build();
            tripService.createTrip(tripCreateRequestDto);
        }
        //when
        CityTripResponseDto cities = cityService.getCities();
        //then
        assertEquals(cities.getCities().size(), 10);
    }

    @Test
    @DisplayName("여행 일정 없는 도시도 조회되는지 조회")
    public void test() throws Exception {
        City city1 = createCity("런던", "영국의 수도");

        City city2 = createCity("서울", "대한민국의 수도");
        City city3 = createCity("워싱턴", "미국의 수도");
        for (int i = 0; i < 5; i++) {
            TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                    .tripName("친구와 여행")
                    .tripStartDate(LocalDate.of(2022, 9, 13).plusDays(i))
                    .tripEndDate(LocalDate.of(2022, 9, 14).plusDays(i))
                    .cityName(city1.getName())
                    .build();
            tripService.createTrip(tripCreateRequestDto);
        }

        for (int i = 0; i < 4; i++) {
            TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                    .tripName("친구와 여행")
                    .tripStartDate(LocalDate.of(2022, 4, 13).plusDays(i))
                    .tripEndDate(LocalDate.of(2022, 4, 14).plusDays(i))
                    .cityName(city2.getName())
                    .build();
            tripService.createTrip(tripCreateRequestDto);
        }
        //when
        CityTripResponseDto cities = cityService.getCities();
        //then
        assertEquals(cities.getCities().size(), 10);
    }

    public City createCity(String name, String info) {
        City city = City.builder()
                .name(name)
                .info(info)
                .registerDate(LocalDate.now())
                .getOneDate(LocalDateTime.now())
                .build();
        return cityRepository.save(city);
    }


}
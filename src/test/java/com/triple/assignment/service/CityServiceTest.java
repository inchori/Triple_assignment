package com.triple.assignment.service;

import com.triple.assignment.dto.CityCreateRequestDto;
import com.triple.assignment.dto.CityCreateResponseDto;
import com.triple.assignment.dto.CityGetOneResponseDto;
import com.triple.assignment.entity.City;
import com.triple.assignment.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CityServiceTest {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @BeforeEach
    public void setUp() {
        cityRepository.deleteAll();
    }

    @Test
    @DisplayName("도시 등록 성공")
    public void 도시_등록() {
        //given
        City city = createCity("런던", "영국의 수도");

        CityCreateRequestDto cityCreateRequestDto = CityCreateRequestDto.builder()
                .cityName(city.getName())
                .cityInfo(city.getInfo())
                .build();

        //when
        CityCreateResponseDto resultCity = cityService.create(cityCreateRequestDto);

        //then
        assertEquals(resultCity.getCityName(), city.getName());
        assertEquals(resultCity.getCityInfo(), city.getInfo());
    }

    @Test
    @DisplayName("도시 하나 조회")
    public void 도시_하나_조회() {
        //given
        City city = createCity("런던", "영국의 수도");
        CityCreateRequestDto cityCreateRequestDto = CityCreateRequestDto.builder()
                .cityName(city.getName())
                .cityInfo(city.getInfo())
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

    public City createCity(String name, String info) {
        City city = City.builder()
                .name(name)
                .info(info)
                .build();
        cityRepository.save(city);
        return city;
    }
}
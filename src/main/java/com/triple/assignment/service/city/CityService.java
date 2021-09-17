package com.triple.assignment.service.city;

import com.triple.assignment.service.city.domain.City;
import com.triple.assignment.service.city.exception.CityExistedException;
import com.triple.assignment.service.city.exception.CityNotFoundException;
import com.triple.assignment.service.city.repository.CityRepository;
import com.triple.assignment.service.trip.domain.Trip;
import com.triple.assignment.web.city.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CityCreateResponseDto create(CityCreateRequestDto cityCreateRequestDto) {
        if (cityRepository.findByName(cityCreateRequestDto.getCityName()).isPresent()) {
            throw new CityExistedException();
        }
        City savedCity = cityRepository.save(City.createCity(cityCreateRequestDto));
        return modelMapper.map(savedCity, CityCreateResponseDto.class);
    }


    public CityGetOneResponseDto getOneCity(Long id) {
        City findCity = cityRepository.findById(id).orElseThrow(CityNotFoundException::new);
        findCity.setGetOneDate(LocalDateTime.now());
        return modelMapper.map(findCity, CityGetOneResponseDto.class);
    }


    @Transactional(readOnly = true)
    public CityTripResponseDto getCities() {

        List<City> cities = cityRepository.findTenCities();
//        cities.sort((o1, o2) -> o1.getRegisterDate().compareTo(LocalDateTime.now().minusDays(1)));
//        cities.sort((o1, o2) -> o1.getGetOneDate().compareTo(LocalDateTime.now().minusWeeks(1)));
        List<Trip> trip = new ArrayList<>();
        for (City city : cities) {
            if (city.getTrip() != null) trip.addAll(city.getTrip());
        }

        List<Trip> tripPaging = trip.stream().limit(10).collect(Collectors.toList());


        List<CityTripGetOneResponseDto> cityTripGetOneResponseList = new ArrayList<>();

        for (Trip tripPage : tripPaging) {
            cityTripGetOneResponseList.add(new CityTripGetOneResponseDto(tripPage));
        }

        CityTripResponseDto cityTripResponseDto = CityTripResponseDto.builder()
                .cities(cityTripGetOneResponseList)
                .build();


        return cityTripResponseDto;
    }

}

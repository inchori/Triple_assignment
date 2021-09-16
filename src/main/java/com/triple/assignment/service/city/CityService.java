package com.triple.assignment.service.city;

import com.triple.assignment.service.city.domain.City;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CityCreateResponseDto create(CityCreateRequestDto cityCreateRequestDto) {
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
        List<Trip> trip = new ArrayList<>();
        for (City city : cities) {
            if (city.getTrip() != null) trip.addAll(city.getTrip());
        }

//        trip.addAll(cities.stream().filter(city -> Objects.nonNull(city.getTrip()))
//                .map(City::getTrip).flatMap(trips -> ).collect(Collectors.toList()));

        List<Trip> tripPaging = trip.stream().limit(10).collect(Collectors.toList());

        CityTripResponseDto cityTripResponseDto = new CityTripResponseDto();
        List<CityTripGetOneResponseDto> cityTripGetOneResponseDto = new ArrayList<>();

        for (Trip tripPage : tripPaging) {
            cityTripGetOneResponseDto.add(new CityTripGetOneResponseDto(tripPage));
        }

        cityTripResponseDto.setCities(cityTripGetOneResponseDto);

        for (CityTripGetOneResponseDto tripGetOneDto : cityTripGetOneResponseDto) {
            System.out.println("tripGetOneDto.getCityName() = " + tripGetOneDto.getCityName());
        }

        return cityTripResponseDto;
    }

}

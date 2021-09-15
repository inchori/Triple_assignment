package com.triple.assignment.service;

import com.triple.assignment.dto.CityCreateRequestDto;
import com.triple.assignment.dto.CityCreateResponseDto;
import com.triple.assignment.dto.CityGetOneResponseDto;
import com.triple.assignment.dto.CityTripResponseDto;
import com.triple.assignment.entity.City;
import com.triple.assignment.exception.CityNotFoundException;
import com.triple.assignment.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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


    @Transactional(readOnly = true)
    public CityGetOneResponseDto getOneCity(Long id) {
        City findCity = cityRepository.findById(id).orElseThrow(CityNotFoundException::new);
        return modelMapper.map(findCity, CityGetOneResponseDto.class);
    }


    @Transactional(readOnly = true)
    public List<CityTripResponseDto> getCities() {
        List<City> tenCities = cityRepository.findTenCities();
        return tenCities.stream()
                .map(tenCity -> modelMapper.map(tenCity, CityTripResponseDto.class))
                .collect(Collectors.toList());
    }


}

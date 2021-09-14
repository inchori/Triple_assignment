package com.triple.assignment.service;

import com.triple.assignment.dto.CityCreateRequestDto;
import com.triple.assignment.dto.CityCreateResponseDto;
import com.triple.assignment.dto.CityGetOneResponseDto;
import com.triple.assignment.entity.City;
import com.triple.assignment.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//        cityRepository.findById(id).orElseThrow(NotFou)
        return modelMapper.map(cityRepository.getById(id), CityGetOneResponseDto.class);
    }
}

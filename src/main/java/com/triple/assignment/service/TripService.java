package com.triple.assignment.service;

import com.triple.assignment.dto.TripCreateRequestDto;
import com.triple.assignment.dto.TripCreateResponseDto;
import com.triple.assignment.dto.TripGetOneResponseDto;
import com.triple.assignment.entity.City;
import com.triple.assignment.entity.Trip;
import com.triple.assignment.exception.CityNotFoundException;
import com.triple.assignment.exception.TripIsNotFutureException;
import com.triple.assignment.exception.TripNotFoundException;
import com.triple.assignment.repository.CityRepository;
import com.triple.assignment.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public TripCreateResponseDto createTrip(TripCreateRequestDto createRequestDto) {
        if (LocalDateTime.now().isAfter(createRequestDto.getTripStartDate())) {
            throw new TripIsNotFutureException("여행 시작은 미래 날짜만 가능합니다.");
        }
        City city = cityRepository.findByName(createRequestDto.getCityName()).orElseThrow(CityNotFoundException::new);
        Trip savedTrip = tripRepository.save(Trip.createTrip(createRequestDto, city));
        return modelMapper.map(savedTrip, TripCreateResponseDto.class);
    }

    @Transactional(readOnly = true)
    public TripGetOneResponseDto getOneTrip(Long id) {
        Trip findTrip = tripRepository.findById(id).orElseThrow(TripNotFoundException::new);
        return modelMapper.map(findTrip, TripGetOneResponseDto.class);
    }

}

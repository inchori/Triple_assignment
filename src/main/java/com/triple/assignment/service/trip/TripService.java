package com.triple.assignment.service.trip;

import com.triple.assignment.service.city.domain.City;
import com.triple.assignment.service.city.exception.CityNotFoundException;
import com.triple.assignment.service.trip.domain.Trip;
import com.triple.assignment.service.trip.exception.TripIsNotFutureException;
import com.triple.assignment.web.trip.TripCreateRequestDto;
import com.triple.assignment.web.trip.TripCreateResponseDto;
import com.triple.assignment.web.trip.TripGetOneResponseDto;
import com.triple.assignment.service.trip.exception.TripNotFoundException;
import com.triple.assignment.service.city.repository.CityRepository;
import com.triple.assignment.service.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public TripCreateResponseDto createTrip(TripCreateRequestDto createRequestDto) {
        if (LocalDate.now().isAfter(createRequestDto.getTripStartDate())) {
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
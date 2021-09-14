package com.triple.assignment.service;

import com.triple.assignment.dto.TripCreateRequestDto;
import com.triple.assignment.dto.TripCreateResponseDto;
import com.triple.assignment.entity.City;
import com.triple.assignment.entity.Trip;
import com.triple.assignment.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TripService {
    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;

    public TripCreateResponseDto createTrip(TripCreateRequestDto createRequestDto, City city) {
        Trip savedTrip = tripRepository.save(Trip.createTrip(createRequestDto, city));
        return modelMapper.map(savedTrip, TripCreateResponseDto.class);
    }

}

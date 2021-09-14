package com.triple.assignment.controller;

import com.triple.assignment.dto.TripCreateRequestDto;
import com.triple.assignment.dto.TripCreateResponseDto;
import com.triple.assignment.dto.TripGetOneResponseDto;
import com.triple.assignment.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public TripCreateResponseDto registerTrip(@RequestBody @Valid TripCreateRequestDto createRequestDto) {
        return tripService.createTrip(createRequestDto);
    }

    @GetMapping("/{id}")
    public TripGetOneResponseDto getTrip(@PathVariable("id") Long id) {
        return tripService.getOneTrip(id);
    }
}

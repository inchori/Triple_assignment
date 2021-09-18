package com.triple.assignment.web.trip;

import com.triple.assignment.service.trip.TripService;
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

package com.triple.assignment.controller;

import com.triple.assignment.dto.CityCreateRequestDto;
import com.triple.assignment.dto.CityCreateResponseDto;
import com.triple.assignment.dto.CityGetOneResponseDto;
import com.triple.assignment.dto.CityTripResponseDto;
import com.triple.assignment.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;

    @PostMapping
    public CityCreateResponseDto registerCity(@RequestBody @Valid CityCreateRequestDto cityCreateRequestDto) {
        return cityService.create(cityCreateRequestDto);
    }

    @GetMapping("/{id}")
    public CityGetOneResponseDto getCity(@PathVariable("id") Long id) {
        return cityService.getOneCity(id);
    }


    @GetMapping("/list")
    public List<CityTripResponseDto> getCityList() {
        return cityService.getCities();
    }

}

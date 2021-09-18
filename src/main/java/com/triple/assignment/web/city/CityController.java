package com.triple.assignment.web.city;

import com.triple.assignment.service.city.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public CityTripResponseDto getCityList() {
        return cityService.getCities();
    }

}

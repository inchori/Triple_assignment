package com.triple.assignment.web.city;

import lombok.Data;

import java.util.List;

@Data
public class CityTripResponseDto {

    private List<CityTripGetOneResponseDto> cities;
}

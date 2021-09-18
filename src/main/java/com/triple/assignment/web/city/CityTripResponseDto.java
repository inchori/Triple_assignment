package com.triple.assignment.web.city;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CityTripResponseDto {

    private int count;
    private List<CityTripGetOneResponseDto> cities;
}

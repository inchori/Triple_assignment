package com.triple.assignment.dto;

import com.triple.assignment.entity.City;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CityTripResponseDto {

    private Long cityId;
    private String cityName;
    private String cityInfo;

    public CityTripResponseDto(City city) {
        this.cityId = city.getId();
        this.cityName = city.getName();
        this.cityInfo = city.getInfo();
    }
}

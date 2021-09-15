package com.triple.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CityTripResponseDto {

    private Long cityId;
    private String cityName;
    private String cityInfo;
    private TripGetOneResponseDto tripGetOneResponseDto;
}

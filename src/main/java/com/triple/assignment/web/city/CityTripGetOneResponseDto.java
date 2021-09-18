package com.triple.assignment.web.city;

import com.triple.assignment.service.city.domain.City;
import com.triple.assignment.service.trip.domain.Trip;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CityTripGetOneResponseDto {

    private Long cityId;
    private String cityName;
    private String cityInfo;
    private LocalDate cityRegisteredDate;
    private LocalDateTime cityGetOneDate;

    private String tripName;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;


    public CityTripGetOneResponseDto(Trip trip) {
        this.cityId = trip.getCity().getId();
        this.cityName = trip.getCity().getName();
        this.cityInfo = trip.getCity().getInfo();
        this.cityRegisteredDate = trip.getCity().getRegisterDate();
        this.cityGetOneDate = trip.getCity().getGetOneDate();
        this.tripName = trip.getName();
        this.tripStartDate = trip.getStartTripDate();
        this.tripEndDate = trip.getEndTripDate();
    }

    public CityTripGetOneResponseDto(City city) {
        this.cityId = city.getId();
        this.cityName = city.getName();
        this.cityInfo = city.getInfo();
        this.cityRegisteredDate = city.getRegisterDate();
        this.cityGetOneDate = city.getGetOneDate();
    }
}

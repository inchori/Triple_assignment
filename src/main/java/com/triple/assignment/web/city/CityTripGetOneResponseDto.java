package com.triple.assignment.web.city;

import com.triple.assignment.service.trip.domain.Trip;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CityTripGetOneResponseDto {

    private Long cityId;
    private String cityName;
    private String cityInfo;
    private LocalDateTime cityRegisteredDate;
    private LocalDateTime cityGetOneDate;

    private String tripName;
    private LocalDateTime tripStartDate;
    private LocalDateTime tripEndDate;

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
}

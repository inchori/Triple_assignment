package com.triple.assignment.web.trip;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TripGetOneResponseDto {

    private Long tripId;

    private String tripName;

    private LocalDate tripStartDate;

    private LocalDate tripEndDate;

    private String cityInfo;
}

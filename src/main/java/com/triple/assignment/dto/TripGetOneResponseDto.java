package com.triple.assignment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripGetOneResponseDto {

    private Long tripId;

    private String tripName;

    private LocalDateTime tripStartDate;

    private LocalDateTime tripEndDate;
}

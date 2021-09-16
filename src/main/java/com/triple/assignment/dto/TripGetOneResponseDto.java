package com.triple.assignment.dto;

import com.triple.assignment.entity.Trip;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TripGetOneResponseDto {

    private Long tripId;

    private String tripName;

    private LocalDateTime tripStartDate;

    private LocalDateTime tripEndDate;

    private String cityInfo;
}

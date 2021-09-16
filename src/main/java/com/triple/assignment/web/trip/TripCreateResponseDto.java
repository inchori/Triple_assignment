package com.triple.assignment.web.trip;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
public class TripCreateResponseDto {

    private Long tripId;
    private String tripName;
    private LocalDateTime tripStartDate;
    private LocalDateTime tripEndDate;
    private String cityInfo;
}

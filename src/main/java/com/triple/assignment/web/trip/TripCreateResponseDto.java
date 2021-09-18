package com.triple.assignment.web.trip;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
public class TripCreateResponseDto {

    private Long tripId;
    private String tripName;
    private LocalDate tripStartDate;
    private LocalDate tripEndDate;
    private String cityInfo;
}

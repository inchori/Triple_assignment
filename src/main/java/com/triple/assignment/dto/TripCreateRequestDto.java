package com.triple.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripCreateRequestDto {

    @NotEmpty
    private String tripName;

    @NotEmpty
    private LocalDateTime tripStartDate;

    @NotEmpty
    private LocalDateTime tripEndDate;

//    @NotEmpty
//    private String cityName;
}

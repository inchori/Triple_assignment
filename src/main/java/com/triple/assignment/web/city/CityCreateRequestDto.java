package com.triple.assignment.web.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityCreateRequestDto {

    @NotEmpty
    private String cityName;

    @NotEmpty
    private String cityInfo;
}

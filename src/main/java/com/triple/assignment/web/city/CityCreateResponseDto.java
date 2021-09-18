package com.triple.assignment.web.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityCreateResponseDto {

    private Long cityId;

    private String cityName;

    private String cityInfo;
}

package com.triple.assignment.web;

import com.triple.assignment.service.city.CityService;
import com.triple.assignment.service.city.domain.City;
import com.triple.assignment.service.city.repository.CityRepository;
import com.triple.assignment.service.trip.TripService;
import com.triple.assignment.web.city.CityCreateRequestDto;
import com.triple.assignment.web.city.CityCreateResponseDto;
import com.triple.assignment.web.trip.TripCreateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CityControllerTest extends ApiDocsConfig {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private TripService tripService;

    @BeforeEach
    public void setUp() {
        cityRepository.deleteAll();
    }

    @Test
    @DisplayName("도시 등록")
    public void 도시_등록() throws Exception {
        //given
        CityCreateRequestDto cityCreateRequestDto = CityCreateRequestDto.builder()
                .cityName("London")
                .cityInfo("The capital of U.K")
                .build();

        mvc.perform(post("/api/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cityCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("cityName").value("London"))
                .andExpect(jsonPath("cityInfo").value("The capital of U.K"))
                .andDo(document("register-city",
                        requestFields(
                                fieldWithPath("cityName").description("등록할 도시의 이름"),
                                fieldWithPath("cityInfo").description("등록할 도시의 정보")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("cityId").description("등록된 도시의 고유 아이디"),
                                fieldWithPath("cityName").description("등록된 도시의 이름"),
                                fieldWithPath("cityInfo").description("등록된 도시의 정보")
                        )
                ));
    }

    @Test
    @DisplayName("도시 단건 조회")
    public void 도시_단건_조회() throws Exception {
        //given
        CityCreateRequestDto cityCreateRequestDto = CityCreateRequestDto.builder()
                .cityName("London")
                .cityInfo("The capital of U.K")
                .build();

        CityCreateResponseDto cityCreateResponseDto = cityService.create(cityCreateRequestDto);

        mvc.perform(RestDocumentationRequestBuilders.get("/api/city/{id}", cityCreateResponseDto.getCityId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("cityId").exists())
                .andExpect(jsonPath("cityName").exists())
                .andExpect(jsonPath("cityInfo").exists())
                .andDo(document("city-getOne",
                        pathParameters(
                                parameterWithName("id").description("도시 고유 아이디")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("cityId").description("조회한 도시 아이디"),
                                fieldWithPath("cityName").description("조회한 도시 이름"),
                                fieldWithPath("cityInfo").description("조회한 도시 정보")
                        )
                ));
    }

    @Test
    @DisplayName("도시 리스트 조회")
    public void 도시_리스트_조회() throws Exception {
        //given

        CityCreateRequestDto cityCreateRequestDto1 = CityCreateRequestDto.builder()
                .cityName("런던")
                .cityInfo("영국의 수도")
                .build();
        CityCreateResponseDto cityCreateResponseDto1 = cityService.create(cityCreateRequestDto1);

        CityCreateRequestDto cityCreateRequestDto2 = CityCreateRequestDto.builder()
                .cityName("서울")
                .cityInfo("대한민국의 수도")
                .build();
        CityCreateResponseDto cityCreateResponseDto2 = cityService.create(cityCreateRequestDto2);

        CityCreateRequestDto cityCreateRequestDto3 = CityCreateRequestDto.builder()
                .cityName("워싱턴 D.C")
                .cityInfo("미국의 수도")
                .build();
        CityCreateResponseDto cityCreateResponseDto3 = cityService.create(cityCreateRequestDto3);

        cityService.getOneCity(cityCreateResponseDto1.getCityId());
        cityService.getOneCity(cityCreateResponseDto2.getCityId());
        cityService.getOneCity(cityCreateResponseDto3.getCityId());
        //when

        for (int i = 0; i < 3; i++) {
            TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                    .tripName("친구와 여행")
                    .tripStartDate(LocalDate.of(2022, 9, 13).plusDays(i))
                    .tripEndDate(LocalDate.of(2022, 9, 14).plusDays(i))
                    .cityName(cityCreateResponseDto1.getCityName())
                    .build();
            tripService.createTrip(tripCreateRequestDto);
        }

        for (int i = 0; i < 3; i++) {
            TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                    .tripName("친구와 여행")
                    .tripStartDate(LocalDate.of(2022, 9, 13).plusDays(i))
                    .tripEndDate(LocalDate.of(2022, 9, 14).plusDays(i))
                    .cityName(cityCreateResponseDto2.getCityName())
                    .build();
            tripService.createTrip(tripCreateRequestDto);
        }

        for (int i = 0; i < 4; i++) {
            TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                    .tripName("혼자 여행")
                    .tripStartDate(LocalDate.of(2022, 9, 13).plusDays(i))
                    .tripEndDate(LocalDate.of(2022, 9, 14).plusDays(i))
                    .cityName(cityCreateResponseDto3.getCityName())
                    .build();
            tripService.createTrip(tripCreateRequestDto);
        }

        //then
        mvc.perform(RestDocumentationRequestBuilders.get("/api/city/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("city-list",
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content_Type")
                        ),
                        responseFields(
                                fieldWithPath("count").description("조회한 도시 리스트 사이즈"),
                                fieldWithPath("cities").description("조회한 도시 리스트"),
                                fieldWithPath("cities[].cityId").description("조회한 도시 리스트의 도시 고유 아이디"),
                                fieldWithPath("cities[].cityName").description("조회한 도시 리스트의 도시 이름"),
                                fieldWithPath("cities[].cityInfo").description("조회한 도시 리스트의 도시 정보"),
                                fieldWithPath("cities[].cityRegisteredDate").description("조회한 도시 리스트의 도시 등록 시간"),
                                fieldWithPath("cities[].cityGetOneDate").description("조회한 도시 리스트의 조회 시간"),
                                fieldWithPath("cities[].tripName").description("조회한 도시 리스트의 여행 이름"),
                                fieldWithPath("cities[].tripStartDate").description("조회한 도시 리스트의 여행 시작 날짜"),
                                fieldWithPath("cities[].tripEndDate").description("조회한 도시 리스트의 여행 종료 날짜")
                        )
                ));
    }
}
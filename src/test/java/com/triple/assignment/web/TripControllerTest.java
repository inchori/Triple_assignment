package com.triple.assignment.web;

import com.triple.assignment.web.city.CityCreateRequestDto;
import com.triple.assignment.web.trip.TripCreateRequestDto;
import com.triple.assignment.web.trip.TripCreateResponseDto;
import com.triple.assignment.service.city.repository.CityRepository;
import com.triple.assignment.service.trip.repository.TripRepository;
import com.triple.assignment.service.city.CityService;
import com.triple.assignment.service.trip.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

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

class TripControllerTest extends ApiDocsConfig {
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityService cityService;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private TripService tripService;

    @BeforeEach
    public void setUp() {
        tripRepository.deleteAll();
        cityRepository.deleteAll();

        CityCreateRequestDto cityCreateRequestDto = CityCreateRequestDto.builder()
                .cityName("London")
                .cityInfo("The capital of U.K")
                .build();

        cityService.create(cityCreateRequestDto);
    }

    @Test
    @DisplayName("여행 등록")
    public void 여행_등록() throws Exception {
        TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                .tripName("Trip with Family")
                .tripStartDate(LocalDateTime.of(2021, 10, 5, 0, 0))
                .tripEndDate(LocalDateTime.of(2021, 10, 30, 0, 0))
                .cityName("London")
                .build();

        mvc.perform(post("/api/trip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tripCreateRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("tripName").value("Trip with Family"))
                .andExpect(jsonPath("tripStartDate").exists())
                .andExpect(jsonPath("tripEndDate").exists())
                .andExpect(jsonPath("cityInfo").value("The capital of U.K"))
                .andDo(document("register-trip",
                        requestFields(
                                fieldWithPath("tripName").description("등록할 여행 이름"),
                                fieldWithPath("tripStartDate").description("등록할 여행 시작 날짜"),
                                fieldWithPath("tripEndDate").description("등록할 여행 종료 날짜"),
                                fieldWithPath("cityName").description("여행할 도시 이름")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("tripId").description("등록된 여행 고유 아이디"),
                                fieldWithPath("tripName").description("등록된 여행 이름"),
                                fieldWithPath("tripStartDate").description("등록된 여행 시작 날짜"),
                                fieldWithPath("tripEndDate").description("등록된 여행 종료 날짜"),
                                fieldWithPath("cityInfo").description("등록된 여행의 도시 정보")
                        )
                ));
    }

    @Test
    @DisplayName("여행 단건 조회")
    public void 여행_단건_조회() throws Exception {
        //given
        TripCreateRequestDto tripCreateRequestDto = TripCreateRequestDto.builder()
                .tripName("Trip with Family")
                .tripStartDate(LocalDateTime.of(2021, 10, 5, 0, 0))
                .tripEndDate(LocalDateTime.of(2021, 10, 30, 0, 0))
                .cityName("London")
                .build();

        TripCreateResponseDto tripCreateResponseDto = tripService.createTrip(tripCreateRequestDto);

        mvc.perform(RestDocumentationRequestBuilders.get("/api/trip/{id}", tripCreateResponseDto.getTripId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("tripId").exists())
                .andExpect(jsonPath("tripName").exists())
                .andExpect(jsonPath("tripStartDate").exists())
                .andExpect(jsonPath("tripEndDate").exists())
                .andExpect(jsonPath("cityInfo").exists())
                .andDo(document("trip-getOne",
                        pathParameters(
                                parameterWithName("id").description("조회할 여행의 이름")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content Type")
                        ),
                        responseFields(
                                fieldWithPath("tripId").description("조회된 여행의 고유 아이디"),
                                fieldWithPath("tripName").description("조회된 여행 이름"),
                                fieldWithPath("tripStartDate").description("조회된 여행 시작 날짜"),
                                fieldWithPath("tripEndDate").description("조회된 여행 종료 날짜"),
                                fieldWithPath("cityInfo").description("조회된 여행 도시 정보")
                        )
                ));


    }

}
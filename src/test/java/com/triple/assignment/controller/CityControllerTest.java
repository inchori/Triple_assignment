package com.triple.assignment.controller;

import com.triple.assignment.dto.CityCreateRequestDto;
import com.triple.assignment.dto.CityCreateResponseDto;
import com.triple.assignment.repository.CityRepository;
import com.triple.assignment.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

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
}
package com.triple.assignment.service.city;

import com.triple.assignment.service.city.domain.City;
import com.triple.assignment.service.city.exception.CityExistedException;
import com.triple.assignment.service.city.exception.CityNotFoundException;
import com.triple.assignment.service.city.repository.CityRepository;
import com.triple.assignment.service.trip.domain.Trip;
import com.triple.assignment.web.city.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final static LocalDate lastOneDay = LocalDate.now().minusDays(1);
    private final static LocalDateTime lastOneWeek = LocalDateTime.now().minusWeeks(1);

    public CityCreateResponseDto create(CityCreateRequestDto cityCreateRequestDto) {
        if (cityRepository.findByName(cityCreateRequestDto.getCityName()).isPresent()) {
            throw new CityExistedException();
        }
        City savedCity = cityRepository.save(City.createCity(cityCreateRequestDto));
        return modelMapper.map(savedCity, CityCreateResponseDto.class);
    }


    public CityGetOneResponseDto getOneCity(Long id) {
        City findCity = cityRepository.findById(id).orElseThrow(CityNotFoundException::new);
        findCity.setGetOneDate(LocalDateTime.now());
        return modelMapper.map(findCity, CityGetOneResponseDto.class);
    }


    @Transactional(readOnly = true)
    public CityTripResponseDto getCities() {
        List<City> cities = cityRepository.findTenCities();
        List<Trip> trip = new ArrayList<>();
        List<City> notTripCities = new ArrayList<>();
        for (City city : cities) {
            if (city.getTrip().size() > 0) trip.addAll(city.getTrip());
            else notTripCities.add(city);
        }

        trip.sort(this::extracted);
        notTripCities.sort(this::notTrip);

        List<CityTripGetOneResponseDto> cityTripGetOneResponseList = new ArrayList<>();
        List<Trip> tripPaging = trip.stream().limit(10).collect(Collectors.toList());

        for (Trip tripPage : tripPaging) {
            cityTripGetOneResponseList.add(new CityTripGetOneResponseDto(tripPage));
        }

        for (City notTripCity : notTripCities) {
            if (cityTripGetOneResponseList.size() >= 10) break;
            cityTripGetOneResponseList.add(new CityTripGetOneResponseDto(notTripCity));
        }

        return CityTripResponseDto.builder()
                .cities(cityTripGetOneResponseList)
                .count(cityTripGetOneResponseList.size())
                .build();
    }

    private int extracted(Trip o1, Trip o2) {
        if (o1.getStartTripDate().equals(o2.getStartTripDate())) {
            if (o1.getCity().getRegisterDate().isAfter(lastOneDay) && o2.getCity().getRegisterDate().isAfter(lastOneDay)) { //
                if (o1.getCity().getRegisterDate().equals(o2.getCity().getRegisterDate())) {
                    if (o1.getCity().getGetOneDate().isAfter(lastOneWeek) && o2.getCity().getGetOneDate().isAfter(lastOneWeek)) {
                        return o2.getCity().getGetOneDate().compareTo(o1.getCity().getGetOneDate());
                    }
                    return extracted3(o1.getCity(), o2.getCity());
                }
                return o2.getCity().getRegisterDate().compareTo(o1.getCity().getRegisterDate());
            }
            return extracted2(o1.getCity(), o2.getCity());
        }
        return o1.getStartTripDate().compareTo(o2.getStartTripDate());
    }

    private int notTrip(City o1, City o2) {
        if (o1.getRegisterDate().isAfter(lastOneDay) && o2.getRegisterDate().isAfter(lastOneDay)) { //
            if (o1.getRegisterDate().equals(o2.getRegisterDate())) {
                if (o1.getGetOneDate().isAfter(lastOneWeek) && o2.getGetOneDate().isAfter(lastOneWeek)) {
                    return o2.getGetOneDate().compareTo(o1.getGetOneDate());
                }
                return extracted3(o1, o2);
            }
            return o2.getRegisterDate().compareTo(o1.getRegisterDate());
        }
        return extracted2(o1, o2);
    }

    private int extracted2(City o1, City o2) {
        if (o1.getRegisterDate().isAfter(lastOneDay)) return 1;
        else if (o2.getRegisterDate().isAfter(lastOneDay)) return -1;
        return 0;
    }

    private int extracted3(City o1, City o2) {
        if (o1.getGetOneDate().isAfter(lastOneWeek)) return 1;
        else if (o2.getGetOneDate().isAfter(lastOneWeek)) return -1;
        return 0;
    }
}

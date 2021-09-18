package com.triple.assignment.service.trip.domain;

import com.triple.assignment.service.city.domain.City;
import com.triple.assignment.web.trip.TripCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;

    @Column(name = "trip_name")
    private String name;

    @Column(name = "start_trip_time")
    private LocalDate startTripDate;

    @Column(name = "end_trip_time")
    private LocalDate endTripDate;

    @JoinColumn(name = "city_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    private void setCity(City city) {
        this.city = city;
        city.getTrip().add(this);
    }

    public static Trip createTrip(TripCreateRequestDto createRequestDto, City city) {
        Trip trip = Trip.builder()
                .name(createRequestDto.getTripName())
                .startTripDate(createRequestDto.getTripStartDate())
                .endTripDate(createRequestDto.getTripEndDate())
                .build();
        trip.setCity(city);
        return trip;
    }

}

package com.triple.assignment.entity;

import com.triple.assignment.dto.TripCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime startTripDate;

    @Column(name = "end_trip_time")
    private LocalDateTime endTripDate;

    @JoinColumn(name = "city_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    public void setCity(City city) {
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

package com.triple.assignment.service.city.domain;
import com.triple.assignment.service.trip.domain.Trip;
import com.triple.assignment.web.city.CityCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "city")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @Column(name = "city_name")
    private String name;

    @Column(name = "city_info")
    private String info;

    @Column(name = "register_date")
    @CreatedDate
    private LocalDateTime registerDate;

    @Column(name = "get_one_date")
    private LocalDateTime getOneDate;

    public void setGetOneDate(LocalDateTime getOneDate) {
        this.getOneDate = getOneDate;
    }

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private final List<Trip> trip = new ArrayList<>();

    public static City createCity(CityCreateRequestDto cityCreateRequestDto) {
        City city = City.builder()
                .name(cityCreateRequestDto.getCityName())
                .info(cityCreateRequestDto.getCityInfo())
                .registerDate(LocalDateTime.now())
                .build();
        return city;
    }
}

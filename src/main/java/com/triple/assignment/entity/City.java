package com.triple.assignment.entity;


import com.triple.assignment.dto.CityCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @OneToOne(mappedBy = "city")
    private Trip trip;

    @Column(name = "register_date")
    @CreatedDate
    private LocalDateTime registerDate;

//    @Column(name = "get_one_date")
//    private LocalDateTime getOneDate;

    public static City createCity(CityCreateRequestDto cityCreateRequestDto) {
        City city = City.builder()
                .name(cityCreateRequestDto.getCityName())
                .info(cityCreateRequestDto.getCityInfo())
                .registerDate(LocalDateTime.now())
                .build();
        return city;
    }
}

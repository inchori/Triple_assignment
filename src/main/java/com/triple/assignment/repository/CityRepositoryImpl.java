package com.triple.assignment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triple.assignment.entity.City;
import com.triple.assignment.entity.Date;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.triple.assignment.entity.QCity.city;
import static com.triple.assignment.entity.QTrip.trip;

public class CityRepositoryImpl implements CityCustomRepository {

    private JPAQueryFactory queryFactory;

    public CityRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    Date date = new Date(1L, LocalDateTime.now(), LocalDateTime.now().minusDays(1L));

    @Override
    public List<City> findTenCities() {
        LocalDateTime nowSubOne = LocalDateTime.now().minusDays(1L);
        LocalDateTime now = LocalDateTime.now();

        return queryFactory.select(city)
                .from(city, trip)
                .where(city.id.eq(trip.city.id))
                .orderBy(trip.startTripDate.asc())
                .offset(0)
                .limit(10)
                .fetch();
    }
}

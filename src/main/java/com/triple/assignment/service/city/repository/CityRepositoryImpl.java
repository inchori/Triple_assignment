package com.triple.assignment.service.city.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triple.assignment.service.city.domain.City;

import javax.persistence.EntityManager;
import java.util.List;

import static com.triple.assignment.service.city.domain.QCity.city;
import static com.triple.assignment.service.trip.domain.QTrip.trip;

public class CityRepositoryImpl implements CityCustomRepository {

    private JPAQueryFactory queryFactory;

    public CityRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }


    //select c.* from city c, trip t where c.trip_id = t.trip_id order by ;
    @Override
    public List<City> findTenCities() {
        return queryFactory.selectDistinct(city)
                .from(city, trip)
                .innerJoin(city.trip, trip)
                .orderBy(trip.startTripDate.asc())
                .fetchJoin()
                .fetch();
    }

}

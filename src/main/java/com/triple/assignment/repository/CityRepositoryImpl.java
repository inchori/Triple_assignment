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


    //select c.* from city c, trip t where c.trip_id = t.trip_id order by ;
    @Override
    public List<City> findTenCities() {
        Date date = new Date(1L, LocalDateTime.now(), LocalDateTime.now().minusDays(1), LocalDateTime.now().minusWeeks(1));
        List<City> cities = queryFactory.select(city)
                .from(city, trip)
                .where(city.id.eq(trip.city.id))
                .orderBy(
                        trip.startTripDate.before(date.getNow()).asc(),
                        trip.startTripDate.asc(),
                        city.registerDate.between(date.getOneDayMinus(), date.getNow()).desc(),
                        city.getOneDate.between(date.getOneWeekMinus(), date.getNow()).desc())
                .offset(0)
                .limit(10)
                .fetch();
        for (City city1 : cities) {
            System.out.println("city1 = " + city1);
        }
        return cities;
    }

}

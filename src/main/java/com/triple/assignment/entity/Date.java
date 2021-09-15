package com.triple.assignment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Date {

    @Id
    private Long id;

    private LocalDateTime now;

    private LocalDateTime oneDayMinus;

    public Date(Long id, LocalDateTime now, LocalDateTime oneDayMinus) {
        this.id = id;
        this.now = now;
        this.oneDayMinus = oneDayMinus;
    }

}

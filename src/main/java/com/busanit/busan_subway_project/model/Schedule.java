package com.busanit.busan_subway_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;

@Entity
@Data
@Table(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schedule_id;     // 스케줄 ID(인조 식별자)

    @Column(nullable = false)
    private int continuity;

    @ManyToOne
    @JoinColumn(name = "scode")
    Station station;

    @Column(nullable = false)
    private Time arrival_time;

    @Column
    private int direction; // 상하행 구분(상행 : 1, 하행 : 2)

    @Column
    private int day; // 요일 구분(평일 : 1, 토요일 : 2, 공휴일 : 3)
}

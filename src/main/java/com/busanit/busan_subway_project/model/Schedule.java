package com.busanit.busan_subway_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Data
@Table(name = "schedule")
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scheduleId;     // 스케줄 ID(인조 식별자)

    @ManyToOne  // 1:N
    @JoinColumn(name = "scode")
    private Station station;    // 역 코드(FK)

    @Column(nullable = false)
    private Time time;          // 시간
}

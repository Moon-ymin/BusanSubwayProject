package com.busanit.busan_subway_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "station_schedule")
@AllArgsConstructor
@NoArgsConstructor
public class Station_Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stn_sc_id;

    @Column
    private String time;

    @ManyToOne
    @JoinColumn(name = "scode")
    Station station;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    Schedule schedule;
}

package com.busanit.busan_subway_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Data
@Table(name = "exchange")
@AllArgsConstructor
@NoArgsConstructor
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int exchange_id;

    @OneToOne
    @JoinColumn(name = "scode")
    Station station;

    @Column(nullable = false)
    private int line_cd;

    @Column(nullable = false)
    private int ex_scode;

    @Column(nullable = false)
    private int ex_line_cd;

    @Column
    private Time walking_time;
}

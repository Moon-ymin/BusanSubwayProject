package com.busanit.busan_subway_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "station_location")
@AllArgsConstructor
@NoArgsConstructor
public class StationLocation {

    @Id // ??
    @OneToOne
    @JoinColumn(name = "scode")
    private Station station;    // 역 코드(PK & FK)

    @Column(nullable = false)
    private int x1;

    @Column(nullable = false)
    private int y1;

    @Column(nullable = false)
    private int x2;

    @Column(nullable = false)
    private int y2;
}

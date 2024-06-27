package com.busanit.busan_subway_project.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "metro")
public class Metro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 고유 식별자

    private int dist;           // 이동거리 (단위 : 0.1km)
    private int endSc;          // 도착역코드 (Station의 scode와 상동)
    private String endSn;       // 도착역명
    private String exchange;    // 환승역 구분 (환승역 : Y, 환승역 아님 : N)
    private int startSc;        // 출발역코드 (Station의 scode와 상동
    private String startSn;     // 출발역명
    private int stoppingTime;   // 정차시간 (단위 : 초)
    private int time;           // 이동시간 (단위 : 초)
}

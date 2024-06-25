package com.busanit.busan_subway_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


@Data   // Getter, Setter 생성
@AllArgsConstructor //  LocationData 무조건 출발, 경유, 도착 파라미터 값 다 넣을거임
@NoArgsConstructor
public class LocationData {
    private Integer from;
    private Integer via;
    private Integer to;
    private String settingTime;

    public LocationData(Integer from, Integer via, Integer to) {
        this.from = from;
        this.via = via;
        this.to = to;
        this.settingTime = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}

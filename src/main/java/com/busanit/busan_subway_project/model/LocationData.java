package com.busanit.busan_subway_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data   // Getter, Setter 생성
@AllArgsConstructor //  LocationData 무조건 출발, 경유, 도착 파라미터 값 다 넣을거임
public class LocationData {
    private Integer from;
    private Integer via;
    private Integer to;
}

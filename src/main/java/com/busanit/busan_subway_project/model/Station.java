package com.busanit.busan_subway_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "station")
@AllArgsConstructor
@NoArgsConstructor
public class Station {

    @Id
    private Long scode;  // 역 코드

    @Column(nullable = false, unique = true)
    private String sname;           // 역 명

    @ManyToOne  // 1:N
    @JoinColumn(name = "line_cd")
    private Line line;              // 호선 코드(FK)

    @Column(nullable = false)
    private Long exchange;

}

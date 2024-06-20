package com.busanit.busan_subway_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "line")
@AllArgsConstructor
@NoArgsConstructor
public class Line {

    @Id
    @Column(name = "line_cd")
    private int lineCd;         // 호선 코드

    @Column(name = "line_name", nullable = false, unique = true)
    private String lineName;    // 호선명
}

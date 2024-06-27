package com.busanit.busan_subway_project.model;

import com.busanit.busan_subway_project.Subway;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ResultWrapper {    // 최소환승, 최단시간 경로 결과를 담을 객체
    private Subway.Result minTransferResult;
    private Subway.Result minTimeResult;

}

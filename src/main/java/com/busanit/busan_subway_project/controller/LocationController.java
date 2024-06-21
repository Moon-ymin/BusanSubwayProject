package com.busanit.busan_subway_project.controller;

import com.busanit.busan_subway_project.Subway;
import com.busanit.busan_subway_project.model.LocationData;
import com.busanit.busan_subway_project.model.Metro;
import com.busanit.busan_subway_project.model.ResultWrapper;
import com.busanit.busan_subway_project.model.Station;
import com.busanit.busan_subway_project.service.MetroService;
import com.busanit.busan_subway_project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.busanit.busan_subway_project.Subway.combineResults;

@RestController
@RequestMapping("/api")
public class LocationController {
    @Autowired
    private StationService stationService;
    @Autowired
    private MetroService metroService;

    @PostMapping("/location")
    public ResultWrapper receiveLocationData(@RequestBody LocationData locationData) {
        // 1. 안드로이드로부터 받은 (출발, 경유, 도착) 데이터
        Integer from = locationData.getFrom();
        Integer via = locationData.getVia();
        Integer to = locationData.getTo();

        // 2. 최소환승, 최단경로 메서드 수행하기
        // 1) 객체 생성
        Map<Integer, Map<String, Integer>> stages = new HashMap<>();   // 모든 역의 scode, sname, line_cd 저장
        Map<Integer, Subway.Stage> subwayMap = new HashMap<>();  // 생성되는 모든 객체 저장

        // Station 테이블의 모든 scode, sname, lineCd 값을 가져와서 stages에 추가
        List<Station> stations = stationService.getAllStations();
        for (Station s : stations) {
            Map<String, Integer> stationInfo = new HashMap<>();
            stationInfo.put("name", s.getSname().hashCode());
            stationInfo.put("line_cd", s.getLine().getLineCd());
            stages.put(s.getScode(), stationInfo);
        }

        // 반복문을 통한 노드 객체 생성
        for (Integer key : stages.keySet()) {  // scode 가 key임
            Map<String, Integer> stationInfo = stages.get(key);
            String stationName = ""; // stationInfo에서 이름을 가져와야 함
            for (Station s : stations) {
                if (s.getScode() == key) {
                    stationName = s.getSname();  // 실제 역 이름을 가져옴
                    break;
                }
            }
            int lineCd = stationInfo.get("line_cd");
            Subway.Stage stage = new Subway.Stage(key, stationName, lineCd);
            subwayMap.put(key, stage);
        }

        // 2) 엣지 연결
        // key 와 metro 테이블의 start_sc 인 행 가져와서 매번 edge 추가
        // (metro.end_sc, station.line_cd, metro.time) 필요
        // subwayMap.get(95).edges.add(new Subway.Edge(96,1,5));
        List<Metro> metros = metroService.getAllMetros();
        for (Integer key : stages.keySet()) {
            Integer lineCd = stages.get(key).get("line_cd");  // station.line_cd
            Subway.Stage startStage = subwayMap.get(key);
            for(Metro metro : metros) {
                if (metro.getStartSc() == key.intValue()){
                    Subway.Edge edge = new Subway.Edge(metro.getEndSc(),
                            lineCd.intValue(), metro.getTime());
                    startStage.edges.add(edge);
                }
            }
            // 3. subwayMap 객체 추가
            subwayMap.put(key, startStage);
        }

        // 3. 결과 안드로이드에 전송하기 - 최소환승 경로 및 최단시간 경로 계산
        Subway.Result minTransferResult;
        Subway.Result minTimeResult;
        if (via != 0) {   // 경유지 있는 경우 : from - via, via - to 경로 두 개 생각해서 더하기
            // from - via
            Subway.Result minTransferResult1 = Subway.minTransferRoute(subwayMap, from, via);
            Subway.Result minTimeResult1 = Subway.minTimeRoute(subwayMap, from, via);

            // via - to
            Subway.Result minTransferResult2 = Subway.minTransferRoute(subwayMap, via, to);
            Subway.Result minTimeResult2 = Subway.minTimeRoute(subwayMap, via, to);

            // 합친 결과
            minTransferResult = combineResults(minTransferResult1, minTransferResult2);
            minTimeResult = combineResults(minTimeResult1, minTimeResult2);


        } else {    // 경유지 없는 경우
            minTransferResult = Subway.minTransferRoute(subwayMap, from, to);
            minTimeResult = Subway.minTimeRoute(subwayMap, from, to);
        }

        // schedule 테이블 연결, 운행 시간표 적용

        // 결과를 안드로이드로 전송할 객체 생성
        ResultWrapper resultWrapper = new ResultWrapper(minTransferResult, minTimeResult);

        return resultWrapper;
    }
}

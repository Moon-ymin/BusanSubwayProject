package com.busanit.busan_subway_project;

import com.busanit.busan_subway_project.model.Metro;
import com.busanit.busan_subway_project.model.Schedule;
import com.busanit.busan_subway_project.model.Station;
import com.busanit.busan_subway_project.service.MetroService;
import com.busanit.busan_subway_project.service.ScheduleService;
import com.busanit.busan_subway_project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@SpringBootApplication
public class Test implements CommandLineRunner {
    @Autowired
    private StationService stationService;
    @Autowired
    private MetroService metroService;
    @Autowired
    private ScheduleService scheduleService;


    public static void main(String[] args) {
        SpringApplication.run(Test.class,args);
    }

    @Override
    public void run(String... args) {
        // 1. 객체 생성
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
        for (Integer key : stages.keySet()) {  // scode가 key임
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

        // 2. 엣지 연결
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
// 122, 209
        Subway.Result Result = Subway.minTransferRoute(subwayMap, 912, 917);
        Subway.Result Result2 = Subway.minTimeRoute(subwayMap, 912, 917);

        // schedule 테이블 연결, 운행 시간표 적용
        Result = applySchedule(Result);
        Result2 = applySchedule(Result2);


        /*if (Result != null) {
            System.out.println("Minimum transfers: " + Result.transfers);
            System.out.println("Total time: " + Result.totalTime);
            System.out.println("Path: " + Result.path);
        } else {
            System.out.println("No path found");
        }*/
    }
    private Subway.Result applySchedule(Subway.Result result){
        // 현재 시간

        // 환승하는 경우
        if (result.transfers == 0) {

        } else {    // 환승없는 경우 : 걍 바로 path의 String 뒤에 운행스케줄 붙이면 됨
            String startPath = result.path.get(0);  // scode|sname|line_cd
            String endPath = result.path.get(result.path.size()-1);  // scode|sname|line_cd

            int startSc = Integer.parseInt(startPath.split("\\|")[0]);
            int endSc = Integer.parseInt(endPath.split("\\|")[0]);

            int direction = startSc < endSc ? 1 : 2; // 상행 1, 하행 2
            // int day = ; // day 는 어케할겨?
            List<Schedule> schedules = scheduleService.getSchedules(direction, 1, startSc, new Time(System.currentTimeMillis()), endSc);
            System.out.println(schedules);

        }




        return result;
    }
}

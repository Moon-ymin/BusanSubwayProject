package com.busanit.busan_subway_project;

import com.busanit.busan_subway_project.model.Metro;
import com.busanit.busan_subway_project.model.Schedule;
import com.busanit.busan_subway_project.model.Station;
import com.busanit.busan_subway_project.service.HolidayService;
import com.busanit.busan_subway_project.service.MetroService;
import com.busanit.busan_subway_project.service.ScheduleService;
import com.busanit.busan_subway_project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Test implements CommandLineRunner {
    @Autowired
    private StationService stationService;
    @Autowired
    private MetroService metroService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private ScheduleService scheduleService;
    private LocalDate localDate;
    private LocalTime time;


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

    }
    // 운행 시간표 추가하는 메서드
    private Subway.Result applySchedule(Subway.Result result) {
        // time : Time 타입, 일단은 현재 시간 넣을 거임
        time = LocalTime.now();    // 19:15:49.934230

        // day : (평일 : 1, 토요일 : 2, 공휴일 : 3)
        localDate = LocalDate.now();    // 현재 서버 날짜
        //LocalDate customdate = LocalDate.of(2024,6,6); // 커스텀 날짜
        int day;
        if ( holidayService.isHoliday(localDate) ){ // 먼저 공휴일인지 확인
            day = 3;
        } else {    // 공휴일 아니라면
            switch ( localDate.getDayOfWeek() ){
                case SUNDAY: day = 3; break;    // 일요일이면 3
                case SATURDAY: day = 2; break;  // 토요일이면 2
                default: day = 1; break;        // 그 외 평일이면 1
            }
        }
        // 추가로 direction, scode(start, end) 필요
        if (result.transfers != 0) {    // 환승인 경우
            List<List<String>> paths = splitTransferPaths(result.path);
            Time arrivalTime = null;
            for(List<String> p : paths){
                int startCd = Integer.parseInt(p.get(0).split("\\|")[0]);
                int endCd = Integer.parseInt(p.get(p.size()-1).split("\\|")[0]);
                int direction = startCd < endCd ? 1 : 2;
                int small = 0; int big = 0;
                if (startCd < endCd) {
                    small = startCd; big = endCd;
                } else {
                    small = endCd; big = startCd;
                }
                List<Schedule> schedules = scheduleService.getSchedules(startCd, small, big, time, direction, day);


                for (int i = 0; i < p.size(); i++) {
                    String change = result.path.get(i);
                    arrivalTime = schedules.get(i).getArrival_time();
                    change += "|" + arrivalTime.toString();  // 운행 시간표까지 붙이기
                    result.path.set(i, change);
                }
                time = arrivalTime.toLocalTime();
            }
        } else {    // 환승아닌경우
            int startCd = Integer.parseInt(result.path.get(0).split("\\|")[0]); // scode|sname|line_cd
            int endCd = Integer.parseInt(result.path.get(result.path.size() - 1)
                    .split("\\|")[0]);
            int direction = startCd < endCd ? 1 : 2;
            int small = 0; int big = 0;
            if (startCd < endCd) {
                small = startCd; big = endCd;
            } else {
                small = endCd; big = startCd;
            }
            List<Schedule> schedules = scheduleService.getSchedules(startCd, small, big, time, direction, day);

            for (int i = 0; i < schedules.size(); i++) {
                String change = result.path.get(i);
                change += "|" + schedules.get(i).getArrival_time().toString();  // 운행 시간표까지 붙이기
                result.path.set(i, change);
            }
        }

        return result;
    }
    private List<List<String>> splitTransferPaths(List<String> resultpath){
        List<List<String>> transferLines = new ArrayList<>(); // 호선 다른 경로끼리 묶여진 이중 배열
        List<String> currentLine = new ArrayList<>();  // 호선 같은 애들 담은 배열
        String preLine = null;

        for (String path : resultpath) {
            String lineCd = path.split("\\|")[2];
            if (preLine != null && !lineCd.equals(preLine)) { // 환승했을 경우
                transferLines.add(currentLine);
                currentLine = new ArrayList<>();
            }
            currentLine.add(path);
            preLine = lineCd;
        }
        if (!currentLine.isEmpty()) transferLines.add(currentLine); // 마지막 경로 담기

        return transferLines;
    }

}

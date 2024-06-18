package com.busanit.busan_subway_project.controller;

import com.busanit.busan_subway_project.service.StationService;
import com.busanit.busan_subway_project.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StationController {
    @Autowired
    private StationService stationService;

    // scode 로 Station 조회하기
    @GetMapping("station/{scode}")
    public Station getStationByScode(@PathVariable Integer scode){
        return stationService.getSname(scode);
    }

    // sname으로 Station 조회하기
    @GetMapping("station/name/{sname}")
    public Station getStationBySname(@PathVariable String sname) { return stationService.getScode(sname); }
}

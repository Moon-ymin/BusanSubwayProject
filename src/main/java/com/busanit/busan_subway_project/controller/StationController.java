package com.busanit.busan_subway_project.controller;

import com.busanit.busan_subway_project.dao.StationDao;
import com.busanit.busan_subway_project.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StationController {
    @Autowired
    private StationDao stationDao;

    // scode 로 Station 조회하기
    @GetMapping("station/{scode}")
    public Station getStationByScode(@PathVariable Integer scode){
        return stationDao.getSname(scode);
    }

    // sname으로 Station 조회하기
    @GetMapping("station/name/{sname}")
    public Station getStationBySname(@PathVariable String sname) { return stationDao.getScode(sname); }
}

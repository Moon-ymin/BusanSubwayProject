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

    @GetMapping("station/{scode}")
    public Station getStationByScode(@PathVariable("scode") Integer scode){
        return stationDao.getSname(scode);
    }
}

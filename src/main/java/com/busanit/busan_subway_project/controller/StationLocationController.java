package com.busanit.busan_subway_project.controller;

import com.busanit.busan_subway_project.service.StationLocationService;
import com.busanit.busan_subway_project.model.Station_Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StationLocationController {
    @Autowired
    private StationLocationService stationLocationService;

    @GetMapping("stationlocation/{scode}")
    public Station_Location getSLByScode(@PathVariable Long scode){
        return stationLocationService.getStationLocation(scode);
    }

}

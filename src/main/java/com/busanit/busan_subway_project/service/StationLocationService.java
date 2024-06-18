package com.busanit.busan_subway_project.service;

import com.busanit.busan_subway_project.model.Station_Location;
import com.busanit.busan_subway_project.repo.StationLocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationLocationService {
    @Autowired
    private StationLocationRepo stationLocationRepo;

    // Station으로 x1, y1, x2, y2 출력
    public Station_Location getStationLocation(Long scode){
        Station_Location sl = stationLocationRepo.findStationLocationByScode(scode);
        return new Station_Location(sl.getScode(), sl.getStation(), sl.getX1(), sl.getY1(), sl.getX2(), sl.getY2());
    }
}

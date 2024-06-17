package com.busanit.busan_subway_project.dao;

import com.busanit.busan_subway_project.model.Station;
import com.busanit.busan_subway_project.repo.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationDao {
    @Autowired  // data access object, jpa 쓸 때 필요
    private StationRepo stationRepo;

    // Station 에서 scode로 sname을 반환하는 메서드
    public Station getSname(Integer scode) {
        Station station = stationRepo.findByScode(scode);
        return new Station(station.getScode(), station.getSname(), station.getLine(), station.getExchange());
    }
}

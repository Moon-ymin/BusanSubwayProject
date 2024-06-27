package com.busanit.busan_subway_project.repo;

import com.busanit.busan_subway_project.model.Station;
import com.busanit.busan_subway_project.model.Station_Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationLocationRepo extends JpaRepository<Station_Location, Integer> {
    // Station_Location의 PK는 Station
    Station_Location findStationLocationByScode(int scode);
}

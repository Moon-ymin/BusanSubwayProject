package com.busanit.busan_subway_project.repo;

import com.busanit.busan_subway_project.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepo extends JpaRepository<Station, Integer> {
    // Station 의 PK는 scode, Integer 타입
    Station findByScode(Integer scode);
}

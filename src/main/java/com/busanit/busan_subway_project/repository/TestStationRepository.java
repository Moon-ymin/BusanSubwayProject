package com.busanit.busan_subway_project.repository;

import com.busanit.busan_subway_project.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestStationRepository extends JpaRepository<Station, Long> {

    Station findBySname(String sname);
}

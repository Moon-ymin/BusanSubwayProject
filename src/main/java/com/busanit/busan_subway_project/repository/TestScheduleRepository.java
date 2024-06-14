package com.busanit.busan_subway_project.repository;

import com.busanit.busan_subway_project.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestScheduleRepository extends JpaRepository<Schedule, Long> {}

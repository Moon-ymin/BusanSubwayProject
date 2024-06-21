package com.busanit.busan_subway_project.repo;

import com.busanit.busan_subway_project.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {
    @Query("SELECT ss FROM Schedule ss WHERE ss.direction = :direction AND ss.day = :day " +
            "AND ss.scode = :start AND ss.arrivalTime >= :time ORDER BY ss.arrivalTime ASC")
    List<Schedule> findFirstSchedule(@Param("direction") int direction, @Param("day") int day,
                                     @Param("start") int start, @Param("time") Time time);

    @Query("SELECT s FROM Schedule s WHERE s.continuity = :continuity AND s.scode BETWEEN :start AND :end")
    List<Schedule> findSchedulesWithContinuity(@Param("continuity") int continuity,
                                               @Param("start") int start, @Param("end") int end);
}



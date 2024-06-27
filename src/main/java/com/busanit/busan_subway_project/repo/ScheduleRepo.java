package com.busanit.busan_subway_project.repo;


import com.busanit.busan_subway_project.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Integer> {
    @Query(value = "SELECT * FROM schedule s WHERE s.continuity = " +
            "(SELECT sc.continuity FROM schedule sc WHERE sc.direction = :direction " +
            "AND sc.day = :day AND sc.scode = :start " +
            "AND sc.arrival_time >= :time " +
            "order by arrival_time " +
            "LIMIT 1) " +
            "AND s.scode BETWEEN :small AND :big " +
            "ORDER BY s.schedule_id", nativeQuery = true)
    List<Schedule> findSchedules(@Param("start") int start,
                                 @Param("small") int small,
                                 @Param("big") int big,
                                 @Param("time") LocalTime time,
                                 @Param("direction") int direction,
                                 @Param("day") int day);
}

package com.busanit.busan_subway_project.service;

import com.busanit.busan_subway_project.model.Schedule;
import com.busanit.busan_subway_project.repo.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;

    public List<Schedule> getSchedules(int start, int small, int big, LocalTime time,
                                       int direction, int day) {
        return scheduleRepo.findSchedules(start, small, big, time, direction, day);
    }
}


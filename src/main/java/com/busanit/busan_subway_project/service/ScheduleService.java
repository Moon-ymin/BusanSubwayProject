package com.busanit.busan_subway_project.service;

import com.busanit.busan_subway_project.model.Schedule;
import com.busanit.busan_subway_project.repo.ScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepo scheduleRepo;

    public List<Schedule> getSchedules(int direction, int day, int start, Time time, int end) {
        // 첫 번째 스케줄을 가져오기 위해 Pageable을 사용
        List<Schedule> subSchedules = scheduleRepo.findFirstSchedule(direction, day, start, time);
        if (subSchedules.isEmpty()) {
            return new ArrayList<>();
        }

        int continuity = subSchedules.get(0).getContinuity();
        return scheduleRepo.findSchedulesWithContinuity(continuity, start, end);
    }
}


package com.busanit.busan_subway_project.controller;

import com.busanit.busan_subway_project.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/holidays")
public class HolidayController {
    @Autowired
    private HolidayService holidayService;

    @GetMapping("/isHoliday")
    public int isHoliday(@RequestParam LocalDate date) {
        boolean isHoliday = holidayService.isHoliday(date);
        return isHoliday ? 1 : 0;
    }
}

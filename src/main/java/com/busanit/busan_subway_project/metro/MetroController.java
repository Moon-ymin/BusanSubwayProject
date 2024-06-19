package com.busanit.busan_subway_project.metro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MetroController {
    @Autowired
    private MetroService metroService;

    @GetMapping("/train-schedule")
    public List<Metro> getTrainSchedule(
            @RequestParam String act,
            @RequestParam String scode
    ) {
        return metroService.getMetro(act, scode);
    }
}

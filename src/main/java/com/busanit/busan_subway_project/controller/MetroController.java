package com.busanit.busan_subway_project.controller;

import com.busanit.busan_subway_project.model.Metro;
import com.busanit.busan_subway_project.service.MetroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MetroController {

    private static final Logger logger = LoggerFactory.getLogger(MetroController.class);

    @Autowired
    private MetroService metroService;

    @GetMapping("/train-schedule")
    public List<Metro> getTrainSchedule() {
        metroService.fetchAndSaveMetroDataOnce(); // 데이터 가져오고 저장
        return metroService.getAllMetros(); // 저장된 데이터 반환
    }
}


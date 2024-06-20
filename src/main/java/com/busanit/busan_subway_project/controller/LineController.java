package com.busanit.busan_subway_project.controller;


import com.busanit.busan_subway_project.service.LineService;
import com.busanit.busan_subway_project.model.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineController {
    @Autowired
    private LineService lineService;

    @GetMapping("/line/{line_cd}")
    public Line getLineByCode(@PathVariable("line_cd") int line_cd){
        return lineService.getLine(line_cd);
    }
}

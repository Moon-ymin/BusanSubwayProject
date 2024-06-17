package com.busanit.busan_subway_project.controller;


import com.busanit.busan_subway_project.dao.LineDao;
import com.busanit.busan_subway_project.model.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineController {
    @Autowired
    private LineDao lineDao;

    @GetMapping("/line/{line_cd}")
    public Line getLineByCode(@PathVariable("line_cd") Long line_cd){
        return lineDao.getLine(line_cd);
    }
}

package com.busanit.busan_subway_project.service;

import com.busanit.busan_subway_project.model.Line;
import com.busanit.busan_subway_project.repo.LineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineService {
    @Autowired
    private LineRepo lineRepo;

    // line-cd 로 Line 출력 메서드
    public Line getLine(int line_cd){
        Line line = lineRepo.findByLineCd(line_cd);
        return new Line(line.getLineCd(), line.getLineName());
    }
}

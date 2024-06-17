package com.busanit.busan_subway_project.repo;

import com.busanit.busan_subway_project.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepo extends JpaRepository<Line, Long> {
    // pk는 line_cd, Long 타입
    Line findByLineCd(Long lineCd);
}

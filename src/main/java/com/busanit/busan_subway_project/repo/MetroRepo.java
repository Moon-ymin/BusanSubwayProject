package com.busanit.busan_subway_project.repo;

import com.busanit.busan_subway_project.model.Metro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetroRepo extends JpaRepository<Metro, Integer> {
}

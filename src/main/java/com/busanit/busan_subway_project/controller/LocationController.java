package com.busanit.busan_subway_project.controller;

import com.busanit.busan_subway_project.model.LocationData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LocationController {

    @PostMapping("/location")
    public String receiveLocationData(@RequestBody LocationData locationData) {
        // 안드로이드로부터 받은 (출발, 경유, 도착) 데이터
        Integer from = locationData.getFrom();
        Integer via = locationData.getVia();
        Integer to = locationData.getTo();

        // 다른 비즈니스 로직 수행!!
        // 최소환승, 최단경로 메서드 수행하기

        // 예제로서 간단하게 받은 데이터를 로그에 출력하고 응답을 반환
        System.out.println("Received location data:");
        System.out.println("From: " + from);
        System.out.println("Via: " + via);
        System.out.println("To: " + to);

        // 안드로이드로 응답 반환
        return "Location data received successfully!";
    }
}

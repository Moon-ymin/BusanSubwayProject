package com.busanit.busan_subway_project.controller;

import com.busanit.busan_subway_project.service.OpenApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class OpenApiController {

    private final OpenApiService openApiService;

    @Autowired
    public OpenApiController(OpenApiService openApiService) {
        this.openApiService = openApiService;
    }

    @GetMapping("/schedule")
    public String getSubScheduleData(@RequestParam String sname) throws URISyntaxException, IOException {

        return openApiService.getSubScheduleData(sname);
    }
}

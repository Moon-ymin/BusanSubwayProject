package com.busanit.busan_subway_project.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

@Service
public class OpenApiService {

    private final RestTemplate restTemplate;
    private final String serviceKey;

    public OpenApiService(RestTemplate restTemplate, @Value("${api.service.key}") String serviceKey) {
        this.restTemplate = restTemplate;
        this.serviceKey = serviceKey;
    }

    public String getSubScheduleData(String sname) {

        String url = "http://data.humetro.busan.kr/voc/api/open_api_process.tnn";

        System.out.println(serviceKey);

        UriComponents builder = UriComponentsBuilder
                .fromHttpUrl(url.toString())
                .queryParam("serviceKey", URLEncoder.encode(serviceKey, StandardCharsets.UTF_8))
                .queryParam("scode", 101)
                .queryParam("act", "xml")
                .build();

        System.out.println(builder.toString());

        return restTemplate.getForObject(builder.toString(), String.class);
    }
}

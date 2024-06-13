package com.busanit.busan_subway_project.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;

@Service
public class OpenApiService {

    private final RestTemplate restTemplate;
    private final String serviceKey;

    public OpenApiService(RestTemplate restTemplate, @Value("${api.service.key}") String serviceKey) {
        this.restTemplate = restTemplate;
        this.serviceKey = serviceKey;
    }

    public String getSubScheduleData(String sname) throws URISyntaxException, IOException {

        String url = "http://data.humetro.busan.kr/voc/api/open_api_process.tnn";

        //URI uri = new URI(url);

        System.out.println(serviceKey);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url.toString())
                .queryParam("serviceKey", URLEncoder.encode(serviceKey,"UTF-8"))
                .queryParam("scode", 101)
                .queryParam("act", "xml")
                .encode(StandardCharsets.UTF_8);

        System.out.println(builder.toUriString());

        return restTemplate.getForObject(builder.toUriString(), String.class);
    }
}

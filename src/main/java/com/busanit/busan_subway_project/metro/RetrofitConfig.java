package com.busanit.busan_subway_project.metro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Bean
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://data.humetro.busan.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}


package com.busanit.busan_subway_project.metro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MetroService {

    private final ApiService apiService;

    @Value("${humetro-api-key}")
    private String serviceKey;

    @Autowired
    public MetroService(Retrofit retrofit) {
        this.apiService = retrofit.create(ApiService.class);
    }

    public List<Metro> getMetro(String act, String scode) {
        try {
            String encodedApiKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
            String decodedApiKey = URLDecoder.decode(encodedApiKey, StandardCharsets.UTF_8);

            Call<MetroResponse> metroApi = apiService.getMetroApi(decodedApiKey, act, scode);
            Response<MetroResponse> response = metroApi.execute();
            if (response.isSuccessful()) {
                return response.body().response.body.itemList;
            } else {
                throw new RuntimeException("API call failed with status code: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException("Network error", e);
        }
    }
}

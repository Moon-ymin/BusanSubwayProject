package com.busanit.busan_subway_project.service;

import com.busanit.busan_subway_project.metro.MetroResponse;
import com.busanit.busan_subway_project.model.Metro;
import com.busanit.busan_subway_project.metro.ApiService;
import com.busanit.busan_subway_project.model.Station;
import com.busanit.busan_subway_project.repo.MetroRepo;
import com.busanit.busan_subway_project.repo.StationRepo;
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
    @Autowired
    private final MetroRepo metroRepo;
    private final StationRepo stationRepo;

    @Value("${humetro-api-key}")
    private String serviceKey;

    @Autowired
    public MetroService(Retrofit retrofit, MetroRepo metroRepo, StationRepo stationRepo) {
        this.apiService = retrofit.create(ApiService.class);
        this.metroRepo = metroRepo;
        this.stationRepo = stationRepo;
    }

    public void fetchAndSaveMetroDataOnce() {
        List<Station> stations = stationRepo.findAll();
        for (Station station : stations) {
            getAndSaveMetroData("json", station.getScode());
        }
    }

    private void getAndSaveMetroData(String act, int scode) {
        try {
            String encodedApiKey = URLEncoder.encode(serviceKey, StandardCharsets.UTF_8);
            String decodedApiKey = URLDecoder.decode(encodedApiKey, StandardCharsets.UTF_8);

            Call<MetroResponse> metroApi = apiService.getMetroApi(decodedApiKey, act, scode);
            Response<MetroResponse> response = metroApi.execute();

            if (response.isSuccessful()) {
                MetroResponse metroResponse = response.body(); // Retrofit에서 JSON 응답을 MetroResponse 객체로 변환
                if (metroResponse != null && metroResponse.getResponse() != null
                        && metroResponse.getResponse().getBody() != null) {
                    List<Metro> metros = extractMetrosFromResponse(metroResponse); // Metro 데이터 리스트 추출
                    metroRepo.saveAll(metros); // 데이터베이스에 저장
                } else {
                    throw new RuntimeException("Empty or malformed response body");
                }
            } else {
                throw new RuntimeException("API call failed with status code: " + response.code());
            }

        } catch (IOException e) {
            throw new RuntimeException("Network error", e);
        }
    }

    private List<Metro> extractMetrosFromResponse(MetroResponse metroResponse) {
        List<Metro> metros = metroResponse.getResponse().getBody().getItemList();
        // 여기서 필요에 따라 Metro 객체의 데이터 형식에 맞게 변환 또는 가공할 수 있음
        return metros;
    }

    public List<Metro> getAllMetros() {
        return metroRepo.findAll();
    }
}
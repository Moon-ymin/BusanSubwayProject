package com.busanit.busan_subway_project.metro;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("voc/api/open_api_distance.tnn")
    Call<MetroResponse> getMetroApi(
            @Query("serviceKey") String serviceKey,
            @Query("act") String act,
            @Query("scode") Long scode
    );
}

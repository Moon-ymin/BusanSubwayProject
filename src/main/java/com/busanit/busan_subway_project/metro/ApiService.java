package com.busanit.busan_subway_project.metro;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/voc/api/open_api_process.tnn")
    Call<MetroResponse> getMetroApi(
            @Query("serviceKey") String serviceKey,
            @Query("act") String act,
            @Query("scode") String scode
    );
}

package com.busanit.busan_subway_project.metro;

import com.google.gson.annotations.SerializedName;
import org.apache.catalina.connector.Response;

import java.util.List;

public class MetroResponse {
    public Response response;

    public static class Response {
        public Body body;
    }

    public static class Body {
        @SerializedName("item")
        public List<Metro> itemList;
    }
}

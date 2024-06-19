package com.busanit.busan_subway_project.metro;

import com.busanit.busan_subway_project.model.Metro;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class MetroResponse {

    @SerializedName("response")
    private Response response;

    @Data
    public static class Response {
        @SerializedName("header")
        private Header header;

        @SerializedName("body")
        private Body body;

    }
    @Data
    public static class Header {
        @SerializedName("resultCode")
        private String resultCode;

        @SerializedName("resultMsg")
        private String resultMsg;
    }

    @Data
    public static class Body {
        @SerializedName("item")
        private List<Metro> itemList;

        @SerializedName("numOfRows")
        private int numOfRows;

        @SerializedName("pageNo")
        private int pageNo;

        @SerializedName("totalCount")
        private int totalCount;
    }
}


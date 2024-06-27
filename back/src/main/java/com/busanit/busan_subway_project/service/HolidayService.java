package com.busanit.busan_subway_project.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class HolidayService {
    @Value("${open-encoding-key}")
    private String serviceKey;
    private final OkHttpClient client = new OkHttpClient();
    public boolean isHoliday(LocalDate date) {
        String apiUrl = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo"
                + "?serviceKey=" + serviceKey
                + "&solYear=" + date.getYear()
                + "&solMonth=" + String.format("%02d", date.getMonthValue());

        Request request = new Request.Builder().url(apiUrl).build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return parseHolidayResponse(responseBody, date);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean parseHolidayResponse(String responseBody, LocalDate date) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8)));

            document.getDocumentElement().normalize();

            NodeList items = document.getElementsByTagName("item");

            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;
                    String locdate = element.getElementsByTagName("locdate").item(0).getTextContent();
                    String isHoliday = element.getElementsByTagName("isHoliday").item(0).getTextContent();
                    LocalDate holidayDate = LocalDate.parse(locdate, DateTimeFormatter.BASIC_ISO_DATE);

                    if (holidayDate.equals(date) && "Y".equalsIgnoreCase(isHoliday)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

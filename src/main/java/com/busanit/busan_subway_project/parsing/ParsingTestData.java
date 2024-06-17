package com.busanit.busan_subway_project.parsing;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingTestData {

    public static void main(String[] args) {
        String inputFile = "C:\\Users\\admin\\Desktop\\CSV_Schedule\\Test_Coalesce.csv";
        String outputFile = "C:\\Users\\admin\\Desktop\\CSV_Schedule\\Test_Coalesce_Output.csv";

        try {
            List<String[]> parsedData = parseCsv(inputFile);
            List<String[]> transformedData = transformData(parsedData);
            writeCsv(outputFile, transformedData);
            System.out.println("CSV 파일 파싱 및 변환 완료");
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> parseCsv(String inputFile) throws IOException, CsvException {
        List<String[]> allRows = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(inputFile))) {
            allRows = reader.readAll();
        }
        return allRows;
    }

    public static List<String[]> transformData(List<String[]> data) {
        List<String[]> transformedData = new ArrayList<>();

        transformedData.add(new String[]{"schedule_id", "continuity", "scode", "arrival_time", "direction", "day"});

        int id = 1;
        for (String[] row : data) {
            String sname = row[0];
            String schedule = row[1];
            String direction = row[2];
            String day = row[3];

            String[] stations = sname.split("\\+");
            String[] times = schedule.split("\\+");

            for (int i = 0; i < stations.length; i++) {
                String[] stationInfo = stations[i].split("-");
                String stationName = stationInfo.length == 2 ? stationInfo[1] : "";

                String[] timeInfo = times[i].split("-");
                String time = timeInfo.length == 2 ? timeInfo[1] : "";

                transformedData.add(new String[]{
                        String.valueOf(id), stationName, time, direction, day
                });
            }
            id++;
        }

        return transformedData;
    }

    public static void writeCsv(String outputFile, List<String[]> data) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {
            writer.writeAll(data);
        }
    }
}

package com.busanit.busan_subway_project.parsing;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParsingData_Test {

    public static void main(String[] args) {
        String inputFileA = "C:\\Users\\admin\\Desktop\\BSP\\CSV_Schedule\\Test_Coalesce.csv";
        String inputFileB = "C:\\Users\\admin\\Desktop\\BSP\\Station.csv";
        String outputFile = "C:\\Users\\admin\\Desktop\\Test_Output.csv";

        try {
            Map<String, String> dataB = readCsvB(inputFileB);
            transformAndWriteCsv(inputFileA, outputFile, dataB, Charset.forName("CP949"));


            System.out.println("CSV 파일 파싱 및 변환 완료");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, String> readCsvB(String inputFileB) {

        Map<String, String> dataB = new HashMap<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(inputFileB), StandardCharsets.UTF_8))) {

            List<String[]> allRows = reader.readAll();

            for (String[] row : allRows) {
                String scode = row[0];
                String sname = row[1];
                dataB.put(sname, scode);
            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return dataB;
    }

    private static void transformAndWriteCsv(String inputFileA, String outputFile, Map<String, String> dataB, Charset charset) {

        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(inputFileA), charset));
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(outputFile), charset))) {

//          데이터 파일 읽기
            List<String[]> allRows = reader.readAll();

            List<String[]> result = new ArrayList<>();

            result.add(new String[]{"schedule_id", "continuity", "scode", "arrival_time", "direction", "day"});

            int schedule_id = 1;
            int continuity = 0;
            for (String[] row : allRows) {
                String names = row[0];
                String times = row[1];
                String direction = row[2];
                String day = row[3];

                String[] snames = names.split("\\+");
                String[] arrival_times = times.split("\\+");

                for (int i = 1; i < snames.length; i++) {
                    String[] stationInfo = snames[i].split("-");
                    String sname = stationInfo.length == 2 ? stationInfo[1] : "";

                    String[] timeInfo = arrival_times[i].split("-");
                    String arrival_time = timeInfo.length == 2 ? timeInfo[1] : "";

                    String scode = "";
                    if (sname.equals("부산역")) {
                        scode = dataB.get(sname);
                    } else {
                        scode = dataB.get(sname.concat("역"));
                    }

                    result.add(new String[]{
                            String.valueOf(schedule_id), String.valueOf(continuity), scode, arrival_time, direction, day
                    });

                    schedule_id++;
                }
                continuity++;
            }

            writer.writeAll(result);

        } catch (CsvException | IOException e) {
            e.printStackTrace();
        }
    }
}

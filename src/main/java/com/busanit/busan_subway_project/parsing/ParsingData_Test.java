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
        String inputFileA = "C:\\Users\\admin\\Desktop\\BSP\\CSV_Schedule\\Test\\All_Test.csv";
        String inputFileB = "C:\\Users\\admin\\Desktop\\BSP\\CSV_Schedule\\Test\\Station.csv";
        String inputFileC = "C:\\Users\\admin\\Desktop\\BSP\\CSV_Schedule\\Test\\BnG_Test.csv";
        String outputFile = "C:\\Users\\admin\\Desktop\\BSP\\CSV_Schedule\\Test\\Output.csv";

        try {
            Map<String, String> dataB = readCsvB(inputFileB);
            List<String[]> dataC = readCsvC(inputFileC);

            List<String[]> parsedData = transformAndWriteCsv(inputFileA, dataB, Charset.forName("CP949"));

            List<String[]> combinedData = combineData(parsedData, dataC, Charset.forName("CP949"));

            writeCsv(outputFile, combinedData, Charset.forName("CP949"));

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

    private static List<String[]> readCsvC(String inputFileC) {

        List<String[]> dataC = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(inputFileC), StandardCharsets.UTF_8))) {

            dataC = reader.readAll();

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return dataC;
    }

    private static List<String[]> transformAndWriteCsv(String inputFileA, Map<String, String> dataB, Charset charset) {

        List<String[]> result = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(inputFileA), charset))) {

//          데이터 파일 읽기
            List<String[]> allRows = reader.readAll();

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

        } catch (CsvException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static List<String[]> combineData(List<String[]> dataA, List<String[]> dataC, Charset charset) {

        List<String[]> combinedData = new ArrayList<>();

        combinedData.add(new String[]{"schedule_id", "continuity", "scode", "arrival_time", "direction", "day"});

        // 기존의 dataA 데이터를 combinedData에 추가
        combinedData.addAll(dataA.subList(1, dataA.size()));  // 헤더를 제외한 데이터 추가

        int lastSchedule_id = 0;
        int lastContinuity = 0;

        for (String[] data : dataA.subList(1, dataA.size())) {  // 헤더를 제외한 데이터 처리
            int schedule_id = Integer.parseInt(data[0]);
            int continuity = Integer.parseInt(data[1]);

            if (continuity > lastContinuity) {
                lastContinuity = continuity;
            }

            if (schedule_id > lastSchedule_id) {
                lastSchedule_id = schedule_id;
            }
        }

        lastSchedule_id = lastSchedule_id + 1;
        int nextContinuity = lastContinuity + 1;
        int previousContinuity = Integer.parseInt(dataC.get(1)[0]);

        for (String[] rowC : dataC.subList(1, dataC.size())) {  // 헤더를 제외한 데이터 처리
            int currentContinuity = Integer.parseInt(rowC[0]);

            if (currentContinuity != previousContinuity) {
                nextContinuity++;
                previousContinuity = currentContinuity;
            }

            combinedData.add(new String[]{
                    String.valueOf(lastSchedule_id), String.valueOf(nextContinuity), rowC[1], rowC[2], rowC[3], rowC[4]
            });

            lastSchedule_id++;
        }

        return combinedData;
    }

    private static void writeCsv(String outputFile, List<String[]> data, Charset charset) {

        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(outputFile), charset))) {
            writer.writeAll(data);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

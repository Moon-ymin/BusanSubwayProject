package com.busanit.busan_subway_project.parsing;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ParsingWeekdaysUpScheduleData {

    public static void main(String[] args) {

        String inputData = "C:\\Users\\admin\\Desktop\\CSV_Schedule\\No_Coalesce\\Weekdays_Up_Input.csv";
        String outputData = "C:\\Users\\admin\\Desktop\\CSV_Schedule\\Weekdays_Up_Output.csv";

        transformCSV(inputData, outputData);
    }

    public static void transformCSV(String inputData, String outputData) {

        try (CSVReader reader = new CSVReader(new FileReader(inputData));
             CSVWriter writer = new CSVWriter(new FileWriter(outputData))) {

            List<String[]> allRows = reader.readAll();

            if (allRows.isEmpty()) throw new IOException("Retry");

            Set<String> stationSet = new LinkedHashSet<>();
            for (String[] row : allRows) {
                String[] stationNames = parseStationNames(row[0]);
                stationSet.addAll(Arrays.asList(stationNames));
            }

            String[] stationNames = stationSet.toArray(new String[0]);

            writer.writeNext(stationNames);

            for (String[] row : allRows) {
                String[] times = parseTimes(row[0], row[1], stationNames);
                writer.writeNext(times);
            }

        } catch (FileNotFoundException | CsvException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String[] parseStationNames(String stationPart) {

        String[] parts = stationPart.split("\\+");

        List<String> stationNames = new ArrayList<>();
        for (String part : parts) {
            String[] stationInfo = part.split("-");
            if (stationInfo.length == 2) stationNames.add(stationInfo[1]);
        }

        return stationNames.toArray(new String[0]);
    }

    private static String[] parseTimes(String stationPart, String timePart, String[] stationNames) {

        Map<String, String> timeMap = new HashMap<>();
        String[] stationParts = stationPart.split("\\+");
        String[] timeParts = timePart.split("\\+");

        for (String part : stationParts) {
            String[] stationInfo = part.split("-");
            if (stationInfo.length == 2) timeMap.put(stationInfo[1], null);
        }

        for (String part : timeParts) {
            String[] timeInfo = part.split("-");
            if (timeInfo.length == 2) {
                try {
                    int index = Integer.parseInt(timeInfo[0]) - 1;
                    if (index >= 0 && index < stationParts.length) {
                        String station = stationParts[index].split("-")[1];
                        timeMap.put(station, timeInfo[1]);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        }

        List<String> times = new ArrayList<>();
        for (String station : stationNames) {
            times.add(timeMap.getOrDefault(station, ""));
        }

        return times.toArray(new String[0]);
    }
}

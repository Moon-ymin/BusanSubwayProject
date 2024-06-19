package com.busanit.busan_subway_project;

import java.util.*;

public class Subway {
    static class Station {  // 지하철 역 나타내는 클래스
        int code;   // 역 코드 넣을거임
        List<Edge> edges = new ArrayList<>();

        Station(int code) {
            this.code = code;
        }
    }
    static class Edge { // 연결된 역의 코드, 호선 코드, 이동 시간
        int code;
        int line;
        int travelTime;

        Edge(int code, int line, int travelTime) {
            this.code = code;
            this.line = line;
            this.travelTime = travelTime;
        }
    }
    static class Route {    // 현재 경로 정보를 저장 : 현재 역, 현재 호선, 환승횟수, 총 소요시간, 경로 리스트
        int code;   // 현재 역 코드 넣을 거임
        int line;   // 현재 호선 코드 넣을 거임
        int transfers;
        int totalTime;
        List<String> path; // 역 코드, 호선 코드 담은 경로

        Route(int code, int line, int transfers, int totalTime, List<String> path) {
            this.code = code;
            this.line = line;
            this.transfers = transfers;
            this.totalTime = totalTime;
            this.path = new ArrayList<>(path);
            this.path.add(code + "|" + line);
        }
    }
    static class Result {   // 최종 결과 저장 : (Route)경로 리스트, 환승 횟수, 총 소요 시간
        List<String> path;
        int transfers;
        int totalTime;

        public Result(List<String> path, int transfers, int totalTime) {
            this.path = path;
            this.transfers = transfers;
            this.totalTime = totalTime;
        }
    }

    public static Result minTransferRoute(Map<Integer, Station> subwayMap, int start, int end) {
        Queue<Route> queue = new LinkedList<>();
        Map<Integer, Integer> visited = new HashMap<>();
        queue.add(new Route(start, -1, 0, 0, new ArrayList<>()));
        visited.put(start, 0);

        while(!queue.isEmpty()) {
            Route current = queue.poll();
            int currentCode = current.code;
            int currentLine = current.line;
            int currentTransfers = current.transfers;
            int currentTime = current.totalTime;

            if (currentCode == end) {
                return new Result(current.path, currentTransfers, currentTime);
            }

            for(Edge edge : subwayMap.get(currentCode).edges) {
                int neighborCode = edge.code;
                int neighborLine = edge.line;
                int travelTime = edge.travelTime;
                int newTransfers = currentTransfers + (currentLine == -1 || currentLine != neighborLine ? 1 : 0);
                int newTime = currentTime + travelTime;

                if (!visited.containsKey(neighborCode) || newTransfers < visited.get(neighborCode)) {
                    visited.put(neighborCode, newTransfers);
                    queue.add(new Route(neighborCode, neighborLine, newTransfers, newTime, current.path));
                }
            }
        }
        return null;    // 경로 없을 경우
    }
}

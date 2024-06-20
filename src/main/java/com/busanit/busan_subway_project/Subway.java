package com.busanit.busan_subway_project;

import java.util.*;

public class Subway {
    public static class Stage {  // 지하철 역 나타내는 클래스
        int code;   // 역 코드 넣을거임
        public List<Edge> edges = new ArrayList<>();

        public Stage(int code) {
            this.code = code;
        }
    }
    public static class Edge { // 연결된 역의 코드, 호선 코드, 이동 시간
        int code;
        int line;
        int travelTime;

        public Edge(int code, int line, int travelTime) {
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
        // 생성자 : 최소환승 경로 구할 때 사용
        Route(int code, int line, int transfers, int totalTime, List<String> path) {
            this.code = code;
            this.line = line;
            this.transfers = transfers;
            this.totalTime = totalTime;
            this.path = new ArrayList<>(path);
            this.path.add(code + "|" + line);
        }
        // 생성자 오버로드 : 최단시간 경로 구할 때 사용
        Route(int code, int totalTime, int transfers, List<String> path) {
            this.code = code;
            this.totalTime = totalTime;
            this.transfers = transfers;

            // 경로 생성 시 중복 체크를 하여 같은 경로가 두 번 추가되지 않도록 함
            this.path = new ArrayList<>(path);
            if (!path.isEmpty()) {
                String lastEntry = path.get(path.size() - 1);
                String[] lastParts = lastEntry.split("\\|");
                if (lastParts.length == 2 && Integer.parseInt(lastParts[0]) != code) {
                    this.path.add(code + "|" + (path.isEmpty() ? -1 : path.get(path.size() - 1).split("\\|")[1]));
                }
            } else {
                this.path.add(code + "|-1"); // 첫 번째 경로 추가
            }
        }
    }
    public static class Result {   // 최종 결과 저장 : (Route)경로 리스트, 환승 횟수, 총 소요 시간
        List<String> path;
        int transfers;
        int totalTime;

        public Result(List<String> path, int transfers, int totalTime) {
            this.path = path;
            this.transfers = transfers;
            this.totalTime = totalTime;
        }
    }

    // 최소환승 minTransferRoute 메서드, BFS 알고리즘 적용
    public static Result minTransferRoute(Map<Integer, Stage> subwayMap, int start, int end) {
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

    // 최단시간 minTimeRoute 메서드, 다익스트라 알고리즘 적용
    public static Result minTimeRoute(Map<Integer, Stage> subwayMap, int start, int end) {
        PriorityQueue<Route> minHeap = new PriorityQueue<>(Comparator.comparingInt(r -> r.totalTime));
        Map<Integer, Map<Integer, Route>> shortestPaths = new HashMap<>();

        Route initialRoute = new Route(start, 0, 0, new ArrayList<>(List.of(start + "|-1")));
        minHeap.offer(initialRoute);

        while (!minHeap.isEmpty()) {
            Route current = minHeap.poll();
            int currentCode = current.code;
            int currentTime = current.totalTime;
            int currentTransfers = current.transfers;
            List<String> currentPath = current.path;

            if (currentCode == end) {
                return new Result(currentPath, currentTime, currentTransfers);
            }

            if (!shortestPaths.containsKey(currentCode)) {
                shortestPaths.put(currentCode, new HashMap<>());
            }
            Map<Integer, Route> currentShortest = shortestPaths.get(currentCode);

            for (Edge edge : subwayMap.get(currentCode).edges) {
                int neighborCode = edge.code;
                int travelTime = edge.travelTime;
                int neighborLine = edge.line;
                int newTime = currentTime + travelTime;
                int newTransfers = currentTransfers + (currentTransfers == 0 || currentPath.get(currentPath.size() - 1).endsWith("|" + neighborLine) ? 0 : 1);
                List<String> newPath = new ArrayList<>(currentPath);
                newPath.add(neighborCode + "|" + neighborLine);

                if (!currentShortest.containsKey(neighborCode) || newTime < currentShortest.get(neighborCode).totalTime) {
                    currentShortest.put(neighborCode, new Route(neighborCode, newTime, newTransfers, newPath));
                    minHeap.offer(new Route(neighborCode, newTime, newTransfers, newPath));
                }
            }
        }

        return null; // No path found
    }
}

package com.busanit.busan_subway_project;

import lombok.AllArgsConstructor;

import java.util.*;

public class Subway {
    public static class Stage {  // 지하철 역 나타내는 클래스
        int code;   // 역 코드 넣을거임
        String name;    // 역 이름
        int line_cd;    // 호선 코드
        public List<Edge> edges = new ArrayList<>();
        public Stage(int code, String name, int line_cd) {
            this.code = code;
            this.name = name;
            this.line_cd = line_cd;
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
    static class Route {    // 현재 경로 정보를 저장 : 현재 역 코드, 현재 역 이름, 현재 호선, 환승횟수, 총 소요시간, 경로 리스트
        int code;   // 현재 역 코드
        String name;    // 현재 역 이름
        int line;   // 현재 호선 코드
        int transfers;
        int totalTime;
        List<String> path; // 역 코드, 역 이름, 호선 코드 담은 경로

        // 생성자 : 최소환승 경로 구할 때 사용 - 환승여부가 중요하기 때문에 line 필요
        Route(int code, String name, int line, int transfers, int totalTime, List<String> path) {
            this.code = code;
            this.name = name;
            this.line = line;
            this.transfers = transfers;
            this.totalTime = totalTime;
            this.path = new ArrayList<>(path);
            this.path.add(code + "|" + name + "|" + line);
        }


        // 생성자 오버로드 : 최단시간 경로 구할 때 사용
        Route(int code, String name, int totalTime, int transfers, List<String> path) {
            this.code = code;
            this.name = name;
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

    // 최소환승 minTransferRoute 메서드, 다익스트라 알고리즘 적용
    public static Result minTransferRoute(Map<Integer, Stage> subwayMap, int start, int end) {
        // 1. 우선순위 큐 : 환승 횟수를 기준을 정렬하는 우선순위 큐 생성
        PriorityQueue<Route> pq = new PriorityQueue<>(Comparator.comparingInt(r -> r.transfers));
        Map<Integer, Route> shortestPaths = new HashMap<>(); // 같은 경로 중복 저장되는거 막기 위해

        // 2. 시작 노드의 Route 객체를 생성해 큐에 먼저 추가, 시작의 line 은 -1로 표시
        Stage startStage = subwayMap.get(start);
        Route initialRoute = new Route(startStage.code, startStage.name, -1, 0, 0, new ArrayList<>());
        pq.offer(initialRoute);
        shortestPaths.put(start, initialRoute);

        // 3. 탐색 루프 : 큐가 비어있지 않은 동안 반복
        while (!pq.isEmpty()) {
            Route current = pq.poll(); // 큐에서 가장 적은 환승 횟수를 가진 Route 객체를 꺼냄
            int currentCode = current.code;
            int currentLine = current.line;
            int currentTransfers = current.transfers;
            int currentTime = current.totalTime;
            List<String> currentPath = current.path;

            if (currentCode == end) {   // 5. 현재 노드가 목적지인지 확인해서, 목적지이면 반환
                return new Result(currentPath, currentTransfers, currentTime);
            }

            // 6. 인접 노드 탐색 : 현재 노드의 인접 노드를 탐색하며 새로운 경로 생성
            for (Edge edge : subwayMap.get(currentCode).edges) {
                int neighborCode = edge.code;
                Stage neighborStage = subwayMap.get(neighborCode);
                int neighborLine = edge.line;
                int travelTime = edge.travelTime;
                int newTime = currentTime + travelTime;
                int newTransfers = currentTransfers + (currentLine != neighborLine ? 1 : 0); // 환승여부 확인
                    // 새로운 경로이거나(중복 아니거나) | 환승횟수가 작을 때
                if (!shortestPaths.containsKey(neighborCode) || newTransfers < shortestPaths.get(neighborCode).transfers) {
                    Route newRoute = new Route(neighborCode, neighborStage.name,
                            neighborLine, newTransfers, newTime, currentPath);
                    shortestPaths.put(neighborCode, newRoute);
                    pq.offer(newRoute);
                }
            }
        }
        return null; // 경로 없을 경우
    }

    // 최단시간 minTimeRoute 메서드, 다익스트라 알고리즘 적용
    public static Result minTimeRoute(Map<Integer, Stage> subwayMap, int start, int end) {
        // 1. 초기 변수 설정 : 결과를 저장할 변수들
        List<String> path = new ArrayList<>();
        int transfers = -1;
        int totalTime = -1;

        // 2. 시작 역과 종료 역이 유효한지 확인
        if (!subwayMap.containsKey(start) || !subwayMap.containsKey(end)) {
            return new Result(path, transfers, totalTime);
        }

        // 3. 우선순위 큐 생성 : 이동 시간(totalTime) 기준으로 정렬하는 우선순위 큐 생성
        PriorityQueue<Route> pq = new PriorityQueue<>(Comparator.comparingInt(r -> r.totalTime));

        // 4. 시작 역을 큐에 추가
        Stage startStage = subwayMap.get(start);
        pq.offer(new Route(startStage.code, startStage.name, 0, 0, new ArrayList<>()));

        while (!pq.isEmpty()) { // 5. 탐색 루프 : 큐가 비어있지 않은 동안 반복
            // 6. 큐에서 가장 적은 이동시간 가진 Route 객체 꺼냄
            Route currentRoute = pq.poll();
            Stage currentStage = subwayMap.get(currentRoute.code);

            // 7. 목적지에 도달한 경우 -> 결과 반환
            if (currentRoute.code == end) {
                return new Result(currentRoute.path, currentRoute.transfers, currentRoute.totalTime);
            }

            // 8. 인접 역을 탐색하여 최소시간 경로를 찾음
            for (Edge edge : currentStage.edges) {
                Stage nextStage = subwayMap.get(edge.code);
                int travelTime = edge.travelTime;

                // 새 경로 생성 및 우선순위 큐에 추가
                List<String> newPath = new ArrayList<>(currentRoute.path);
                newPath.add(nextStage.code + "|" + nextStage.name + "|" + nextStage.line_cd);
                Route newRoute = new Route(nextStage.code, nextStage.name, currentRoute.totalTime + travelTime,
                        currentRoute.transfers, newPath);
                pq.offer(newRoute);
            }
        }
        // 도착 역에 도달하지 못한 경우
        return new Result(path, transfers, totalTime);
    }
}

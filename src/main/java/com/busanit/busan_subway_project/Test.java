package com.busanit.busan_subway_project;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<Integer, Subway.Station> subwayMap = new HashMap<>();

        Subway.Station a = new Subway.Station(1);
        Subway.Station b = new Subway.Station(2);
        Subway.Station c = new Subway.Station(3);
        Subway.Station d = new Subway.Station(4);

        a.edges.add(new Subway.Edge(2, 1, 5));
        a.edges.add(new Subway.Edge(3, 2, 10));
        b.edges.add(new Subway.Edge(1, 1, 5));
        b.edges.add(new Subway.Edge(4, 1, 7));
        c.edges.add(new Subway.Edge(1, 2, 10));
        c.edges.add(new Subway.Edge(4, 2, 3));
        d.edges.add(new Subway.Edge(2, 1, 7));
        d.edges.add(new Subway.Edge(3, 2, 3));

        subwayMap.put(1, a);
        subwayMap.put(2, b);
        subwayMap.put(3, c);
        subwayMap.put(4, d);

        Subway.Result result = Subway.minTransferRoute(subwayMap, 1, 4);
        if (result != null) {
            System.out.println("Minimum transfers: " + result.transfers);
            System.out.println("Total time: " + result.totalTime);
            System.out.println("Path: " + result.path);
        } else {
            System.out.println("No path found");
        }
    }
}

package com.fhm.oop_ems;

import p1.Day;
import p1.TimeRange;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<Day, ArrayList<TimeRange>> map = new LinkedHashMap<>();
        map.put(Day.SATURDAY, new ArrayList<>(List.of(new TimeRange(6.25, 17))));
        map.put(Day.MONDAY, new ArrayList<>(List.of(new TimeRange(9, 21.57))));
        map.put(Day.TUESDAY, new ArrayList<>(List.of(new TimeRange("9:11", "19:12"))));
        map.put(Day.WEDNESDAY, new ArrayList<>(List.of(new TimeRange("9:30", "17:15"))));
        map.put(Day.THURSDAY, new ArrayList<>(List.of(new TimeRange("12:30", "18:30"), new TimeRange("22:30", "00:30"))));
        System.out.println(map.toString().replace("=", " --> "));
        if(map.get(Day.THURSDAY).getFirst().Contains(new TimeRange("15:15" , "17:30"))) {
            ArrayList<TimeRange> updated = map.get(Day.THURSDAY).getFirst().Remove(new TimeRange("15:15", "17:30"));
            map.get(Day.THURSDAY).removeFirst();
            map.get(Day.THURSDAY).addFirst(updated.getLast());
            map.get(Day.THURSDAY).addFirst(updated.getFirst());
        }
        System.out.println(map.toString().replace("=", " --> "));
    }
}
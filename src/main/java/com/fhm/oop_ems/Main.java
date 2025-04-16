package com.fhm.oop_ems;

import p1.Day;
import p1.TimeRange;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<Day, TimeRange> map = new LinkedHashMap<>();
        map.put(Day.SATURDAY, new TimeRange(6.25, 17));
        map.put(Day.MONDAY, new TimeRange(9, 21.57));
        map.put(Day.TUESDAY, new TimeRange("9:11", "19:12"));
        map.put(Day.WEDNESDAY, new TimeRange("9:30", "17:15"));
        map.put(Day.THURSDAY, new TimeRange("1:30", "12:30"));
        System.out.println(map.toString().replace("=", " --> ").replace(", ", "\n").replaceAll("[{}]", ""));
    }
}

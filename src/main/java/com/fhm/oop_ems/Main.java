package com.fhm.oop_ems;

import p1.Category;
import p1.Database;
import p1.Day;
import p1.TimeRange;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Database.Connect();
        Day.Init();
        Map<Day, ArrayList<TimeRange>> map = new LinkedHashMap<>();
        map.put(Day.SATURDAY, new ArrayList<>(List.of(new TimeRange(6.25, 17))));
        map.put(Day.MONDAY, new ArrayList<>(List.of(new TimeRange(9, 21.57))));
        map.put(Day.TUESDAY, new ArrayList<>(List.of(new TimeRange("9:11", "19:12"))));
        map.put(Day.WEDNESDAY, new ArrayList<>(List.of(new TimeRange("9:30", "17:15"))));
        map.put(Day.THURSDAY, new ArrayList<>(List.of(new TimeRange("13:30", "18:30"), new TimeRange("22:30", "23:30"))));
        System.out.println(map.toString().replace("=", " --> "));
        TimeRange tr = new TimeRange("14:10", "15:30");
        if(map.get(Day.THURSDAY).getFirst().Contains(tr)) {
            ArrayList<TimeRange> updated = map.get(Day.THURSDAY).getFirst().Remove(tr);
            map.get(Day.THURSDAY).removeFirst();
            for (TimeRange range : updated.reversed()) {
                map.get(Day.THURSDAY).addFirst(range);
            }
        }
        System.out.println(map.toString().replace("=", " --> "));

        String encrypted = TimeRange.EncryptTimeRange(map);
        System.out.println(encrypted);

        Map<Day, ArrayList<TimeRange>> decryptedMap = TimeRange.DecryptTimeRange(encrypted);
        System.out.println(map.toString().replace("=", " --> "));
        
        Database.CloseConnection();
    }
}
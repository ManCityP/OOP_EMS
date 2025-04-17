package com.fhm.oop_ems;

import p1.*;
import p3.Gender;

import java.sql.ResultSet;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Database.Connect();
        Map<Date, ArrayList<TimeRange>> map = new LinkedHashMap<>();
        //map.put(new Date("10/22/2023"), new ArrayList<>(List.of(new TimeRange(6.25, 17))));
        map.put(new Date(2011, 12, 14), new ArrayList<>(List.of(new TimeRange(9, 21.57))));
        map.put(new Date(2025, 4, 17), new ArrayList<>(List.of(new TimeRange("9:11", "19:12"))));
        System.out.println(map.toString().replace("=", " --> "));

        Date date = new Date("10/23/2023");
        System.out.println();

        Database.CloseConnection();
    }
}
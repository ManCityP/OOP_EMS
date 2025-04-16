package com.fhm.oop_ems;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String args[]) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("Friday", "9-17");
        map.put("Saturday", "9-17");
        map.put("Sunday", "9-17");
        map.put("Monday", "9-17");
        map.put("Tuesday", "9-17");
        map.put("Wednesday", "9-17");
        map.put("Thursday", "9-17");
        System.out.println(map.toString().replace("=", " --> ").replace(", ", "\n").replaceAll("[{}]", ""));

        System.out.println("/n");
        System.out.println(map.get("9-17"));

    }
}

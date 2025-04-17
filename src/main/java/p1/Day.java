package p1;

import java.util.HashMap;
import java.util.Map;

public enum Day {
    SATURDAY("Saturday"), SUNDAY("Sunday"), MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"), THURSDAY("Thursday"), FRIDAY("Friday");

    public static Map<String, Integer> days = new HashMap<>();

    public static void Init() {
        days.put("Saturday", 0);
        days.put("Sunday", 1);
        days.put("Monday", 2);
        days.put("Tuesday", 3);
        days.put("Wednesday", 4);
        days.put("Thursday", 5);
        days.put("Friday", 6);
    }

    public static Day Translate(int n) {
        return switch (n) {
            case 0 -> SATURDAY;
            case 1 -> SUNDAY;
            case 2 -> MONDAY;
            case 3 -> TUESDAY;
            case 4 -> WEDNESDAY;
            case 5 -> THURSDAY;
            case 6 -> FRIDAY;
            default -> null;
        };
    }

    private final String day;

    Day(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return this.day;
    }
}
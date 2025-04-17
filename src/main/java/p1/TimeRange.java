package p1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class TimeRange {
    double start;
    double end;

    public TimeRange(double start, double end) throws Exception {
        if(start >= end || start < 0 || end >= 24)
            throw new Exception("Invalid time range!");
        this.start = start;
        this.end = end;
    }

    public TimeRange(String start, String end) throws Exception {
        this.start = Convert(start);
        this.end = Convert(end);
    }

    public double GetTotalTime() {
        return this.end - this.start;
    }

    public static String Convert(double time) throws Exception {
        if(time < 0 || time >= 24)
            throw new Exception("Invalid time!");
        return String.format("%02d:%02d", Math.round(Math.floor(time)), Math.round((time-Math.floor(time))*60));
    }

    public boolean Contains(TimeRange timeRange) {
        return (timeRange.start >= this.start && timeRange.end <= this.end);
    }

    public ArrayList<TimeRange> Remove(TimeRange timeRange) throws Exception {
        ArrayList<TimeRange> newRange = new ArrayList<>();
        if (timeRange.start > this.start)
            newRange.add(new TimeRange(this.start, timeRange.start));
        if (timeRange.end < this.end)
            newRange.add(new TimeRange(timeRange.end, this.end));
        return newRange;
    }

    public static double Convert(String time) throws Exception {
        String[] t = time.split(":");
        double d1 = Double.parseDouble(t[0]);
        if (d1 < 0 || d1 >= 24)
            throw new Exception("Invalid time!");
        double d2 = Double.parseDouble(t[1]);
        if (d2 < 0 || d2 >= 60)
            throw new Exception("Invalid time!");
        if (d1 > Math.floor(d1) || d2 > Math.floor(d2))
            throw new Exception("Invalid time");
        return d1 + (d2/60.0);
    }

    public boolean InRange(double time) {
        return (time >= this.start && time <= this.end);
    }

    public static String EncryptTimeRange(Map<Day, ArrayList<TimeRange>> map) {
        // {Saturday --> [06:15-17:00], Monday --> [09:00-21:34], Tuesday --> [09:11-19:12], Wednesday --> [09:30-17:15], Thursday --> [13:30-18:30, 22:30-23:30]}

        StringBuilder allHours = new StringBuilder();
        for (Map.Entry<Day, ArrayList<TimeRange>> entry: map.entrySet()) {
            allHours.append(Day.days.get(entry.getKey().toString()));
            allHours.append(">[");
            for(TimeRange timeRange : entry.getValue()) {
                allHours.append(timeRange.toString());
                allHours.append(',');
            }
            allHours.deleteCharAt(allHours.length() - 1);
            allHours.append(']');
            allHours.append('/');
        }
        allHours.deleteCharAt(allHours.length() - 1);

        // "0>[06:15-17:00]/2>[09:00-21:34]/3>[09:11-19:12]/4>[09:30-17:15]/5>[13:30-18:30,22:30-23:30]"

        return allHours.toString();
    }

    public static Map<Day, ArrayList<TimeRange>> DecryptTimeRange(String allHours) throws Exception {
        Map<Day, ArrayList<TimeRange>> map = new LinkedHashMap<>();

        String[] strings = allHours.split("/");
        for (String str : strings) {
            String[] day = str.split(">");
            String timeRangeList = day[1].replace("[", "").replace("]", "");
            String[] splits = timeRangeList.split(",");
            ArrayList<TimeRange> timeRanges = new ArrayList<>();

            for(String split : splits) {
                String[] times = split.split("-");
                timeRanges.add(new TimeRange(times[0], times[1]));
            }

            map.put(Day.Translate(Integer.parseInt(day[0])), timeRanges);
        }
        return map;
    }


    @Override
    public String toString() {
        try {
            return Convert(this.start) + "-" + Convert(this.end);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
}

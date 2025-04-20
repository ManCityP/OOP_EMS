package p1;

import java.util.ArrayList;
import java.util.Date;
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
        if(timeRange == null) {
            newRange.add(this);
            return newRange;
        }
        if (timeRange.start > this.start)
            newRange.add(new TimeRange(this.start, timeRange.start));
        if (timeRange.end < this.end)
            newRange.add(new TimeRange(timeRange.end, this.end));
        return newRange;
    }

    public static TimeRange GetOverlap(TimeRange range1, TimeRange range2) throws Exception {

        double start1 = range1.start;
        double end1 = range1.end;
        double start2 = range2.start;
        double end2 = range2.end;

        double overlapStart = Math.max(start1, start2);
        double overlapEnd = Math.min(end1, end2);


        if (overlapStart < overlapEnd) {
            TimeRange newRange = new TimeRange(overlapStart, overlapEnd);
            return newRange;
        }/* else {
            throw new Exception("No Overlap");
        }*/
        return null;
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
    public static String EncryptWorkingHours(Map<String, ArrayList<TimeRange>> map) {
        // {Saturday --> [06:15-17:00], Monday --> [09:00-21:34], Tuesday --> [09:11-19:12], Wednesday --> [09:30-17:15], Thursday --> [13:30-18:30, 22:30-23:30]}

        if(map == null)
            return "";

        StringBuilder allHours = new StringBuilder();
        for (Map.Entry<String, ArrayList<TimeRange>> entry: map.entrySet()) {
            allHours.append(Day.days.get(entry.getKey()));
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

    public static Map<String, ArrayList<TimeRange>> DecryptWorkingHours(String allHours) throws Exception {
        Map<String, ArrayList<TimeRange>> map = new LinkedHashMap<>();

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

            map.put(Day.Translate(Integer.parseInt(day[0])).toString(), timeRanges);
        }
        return map;
    }

    public static String EncryptTimeRange(Map<String, ArrayList<TimeRange>> map) {
        // {19/7/2024 --> [06:15-17:00], 21/7/2024 --> [09:00-21:34], 22/7/2024 --> [09:11-19:12], 23/7/2024 --> [09:30-17:15], 28/7/2024 --> [13:30-18:30, 22:30-23:30]}

        StringBuilder allHours = new StringBuilder();
        for (Map.Entry<String, ArrayList<TimeRange>> entry: map.entrySet()) {
            //allHours.append(Day.days.get(entry.getKey().toString()));
            allHours.append(entry.getKey());
            allHours.append(">[");
            for(TimeRange timeRange : entry.getValue()) {
                allHours.append(timeRange.toString());
                allHours.append(',');
            }
            allHours.deleteCharAt(allHours.length() - 1);
            allHours.append(']');
            allHours.append('!');
        }
        if(!allHours.isEmpty())
            allHours.deleteCharAt(allHours.length() - 1);

        // "19/7/2024>[06:15-17:00]!21/7/2024>[09:00-21:34]!22/7/2024>[09:11-19:12]!23/7/2024>[09:30-17:15]!28/7/2024>[13:30-18:30,22:30-23:30]"

        return allHours.toString();
    }

    public static Map<String, ArrayList<TimeRange>> DecryptTimeRange(String allHours) throws Exception {
        Map<String, ArrayList<TimeRange>> map = new LinkedHashMap<>();

        String[] strings = allHours.split("!");
        for (String str : strings) {
            String[] day = str.split(">");
            String timeRangeList = day[1].replace("[", "").replace("]", "");
            String[] splits = timeRangeList.split(",");
            ArrayList<TimeRange> timeRanges = new ArrayList<>();

            for(String split : splits) {
                String[] times = split.split("-");
                timeRanges.add(new TimeRange(times[0], times[1]));
            }

            map.put(new MyDate(day[0]).toString(), timeRanges);
        }
        return map;
    }

    public double GetStart() {return this.start;}
    public double GetEnd() {return this.end;}

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

package p1;

import java.util.ArrayList;

public class TimeRange {
    double start;
    double end;
    String range;

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
        return (timeRange.start >= this.start && timeRange.start < this.end && timeRange.end <= this.end && timeRange.end > this.start
                && (this.end - this.start)*(timeRange.end - timeRange.start) > 0);
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

    @Override
    public String toString() {
        try {
            return Convert(this.start) + " - " + Convert(this.end);
        }
        catch (Exception e) {
            return e.getMessage();
        }
    }
}

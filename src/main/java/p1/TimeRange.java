package p1;

public class TimeRange {
    float start;
    float end;

    public TimeRange(float start, float end) {
        if (start)
        this.start = start;
        this.end = end;
    }

    public boolean InRange(float time) {
        return (time >= start && time <= end);
    }

    @Override
}

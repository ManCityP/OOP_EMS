package p1;

public enum Day {
    SATURDAY("Saturday"), SUNDAY("Sunday"), MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"), THURSDAY("Thursday"), FRIDAY("Friday");

    private final String day;

    Day(String day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return this.day;
    }
}

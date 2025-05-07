package p2;

public enum Status {
    UPCOMING("Upcoming"), ONGOING("Ongoing"), OVER("Over");
    private final String status;

    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}

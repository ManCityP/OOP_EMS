package p1;

public enum DataType {

    USER("user"), Category("category"), Room("room"), EVENT("event"), WALLET("wallet"), ID("id"),
    USERNAME("username"), EMAIL("email"), PASSWORD("password"), GENDER("gender"), BIRTH_YEAR("birth_year"), BIRTH_MONTH("birth_month"), BIRTH_DAY("birth_day"), CREATE_TIME("create_time"),
    PRICE("price"), BALANCE("balance"), USER_ID("user_id"), ROOM_ID("room_id"), EVENT_ID("event_id"), AVAILABLE_TIME("available_time"), NAME("name"),
    TIME_RANGE("time_range"), DAY("day"), TYPE("type"), ROLE("role"), INTERESTS("interests");

    private final String type;

    DataType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
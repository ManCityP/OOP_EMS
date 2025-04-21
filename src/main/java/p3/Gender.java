package p3;

public enum Gender {
    MALE("Male"), FEMALE("Female"), ENGINEER("Engineer");
    private final String gender;
    Gender(String gender) {
        this.gender = gender;
    }
    @Override
    public String toString() {
        return this.gender;
    }
}

package p3;

public enum Gender {
    MALE("Male"), FEMALE("Female"), ENGINEER("Engineer");
    private final String GENDER;
    Gender(String gender) {
        this.GENDER = gender;
    }
    @Override
    public String toString() {
        return this.GENDER;
    }
}

package com.fhm.oop_ems;

import p1.*;
import p3.Gender;

import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Day.Init();
        Database.Connect();

        Map<Day, ArrayList<TimeRange>> workingHours = new LinkedHashMap<>();
        workingHours.put(Day.SATURDAY, new ArrayList<>(List.of(new TimeRange("08:00", "11:50"), new TimeRange("12:30", "19:30"))));
        workingHours.put(Day.SUNDAY, new ArrayList<>(List.of(new TimeRange("08:00", "11:50"), new TimeRange("12:30", "14:20"))));
        workingHours.put(Day.MONDAY, new ArrayList<>(List.of(new TimeRange("08:00", "11:50"))));
        workingHours.put(Day.TUESDAY, new ArrayList<>(List.of(new TimeRange("08:00", "09:50"), new TimeRange("11:00", "11:50"), new TimeRange("12:30", "18:20"))));
        workingHours.put(Day.WEDNESDAY, new ArrayList<>(List.of(new TimeRange("13:30", "15:20"))));

        Admin admin = new Admin("Ahmed Hesham", "fris@gmail.com", "24p0029", new MyDate(14, 12, 2006), Gender.MALE, "Mol2at Ba4ary", workingHours);

        ResultSet resultSet = Database.GetData(DataType.USER.toString());
        while(resultSet.next()) {
            String username = resultSet.getString(DataType.USERNAME.toString());
            String email = resultSet.getString(DataType.EMAIL.toString());
            String password = resultSet.getString(DataType.PASSWORD.toString());
            MyDate dob = new MyDate(resultSet.getInt(DataType.BIRTH_DAY.toString()), resultSet.getInt(DataType.BIRTH_MONTH.toString()), resultSet.getInt(DataType.BIRTH_YEAR.toString()));
            Gender gender = resultSet.getString(DataType.GENDER.toString()).equals("Male")? Gender.MALE : Gender.FEMALE;
            String type = resultSet.getString(DataType.TYPE.toString());
            String role = resultSet.getString(DataType.ROLE.toString());
            Map<Day, ArrayList<TimeRange>> hours = TimeRange.DecryptWorkingHours(resultSet.getString(DataType.TIME_RANGE.toString()));

            System.out.printf("Username: %s\tEmail: %s\tPassword: %s\tBirthDate: %s\tGender: %s\tType: %s\t", username, email, password, dob, gender, type);
            if (type.equals("Admin"))
                System.out.printf("Role: %s\tWorkingHours: %s", role, hours);
            System.out.print("\n");
        }

        Database.CloseConnection();
    }
}
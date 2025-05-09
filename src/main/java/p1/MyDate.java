package p1;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class MyDate {

    private final int DAY, MONTH, YEAR;        //Almost there

    public MyDate(){
        LocalDateTime currentTime = LocalDateTime.now();
        this.DAY = currentTime.getDayOfMonth();
        this.MONTH = currentTime.getMonthValue();
        this.YEAR = currentTime.getYear();
    }

    public MyDate(int day,int month, int year) throws Exception {
        int[] daysInMonth = { 31, IsLeapYear(year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (month <= 0 || month > 12 || year < 1900 || day <= 0 || !(day <= daysInMonth[month - 1]))
            throw new Exception("Invalid date");

        this.DAY = day;
        this.MONTH = month;
        this.YEAR = year;
    }

    public MyDate(String date) throws Exception {
        String[] d = date.split("/");
        if(d[0] == null || d[1] == null || d[2] == null)
            throw new Exception("Invalid date");
        int[] daysInMonth = { 31, IsLeapYear(Integer.parseInt(d[2].trim())) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (Integer.parseInt(d[1].trim()) <= 0 || Integer.parseInt(d[1].trim()) > 12 ||
            Integer.parseInt(d[2].trim()) < 1900 || Integer.parseInt(d[0].trim()) <= 0 ||
            !(Integer.parseInt(d[0].trim()) <= daysInMonth[Integer.parseInt(d[1].trim()) - 1]))
                throw new Exception("Invalid date");

        this.DAY = Integer.parseInt(d[0].trim());
        this.MONTH = Integer.parseInt(d[1].trim());
        this.YEAR = Integer.parseInt(d[2].trim());
    }

    private static boolean IsLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year%400==0);
    }

    public int GetDay(){
        return this.DAY;
    }
    public int GetMonth(){
        return this.MONTH;
    }
    public int GetYear(){
        return this.YEAR;
    }

    public static Day GetDayOfTheWeek(MyDate date){
        LocalDate d = LocalDate.of(date.YEAR,date.MONTH,date.DAY);
        return Day.Translate((d.getDayOfWeek().getValue()+1) % 7);
    }

    public String GetMonthOfTheYear(){
        return Month.of(this.MONTH).toString();
    }

    public MyDate NextMonth() throws Exception {
        if(this.MONTH == 12)
            return new MyDate(1,1,this.GetYear()+1);
        return new MyDate(1,this.GetMonth()+1,this.GetYear());
    }

    public MyDate LastMonth() throws Exception {
        if(this.MONTH == 1)
            return new MyDate(1,12,this.GetYear()-1);
        return new MyDate(1,this.GetMonth()-1,this.GetYear());
    }

    public int MaxDay(){
        int[] daysInMonth = { 31, IsLeapYear(this.YEAR) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        return daysInMonth[this.MONTH-1];
    }

    @Override
    public String toString(){
        return this.DAY + "/" + this.MONTH + "/" + this.YEAR;
    }

    @Override
    public boolean equals(Object date){
        if(date == null)
            return false;
        return ( (date instanceof MyDate) && this.DAY == ((MyDate)date).DAY && this.MONTH == ((MyDate)date).MONTH && this.YEAR == ((MyDate)date).YEAR);
    }
}

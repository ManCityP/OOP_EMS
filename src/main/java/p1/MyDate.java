package p1;

import java.time.LocalDate;

public class MyDate {

    int day,month,year;

    public MyDate(int day,int month, int year) throws Exception {
        int[] daysInMonth = { 31, isLeapYear(year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (month <= 0 || month > 12 || year < 1900 || day <= 0 || !(day <= daysInMonth[month - 1]))
            throw new Exception("Invalid date");

        this.day = day;
        this.month = month;
        this.year = year;
    }
    public MyDate(String date) throws Exception {
        String[] d = date.split("/");
//        if (Integer.parseInt(d[0].trim()) <= 0)
//            throw new Exception("Invalid date");
//        this.day = Integer.parseInt(d[0].trim());
//        if (Integer.parseInt(d[1].trim()) <= 0)
//            throw new Exception("Invalid date");
//        this.month = Integer.parseInt(d[1].trim());
//        if (Integer.parseInt(d[2].trim()) <= 0)
//            throw new Exception("Invalid date");
//        this.year = Integer.parseInt(d[2].trim());
        if(d[0] == null || d[1] == null || d[2] == null)
            throw new Exception("Invalid date");
      //  this(Integer.parseInt(d[0].trim()), Integer.parseInt(d[1].trim()), Integer.parseInt(d[2].trim()));
        int[] daysInMonth = { 31, isLeapYear(Integer.parseInt(d[2].trim())) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (Integer.parseInt(d[1].trim()) <= 0 || Integer.parseInt(d[1].trim()) > 12 ||
            Integer.parseInt(d[2].trim()) < 1900 || Integer.parseInt(d[0].trim()) <= 0 ||
            !(Integer.parseInt(d[0].trim()) <= daysInMonth[Integer.parseInt(d[1].trim()) - 1]))
            throw new Exception("Invalid date");

        this.day = Integer.parseInt(d[0].trim());
        this.month = Integer.parseInt(d[1].trim());
        this.year = Integer.parseInt(d[2].trim());
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year%400==0);
    }

    public int GetDay(){
        return this.day;
    }
    public int GetMonth(){
        return this.month;
    }
    public int GetYear(){
        return this.year;
    }
    public static Day GetDayofTheWeek(MyDate date){
        LocalDate d = LocalDate.of(date.year,date.month,date.day);
        return Day.Translate((d.getDayOfWeek().getValue()+1) % 7);
    }

    @Override
    public String toString(){
        return this.day + "/" + this.month + "/" + this.year;
    }

    @Override
    public boolean equals(Object date){
        return ( (date instanceof MyDate) && this.day == ((MyDate)date).day && this.month == ((MyDate)date).month && this.year == ((MyDate)date).year);
    }
}

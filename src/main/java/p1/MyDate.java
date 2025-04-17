package p1;

public class MyDate {

    int day,month,year;

    public MyDate(int day,int month, int year) throws Exception {
        if (day <= 0 || month <= 0 || year <= 0)
            throw new Exception("Invalid date");
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public MyDate(String date) throws Exception {
        String[] d = date.split("/");
        if (Integer.parseInt(d[0].trim()) <= 0)
            throw new Exception("Invalid date");
        this.day = Integer.parseInt(d[0].trim());
        if (Integer.parseInt(d[1].trim()) <= 0)
            throw new Exception("Invalid date");
        this.month = Integer.parseInt(d[1].trim());
        if (Integer.parseInt(d[2].trim()) <= 0)
            throw new Exception("Invalid date");
        this.year = Integer.parseInt(d[2].trim());
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

    @Override
    public String toString(){
        return this.day + "/" + this.month + "/" + this.year;
    }

    @Override
    public boolean equals(Object date){
        return ( (date instanceof MyDate) && this.day == ((MyDate)date).day && this.month == ((MyDate)date).month && this.year == ((MyDate)date).year);
    }
}

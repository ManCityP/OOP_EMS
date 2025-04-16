package p1;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;

public class Hours {
    Map<Day, TimeRange> map = new LinkedHashMap<>();

    void DisplayRange(Day day){

        System.out.println(this.map.get(day));


    }














    Hours(){}
    Hours(Map map){
        this.map=map;
    }

}

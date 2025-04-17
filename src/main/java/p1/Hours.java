package p1;

import java.sql.Time;
import java.util.*;

public class Hours {
    Map<Date, ArrayList<TimeRange>> map = new LinkedHashMap<>();

    void DisplayRange(Day day){

        System.out.println(this.map.get(day));


    }

    void RemoveTime(Date day, TimeRange rTime) throws Exception {
        double start = rTime.start;
        double end = rTime.end;
boolean inRange=false;
        ArrayList<TimeRange> arrTime = this.map.get(day);


        int i;
        for (i = 0; i < arrTime.size(); i++) {
            inRange = arrTime.get(i).Contains(rTime);
            if (inRange) {
                break;
            }
        }
        if (!inRange){
            throw new Exception("Time to be removed is out of range");
        }


        arrTime.get(i).Remove(rTime); // done? inshallah done.


    }

void AddTime(Date day, TimeRange rTime) throws Exception {
    double start = rTime.start;
    double end = rTime.end;
    boolean inRange=false;
    ArrayList<TimeRange> arrTime = this.map.get(day);
    int i;
    for (i = 0; i < arrTime.size(); i++) {
        inRange = arrTime.get(i).Contains(rTime);
        if (inRange) {
            break;
        }
    }
    if (inRange){
        throw new Exception("Time to be added already exists");
    }
/* now we need to manage A LOT of cases.

CASES HANDLED:
1. if added time has 0 overlap with any existing time
rTime start > allArrTime ends && rTime end < allArrTime starts

CASES NOT HANDLED:

ALL THE REST

 */
    boolean isCase1;
    double case1start;
    double case1end;
    int case1s=0;
    int case1e=0;
    for (i = 0; i < arrTime.size(); i++) {
        case1start = arrTime.get(i).start;
        case1end = arrTime.get(i).end;

        if(start > case1end){
            case1s++;
        }
        if(end < case1start){
            case1e++;
        }
    }
    isCase1 = (case1e==arrTime.size())&&(case1s==arrTime.size());
// case 1 detected, now which index to add wanted TimeRange
if(isCase1){
    for (i = 0; i < arrTime.size(); i++) {
        case1end = arrTime.get(i).end;

        if(start < case1end) {
            break;
        }
    }
    arrTime.add(i, rTime); // CASE ONE COMPLETED (INSHALLAH)
    }


}













    Hours(){}
    Hours(Map map){
        this.map=map;
    }

}

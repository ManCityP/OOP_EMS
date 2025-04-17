package p1;

import java.sql.Time;
import java.util.*;

import static p1.TimeRange.GetOverlap;

public class Hours {
    Map<String, ArrayList<TimeRange>> map = new LinkedHashMap<>();

    void DisplayRange(String day){

        System.out.println(this.map.get(day));


    }

  public  void RemoveTime(String day, TimeRange rTime) throws Exception {
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



   ArrayList<TimeRange> temp = arrTime.get(i).Remove(rTime); // done? inshallah done.
this.map.get(day).addAll(i,temp);

    }

public void AddTime(String day, TimeRange rTime) throws Exception {


    for (int i = 0; i < this.map.get(day).size(); i++) {
        if (GetOverlap(this.map.get(day).get(i), rTime) == null) {
            continue;
        }

   ArrayList<TimeRange> temp = rTime.Remove(GetOverlap(this.map.get(day).get(i), rTime));

        for(int faris = 0 ; faris < temp.size(); faris++){
            AddTime(day, temp.get(faris));
        }
        return;
        //  this.map.get(day).addAll(i,this.map.get(day).get(i).Remove(GetOverlap(this.map.get(day).get(i),rTime)));
    }
int i;
    for ( i = 0; i < this.map.get(day).size(); i++) {


        if (rTime.end <= this.map.get(day).get(i).start) {
            break;
        }
    }
    this.map.get(day).add(i, rTime); // CASE ONE COMPLETED (INSHALLAH)
    CheckArrange();

}

public void CheckArrange(){


    for (Map.Entry<String, ArrayList<TimeRange>> entry : map.entrySet()) {
     //  ArrayList<TimeRange> temp = this.map.get(entry.getKey());
        for (int i = 0; i < this.map.get(entry.getKey()).size()-1; i++) {

if(this.map.get(entry.getKey()).get(i).end == this.map.get(entry.getKey()).get(i+1).start){
    this.map.get(entry.getKey()).get(i).end = this.map.get(entry.getKey()).get(i+1).end;
    this.map.get(entry.getKey()).remove(i+1);
i--;
}
           /* if((this.map.get(entry.getKey()).get(i).start == this.map.get(entry.getKey()).get(i+1).start)&&(this.map.get(entry.getKey()).get(i).end == this.map.get(entry.getKey()).get(i+1).end)){
                this.map.get(entry.getKey()).remove(i);
            }*/
        }
    }

}


public String toString(){
        return this.map.toString();
}




    Hours(){}
    public Hours(Map<String, ArrayList<TimeRange>> map){
        this.map=map;
    }

}

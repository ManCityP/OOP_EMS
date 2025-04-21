package p1;

import java.util.*;

import static p1.TimeRange.GetOverlap;

public class Hours {
    Map<String, ArrayList<TimeRange>> map = new LinkedHashMap<>();

    void DisplayRange(String day) {

        System.out.println(this.map.get(day));


    }

    public boolean Contains(TimeRange timeRange, String day) {
        boolean isContained = false;
        for (int i = 0; i < this.map.get(day).size(); i++) {
            if (this.map.get(day).get(i).Contains(timeRange)) {
                isContained = true;
            }
        }
        return isContained;
    }

    public static boolean Contains(Map<Day, ArrayList<TimeRange>> map, TimeRange timeRange, String day) {
        boolean isContained = false;
        for (int i = 0; i < map.get(day).size(); i++) {
            if (map.get(day).get(i).Contains(timeRange)) {
                isContained = true;
            }
        }
        return isContained;
    }

    public void RemoveTime(String day, TimeRange rTime) throws Exception {
        ArrayList<TimeRange> temp = new ArrayList<>();
        for (TimeRange timeRange : this.map.get(day)) {
            temp.addAll(timeRange.Remove(GetOverlap(timeRange, rTime)));
        }
        for (TimeRange timeRange : temp)
            if (timeRange == null)
                temp.remove(timeRange);
        this.map.put(day, temp);
    }



    public void AddTime(String day, TimeRange rTime) throws Exception {

        if (!this.map.containsKey(day)) {
            this.map.put(day, new ArrayList<>(List.of(rTime)));
        } else {
            for (int i = 0; i < this.map.get(day).size(); i++) {
                if (GetOverlap(this.map.get(day).get(i), rTime) == null) {
                    continue;
                }

                ArrayList<TimeRange> temp = rTime.Remove(GetOverlap(this.map.get(day).get(i), rTime));

                for (int faris = 0; faris < temp.size(); faris++) {
                    AddTime(day, temp.get(faris));
                }
                return;
                //  this.map.get(day).addAll(i,this.map.get(day).get(i).Remove(GetOverlap(this.map.get(day).get(i),rTime)));
            }
            int i;
            for (i = 0; i < this.map.get(day).size(); i++) {


                if (rTime.end <= this.map.get(day).get(i).start) {
                    break;
                }
            }
            this.map.get(day).add(i, rTime); // CASE ONE COMPLETED (INSHALLAH)
            CheckArrange();

        }
    }

    public void CheckArrange() {


        for (Map.Entry<String, ArrayList<TimeRange>> entry : map.entrySet()) {
            //  ArrayList<TimeRange> temp = this.map.get(entry.getKey());
            for (int i = 0; i < this.map.get(entry.getKey()).size() - 1; i++) {

                if (this.map.get(entry.getKey()).get(i).end == this.map.get(entry.getKey()).get(i + 1).start) {
                    this.map.get(entry.getKey()).get(i).end = this.map.get(entry.getKey()).get(i + 1).end;
                    this.map.get(entry.getKey()).remove(i + 1);
                    i--;
                }
           /* if((this.map.get(entry.getKey()).get(i).start == this.map.get(entry.getKey()).get(i+1).start)&&(this.map.get(entry.getKey()).get(i).end == this.map.get(entry.getKey()).get(i+1).end)){
                this.map.get(entry.getKey()).remove(i);
            }*/
            }
        }

    }


    public String toString() {
        return this.map.toString();
    }

    public Hours(Map<String, ArrayList<TimeRange>> map) {
        this.map = map;
    }

}

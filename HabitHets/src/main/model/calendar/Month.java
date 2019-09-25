package main.calender;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Month {
    List<Day> days;
    int monthNb;
    public Month(int year, int monthNr) {
        this.days = getDays(year, monthNr);
    }

    public List<Day> getDays(int year, int monthNb){
        LocalDateTime ldt = LocalDateTime.of(year, monthNb,1,0,0);
        List<Day> tmpDays = new ArrayList<>();
        while (ldt.getMonthValue() == monthNb){
            Day d = new Day(ldt);
            tmpDays.add(d);
            ldt = ldt.plusDays(1);
        }
        return tmpDays;
    }
}

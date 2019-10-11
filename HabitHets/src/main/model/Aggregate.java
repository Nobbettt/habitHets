package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Aggregate {
    private Calender calender = Calender.getInstant();

    public List<Day> getWeekFromDate(LocalDateTime localDateTime) {
        return calender.getWeekFromLDT(localDateTime);
    }

    public List<Day> getDayFromDate(LocalDateTime localDateTime) {
        List<Day> singleDayList = new ArrayList();
        singleDayList.add(calender.getDayFromLDT(localDateTime));
        return singleDayList;
    }

    public List<Month> getYearFromDate(LocalDateTime localDateTime) {
        return calender.getYearFromLDT(localDateTime);
    }

    public Month getMonth (LocalDateTime ldt){
        return calender.getMonth(ldt);
    }
}

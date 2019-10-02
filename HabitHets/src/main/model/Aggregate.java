package main.model;

import java.time.LocalDateTime;
import java.util.List;

public class Aggregate {
    private Calender calender = Calender.getInstant();

    public List<Day> getWeekFromDate(LocalDateTime localDateTime) {
        return calender.getWeekFromLDT(localDateTime);
    }
}

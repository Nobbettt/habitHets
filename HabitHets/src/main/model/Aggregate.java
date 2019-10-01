package main.model;

import java.util.List;

public class Aggregate {
    private Calender calender = new Calender();

    public List<Day> getDaysFromWeek(int year, int weekNr) {
        return calender.getWeekFromWeek(year, weekNr);
    }
}

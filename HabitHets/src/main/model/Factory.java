package main.model;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class Factory {
    private static int id = 0;

    public static Event createBasicEvent(LocalDateTime start, LocalDateTime end, String title){
        Event createdBasicEvent = new Event(id,start, end, title);
        id++;
        return createdBasicEvent;
    }

    public static Event createAdvEvent(LocalDateTime start, LocalDateTime end, String title, String location, String desc, String color){
        Event createdAdvEvent = new Event(id,start, end, title,location,desc,color);
        id++;
        return createdAdvEvent;
    }

    public static Habit createHabit(String title, boolean checkedToday, LocalDate lastChecked, int currentStreak, int bestStreak, String description, String color){
        Habit createHabit = new Habit(id,title,checkedToday,lastChecked,currentStreak,bestStreak,description,color);
        id++;
        return createHabit;
    }
}

package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Day implements CalendarAble {
    private LocalDateTime ldt;

    public Day(LocalDateTime ldt) {
        this.ldt = ldt;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public int getYearNbr(){
        return ldt.getYear();
    }

    public int getWeekNr(){
        Calendar javaCal = new GregorianCalendar();
        javaCal.set(getLdt().getYear(), getLdt().getMonthValue(), getLdt().getDayOfMonth());
        return javaCal.get(javaCal.WEEK_OF_YEAR);
    }

    public String getDateString() {
        return "" + ldt.getDayOfMonth() + "/" + ldt.getMonthValue() + "";
    }

    public List<Event> getEventsOfDay(){
        EventHandler eventHandler = EventHandler.getInstant();
        List<Event> eventList = new ArrayList<>();
        for (Event event : eventHandler.getEventList()){
            if (event.getStartTime().getYear() == ldt.getYear() && event.getStartTime().getDayOfYear() == ldt.getDayOfYear()){
                eventList.add(event);
            }
        }
        return eventList;
    }
}

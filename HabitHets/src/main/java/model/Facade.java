package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Facade {
    private Calender calender = Calender.getInstant();
    private EventOrganizer eventOrganizer = EventOrganizer.getInstant();

    public List<Day> getWeekFromDate(LocalDateTime localDateTime) {
        return calender.getWeekFromLDT(localDateTime);
    }

    public List<Day> getDayFromDate(LocalDateTime localDateTime) {
        List<Day> singleDayList = new ArrayList();
        singleDayList.add(calender.getDayFromLDT(localDateTime));
        return singleDayList;
    }
    public List<Day> getMonthFromDate(LocalDateTime localDateTime) {
        return calender.getMonth(localDateTime).getDays();
    }
    public List<Month> getYearFromDate(LocalDateTime localDateTime) {
        return calender.getYearFromLDT(localDateTime);
    }

    public Month getMonth (LocalDateTime ldt){
        return calender.getMonth(ldt);
    }

    public Event getEventFromID(int id){
        return eventOrganizer.getEventOfId(id);
    }

    public String getEventTitle(int id){
        Event event = eventOrganizer.getEventOfId(id);
        return event.getTitle();
    }

    public String getEventLocation(int id){
        Event event = eventOrganizer.getEventOfId(id);
        return event.getLocation();
    }

    public String getEventDesc(int id){
        Event event = eventOrganizer.getEventOfId(id);
        return event.getDescription();
    }

    public LocalDateTime getEventStarttime(int id){
        Event event = eventOrganizer.getEventOfId(id);
        return event.getStartTime();
    }

    public LocalDateTime getEventEndtime(int id){
        Event event = eventOrganizer.getEventOfId(id);
        return event.getEndTime();
    }

    public List<Integer> getAllIdsOfDay(LocalDateTime ltd){
        return eventOrganizer.getAllIDsOfDay(ltd);
    }

    public long getLength(int id){
        long valueMinute = ChronoUnit.MINUTES.between(eventOrganizer.getEventOfId(id).getStartTime(), eventOrganizer.getEventOfId(id).getEndTime());
        return (valueMinute);
    }

    public double calculateWidth(LocalDateTime dateTime, int id) {
        double i = 0;
        List<Event> overlaps = new ArrayList<>();
        List<Event> overlaps2 = new ArrayList<>();
        List<Event> events = EventOrganizer.getInstant().getEventsOfDay(dateTime);
        Interval interval = new Interval(getEventStarttime(id), true, getEventEndtime(id), true);
        for (Event tmpEvent : events) {
            Interval tmpInterval = new Interval(tmpEvent.getStartTime(), true, tmpEvent.getEndTime(), true);
            if (interval.overlaps(tmpInterval)) {
                i++;
                if (!overlaps.contains(tmpEvent) && !tmpEvent.equals(getEventFromID(id))) {
                    overlaps.add(tmpEvent);
                }
            }
        }
        for (Event event1 : overlaps) {
            Interval interval1 = new Interval(event1.getStartTime(), true, event1.getEndTime(), true);
            for (Event event2 : events) {
                Interval interval2 = new Interval(event2.getStartTime(), true, event2.getEndTime(), true);
                if (interval1.overlaps(interval2) && event2 != event1 && event2 != getEventFromID(id)) {
                    i++;
                    if (!overlaps2.contains(event2) && !event2.equals(event1)) {
                        overlaps2.add(event2);
                    }
                }
            }
        }
        events.add(getEventFromID(id));
        //double vboxWidth = getVboxWidth();
        return i;
    }

}
package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventHandler implements IHandler {

    public static EventHandler instant;
    private static List<Event> eventList;

    private EventHandler() {
        this.eventList = new ArrayList<>();
    }

    public static EventHandler getInstant() {
        if (instant == null) {

            instant = new EventHandler();
            return instant;

        } else {
            return instant;
        }
    }


    @Override
    public void add() { //todo
        eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019,10,9,12,30), LocalDateTime.of(2019,10,9,13,30), "hello3"));
        eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019,10,9,11,00), LocalDateTime.of(2019,10,9,12,00), "hello"));
        eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019,10,9,11,45), LocalDateTime.of(2019,10,9,12,45), "hello2"));
        eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019,10,9,21,45), LocalDateTime.of(2019,10,9,22,45), "hello4"));

    }

    @Override
    public void remove(int id) {
        for (Event event : eventList){
            if (event.getId() == id){
                eventList.remove(event);
                return;
            }
        }
        System.out.println("The ID: '" + id + "' does not exist");
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public List<Event> getEventsOfDay(Day day){
        List<Event> eventList = new ArrayList<>();
        for (Event event : getEventList()){
            if (event.getStartTime().getYear() == day.getLdt().getYear() && event.getStartTime().getDayOfYear() == day.getLdt().getDayOfYear()){
                eventList.add(event);
            }
        }
        return eventList;
    }
}

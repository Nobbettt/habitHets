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
        eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019,10,07,9,30), LocalDateTime.of(2019,10,7,13,30),"Hets i Dicken"));
        eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019,10,9,11,45), LocalDateTime.of(2019,10,9,13,15), "Lunch"));
        eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019,10,11,21,15), LocalDateTime.of(2019,10,11,23,59), "FEST FÖFAN"));

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
}

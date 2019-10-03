package main.java.model;

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
    public void add() {
        eventList.add(Factory.createBasicEvent(LocalDateTime.now().plusMinutes(5), LocalDateTime.now().plusMinutes(50),"TestEvent"));
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

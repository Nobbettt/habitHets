package main.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventOrganizer implements IHandler {

    private static EventOrganizer instant;
    private static List<Event> eventList;
    private int i; //todo

    private EventOrganizer() {
        eventList = new ArrayList<>();
        i = 0;
    }

    public static EventOrganizer getInstant() {
        if (instant == null) {

            instant = new EventOrganizer();
            return instant;

        } else {
            return instant;
        }
    }


    @Override
    public void add() { //todo
        if (i == 0) {
            eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019, 10, 9, 12, 30), LocalDateTime.of(2019, 10, 9, 13, 30), "hello3"));
        } else if (i == 1) {
            eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019, 10, 9, 11, 00), LocalDateTime.of(2019, 10, 9, 12, 00), "hello"));
        } else if (i == 2) {
            eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019, 10, 9, 11, 45), LocalDateTime.of(2019, 10, 9, 12, 45), "hello2"));
        } else if (i == 3) {
            eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019, 10, 9, 21, 45), LocalDateTime.of(2019, 10, 9, 22, 45), "hello4"));
        }else if (i == 4) {
            eventList.add(Factory.createBasicEvent(LocalDateTime.of(2019, 10, 9, 9, 10), LocalDateTime.of(2019, 10, 9, 10, 30), "hello4"));
        }
        i++;
    }

    public void addEvent(LocalDateTime ldt, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute, String title, String location, String desc){
        eventList.add(Factory.createAdvEvent(ldt.withHour(startHour).withMinute(startMinute), ldt.withHour(endHour).withMinute(endMinute), title, location, desc, null));
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

    public void editEvent(int id, String title, String location, String desc, LocalDateTime startTime, LocalDateTime endTime){
        Event eventToEdit = getEventOfId(id);
        eventToEdit.setTitle(title);
        eventToEdit.setLocation(location);
        eventToEdit.setDescription(desc);
        eventToEdit.setStartTime(startTime);
        eventToEdit.setEndTime(endTime);
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public List<Event> getEventsOfDay(LocalDateTime ldt){
        List<Event> eventList = new ArrayList<>();
        for (Event event : getEventList()){
            if (event.getStartTime().getYear() == ldt.getYear() && event.getStartTime().getDayOfYear() == ldt.getDayOfYear()){
                eventList.add(event);
            }
        }
        return eventList;
    }

    public Event getEventOfId(int id){
        for (Event event : eventList){
            if (event.getId() == id){
                return event;
            } else {
                System.out.println("" + event.getTitle() +"NO SUCH EVENT");
            }
        }
        return null;
    }

    public List<Integer> getAllIDsOfDay(LocalDateTime ldt){
        List<Integer> ids = new ArrayList<>();
        for (Event event : eventList){
            if (event.getStartTime().getYear() == ldt.getYear() && event.getStartTime().getDayOfYear() == ldt.getDayOfYear()) {
                ids.add(event.getId());
            }
        }
        return ids;
    }
}

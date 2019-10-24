package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockEventOrganizer implements IHandler {
    private static MockEventOrganizer instant;
    private static List<Event> eventList;

    private MockEventOrganizer() {
        eventList = new ArrayList<>();
    }

    static MockEventOrganizer getInstant() {
        if (instant == null) {
            instant = new MockEventOrganizer();
            return instant;
        } else {
            return instant;
        }
    }

    static void addEvent(LocalDateTime ldt, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute, String title, String location, String desc){
        eventList.add(Factory.createAdvEvent(ldt.withHour(startHour).withMinute(startMinute), ldt.withHour(endHour).withMinute(endMinute), title, location, desc, null));
    }

    static void setEventList(List<Event> eventList) {
        MockEventOrganizer.eventList = eventList;
    }

    @Override
    public void remove(int id) {
        for (Event event : eventList){
            if (event.getId() == id){
                eventList.remove(event);
                return;
            }
        }
    }

    static void editEvent(int id, String title, String location, String desc, LocalDateTime startTime, LocalDateTime endTime){
        Event eventToEdit = getEventOfId(id);
        eventToEdit.setTitle(title);
        eventToEdit.setLocation(location);
        eventToEdit.setDescription(desc);
        eventToEdit.setStartTime(startTime);
        eventToEdit.setEndTime(endTime);
    }

    static List<Event> getEventList() {
        return eventList;
    }

    static List<Event> getEventsOfDay(LocalDateTime ldt){
        List<Event> eventList = new ArrayList<>();
        for (Event event : getEventList()){
            if (event.getStartTime().getYear() == ldt.getYear() && event.getStartTime().getDayOfYear() == ldt.getDayOfYear()){
                eventList.add(event);
            }
        }
        return eventList;
    }

    static Event getEventOfId(int id){
        for (Event event : eventList){
            if (event.getId() == id){
                return event;
            }
        }
        return null;
    }

    static List<Integer> getAllIDsOfDay(LocalDateTime ldt){
        List<Integer> ids = new ArrayList<>();
        for (Event event : eventList){
            if (event.getStartTime().getYear() == ldt.getYear() && event.getStartTime().getDayOfYear() == ldt.getDayOfYear()) {
                ids.add(event.getId());
            }
        }
        return ids;
    }
}

package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *This class handles logic that has to do with more than one event.
 */
class EventOrganizer implements IOrganizer {
    private static List<Event> eventList;

    /**
     * A private constructor for the EventOrganizer so that only a single instance will exist, this is in line with the
     * Singleton Pattern
     */
    EventOrganizer() {
        if (eventList == null){
            eventList = new ArrayList<>();
        }
    }


    /**
     * Creates a new event through the Factory class and adds it to the static list of events
     * @param ldt the date on which the event will be on
     * @param startHour the hour value of the events start time
     * @param startMinute the minute value of the events start time
     * @param endHour the hour value of the events end time
     * @param endMinute the minute value of the events end time
     * @param title the title of the event
     * @param location the location of the event, in the form of a String
     * @param desc the description of the event
     */
    static void addEvent(LocalDateTime ldt, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute, String title, String location, String desc){
        eventList.add(Factory.createAdvEvent(ldt.withHour(startHour).withMinute(startMinute), ldt.withHour(endHour).withMinute(endMinute), title, location, desc, null));
    }

    /**
     * Is used by the database services that sets the list of event at the start of the application
     * @param eventList the list of events saved in the database
     */
    static void setEventList(List<Event> eventList) {
        EventOrganizer.eventList = eventList;
    }

    /**
     * an implemented method from the IOrganizer interface, removes a single event from the list of events
     * @param id the id of the event that is to be removed
     */
    @Override
    public void remove(int id) {
        for (Event event : eventList){
            if (event.getId() == id){
                eventList.remove(event);
                return;
            }
        }
    }

    /**
     * Edits an event in the eventlist updating all of that events values, all values is updated and if no change is to
     * be made the new value is identical to the old one
     * @param id the id of the event that will be update
     * @param title the new title that event will have
     * @param location the new location String the event will have
     * @param desc the new description the event will have
     * @param startTime the new start time of the event
     * @param endTime the new end time of the event
     */
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

    /**
     * A method that given a LocalDateTime that represents a date checks if any events is located on that date and
     * returns a list of all events located on that day. The list will be empty if there is no event on that date
     * @param ldt the LocatDateTime that represents the date the method will check if there is any events on
     * @return the list of events on the day
     */
    static List<Event> getEventsOfDay(LocalDateTime ldt){
        List<Event> eventList = new ArrayList<>();
        for (Event event : getEventList()){
            if (event.getStartTime().getYear() == ldt.getYear() && event.getStartTime().getDayOfYear() == ldt.getDayOfYear()){
                eventList.add(event);
            }
        }
        return eventList;
    }

    /**
     * A method that given a id, returns the event of that id from the eventlist and returns null if there is no such
     * event
     * @param id the id of the event object that should be returned
     * @return an event with the parameter id or null if there is no such event
     */
    static Event getEventOfId(int id){
        for (Event event : eventList){
            if (event.getId() == id){
                return event;
            }
        }
        return null;
    }

    /**
     * A method that given a LocalDateTime that represents a date checks if any events is located on that date and
     * returns a list of all those events ids located on that day. The list will be empty if there is no event on that date
     * @param ldt the LocatDateTime that represents the date the method will check if there is any events on
     * @return the list of ids on the day
     */
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

package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Static class that counts number of overlaps given an id that used to find event
 */
class OverlapsCounter {
    private static List<Event> overlaps;
    private static List<Event> overlaps2;
    private static Interval interval;
    private static double count;

    /**
     * Function calculates how many times a event overlaps with others
     * @param dateTime
     * @param id
     * @return
     */
    static double countOverlaps(LocalDateTime dateTime, int id) {
        count = 0;
        overlaps = new ArrayList<>();
        overlaps2 = new ArrayList<>();
        interval = new Interval(EventOrganizer.getEventOfId(id).getStartTime(), true, EventOrganizer.getEventOfId(id).getEndTime(), true);

        List<Event> events = EventOrganizer.getEventsOfDay(dateTime);

        firstCheck(events, id);
        secondCheck(events, id);

        events.add(EventOrganizer.getEventOfId(id));
        return count;
    }

    /**
     * checks if the event that is given overlaps with any other event on that day. if that is true it adds that event
     * to another list called overlaps. The events in the overlaps list is also later checked if they overlap
     * @param events the list of events on the day
     * @param id the id of the event
     */
    private static void firstCheck(List<Event> events, int id) {
        for (Event tmpEvent : events) {
            Interval tmpInterval = new Interval(tmpEvent.getStartTime(), true, tmpEvent.getEndTime(), true);
            if (interval.overlaps(tmpInterval)) {
                count++;
                if (!overlaps.contains(tmpEvent) && !tmpEvent.equals(EventOrganizer.getEventOfId(id))) {
                    overlaps.add(tmpEvent);
                }
            }
        }
    }

    /**
     * checks if any of the events that overlapped with the first event overlaps with any other events
     * @param events the list of events on that day
     * @param id the id of the event that is tested
     */
    private static void secondCheck(List<Event> events, int id) {
        for (Event event1 : overlaps) {
            Interval interval1 = new Interval(event1.getStartTime(), true, event1.getEndTime(), true);
            for (Event event2 : events) {
                Interval interval2 = new Interval(event2.getStartTime(), true, event2.getEndTime(), true);
                if (interval1.overlaps(interval2) && event2 != event1 && event2 != EventOrganizer.getEventOfId(id)) {
                    count++;
                    if (!overlaps2.contains(event2) && !event2.equals(event1)) {
                        overlaps2.add(event2);
                    }
                }
            }
        }
    }
}
